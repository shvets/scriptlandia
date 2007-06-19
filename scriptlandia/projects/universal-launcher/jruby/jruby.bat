rem SET IDEA_HOME="C:\Editors\IntelliJ IDEA 7020"
rem SET IDEA_PROPERTIES=%IDEA_HOME%\bin\idea.properties
rem SET PATH="%IDEA_HOME%\bin";%PATH%

..\launcher.bat "-deps.file.name=%CD%\deps.xml" "-main.class.name=org.jruby.Main" %*
