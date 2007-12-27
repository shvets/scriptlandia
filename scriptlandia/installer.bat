@ECHO OFF

@call config.bat

if not exist %LAUNCHER_HOME%\launcher.bat (
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
)

if not exist %REPOSITORY_HOME%\org\sf\jlaunchpad\jlaunchpad-launcher\%LAUNCHER_VERSION%\jlaunchpad-launcher-%LAUNCHER_VERSION%.jar (
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
)

if not exist %REPOSITORY_HOME%\org\sf\jlaunchpad\jlaunchpad-common\%LAUNCHER_VERSION%\jlaunchpad-common-%LAUNCHER_VERSION%.jar (
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
)

if not exist %REPOSITORY_HOME%\org\sf\jlaunchpad\pom-reader\%LAUNCHER_VERSION%\pom-reader-%LAUNCHER_VERSION%.jar (
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
)

if not exist %REPOSITORY_HOME%\org\apache\maven\bootstrap\bootstrap-mini\2.0.8\bootstrap-mini-2.0.8.jar (
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
)


SET PROPERTIES1="-deps.file.name=%REPOSITORY_HOME%/org/sf/jlaunchpad/jlaunchpad-launcher/%LAUNCHER_VERSION%/jlaunchpad-launcher-%LAUNCHER_VERSION%.pom" 
SET PROPERTIES1=%PROPERTIES1% "-main.class.name=org.sf.pomreader.ProjectInstaller"

rem Install antrun project

@call %LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% %PROPERTIES1% "-Dbasedir=projects/antrun" "-Dbuild.required=false"

rem Install scriptlandia-installer project

@call %LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% %PROPERTIES1% "-Dbasedir=projects/scriptlandia-installer" "-Dbuild.required=false"

rem Execute scriptlandia-installer project

rem SET MAIN_CLASS=org.sf.scriptlandia.install.CoreInstaller
SET MAIN_CLASS=org.sf.scriptlandia.install.GuiInstaller

SET PROPERTIES2="-deps.file.name=projects/scriptlandia-installer/pom.xml"
SET PROPERTIES2=%PROPERTIES2% "-main.class.name=%MAIN_CLASS%"

@call %LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% %PROPERTIES2% -wait
