if "%NAILGUN_MODE%"=="true" goto nailgun
goto runJava

:nailgun
echo %NAILGUN% %LAUNCHER_CLASS% %CMD_LINE_ARGS% -ng
goto end

:runJava
SET CMD_LINE_ARGS=%*
SET PROCEED=true

:end

