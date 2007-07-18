@ECHO OFF

if exist "%USERPROFILE%\jlaunchpad\config.bat" @call "%USERPROFILE%\jlaunchpad\config.bat"

@call config.bat

SET BOOTSTRAP_MINI_PROJECT=projects\bootstrap-mini
SET UNIVERSAL_LAUNCHER_COMMON_PROJECT=projects\universal-launcher-common
SET POM_READER_PROJECT=projects\pom-reader
SET UNIVERSAL_LAUNCHER_PROJECT=projects\universal-launcher

SET CLASSPATH=%BOOTSTRAP_MINI_PROJECT%\target\bootstrap-mini.jar
SET CLASSPATH=%CLASSPATH%;%UNIVERSAL_LAUNCHER_COMMON_PROJECT%\target\universal-launcher-common.jar
SET CLASSPATH=%CLASSPATH%;%POM_READER_PROJECT%\target\pom-reader.jar
SET CLASSPATH=%CLASSPATH%;%UNIVERSAL_LAUNCHER_PROJECT%\target\universal-launcher.jar


REM SET MAIN_CLASS=org.sf.jlaunchpad.install.CoreInstaller
SET MAIN_CLASS=org.sf.jlaunchpad.install.GuiInstaller

%JAVA_HOME%\bin\java -classpath %CLASSPATH% %SYSTEM_PROPERTIES% %MAIN_CLASS%
