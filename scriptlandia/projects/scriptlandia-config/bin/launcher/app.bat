@echo off

REM see https://java-app-launcher.dev.java.net
REM JavaAppLauncher: Generic Java Application Launcher
REM Copyright (C) 2007  Santhosh Kumar T
REM 
REM This library is free software; you can redistribute it and/or
REM modify it under the terms of the GNU Lesser General Public
REM License as published by the Free Software Foundation; either
REM version 2.1 of the License, or (at your option) any later version.
REM 
REM This library is distributed in the hope that it will be useful,
REM but WITHOUT ANY WARRANTY; without even the implied warranty of
REM MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
REM Lesser General Public License for more details.

if defined APP goto conf
echo APP variable is not defined
pause
goto end

:conf
set CMD=java.exe
if defined JAVA_HOME set CMD="%JAVA_HOME%\bin\%CMD%"
if defined JAVA_CMD set CMD="%JAVA_CMD%"
set JAVA_CMD=

SET JAVA_CLASSPATH=
SET JAVA_ENDORSED_DIRS=
SET JAVA_EXT_DIRS=
SET JAVA_LIBRARY_PATH=
SET JAVA_SYSTEM_PROPS=
SET JAVA_BOOTCLASSPATH=
SET JAVA_BOOTCLASSPATH_PREPEND=
SET JAVA_BOOTCLASSPATH_APPEND=
SET JVM_ARGS=

SET PARAMETERS=

call "%~p0"processArgs.bat %*

set SECTION=
set RESULT=
FOR /F "usebackq delims=" %%i in ("%APP%.conf") DO call :processline "%%i"

IF EXIST %CD%\%APP_NAME%.conf FOR /F "usebackq delims=" %%i in ("%CD%\%APP_NAME%.conf") DO call :processline "%%i"

rem append result to command
if DEFINED RESULT call :processresult


call "%~p0"customizeExecution.bat %*

if not defined PROCEED goto end

%CMD% %JAVA_BOOTCLASSPATH_APPEND% %JAVA_BOOTCLASSPATH_PREPEND% %JAVA_BOOTCLASSPATH% %JAVA_LIBRARY_PATH% %JAVA_EXT_DIRS% %JAVA_ENDORSED_DIRS% %JAVA_SYSTEM_PROPS% %JAVA_CLASSPATH% %JVM_ARGS% %CMD_LINE_ARGS% 

goto end

:processline
if %1 == "<java.classpath>" goto option
if %1 == "<java.endorsed.dirs>" goto option
if %1 == "<java.ext.dirs>" goto option
if %1 == "<java.library.path>" goto option
if %1 == "<java.system.props>" goto option
if %1 == "<java.bootclasspath>" goto option
if %1 == "<java.bootclasspath.append>" goto option
if %1 == "<java.bootclasspath.prepend>" goto option
if %1 == "<jvm.args>" goto option

rem ignore if line is comment
SET LINE=%1
SET FIRST_CHAR=%LINE:~1,1%
IF "%FIRST_CHAR%" == "#" goto end

rem join the line to result
if defined RESULT set RESULT=%RESULT%%SEPARATOR%
set RESULT=%RESULT%%PREFIX%%~1
goto end

:option

rem append result to command
if DEFINED RESULT call :processresult

set OPTION=%1
set SECTION=%OPTION:~2,-2%
goto %SECTION%

:java.classpath
set VARIABLE_NAME="JAVA_CLASSPATH"
set RESULT=
set SECTION_PREFIX=-classpath 
set PREFIX=
set SEPARATOR=;
goto end

:java.endorsed.dirs
set VARIABLE_NAME="JAVA_ENDORSED_DIRS"
set RESULT=
set SECTION_PREFIX=-Djava.endorsed.dirs=
set PREFIX=
set SEPARATOR=;
goto end

:java.ext.dirs
set VARIABLE_NAME="JAVA_EXT_DIRS"
set RESULT=
set SECTION_PREFIX=-Djava.ext.dirs=
set PREFIX=
set SEPARATOR=;
goto end

:java.library.path
set VARIABLE_NAME="JAVA_LIBRARY_PATH"
set RESULT=
set SECTION_PREFIX=-Djava.library.path=
set PREFIX=
set SEPARATOR=;
goto end

