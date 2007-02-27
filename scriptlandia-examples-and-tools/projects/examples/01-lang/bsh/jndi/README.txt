This example show JNDI components under JBoss server.

1. "jndi.properties" file contains settings for your JNDI server.

2. JBoss server should be started before the test.

3. During antlet start we have to specify the location of libraries 
with core JNDI classes. For example:

>jndiLister.ant -lib ${jboss.home}\client

