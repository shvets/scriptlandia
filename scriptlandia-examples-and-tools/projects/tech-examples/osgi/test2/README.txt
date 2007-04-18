    How to uninstall HelloWorld bundle programmatically

Details: http://neilbartlett.name/blog/osgi-articles

1. Run equinox (OSGI implementation):

>start ant osgi

2. Check installed bundles in console:

osgi>ss .

Make sure that HelloWorld bundle is installed (see previous example).

3. Compile and jar example

>ant jar

4. Install bundle:

osgi>install file:target/HelloWorldKiller.jar

Check ID for installed bundle (e.g. 2)

osgi>ss .

5. Start bundle by using correct ID:

osgi>start 2

Check if HelloWorld bundle is uninstalled:

osgi>ss .
