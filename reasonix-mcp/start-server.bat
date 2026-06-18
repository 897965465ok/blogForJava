@echo off
chcp 65001 >nul
echo ========================================
echo  Reasonix MCP Server — 启动中...
echo ========================================
echo.
echo 请保持此窗口打开，关闭窗口即停止服务
echo.
cd /d "%~dp0"
python server.py
if %errorlevel% neq 0 (
    echo.
    echo ❌ 启动失败，请确认已安装 Python 和 mcp 包
    echo 运行: pip install mcp
    pause
)
