@ECHO OFF

@call config.bat

rem SET MAIN_CLASS=org.sf.scriptlandia.install.CoreInstaller
SET MAIN_CLASS=org.sf.scriptlandia.install.GuiInstaller

SET PROPERTIES="-classpath.file.name=scriptlandia.classpath" "-main.class.name=%MAIN_CLASS%"
%LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% %PROPERTIES% -wait
