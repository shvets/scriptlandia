http://scala-blogs.org/2008/01/maven-for-scala.html

1.
mvn org.apache.maven.plugins:maven-archetype-plugin:create ^
-DarchetypeGroupId=org.scala-tools.archetypes ^
-DarchetypeArtifactId=scala-archetype-simple ^
-DarchetypeVersion=1.1 ^
-DremoteRepositories=http://scala-tools.org/repo-releases ^
-DgroupId=group -DartifactId=project

2. 

cd project
mvn compile
mvn test
