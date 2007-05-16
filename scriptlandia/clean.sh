#!/bin/sh

rm -d -r -f -v projects/bootstrap-mini/target
rm -d -r -f projects/nailgun/target
rm -d -r -f projects/pomreader/target
rm -d -r -f projects/scriptlandia-common/target
rm -d -r -f projects/antrun\target
rm -d -r -f Cprojects/classworlds-launcher/target

rm -d -r -f projects/scriptlandia-launcher/target
rm -d -r -f projects/scriptlandia-nailgun/target
rm -d -r -f projects/scriptlandia-installer/target
rm -d -r -f projects/scriptlandia-config/target
rm -d -r -f projects/scriptlandia-startup/target
rm -d -r -f projects/scriptlandia/target

rm -d -r -f target
rm -d -r -f classes

mvn -f pom.xml clean