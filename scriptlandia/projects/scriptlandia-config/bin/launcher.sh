#! /bin/sh

export JAVA_HOME=@java.home@
export MOBILE_JAVA_HOME=@mobile.java.home@

export NAILGUN=@repository.home@/com/martiansoftware/nailgun-bin/@nailgun.version@/ng

APP=`dirname "$0"`

# Make the path absolute
export APP=`cd "$APP" && pwd`

. @scriptlandia.home@/launcher/app.sh $*
