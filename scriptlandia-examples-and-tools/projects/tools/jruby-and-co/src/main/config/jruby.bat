@echo off

SET JAVA_HOME=@java.home@
SET SCRIPTLANDIA_VERSION=@scriptlandia.version@
SET REPOSITORY_HOME=@repository.home@
SET JRUBY_VERSION=0.9.9

SET JAVA_BINARY="%JAVA_HOME%\bin\java" -server

SET DEPS_FILE=@repository.home@\org\sf\scriptlandia\jruby\%SCRIPTLANDIA_VERSION%\jruby-%SCRIPTLANDIA_VERSION%.pom

if NOT "@proxy.server.host@" == "" set HTTP_PROXY=http://@proxy.server.host@:@proxy.server.port@

SET SYSTEM_PARAMETERS=-Djruby.home=@repository.home@\jruby\jruby\%JRUBY_VERSION% -Djruby.lib=@repository.home@\jruby\jruby-lib\%JRUBY_VERSION%

SET MAIN_CLASS_NAME=org.jruby.Main

@scriptlandia.home@\launcher.bat -Xmx256m -Xss1024k -da "-deps.file.name=%DEPS_FILE%" -main.class.name=%MAIN_CLASS_NAME% %*
