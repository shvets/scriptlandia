SET JAVA_HOME=c:\Java\jdk1.6.0

SET REPOSITORY_HOME=c:\maven-repository

SET SYSTEM_PROPS=-Dpomstrap.maven2.repository=file:/%REPOSITORY_HOME%
SET SYSTEM_PROPS=%SYSTEM_PROPS% -Dpomstrap.autogroup.dependencies=false

SET CLASSPATH=%REPOSITORY_HOME%\com\prefetch\pomstrap\1.0.7\pomstrap-1.0.7.jar

SET BOOTSTRAP_CLASS=com.prefetch.pomstrap.Bootstrap

SET GROUP_ID=org.gjt.jclasslib
SET ARTIFACT_ID=browser
SET VERSION=3.0
SET MAIN_CLASS=org.gjt.jclasslib.browser.BrowserApplication

SET PARAMETERS=%GROUP_ID%:%ARTIFACT_ID%:%VERSION% %MAIN_CLASS%

%JAVA_HOME%\bin\java %SYSTEM_PROPS% -cp %CLASSPATH% %BOOTSTRAP_CLASS% %PARAMETERS%




