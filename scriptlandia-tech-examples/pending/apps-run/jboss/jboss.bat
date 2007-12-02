SET JBOSS_HOME=C:\AppServers\jboss-4.0.3SP1

start jboss.cwd "-Djboss.home=%JBOSS_HOME%" "-Dscriptlandia.continuous.mode=true" %*