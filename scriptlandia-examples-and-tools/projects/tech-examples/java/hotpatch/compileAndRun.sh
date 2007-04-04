#!/bin/sh
JAVAHOME=/home/shirazja/tmp/j6/jdk1.6.0

echo compiling the app
cp ORIGINAL-Reporter.java Reporter.java
$JAVAHOME/bin/javac Logging.java Reporter.java Main.java
echo starting the app, it is clearer if you start this in a separate window though
$JAVAHOME/bin/java Main &
sleep 30
echo compiling the agent to dump the Pile
$JAVAHOME/bin/javac DumpPile.java
echo creating the agent jar
echo Agent-Class: DumpPile> manifest.mf
$JAVAHOME/bin/jar cvfm dump1.jar manifest.mf DumpPile.class
echo compiling the attacher class
$JAVAHOME/bin/javac -classpath $JAVAHOME/lib/tools.jar Attach.java
echo getting the pid of the running app
$JAVAHOME/bin/jps | grep 'Main' > tmp.txt
echo attaching the Pile dump agent to get the Pile dump
$JAVAHOME/bin/java -cp $JAVAHOME/lib/tools.jar:. Attach tmp.txt dump1.jar
sleep 10

echo recompiling Reporter.java with added log statement
cp MAXCOUNTREPORTING-Reporter.java Reporter.java
$JAVAHOME/bin/javac Reporter.java
echo compiling the agent to dump the Pile
$JAVAHOME/bin/javac DumpMaxCount.java
echo creating the agent jar
echo Agent-Class: DumpMaxCount> manifest.mf
echo Can-Redefine-Classes: true>> manifest.mf
$JAVAHOME/bin/jar cvfm dump2.jar manifest.mf DumpMaxCount.class
echo attaching the DumpMaxCount dump agent to get the MaxCount reported
$JAVAHOME/bin/java -cp $JAVAHOME/lib/tools.jar:. Attach tmp.txt dump2.jar
sleep 10

echo recompiling Reporter.java with fixes
cp FIXED-Reporter.java Reporter.java
$JAVAHOME/bin/javac Reporter.java
echo attaching the DumpMaxCount dump agent to get the Reporter class fixed
$JAVAHOME/bin/java -cp $JAVAHOME/lib/tools.jar:. Attach tmp.txt dump2.jar

sleep 10
