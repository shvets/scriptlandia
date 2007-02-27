@echo off

SET JAVA_HOME=@java.home@

SET JAVA_BINARY=%JAVA_HOME%\bin\java -server

SET DEPS_FILE=@repository.home@\org\sf\scriptlandia\jruby\2.2.0\jruby-2.2.0.pom

SET SYSTEM_PARAMETERS=-Djruby.home=@ruby.home@

@scriptlandia.home@\launcher-core.bat "-deps.file.name=%DEPS_FILE%" -main.class.name=org.jruby.Main  %*
