#!/bin/sh

LAUNCHER_HOME=/media/hda5/launcher

MAIN_CLASS=groovy.ui.GroovyMain

PROPERTIES="-deps.file.name=`pwd`/deps.xml -main.class.name=$MAIN_CLASS"

# $LAUNCHER_HOME/launcher.sh $PROPERTIES $*

$LAUNCHER_HOME/launcher.sh $PROPERTIES Hello.groovy
