#! /bin/sh

MOBILE_JAVA_HOME=@mobile.java.home@
SCRIPTLANDIA_HOME=@scriptlandia.home@
LAUNCHER_HOME=@launcher.home@

export NAILGUN=@repository.home@/com/martiansoftware/nailgun-bin/@nailgun.version@/ng

APP=`dirname "$0"`

# Make the path absolute
export APP=`cd "$APP" && pwd`

. $LAUNCHER_HOME/launcher-core.sh $*
