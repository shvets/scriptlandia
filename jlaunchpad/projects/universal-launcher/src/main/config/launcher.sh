#! /bin/sh

JAVA_HOME=@java.home.internal@
LAUNCHER_HOME=@launcher.home@

APP=`dirname "$0"`
export APP=`cd "$APP" && pwd`

. $LAUNCHER_HOME/launcher-core.sh $*
