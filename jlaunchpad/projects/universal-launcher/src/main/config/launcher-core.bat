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

set JAVA_HOME=@java.home.internal@

if not defined CMD set CMD=java.exe
if defined JAVA_HOME set CMD="%JAVA_HOME%\bin\%CMD%"
if defined JAVA_CMD set CMD="%JAVA_CMD%"
set JAVA_CMD=

set LAUNCHER_APP=%LAUNCHER_HOME%\launcher
set APP=%CD%\%APP_NAME%


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

rem process command line

FOR %%i in (%*) DO call :processarg ^%%i^

set SECTION=
set RESULT=

rem process config file located in $launcher.home

FOR /F "usebackq delims=" %%i in ("%LAUNCHER_APP%.conf") DO call :processline  ^"%%i^"

rem process config file located in $current.dir

if not "%LAUNCHER_APP%.conf" == "%APP%.conf" (
  if exist %APP%.conf FOR /F "usebackq delims=" %%i in ("%APP%.conf") DO call :processline ^"%%i^"
)


if not "%JAVA_CLASSPATH%" == "" (
  SET JAVA_CLASSPATH=-classpath "%JAVA_CLASSPATH%"
)

if not "%JAVA_BOOTCLASSPATH_PREPEND%" == "" (
  SET JAVA_BOOTCLASSPATH_PREPEND=-Xbootclasspath/p:"%JAVA_BOOTCLASSPATH_PREPEND%"
)

if not "%JAVA_BOOTCLASSPATH_APPPEND%" == "" (
  SET JAVA_BOOTCLASSPATH_APPPEND=-Xbootclasspath/a:"%JAVA_BOOTCLASSPATH_APPPEND%"
)

if not "%JAVA_ENDORSED_DIRS%" == "" (
  SET JAVA_ENDORSED_DIRS=-Djava.endorsed.dirs="%JAVA_ENDORSED_DIRS%"
)

if not "%JAVA_EXT_DIRS" == "" (
  SET JAVA_EXT_DIRS=-Djava.ext.dirs="%JAVA_EXT_DIRS%"
)

if not "%JAVA_LIBRARY_PATH" == "" (
  SET JAVA_LIBRARY_PATH=-Djava.library.path="%JAVA_LIBRARY_PATH%"
)

%CMD% ^
  %JAVA_BOOTCLASSPATH_APPEND% %JAVA_BOOTCLASSPATH_PREPEND% %JAVA_BOOTCLASSPATH% ^
  %JAVA_LIBRARY_PATH% %JAVA_EXT_DIRS% %JAVA_ENDORSED_DIRS% ^
  %JVM_ARGS% ^
  %JAVA_SYSTEM_PROPS% ^
  %JAVA_CLASSPATH% ^
  %LAUNCHER_CLASS% ^
  %COMMAND_LINE_ARGS%

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

if "%VARIABLE_NAME%" == "JAVA_SYSTEM_PROPS" (
  set RESULT=%RESULT%-D"%~1%~2"
  goto end
)

if "%VARIABLE_NAME%" == "SET_VARIABLES" (
  set %~1
  goto end
)

set RESULT=%RESULT%%~1
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
rem set SECTION_PREFIX=
set PREFIX=
set SEPARATOR=;
goto end

:java.endorsed.dirs
set VARIABLE_NAME=JAVA_ENDORSED_DIRS
set VARIABLE_VALUE=%JAVA_ENDORSED_DIRS%
set RESULT=
rem set SECTION_PREFIX=-Djava.endorsed.dirs=
rem set PREFIX=
set SEPARATOR=;
goto end

:java.ext.dirs
set VARIABLE_NAME=JAVA_EXT_DIRS
set VARIABLE_VALUE=%JAVA_EXT_DIRS%
set RESULT=
rem set SECTION_PREFIX=-Djava.ext.dirs=
rem set PREFIX=
set SEPARATOR=;
goto end

:java.library.path
set VARIABLE_NAME=JAVA_LIBRARY_PATH
set VARIABLE_VALUE=%JAVA_LIBRARY_PATH%
set RESULT=
rem set SECTION_PREFIX=-Djava.library.path=
rem set PREFIX=
set SEPARATOR=;
goto end

