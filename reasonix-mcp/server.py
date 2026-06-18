"""
Reasonix MCP Server
为 IntelliJ IDEA（通过 Continue 插件）提供代码分析、文件操作等工具
"""
from mcp.server import Server, NotificationOptions
from mcp.server.models import InitializationOptions
import mcp.server.stdio
import mcp.types as types

import os
import subprocess
import json
import re
import asyncio
from pathlib import Path

# ── 辅助函数 ─────────────────────────────────────

def _resolve_path(path: str) -> Path:
    """解析并校验路径安全性"""
    p = Path(path)
    if p.is_absolute():
        return p.resolve()
    return (Path.cwd() / p).resolve()

def _safe_path(p: Path) -> bool:
    """简单安全检查：跳过常见的二进制/生成目录"""
    skip = {".git", "node_modules", ".venv", "venv", "target", "build",
            "dist", ".mvn", ".gradle", "__pycache__", ".idea"}
    return not any(part in skip for part in p.parts)


# ── MCP Server ──────────────────────────────────

server = Server("reasonix-mcp")


@server.list_tools()
async def handle_list_tools() -> list[types.Tool]:
    return [
        types.Tool(
            name="read_file",
            description="读取文件内容，支持行号偏移和行数限制",
            inputSchema={
                "type": "object",
                "properties": {
                    "path": {"type": "string", "description": "文件路径（绝对或相对）"},
                    "offset": {"type": "integer", "description": "起始行（0-based，可选）"},
                    "limit": {"type": "integer", "description": "最大行数（可选）"},
                },
                "required": ["path"],
            },
        ),
        types.Tool(
            name="write_file",
            description="写入文件（覆盖已有内容）",
            inputSchema={
                "type": "object",
                "properties": {
                    "path": {"type": "string", "description": "文件路径"},
                    "content": {"type": "string", "description": "文件内容"},
                },
                "required": ["path", "content"],
            },
        ),
        types.Tool(
            name="edit_file",
            description="替换文件中精确匹配的文本段（必须唯一匹配）",
            inputSchema={
                "type": "object",
                "properties": {
                    "path": {"type": "string", "description": "文件路径"},
                    "old_string": {"type": "string", "description": "被替换的精确文本"},
                    "new_string": {"type": "string", "description": "替换后的文本"},
                },
                "required": ["path", "old_string", "new_string"],
            },
        ),
        types.Tool(
            name="grep",
            description="在文件中搜索正则表达式",
            inputSchema={
                "type": "object",
                "properties": {
                    "pattern": {"type": "string", "description": "正则表达式"},
                    "path": {"type": "string", "description": "搜索路径（可选，默认当前目录）"},
                    "max_results": {"type": "integer", "description": "最多返回结果数（默认50）"},
                },
                "required": ["pattern"],
            },
        ),
        types.Tool(
            name="glob",
            description="使用 glob 模式查找文件",
            inputSchema={
                "type": "object",
                "properties": {
                    "pattern": {"type": "string", "description": "Glob 模式，如 **/*.java"},
                    "path": {"type": "string", "description": "搜索根路径（可选）"},
                },
                "required": ["pattern"],
            },
        ),
        types.Tool(
            name="bash",
            description="执行 shell 命令",
            inputSchema={
                "type": "object",
                "properties": {
                    "command": {"type": "string", "description": "要执行的命令"},
                    "timeout": {"type": "integer", "description": "超时秒数（默认30）"},
                },
                "required": ["command"],
            },
        ),
        types.Tool(
            name="ls",
            description="列出目录内容",
            inputSchema={
                "type": "object",
                "properties": {
                    "path": {"type": "string", "description": "目录路径（可选，默认当前目录）"},
                    "recursive": {"type": "boolean", "description": "是否递归列出"},
                },
            },
        ),
        types.Tool(
            name="get_project_info",
            description="获取项目基本信息（构建工具、文件统计等）",
            inputSchema={
                "type": "object",
                "properties": {
                    "path": {"type": "string", "description": "项目根路径（可选）"},
                },
            },
        ),
    ]


