@echo off 

if exist projects\bootstrap-mini\target  rmdir /S /Q projects\bootstrap-mini\target
if exist projects\pomreader\target rmdir /S /Q projects\pomreader\target
if exist projects\scriptlandia-common\target rmdir /S /Q projects\scriptlandia-common\target
if exist projects\antrun\target rmdir /S /Q projects\antrun\target
if exist projects\classworlds-launcher\target rmdir /S /Q projects\classworlds-launcher\target

if exist projects\scriptlandia-launcher\target rmdir /S /Q projects\scriptlandia-launcher\target
if exist projects\scriptlandia-installer\target rmdir /S /Q projects\scriptlandia-installer\target
if exist projects\scriptlandia-config\target rmdir /S /Q projects\scriptlandia-config\target
if exist projects\scriptlandia-nailgun\target rmdir /S /Q projects\scriptlandia-nailgun\target
if exist projects\scriptlandia-startup\target rmdir /S /Q projects\scriptlandia-startup\target
if exist projects\scriptlandia\target rmdir /S /Q projects\scriptlandia\target

if exist target rmdir /S /Q target
if exist classes rmdir /S /Q classes

@call mvn -f pom.xml clean