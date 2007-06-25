@echo off

set JAVA_HOME=@java.home@
set MOBILE_JAVA_HOME=@mobile.java.home@
set SCRIPTLANDIA_HOME=@scriptlandia.home@
set LAUNCHER_HOME=@launcher.home@

SET NAILGUN=@repository.home@\com\martiansoftware\nailgun-bin\@nailgun.version@\ng

set APP=%~nx0
set APP_NAME=%APP:~0,-4%
set APP=%SCRIPTLANDIA_HOME%\%APP:~0,-4%

call %LAUNCHER_HOME%\launcher-core.bat %*
