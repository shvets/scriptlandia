Notes

1. xfire-spring-test.maven specifies dependencies for axis2 and is used for starting Jetty AS.

2. xfire-spring-test.ant is used as wrapper around maven project file and allows additional
commands written in ant.

3. Build war file:

>xfire-spring-test.maven war:war

3. run jetty AS:

>xfire-spring-test.maven jetty:run

Now, web service is started. You can test it in the browser:

>http://localhost:9090/xfire-spring-test/EchoService?wsdl

4. Run the client:

>xfire-spring-test.maven test