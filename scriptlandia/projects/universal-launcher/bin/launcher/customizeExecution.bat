if "%NAILGUN_MODE%"=="true" goto nailgun
goto runJava

:nailgun
SET LAUNCHER_CLASS=org.sf.scriptlandia.classworlds.launcher.Launcher
%NAILGUN% %LAUNCHER_CLASS% %CMD_LINE_ARGS% -ng
goto end

:runJava
SET CMD_LINE_ARGS=%*
SET PROCEED=true

:end

