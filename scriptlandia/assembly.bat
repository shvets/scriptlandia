@echo off 

@call ..\..\..\jlaunchpad\trunk\jlaunchpad\config.bat
@call clean.bat

@call build.bat

@call %JLAUNCHPAD_HOME%\jlaunchpad.bat %SYSTEM_PROPERTIES% ^
  "-deps.file.name=languages/ant/core/pom.xml" ^
  "-deps.file.name=languages/beanshell/core/pom.xml" ^
  "-deps.file.name=languages/maven/core/pom.xml" ^
  "-deps.file.name=projects/scriptlandia-installer/pom.xml" ^
  "-main.class.name=org.apache.tools.ant.Main" -f build.xml assembly

