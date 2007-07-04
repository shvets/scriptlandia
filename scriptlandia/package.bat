@ECHO OFF

if exist "%USERPROFILE%\scriptlandia\config.bat" @call "%USERPROFILE%\scriptlandia\config.bat"

@call config.bat

SET UNIVERSAL_LAUNCHER_COMMON_PROJECT=projects\universal-launcher-common
SET BOOTSTRAP_MINI_PROJECT=projects\bootstrap-mini
SET POM_READER_PROJECT=projects\pom-reader
SET SCRIPTLANDIA_INSTALLER_PROJECT=projects\scriptlandia-installer

echo ---### Java Specification Version: %JAVA_SPECIFICATION_VERSION%

echo ---### Builds universal-launcher-common project

if not exist %UNIVERSAL_LAUNCHER_COMMON_PROJECT%\target\classes mkdir %UNIVERSAL_LAUNCHER_COMMON_PROJECT%\target\classes

SET SL_COMMON_CLASSPATH=%UNIVERSAL_LAUNCHER_COMMON_PROJECT%\src\main\java

%JAVA_HOME%\bin\javac -nowarn -source %JAVA_SPECIFICATION_VERSION% -target %JAVA_SPECIFICATION_VERSION% ^
  -classpath %SL_COMMON_CLASSPATH% ^
  -d %UNIVERSAL_LAUNCHER_COMMON_PROJECT%\target\classes ^
  %UNIVERSAL_LAUNCHER_COMMON_PROJECT%\src\main\java\org\sf\scriptlandia\util\*.java ^
  %UNIVERSAL_LAUNCHER_COMMON_PROJECT%\src\main\java\org\sf\scriptlandia\launcher\*.java

%JAVA_HOME%\bin\jar cf %UNIVERSAL_LAUNCHER_COMMON_PROJECT%\target\universal-launcher-common.jar ^
  -C %UNIVERSAL_LAUNCHER_COMMON_PROJECT%\target\classes .

echo ---### Builds bootstrap-mini project

if not exist %BOOTSTRAP_MINI_PROJECT%\target\classes mkdir %BOOTSTRAP_MINI_PROJECT%\target\classes

SET BM_CLASSPATH=%UNIVERSAL_LAUNCHER_COMMON_PROJECT%\target\classes
SET BM_CLASSPATH=%BM_CLASSPATH%;%BOOTSTRAP_MINI_PROJECT%\src\main\java

%JAVA_HOME%\bin\javac -nowarn -source %JAVA_SPECIFICATION_VERSION% -target %JAVA_SPECIFICATION_VERSION% ^
  -classpath %BM_CLASSPATH% ^
  -d %BOOTSTRAP_MINI_PROJECT%\target\classes ^
  %BOOTSTRAP_MINI_PROJECT%\src\main\java\org\apache\maven\bootstrap\Bootstrap.java

%JAVA_HOME%\bin\jar cf %BOOTSTRAP_MINI_PROJECT%\target\bootstrap-mini.jar ^
  -C %BOOTSTRAP_MINI_PROJECT%\target\classes .

echo ---### Builds pom-reader project

if not exist %POM_READER_PROJECT%\target\classes mkdir %POM_READER_PROJECT%\target\classes

SET PR_CLASSPATH=%BOOTSTRAP_MINI_PROJECT%\target\classes
SET PR_CLASSPATH=%PR_CLASSPATH%;%UNIVERSAL_LAUNCHER_COMMON_PROJECT%\target\classes
SET PR_CLASSPATH=%PR_CLASSPATH%;%POM_READER_PROJECT%\src\main\java

%JAVA_HOME%\bin\javac -nowarn -source %JAVA_SPECIFICATION_VERSION% -target %JAVA_SPECIFICATION_VERSION% ^
  -classpath %PR_CLASSPATH% ^
  -d %POM_READER_PROJECT%\target\classes ^
  %POM_READER_PROJECT%\src\main\java\org\sf\scriptlandia\pomreader\PomReader.java ^
  %POM_READER_PROJECT%\src\main\java\org\sf\scriptlandia\pomreader\ProjectInstaller.java

