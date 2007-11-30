SET JAVA_HOME=c:\Java\jdk1.6.0

SET REPOSITORY_HOME=c:\maven-repository

SET CLASSPATH=groovy-classes
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\codehaus\groovy\groovy-all\1.1-rc-3\groovy-all-1.1-rc-3.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\apache\ant\ant\1.7.0\ant-1.7.0.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\groovy\groovyws-standalone\0.1.1\groovyws-standalone-0.1.1.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\apache\cxf\2.0.3-incubator\cxf-2.0.3-incubator.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\wsdl4j\wsdl4j\1.6.1\wsdl4j-1.6.1.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\xml-resolver\xml-resolver\1.2\xml-resolver-1.2.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\org\apache\ws\commons\schema\XmlSchema\1.3.2\XmlSchema-1.3.2.jar

SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\javax\xml\jaxb-impl\2.1.4\jaxb-impl-2.1.4.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\javax\xml\jaxb-xjc\2.1.4\jaxb-xjc-2.1.4.jar

%JAVA_HOME%\bin\java -Djava.endorsed.dirs=%REPOSITORY_HOME%\javax\xml\bind\jaxb-api\2.1 -classpath %CLASSPATH% client
