#!/bin/sh

rm -d -r -f projects/antrun\target
rm -d -r -f Cprojects/classworlds-launcher/target
rm -d -r -f projects/scriptlandia-helper/target
rm -d -r -f projects/scriptlandia-installer/target
rm -d -r -f projects/scriptlandia-launcher/target
rm -d -r -f projects/scriptlandia-nailgun/target
rm -d -r -f projects/scriptlandia/target

rm -d -r -f target
rm -d -r -f classes

/media/hda5/scriptlandia/mvn.sh -f pom.xml clean