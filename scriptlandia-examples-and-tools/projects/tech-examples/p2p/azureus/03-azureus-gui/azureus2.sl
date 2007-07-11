<?xml version="1.0" encoding="UTF-8"?>

<!-- Resolving azureus dependencies. -->

<project>
  <modelVersion>4.0.0</modelVersion>

  <groupId>azureus-exec</groupId>
  <artifactId>azureus-exec</artifactId>
  <version>1.0</version>
  <packaging>pom</packaging>

  <name>Test</name>

  <properties>
    <java.library.path>${repository.home}/org/eclipse/swt-win32/3.2.1</java.library.path>
  </properties>

  <dependencies>
    <dependency>
      <groupId>commons-cli</groupId>
      <artifactId>commons-cli</artifactId>
      <version>1.0</version>
    </dependency>

    <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
      <version>1.2.13</version>
    </dependency>

    <dependency>
      <groupId>org.eclipse</groupId>
      <artifactId>swt-win32</artifactId>
      <version>3.2.1</version>
    </dependency>

    <dependency>
      <groupId>azureus</groupId>
      <artifactId>azureus</artifactId>
      <version>2.5.0.0</version>
    </dependency>
  </dependencies>

  <repositories>
    <repository>
      <id>scriptlandia-repo</id>
      <name>Scriptlandia Maven2 repository</name>
      <url>http://scriptlandia-repository.googlecode.com/svn/trunk/tools</url>
    </repository>
  </repositories>

  <build>
    <defaultGoal>exec:exec</defaultGoal>

    <plugins>
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>exec-maven-plugin</artifactId>
        <executions>
          <execution>
            <goals>
              <goal>exec</goal>
            </goals>
          </execution>
        </executions>

        <configuration>
          <mainClass>org.gudy.azureus2.ui.common.Main</mainClass>
        </configuration>
      </plugin>
    </plugins>
  </build>

</project>