:java.system.props
set VARIABLE_NAME="JAVA_SYSTEM_PROPS"
set RESULT=
set SECTION_PREFIX=
set PREFIX=-D
set SEPARATOR= 
goto end

:java.bootclasspath
set VARIABLE_NAME="JAVA_BOOTCLASSPATH"
set RESULT=
set SECTION_PREFIX=-Xbootclasspath:
set PREFIX=
set SEPARATOR=;
goto end

:java.bootclasspath.prepend
set VARIABLE_NAME="JAVA_BOOTCLASSPATH_PREPEND"
set RESULT=
set SECTION_PREFIX=-Xbootclasspath/p:
set PREFIX=
set SEPARATOR=;
goto end

:java.bootclasspath.append
set VARIABLE_NAME="JAVA_BOOTCLASSPATH_APPEND"
set RESULT=
set SECTION_PREFIX=-Xbootclasspath/a:
set PREFIX=
set SEPARATOR=;
goto end

:jvm.args
set VARIABLE_NAME="JVM_ARGS"
set RESULT=
set SECTION_PREFIX=
set PREFIX=
set SEPARATOR= 
goto end

:processresult
if %VARIABLE_NAME% == "JAVA_CLASSPATH" if "%JAVA_CLASSPATH%" == "" (set JAVA_CLASSPATH=%SECTION_PREFIX%%RESULT%) else set JAVA_CLASSPATH=%JAVA_CLASSPATH%%SEPARATOR%%RESULT%
if %VARIABLE_NAME% == "JAVA_ENDORSED_DIRS" if "%JAVA_ENDORSED_DIRS%" == "" (set JAVA_ENDORSED_DIRS=%SECTION_PREFIX%%RESULT%) else set JAVA_ENDORSED_DIRS=%JAVA_ENDORSED_DIRS%%SEPARATOR%%RESULT%
if %VARIABLE_NAME% == "JAVA_EXT_DIRS" if "%JAVA_EXT_DIRS%" == "" (set JAVA_EXT_DIRS=%SECTION_PREFIX%%RESULT%) else set JAVA_EXT_DIRS=%JAVA_EXT_DIRS%%SEPARATOR%%RESULT%
if %VARIABLE_NAME% == "JAVA_LIBRARY_PATH" if "%JAVA_LIBRARY_PATH%" == "" (set JAVA_LIBRARY_PATH=%SECTION_PREFIX%%RESULT%) else set JAVA_LIBRARY_PATH=%JAVA_LIBRARY_PATH%%SEPARATOR%%RESULT%
if %VARIABLE_NAME% == "JAVA_SYSTEM_PROPS" if "%JAVA_SYSTEM_PROPS%" == "" (set JAVA_SYSTEM_PROPS=%RESULT%) else set JAVA_SYSTEM_PROPS=%JAVA_SYSTEM_PROPS%%SEPARATOR%%RESULT%
if %VARIABLE_NAME% == "JAVA_BOOTCLASSPATH" if "%JAVA_BOOTCLASSPATH%" == "" (set JAVA_BOOTCLASSPATH=%SECTION_PREFIX%%RESULT%) else set JAVA_BOOTCLASSPATH=%JAVA_BOOTCLASSPATH%%SEPARATOR%%RESULT%
if %VARIABLE_NAME% == "JAVA_BOOTCLASSPATH_PREPEND" if "%JAVA_BOOTCLASSPATH_PREPEND%" == "" (set JAVA_BOOTCLASSPATH_PREPEND=%SECTION_PREFIX%%RESULT%) else set JAVA_BOOTCLASSPATH_PREPEND=%JAVA_BOOTCLASSPATH_PREPEND%%SEPARATOR%%RESULT%
if %VARIABLE_NAME% == "JAVA_BOOTCLASSPATH_APPEND" if "%JAVA_BOOTCLASSPATH_APPEND%" == "" (set JAVA_BOOTCLASSPATH_APPEND=%SECTION_PREFIX%%RESULT%) else set JAVA_BOOTCLASSPATH_APPEND=%JAVA_BOOTCLASSPATH_APPEND%%SEPARATOR%%RESULT%
if %VARIABLE_NAME% == "JVM_ARGS" if "%JVM_ARGS%" == "" (set JVM_ARGS=%SECTION_PREFIX%%RESULT%) else set JVM_ARGS=%JVM_ARGS%%SEPARATOR%%RESULT%


:end
