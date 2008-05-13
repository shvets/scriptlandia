    How to compile and run Scala


1. With Ant (scala is preinstalled)

>ant -f scala-test1.ant run


2. With Ant (scala will be installed by maven 2)

>ant -f scala-test2.ant run


3. With Maven 2 through plexus-compiler-scalac ()

>mvn -f scala-test3.maven compile

>mvn -f scala-test3.maven exec:java


4. With Maven 2 through maven-scala-plugin (http://millstone.iodp.tamu.edu/~blambi/maven-scala-plugin)

>mvn -f scala-test4.maven scala:compile

>mvn -f scala-test4.maven scala:run -DmainClass=HelloWorld

