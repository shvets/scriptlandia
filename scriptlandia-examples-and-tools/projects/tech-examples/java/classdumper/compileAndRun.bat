set JAVAHOME=c:\Java\jdk1.6.0


rem compiling app
%JAVAHOME%\bin\javac Main.java

rem  running app
start %JAVAHOME%\bin\java -cp . Main

rem  compiling class dumper agent
%JAVAHOME%\bin\javac ClassDumperAgent.java

rem  preparing jar file for class dumper agent
%JAVAHOME%\bin\jar cvfm classdumper.jar MANIFEST.MF ClassDumperAgent.class

rem  compiling attacher
%JAVAHOME%\bin\javac -classpath %JAVAHOME%\lib\tools.jar Attacher.java

rem  search for running app PID
%JAVAHOME%\bin\jps | findstr Main > tmp.txt

rem  attaching to running process
%JAVAHOME%\bin\java -cp %JAVAHOME%\lib\tools.jar;. Attacher tmp.txt classdumper.jar dumpDir=dumpDir,classes=.*

pause
