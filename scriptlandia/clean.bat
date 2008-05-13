@echo off 

@call config.bat

if exist projects\antrun\target rmdir /S /Q projects\antrun\target
if exist projects\scriptlandia-helper\target rmdir /S /Q projects\scriptlandia-helper\target
if exist projects\scriptlandia-installer\target rmdir /S /Q projects\scriptlandia-installer\target
if exist projects\scriptlandia-launcher\target rmdir /S /Q projects\scriptlandia-launcher\target
if exist projects\scriptlandia-nailgun\target rmdir /S /Q projects\scriptlandia-nailgun\target
if exist projects\scriptlandia\target rmdir /S /Q projects\scriptlandia\target

if exist target rmdir /S /Q target
if exist classes rmdir /S /Q classes

%JLAUNCHPAD_HOME%\jlaunchpad.bat "-deps.file.name=languages/maven/core/pom.xml" "-main.class.name=org.apache.maven.cli.MavenCli" -f pom.xml clean
