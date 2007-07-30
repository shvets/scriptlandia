#!/bin/sh

LAUNCHER_HOME=/media/hda5/launcher

MAIN_CLASS=org.jruby.Main

PROPERTIES="-deps.file.name=`pwd`/deps.xml -main.class.name=$MAIN_CLASS"

# $LAUNCHER_HOME/launcher.sh $PROPERTIES $*

$LAUNCHER_HOME/launcher.sh $PROPERTIES test1.rb
