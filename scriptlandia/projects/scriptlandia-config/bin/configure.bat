@echo off

SET JAVA_HOME=@java.home@
SET MOBILE_JAVA_HOME=@mobile.java.home@

rem SET JAVA_BINARY=start %JAVA_HOME%\bin\javaw

rem set SYSTEM_PARAMETERS=-Dbeanshell.version=@beanshell.version@ -Dant.version.internal=@ant.version.internal@

@scriptlandia.home@\launcher.bat -config %*
