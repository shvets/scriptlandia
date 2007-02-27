This tool helps to configure JRuby to work with Ralis project.

You should install Ruby tool before this task.


1. Create new project (testrails):

>rails.bat testrails

or

>jrails.bat testrails


Change the current directory:

>cd testrails


2. Modify testrails\config\database.yml to point to your database.

3. Start the database. Create database for devepoment: testrails_development

4. Generate the model

>ruby.bat script\generate model MyTest

or 

>jruby.bat .\script\generate controller MyTest


5. Generate the controller

>ruby.bat script\generate controller MyTest

or 

>jruby.bat .\script\generate controller MyTest


 6. Start the WEBrick server

>start ruby.bat script\server

or

>start jruby.bat .\script\server


9. Test if server is started properly in the browser:

>http://localhost:3000/my_test

Now you should see your view.


