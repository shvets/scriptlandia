@echo off

rem nant.bat

SET REPOSITORY_HOME=@repository.home@
SET SCRIPT_NAME=%REPOSITORY_HOME%\nant\nant\0.85\bin\nant.exe

%SCRIPT_NAME% %*
