@ECHO OFF

@call ..\..\..\jlaunchpad\trunk\jlaunchpad\config.bat

if not exist %JLAUNCHPAD_HOME%\jlaunchpad.bat (
  echo Please run jlaunchpad-installer.bat first.

  pause
  exit
)

echo ---### Java Specification Version Level: %JAVA_SPECIFICATION_VERSION_LEVEL%
echo ---### Installing basic dependencies, required projects and configuration files...

SET PROPERTIES="-deps.file.name=%REPOSITORY_HOME%/org/sf/jlaunchpad/jlaunchpad-launcher/1.0.1/jlaunchpad-launcher-1.0.1.pom" 
SET PROPERTIES=%PROPERTIES% "-main.class.name=org.sf.pomreader.ProjectInstaller"

@call %JLAUNCHPAD_HOME%\jlaunchpad.bat %SYSTEM_PROPERTIES% %PROPERTIES% "-Dbasedir=projects/antrun" "-Dbuild.required=true"

@call %JLAUNCHPAD_HOME%\jlaunchpad.bat -Xbootclasspath/a:%JAVA_HOME%/jre/lib/deploy.jar %SYSTEM_PROPERTIES% %PROPERTIES% "-Dbasedir=projects/scriptlandia-installer" "-Dbuild.required=true"

@call %JLAUNCHPAD_HOME%\jlaunchpad.bat %SYSTEM_PROPERTIES% %PROPERTIES% "-Dbasedir=projects/scriptlandia-nailgun" "-Dbuild.required=true"

@call %JLAUNCHPAD_HOME%\jlaunchpad.bat %SYSTEM_PROPERTIES% %PROPERTIES% "-Dbasedir=projects/scriptlandia-launcher" "-Dbuild.required=true"

@call %JLAUNCHPAD_HOME%\jlaunchpad.bat %SYSTEM_PROPERTIES% %PROPERTIES% "-Dbasedir=projects/scriptlandia-helper" "-Dbuild.required=true"
