@ECHO OFF

@call ..\config.bat

if not exist %JLAUNCHPAD_HOME%\jlaunchpad.bat (
  echo Please run jlaunchpad-installer.bat first.

  pause
  exit
)


echo ---### Java Specification Version: %JAVA_SPECIFICATION_VERSION_LEVEL%
echo ---### Installing basic dependencies, required projects and configuration files...

SET MAIN_CLASS=org.apache.tools.ant.Main

SET PROPERTIES="-deps.file.name=../projects/scriptlandia-installer/pom.xml" "-main.class.name=%MAIN_CLASS%"

%JLAUNCHPAD_HOME%\jlaunchpad.bat %SYSTEM_PROPERTIES% %PROPERTIES% -f build.xml build
