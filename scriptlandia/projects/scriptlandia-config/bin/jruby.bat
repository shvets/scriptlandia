@echo off

SET JAVA_HOME=@java.home@
SET SCRIPTLANDIA_VERSION=@scriptlandia.version@
SET REPOSITORY_HOME=@repository.home@

SET JAVA_BINARY="%JAVA_HOME%\bin\java" -server

SET DEPS_FILE=@repository.home@\org\sf\scriptlandia\jruby\%SCRIPTLANDIA_VERSION%\jruby-%SCRIPTLANDIA_VERSION%.pom

if NOT "@proxy.server.host@" == "" set HTTP_PROXY=http://@proxy.server.host@:@proxy.server.port@

SET SYSTEM_PARAMETERS=-Djruby.home=@repository.home@\jruby\jruby\0.9.8 -Djruby.lib=@repository.home@\jruby\jruby-lib\0.9.8

@scriptlandia.home@\launcher-core.bat "-deps.file.name=%DEPS_FILE%" -main.class.name=org.jruby.Main  %*

