set JAVAHOME=c:\Java\jdk1.6.0

echo compiling the app
copy ORIGINAL-Reporter.java Reporter.java
%JAVAHOME%\bin\javac Logging.java Reporter.java Main.java 
echo starting the app
start %JAVAHOME%\bin\java -cp . Main
pause
echo compiling the agent to dump the Pile
%JAVAHOME%\bin\javac DumpPile.java
echo creating the agent jar
echo Agent-Class: DumpPile> manifest.mf
%JAVAHOME%\bin\jar cvfm dump1.jar manifest.mf DumpPile.class
echo compiling the attacher class
%JAVAHOME%\bin\javac -classpath %JAVAHOME%\lib\tools.jar Attach.java
echo getting the pid of the running app
%JAVAHOME%\bin\jps | findstr Main > tmp.txt
echo attaching the Pile dump agent to get the Pile dump
%JAVAHOME%\bin\java -cp %JAVAHOME%\lib\tools.jar;. Attach tmp.txt dump1.jar
pause

echo recompiling Reporter.java with added log statement
copy MAXCOUNTREPORTING-Reporter.java Reporter.java
%JAVAHOME%\bin\javac Reporter.java
echo compiling the agent to dump the Pile
%JAVAHOME%\bin\javac DumpMaxCount.java
echo creating the agent jar
echo Agent-Class: DumpMaxCount> manifest.mf
echo Can-Redefine-Classes: true>> manifest.mf
%JAVAHOME%\bin\jar cvfm dump2.jar manifest.mf DumpMaxCount.class
echo attaching the DumpMaxCount dump agent to get the MaxCount reported
%JAVAHOME%\bin\java -cp %JAVAHOME%\lib\tools.jar;. Attach tmp.txt dump2.jar
pause

echo recompiling Reporter.java with fixes
copy FIXED-Reporter.java Reporter.java
%JAVAHOME%\bin\javac Reporter.java
echo attaching the DumpMaxCount dump agent to get the Reporter class fixed
%JAVAHOME%\bin\java -cp %JAVAHOME%\lib\tools.jar;. Attach tmp.txt dump2.jar

pause
