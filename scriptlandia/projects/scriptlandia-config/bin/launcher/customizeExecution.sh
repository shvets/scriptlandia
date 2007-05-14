#!/bin/sh

if [ "$NAILGUN_MODE" = "true" ]; then
  $NAILGUN $LAUNCHER_CLASS $CMD_LINE_ARGS -ng
else
  export CMD_LINE_ARGS=$*
  export PROCEED=true
fi

