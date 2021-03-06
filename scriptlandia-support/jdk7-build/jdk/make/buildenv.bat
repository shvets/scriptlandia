SET LD_LIBRARY_PATH=
SET CLASSPATH=
SET JAVA_HOME=
SET SHLVL=1

set OPENJDK=true

set BUILD_LANGTOOLS=false
set BUILD_JAXP=fales
set BUILD_JAXWS=false
set BUILD_CORBA=false
set BUILD_HOTSPOT=true
set BUILD_JDK=false

set USERNAME=alex
set HOME=/home/%USERNAME%

SET CYGWIN_HOME=c:/cygwin
SET TOOLS_HOME=c:/tools

SET ANT_HOME=%TOOLS_HOME%/apache-ant-1.7.0
SET FINDBUGS_HOME=%TOOLS_HOME%/findbugs-1.3.1
SET FREETYPE_HOME=%TOOLS_HOME%/freetype-2.3.4
SET UNICOWS_HOME=%TOOLS_HOME%/unicows
SET DIRECTX_HOME=%TOOLS_HOME%/dxsdk

SET ALT_FREETYPE_LIB_PATH=%FREETYPE_HOME%/windows/freetype-i586/lib
SET ALT_FREETYPE_HEADERS_PATH=%FREETYPE_HOME%/windows/freetype-i586/include
set ALT_UNICOWS_LIB_PATH=%UNICOWS_HOME%
set ALT_UNICOWS_DLL_PATH=%UNICOWS_HOME%

SET ALT_BOOTDIR=c:/Java/jdk1.6.0
SET ALT_BINARY_PLUGS_PATH=%TOOLS_HOME%/openjdk-binary-plugs
set ALT_JDK_IMPORT_PATH=c:/Java/jdk1.7.0
set ALT_DXSDK_PATH=%DIRECTX_HOME%

SET ALT_OUTPUTDIR=%TOOLS_HOME%/openjdk-output

rem SET JCE_JAR=%ALT_BOOTDIR%/jre/lib/jce.jar
SET JCE_JAR=%ALT_JDK_IMPORT_PATH%/jre/lib/jce.jar
rem SET JAVAC_JAR=%ALT_OUTPUTDIR%/dist/bootstrap/lib/javac.jar
SET JAVAC_JAR=%ALT_JDK_IMPORT_PATH%/lib/tools.jar

SET JAVAC=%ALT_BOOTDIR%/bin/java -Xmx896m -Xms128m "-Xbootclasspath/p:%JAVAC_JAR%;%JCE_JAR%" -jar %JAVAC_JAR% -source 1.5 -target 5 -encoding ascii

set VISUAL_STUDIO_HOME=C:/Program Files/Microsoft Visual Studio 9.0

set WINDOWS_SDK_HOME=C:/Program Files/Microsoft Platform SDK for Windows Server 2003 R2

rem set VS90COMNTOOLS="%VISUAL_STUDIO_HOME%/Common7/Tools"

set ALT_COMPILER_PATH=%VISUAL_STUDIO_HOME%/VC/bin

rem call %VC%/Common7/Tools/vsvars32.bat

SET VSINSTALLDIR=%VISUAL_STUDIO_HOME%
SET VCINSTALLDIR=:%VISUAL_STUDIO_HOME%/VC
SET FrameworkDir=C:/WINDOWS/Microsoft.NET/Framework
SET FrameworkVersion=v2.0.50727
SET Framework35Version=v3.5

set PATH=%VISUAL_STUDIO_HOME%/Common7/IDE
set PATH=%PATH%;%VISUAL_STUDIO_HOME%/VC/BIN;
set PATH=%PATH%;%VISUAL_STUDIO_HOME%/Common7/Tools
set PATH=%PATH%;C:\WINDOWS\Microsoft.NET\Framework\v3.5
set PATH=%PATH%;C:\WINDOWS\Microsoft.NET\Framework\v2.0.50727
set PATH=%PATH%;%VISUAL_STUDIO_HOME%/VC/VCPackages
set PATH=%PATH%;%WINDOWS_SDK_HOME%/bin
set PATH=%PATH%;%CYGWIN_HOME%/bin
set PATH=%PATH%;%ANT_HOME%/lib
set PATH=%PATH%;%FINDBUGS_HOME%/lib
set PATH=%PATH%;c:/WINDOWS/system32
set PATH=%PATH%;c:/WINDOWS/System32/Wbem

set INCLUDE=%WINDOWS_SDK_HOME%/include
set INCLUDE=%INCLUDE%;%WINDOWS_SDK_HOME%/include/mfc
set INCLUDE=%INCLUDE%;%VISUAL_STUDIO_HOME%/VC/INCLUDE

set LIB=%WINDOWS_SDK_HOME%/lib
set LIB=%LIB%;%VISUAL_STUDIO_HOME%/VC/LIB;%LIB%

set LIBPATH=C:\WINDOWS\Microsoft.NET\Framework\v3.5
set LIBPATH=%LIBPATH%;C:\WINDOWS\Microsoft.NET\Framework\v2.0.50727
set LIBPATH=%LIBPATH%;%VISUAL_STUDIO_HOME%/VC/LIB

rem set DevEnvDir=%VISUAL_STUDIO_HOME%/Common7/IDE

start bash
