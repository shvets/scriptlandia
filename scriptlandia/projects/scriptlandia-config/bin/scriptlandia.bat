@echo off

set MOBILE_JAVA_HOME=@mobile.java.home@
set SCRIPTLANDIA_HOME=@scriptlandia.home@
set JLAUNCHPAD_HOME=@jlaunchpad.home@

SET NAILGUN=@repository.home@\com\martiansoftware\nailgun-bin\@nailgun.version@\ng


SET MAIN_APP_CONF=%SCRIPTLANDIA_HOME%\%APP_NAME%.jlcnf

if not exist %MAIN_APP_CONF% (
  SET MAIN_APP_CONF=%SCRIPTLANDIA_HOME%\scriptlandia.jlcnf
)

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
call %JLAUNCHPAD_HOME%\jlaunchpad-core.bat %*

:end
