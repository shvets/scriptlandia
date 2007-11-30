@echo off

rem set JAVA_HOME=@java.home@
set MOBILE_JAVA_HOME=@mobile.java.home@
set SCRIPTLANDIA_HOME=@scriptlandia.home@
set LAUNCHER_HOME=@launcher.home@

SET NAILGUN=@repository.home@\com\martiansoftware\nailgun-bin\@nailgun.version@\ng

rem set APP=%~nx0
rem set APP_NAME=%APP:~0,-4%
rem set APP=%SCRIPTLANDIA_HOME%\%APP:~0,-4%

if "%APP_NAME%" == "" (
  SET APP_NAME=scriptlandia
)

SET MAIN_APP_CONF=%SCRIPTLANDIA_HOME%\%APP_NAME%.conf

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
