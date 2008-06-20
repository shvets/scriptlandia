Note: You should use Java 6 Compiler.


1. Retrieve latest version of javax-scripting:

>cvs -d :pserver:guest@cvs.dev.java.net:/cvs login
>cvs -d :pserver:guest@cvs.dev.java.net:/cvs checkout scripting

Make "javax-scripting-src.zip" archive from it. Copy this file into root folder.


2. Copy updates from zip into source folders:

>prepare.ant synch-zip

3. Run command to prepare jdk1.5 version of archives:

>prepare.ant prepare15

4. Run command to prepare jdk1.5 version of archives:

>prepare.ant prepare16

5. Copy generated artifacts to svn directory:

>prepare.ant copy-jars


You can download javax-scripting reference implementation from here: 

http://jcp.org/en/jsr/detail?id=223
