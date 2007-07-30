SET LAUNCHER_HOME=d:\launcher

SET MAIN_CLASS=org.jruby.Main

SET PROPERTIES="-deps.file.name=%CD%\deps.xml" "-main.class.name=%MAIN_CLASS%"

rem %LAUNCHER_HOME%\launcher.bat %PROPERTIES% %*

%LAUNCHER_HOME%\launcher.bat %PROPERTIES% test1.rb 
