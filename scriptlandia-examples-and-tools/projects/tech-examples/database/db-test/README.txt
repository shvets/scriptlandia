    I. derby

1. In "src\main\resources\sqlmap-config.xml" uncomment line related to derby and comment line related to hsqldb:

  <properties resource="derby-db.properties"/>

2. Run "ij" tool:

>start derby-test.ant ij

ij>connect 'jdbc:derby:test;create=true';

Copy-paste the content of  src\main\sql\User_test.sql file to console.

ij>{content}
ij>exit;

3. Run "sysinfo" tool:

>derby-test.ant sysinfo

4. Run script:

>derby-test.ant


    II. hsqldb

1. Start db manager:

start hsqldb-test.ant dbmanager

2. Select HSQL DB Engine Standalone as type and "jdbc:hsqldb:file:test" as URL. Click "OK".

3. Copy src\main\sql\User_test.sql content into  Editor window. Click "Execute". Colse db manager.

4. Run script:

>hsqldb-test.ant