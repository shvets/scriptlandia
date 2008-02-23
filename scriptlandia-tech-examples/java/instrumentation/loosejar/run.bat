SET JAVA_HOME=c:\Java\jdk1.6.0

SET REPOSITORY_HOME=c:\maven-repository
SET LOOSEJAR_LIBRARY=%REPOSITORY_HOME%\com\googlecode\loosejar\1.0\loosejar-1.0.jar
SET CURRENT_DIR=%~d0%~p0

SET CLASSPATH=%REPOSITORY_HOME%/org/sf/cafebabe/cafebabe-main/1.4/cafebabe-main-1.4.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%/org/sf/serfile/serfile/1.1/serfile-1.1.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%/org/sf/mdi/mdi/1.1/mdi-1.1.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%/org/sf/netlib/netlib/1.1/netlib-1.1.jar

%JAVA_HOME%\bin\java -cp %CLASSPATH% -javaagent:"%LOOSEJAR_LIBRARY%" org.sf.cafebabe.Main
                              