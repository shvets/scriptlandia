#!/bin/sh

. ./config.sh

echo ---### Java Specification Version: $JAVA_SPECIFICATION_VERSION

# echo ---### Installing basic dependencies, required projects and configuration files...

MAIN_CLASS=org.apache.tools.ant.Main

PROPERTIES="-deps.file.name=projects/scriptlandia-startup/pom.xml -main.class.name=$MAIN_CLASS"

$LAUNCHER_HOME/launcher.sh $SYSTEM_PROPERTIES $PROPERTIES install.projects 
