#!/bin/sh

. ../config.sh

if [ -f ~/jlaunchpad/config.sh ]; then
  . ~/jlaunchpad/config.sh
fi

echo ---### Java Specification Version: $JAVA_SPECIFICATION_VERSION
echo ---### Installing required projects and configuration files...

MAIN_CLASS=org.apache.tools.ant.Main

PROPERTIES="-deps.file.name=../projects/scriptlandia-startup/pom.xml -main.class.name=$MAIN_CLASS"

$LAUNCHER_HOME/launcher.sh \
  $SYSTEM_PROPERTIES $PROPERTIES \
  -f package.ant package
