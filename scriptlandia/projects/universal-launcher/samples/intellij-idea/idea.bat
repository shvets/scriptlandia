SET LAUNCHER_HOME=c:\launcher

SET MAIN_CLASS=com.intellij.idea.Main

SET PROPERTIES="-classpath.file.name=%CD%\idea.classpath" "-main.class.name=%MAIN_CLASS%"

%LAUNCHER_HOME%\launcher.bat %PROPERTIES% -wait %*
