@echo off 

@call ..\..\..\jlaunchpad\trunk\launcher\config.bat
@call clean.bat

@call build.bat

@call %LAUNCHER_HOME%\launcher.bat ^
  "-deps.file.name=lang
  uages/ant/core/pom.xml" ^
  "-deps.file.name=languages/beanshell/core/pom.xml" ^
  "-deps.file.name=languages/maven/core/pom.xml" ^
  "-main.class.name=org.apache.tools.ant.Main" -f build.xml assembly

