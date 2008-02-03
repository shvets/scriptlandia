@ECHO OFF

@call ..\..\..\jlaunchpad\trunk\jlaunchpad\config.bat

if not exist %JLAUNCHPAD_HOME%\jlaunchpad.bat (
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
)

if not exist %REPOSITORY_HOME%\org\sf\jlaunchpad\jlaunchpad-launcher\%JLAUNCHPAD_VERSION%\jlaunchpad-launcher-%JLAUNCHPAD_VERSION%.jar (
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
)

if not exist %REPOSITORY_HOME%\org\sf\jlaunchpad\jlaunchpad-common\%JLAUNCHPAD_VERSION%\jlaunchpad-common-%JLAUNCHPAD_VERSION%.jar (
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
)

if not exist %REPOSITORY_HOME%\org\sf\jlaunchpad\pom-reader\%JLAUNCHPAD_VERSION%\pom-reader-%JLAUNCHPAD_VERSION%.jar (
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
)

if not exist %REPOSITORY_HOME%\org\apache\maven\bootstrap\bootstrap-mini\2.0.8\bootstrap-mini-2.0.8.jar (
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
)

SET SCRIPTLANDIA_HOME=%DRIVE_LETTER%\scriptlandia
SET MOBILE_JAVA_HOME=%DRIVE_LETTER%\Java\j2me-2.5
SET RUBY_HOME=%DRIVE_LETTER%\Ruby\ruby-1.8.4-20

SET SCRIPTLANDIA_VERSION=2.2.4
SET ANT_VERSION=1.7.0
SET BEANSHELL_VERSION=2.0b5
SET NAILGUN_VERSION=0.7.1
SET JAVA_COMPILER_VERSION=7.0-b23

IF NOT EXIST %JAVA_HOME% (
  ECHO JDK cannot be found!
  pause
  exit
)

IF NOT EXIST %MOBILE_JAVA_HOME% (
  ECHO Java Micro Edition cannot be found!
)

SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dant.version.internal=%ANT_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dbeanshell.version=%BEANSHELL_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dscriptlandia.version=%SCRIPTLANDIA_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dnailgun.version=%NAILGUN_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Djava.compiler.version=%JAVA_COMPILER_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dscriptlandia.home=%SCRIPTLANDIA_HOME%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Djlaunchpad.home=%JLAUNCHPAD_HOME%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Drepository.home=%REPOSITORY_HOME%"

SET PROPERTIES1="-deps.file.name=%REPOSITORY_HOME%/org/sf/jlaunchpad/jlaunchpad-launcher/%JLAUNCHPAD_VERSION%/jlaunchpad-launcher-%JLAUNCHPAD_VERSION%.pom" 
SET PROPERTIES1=%PROPERTIES1% "-main.class.name=org.sf.pomreader.ProjectInstaller"

rem Install antrun project

@call %JLAUNCHPAD_HOME%\jlaunchpad.bat %SYSTEM_PROPERTIES% %PROPERTIES1% "-Dbasedir=projects/antrun" "-Dbuild.required=false"

rem Install scriptlandia-installer project

@call %JLAUNCHPAD_HOME%\jlaunchpad.bat %SYSTEM_PROPERTIES% %PROPERTIES1% "-Dbasedir=projects/scriptlandia-installer" "-Dbuild.required=false"

rem Execute scriptlandia-installer project

rem SET MAIN_CLASS=org.sf.scriptlandia.install.CoreInstaller
SET MAIN_CLASS=org.sf.scriptlandia.install.GuiInstaller

SET PROPERTIES2="-deps.file.name=projects/scriptlandia-installer/pom.xml"
SET PROPERTIES2=%PROPERTIES2% "-main.class.name=%MAIN_CLASS%"

@call %JLAUNCHPAD_HOME%\jlaunchpad.bat %SYSTEM_PROPERTIES% %PROPERTIES2% -wait
