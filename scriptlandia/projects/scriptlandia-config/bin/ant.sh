#!/bin/sh

SCRIPTLANDIA_VERSION=@scriptlandia.version@
REPOSITORY_HOME=@repository.home@
SCRIPTLANDIA_HOME=@scriptlandia.home@

DEPS_FILE=$REPOSITORY_HOME/org/sf/scriptlandia/ant-starter/$SCRIPTLANDIA_VERSION/ant-starter-$SCRIPTLANDIA_VERSION.pom
MAIN_CLASS_NAME=org.sf.scriptlandia.AntStarter

$SCRIPTLANDIA_HOME/launcher.sh -deps.file.name=$DEPS_FILE -main.class.name=$MAIN_CLASS_NAME $*
