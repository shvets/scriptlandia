SET LAUNCHER_HOME=c:\launcher

SET MAIN_CLASS=org.jboss.Main

SET PROPERTIES="-classpath.file.name=%CD%\jboss-deps.classpath" "-main.class.name=%MAIN_CLASS%"

start %LAUNCHER_HOME%\launcher.bat %PROPERTIES% -wait %*

