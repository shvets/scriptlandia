    How to support dependencies between bundles

Details: http://neilbartlett.name/blog/osgi-articles

1. Run equinox (OSGI implementation):

>start ant osgi

Check installed bundles in console:

osgi>ss .

2. Compile and jar 2 bundles

>ant jar

3. Install bundle 1 (id: 4):

osgi>install file:target/BasicMovieFinder.jar

You cannot start this bundle yet:

osgi>diag 4

You can see missing dependencies here.

4. Now install another bundle:

osgi>install file:target/MoviesInterface.jar 

Check ID for installed bundle (e.g. 5)

osgi>ss .

Refresh statuses:

osgi>refresh

You'll see all statuses as RESOLVED

5. Start bundle 1 by using correct ID:

osgi>start 4


