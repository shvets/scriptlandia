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
                RESULT=$JAVA_HOME/lib/ext:$JAVA_HOME/jre/lib/ext:$RESULT
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
    while read line
    do
        processline
    done < $FILE 
    processline
    processresult
}

. `dirname "$0"`/processArgs.bat $*

. `dirname "$0"`/customizeExecution.sh $*

APP=`basename $0 .sh` # compute app name from this file name without prefix

if [ $JAVA_HOME ] # is JAVA_HOME defined
then
    CMD=$JAVA_HOME/bin/java
else
    CMD=java
fi

CMD=$CMD' '-DPID=$$

FILE=$APP'.conf'
if [ -r $FILE ]; then
    readFile
    exec $CMD $*
else
    echo $FILE not found
fi
