rem SET IDEA_HOME=C:\Editors\IntelliJ IDEA-4192

rem SET IDEA_HOME=C:/Editors/IntelliJ IDEA 5.1.2
SET IDEA_HOME=C:/Editors/IntelliJ IDEA 6.0

idea.bsh -Xbootclasspath/p:%IDEA_HOME%\lib\boot.jar;%IDEA_HOME%\lib\jdom.jar;%IDEA_HOME%\lib\log4j.jar -Didea.no.jdk.check=true -Didea.home=%IDEA_HOME% %*