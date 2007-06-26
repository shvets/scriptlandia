SET LAUNCHER_HOME=..
SET MAIN_CLASS=org.jboss.Main

SET SYSTEM_PROPERTIES="-deps.file.name=%CD%\jboss-deps.classpath" "-main.class.name=%MAIN_CLASS%"

start %LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% -gui %*

