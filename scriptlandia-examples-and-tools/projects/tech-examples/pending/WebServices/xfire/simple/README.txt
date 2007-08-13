Notes

1. xfire-simple-test.maven specifies dependencies for axis2 and is used for starting Jetty AS.

2. xfire-simple-test.ant is used as wrapper around maven project file and allows additional
commands written in ant.

3. Build war file:

>xfire-simple-test.maven war:war

3. run jetty AS:

>xfire-simple-test.maven jetty:run

Now, web service is started. You can test it in the browser:

>http://localhost:9090/xfire-simple-test/services

4. Run the client:

>xfire-simple-test.maven test