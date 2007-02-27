@echo off

SET JAVA_HOME=@java.home@
SET MOBILE_JAVA_HOME=@mobile.java.home@

SET JAVA_BINARY=start %JAVA_HOME%\bin\javaw

@scriptlandia.home@\launcher-core.bat -ngtray %*
