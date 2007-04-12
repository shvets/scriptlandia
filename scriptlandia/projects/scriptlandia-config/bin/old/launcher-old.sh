#!/bin/bash

# 1. init

JAVA_HOME=@java.home@
MOBILE_JAVA_HOME=@mobile.java.home@


# 2. get the config name

CONFIG_NAME=%1

# 3. get command line parameters

CMD_LINE_ARGS=

for i in $*
do
  CMD_LINE_ARGS="$CMD_LINE_ARGS $i"
done


# 4. launch JVM

LAUNCHER_CLASS=org.codehaus.classworlds.Launcher
MOBILE_CLASSPATH=$MOBILE_JAVA_HOME/wtklib/kenv.zip:$MOBILE_JAVA_HOME/wtklib/ktools.zip:$MOBILE_JAVA_HOME/wtklib/customjmf.jar
CLASSPATH=@repository.home@/classworlds/classworlds/@classworlds.version@/classworlds-@classworlds.version@.jar:$MOBILE_CLASSPATH
CONFIG_FILE=-Dclassworlds.conf=@scriptlandia.home@/$CONFIG_NAME

SET MOBILE_PARAMETERS=-Dmobile.java.home=$MOBILE_JAVA_HOME -Dkvem.home=$MOBILE_JAVA_HOME -Djava.library.path=@mobile.java.home@/bin -Dsun.java2d.ddlock=true -Dsun.java2d.gdiblit=false 
SET SYSTEM_PARAMETERS=-Xmx256m $MOBILE_PARAMETERS $CONFIG_FILE


$JAVA_HOME/bin/java $SYSTEM_PARAMETERS $CONFIG_FILE -classpath $CLASSPATH $LAUNCHER_CLASS $CMD_LINE_ARGS
