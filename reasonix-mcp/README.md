# Reasonix MCP Server — IntelliJ IDEA 对接指南

## 📦 方案：Continue 插件 + MCP 协议

在 IntelliJ IDEA 中安装 Continue 插件，通过 MCP 协议连接 Reasonix 服务端，在 IDE 内直接使用 Reasonix 的代码分析、文件操作、命令执行等能力。

---

## 第一步：安装 Continue 插件

1. 打开 IntelliJ IDEA → **File → Settings → Plugins**
2. 搜索 **Continue** 并安装（JetBrains Marketplace）
3. 重启 IDE

---

## 第二步：配置 Continue 连接 Reasonix

### 方法 A：自动配置（推荐）

在 IntelliJ 中打开 Continue 面板（侧边栏图标或 `Ctrl+Shift+R` / `Cmd+Shift+R`），
点击 Continue 面板右上角的齿轮 ⚙️ → **Open Config**，打开 `~/.continue/config.json`，将以下内容**合并**进去：

```json
{
  "mcpServers": {
    "reasonix": {
      "command": "python",
      "args": ["G:\\JAVA\\Mblog\\reasonix-mcp\\server.py"],
      "env": {},
      "disabled": false,
      "alwaysAllow": [
        "read_file", "write_file", "edit_file",
        "grep", "glob", "ls", "bash", "get_project_info"
      ]
    }
  }
}
```

> ⚠️ 将 `G:\\JAVA\\Mblog\\reasonix-mcp\\server.py` 替换为你的实际路径

### 方法 B：手动复制

将 `reasonix-mcp/continue-config.json` 中的 `mcpServers` 部分复制到 `~/.continue/config.json`。

---

## 第三步：启动并使用

1. **启动 MCP 服务端**（后台保持运行）：
   ```bash
   cd reasonix-mcp
   python server.py
   ```
   或在终端中双击 `start-server.bat`（Windows）。

2. 在 IntelliJ IDEA 中打开 Continue 面板（`Ctrl+Shift+R` / `Cmd+Shift+R`）

3. 在聊天中，Continue 的 AI 会自动发现 Reasonix 提供的工具，你可以在对话中让它：
   - **读取文件**："读一下 src/main/java/.../Application.java"
   - **搜索代码**："在这个项目里搜索 @Service 注解"
   - **编辑文件**："把 MyController 中的 @RequestMapping 改成 @GetMapping"
   - **执行命令**："帮我运行 mvn test"
   - **查看项目结构**："这个项目是什么结构？"

---

## 可用工具列表

| 工具 | 说明 |
|------|------|
| `read_file` | 读取文件，支持行号偏移和截取 |
| `write_file` | 写入/覆盖文件 |
| `edit_file` | 精确匹配替换，实现精准修改 |
| `grep` | 正则搜索代码 |
| `glob` | 按模式查找文件 |
| `bash` | 执行 shell 命令 |
| `ls` | 列出目录内容 |
| `get_project_info` | 获取项目构建工具和文件统计 |

---

## 故障排除

### ❌ Continue 面板提示 "MCP server not found"
- 确认 `~/.continue/config.json` 中 `mcpServers.reasonix` 配置正确
- 确认 `python server.py` 路径正确

### ❌ 工具调用失败
- 确认 MCP 服务端进程正在运行
- 检查终端有无报错信息

### ❌ 不想每次都手动启动
- 在 IntelliJ 的 **Terminal** 中直接运行 `python G:\JAVA\Mblog\reasonix-mcp\server.py`
- 或将启动命令添加到系统开机自启

---

## 原理示意

```
┌──────────────────────┐     STDIO / HTTP      ┌────────────────────┐
│  IntelliJ IDEA        │ ◄──────────────────►  │  python server.py  │
│  ┌────────────────┐   │     MCP 协议          │  (Reasonix MCP)    │
│  │ Continue 插件  │───┤                       │  ├── read_file     │
│  │ (Chat 面板)    │   │                       │  ├── edit_file     │
│  └────────────────┘   │                       │  ├── grep / glob   │
└──────────────────────┘                       │  ├── bash          │
                                               │  └── ...           │
                                               └────────────────────┘
```