@server.call_tool()
async def handle_call_tool(
    name: str, arguments: dict | None
) -> list[types.TextContent | types.ImageContent | types.EmbeddedResource]:
    if arguments is None:
        arguments = {}

    try:
        if name == "read_file":
            path = _resolve_path(arguments["path"])
            content = path.read_text(encoding="utf-8", errors="replace")
            lines = content.splitlines()
            offset = arguments.get("offset", 0)
            limit = arguments.get("limit", len(lines))
            selected = lines[offset: offset + limit]
            result = "\n".join(
                f"{i + 1 + offset:>6}→{line}" for i, line in enumerate(selected)
            )
            total = len(lines)
            meta = f"\n--- {len(selected)}/{total} lines ---"
            return [types.TextContent(type="text", text=result + meta)]

        elif name == "write_file":
            path = _resolve_path(arguments["path"])
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_text(arguments["content"], encoding="utf-8")
            return [types.TextContent(type="text", text=f"✅ 已写入 {path}")]

        elif name == "edit_file":
            path = _resolve_path(arguments["path"])
            old = arguments["old_string"]
            new = arguments["new_string"]
            content = path.read_text(encoding="utf-8")
            count = content.count(old)
            if count == 0:
                return [types.TextContent(
                    type="text",
                    text=f"❌ 未找到匹配文本:\n```\n{old[:300]}\n```"
                )]
            if count > 1:
                return [types.TextContent(
                    type="text",
                    text=f"❌ 匹配到 {count} 处（需要唯一匹配），请增加上下文"
                )]
            content = content.replace(old, new, 1)
            path.write_text(content, encoding="utf-8")
            return [types.TextContent(type="text", text=f"✅ 已编辑 {path} (1处替换)")]

        elif name == "grep":
            pattern = arguments["pattern"]
            search_path = arguments.get("path", ".")
            p = _resolve_path(search_path)
            max_results = arguments.get("max_results", 50)
            results = []
            if p.is_file():
                files = [p]
            else:
                files = sorted(p.rglob("*"))
            for f in files:
                if f.is_dir() or not _safe_path(f):
                    continue
                try:
                    text = f.read_text(encoding="utf-8", errors="replace")
                    for i, line in enumerate(text.splitlines(), 1):
                        if re.search(pattern, line):
                            results.append(f"{f}:{i}:{line[:200]}")
                            if len(results) >= max_results:
                                break
                except Exception:
                    continue
                if len(results) >= max_results:
                    break
            text = "\n".join(results) if results else "(无匹配结果)"
            return [types.TextContent(type="text", text=text)]

        elif name == "glob":
            pattern = arguments["pattern"]
            base = arguments.get("path", ".")
            p = _resolve_path(base)
            matches = sorted(p.rglob(pattern.lstrip("**/"))) if "**" in pattern else sorted(p.glob(pattern))
            text = "\n".join(str(m) for m in matches) if matches else "(无匹配文件)"
            return [types.TextContent(type="text", text=text)]

        elif name == "bash":
            cmd = arguments["command"]
            timeout = arguments.get("timeout", 30)
            try:
                r = subprocess.run(
                    cmd, shell=True, capture_output=True, text=True,
                    timeout=timeout, cwd=Path.cwd()
                )
                out = r.stdout or ""
                err = r.stderr or ""
                text = out
                if err:
                    text += f"\n--- stderr ---\n{err}"
                text += f"\n>>> exit code: {r.returncode}"
                return [types.TextContent(type="text", text=text)]
            except subprocess.TimeoutExpired:
                return [types.TextContent(type="text", text=f"❌ 命令超时 ({timeout}s)")]
            except Exception as e:
                return [types.TextContent(type="text", text=f"❌ 执行失败: {e}")]

        elif name == "ls":
            target = arguments.get("path", ".")
            p = _resolve_path(target)
            recursive = arguments.get("recursive", False)
            if recursive:
                lines = []
                for f in sorted(p.rglob("*")):
                    if not _safe_path(f):
                        continue
                    rel = f.relative_to(p)
                    depth = len(rel.parts) - 1
                    name = f"{rel.name}/" if f.is_dir() else rel.name
                    lines.append("  " * depth + name)
                text = "\n".join(lines)
            else:
                entries = sorted(p.iterdir())
                lines = []
                for e in entries:
                    if e.is_dir():
                        lines.append(f"{e.name}/")
                    else:
                        sz = e.stat().st_size
                        lines.append(f"{e.name}  ({sz} B)")
                text = "\n".join(lines) if lines else "(空目录)"
            return [types.TextContent(type="text", text=text)]

        elif name == "get_project_info":
            base = arguments.get("path", ".")
            p = _resolve_path(base)
            info = {"path": str(p)}
            if (p / "pom.xml").exists():
                info["build_tool"] = "Maven"
            elif (p / "build.gradle").exists() or (p / "build.gradle.kts").exists():
                info["build_tool"] = "Gradle"
            elif (p / "package.json").exists():
                info["build_tool"] = "npm/yarn/pnpm"
            elif (p / "Cargo.toml").exists():
                info["build_tool"] = "Cargo"
            elif (p / "go.mod").exists():
                info["build_tool"] = "Go modules"
            elif (p / "CMakeLists.txt").exists():
                info["build_tool"] = "CMake"
            else:
                info["build_tool"] = "未知"

            info["file_counts"] = {
                "java": len(list(p.rglob("*.java"))),
                "xml": len(list(p.rglob("*.xml"))),
                "properties": len(list(p.rglob("*.properties"))),
                "yaml": len(list(p.rglob("*.yml"))) + len(list(p.rglob("*.yaml"))),
            }
            return [types.TextContent(
                type="text",
                text=json.dumps(info, indent=2, ensure_ascii=False)
            )]

        else:
            return [types.TextContent(type="text", text=f"❌ 未知工具: {name}")]

    except Exception as e:
        return [types.TextContent(type="text", text=f"❌ 错误: {type(e).__name__}: {e}")]


async def main():
    async with mcp.server.stdio.stdio_server() as (read_stream, write_stream):
        await server.run(
            read_stream,
            write_stream,
            InitializationOptions(
                server_name="reasonix-mcp",
                server_version="1.0.0",
                capabilities=server.get_capabilities(
                    notification_options=NotificationOptions(),
                    experimental_capabilities={},
                ),
            ),
        )

if __name__ == "__main__":
    asyncio.run(main())