:java.system.props
set VARIABLE_NAME=JAVA_SYSTEM_PROPS
set VARIABLE_VALUE=%JAVA_SYSTEM_PROPS%
set RESULT=
rem set SECTION_PREFIX=
rem set PREFIX=-D
set SEPARATOR= 
goto end

:java.bootclasspath
set VARIABLE_NAME=JAVA_BOOTCLASSPATH
set VARIABLE_VALUE=%JAVA_BOOTCLASSPATH%
set RESULT=
rem set SECTION_PREFIX=-Xbootclasspath:
rem set PREFIX=
set SEPARATOR=;
goto end

:java.bootclasspath.prepend
set VARIABLE_NAME=JAVA_BOOTCLASSPATH_PREPEND
set VARIABLE_VALUE=%JAVA_BOOTCLASSPATH_PREPEND%
set RESULT=
rem set SECTION_PREFIX=-Xbootclasspath/p:
set SECTION_PREFIX=
rem set PREFIX=
set SEPARATOR=;
goto end

:java.bootclasspath.append
set VARIABLE_NAME=JAVA_BOOTCLASSPATH_APPEND
set VARIABLE_VALUE=%JAVA_BOOTCLASSPATH_APPEND%
set RESULT=
rem set SECTION_PREFIX=-Xbootclasspath/a:
rem set SECTION_PREFIX=
rem set PREFIX=
set SEPARATOR=;
goto end

:jvm.args
set VARIABLE_NAME=JVM_ARGS
set VARIABLE_VALUE=%JVM_ARGS%
SET JVM_ARGS=
set RESULT=
rem set SECTION_PREFIX=
rem set PREFIX=
set SEPARATOR= 
goto end

:launcher.class
set VARIABLE_NAME=LAUNCHER_CLASS
set VARIABLE_VALUE=%LAUNCHER_CLASS%
SET LAUNCHER_CLASS=
set RESULT=
rem set SECTION_PREFIX=
rem set PREFIX=
set SEPARATOR= 
goto end

:set.variables
set VARIABLE_NAME=SET_VARIABLES
set VARIABLE_VALUE=%SET_VARIABLES%
set RESULT=
rem set SECTION_PREFIX=
rem set PREFIX=SET
set SEPARATOR= 
goto end

:command.line.args
set VARIABLE_NAME=COMMAND_LINE_ARGS
set VARIABLE_VALUE=%COMMAND_LINE_ARGS%
set RESULT=
rem set SECTION_PREFIX=
rem set PREFIX=
set SEPARATOR= 
goto end

:processresult

if not defined %VARIABLE_NAME% (
  set %VARIABLE_NAME%=%SECTION_PREFIX%%RESULT%
) else (
  set %VARIABLE_NAME%=%VARIABLE_VALUE%%SEPARATOR%%RESULT%
)

goto end

:processarg

if "%~1" == "" goto end

set TEMP=%~1
set PARAM1=%TEMP:~0,2%
set PARAM2=%TEMP:~0,18%
set PARAM3=%TEMP:~0,19%

if "%PARAM1%" == "-D" (
  SET JAVA_SYSTEM_PROPS=%JAVA_SYSTEM_PROPS% "%~1%"
  goto end
)

if "%PARAM2%"=="-Xbootclasspath/p:" (
  SET JAVA_BOOTCLASSPATH_PREPEND=%JAVA_BOOTCLASSPATH_PREPEND% "%~1%"
  goto end
)

if "%PARAM2%"=="-Xbootclasspath/a:" (
  SET JAVA_BOOTCLASSPATH_APPEND=%JAVA_BOOTCLASSPATH_APPEND% "%~1%"
  goto end
)

if "%TEMP%"=="-debug" (
  SET JAVA_SYSTEM_PROPS=%JAVA_SYSTEM_PROPS% -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=6006
  goto end
)

if "%PARAM3%"=="-Djava.library.path" (
  SET JAVA_LIBRARY_PATH=%JAVA_LIBRARY_PATH% "%~1%"
  goto end
)

set COMMAND_LINE_ARGS=%COMMAND_LINE_ARGS% "%~1%"

:end
