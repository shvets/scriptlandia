#!/bin/sh

if [ -f ~/scriptlandia/config.sh ]; then
  . ~/scriptlandia/config.sh
fi

. ./config.sh

BOOTSTRAP_MINI_PROJECT=projects/bootstrap-mini
SCRIPTLANDIA_COMMON_PROJECT=projects/scriptlandia-common
POM_READER_PROJECT=projects/pomreader
IMAGE4J_PROJECT=projects/image4j
SCRIPTLANDIA_INSTALLER_PROJECT=projects/scriptlandia-installer

CLASSPATH=$BOOTSTRAP_MINI_PROJECT/target/bootstrap-mini.jar
CLASSPATH=$CLASSPATH:$POM_READER_PROJECT/target/pomreader.jar
CLASSPATH=$CLASSPATH:$IMAGE4J_PROJECT/target/image4j.jar
CLASSPATH=$CLASSPATH:$SCRIPTLANDIA_COMMON_PROJECT/target/scriptlandia-common.jar
CLASSPATH=$CLASSPATH:$SCRIPTLANDIA_INSTALLER_PROJECT/target/scriptlandia-installer.jar

# MAIN_CLASS=org.sf.scriptlandia.install.CoreInstaller
MAIN_CLASS=org.sf.scriptlandia.install.GuiInstaller

$JAVA_HOME/bin/java -classpath $CLASSPATH $SYSTEM_PROPERTIES $MAIN_CLASS
