@echo off

SET SCRIPTLANDIA_VERSION=@scriptlandia.version@
SET REPOSITORY_HOME=@repository.home@
SET SCRIPTLANDIA_HOME=@scriptlandia.home@

SET DEPS_FILE=%REPOSITORY_HOME%\org\sf\scriptlandia\maven-starter\%SCRIPTLANDIA_VERSION%\maven-starter-%SCRIPTLANDIA_VERSION%.pom
SET MAIN_CLASS_NAME=org.sf.scriptlandia.MavenStarter

%SCRIPTLANDIA_HOME%\scriptlandia.bat "-deps.file.name=%DEPS_FILE%" -main.class.name=%MAIN_CLASS_NAME% %*
