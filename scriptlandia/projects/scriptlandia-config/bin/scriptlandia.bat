@echo off

rem set JAVA_HOME=@java.home@
set MOBILE_JAVA_HOME=@mobile.java.home@
set SCRIPTLANDIA_HOME=@scriptlandia.home@
set LAUNCHER_HOME=@launcher.home@

SET NAILGUN=@repository.home@\com\martiansoftware\nailgun-bin\@nailgun.version@\ng

set APP=%~nx0
set APP_NAME=%APP:~0,-4%
set APP=%SCRIPTLANDIA_HOME%\%APP:~0,-4%

FOR %%i IN (%*) DO call :processParam ^"%%i^"

goto startLauncher

:processParam

SET LINE=%1
SET CHARS=%LINE:~1,4%

if ^"%CHARS%^" == ^"-ng"^" SET NAILGUN_MODE=true

goto end

:startLauncher

if "%NAILGUN_MODE%"=="true" goto nailgun
goto runJava

:nailgun
SET LAUNCHER_CLASS=org.codehaus.classworlds.Launcher
%NAILGUN% %LAUNCHER_CLASS% -ng %*
goto end

:runJava
call %LAUNCHER_HOME%\launcher-core.bat %*

:end
