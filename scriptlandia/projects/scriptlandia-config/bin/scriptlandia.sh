#! /bin/sh

MOBILE_JAVA_HOME=@mobile.java.home@
SCRIPTLANDIA_HOME=@scriptlandia.home@
JLAUNCHPAD_HOME=@jlaunchpad.home@
NAILGUN=@repository.home@/com/martiansoftware/nailgun-bin/@nailgun.version@/ng

#APP=`dirname "$0"`

# Make the path absolute
#export APP=`cd "$APP" && pwd`

MAIN_APP_CONF=$SCRIPTLANDIA_HOME/$APP_NAME.jlcnf

if [ ! -f $MAIN_APP_CONF ]; then
  MAIN_APP_CONF=$SCRIPTLANDIA_HOME/scriptlandia.jlcnf
fi                                                                                               
export JLAUNCHPAD_HOME NAILGUN MAIN_APP_CONF

$JLAUNCHPAD_HOME/jlaunchpad-core.sh $*
