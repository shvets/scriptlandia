@echo off

SET REPOSITORY_HOME=@repository.home@
SET SCRIPTLANDIA_HOME=@scriptlandia.home@

SET DEPS_FILE=%REPOSITORY_HOME%\org\sf\scriptlandia\maven-starter\1.0\maven-starter-1.0.pom
SET MAIN_CLASS_NAME=org.sf.scriptlandia.MavenStarter

%SCRIPTLANDIA_HOME%\scriptlandia.bat "-deps.file.name=%DEPS_FILE%" "-main.class.name=%MAIN_CLASS_NAME%" %*
