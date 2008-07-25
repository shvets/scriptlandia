Link: http://weblogs.java.net/blog/arungupta/archive/2008/07/jruby_113_relea.html

1.
jgem install rails --no-ri --no-rdoc
jgem install glassfish --no-ri --no-rdoc

2.
jrails helloworld -d mysql  

3.
copy-jar.bat

4.
start jruby -S glassfish_rails helloworld -wait

5.
cd helloworld

jruby script/generate scaffold runner distance:float minutes:integer

6.
// Start MySQL database

7.
jrake db:create
jrake db:migrate

8.
http://localhost:8080/helloworld/runners 