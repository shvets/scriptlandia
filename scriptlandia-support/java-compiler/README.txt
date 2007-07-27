1. Retrieve the latest version of openjdk sources as archive bundle (http://download.java.net/openjdk/jdk7) 
or through Subversion:

>svn checkout https://openjdk.dev.java.net/svn/openjdk/jdk/tags/jdk7-b16 openjdk --username username

Note: you should be registered user on java.net to get openjdk from svn.


2. In prepare.ant file modify "original.project.home":

  <property name="original.project.home" value="openjdk"/>

3. In build.properties correct compiler version:

build.number = b16

4.

>ant prepare

5.

>ant

