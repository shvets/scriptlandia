@ECHO OFF

if exist "%USERPROFILE%\scriptlandia\config.bat" @call "%USERPROFILE%\scriptlandia\config.bat"

@call config.bat

SET SCRIPTLANDIA_INSTALLER_PROJECT=projects\scriptlandia-installer

SET CLASSPATH=%REPOSITORY_HOME%\org\sf\jlaunchpad\universal-launcher-common\%LAUNCHER_VERSION%\universal-launcher-common-%LAUNCHER_VERSION%.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\apache\maven\bootstrap\bootstrap-mini\2.0.7\bootstrap-mini-2.0.7.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\sf\jlaunchpad\pom-reader\%LAUNCHER_VERSION%\pom-reader-%LAUNCHER_VERSION%.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\sf\jlaunchpad\universal-launcher\%LAUNCHER_VERSION%\universal-launcher-%LAUNCHER_VERSION%.jar

SET CLASSPATH=%CLASSPATH%;%SCRIPTLANDIA_INSTALLER_PROJECT%\target\scriptlandia-installer.jar

rem SET MAIN_CLASS=org.sf.scriptlandia.install.CoreInstaller
SET MAIN_CLASS=org.sf.scriptlandia.install.GuiInstaller

%JAVA_HOME%\bin\java -classpath %CLASSPATH% %SYSTEM_PROPERTIES% %MAIN_CLASS%
