@ECHO OFF

if exist "%USERPROFILE%\jlaunchpad\config.bat" @call "%USERPROFILE%\jlaunchpad\config.bat"

@call config.bat

SET SCRIPTLANDIA_INSTALLER_PROJECT=projects\scriptlandia-installer

rem SET CLASSPATH=%REPOSITORY_HOME%\org\sf\jlaunchpad\universal-launcher-common\%LAUNCHER_VERSION%\universal-launcher-common-%LAUNCHER_VERSION%.jar
rem SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\apache\maven\bootstrap\bootstrap-mini\2.0.7\bootstrap-mini-2.0.7.jar
rem SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\sf\jlaunchpad\pom-reader\%LAUNCHER_VERSION%\pom-reader-%LAUNCHER_VERSION%.jar
rem SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\sf\jlaunchpad\universal-launcher\%LAUNCHER_VERSION%\universal-launcher-%LAUNCHER_VERSION%.jar

rem SET CLASSPATH=%CLASSPATH%;%SCRIPTLANDIA_INSTALLER_PROJECT%\target\scriptlandia-installer.jar

rem SET MAIN_CLASS=org.sf.scriptlandia.install.CoreInstaller
SET MAIN_CLASS=org.sf.scriptlandia.install.GuiInstaller

rem %JAVA_HOME%\bin\java -classpath %CLASSPATH% %SYSTEM_PROPERTIES% %MAIN_CLASS%


SET PROPERTIES="-deps.file.name=projects\scriptlandia-installer\pom.xml" "-main.class.name=%MAIN_CLASS%"

%LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% %PROPERTIES% -wait
