SET IDEA_HOME=C:\Editors\IntelliJ IDEA-4192

diff.bsh "-Xbootclasspath/p:%IDEA_HOME%\lib\boot.jar" "-Didea.home=%IDEA_HOME%" -gui %*
