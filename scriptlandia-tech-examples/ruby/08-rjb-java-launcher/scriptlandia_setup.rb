# scriptlandia_setup.rb

groovy.ui.GroovyMain

options.proxy.http = 'http://myproxy:8080'
options.proxy.exclude << '*.mycompany.com'
options.proxy.exclude << 'localhost'

repositories.local = 'c:/Work/maven-repository'

repositories.remote << ['http://www.ibiblio.org/maven2/'

artifact('org.apache.openjpa:openjpa-all:jar:0.9.7').invoke
artifacts(OPENJPA).each(&:invoke)

DOJO = '0.2.2'

url = "http://download.dojotoolkit.org/release-#{DOJO}/dojo-#{DOJO}-widget.zip"
download(artifact("dojo:dojo:zip:widget:#{DOJO}")=>url)


http://scriptlandia-repository.googlecode.com/svn/trunk/languages</url>
<url>http://scriptlandia-repository.googlecode.com/svn/trunk/tools</url>
        </repository>

        <repository>
          <id>com.springsource.repository.bundles.release</id>
          <name>SpringSource Enterprise Bundle Repository - SpringSource Bundle Releases</name>
          <url>http://repository.springsource.com/maven/bundles/release</url>
        </repository>

        <repository>
          <id>com.springsource.repository.bundles.external</id>
          <name>SpringSource Enterprise Bundle Repository - External Bundle Releases</name>
          <url>http://repository.springsource.com/maven/bundles/external</url>
        </repository>

        <repository>
          <id>com.springsource.repository.libraries.release</id>
          <name>SpringSource Enterprise Bundle Repository - SpringSource Library Releases</name>
          <url>http://repository.springsource.com/maven/libraries/release</url>
        </repository>

        <repository>
          <id>com.springsource.repository.libraries.external</id>
          <name>SpringSource Enterprise Bundle Repository - External Library Releases</name>
          <url>http://repository.springsource.com/maven/libraries/external</url>
        </repository>

        <repository>
          <id>jboss-maven2</id>
          <name>JBoss Maven Repository</name>
          <url>http://repository.jboss.com/maven2</url>
        </repository>

        <repository>
          <id>mergere-repo</id>
          <name>Mergere Maven2 repository</name>
          <url>http://repo.mergere.com/maven2</url>
        </repository>

        <repository>
          <id>central1</id>
          <name>Central Repository 1</name>
          <url>http://repo1.maven.org/maven2</url>
        </repository>

        <repository>
          <id>central2</id>
          <name>Central Repository 2</name>
          <url>http://repo1.maven.org/maven-spring</url>
        </repository>

        <repository>
          <id>groovy</id>
          <name>Groovy repository</name>
          <url>http://dist.codehaus.org</url>
          <layout>legacy</layout>
        </repository>

        <repository>
          <id>codehaus-repo</id>
          <name>Codehaus Maven2 repository</name>
          <url>http://repository.codehaus.org</url>
        </repository>

        <repository>
          <id>maven2-repository.dev.java.net</id>
          <name>Java.net Repository for Maven</name>
          <url>http://download.java.net/maven/2/</url>
          <layout>default</layout>
        </repository>

        <repository>
          <id>javanet</id>
          <name>Java.Net repository</name>
          <url>https://maven-repository.dev.java.net/nonav/repository</url>
          <layout>legacy</layout>
        </repository>

        <repository>
          <id>javanet2</id>
          <name>Java.Net repository 2</name>
          <url>http://download.java.net/maven/2</url>
          <layout>legacy</layout>
        </repository>

        <!--
          <repository>
            <id>sourcelabs-repo</id>
            <name>Sourcelab Maven2 repository</name>
            <url>http://dist.sourcelabs.com/sash/m2</url>
          </repository>
        -->

        <repository>
          <id>jyaml-maven2-repository</id>
          <name>Maven Repository for jyaml</name>
          <url>http://jyaml.sourceforge.net/m2-repo</url>
        </repository>

        <repository>
          <id>mule-deps</id>
          <name>Mule Dependencies</name>
          <url>http://dist.codehaus.org/mule/dependencies/maven2</url>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
        </repository>
      </repositories>
