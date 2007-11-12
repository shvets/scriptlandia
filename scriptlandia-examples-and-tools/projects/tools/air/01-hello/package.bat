SET JAVA_HOME=C:\Java\jdk1.5.0
SET AIR_HOME=C:\Java\air-sdk

%JAVA_HOME%\bin\java -jar %AIR_HOME%\lib\adt.jar -package -certificate sampleCert.pfx -password pwd AIRHelloWorld.air AIRHelloWorld.xml AIRHelloWorld.html
 
