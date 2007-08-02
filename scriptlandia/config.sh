#!/bin/sh

if [ -f ~/jlaunchpad/config.sh ]; then
  . ~/jlaunchpad/config.sh
fi

#if [ "x$PROXY_SERVER_HOST_NAME" = "x" ]; then
#  PROXY_SERVER_HOST_NAME=
#fi

#if [ "x$PROXY_SERVER_PORT" = "x" ]; then
#  PROXY_SERVER_PORT=
#fi

JAVA_HOME=~/jdk1.6.0_01
MOBILE_JAVA_HOME=/opt/j2me-2.5
RUBY_HOME=~/Ruby/ruby-1.8.4-20

SCRIPTLANDIA_HOME=~/scriptlandia
LAUNCHER_HOME=$SCRIPTLANDIA_HOME/launcher
REPOSITORY_HOME=/media/hda5/maven-repository

#LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$REPOSITORY_HOME/org/jdesktop/jdic/0.9.2

JAVA_SPECIFICATION_VERSION=1.5
ANT_VERSION=1.7.0
BEANSHELL_VERSION=2.0b5
SCRIPTLANDIA_VERSION=2.2.3
LAUNCHER_VERSION=1.0

# if [ "x$PROXY_SERVER_HOST_NAME" = "x" ]; then
#  PROXY_PARAMS="$SYSTEM_PROPERTIES -DproxyHost=$PROXY_SERVER_HOST_NAME -DproxyPort=$PROXY_SERVER_PORT"
#fi

if [ "x$JAVA_HOME" = "x" ]; then
  ECHO JDK cannot be found!
  pause
  exit 1
fi

if [ "x$MOBILE_JAVA_HOME" = "x" ]; then
  ECHO Java Micro Edition cannot be found!
fi

SYSTEM_PROPERTIES="-Dmobile.java.home=$MOBILE_JAVA_HOME \
-Dant.version.internal=$ANT_VERSION \
-Dbeanshell.version=$BEANSHELL_VERSION \
-Dscriptlandia.version=$SCRIPTLANDIA_VERSION \
-Dlauncher.version=$LAUNCHER_VERSION \
-Djava.specification.version=$JAVA_SPECIFICATION_VERSION \
-Dscriptlandia.home=$SCRIPTLANDIA_HOME \
-Dlauncher.home=$LAUNCHER_HOME \
-Drepository.home=$REPOSITORY_HOME"

# SYSTEM_PROPERTIES="$SYSTEM_PROPERTIES $PROXY_PARAMS"

if [ ! -f $RUBY_HOME ]; then
  SYSTEM_PROPERTIES="$SYSTEM_PROPERTIES -Dnative.ruby.home=$RUBY_HOME"
fi

export PROXY_SERVER_HOST_NAME PROXY_SERVER_PORT
export JAVA_HOME MOBILE_JAVA_HOME RUBY_HOME SCRIPTLANDIA_HOME REPOSITORY_HOME
export JAVA_SPECIFICATION_VERSION ANT_VERSION BEANSHELL_VERSION SCRIPTLANDIA_VERSION
export SYSTEM_PROPERTIES
