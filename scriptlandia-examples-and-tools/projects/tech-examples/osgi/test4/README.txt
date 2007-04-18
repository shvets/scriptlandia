    How to register bundle as a service

Details: http://neilbartlett.name/blog/osgi-articles

1. Run equinox (OSGI implementation):

>start ant osgi

Check installed bundles in console:

osgi>ss .

2. Compile and jar 2 bundles

>ant jar

3. Install (or update if exists) bundle 1 (id: 4):

osgi>install file:target/BasicMovieFinder.jar

or 

osgi>update 4

4. Install (or update) another bundle:

osgi>install file:target/MoviesInterface.jar

or 

osgi>update 5

Refresh statuses:

osgi>refresh

You'll see all statuses as RESOLVED

5. Start bundle 1 by using correct ID:

osgi>start 4

No visual effect, but service is registered:

osgi>services (objectClass=*MovieFinder)


