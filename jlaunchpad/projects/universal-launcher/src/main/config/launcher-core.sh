#! /bin/sh

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
 
join() {
    if [ -n "$line" ] # skip lines of zero length
    then
        if [ `echo $line | cut -c 1` = '#' ]; then
            return # ignore lines starting with '#'
        fi
        line=`echo $line | sed 's/\(%\)\([a-zA-Z0-9_]*\)\(\%\)/\$\2/g'`
        line=`eval echo "$line"` # evaluate environment variables used, if any
        if [ -n "$RESULT" ]; then
            RESULT=$RESULT$SEPARATOR
        fi
        RESULT=$RESULT$PREFIX$line
    fi
}

processresult() {
    if [ -n "$RESULT" ]; then
        if [ "$SECTION" = "<java.ext.dirs>" ]; then
            if [ -n "$JAVA_HOME" ]; then
                RESULT="$JAVA_HOME/lib/ext":"$JAVA_HOME/jre/lib/ext":$RESULT
            fi
        fi
        CMD=$CMD' '$SECTION_PREFIX$RESULT
    fi
    SECTION=$line
}

processline() {
    case "$line" in
        '<java.classpath>')
            processresult
            RESULT="";
            SECTION_PREFIX="-classpath "
            PREFIX=""
            SEPARATOR=":"
            ;;
        '<java.endorsed.dirs>')
            processresult
            RESULT="";
            SECTION_PREFIX="-Djava.endorsed.dirs="
            PREFIX=""
            SEPARATOR=":"
            ;;
        '<java.ext.dirs>')
            processresult
            RESULT="";
            SECTION_PREFIX="-Djava.ext.dirs="
            PREFIX=""
            SEPARATOR=":"
            ;;
        '<java.library.path>')
            processresult
            RESULT="";
            SECTION_PREFIX="-Djava.library.path="
            PREFIX=""
            SEPARATOR=":"
            ;;
        '<java.system.props>')
            processresult
            RESULT="";
            SECTION_PREFIX=""
            PREFIX="-D"
            SEPARATOR=" "
            ;;
        '<java.bootclasspath>')
            processresult
            RESULT="";
            SECTION_PREFIX="-Xbootclasspath:"
            PREFIX=""
            SEPARATOR=":"
            ;;
        '<java.bootclasspath.prepend>')
            processresult
            RESULT="";
            SECTION_PREFIX="-Xbootclasspath/p:"
            PREFIX=""
            SEPARATOR=":"
            ;;
        '<java.bootclasspath.append>')
            processresult
            RESULT="";
            SECTION_PREFIX="-Xbootclasspath/a:"
            PREFIX=""
            SEPARATOR=":"
            ;;
        '<jvm.args>')
            processresult
            CMD=$CMD' '-DSCRIPT_FILE=`basename $0`
            RESULT="";
            SECTION_PREFIX=""
            PREFIX=""
            SEPARATOR=" "
            ;;
        *)
            join
    esac         
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

APP=`dirname $0`/`basename $0 .sh` # compute app name from this file name without prefix

if [ -n "$JAVA_CMD" ]
then
    CMD="$JAVA_CMD"
elif [ -n "$JAVA_HOME" ] # is JAVA_HOME defined
then
    CMD="$JAVA_HOME/bin/java"
else
    CMD=java
fi

FILE=$APP'.conf'
if [ -r "$FILE" ]; then
    readFile
    $CMD $*
else
    echo $FILE not found
fi
