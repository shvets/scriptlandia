#! /bin/sh

CMD_LINE_ARGS=""
PARAMETERS=""
NAILGUN_MODE=false

#. Process bootclasspath, command line arguments
processArgs() {
  while [ "x$1" != "x" ]; do
    temp=$1
#    param=`${temp:0:2}`
param=""

    if [ "$param" = "-D" ]; then
      PARAMETERS="$PARAMETERS $1"
    elif [ "$param" = "-X" ]; then
      PARAMETERS="$PARAMETERS $1"
    elif [ "$temp" = "-debug" ]; then
      PARAMETERS="$PARAMETERS -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=6006"
    fi

#    param=`${temp:0:4}`
param=""

    if [ "$param" = "-ng" ]; then
      NAILGUN_MODE=true
    fi

#    param=`${temp:0:19}`
param=""

    if [ "$param" = "-Djava.library.path" ]; then
      PARAMETERS="$PARAMETERS $1"
    fi

    CMD_LINE_ARGS="$CMD_LINE_ARGS $1"

    shift
  done
}

processArgs "$@"

export CMD_LINE_ARGS PARAMETERS NAILGUN_MODE
