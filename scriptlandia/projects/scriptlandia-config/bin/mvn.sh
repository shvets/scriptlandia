#!/bin/sh

REPOSITORY_HOME=@repository.home@
SCRIPTLANDIA_HOME=@scriptlandia.home@

DEPS_FILE=$REPOSITORY_HOME/org/sf/scriptlandia/maven-starter/1.0.1/maven-starter-1.0.1.pom
MAIN_CLASS_NAME=org.sf.scriptlandia.MavenStarter

$SCRIPTLANDIA_HOME/scriptlandia.sh -deps.file.name=$DEPS_FILE -main.class.name=$MAIN_CLASS_NAME $*

