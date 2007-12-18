@ECHO OFF

@call config.bat

SET JLAUNCHPAD_PROJECT=.
SET ORIGINAL_JLAUNCHPAD_PROJECT=..\..\..\jlaunchpad\trunk\launcher

if exist "%ORIGINAL_JLAUNCHPAD_PROJECT%\projects\jlaunchpad-launcher\target\jlaunchpad-launcher.jar" (
  cd %ORIGINAL_JLAUNCHPAD_PROJECT%
)

SET BOOTSTRAP_MINI_PROJECT=%JLAUNCHPAD_PROJECT%\projects\bootstrap-mini
SET JLAUNCHPAD_COMMON_PROJECT=%JLAUNCHPAD_PROJECT%\projects\jlaunchpad-common
SET POM_READER_PROJECT=%JLAUNCHPAD_PROJECT%\projects\pom-reader
SET JLAUNCHPAD_LAUNCHER_PROJECT=%JLAUNCHPAD_PROJECT%\projects\jlaunchpad-launcher
SET JDOM_PROJECT=%JLAUNCHPAD_PROJECT%\projects\jdom

SET CLASSPATH=%BOOTSTRAP_MINI_PROJECT%\target\bootstrap-mini.jar
SET CLASSPATH=%CLASSPATH%;%JLAUNCHPAD_COMMON_PROJECT%\target\jlaunchpad-common.jar
SET CLASSPATH=%CLASSPATH%;%POM_READER_PROJECT%\target\pom-reader.jar
SET CLASSPATH=%CLASSPATH%;%JLAUNCHPAD_LAUNCHER_PROJECT%\target\jlaunchpad-launcher.jar
SET CLASSPATH=%CLASSPATH%;%JDOM_PROJECT%\target\jdom.jar


REM SET MAIN_CLASS=org.sf.jlaunchpad.install.CoreInstaller
SET MAIN_CLASS=org.sf.jlaunchpad.install.GuiInstaller

%JAVA_HOME%\bin\java -classpath %CLASSPATH% %SYSTEM_PROPERTIES% -Dclassworlds.version=1.1 %MAIN_CLASS%
