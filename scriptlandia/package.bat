@ECHO OFF

if exist user\config.bat @call user\config.bat

@call config.bat

SET SCRIPTLANDIA_COMMON_PROJECT=projects\scriptlandia-common
SET BOOTSTRAP_MINI_PROJECT=projects\bootstrap-mini
SET POM_READER_PROJECT=projects\pomreader
SET IMAGE4J_PROJECT=projects\image4j
SET SCRIPTLANDIA_INSTALLER_PROJECT=projects\scriptlandia-installer

echo ---### Java Specification Version: %JAVA_SPECIFICATION_VERSION%

echo ---### Builds Scriptlandia-Common project

if not exist %SCRIPTLANDIA_COMMON_PROJECT%\target\classes mkdir %SCRIPTLANDIA_COMMON_PROJECT%\target\classes

SET SL_COMMON_CLASSPATH=%SCRIPTLANDIA_COMMON_PROJECT%\src\main\java

%JAVA_HOME%\bin\javac -nowarn -source %JAVA_SPECIFICATION_VERSION% -target %JAVA_SPECIFICATION_VERSION% ^
  -classpath %SL_COMMON_CLASSPATH% ^
  -d %SCRIPTLANDIA_COMMON_PROJECT%\target\classes ^
  %SCRIPTLANDIA_COMMON_PROJECT%\src\main\java\org\sf\scriptlandia\util\*.java ^
  %SCRIPTLANDIA_COMMON_PROJECT%\src\main\java\org\sf\scriptlandia\launcher\*.java

%JAVA_HOME%\bin\jar cf %SCRIPTLANDIA_COMMON_PROJECT%\target\scriptlandia-common.jar ^
  -C %SCRIPTLANDIA_COMMON_PROJECT%\target\classes .

echo ---### Builds Bootstrap-Mini project

if not exist %BOOTSTRAP_MINI_PROJECT%\target\classes mkdir %BOOTSTRAP_MINI_PROJECT%\target\classes

SET BM_CLASSPATH=%SCRIPTLANDIA_COMMON_PROJECT%\target\classes
SET BM_CLASSPATH=%BM_CLASSPATH%;%BOOTSTRAP_MINI_PROJECT%\src\main\java

%JAVA_HOME%\bin\javac -nowarn -source %JAVA_SPECIFICATION_VERSION% -target %JAVA_SPECIFICATION_VERSION% ^
  -classpath %BM_CLASSPATH% ^
  -d %BOOTSTRAP_MINI_PROJECT%\target\classes ^
  %BOOTSTRAP_MINI_PROJECT%\src\main\java\org\apache\maven\bootstrap\Bootstrap.java

%JAVA_HOME%\bin\jar cf %BOOTSTRAP_MINI_PROJECT%\target\bootstrap-mini.jar ^
  -C %BOOTSTRAP_MINI_PROJECT%\target\classes .

echo ---### Builds PomReader project

if not exist %POM_READER_PROJECT%\target\classes mkdir %POM_READER_PROJECT%\target\classes

SET PR_CLASSPATH=%BOOTSTRAP_MINI_PROJECT%\target\classes
SET PR_CLASSPATH=%PR_CLASSPATH%;%SCRIPTLANDIA_COMMON_PROJECT%\target\classes
SET PR_CLASSPATH=%PR_CLASSPATH%;%POM_READER_PROJECT%\src\main\java

%JAVA_HOME%\bin\javac -nowarn -source %JAVA_SPECIFICATION_VERSION% -target %JAVA_SPECIFICATION_VERSION% ^
  -classpath %PR_CLASSPATH% ^
  -d %POM_READER_PROJECT%\target\classes ^
  %POM_READER_PROJECT%\src\main\java\org\sf\scriptlandia\pomreader\PomReader.java

%JAVA_HOME%\bin\jar cf %POM_READER_PROJECT%\target\pomreader.jar ^
  -C %POM_READER_PROJECT%\target\classes .

