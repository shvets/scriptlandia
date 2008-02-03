#!/bin/sh

. ./config.sh

. ./clean.sh

. ./build.sh

. ./$JLAUNCHPAD_HOME/jlaunchpad.sh \
  "-deps.file.name=languages/ant/core/pom.xml" \
  "-deps.file.name=languages/beanshell/core/pom.xml" \
  "-deps.file.name=languages/maven/core/pom.xml" \
  "-main.class.name=org.apache.tools.ant.Main" -f build.xml assembly


