#!/bin/sh

if [ -f ~/scriptlandia/config.sh ]; then
  . ~/scriptlandia/config.sh
fi

. ./config.sh

SCRIPTLANDIA_COMMON_PROJECT=$PWD/projects/scriptlandia-common
BOOTSTRAP_MINI_PROJECT=$PWD/projects/bootstrap-mini
POM_READER_PROJECT=$PWD/projects/pomreader
IMAGE4J_PROJECT=$PWD/projects/image4j
SCRIPTLANDIA_INSTALLER_PROJECT=$PWD/projects/scriptlandia-installer

echo ---### Java Specification Version: $JAVA_SPECIFICATION_VERSION

echo ---### Builds Scriptlandia-Common project

if [ ! -f $SCRIPTLANDIA_COMMON_PROJECT/target/classes ]; then
  mkdir -p $SCRIPTLANDIA_COMMON_PROJECT/target/classes
fi

SL_COMMON_CLASSPATH=$SCRIPTLANDIA_COMMON_PROJECT/src/main/java

$JAVA_HOME/bin/javac -nowarn -source $JAVA_SPECIFICATION_VERSION -target $JAVA_SPECIFICATION_VERSION \
  -classpath $SL_COMMON_CLASSPATH \
  -d $SCRIPTLANDIA_COMMON_PROJECT/target/classes \
  $SCRIPTLANDIA_COMMON_PROJECT/src/main/java/org/sf/scriptlandia/util/*.java \
  $SCRIPTLANDIA_COMMON_PROJECT/src/main/java/org/sf/scriptlandia/launcher/*.java

$JAVA_HOME/bin/jar cf $SCRIPTLANDIA_COMMON_PROJECT/target/scriptlandia-common.jar \
  -C $SCRIPTLANDIA_COMMON_PROJECT/target/classes .

echo ---### Builds Bootstrap-Mini project

if [ ! -f $BOOTSTRAP_MINI_PROJECT/target/classes ]; then
  mkdir -p $BOOTSTRAP_MINI_PROJECT/target/classes
fi

BM_CLASSPATH=$SCRIPTLANDIA_COMMON_PROJECT/target/classes
BM_CLASSPATH=$BM_CLASSPATH:$BOOTSTRAP_MINI_PROJECT/src/main/java

$JAVA_HOME/bin/javac -nowarn -source $JAVA_SPECIFICATION_VERSION -target $JAVA_SPECIFICATION_VERSION \
  -classpath $BM_CLASSPATH \
  -d $BOOTSTRAP_MINI_PROJECT/target/classes \
  $BOOTSTRAP_MINI_PROJECT/src/main/java/org/apache/maven/bootstrap/Bootstrap.java

$JAVA_HOME/bin/jar cf $BOOTSTRAP_MINI_PROJECT/target/bootstrap-mini.jar \
  -C $BOOTSTRAP_MINI_PROJECT/target/classes .


echo ---### Builds PomReader project

if [ ! -f $POM_READER_PROJECT/target/classes ]; then
  mkdir -p $POM_READER_PROJECT/target/classes
fi

PR_CLASSPATH=$BOOTSTRAP_MINI_PROJECT/target/classes
PR_CLASSPATH=$PR_CLASSPATH:$SCRIPTLANDIA_COMMON_PROJECT/target/classes
PR_CLASSPATH=$PR_CLASSPATH:$POM_READER_PROJECT/src/main/java

$JAVA_HOME/bin/javac -nowarn -source $JAVA_SPECIFICATION_VERSION -target $JAVA_SPECIFICATION_VERSION \
  -classpath $PR_CLASSPATH \
  -d $POM_READER_PROJECT/target/classes \
  $POM_READER_PROJECT/src/main/java/org/sf/scriptlandia/pomreader/PomReader.java

$JAVA_HOME/bin/jar cf $POM_READER_PROJECT/target/pomreader.jar \
  -C $POM_READER_PROJECT/target/classes .


echo ---### Builds Image4j project

if [ ! -f $IMAGE4J_PROJECT/target/classes ]; then
  mkdir -p $IMAGE4J_PROJECT/target/classes
fi

IMAGE4J_CLASSPATH=$IMAGE4J_PROJECT/target/classes

$JAVA_HOME/bin/javac -nowarn -source $JAVA_SPECIFICATION_VERSION -target $JAVA_SPECIFICATION_VERSION \
  -classpath $IMAGE4J_CLASSPATH \
  -d $IMAGE4J_PROJECT/target/classes \
  $IMAGE4J_PROJECT/src/main/java/net/sf/image4j/codec/ico/*.java \
  $IMAGE4J_PROJECT/src/main/java/net/sf/image4j/codec/bmp/*.java \
  $IMAGE4J_PROJECT/src/main/java/net/sf/image4j/io/*.java \
  $IMAGE4J_PROJECT/src/main/java/net/sf/image4j/util/*.java

$JAVA_HOME/bin/jar cf $IMAGE4J_PROJECT/target/image4j.jar \
  -C $IMAGE4J_PROJECT/target/classes .

echo ---### Builds Installer project

if [ ! -f $SCRIPTLANDIA_INSTALLER_PROJECT/target/classes ]; then
  mkdir -p $SCRIPTLANDIA_INSTALLER_PROJECT/target/classes
fi

INST_CLASSPATH=$BOOTSTRAP_MINI_PROJECT/target/classes
INST_CLASSPATH=$INST_CLASSPATH:$POM_READER_PROJECT/target/classes
INST_CLASSPATH=$INST_CLASSPATH:$IMAGE4J_PROJECT/target/classes
INST_CLASSPATH=$INST_CLASSPATH:$SCRIPTLANDIA_COMMON_PROJECT/target/classes
INST_CLASSPATH=$INST_CLASSPATH:$SCRIPTLANDIA_INSTALLER_PROJECT/src/main/java

$JAVA_HOME/bin/javac -nowarn -source $JAVA_SPECIFICATION_VERSION -target $JAVA_SPECIFICATION_VERSION \
  -classpath $INST_CLASSPATH \
  -d $SCRIPTLANDIA_INSTALLER_PROJECT/target/classes \
  $SCRIPTLANDIA_INSTALLER_PROJECT/src/main/java/org/sf/scriptlandia/install/*.java

$JAVA_HOME/bin/jar cf $SCRIPTLANDIA_INSTALLER_PROJECT/target/scriptlandia-installer.jar \
  -C $SCRIPTLANDIA_INSTALLER_PROJECT/target/classes .

echo ---### Installing basic dependencies...

BASIC_CLASSPATH=$SCRIPTLANDIA_COMMON_PROJECT/target/scriptlandia-common.jar
BASIC_CLASSPATH=$BASIC_CLASSPATH:$BOOTSTRAP_MINI_PROJECT/target/bootstrap-mini.jar
BASIC_CLASSPATH=$BASIC_CLASSPATH:$POM_READER_PROJECT/target/pomreader.jar
BASIC_CLASSPATH=$BASIC_CLASSPATH:$IMAGE4J_PROJECT/target/image4j.jar
BASIC_CLASSPATH=$BASIC_CLASSPATH:$SCRIPTLANDIA_INSTALLER_PROJECT/target/scriptlandia-installer.jar

$JAVA_HOME/bin/java \
  -Dmaven.repo.local=$REPOSITORY_HOME $PROXY_PARAMS -Dbasedir=projects/scriptlandia-startup \
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
