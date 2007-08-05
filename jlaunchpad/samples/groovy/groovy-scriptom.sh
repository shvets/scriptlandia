#!/bin/sh

CYGWIN=true

LAUNCHER_HOME=/cygdrive/d/launcher-cygnus

MAIN_CLASS=groovy.ui.GroovyMain

PWD=`pwd`

if [ "$CYGWIN" = "true" ]; then
  PWD=`cygpath -wp $PWD`
fi

PROPERTIES="-deps.file.name=$PWD/deps.xml -main.class.name=$MAIN_CLASS"

export APP_NAME=groovy

$LAUNCHER_HOME/launcher.sh $PROPERTIES ie.groovy
