@ECHO OFF

@call config.bat

if not exist %LAUNCHER_HOME%\launcher.bat (
  echo Please run jlaunchpad-installer.bat first.

  pause
  exit
)

rem SET MAIN_CLASS=org.sf.scriptlandia.install.CoreInstaller
SET MAIN_CLASS=org.sf.scriptlandia.install.GuiInstaller

rem %LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% ^
rem   "-classpath.file.name=scriptlandia.classpath" "-main.class.name=%MAIN_CLASS%" -wait

rem "-classpath.file.name=scriptlandia.classpath" 

@call %LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% ^
  "-deps.file.name=projects\scriptlandia-installer\pom.xml" "-main.class.name=%MAIN_CLASS%" -wait
