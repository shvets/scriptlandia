@echo off

set JAVA_HOME=@java.home.internal@
set LAUNCHER_HOME=@launcher.home@

set APP=%~nx0
set APP_NAME=%APP:~0,-4%
set APP=%LAUNCHER_HOME%\%APP:~0,-4%

call %LAUNCHER_HOME%\launcher-core.bat %*
