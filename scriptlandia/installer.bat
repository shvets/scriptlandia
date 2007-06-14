@ECHO OFF

if exist "%USERPROFILE%\scriptlandia\config.bat" @call "%USERPROFILE%\scriptlandia\config.bat"

@call config.bat

SET BOOTSTRAP_MINI_PROJECT=projects\bootstrap-mini
SET UNIVERSAL_LAUNCHER_COMMON_PROJECT=projects\universal-launcher-common
SET POM_READER_PROJECT=projects\pomreader
SET SCRIPTLANDIA_INSTALLER_PROJECT=projects\scriptlandia-installer

SET CLASSPATH=%BOOTSTRAP_MINI_PROJECT%\target\bootstrap-mini.jar
SET CLASSPATH=%CLASSPATH%;%POM_READER_PROJECT%\target\pomreader.jar
SET CLASSPATH=%CLASSPATH%;%UNIVERSAL_LAUNCHER_COMMON_PROJECT%\target\universal-launcher-common.jar
SET CLASSPATH=%CLASSPATH%;%SCRIPTLANDIA_INSTALLER_PROJECT%\target\scriptlandia-installer.jar

rem SET MAIN_CLASS=org.sf.scriptlandia.install.CoreInstaller
SET MAIN_CLASS=org.sf.scriptlandia.install.GuiInstaller

%JAVA_HOME%\bin\java -classpath %CLASSPATH% %SYSTEM_PROPERTIES% %MAIN_CLASS%
