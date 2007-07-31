#!/bin/sh

# JavaAppLauncher: Generic Java Application Launcher
# Copyright (C) 2007  Santhosh Kumar T
# 
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License as published by the Free Software Foundation; either
# version 2.1 of the License, or (at your option) any later version.
# 
# This library is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# Lesser General Public License for more details.

readCommandLine() {
  for arg in $*; do
    processArg $arg
  done
}

processArg() {
  arg=$1

  if [ "$arg" = "" ]; then
    return
  fi

  if [ ${#arg} -lt 2 ]; then
    return
  fi

  PARAM1=${arg:0:2}
  PARAM2=${arg:0:18}
  PARAM3=${arg:0:19}

  if [ "$PARAM1" = "-D" ]; then
    JAVA_SYSTEM_PROPS="$JAVA_SYSTEM_PROPS $arg"
  elif [ "$PARAM2" = "-Xbootclasspath/p:" ]; then
    JAVA_BOOTCLASSPATH_PREPEND="$JAVA_BOOTCLASSPATH_PREPEND $arg"
  elif [ "$PARAM2" = "-Xbootclasspath/a:" ]; then
    JAVA_BOOTCLASSPATH_APPEND="$JAVA_BOOTCLASSPATH_APPEND $arg"
  elif [ "$arg" = "-debug" ]; then
    JAVA_SYSTEM_PROPS="$JAVA_SYSTEM_PROPS -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=6006"
  elif [ "$PARAM3" = "-Djava.library.path" ]; then
    JAVA_LIBRARY_PATH="$JAVA_LIBRARY_PATH $arg"
  else
    COMMAND_LINE_ARGS="$COMMAND_LINE_ARGS $arg"
  fi
}

readFile() {
    SECTION=""
    RESULT=""
    line=""

    if [ -n "`tail -1 $FILE`" ]; then
        echo>>$FILE
    fi

    temp_dir=`date "+%d%m%y%H%M%S%N"`
    mkdir $temp_dir
    # in solaris bash shell, while loop spawns a new subshell
    # http://www.kilala.nl/Sysadmin/script-variablescope.php
    # so using temporary file read/write to gain access to
    # changes in variables from while loop
    while read line
    do
        processline
        echo "$RESULT">$temp_dir/RESULT
        echo "$SEPARATOR">$temp_dir/SEPARATOR
        echo "$line">$temp_dir/line
        echo "$SECTION">$temp_dir/SECTION
        echo "$SECTION_PREFIX">$temp_dir/SECTION_PREFIX
        echo "$PREFIX">$temp_dir/PREFIX
        echo "$CMD">$temp_dir/CMD
    done < $FILE

    RESULT=`cat $temp_dir/RESULT`
    SEPARATOR=`cat $temp_dir/SEPARATOR`
    line=`cat $temp_dir/line`
    SECTION=`cat $temp_dir/SECTION`
    PREFIX=`cat $temp_dir/PREFIX`
    SECTION_PREFIX=`cat $temp_dir/SECTION_PREFIX`
    CMD=`cat $temp_dir/CMD`
    rm -rf $temp_dir
    #processline
    processresult
}

processline() {
    case "$line" in
        '<java.classpath>')
            processresult
            VARIABLE_NAME="JAVA_CLASSPATH"
            VARIABLE_VALUE="$JAVA_CLASSPATH"
            RESULT="";
            SECTION_PREFIX="-classpath "
            PREFIX=""
            SEPARATOR=":"
            ;;
        '<java.endorsed.dirs>')
            processresult
            VARIABLE_NAME="JAVA_ENDORSED_DIRS"
            VARIABLE_VALUE="$JAVA_ENDORSED_DIRS"
            RESULT="";
            SECTION_PREFIX="-Djava.endorsed.dirs="
            PREFIX=""
            SEPARATOR=":"
            ;;
        '<java.ext.dirs>')
            processresult
            VARIABLE_NAME="JAVA_EXT_DIRS"
            VARIABLE_VALUE="$JAVA_EXT_DIRS"
            RESULT="";
            SECTION_PREFIX="-Djava.ext.dirs="
            PREFIX=""
            SEPARATOR=":"
            ;;
        '<java.library.path>')
            processresult
            VARIABLE_NAME="JAVA_LIBRARY_PATH"
            VARIABLE_VALUE="$JAVA_LIBRARY_PATH"
            RESULT="";
            SECTION_PREFIX="-Djava.library.path="
            PREFIX=""
            SEPARATOR=":"
            ;;
        '<java.system.props>')
            processresult
            VARIABLE_NAME="JAVA_SYSTEM_PROPS"
            VARIABLE_VALUE="$JAVA_SYSTEM_PROPS"
            RESULT="";
            SECTION_PREFIX=""
            PREFIX="-D"
            SEPARATOR=" "
            ;;
        '<java.bootclasspath>')
            processresult
            VARIABLE_NAME="$JAVA_BOOTCLASSPATH"
            VARIABLE_VALUE="$JAVA_BOOTCLASSPATH"
            RESULT="";
            SECTION_PREFIX="-Xbootclasspath:"
            PREFIX=""
            SEPARATOR=":"
            ;;
        '<java.bootclasspath.prepend>')
            processresult
            VARIABLE_NAME="JAVA_BOOTCLASSPATH_PREPEND"
            VARIABLE_VALUE="$JAVA_BOOTCLASSPATH_PREPEND"
            RESULT="";
            SECTION_PREFIX="-Xbootclasspath/p:"
            PREFIX=""
            SEPARATOR=":"
            ;;
        '<java.bootclasspath.append>')
            processresult
            VARIABLE_NAME="JAVA_BOOTCLASSPATH_APPEND"
            VARIABLE_VALUE="$JAVA_BOOTCLASSPATH_APPEND"
            RESULT="";
            SECTION_PREFIX="-Xbootclasspath/a:"
            PREFIX=""
            SEPARATOR=":"
            ;;
        '<jvm.args>')
            processresult
            VARIABLE_NAME="JVM_ARGS"
            VARIABLE_VALUE="$JVM_ARGS"
            CMD=$CMD' '-DSCRIPT_FILE=`basename $0`
            RESULT="";
            SECTION_PREFIX=""
            PREFIX=""
            SEPARATOR=" "
            ;;

       '<launcher.class>')
            processresult
            VARIABLE_NAME="LAUNCHER_CLASS"
            VARIABLE_VALUE="$LAUNCHER_CLASS"
            LAUNCHER_CLASS=
            RESULT="";
            SECTION_PREFIX=""
            PREFIX=""
            SEPARATOR=" "
            ;;

       '<set.variables>')
            processresult
            VARIABLE_NAME="SET_VARIABLES"
            VARIABLE_VALUE="$SET_VARIABLES"
            RESULT="";
            SECTION_PREFIX=""
            PREFIX="SET"
            SEPARATOR=" "
            ;;

      '<command.line.args>')
            processresult
            VARIABLE_NAME="COMMAND_LINE_ARGS"
            VARIABLE_VALUE="$COMMAND_LINE_ARGS"
            RESULT="";
            SECTION_PREFIX=""
            PREFIX=""
            SEPARATOR=" "
            ;;
        *)
            join
    esac
}

