#! /bin/sh

JAVA_HOME=@java.home@
MOBILE_JAVA_HOME=@mobile.java.home@
SCRIPTLANDIA_HOME=@scriptlandia.home@

export NAILGUN=@repository.home@/com/martiansoftware/nailgun-bin/@nailgun.version@/ng

APP=`dirname "$0"`

# Make the path absolute
export APP=`cd "$APP" && pwd`

. $SCRIPTLANDIA_HOME/launcher/app.sh $*
