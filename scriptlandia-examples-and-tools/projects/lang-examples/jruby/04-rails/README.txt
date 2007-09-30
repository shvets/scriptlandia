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

Article: http://www.onlamp.com/lpt/a/6826

mysqld

mysql -u root -p

mysql>create database cookbook2_development;

mysql>grant all on cookbook2_development.* to 'ODBC'@'localhost';

mysql>exit

drop table if exists recipes;
drop table if exists categories;
create table categories (
    id                     int            not null auto_increment,
    name                   varchar(100)   not null default '',
    primary key(id)
) engine=InnoDB;

create table recipes (
    id                     int            not null auto_increment,
    category_id            int            not null,
    title                  varchar(100)   not null default '',
    description            varchar(255)   null,
    date                   date           null,
    instructions           text           null,
    constraint fk_recipes_categories foreign key (category_id) references categories(id),
    primary key(id)
) engine=InnoDB;


mysql cookbook2_development <db\create.sql
