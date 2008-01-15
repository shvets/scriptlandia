Note: You should use Java 6 Compiler.


1. Retrieve latest version of javax-scripting:

>cvs -d :pserver:guest@cvs.dev.java.net:/cvs login
>cvs -d :pserver:guest@cvs.dev.java.net:/cvs checkout scripting

2. In prepare.ant file modify "original.project.home":

  <property name="original.project.home" value="c:/Sources/scripting/engines"/>

3. 

>prepare.ant synch

4.

>prepare.ant
>prepare.ant prepare16