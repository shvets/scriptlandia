#!/bin/sh

if [ -f ~/scriptlandia/config.sh ]; then
  . ~/scriptlandia/config.sh
fi

. ./config.sh

echo ---### Java Specification Version: $JAVA_SPECIFICATION_VERSION

echo ---### Installing basic dependencies...

BASIC_CLASSPATH=$REPOSITORY_HOME/org/sf/jlaunchpad/universal-launcher-common/$LAUNCHER_VERSION/universal-launcher-common-$LAUNCHER_VERSION.jar
BASIC_CLASSPATH=$BASIC_CLASSPATH:$REPOSITORY_HOME/org/apache/maven/bootstrap/bootstrap-mini/2.0.7/bootstrap-mini-2.0.7.jar
BASIC_CLASSPATH=$BASIC_CLASSPATH:$REPOSITORY_HOME/org/sf/jlaunchpad/pom-reader/$LAUNCHER_VERSION/pom-reader-$LAUNCHER_VERSION.jar

BASIC_CLASSPATH=$BASIC_CLASSPATH:$SCRIPTLANDIA_INSTALLER_PROJECT/target/scriptlandia-installer.jar

$JAVA_HOME/bin/java \
  -Drepository.home=$REPOSITORY_HOME \
  $PROXY_PARAMS \
  -Dbasedir=projects/scriptlandia-startup \
  -classpath $BASIC_CLASSPATH \
  $SYSTEM_PROPERTIES \
  org.sf.scriptlandia.install.ProjectInstaller

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
  org.apache.tools.ant.launch.Launcher -f package.ant package.projects