%JAVA_HOME%\bin\jar cf %POM_READER_PROJECT%\target\pom-reader.jar ^
  -C %POM_READER_PROJECT%\target\classes .

rem echo ---### Builds installer project

rem if not exist %SCRIPTLANDIA_INSTALLER_PROJECT%\target\classes mkdir %SCRIPTLANDIA_INSTALLER_PROJECT%\target\classes

rem SET INST_CLASSPATH=%BOOTSTRAP_MINI_PROJECT%\target\classes
rem SET INST_CLASSPATH=%INST_CLASSPATH%;%POM_READER_PROJECT%\target\classes
rem SET INST_CLASSPATH=%INST_CLASSPATH%;%UNIVERSAL_LAUNCHER_COMMON_PROJECT%\target\classes
rem SET INST_CLASSPATH=%INST_CLASSPATH%;%SCRIPTLANDIA_INSTALLER_PROJECT%\src\main\java

rem %JAVA_HOME%\bin\javac -nowarn -source %JAVA_SPECIFICATION_VERSION% -target %JAVA_SPECIFICATION_VERSION% ^
rem   -classpath %INST_CLASSPATH% ^
rem   -d %SCRIPTLANDIA_INSTALLER_PROJECT%\target\classes ^
rem   %SCRIPTLANDIA_INSTALLER_PROJECT%\src\main\java\org\sf\scriptlandia\install\*.java

rem %JAVA_HOME%\bin\jar cf %SCRIPTLANDIA_INSTALLER_PROJECT%\target\scriptlandia-installer.jar ^
rem   -C %SCRIPTLANDIA_INSTALLER_PROJECT%\target\classes .

echo ---### Installing basic dependencies...

SET BASIC_CLASSPATH=%UNIVERSAL_LAUNCHER_COMMON_PROJECT%\target\universal-launcher-common.jar
SET BASIC_CLASSPATH=%BASIC_CLASSPATH%;%BOOTSTRAP_MINI_PROJECT%\target\bootstrap-mini.jar
SET BASIC_CLASSPATH=%BASIC_CLASSPATH%;%POM_READER_PROJECT%\target\pom-reader.jar
rem SET BASIC_CLASSPATH=%BASIC_CLASSPATH%;%SCRIPTLANDIA_INSTALLER_PROJECT%\target\scriptlandia-installer.jar

%JAVA_HOME%\bin\java ^
  -Dmaven.repo.local=%REPOSITORY_HOME% %PROXY_PARAMS% -Dbasedir=projects\scriptlandia-startup ^
  -classpath %BASIC_CLASSPATH% ^
  %SYSTEM_PROPERTIES% ^
  org.sf.scriptlandia.pomreader.ProjectInstaller

echo ---### Installing required projects and configuration files...

SET CLASSPATH=%REPOSITORY_HOME%\org\apache\ant\ant-launcher\%ANT_VERSION%\ant-launcher-%ANT_VERSION%.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\apache\ant\ant\%ANT_VERSION%\ant-%ANT_VERSION%.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\apache\ant\ant-apache-bsf\%ANT_VERSION%\ant-apache-bsf-%ANT_VERSION%.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\apache\ant\ant-nodeps\%ANT_VERSION%\ant-nodeps-%ANT_VERSION%.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\commons-logging\commons-logging\1.0.4\commons-logging-1.0.4.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\bsf\bsf\2.4.0\bsf-2.4.0.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\bsh\bsh\%BEANSHELL_VERSION%\bsh-%BEANSHELL_VERSION%.jar

%JAVA_HOME%\bin\java ^
  -classpath %CLASSPATH% ^
  %SYSTEM_PROPERTIES% ^
  org.apache.tools.ant.launch.Launcher -f package.ant package.projects
