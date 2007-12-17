#!/bin/sh

. ./config.sh

echo ---### Java Specification Version: $JAVA_SPECIFICATION_VERSION
echo ---### Installing basic dependencies, required projects and configuration files...

if [ -f $LAUNCHER_HOME/launcher.sh ]; then
  ;
else
  echo Please run jlaunchpad-installer.sh first.

  pause
  exit 1
fi

PROPERTIES=$PROPERTIES "-deps.file.name=$REPOSITORY_HOME/org/sf/jlaunchpad/jlaunchpad-launcher/1.0.1/jlaunchpad-launcher-1.0.1.pom" 
PROPERTIES=$PROPERTIES "-main.class.name=org.sf.pomreader.ProjectInstaller"

. ./$LAUNCHER_HOME/launcher.sh $SYSTEM_PROPERTIES $PROPERTIES "-Dbasedir=projects/antrun" "-Dbuild.required=true"

. ./$LAUNCHER_HOME/launcher.sh $SYSTEM_PROPERTIES $PROPERTIES "-Dbasedir=projects/scriptlandia-installer" "-Dbuild.required=true"

. ./$LAUNCHER_HOME/launcher.sh $SYSTEM_PROPERTIES $PROPERTIES "-Dbasedir=projects/scriptlandia-nailgun" "-Dbuild.required=true"

. ./$LAUNCHER_HOME/launcher.sh $SYSTEM_PROPERTIES $PROPERTIES "-Dbasedir=projects/scriptlandia-launcher" "-Dbuild.required=true"

. ./$LAUNCHER_HOME/launcher.sh $SYSTEM_PROPERTIES $PROPERTIES "-Dbasedir=projects/scriptlandia-helper" "-Dbuild.required=true"

