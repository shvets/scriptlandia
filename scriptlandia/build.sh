#!/bin/sh

. ./config.sh

if [ ! -f $LAUNCHER_HOME/launcher.sh ]; then
  echo Please run jlaunchpad-installer.sh first.

  pause
  exit 1
fi

echo ---### Java Specification Version Level: $JAVA_SPECIFICATION_VERSION_LEVEL
echo ---### Installing basic dependencies, required projects and configuration files...

SYSTEM_PROPERTIES="$SYSTEM_PROPERTIES -Dbuild.required=true"

PROPERTIES="-deps.file.name=$REPOSITORY_HOME/org/sf/jlaunchpad/jlaunchpad-launcher/1.0.1/jlaunchpad-launcher-1.0.1.pom"
PROPERTIES="$PROPERTIES -main.class.name=org.sf.pomreader.ProjectInstaller"

$LAUNCHER_HOME/launcher.sh $SYSTEM_PROPERTIES -Dbasedir=projects/antrun $PROPERTIES

$LAUNCHER_HOME/launcher.sh $SYSTEM_PROPERTIES -Dbasedir=projects/scriptlandia-installer $PROPERTIES

$LAUNCHER_HOME/launcher.sh "$SYSTEM_PROPERTIES -Dbasedir=projects/scriptlandia-nailgun $PROPERTIES"

$LAUNCHER_HOME/launcher.sh "$SYSTEM_PROPERTIES -Dbasedir=projects/scriptlandia-launcher $PROPERTIES"

$LAUNCHER_HOME/launcher.sh "$SYSTEM_PROPERTIES -Dbasedir=projects/scriptlandia-helper $PROPERTIES"

