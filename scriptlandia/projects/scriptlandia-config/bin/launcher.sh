#! /bin/sh

export JAVA_HOME=@java.home@
export MOBILE_JAVA_HOME=@mobile.java.home@

export NAILGUN=@repository.home@/com/martiansoftware/nailgun-bin/@nailgun.version@/ng

APP=%~nx0
export APP=@scriptlandia.home@/%APP:~0,-4%

. @scriptlandia.home@/launcher/app.bat $*
