#!/bin/sh

. ../../../jlaunchpad/trunk/launcher/config.sh

rm -d -r -f projects/antrun\target
rm -d -r -f projects/scriptlandia-helper/target
rm -d -r -f projects/scriptlandia-installer/target
rm -d -r -f projects/scriptlandia-launcher/target
rm -d -r -f projects/scriptlandia-nailgun/target
rm -d -r -f projects/scriptlandia/target

rm -d -r -f target
rm -d -r -f classes

$LAUNCHER_HOME/launcher.sh "-deps.file.name=languages/maven/core/pom.xml" "-main.class.name=org.apache.maven.cli.MavenCli" -f pom.xml clean
