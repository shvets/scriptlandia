See also: http://www.developer.com/open/print.php/3662031


1. Configuration 


1.1. Test JRuby version:

>jruby -v

1.2. Test Gem version:

>jgem -v

1.3. Install Rails:

>jgem install rails -y --no-rdoc --no-ri

Sample output:

Successfully installed rails-1.2.3
Successfully installed rake-0.7.2
Successfully installed activesupport-1.4.2
Successfully installed activerecord-1.15.3
Successfully installed actionpack-1.13.3
Successfully installed actionmailer-1.3.3
Successfully installed actionwebservice-1.2.3

1.4. Install raven:

>jgem install raven -y --no-rdoc --no-ri

Sample output:

Successfully installed raven-1.2.2


2. Rails Project


2.1. Create new project (testrails):

>jrails.bat testrails


Change the current directory:

>cd testrails

2.2. Modify testrails\config\database.yml to point to your database.

2.3. Start the database. Create database for devepoment: testrails_development

2.4. Generate the model

>jruby.bat %CD%\script\generate controller MyTest

2.5. Generate the controller

>jruby.bat %CD%\script\generate controller MyTest

2.6. Start the WEBrick server

>start jruby.bat %CD%\script\server

2.7. Test if server is started properly in the browser:

>http://localhost:3000/my_test

Now you should see your view.

