@echo off

SET JAVA_HOME=@java.home@
SET MOBILE_JAVA_HOME=@mobile.java.home@

SET JAVA_BINARY="%JAVA_HOME%\bin\java"
rem -server

@scriptlandia.home@\launcher-core.bat %*
