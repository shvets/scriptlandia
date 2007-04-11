IF NOT "%JAVA_BINARY%" == "" goto start

echo "Variable JAVA_BINARY is not set up."

goto end

:start

rem 1. init

set BOOT_CLASSPATH=
SET SYSTEM_PARAMETERS=%SYSTEM_PARAMETERS%
set CMD_LINE_ARGS=

rem 2. Process bootclasspath, command line arguments

:setupArgs

if "%~1"=="" goto doneStart

set TEMP=%~1
set PARAM=%TEMP:~0,15%

rem if "%PARAM%"=="-Xbootclasspath" goto prepareBootClasspath

set PARAM=%TEMP:~0,2%

if "%PARAM%"=="-D" goto prepareSystemParameters

if "%PARAM%"=="-X" goto prepareBootClasspath

if "%TEMP%"=="-debug" goto prepareDebugParameters

set PARAM=%TEMP:~0,4%

if "%PARAM%"=="-ng" goto prepareNailgunMode

set CMD_LINE_ARGS=%CMD_LINE_ARGS% "%~1%"

:endSetupArgs

shift

goto setupArgs


:prepareBootClasspath
set BOOT_CLASSPATH=%BOOT_CLASSPATH% "%~1%"

goto endSetupArgs


:prepareSystemParameters
set SYSTEM_PARAMETERS=%SYSTEM_PARAMETERS% "%~1%"

goto endSetupArgs

:prepareDebugParameters
set DEBUG_PARAMETERS=-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=6006

goto endSetupArgs

:prepareNailgunMode
set NAILGUN_MODE=true
goto endSetupArgs

:doneStart

IF "@proxy.server.host@" == "" goto setup

SET SYSTEM_PARAMETERS=%SYSTEM_PARAMETERS% -Dproxy.server.host=@proxy.server.host@ 
SET SYSTEM_PARAMETERS=%SYSTEM_PARAMETERS% -Dproxy.server.port=@proxy.server.port@ 
SET SYSTEM_PARAMETERS=%SYSTEM_PARAMETERS% -Dhttp.proxyHost=@proxy.server.host@
SET SYSTEM_PARAMETERS=%SYSTEM_PARAMETERS% -Dhttp.proxyPort=@proxy.server.port@

:setup
SET SYSTEM_PARAMETERS=%SYSTEM_PARAMETERS% -Xmx256m %DEBUG_PARAMETERS% 
set SYSTEM_PARAMETERS=%SYSTEM_PARAMETERS% -Drepository.home=@repository.home@
set SYSTEM_PARAMETERS=%SYSTEM_PARAMETERS% -Dclassworlds.conf=@scriptlandia.home@\scriptlandia.conf
rem SET SYSTEM_PARAMETERS=%SYSTEM_PARAMETERS% -Djavax.xml.parsers.SAXParserFactory=com.bluecast.xml.JAXPSAXParserFactory
rem SET CLASSPATH=@repository.home@\classworlds\classworlds\@classworlds.version@\classworlds-@classworlds.version@.jar
SET CLASSPATH=@classworlds.base@\classworlds-@classworlds.version@.jar
SET CLASSPATH=%CLASSPATH%;@repository.home@\org\sf\scriptlandia\classworlds-launcher\2.2.1\classworlds-launcher-2.2.1.jar
SET CLASSPATH=%CLASSPATH%;@repository.home@\org\sf\scriptlandia\scriptlandia-common\2.2.1\scriptlandia-common-2.2.1.jar

rem due to fortess issue
SET CLASSPATH=%CLASSPATH%;@repository.home@\com\sun\fortress\fortress\156\fortress-156.jar
SET CLASSPATH=%CLASSPATH%;@repository.home@\concurrent\concurrent\1.3.4\concurrent-1.3.4.jar
SET CLASSPATH=%CLASSPATH%;@repository.home@\xtc\xtc\1.10.0\xtc-1.10.0.jar
SET CLASSPATH=%CLASSPATH%;@repository.home@\dstm\dstm\2.0\dstm-2.0.jar
SET CLASSPATH=%CLASSPATH%;@repository.home@\bcel\bcel\5.2\bcel-5.2.jar

if "%MOBILE_JAVA_HOME%"=="" goto run

SET MOBILE_CLASSPATH=%MOBILE_JAVA_HOME%\wtklib\kenv.zip
SET MOBILE_CLASSPATH=%MOBILE_CLASSPATH%;%MOBILE_JAVA_HOME%\wtklib\ktools.zip
SET MOBILE_CLASSPATH=%MOBILE_CLASSPATH%;%MOBILE_JAVA_HOME%\wtklib\customjmf.jar

SET MOBILE_PARAMETERS=-Dmobile.java.home=%MOBILE_JAVA_HOME%
SET MOBILE_PARAMETERS=%MOBILE_PARAMETERS% -Dkvem.home=%MOBILE_JAVA_HOME%
SET MOBILE_PARAMETERS=%MOBILE_PARAMETERS% -Djava.library.path=@mobile.java.home@/bin
SET MOBILE_PARAMETERS=%MOBILE_PARAMETERS% -Dsun.java2d.ddlock=true -Dsun.java2d.gdiblit=false 

SET CLASSPATH=%MOBILE_CLASSPATH%;%CLASSPATH%
SET SYSTEM_PARAMETERS=%MOBILE_PARAMETERS% %SYSTEM_PARAMETERS%

rem 3. launch JVM

:run

rem SET LAUNCHER_CLASS=org.codehaus.classworlds.Launcher
SET LAUNCHER_CLASS=org.sf.scriptlandia.classworlds.launcher.Launcher

if "%NAILGUN_MODE%"=="true" goto nailgun
goto runJava

:nailgun
@repository.home@\com\martiansoftware\nailgun-bin\@nailgun.version@\ng %LAUNCHER_CLASS% %CMD_LINE_ARGS% -ng
goto end

:runJava
SET CMD_LINE_ARGS=%*
%JAVA_BINARY% %BOOT_CLASSPATH% %SYSTEM_PARAMETERS% -classpath %CLASSPATH% %LAUNCHER_CLASS% %CMD_LINE_ARGS%

:end
