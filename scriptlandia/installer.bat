@ECHO OFF

if exist "%USERPROFILE%\scriptlandia\config.bat" @call "%USERPROFILE%\scriptlandia\config.bat"

@call config.bat

SET BOOTSTRAP_MINI_PROJECT=projects\bootstrap-mini
SET SCRIPTLANDIA_COMMON_PROJECT=projects\scriptlandia-common
SET POM_READER_PROJECT=projects\pomreader
SET IMAGE4J_PROJECT=projects\image4j
SET SCRIPTLANDIA_INSTALLER_PROJECT=projects\scriptlandia-installer

SET CLASSPATH=%BOOTSTRAP_MINI_PROJECT%\target\bootstrap-mini.jar
SET CLASSPATH=%CLASSPATH%;%POM_READER_PROJECT%\target\pomreader.jar
SET CLASSPATH=%CLASSPATH%;%IMAGE4J_PROJECT%\target\image4j.jar
SET CLASSPATH=%CLASSPATH%;%SCRIPTLANDIA_COMMON_PROJECT%\target\scriptlandia-common.jar
SET CLASSPATH=%CLASSPATH%;%SCRIPTLANDIA_INSTALLER_PROJECT%\target\scriptlandia-installer.jar

rem SET MAIN_CLASS=org.sf.scriptlandia.install.CoreInstaller
SET MAIN_CLASS=org.sf.scriptlandia.install.GuiInstaller

%JAVA_HOME%\bin\java -classpath %CLASSPATH% %SYSTEM_PROPERTIES% %MAIN_CLASS%
