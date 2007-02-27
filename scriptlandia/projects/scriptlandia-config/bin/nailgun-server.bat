@echo off

SET JAVA_HOME=@java.home@
SET MOBILE_JAVA_HOME=@mobile.java.home@

SET JAVA_BINARY=start %JAVA_HOME%\bin\java -server

rem SET LAUNCHER_CLASS=org.sf.scriptlandia.nailgun.NGServerLauncher
rem @scriptlandia.home@\launcher-core.bat -launcher.class.name=%LAUNCHER_CLASS% %*

@scriptlandia.home@\launcher-core.bat -ngserver %*
