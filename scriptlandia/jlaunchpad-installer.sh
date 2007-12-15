#!/bin/sh

. ./config.sh

JLAUNCHPAD_PROJECT=.
ORIGINAL_JLAUNCHPAD_PROJECT=../../../jlaunchpad/trunk

if [ -f ~/jlaunchpad/config.sh ]; then
  . ~/jlaunchpad/config.sh
fi

if [ -f $ORIGINAL_JLAUNCHPAD_PROJECT/projects/jlaunchpad-launcher/target\jlaunchpad-launcher.jar ]; then
  cd $ORIGINAL_JLAUNCHPAD_PROJECT
fi


BOOTSTRAP_MINI_PROJECT=$JLAUNCHPAD_PROJECT/projects/bootstrap-mini
JLAUNCHPAD_COMMON_PROJECT=$JLAUNCHPAD_PROJECT/projects/jlaunchpad-common
POM_READER_PROJECT=$JLAUNCHPAD_PROJECT/projects/pom-reader
JLAUNCHPAD_LAUNCHER_PROJECT=$JLAUNCHPAD_PROJECT/projects/universal-launcher

CLASSPATH=$BOOTSTRAP_MINI_PROJECT/target/bootstrap-mini.jar
CLASSPATH=$CLASSPATH:$JLAUNCHPAD_COMMON_PROJECT/target/jlaunchpad-common.jar
CLASSPATH=$CLASSPATH:$POM_READER_PROJECT/target/pom-reader.jar
CLASSPATH=$CLASSPATH:$JLAUNCHPAD_LAUNCHER_PROJECT/target/jlaunchpad-launcher.jar


# MAIN_CLASS=org.sf.jlaunchpad.install.CoreInstaller
MAIN_CLASS=org.sf.jlaunchpad.install.GuiInstaller

$JAVA_HOME/bin/java -classpath $CLASSPATH $SYSTEM_PROPERTIES $MAIN_CLASS
