Notes

1. axis2-test.maven specifies dependencies for axis2 and is used for starting Jetty AS.

2. axis2-test.ant is used as wrapper around maven project file and allows additional
commands written in ant.

3. Build war file:

>axis2-test.maven war:war

3. run jetty AS:

>axis2-test.maven jetty:run

Now, web service is started. You can test it in the browser:

>http://localhost:9090/axis2-book

