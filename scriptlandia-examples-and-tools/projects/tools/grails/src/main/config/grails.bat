@echo off

SET JAVA_HOME=@java.home@

SET JAVA_BINARY=%JAVA_HOME%\bin\java -server

rem SET DEPS_FILE=@scriptlandia.home@\grails.sl

rem SET SYSTEM_PARAMETERS=-Dgrails.home=@repository.home@\groovy\grails-templates\${grails.version} -Dbasedir=%CD% 

rem SET GRAILS_HOME=@scriptlandia.home@

rem @scriptlandia.home@\launcher-core.bat -m %DEPS_FILE% -c org.sf.scriptlandia.AntStarter -f @scriptlandia.home@\grails-@grails.version@.ant %*

SET GRAILS_HOME=@grails.home@

@call %GRAILS_HOME%\bin\grails.bat %*