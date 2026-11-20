@echo off
chcp 65001 >nul
title 停止浩天奇缘OL服务端
echo 正在停止服务端（关闭所有 java 进程中的 GameServer）...
for /f "tokens=2 delims=," %%i in ('tasklist /fi "imagename eq java.exe" /fo csv /nh 2^>nul') do (
    taskkill /pid %%~i /f >nul 2>&1
)
echo 已停止。
timeout /t 2 >nul