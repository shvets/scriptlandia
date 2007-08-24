#!/bin/sh

CYGWIN=true

if [ -f ~/jlaunchpad/config.sh ]; then
  . ~/jlaunchpad/config.sh
fi

if [ "$JAVA_HOME" = "" ]; then
  JAVA_HOME=$DRIVE_LETTER/jdk1.6.0
fi

if [ "$LAUNCHER_HOME" = "" ]; then
  LAUNCHER_HOME=$DRIVE_LETTER/launcher
fi

if [ "%REPOSITORY_HOME%" == "" ]; then
  REPOSITORY_HOME=$DRIVE_LETTER/maven-repository
fi

SCRIPTLANDIA_HOME=~/scriptlandia
MOBILE_JAVA_HOME=/opt/j2me-2.5
RUBY_HOME=~/Ruby/ruby-1.8.4-20

JAVA_SPECIFICATION_VERSION=1.5
ANT_VERSION=1.7.0
BEANSHELL_VERSION=2.0b5
SCRIPTLANDIA_VERSION=2.2.3
LAUNCHER_VERSION=1.0
JDIC_VERSION=0.9.2
NAILGUN_VERSION=0.7.1
JAVA_COMPILER_VERSION=7.0-b18

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
-Dlauncher.version=$LAUNCHER_VERSION \
-Djdic.version=$JDIC_VERSION \
-Dnailgun.version=$NAILGUN_VERSION \
-Djava.compiler.version=$JAVA_COMPILER_VERSION \
-Djava.specification.version=$JAVA_SPECIFICATION_VERSION \
-Dscriptlandia.home=$SCRIPTLANDIA_HOME \
-Dlauncher.home=$LAUNCHER_HOME \
-Drepository.home=$REPOSITORY_HOME"

if [ ! -f $RUBY_HOME ]; then
  SYSTEM_PROPERTIES="$SYSTEM_PROPERTIES -Dnative.ruby.home=$RUBY_HOME"
fi

export CYGWIN

export PROXY_SERVER_HOST_NAME PROXY_SERVER_PORT
export JAVA_HOME MOBILE_JAVA_HOME RUBY_HOME SCRIPTLANDIA_HOME REPOSITORY_HOME
export JAVA_SPECIFICATION_VERSION ANT_VERSION BEANSHELL_VERSION SCRIPTLANDIA_VERSION
export SYSTEM_PROPERTIES