echo ---### Builds Image4j project

if not exist %IMAGE4J_PROJECT%\target\classes mkdir %IMAGE4J_PROJECT%\target\classes

SET IMAGE4J_CLASSPATH=%IMAGE4J_PROJECT%\target\classes

%JAVA_HOME%\bin\javac -nowarn -source %JAVA_SPECIFICATION_VERSION% -target %JAVA_SPECIFICATION_VERSION% ^
  -classpath %IMAGE4J_CLASSPATH% ^
  -d %IMAGE4J_PROJECT%\target\classes ^
  %IMAGE4J_PROJECT%\src\main\java\net\sf\image4j\codec\ico\*.java ^
  %IMAGE4J_PROJECT%\src\main\java\net\sf\image4j\codec\bmp\*.java ^
  %IMAGE4J_PROJECT%\src\main\java\net\sf\image4j\io\*.java ^
  %IMAGE4J_PROJECT%\src\main\java\net\sf\image4j\util\*.java

%JAVA_HOME%\bin\jar cf %IMAGE4J_PROJECT%\target\image4j.jar ^
  -C %IMAGE4J_PROJECT%\target\classes .

echo ---### Builds Installer project

if not exist %SCRIPTLANDIA_INSTALLER_PROJECT%\target\classes mkdir %SCRIPTLANDIA_INSTALLER_PROJECT%\target\classes

SET INST_CLASSPATH=%BOOTSTRAP_MINI_PROJECT%\target\classes
SET INST_CLASSPATH=%INST_CLASSPATH%;%POM_READER_PROJECT%\target\classes
SET INST_CLASSPATH=%INST_CLASSPATH%;%IMAGE4J_PROJECT%\target\classes
SET INST_CLASSPATH=%INST_CLASSPATH%;%SCRIPTLANDIA_COMMON_PROJECT%\target\classes
SET INST_CLASSPATH=%INST_CLASSPATH%;%SCRIPTLANDIA_INSTALLER_PROJECT\src\main\java

%JAVA_HOME%\bin\javac -nowarn -source %JAVA_SPECIFICATION_VERSION% -target %JAVA_SPECIFICATION_VERSION% ^
  -classpath %INST_CLASSPATH% ^
  -d %SCRIPTLANDIA_INSTALLER_PROJECT%\target\classes ^
  %SCRIPTLANDIA_INSTALLER_PROJECT%\src\main\java\org\sf\scriptlandia\install\*.java

%JAVA_HOME%\bin\jar cf %SCRIPTLANDIA_INSTALLER_PROJECT%\target\scriptlandia-installer.jar ^
  -C %SCRIPTLANDIA_INSTALLER_PROJECT%\target\classes .

echo ---### Installing basic dependencies...

SET BASIC_CLASSPATH=%SCRIPTLANDIA_COMMON_PROJECT%\target\scriptlandia-common.jar
SET BASIC_CLASSPATH=%BASIC_CLASSPATH%;%BOOTSTRAP_MINI_PROJECT%\target\bootstrap-mini.jar
SET BASIC_CLASSPATH=%BASIC_CLASSPATH%;%POM_READER_PROJECT%\target\pomreader.jar
SET BASIC_CLASSPATH=%BASIC_CLASSPATH%;%IMAGE4J_PROJECT%\target\image4j.jar
SET BASIC_CLASSPATH=%BASIC_CLASSPATH%;%SCRIPTLANDIA_INSTALLER_PROJECT%\target\scriptlandia-installer.jar

%JAVA_HOME%\bin\java ^
  -Dmaven.repo.local=%REPOSITORY_HOME% %PROXY_PARAMS% -Dbasedir=projects\scriptlandia-startup ^
  -classpath %BASIC_CLASSPATH% ^
  org.sf.scriptlandia.install.ProjectInstaller

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
  org.apache.tools.ant.launch.Launcher -f installer.ant package.projects
