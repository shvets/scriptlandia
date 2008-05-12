1. Retrieve the latest version of openjdk sources as archive bundle (http://download.java.net/openjdk/jdk7) 
or through Subversion from the trunk:

>svn co https://openjdk.dev.java.net/svn/openjdk/jdk/trunk openjdk-7-b26

Note: you should be registered user on java.net to get openjdk from svn.


2. Go to "langtools/make" directory. In build.properties correct these properties:

build.number = b26
javac.target = 5

3. Run this command:

>ant build -Dboot.java.home=c:/Java/jdk1.5.0

where "boot.java.home" property points out to preinstalled JDK.

4. In "langtools/dist/lib" directory new file is created: "classes.jar". This archive
contains all class files required by the compiler.

