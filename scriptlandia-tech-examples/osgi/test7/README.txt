Link:
http://weblogs.java.net/blog/arungupta/archive/2008/06/totd_36_deploy.html
http://blogs.sun.com/arungupta/entry/totd_34_using_felix_shell

1.

mvn archetype:create -DarchetypeGroupId=org.apache.maven.archetypes -DgroupId=org.glassfish.samples.osgi.helloworld -DartifactId=helloworld

2. Modify app.java and pom

3.

mvn install

4. Download and install glassfish v3:

https://glassfish.dev.java.net/public/downloadsindex.html

5. Add lines in ${glassfish.home}/felix/conf/config.properties" files for "felix.auto.start.1" property:

${com.sun.aas.installRootURI}/felix/bundle/org.apache.felix.shell.jar \
${com.sun.aas.installRootURI}/felix/bundle/org.apache.felix.shell.tui.jar


5. Run glassfish:

start java -jar C:\Work\glassfishv3-tp2\glassfish\modules\glassfish-10.0-tp-2-SNAPSHOT.jar
