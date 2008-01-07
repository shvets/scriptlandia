1. Retrieve the latest version of openjdk sources as archive bundle (http://download.java.net/openjdk/jdk7) 
or through Subversion:

>svn checkout https://openjdk.dev.java.net/svn/openjdk/jdk/trunk jdk --username USER

Note: you should be registered user on java.net to get openjdk from svn.

2. In prepare.ant file modify "langtools.home" to point to correct location of langtools in openjdk:

  <property name="langtools.home" value="c:/Work/Sources/openjdk-b23/langtools"/>

3. In prepare.ant file modify "repository.home" to point to correct location maven local repository:

  <property name="repository.home" value="c:/maven-repository"/>

We'll copy ant.jar file from there.

4. Run command:

>ant -f prepare.ant build

5. Run command:

>ant -f prepare.ant build

In ${langtools.home}/dist/bootstrap/lib folder you will find assembled jars.


