
if "%DRIVE_LETTER%" == "" SET DRIVE_LETTER=c:

if "%PROXY_SERVER_HOST_NAME%" == "" SET PROXY_SERVER_HOST_NAME=
if "%PROXY_SERVER_PORT%" == "" SET PROXY_SERVER_PORT=

SET JAVA_HOME=%DRIVE_LETTER%\Java\jdk1.5.0

SET MOBILE_JAVA_HOME=%DRIVE_LETTER%\Java\j2me-2.5
SET RUBY_HOME=%DRIVE_LETTER%\Ruby\ruby-1.8.4-20

SET SCRIPTLANDIA_HOME=%DRIVE_LETTER%\scriptlandia
SET REPOSITORY_HOME=%DRIVE_LETTER%\maven-repository

SET JAVA_SPECIFICATION_VERSION=1.5
SET ANT_VERSION=1.7.0
SET BEANSHELL_VERSION=2.0b5
SET SCRIPTLANDIA_VERSION=2.2.2

IF NOT "%PROXY_SERVER_HOST_NAME%" == "" SET PROXY_PARAMS=%SYSTEM_PROPERTIES% -Dproxy.server.host=%PROXY_SERVER_HOST_NAME% -Dproxy.server.port=%PROXY_SERVER_PORT%

IF EXIST %JAVA_HOME% GOTO jme
ECHO JDK cannot be found!
pause
exit

:jme
IF NOT EXIST %MOBILE_JAVA_HOME% ECHO Java Micro Edition cannot be found!

SET SYSTEM_PROPERTIES=-Dmobile.java.home="%MOBILE_JAVA_HOME%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% -Dant.version.internal=%ANT_VERSION%
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% -Dbeanshell.version=%BEANSHELL_VERSION%
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% -Dscriptlandia.version=%SCRIPTLANDIA_VERSION%
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% -Djava.specification.version=%JAVA_SPECIFICATION_VERSION%
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Dscriptlandia.home=%SCRIPTLANDIA_HOME%"
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% "-Drepository.home=%REPOSITORY_HOME%"


SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% %PROXY_PARAMS%

IF EXIST %RUBY_HOME% SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% -Dnative.ruby.home="%RUBY_HOME%"
