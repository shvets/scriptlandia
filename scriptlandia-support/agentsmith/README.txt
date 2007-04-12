1. Retrieve latest version of javax-scripting:

>svn checkout https://agentsmith.dev.java.net/svn/agentsmith/trunk/smith smith --username guest

2. In prepare.ant file modify "original.project.home":

  <property name="original.project.home" value="smith"/>

3. 

>prepare.ant synch

4.

>prepare.ant