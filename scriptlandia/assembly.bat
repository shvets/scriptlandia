@echo off 

@call config.bat

@call clean.bat

@call build.bat

@call %LAUNCHER_HOME%\launcher.bat ^
  "-deps.file.name=languages/ant/core/pom.xml" ^
  "-deps.file.name=languages/beanshell/core/pom.xml" ^
  "-deps.file.name=languages/maven/core/pom.xml" ^
  "-main.class.name=org.apache.tools.ant.Main" -f build.xml assembly

