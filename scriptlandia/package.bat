@ECHO OFF

if exist "%USERPROFILE%\jlaunchpad\config.bat" @call "%USERPROFILE%\jlaunchpad\config.bat"

@call config.bat

echo ---### Java Specification Version: %JAVA_SPECIFICATION_VERSION%

rem echo ---### Installing basic dependencies...

rem SET BASIC_CLASSPATH=%REPOSITORY_HOME%\org\sf\jlaunchpad\universal-launcher-common\%LAUNCHER_VERSION%\universal-launcher-common-%LAUNCHER_VERSION%.jar
rem SET BASIC_CLASSPATH=%BASIC_CLASSPATH%;%REPOSITORY_HOME%\org\apache\maven\bootstrap\bootstrap-mini\2.0.7\bootstrap-mini-2.0.7.jar
rem SET BASIC_CLASSPATH=%BASIC_CLASSPATH%;%REPOSITORY_HOME%\org\sf\jlaunchpad\pom-reader\%LAUNCHER_VERSION%\pom-reader-%LAUNCHER_VERSION%.jar

rem %JAVA_HOME%\bin\java ^
rem   -Drepository.home=%REPOSITORY_HOME% ^
rem   %PROXY_PARAMS% ^
rem   -Dbasedir=projects\scriptlandia-startup ^
rem   -classpath %BASIC_CLASSPATH% ^
rem   %SYSTEM_PROPERTIES% ^
rem   org.sf.pomreader.ProjectInstaller

rem echo ---### Installing required projects and configuration files...

rem SET CLASSPATH=%REPOSITORY_HOME%\org\apache\ant\ant-launcher\%ANT_VERSION%\ant-launcher-%ANT_VERSION%.jar
rem SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\apache\ant\ant\%ANT_VERSION%\ant-%ANT_VERSION%.jar
rem SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\apache\ant\ant-apache-bsf\%ANT_VERSION%\ant-apache-bsf-%ANT_VERSION%.jar
rem SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\apache\ant\ant-nodeps\%ANT_VERSION%\ant-nodeps-%ANT_VERSION%.jar
rem SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\commons-logging\commons-logging\1.0.4\commons-logging-1.0.4.jar
rem SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\bsf\bsf\2.4.0\bsf-2.4.0.jar
rem SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\bsh\bsh\%BEANSHELL_VERSION%\bsh-%BEANSHELL_VERSION%.jar

rem %JAVA_HOME%\bin\java ^
rem   -classpath %CLASSPATH% ^
rem   %SYSTEM_PROPERTIES% ^
rem   org.apache.tools.ant.launch.Launcher -f package.ant package.projects
rem 

rem SET MAIN_CLASS=org.apache.tools.ant.launch.Launcher
SET MAIN_CLASS=org.apache.tools.ant.Main

SET PROPERTIES="-deps.file.name=projects\scriptlandia-startup\pom.xml" "-main.class.name=%MAIN_CLASS%"

%LAUNCHER_HOME%\launcher.bat %PROPERTIES% -f package.ant package.projects 
