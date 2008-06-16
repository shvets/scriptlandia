    How to create and run HelloWorld bundle withing jetty servlet container 

Details: 
http://www.javaworld.com/javaworld/jw-06-2008/jw-06-osgi3.html?page=1

0. Download dependencies

>mvn

1. Run equinox (OSGI implementation):

>start ant osgi                                                                                                        n

2. Check installed bundles in console:

osgi>ss .

3. Install jetty bundle and dependencies:

osgi>install file:/C:/Work/Editors/eclipse/plugins/org.eclipse.osgi.services_3.1.200.v20070605.jar
osgi>install file:/C:/Work/Editors/eclipse/plugins/org.eclipse.equinox.common_3.3.0.v20070426.jar
osgi>install file:/C:/Work/Editors/eclipse/plugins/org.eclipse.equinox.registry_3.3.1.R33x_v20070802.jar
osgi>install file:/C:/Work/Editors/eclipse/plugins/javax.servlet_2.4.0.v200706111738.jar
osgi>install file:/C:/Work/Editors/eclipse/plugins/org.apache.commons.logging_1.0.4.v200803061811.jar
osgi>install file:/C:/Work/Editors/eclipse/plugins/org.mortbay.jetty_5.1.11.v200706111724.jar
osgi>install file:/C:/Work/Editors/eclipse/plugins/org.eclipse.equinox.http.registry_1.0.1.R33x_v20071231.jar
osgi>install file:/C:/Work/Editors/eclipse/plugins/org.eclipse.equinox.http.servlet_1.0.1.R33x_v20070816.jar
osgi>install file:/C:/Work/Editors/eclipse/plugins/org.eclipse.equinox.http.jetty_1.0.1.R33x_v20070816.jar
osgi>ss .

Check bundle ID for (jetty e.g. 1-9)

4. Start installed bundles by using correct ID:

osgi>start 1
...
osgi>start 9

5. Compile and jar examples

>ant jar

6. Install bundles:

osgi>install file:target/programmatic-HelloWorld.jar
osgi>install file:target/declarative-HelloWorld.jar

Check ID for installed bundle (e.g. 10-11)

osgi>ss .

7. Start bundle by using correct ID:

osgi>start 10
osgi>start 11

Check the status of started bundle (should be changed from INSTALLED to ACTIVE)

osgi>ss .

8. Check programmatic example in the browser:

browser>http://localhost/helloworld
browser>http://localhost/helloworld.html

9. Check declarative example in the browser:

browser>http://localhost/decl/helloworld
browser>http://localhost/decl/helloworld.html

