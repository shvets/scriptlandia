<?xml version="1.0" encoding="UTF-8"?>

<!-- Resolving azureus dependencies. -->

<project>
  <modelVersion>4.0.0</modelVersion>

  <groupId>org.gudy.azureus2.ui.common</groupId>
  <artifactId>Main</artifactId>
  <version>1.0</version>

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
      <url>http://scriptlandia.sourceforge.net/maven2-tools</url>
    </repository>
  </repositories>

</project>
