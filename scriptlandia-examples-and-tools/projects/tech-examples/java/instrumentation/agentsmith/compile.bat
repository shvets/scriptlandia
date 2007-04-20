mkdir target\classes

SET JAVA_HOME=c:\Java\jdk1.6.0

%JAVA_HOME%\bin\javac -classpath . -d target\classes src\main\java\Bar.java src\main\java\Main.java