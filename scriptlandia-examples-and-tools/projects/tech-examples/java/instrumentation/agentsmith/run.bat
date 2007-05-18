SET JAVA_HOME=c:\Java\jdk1.6.0

SET REPOSITORY_HOME=c:\maven-repository
SET SMITH_LIBRARY=%REPOSITORY_HOME%\agentsmith\smith\1.0\smith-1.0-jdk1.6.jar
SET CURRENT_DIR=%~d0%~p0

start %JAVA_HOME%\bin\java -cp target\classes -javaagent:"%SMITH_LIBRARY%"="%CURRENT_DIR%target\classes" Main
