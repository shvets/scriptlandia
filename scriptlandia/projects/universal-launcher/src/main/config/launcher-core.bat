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
SET CMD_LINE_ARGS=%*
IF NOT CMDEXTVERSION 2 SET CMD_LINE_ARGS=%CMD_LINE_ARGS:~1%

rem set CMD=java.exe
if not defined CMD set CMD=java.exe
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
SET LAUNCHER_CLASS=
SET COMMAND_LINE_ARGS=

SET PARAMETERS=


call "%~p0"processArgs.bat %CMD_LINE_ARGS%

set SECTION=
set RESULT=

FOR /F "usebackq delims=" %%i in ("%APP%.conf") DO call :processline  ^"%%i^"

if "%APP%.conf" == "%CD%\%APP_NAME%.conf" goto step1

IF EXIST %CD%\%APP_NAME%.conf FOR /F "usebackq delims=" %%i in ("%CD%\%APP_NAME%.conf") DO call :processline ^"%%i^"

:step1

rem append result to command
if DEFINED RESULT call :processresult

if "%JAVA_CLASSPATH%" == "" goto execute

SET JAVA_CLASSPATH=-classpath "%JAVA_CLASSPATH%"

if "%JAVA_BOOTCLASSPATH_PREPEND%" == "" goto execute

SET JAVA_BOOTCLASSPATH_PREPEND=-Xbootclasspath/p:"%JAVA_BOOTCLASSPATH_PREPEND%"

if "%JAVA_BOOTCLASSPATH_APPPEND%" == "" goto execute

SET JAVA_BOOTCLASSPATH_APPPEND=-Xbootclasspath/a:"%JAVA_BOOTCLASSPATH_APPPEND%"


:execute
%CMD% %JAVA_BOOTCLASSPATH_APPEND% %JAVA_BOOTCLASSPATH_PREPEND% %JAVA_BOOTCLASSPATH% %JAVA_LIBRARY_PATH% %JAVA_EXT_DIRS% %JAVA_ENDORSED_DIRS% %JAVA_SYSTEM_PROPS% %JVM_ARGS% %JAVA_CLASSPATH% %LAUNCHER_CLASS% %COMMAND_LINE_ARGS% %CMD_LINE_ARGS% 

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
if %1 == "<launcher.class>" goto option
if %1 == "<set.variables>" goto option
if %1 == "<command.line.args>" goto option

rem ignore if line is comment
SET LINE=%1
SET FIRST_CHAR=%LINE:~1,1%
IF "%FIRST_CHAR%" == "#" goto end

rem join the line to result
if defined RESULT set RESULT=%RESULT%%SEPARATOR%

if "%PREFIX%" == "-D" goto setupJavaSystemProperty
if "%PREFIX%" == "SET" goto setupScriptVar

  set RESULT=%RESULT%%PREFIX%%~1
goto endSetupVar

:setupJavaSystemProperty
  set RESULT=%RESULT%%PREFIX%"%~1"
goto endSetupVar

:setupScriptVar
set %~1

:endSetupVar

goto end

:option

rem append result to command
if DEFINED RESULT call :processresult

set OPTION=%1
set SECTION=%OPTION:~2,-2%
goto %SECTION%

:java.classpath
set VARIABLE_NAME=JAVA_CLASSPATH
set VARIABLE_VALUE=%JAVA_CLASSPATH%
set RESULT=
rem set SECTION_PREFIX=-classpath
set SECTION_PREFIX=
set PREFIX=
set SEPARATOR=;
goto end

:java.endorsed.dirs
set VARIABLE_NAME=JAVA_ENDORSED_DIRS
set VARIABLE_VALUE=%JAVA_ENDORSED_DIRS%
set RESULT=
set SECTION_PREFIX=-Djava.endorsed.dirs=
set PREFIX=
set SEPARATOR=;
goto end

:java.ext.dirs
set VARIABLE_NAME=JAVA_EXT_DIRS
set VARIABLE_VALUE=%JAVA_EXT_DIRS%
set RESULT=
set SECTION_PREFIX=-Djava.ext.dirs=
set PREFIX=
set SEPARATOR=;
goto end

:java.library.path
set VARIABLE_NAME=JAVA_LIBRARY_PATH
set VARIABLE_VALUE=%JAVA_LIBRARY_PATH%
set RESULT=
set SECTION_PREFIX=-Djava.library.path=
set PREFIX=
set SEPARATOR=;
goto end

:java.system.props
set VARIABLE_NAME=JAVA_SYSTEM_PROPS
set VARIABLE_VALUE=%JAVA_SYSTEM_PROPS%
set RESULT=
set SECTION_PREFIX=
set PREFIX=-D
set SEPARATOR= 
goto end

:java.bootclasspath
set VARIABLE_NAME=JAVA_BOOTCLASSPATH
set VARIABLE_VALUE=%JAVA_BOOTCLASSPATH%
set RESULT=
set SECTION_PREFIX=-Xbootclasspath:
set PREFIX=
set SEPARATOR=;
goto end

:java.bootclasspath.prepend
set VARIABLE_NAME=JAVA_BOOTCLASSPATH_PREPEND
set VARIABLE_VALUE=%JAVA_BOOTCLASSPATH_PREPEND%
set RESULT=
rem set SECTION_PREFIX=-Xbootclasspath/p:
set SECTION_PREFIX=
set PREFIX=
set SEPARATOR=;
goto end

:java.bootclasspath.append
set VARIABLE_NAME=JAVA_BOOTCLASSPATH_APPEND
set VARIABLE_VALUE=%JAVA_BOOTCLASSPATH_APPEND%
set RESULT=
rem set SECTION_PREFIX=-Xbootclasspath/a:
set SECTION_PREFIX=
set PREFIX=
set SEPARATOR=;
goto end

:jvm.args
set VARIABLE_NAME=JVM_ARGS
set VARIABLE_VALUE=%JVM_ARGS%
SET JVM_ARGS=
set RESULT=
set SECTION_PREFIX=
set PREFIX=
set SEPARATOR= 
goto end

:launcher.class
set VARIABLE_NAME=LAUNCHER_CLASS
set VARIABLE_VALUE=%LAUNCHER_CLASS%
SET LAUNCHER_CLASS=
set RESULT=
set SECTION_PREFIX=
set PREFIX=
set SEPARATOR= 
goto end

:set.variables
set VARIABLE_NAME=SET_VARIABLES
set VARIABLE_VALUE=%SET_VARIABLES%
set RESULT=
set SECTION_PREFIX=
set PREFIX=SET
set SEPARATOR= 
goto end

:command.line.args
set VARIABLE_NAME=COMMAND_LINE_ARGS
set VARIABLE_VALUE=%COMMAND_LINE_ARGS%
set RESULT=
set SECTION_PREFIX=
set PREFIX=
set SEPARATOR= 
goto end

:processresult

if not defined %VARIABLE_NAME% goto setupVariable
  set %VARIABLE_NAME%=%VARIABLE_VALUE%%SEPARATOR%%RESULT%
goto endSetupVariable
:setupVariable
  set %VARIABLE_NAME%=%SECTION_PREFIX%%RESULT%
:endSetupVariable

:end
