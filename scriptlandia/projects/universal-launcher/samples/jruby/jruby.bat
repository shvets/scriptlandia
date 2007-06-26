SET LAUNCHER_HOME=..

SET MAIN_CLASS=org.jruby.Main

SET SYSTEM_PROPERTIES="-deps.file.name=%CD%\deps.xml" "-main.class.name=%MAIN_CLASS%"

%LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% %*
