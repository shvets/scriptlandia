SET LAUNCHER_HOME=c:/launcher
SET REPOSITORY_HOME=c:/maven-repository

ant -Dlauncher.home=%LAUNCHER_HOME% -Drepository.home=%REPOSITORY_HOME% ^
    -Dlauncher.version=2.2.2 -Dclassworlds.version=1.1
