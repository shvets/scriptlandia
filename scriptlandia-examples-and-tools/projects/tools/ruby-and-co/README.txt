This tool helps to install Ruby and Rails projects.

1. Configuration 

1.1. Install Ruby scripts:

>install.ant 

or

>install.bat 

2. Rails Project


2.1. Create new project (testrails):

>rails.bat testrails

Change the current directory:

>cd testrails

2.2. Modify testrails\config\database.yml to point to your database.

2.3. Start the database. Create database for devepoment: testrails_development

2.4. Generate the model

>ruby.bat script\generate controller MyTest

2.5. Generate the controller

>ruby.bat script\generate controller MyTest

2.6. Start the WEBrick server

>start ruby.bat script\server

2.7. Test if server is started properly in the browser:

>http://localhost:3000/my_test

Now you should see your view.

