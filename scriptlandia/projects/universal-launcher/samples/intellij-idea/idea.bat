SET LAUNCHER_HOME=..
SET MAIN_CLASS=com.intellij.idea.Main

SET SYSTEM_PROPERTIES="-deps.file.name=%CD%\idea-deps.classpath" "-main.class.name=%MAIN_CLASS%"

start %LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% -gui %*
