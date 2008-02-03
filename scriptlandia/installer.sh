#!/bin/sh

. ../../../jlaunchpad/trunk/jlaunchpad/config.sh


if [ ! -f $LAUNCHER_HOME/jlaunchpad.sh ]; then
  echo Please run jlaunchpad-installer.sht first.
  pause
  exit
fi

if [ ! -f $REPOSITORY_HOME/org/sf/jlaunchpad/jlaunchpad-launcher/$JLAUNCHPAD_VERSION/jlaunchpad-launcher-$JLAUNCHPAD_VERSION.jar ]; then
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
fi

if [ ! -f $REPOSITORY_HOME/org/sf/jlaunchpad/jlaunchpad-common/$JLAUNCHPAD_VERSION/jlaunchpad-common-$JLAUNCHPAD_VERSION.jar ]; then
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
fi

if [ ! -f $REPOSITORY_HOME/org/sf/jlaunchpad/pom-reader/$JLAUNCHPAD_VERSION/pom-reader-$JLAUNCHPAD_VERSION.jar ]; then
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
fi

if [ ! -f $REPOSITORY_HOME/org/apache/maven/bootstrap/bootstrap-mini/2.0.8/bootstrap-mini-2.0.8.jar ]; then
  echo Please run jlaunchpad-installer.bat first.
  pause
  exit
fi


SCRIPTLANDIA_HOME=%DRIVE_LETTER%\scriptlandia
MOBILE_JAVA_HOME=%DRIVE_LETTER%\Java\j2me-2.5
RUBY_HOME=%DRIVE_LETTER%\Ruby\ruby-1.8.4-20

SCRIPTLANDIA_VERSION=2.2.4
ANT_VERSION=1.7.0
BEANSHELL_VERSION=2.0b5
NAILGUN_VERSION=0.7.1
JAVA_COMPILER_VERSION=7.0-b23

if [ ! -f $JAVA_HOME ]; then
  ECHO JDK cannot be found!
  pause
  #exit
fi

if [ ! -f $MOBILE_JAVA_HOME ]; then
  ECHO Java Micro Edition cannot be found!
fi


SYSTEM_PROPERTIES="$SYSTEM_PROPERTIES -Dant.version.internal=$ANT_VERSION"
SYSTEM_PROPERTIES="$SYSTEM_PROPERTIES -Dbeanshell.version=$BEANSHELL_VERSION"
SYSTEM_PROPERTIES="$SYSTEM_PROPERTIES -Dscriptlandia.version=$SCRIPTLANDIA_VERSION"
SYSTEM_PROPERTIES="$SYSTEM_PROPERTIES -Dnailgun.version=$NAILGUN_VERSION"
SYSTEM_PROPERTIES="$SYSTEM_PROPERTIES -Djava.compiler.version=$JAVA_COMPILER_VERSION"
SYSTEM_PROPERTIES="$SYSTEM_PROPERTIES -Dscriptlandia.home=$SCRIPTLANDIA_HOME"
SYSTEM_PROPERTIES="$SYSTEM_PROPERTIES -Djlaunchpad.home=$JLAUNCHPAD_HOME"
SYSTEM_PROPERTIES="$SYSTEM_PROPERTIES -Drepository.home=$REPOSITORY_HOME"

PROPERTIES1="-deps.file.name=$REPOSITORY_HOME/org/sf/jlaunchpad/jlaunchpad-launcher/$JLAUNCHPAD_VERSION/jlaunchpad-launcher-$JLAUNCHPAD_VERSION.pom"
PROPERTIES1="$PROPERTIES1 -main.class.name=org.sf.pomreader.ProjectInstaller"

# Install antrun project

$JLAUNCHPAD_HOME/jlaunchpad.sh $SYSTEM_PROPERTIES $PROPERTIES1 -Dbasedir=projects/antrun -Dbuild.required=false

# Install scriptlandia-installer project

$JLAUNCHPAD_HOME/jlaunchpad.sh $SYSTEM_PROPERTIES $PROPERTIES1 -Dbasedir=projects/scriptlandia-installer -Dbuild.required=false

# Execute scriptlandia-installer project

# SET MAIN_CLASS=org.sf.scriptlandia.install.CoreInstaller
MAIN_CLASS=org.sf.scriptlandia.install.GuiInstaller

PROPERTIES2="-deps.file.name=projects/scriptlandia-installer/pom.xml"
PROPERTIES2="$PROPERTIES2 -main.class.name=$MAIN_CLASS"

$JLAUNCHPAD_HOME/jlaunchpad.sh $SYSTEM_PROPERTIES $PROPERTIES2 -wait
