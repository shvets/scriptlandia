@echo off

SET REPOSITORY_HOME=@repository.home@
SET SCRIPTLANDIA_HOME=@scriptlandia.home@

SET DEPS_FILE=%REPOSITORY_HOME%\org\sf\scriptlandia\groovy-starter\1.0.0\groovy-starter-1.0.0.pom
SET MAIN_CLASS_NAME=groovy.ui.GroovyMain

set APP=%~nx0
set APP_NAME=%APP:~0,-4%

%SCRIPTLANDIA_HOME%\scriptlandia.bat "-deps.file.name=%DEPS_FILE%" "-deps.file.name=%SCRIPTLANDIA_HOME%\ws-stack.xml" "-main.class.name=%MAIN_CLASS_NAME%" %*

