#! /bin/sh

MOBILE_JAVA_HOME=@mobile.java.home@
SCRIPTLANDIA_HOME=@scriptlandia.home@
LAUNCHER_HOME=@launcher.home@
NAILGUN=@repository.home@/com/martiansoftware/nailgun-bin/@nailgun.version@/ng

#APP=`dirname "$0"`

# Make the path absolute
#export APP=`cd "$APP" && pwd`

MAIN_APP_CONF=$SCRIPTLANDIA_HOME/$APP_NAME.conf

if [ ! -f $MAIN_APP_CONF ]; then
  MAIN_APP_CONF=$SCRIPTLANDIA_HOME/scriptlandia.conf
fi

export LAUNCHER_HOME NAILGUN MAIN_APP_CONF

$LAUNCHER_HOME/launcher-core.sh $*
