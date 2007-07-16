#!/bin/sh

if [ -f ~/jlaunchpad/config.sh ]; then
  . ~/jlaunchpad/config.sh
fi

. ./config.sh

BOOTSTRAP_MINI_PROJECT=projects/bootstrap-mini
UNIVERSAL_LAUNCHER_COMMON_PROJECT=projects/universal-launcher-common
POM_READER_PROJECT=projects/pom-reader
UNIVERSAL_LAUNCHER_PROJECT=projects/universal-launcher

CLASSPATH=$BOOTSTRAP_MINI_PROJECT/target/bootstrap-mini.jar
CLASSPATH=$CLASSPATH:$UNIVERSAL_LAUNCHER_COMMON_PROJECT/target/universal-launcher-common.jar
CLASSPATH=$CLASSPATH:$POM_READER_PROJECT/target/pom-reader.jar
CLASSPATH=$CLASSPATH:$UNIVERSAL_LAUNCHER_PROJECT/target/universal-launcher.jar

# MAIN_CLASS=org.sf.jlaunchpad.install.CoreInstaller
MAIN_CLASS=org.sf.jlaunchpad.install.GuiInstaller

$JAVA_HOME/bin/java -classpath $CLASSPATH $SYSTEM_PROPERTIES $MAIN_CLASS
