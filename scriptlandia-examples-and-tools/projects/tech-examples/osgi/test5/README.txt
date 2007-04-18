    How to call bundle-service from another bundle

Details: http://neilbartlett.name/blog/osgi-articles

1. Run equinox (OSGI implementation):

>start ant osgi

Check installed bundles in console:

osgi>ss .

Uninstall old bundles - they will be installed as part of this demo.

2. Compile and jar 3 bundles

>ant jar

3. Install bundle 1 (id: 1):

osgi>install file:target/BasicMovieFinder.jar

4. Install bundle 2 (id: 2):

osgi>install file:target/MoviesInterface.jar

5. Install bundle 3 (id: 3):

osgi>install file:target/MovieLister.jar

6. Start bundle 3 by using correct ID:

osgi>start 3

At this time it tries to connect to not-yest-started service. You'll get message:
"Could not retrieve movie list".

7. Start bundle 1 (service) by using correct ID:

osgi>start 1

8. Stop and then start bundle 3 again: 

osgi>stop 3
osgi>start 3

This time you should ge result.
