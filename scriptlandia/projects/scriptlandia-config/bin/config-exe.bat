SET SCRIPTLANDIA_HOME=@scriptlandia.home@

set SYSTEM_PARAMETERS=-Dbeanshell.version=@beanshell.version@ -Dant.version.internal=@ant.version.internal@

start %SCRIPTLANDIA_HOME%\launcher-gui.exe -config
