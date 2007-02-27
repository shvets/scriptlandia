#!/bin/bash

JAVA_HOME=/opt/java_1.5.0
MOBILE_JAVA_HOME=/opt/j2me-2.3
MAVEN_HOME=/opt/maven-2.0.5


:step1

if [ ! -f $programjar ]; then
    die "Missing required file: $programjar"
fi

if [ "x$JAVA_HOME" = "x" ]; then
  ECHO JDK cannot be found!
  pause
  GOTO end
fi

if [ "x$MAVEN_HOME" = "x" ]; then
  MAVEN_HOME=maven
fi

if [ "x$MAVEN_HOME" = "x" ]; then
  ECHO Maven cannot be found!
  pause
  GOTO end
fi

if [ "x$JAVA_MOBILE_HOME" = "x" ]; then
  ECHO Java Micro Edition cannot be found!
fi


SETTINGS_FILE=~/.m2/settings.xml

if [ "x$SETTINGS_FILE" = "x" ]; then
  SETTINGS_FILE=./settings.xml
fi

START_CLASS=org.codehaus.classworlds.Launcher

SYSTEM_PROPERTIES=-Dmobile.java.home=$MOBILE_JAVA_HOME -Dmaven.home=$MAVEN_HOME -Dclassworlds.conf=$MAVEN_HOME/bin/m2.conf

SET CLASSWORLDS_JAR=$MAVEN_HOME/core/boot/classworlds-1.1.jar


$JAVA_HOME/bin/java $SYSTEM_PROPERTIES -classpath $CLASSWORLDS_JAR $START_CLASS --settings settings.xml -f scriptlandia.maven install

:end