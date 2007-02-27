mvn archetype:create \
    -DarchetypeVersion=1.0-SNAPSHOT
    -DarchetypeGroupId=org.codehaus.plexus.examples -DarchetypeArtifactId=plexus-examples-tutorial \
    -DartifactId=tutorial -DgroupId=test -DremoteRepositories=http://snapshots.repository.codehaus.org/ 

cd tutorial

mvn package exec:java
