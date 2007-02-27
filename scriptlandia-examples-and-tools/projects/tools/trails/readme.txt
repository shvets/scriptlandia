//http://wiki.colar.net/dokuwiki/doku.php/trail_install

1. Install Trails into ${trails.home}. Modify "tool.properties" to point to correct 
"trails.version", "trails.home" and "tomcat.home". Run install.ant

2. Create new project:

>ant create-project

>Base directory: c:\testtrails
>Project name: testtrails
>Context dir: context

New directories will be created:

c:\testtrails
      testtrails
              context

3. Edit "build.properties" in project root:

tomcat.home=${tomcat.home}
tomcat.url=http://localhost:8080
manager.username=tomcat
manager.password=tomcat

Properties: "manager.username" and "manager.password" are located in
${tomcat_home}/conf/tomcat-users.xml file.


4.

5.

http://127.0.0.1:8080/trailstest/
