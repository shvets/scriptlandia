URL: http://robmayhew.com/rails-201-todo-list-tutorial/

1.  (2.0.1)

gem install rails --include-dependencies --no-rdoc --no-ri
gem update --system

jruby -S gem install rails --include-dependencies --no-ri --no-rdoc

2.

rails todo

jruby -S rails --database mysql todo

3.
cd todo

4.

rake db:create:all

5. 

ruby script/generate scaffold Todo title:string body:text done:boolean due:datetime

6.

rake db:migrate
rake db:version

7.

ruby script/server

8. Open your browser to 

http://localhost:3000/todos