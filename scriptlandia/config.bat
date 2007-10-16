rem config.bat

if exist "..\..\..\jlaunchpad\trunk\launcher\user\config.bat" (
  @call "..\..\..\jlaunchpad\trunk\launcher\user\config.bat"
)

if exist "%USERPROFILE%\jlaunchpad\config.bat" (
  @call "%USERPROFILE%\jlaunchpad\config.bat"
)

if "%DRIVE_LETTER%" == "" (
  SET DRIVE_LETTER=c:
)

if "%JAVA_HOME%" == "" (
  SET JAVA_HOME=%DRIVE_LETTER%\Java\jdk1.5.0
)

if "%LAUNCHER_HOME%" == "" (
  SET LAUNCHER_HOME=%DRIVE_LETTER%\launcher
)

if "%REPOSITORY_HOME%" == "" (
  SET REPOSITORY_HOME=%DRIVE_LETTER%\maven-repository
)

SET SCRIPTLANDIA_HOME=%DRIVE_LETTER%\scriptlandia
SET MOBILE_JAVA_HOME=%DRIVE_LETTER%\Java\j2me-2.5
SET RUBY_HOME=%DRIVE_LETTER%\Ruby\ruby-1.8.4-20

SET JAVA_SPECIFICATION_VERSION=1.5
SET ANT_VERSION=1.7.0
SET BEANSHELL_VERSION=2.0b5
SET SCRIPTLANDIA_VERSION=2.2.4
SET LAUNCHER_VERSION=1.0.1
SET JDIC_VERSION=0.9.3
SET NAILGUN_VERSION=0.7.1
SET JAVA_COMPILER_VERSION=7.0-b18

IF NOT EXIST %JAVA_HOME% (
  ECHO JDK cannot be found!
  pause
  exit
)

IF NOT EXIST %MOBILE_JAVA_HOME% (
  ECHO Java Micro Edition cannot be found!
)

rem SET SYSTEM_PROPERTIES=-Dmobile.java.home="%MOBILE_JAVA_HOME%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Djava.library.path=%REPOSITORY_HOME%/org/jdesktop/jdic/%JDIC_VERSION%/windows/x86"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dant.version.internal=%ANT_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dbeanshell.version=%BEANSHELL_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dscriptlandia.version=%SCRIPTLANDIA_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dlauncher.version=%LAUNCHER_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Djdic.version=%JDIC_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dnailgun.version=%NAILGUN_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Djava.compiler.version=%JAVA_COMPILER_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Djava.specification.version=%JAVA_SPECIFICATION_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dscriptlandia.home=%SCRIPTLANDIA_HOME%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dlauncher.home=%LAUNCHER_HOME%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Drepository.home=%REPOSITORY_HOME%"


IF EXIST %RUBY_HOME% (
  SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dnative.ruby.home=%RUBY_HOME%"
)
