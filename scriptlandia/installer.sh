#!/bin/sh

. ./config.sh


if [ ! -f $LAUNCHER_HOME/launcher.sh ]; then
  echo Please run jlaunchpad-installer.sht first.
  pause
  exit
fi

if [ ! -f $REPOSITORY_HOME/org/sf/jlaunchpad/jlaunchpad-launcher/$LAUNCHER_VERSION/jlaunchpad-launcher-$LAUNCHER_VERSION.jar ]; then
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
fi

if [ ! -f $REPOSITORY_HOME/org/sf/jlaunchpad/jlaunchpad-common/$LAUNCHER_VERSION/jlaunchpad-common-$LAUNCHER_VERSION.jar ]; then
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
fi

if [ ! -f $REPOSITORY_HOME/org/sf/jlaunchpad/pom-reader/$LAUNCHER_VERSION/pom-reader-$LAUNCHER_VERSION.jar ]; then
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
fi

if [ ! -f $REPOSITORY_HOME/org/apache/maven/bootstrap/bootstrap-mini/2.0.8/bootstrap-mini-2.0.8.jar ]; then
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
fi


PROPERTIES1="-deps.file.name=$REPOSITORY_HOME/org/sf/jlaunchpad/jlaunchpad-launcher/$LAUNCHER_VERSION/jlaunchpad-launcher-$LAUNCHER_VERSION.pom"
PROPERTIES1="$PROPERTIES1 -main.class.name=org.sf.pomreader.ProjectInstaller"

# Install antrun project

$LAUNCHER_HOME/launcher.sh $SYSTEM_PROPERTIES $PROPERTIES1 -Dbasedir=projects/antrun -Dbuild.required=false

# Install scriptlandia-installer project

$LAUNCHER_HOME/launcher.sh $SYSTEM_PROPERTIES $PROPERTIES1 -Dbasedir=projects/scriptlandia-installer -Dbuild.required=false

# Execute scriptlandia-installer project

# SET MAIN_CLASS=org.sf.scriptlandia.install.CoreInstaller
MAIN_CLASS=org.sf.scriptlandia.install.GuiInstaller

PROPERTIES2="-deps.file.name=projects/scriptlandia-installer/pom.xml"
PROPERTIES2="$PROPERTIES2 -main.class.name=$MAIN_CLASS"

LD_LIBRARY_PATH="$LD_LIBRARY_PATH:$REPOSITORY_HOME/org/jdesktop/jdic/0.9.3"

$LAUNCHER_HOME/launcher.sh $SYSTEM_PROPERTIES $PROPERTIES2 -Djava.library.path=$REPOSITORY_HOME/org/jdesktop/jdic/0.9.3 -wait
