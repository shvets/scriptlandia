@echo off

set JAVA_CMD=javaw

SET SCRIPTLANDIA_HOME=@scriptlandia.home@

start %SCRIPTLANDIA_HOME%\launcher.bat -ngtray %*
