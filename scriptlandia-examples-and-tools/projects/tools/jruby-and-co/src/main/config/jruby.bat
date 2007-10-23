@echo off

SET JAVA_HOME=@java.home@
SET SCRIPTLANDIA_VERSION=@scriptlandia.version@
SET REPOSITORY_HOME=@repository.home@
SET JRUBY_VERSION=1.0.1

SET JAVA_BINARY="%JAVA_HOME%\bin\java" -server

SET DEPS_FILE=@repository.home@\org\sf\scriptlandia\jruby\1.0.0\jruby-1.0.0.pom

if NOT "@proxy.server.host@" == "" set HTTP_PROXY=http://@proxy.server.host@:@proxy.server.port@

SET SYSTEM_PARAMETERS="-Djruby.home=@repository.home@\jruby\jruby\%JRUBY_VERSION%" "-Djruby.lib=@repository.home@\jruby\jruby-lib\%JRUBY_VERSION%"

SET MAIN_CLASS_NAME=org.jruby.Main

@scriptlandia.home@\launcher.bat -Xmx256m -Xss1024k -da %SYSTEM_PARAMETERS% "-deps.file.name=%DEPS_FILE%" "-main.class.name=%MAIN_CLASS_NAME%" %*
