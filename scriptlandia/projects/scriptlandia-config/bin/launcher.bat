rem see https://java-app-launcher.dev.java.net

@echo off

set JAVA_HOME=@java.home@
SET MOBILE_JAVA_HOME=@mobile.java.home@

set APP=%~nx0
set APP=@scriptlandia.home@\%APP:~0,-4%

call @scriptlandia.home@\launcher\app.bat %*
