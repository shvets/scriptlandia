@echo off

SET JAVA_BINARY=@java.home@\bin\java -server

SET DEPS_FILE=@scriptlandia.home@\grails.sl

SET SYSTEM_PARAMETERS=-Dgrails.home=@repository.home@\groovy\grails-templates\0.2.1 -Dbasedir=%CD% 

SET GRAILS_HOME=@scriptlandia.home@

@scriptlandia.home@\launcher-core.bat -m %DEPS_FILE% -c org.sf.scriptlandia.AntStarter -f @scriptlandia.home@\grails-@grails.version@.ant %*
