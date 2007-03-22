SET JAVA_HOME=c:\Java\jdk1.6.0

SET REPOSITORY_HOME=c:\maven-repository
SET LANGUAGE=jaskell

SET CLASSPATH=%REPOSITORY_HOME%\com\sun\script\jaskell-engine\1.0\jaskell-engine-1.0.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\com\sun\script\jaskell-engine\1.0\jaskell-engine-1.0.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\jaskell\jaskell\1.0\jaskell-1.0.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\jaskell\jfunutil\1.0\jfunutil-1.0.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\jaskell\jparsec\1.0\jparsec-1.0.jar
SET CLASSPATH=%CLASSPATH%;%REPOSITORY_HOME%\commons-lang\commons-lang\2.1\commons-lang-2.1.jar

%JAVA_HOME%\bin\jrunscript -cp %CLASSPATH% -l %LANGUAGE% %*
