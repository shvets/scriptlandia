#!/bin/sh

export JAVA_HOME=@java.home@
export SCRIPTLANDIA_VERSION=@scriptlandia.version@
export REPOSITORY_HOME=@repository.home@

DEPS_FILE=$REPOSITORY_HOME/org/sf/scriptlandia/maven-starter/$SCRIPTLANDIA_VERSION/maven-starter-$SCRIPTLANDIA_VERSION.pom
MAIN_CLASS_NAME=org.sf.scriptlandia.MavenStarter

@scriptlandia.home@/launcher.sh -deps.file.name=$DEPS_FILE -main.class.name=$MAIN_CLASS_NAME $*

