@echo off

set CMD=javaw

SET SCRIPTLANDIA_HOME=@scriptlandia.home@

start %SCRIPTLANDIA_HOME%\scriptlandia.bat -ngtray %*
