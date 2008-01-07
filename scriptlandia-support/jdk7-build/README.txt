How to build OpenJDK 7.x on x86 Windows Platform

1. Download and install/unzip the following projects:

1.1. Stable binary version of Java. It could be the latest jdk6 (http://java.sun.com/javase/downloads)
or latest jdk7 (http://download.java.net/jdk7/binaries). We call it as bootable JVM. 
Install it into ${boot.dir} directory.

1.2. Latest binary of jdk7 (http://download.java.net/jdk7/binaries). It will be installed into ${jdk.import.path}.
The difference between bootable and this jdk is that we use first for actual build and second - for
importing pre-built components like the VM. It could be the same jdk though.

1.3. Latest source snapshot for Open JDK (http://download.java.net/jdk7). Install it into ${jdk7.sources} directory.

1.4. If you have access to mercurial rerository, check out latest build files from rerository to ${jdk7.sources} directory:

>hg clone http://hg.openjdk.java.net/jdk7/jdk7 ${jdk7.sources}

1.5. Download and install openjdk binary plugs (http://download.java.net/openjdk/jdk7) 
into ${jdk7.binary.plugs} directory.

1.6. Download and install latest (1.7.0) Ant build project (http://ant.apache.org/bindownload.cgi).

1.7. Download and install latest (1.3.1) FindBugs project (http://findbugs.sourceforge.net/downloads.html).

1.8. Install latest version of Cygwin (http://www.cygwin.com). You have to choose dos/text mode and
install Developer, System, Utils, Archive packages in full mode.

***
? For the build, we need make 3.80, so if the cygwin make in not 3.80, download make bundle from http://cygwin.paracoda.com/release/make/make-3.80-1.tar.bz2 
and untar it in a separate folder.

2) Downgraded make to 3.80

    Download from 
http://cygwin.paracoda.com/release/make/make-3.80-1.tar.bz2

  cd /
  tar xjf /cygdrive/c/where-your-download-is/make-3.80-1.tar.bz2
  bin/make.exe --version

  GNU Make 3.80
  Copyright (C) 2002  Free Software Foundation, Inc.
***


1.9. Install Freetype 2.3.5 (http://sourceforge.net/project/showfiles.php?group_id=3157).


1.10. Install Microsoft's Genuine Windows Validation Tool:

http://www.microsoft.com/downloads/exeValidation2.aspx?familyId=0baf2b35-c656-4969-ace8-e4c0c0716adb&displayLang=en

1.11. Microsoft Platform SDK for Windows Server 2003 R2

http://www.microsoft.com/downloads/details.aspx?FamilyId=0BAF2B35-C656-4969-ACE8-E4C0C0716ADB&displaylang=en

- Configuration Options | Register Environmen Variables
- Microsoft Windows Core SDK

1.12. Install Microsoft Visual Studio .NET 2003 Professional


1.13. Install Microsoft DirectX 9.0 SDK

1.14. ? - Download and install Microsoft Unicode library



Useful links:

1. http://mail.openjdk.java.net/pipermail/build-dev/2007-August/000182.html
2. http://blogs.sun.com/poonam/entry/building_openjdk_on_windows
3. http://java4ever.blogspot.com/2007/12/openjdk-build-on-netbeans-with-windows_01.html
