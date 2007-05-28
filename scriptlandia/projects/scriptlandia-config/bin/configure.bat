@echo off

rem set SYSTEM_PARAMETERS=-Dbeanshell.version=@beanshell.version@ -Dant.version.internal=@ant.version.internal@

SET SCRIPTLANDIA_HOME=@scriptlandia.home@

%SCRIPTLANDIA_HOME%\launcher.bat -config %*
