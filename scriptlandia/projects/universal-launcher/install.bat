SET LAUNCHER_HOME=c:/scriptlandia/launcher
SET REPOSITORY_HOME=c:/maven-repository

ant -Dlauncher.home=%LAUNCHER_HOME% -Drepository.home=%REPOSITORY_HOME% ^
    -Dlauncher.version=1.0 -Dclassworlds.version=1.1
