@ECHO OFF

@call config.bat

if not exist %LAUNCHER_HOME%\launcher.bat (
  echo Please run jlaunchpad-installer.bat first.

  pause
  exit
)

echo ---### Java Specification Version: %JAVA_SPECIFICATION_VERSION%
echo ---### Installing basic dependencies, required projects and configuration files...

SET PROPERTIES=%PROPERTIES% "-deps.file.name=%REPOSITORY_HOME%/org/sf/jlaunchpad/jlaunchpad-launcher/1.0.1/jlaunchpad-launcher-1.0.1.pom" 
SET PROPERTIES=%PROPERTIES% "-main.class.name=org.sf.pomreader.ProjectInstaller"

@call %LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% %PROPERTIES% "-Dbasedir=projects/antrun" "-Dbuild.required=true"

@call %LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% %PROPERTIES% "-Dbasedir=projects/scriptlandia-installer" "-Dbuild.required=true"

@call %LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% %PROPERTIES% "-Dbasedir=projects/scriptlandia-nailgun" "-Dbuild.required=true"

@call %LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% %PROPERTIES% "-Dbasedir=projects/scriptlandia-launcher" "-Dbuild.required=true"

@call %LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% %PROPERTIES% "-Dbasedir=projects/scriptlandia-helper" "-Dbuild.required=true"
