@echo off

SET SCRIPTLANDIA_VERSION=@scriptlandia.version@
SET REPOSITORY_HOME=@repository.home@
SET SCRIPTLANDIA_HOME=@scriptlandia.home@

SET DEPS_FILE=%REPOSITORY_HOME%\org\sf\scriptlandia\ant-starter\%SCRIPTLANDIA_VERSION%\ant-starter-%SCRIPTLANDIA_VERSION%.pom
SET MAIN_CLASS_NAME=org.sf.scriptlandia.AntStarter

%SCRIPTLANDIA_HOME%\launcher.bat "-deps.file.name=%DEPS_FILE%" -main.class.name=%MAIN_CLASS_NAME% %*
