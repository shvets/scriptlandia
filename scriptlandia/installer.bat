@ECHO OFF

if exist "%USERPROFILE%\jlaunchpad\config.bat" @call "%USERPROFILE%\jlaunchpad\config.bat"

@call config.bat

rem SET MAIN_CLASS=org.sf.scriptlandia.install.CoreInstaller
SET MAIN_CLASS=org.sf.scriptlandia.install.GuiInstaller

SET CLASSPATH=%REPOSITORY_HOME%\org\sf\jlaunchpad\universal-launcher-common\%LAUNCHER_VERSION%\universal-launcher-common-%LAUNCHER_VERSION%.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\apache\maven\bootstrap\bootstrap-mini\2.0.7\bootstrap-mini-2.0.7.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\sf\jlaunchpad\pom-reader\%LAUNCHER_VERSION%\pom-reader-%LAUNCHER_VERSION%.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\sf\jlaunchpad\universal-launcher\%LAUNCHER_VERSION%\universal-launcher-%LAUNCHER_VERSION%.jar

SET CLASSPATH=%CLASSPATH%;projects\universal-launcher-common\target\universal-launcher-common.jar
SET CLASSPATH=%CLASSPATH%;projects\universal-launcher\target\universal-launcher.jar
SET CLASSPATH=%CLASSPATH%;projects\bootstrap-mini\target\bootstrap-mini.jar
SET CLASSPATH=%CLASSPATH%;projects\pom-reader\target\pom-reader.jar

SET CLASSPATH=%CLASSPATH%;projects\scriptlandia-installer\target\scriptlandia-installer.jar

%JAVA_HOME%\bin\java -classpath %CLASSPATH% %SYSTEM_PROPERTIES% %MAIN_CLASS% 

rem SET PROPERTIES="-deps.file.name=projects\scriptlandia-installer\pom.xml" "-main.class.name=%MAIN_CLASS%"
rem %LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% %PROPERTIES% -wait
