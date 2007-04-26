@echo off

SET JAVA_HOME=C:\Java\jdk1.5.0
SET GRAILS_HOME=C:\JavaPrograms\grails-0.4.2
SET SCRIPTLANDIA_VERSION=2.2.1
SET REPOSITORY_HOME=C:\maven-repository

"%JAVA_HOME%\bin\java.exe" "-Xmx128m" -Dgrails.home=%GRAILS_HOME% -Dgroovy.starter.conf="%GRAILS_HOME%\conf\groovy-starter.conf" -classpath "%GRAILS_HOME%\bin\..\lib\groovy-starter.jar" org.codehaus.groovy.tools.GroovyStarter --main org.codehaus.groovy.grails.cli.GrailsScriptRunner --conf "%GRAILS_HOME%\conf\groovy-starter.conf" --classpath "%GRAILS_HOME%\bin\..\lib\groovy-starter.jar;." %*
