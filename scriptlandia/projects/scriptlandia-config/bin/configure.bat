@echo off

SET LAUNCHER_HOME=@launcher.home@

SET ANT_VERSION=1.7.0
SET BEANSHELL_VERSION=2.0b5
SET SCRIPTLANDIA_VERSION=@scriptlandia.version@
SET JDIC_VERSION=0.9.3
SET NAILGUN_VERSION=0.7.1
SET JAVA_COMPILER_VERSION=7.0-b23

SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dant.version.internal=%ANT_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dbeanshell.version=%BEANSHELL_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dscriptlandia.version=%SCRIPTLANDIA_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Djdic.version=%JDIC_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dnailgun.version=%NAILGUN_VERSION%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Djava.compiler.version=%JAVA_COMPILER_VERSION%"

SET MAIN_CLASS=org.sf.scriptlandia.install.GuiInstaller

SET PROPERTIES="-classpath.file.name=scriptlandia.classpath" "-main.class.name=%MAIN_CLASS%"

%LAUNCHER_HOME%\launcher.bat %SYSTEM_PROPERTIES% %PROPERTIES% "-Dconfig=true" -wait
