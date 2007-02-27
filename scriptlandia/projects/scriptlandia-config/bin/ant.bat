@echo off

SET JAVA_HOME=@java.home@
SET SCRIPTLANDIA_VERSION=@scriptlandia.version@
SET REPOSITORY_HOME=@repository.home@

SET JAVA_BINARY="%JAVA_HOME%\bin\java"
rem -server

SET DEPS_FILE=%REPOSITORY_HOME%\org\sf\scriptlandia\ant-starter\%SCRIPTLANDIA_VERSION%\ant-starter-%SCRIPTLANDIA_VERSION%.pom
SET MAIN_CLASS_NAME=org.sf.scriptlandia.AntStarter

@scriptlandia.home@\launcher-core.bat "-deps.file.name=%DEPS_FILE%" -main.class.name=%MAIN_CLASS_NAME% %*
