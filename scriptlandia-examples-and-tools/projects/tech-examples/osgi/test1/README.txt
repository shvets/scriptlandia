    How to create and run HelloWorld bundle

Details: http://neilbartlett.name/blog/osgi-articles

1. Run equinox (OSGI implementation):

>start ant osgi

2. Check installed bundles in console

osgi>ss .

3. Compile and jar example

>ant jar

4. Install bundle:

osgi>install file:target/HelloWorld.jar

Check ID for installed bundle (e.g. 1)

osgi>ss .

5. Start bundle by using correct ID:

osgi>start 1

Check the status of started bundle (should be changed from INSTALLED to ACTIVE)

osgi>ss .

6. Stop bundle:

osgi>stop 1

Check the status of started bundle (should be changed from ACTIVE to RESOLVED)

osgi>ss .
