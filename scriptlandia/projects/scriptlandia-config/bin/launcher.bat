@echo off

set JAVA_HOME=@java.home@
SET MOBILE_JAVA_HOME=@mobile.java.home@
SET SCRIPTLANDIA_HOME=@scriptlandia.home@

SET NAILGUN=@repository.home@\com\martiansoftware\nailgun-bin\@nailgun.version@\ng

set APP=%~nx0
set APP_NAME=%APP:~0,-4%
set APP=%SCRIPTLANDIA_HOME%\%APP:~0,-4%

call %SCRIPTLANDIA_HOME%\launcher\app.bat %*
