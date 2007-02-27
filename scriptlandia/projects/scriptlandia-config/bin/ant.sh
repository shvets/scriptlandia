#!/bin/bash

# init

JAVA_HOME=@java.home@
CONFIG_NAME=ant.classworlds.conf

LAUNCHER_CLASS=org.codehaus.classworlds.Launcher
CLASSPATH=@repository.home@/classworlds/classworlds/@classworlds.version@/classworlds-@classworlds.version@.jar
CONFIG_FILE=-Dclassworlds.conf=@scriptlandia.home@/$CONFIG_NAME

SYSTEM_PARAMETERS=-Xmx256m $CONFIG_FILE

$JAVA_HOME/bin/java $SYSTEM_PARAMETERS -classpath $CLASSPATH $LAUNCHER_CLASS $CMD_LINE_ARGS $*


pause
