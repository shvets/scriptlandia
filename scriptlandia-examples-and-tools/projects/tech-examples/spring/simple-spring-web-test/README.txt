1. Compile the project:

>mvn compile

2. Run test:

>mvn test


3. Create war file (optional):

>mvn war:war

4. Run jetty application server:

>start mvn jetty:run

5. In your browser use url: http://localhost:9090/simple-spring-test/

You don't have to restart your jetty if you do jsp changes only. It will pick 
your changes automatically.