#!/bin/sh

SCRIPTLANDIA_VERSION=@scriptlandia.version@
REPOSITORY_HOME=@repository.home@
SCRIPTLANDIA_HOME=@scriptlandia.home@

DEPS_FILE=$REPOSITORY_HOME/org/sf/scriptlandia/maven-starter/$SCRIPTLANDIA_VERSION/maven-starter-$SCRIPTLANDIA_VERSION.pom
MAIN_CLASS_NAME=org.sf.scriptlandia.MavenStarter

$SCRIPTLANDIA_HOME/launcher.sh -deps.file.name=$DEPS_FILE -main.class.name=$MAIN_CLASS_NAME $*

