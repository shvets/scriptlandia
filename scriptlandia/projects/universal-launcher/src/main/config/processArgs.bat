rem 1. init

set CMD_LINE_ARGS=

rem 2. Process bootclasspath, command line arguments

:setupArgs

if "%~1"=="" goto doneStart

set TEMP=%~1
set PARAM=%TEMP:~0,15%
set PARAM=%TEMP:~0,2%

if "%PARAM%"=="-D" goto prepareSystemParameters

if "%PARAM%"=="-X" goto prepareBootClasspath

if "%TEMP%"=="-debug" goto prepareDebugParameters

set PARAM=%TEMP:~0,4%

if "%PARAM%"=="-ng" goto prepareNailgunMode

set PARAM=%TEMP:~0,19%

if "%PARAM%"=="-Djava.library.path" goto prepareJavaLibraryPath

set CMD_LINE_ARGS=%CMD_LINE_ARGS% "%~1%"

:endSetupArgs

shift

goto setupArgs


:prepareBootClasspath
set PARAMETERS=%PARAMETERS% "%~1%"

goto endSetupArgs


:prepareSystemParameters
set PARAMETERS=%PARAMETERS% "%~1%"

goto endSetupArgs

:prepareDebugParameters
set PARAMETERS=%PARAMETERS% -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=6006

goto endSetupArgs

:prepareNailgunMode
set NAILGUN_MODE=true
goto endSetupArgs

:prepareJavaLibraryPath
set PARAMETERS=%PARAMETERS% "%~1%"
goto endSetupArgs

:doneStart
