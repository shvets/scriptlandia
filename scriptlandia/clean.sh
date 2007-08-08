#!/bin/sh

rm -d -r -f projects/antrun\target
rm -d -r -f projects/scriptlandia-helper/target
rm -d -r -f projects/scriptlandia-installer/target
rm -d -r -f projects/scriptlandia-launcher/target
rm -d -r -f projects/scriptlandia-nailgun/target
rm -d -r -f projects/scriptlandia/target

rm -d -r -f target
rm -d -r -f classes

SCRIPTLANDIA_HOME=c:/scriptlandia

$SCRIPTLANDIA_HOME/mvn.sh -f pom.xml clean