processresult() {
#    if [ -n "$RESULT" ]; then
#         CMD=$CMD' '$SECTION_PREFIX$RESULT
#    fi

  if [ "$VARIABLE_NAME" != "" ]; then

 if [ "$VARIABLE_NAME" ]; then
    eval "$VARIABLE_NAME=\"$SECTION_PREFIX$RESULT\""
  else
    eval "$VARIABLE_NAME=\"$VARIABLE_VALUE$SEPARATOR$RESULT\""
  fi
  fi

  SECTION=$line
}

join() {
    if [ -n "$line" ] # skip lines of zero length
    then
      if [ `echo $line | cut -c 1` = '#' ]; then
        return # ignore lines starting with '#'
      fi

       if [ "$PREFIX" = "SET" ]; then
          `eval echo "$line"`
        else
          line=`echo $line | sed 's/\(%\)\([a-zA-Z0-9_]*\)\(\%\)/\$\2/g'`
          line=`eval echo "$line"` # evaluate environment variables used, if any
          if [ -n "$RESULT" ]; then
            RESULT=$RESULT$SEPARATOR
          fi
          RESULT=$RESULT$PREFIX$line
        fi
    fi
}


#APP=`dirname "$0"`

#export APP=`cd "$APP" && pwd`

APP=`dirname $0`/`basename $0 .sh` # compute app name from this file name without prefix
APP_NAME=`basename $0 .sh`

JAVA_HOME="@java.home.internal@"

if [ -n "$JAVA_CMD" ]
then
    CMD="$JAVA_CMD"
elif [ -n "$JAVA_HOME" ] # is JAVA_HOME defined
then
    CMD="$JAVA_HOME/bin/java"
else
    CMD=java
fi

JAVA_CLASSPATH=
JAVA_ENDORSED_DIRS=
JAVA_EXT_DIRS=
JAVA_LIBRARY_PATH=
JAVA_SYSTEM_PROPS=
JAVA_BOOTCLASSPATH=
JAVA_BOOTCLASSPATH_PREPEND=
JAVA_BOOTCLASSPATH_APPEND=
JVM_ARGS=
LAUNCHER_CLASS=
COMMAND_LINE_ARGS=

readCommandLine $*

FILE="`pwd`/$APP_NAME.conf"

if [ "$APP.conf -ne $FILE" ]; then
  if [ -r "$FILE" ]; then
    readFile
  fi
fi

FILE=$APP'.conf'

if [ -r "$FILE" ]; then
  readFile
else
    echo $FILE not found
fi

echo $CMD \
  $JAVA_BOOTCLASSPATH_APPEND $JAVA_BOOTCLASSPATH_PREPEND $JAVA_BOOTCLASSPATH \
  $JAVA_LIBRARY_PATH $JAVA_EXT_DIRS $JAVA_ENDORSED_DIRS \
  $JVM_ARGS \
  $JAVA_SYSTEM_PROPS \
  $JAVA_CLASSPATH \
  $LAUNCHER_CLASS \
  $COMMAND_LINE_ARGS
