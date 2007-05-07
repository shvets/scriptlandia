Building the project with ruby

1. Create the project

>rails.bat testrails
>jrails.bat testrails

Change the current directory:

cd testrails

2. Generate the controller

>ruby.bat script\generate controller MyTest
>jruby.bat .\script\generate controller MyTest

3. Generate the another model/controller:

>ruby.bat script\generate model Recipe
>ruby.bat script\generate controller Recipe

>jruby.bat .\script\generate model Recipe
>jruby.bat .\script\generate controller Recipe


4. Generate the another model/controller:

>ruby.bat script\generate controller Category
>ruby.bat script\generate model Category

>jruby.bat .\script\generate controller Category
>jruby.bat .\script\generate model Category

5. modify testrails\config\database.yml for database connection.

6. Start the WEBrick server

>start ruby.bat script\server

Test it:

>http://localhost:3000
>http://localhost:3000/my_test



ruby.bat script/generate migration contact_db
