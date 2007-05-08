SET JAVA_HOME=c:\Java\jdk1.6.0

SET REPOSITORY_HOME=c:\maven-repository

%JAVA_HOME%\bin\java -Dpomstrap.maven2.repository=file://%REPOSITORY_HOME% -Dpomstrap.autogroup.dependencies=true -jar %REPOSITORY_HOME%\com\prefetch\pomstrap\1.0.7\pomstrap-1.0.7.jar pomstrap:testApp:1.0 com.prefetch.pomstrap.App:run
