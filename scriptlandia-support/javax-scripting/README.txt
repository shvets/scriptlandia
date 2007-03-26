1. Retrieve lates version of javax-scripting:

>cvs -d :pserver:guest@cvs.dev.java.net:/cvs login
>cvs -d :pserver:guest@cvs.dev.java.net:/cvs checkout scripting

2. In prepare.ant file modify "original.scripting.home":

  <property name="original.scripting.home" value="scripting"/>

3. 

>prepare.ant synch

4.

>prepare.ant