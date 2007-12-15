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

SET SYSTEM_PROPERTIES1=%SYSTEM_PROPERTIES% "-deps.file.name=%REPOSITORY_HOME%/org/sf/jlaunchpad/jlaunchpad-launcher/%LAUNCHER_VERSION%/jlaunchpad-launcher-%LAUNCHER_VERSION%.pom" 
SET SYSTEM_PROPERTIES1=%SYSTEM_PROPERTIES1% "-main.class.name=org.sf.pomreader.ProjectInstaller"

@call %LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES1% "-Dbasedir=projects/scriptlandia-installer" "-Dbuild.required=false"

rem SET MAIN_CLASS=org.sf.scriptlandia.install.CoreInstaller
SET MAIN_CLASS=org.sf.scriptlandia.install.GuiInstaller

rem %LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% ^
rem   "-classpath.file.name=scriptlandia.classpath" "-main.class.name=%MAIN_CLASS%" -wait

rem "-classpath.file.name=scriptlandia.classpath" 

@call %LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% ^
  "-deps.file.name=projects\scriptlandia-installer\pom.xml" "-main.class.name=%MAIN_CLASS%" -wait
