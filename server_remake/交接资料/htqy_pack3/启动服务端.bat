@echo off
chcp 65001 >nul
title 浩天奇缘OL 本地服务端
cd /d "%~dp0"

REM ===== 本机局域网 IP（手机/模拟器连这个）=====
set HOST=192.168.1.107
if not "%~1"=="" set HOST=%~1

REM ===== 找 Java =====
set JAVA=java
if exist "C:\Program Files\Common Files\Oracle\Java\javapath\java.exe" set JAVA=C:\Program Files\Common Files\Oracle\Java\javapath\java.exe
if exist "%~dp0jre\bin\java.exe" set JAVA=%~dp0jre\bin\java.exe

echo ================================================
echo   浩天奇缘OL 本地服务端
echo   登录口 28000   游戏口 2890   GM口 29002(仅本机)
echo   对外地址 %HOST%
echo ================================================
echo.
echo 正在启动，日志写入 server_out.log ...
echo 关闭这个窗口即停止服务端。
echo.

"%JAVA%" -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dgame.host=%HOST% -cp classes htqysrv.GameServer

echo.
echo 服务端已退出。
pause