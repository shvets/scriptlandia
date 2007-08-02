http://struts.apache.org/2.x/docs/developing-a-portlet-using-eclipse.html
http://portletwork.blogspot.com/2007/07/mvnjetty-and-portlets.html

1.

mvn archetype:create -DgroupId=com.mycompany.myportlet \
                     -DartifactId=myportlet \
                     -DarchetypeGroupId=org.apache.struts \
                     -DarchetypeArtifactId=struts2-archetype-portlet \
                     -DarchetypeVersion=2.0.9-SNAPSHOT \
                     -DremoteRepositories=http://people.apache.org/repo/m2-snapshot-repository

mvn archetype:create "-DgroupId=com.mycompany.myportlet" "-DartifactId=myportlet" "-DarchetypeGroupId=org.apache.struts" "-DarchetypeArtifactId=struts2-archetype-portlet" "-DarchetypeVersion=2.0.9-SNAPSHOT" "-DremoteRepositories=http://people.apache.org/repo/m2-snapshot-repository"

http://code.google.com/p/archy/

2. Add repository: 

<repositories>
  <repository>
    <id>pluto-repo</id>
    <name>Pluto staging repo</name>
    <url>http://people.apache.org/builds/portals-pluto/m2-staging-repository</url>
  </repository>
</repositories>


3. add pluto dependencies

 <dependency>
   <groupId>org.apache.pluto</groupId>
   <artifactId>pluto-portal-driver</artifactId>
   <version>1.1.4</version>
 </dependency>
 <dependency>
   <groupId>org.apache.pluto</groupId>
   <artifactId>pluto-portal-driver-impl</artifactId>
   <version>1.1.4</version>
 </dependency>
 <dependency>
   <groupId>org.apache.pluto</groupId>
   <artifactId>pluto-container</artifactId>
   <version>1.1.4</version>
 </dependency>

 <dependency>
   <groupId>org.apache.pluto</groupId>
   <artifactId>pluto-taglib</artifactId>
   <version>1.1.4</version>
 </dependency>

 <dependency>
   <groupId>org.springframework</groupId>
   <artifactId>spring-web</artifactId>
   <version>2.0.2</version>
 </dependency>

