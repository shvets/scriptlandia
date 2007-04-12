SET JAVA_HOME=c:\Java\jdk1.6.0

SET REPOSITORY_HOME=c:\maven-repository
SET SMITH_LIBRARY=%REPOSITORY_HOME%\agentsmith\smith\0.9.2\smith-0.9.2-jdk1.6.jar
SET CURRENT_DIR=%~d0%~p0

%JAVA_HOME%\bin\java -cp target\classes -javaagent:"%SMITH_LIBRARY%"="%CURRENT_DIR%target\classes" Main
