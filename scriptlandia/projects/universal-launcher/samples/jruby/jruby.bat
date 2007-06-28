SET LAUNCHER_HOME=c:\scriptlandia\launcher

SET MAIN_CLASS=org.jruby.Main

SET PROPERTIES="-deps.file.name=%CD%\deps.xml" "-main.class.name=%MAIN_CLASS%"

%LAUNCHER_HOME%\launcher.bat %PROPERTIES% %*
