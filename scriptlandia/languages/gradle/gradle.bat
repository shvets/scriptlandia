set JAVA_HOME=c:\Java\jdk1.5.0

SET GRADLE_HOME=C:\Work\Programs\gradle-0.4

SET CLASSPATH=%GRADLE_HOME%\lib\gradle-0.4.jar;%GRADLE_HOME%\lib\groovy-all-1.5.5.jar

%JAVA_HOME%\bin\java -cp %CLASSPATH% org.gradle.BootstrapMain %*