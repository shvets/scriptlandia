@echo off

SET JAVA_HOME=C:\Java\jdk1.5.0
SET GRAILS_HOME=C:\JavaPrograms\grails-0.4.2
SET SCRIPTLANDIA_VERSION=2.2.1
SET REPOSITORY_HOME=C:\maven-repository

rem SET DEPS_FILE=%REPOSITORY_HOME%\org\sf\scriptlandia\ant-starter\%SCRIPTLANDIA_VERSION%\ant-starter-%SCRIPTLANDIA_VERSION%.pom
SET DEPS_FILE=c:\scriptlandia\grails-starter.pom
SET MAIN_CLASS_NAME=org.sf.scriptlandia.GrailsStarter
rem org.codehaus.groovy.grails.cli.GrailsScriptRunner

rem "%JAVA_HOME%\bin\java.exe" "-Xmx128m" -Dgrails.home=%GRAILS_HOME% -Dgroovy.starter.conf="%GRAILS_HOME%\conf\groovy-starter.conf" -classpath "%GRAILS_HOME%\bin\..\lib\groovy-starter.jar" org.codehaus.groovy.tools.GroovyStarter --main org.codehaus.groovy.grails.cli.GrailsScriptRunner --conf "%GRAILS_HOME%\conf\groovy-starter.conf" --classpath "%GRAILS_HOME%\bin\..\lib\groovy-starter.jar;."  %*

C:\scriptlandia\launcher.bat "-deps.file.name=%DEPS_FILE%" -main.class.name=%MAIN_CLASS_NAME% %*

rem -Dprogram.name=""  -Dtools.jar="%JAVA_HOME%\lib\tools.jar"
