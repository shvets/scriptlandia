#!/bin/sh

. ../../../jlaunchpad/trunk/jlaunchpad/config.sh

if [ ! -e $JLAUNCHPAD_HOME/jlaunchpad.sh ]; then
  echo Please run jlaunchpad-installer.sh first.

  return
fi

echo ---### Java Specification Version Level: $JAVA_SPECIFICATION_VERSION_LEVEL
echo ---### Installing basic dependencies, required projects and configuration files...

PROPERTIES="-deps.file.name=$REPOSITORY_HOME/org/sf/jlaunchpad/jlaunchpad-launcher/1.0.1/jlaunchpad-launcher-1.0.1.pom"
PROPERTIES="$PROPERTIES -main.class.name=org.sf.pomreader.ProjectInstaller"

$JLAUNCHPAD_HOME/jlaunchpad.sh $SYSTEM_PROPERTIES $PROPERTIES -Dbasedir=projects/antrun -Dbuild.required=true

$JLAUNCHPAD_HOME/jlaunchpad.sh -Xbootclasspath/a:$JAVA_HOME/jre/lib/deploy.jar $SYSTEM_PROPERTIES $PROPERTIES -Dbasedir=projects/scriptlandia-installer -Dbuild.required=true

$JLAUNCHPAD_HOME/jlaunchpad.sh "$SYSTEM_PROPERTIES $PROPERTIES" -Dbasedir=projects/scriptlandia-nailgun -Dbuild.required=true

$JLAUNCHPAD_HOME/jlaunchpad.sh "$SYSTEM_PROPERTIES $PROPERTIES" -Dbasedir=projects/scriptlandia-launcher -Dbuild.required=true

$JLAUNCHPAD_HOME/jlaunchpad.sh "$SYSTEM_PROPERTIES $PROPERTIES" -Dbasedir=projects/scriptlandia-helper -Dbuild.required=true

