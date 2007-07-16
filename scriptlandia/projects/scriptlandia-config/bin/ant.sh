#!/bin/sh

REPOSITORY_HOME=@repository.home@
SCRIPTLANDIA_HOME=@scriptlandia.home@

DEPS_FILE=$REPOSITORY_HOME/org/sf/scriptlandia/ant-starter/1.0/ant-starter-1.0.pom
MAIN_CLASS_NAME=org.sf.scriptlandia.AntStarter

$SCRIPTLANDIA_HOME/scriptlandia.sh -deps.file.name=$DEPS_FILE -main.class.name=$MAIN_CLASS_NAME $*
