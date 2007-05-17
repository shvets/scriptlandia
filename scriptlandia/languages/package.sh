#!/bin/sh

if [ -f ~/scriptlandia/config.sh ]; then
  . ~/scriptlandia/config.sh
fi

. ../config.sh

echo ---### Java Specification Version: $JAVA_SPECIFICATION_VERSION

echo ---### Installing required projects and configuration files...

CLASSPATH=$REPOSITORY_HOME/org/apache/ant/ant-launcher/$ANT_VERSION/ant-launcher-$ANT_VERSION.jar
CLASSPATH=$CLASSPATH:$REPOSITORY_HOME/org/apache/ant/ant/$ANT_VERSION/ant-$ANT_VERSION.jar
CLASSPATH=$CLASSPATH:$REPOSITORY_HOME/org/apache/ant/ant-apache-bsf/$ANT_VERSION/ant-apache-bsf-$ANT_VERSION.jar
CLASSPATH=$CLASSPATH:$REPOSITORY_HOME/org/apache/ant/ant-nodeps/$ANT_VERSION/ant-nodeps-$ANT_VERSION.jar
CLASSPATH=$CLASSPATH:$REPOSITORY_HOME/commons-logging/commons-logging/1.0.4/commons-logging-1.0.4.jar
CLASSPATH=$CLASSPATH:$REPOSITORY_HOME/bsf/bsf/2.4.0/bsf-2.4.0.jar
CLASSPATH=$CLASSPATH:$REPOSITORY_HOME/bsh/bsh/$BEANSHELL_VERSION/bsh-$BEANSHELL_VERSION.jar

$JAVA_HOME/bin/java \
  -classpath $CLASSPATH \
  $SYSTEM_PROPERTIES \
  org.apache.tools.ant.launch.Launcher -f package.ant $*
