@ECHO OFF

@call ..\config.bat

if not exist %LAUNCHER_HOME%\launcher.bat (
  echo Please run jlaunchpad-installer.bat first.

  pause
  exit
)


echo ---### Java Specification Version: %JAVA_SPECIFICATION_VERSION%
echo ---### Installing basic dependencies, required projects and configuration files...

SET MAIN_CLASS=org.apache.tools.ant.Main

SET PROPERTIES="-deps.file.name=..\projects\scriptlandia-startup\pom.xml" "-main.class.name=%MAIN_CLASS%"

%LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% %PROPERTIES% -f package.ant package
