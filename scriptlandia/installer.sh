#!/bin/sh

. ./config.sh

#if [ -r "$LAUNCHER_HOME/launcher.sh" ]; then
#  echo Please run jlaunchpad-installer.bat first.

#  pause
#  exit
#fi

# SET MAIN_CLASS=org.sf.scriptlandia.install.CoreInstaller
MAIN_CLASS=org.sf.scriptlandia.install.GuiInstaller

PROPERTIES="-classpath.file.name=scriptlandia.classpath -main.class.name=$MAIN_CLASS"

$LAUNCHER_HOME/launcher.sh $SYSTEM_PROPERTIES $PROPERTIES -wait
