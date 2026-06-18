#!/usr/bin/env bash
# Reasonix MCP Server 启动脚本（macOS / Linux）
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR" || exit 1
echo "========================================"
echo " Reasonix MCP Server — 启动中..."
echo "========================================"
echo "请保持此终端窗口打开"
python3 server.py 2>/dev/null || python server.py
