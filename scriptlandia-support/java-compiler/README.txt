1. Retrieve the latest version of openjdk sources as archive bundle (http://download.java.net/openjdk/jdk7) 
or through Subversion:

>svn co https://openjdk.dev.java.net/svn/openjdk/jdk/tags/jdk7-b18/j2se/src/share/opensource/javac/src/bin openjdk/j2se/src/share/opensource/javac/src/bin
>svn co https://openjdk.dev.java.net/svn/openjdk/jdk/tags/jdk7-b18/j2se/src/share/classes openjdk/j2se/src/share/classes

Note: you should be registered user on java.net to get openjdk from svn.

2. In prepare.ant file modify "original.project.home" to point to correct location of openjdk:

  <property name="original.project.home" value="d:/Java/sources/openjdk"/>

3. In build.properties correct compiler version:

build.number = b18

4.

>ant prepare

5.

>ant

