   @echo off

rem   Compile and run Win32 Shootout programs
rem   --------------------------------------------------
rem
   echo.
   @echo off
cd ackermann
   @echo on
call nicec --sourcepath=.. -d=. -a ackermann.jar ackermann
   @echo on
java -ea -jar ackermann.jar 8


   @echo off
   echo.
cd ..\ary3
   @echo on
call nicec --sourcepath=.. -d=. -a ary3.jar ary3
   @echo on
java -jar ary3.jar 7000



   @echo off
   echo.
cd ..\echo
   @echo on
call nicec --sourcepath=.. -d=. -a echo.jar echo
   @echo on
java -jar echo.jar 100000


   @echo off
   echo.
cd ..\except
   @echo on
call nicec --sourcepath=.. -d=. -a except.jar except
   @echo on
java -jar except.jar 200000


   @echo off
   echo.
cd ..\fibo
   @echo on
call nicec --sourcepath=.. -d=. -a fibo.jar fibo
   @echo on
java -ea -jar fibo.jar 32



   @echo off
   echo.
cd ..\hash
   @echo on
call nicec --sourcepath=.. -d=. -a hash.jar hash
   @echo on
java -jar hash.jar 80000


   @echo off
   echo.
cd ..\hash2
   @echo on
call nicec --sourcepath=.. -d=. -a hash2.jar hash2
   @echo on
java -jar hash2.jar 150


   @echo off
   echo.
cd ..\heapsort
   @echo on
call nicec --sourcepath=.. -d=. -a heapsort.jar heapsort
   @echo on
java -jar heapsort.jar 80000


   @echo off
   echo.
cd ..\hello
   @echo on
call nicec --sourcepath=.. -d=. -a hello.jar hello
   @echo on
java -jar hello.jar



   @echo off
   echo.
cd ..\lists
   @echo on
call nicec --sourcepath=.. -d=. -a lists.jar lists
   @echo on
java -jar lists.jar 16


   @echo off
   echo.
cd ..\matrix
   @echo on
call nicec --sourcepath=.. -d=. -a matrix.jar matrix
   @echo on
java -jar matrix.jar 300


   @echo off
   echo.
cd ..\methcall
   @echo on
call nicec --sourcepath=.. -d=. -a methcall.jar methcall
   @echo on
java -jar methcall.jar 1000000



   @echo off
   echo.
cd ..\moments
   @echo on
call nicec --sourcepath=.. -d=. -a moments.jar moments
   @echo on
java -jar moments.jar < input.txt


   @echo off
   echo.
cd ..\nestedloop
   @echo on
call nicec --sourcepath=.. -d=. -a nestedloop.jar nestedloop
   @echo on
java -jar nestedloop.jar 16


   @echo off
   echo.
cd ..\objinst
   @echo on
call nicec --sourcepath=.. -d=. -a objinst.jar objinst
   @echo on
java -jar objinst.jar 1000000


   @echo off
   echo.
cd ..\prodcons
   @echo on
call nicec --sourcepath=.. -d=. -a prodcons.jar prodcons
   @echo on
java -jar prodcons.jar 100000


   @echo off
   echo.
cd ..\random
   @echo on
call nicec --sourcepath=.. -d=. -a random.jar random
   @echo on
java -jar random.jar 900000


   @echo off
   echo.
cd ..\regexmatch
   @echo on
call nicec --sourcepath=.. -d=. --classpath ..\Include\jakarta-oro-2.0.7.jar -a regexmatch.jar regexmatch
   jar umf ..\Include\oro-classpath.txt regexmatch.jar
   @echo on
java -jar regexmatch.jar < input.txt 9000


   @echo off
   echo.
cd ..\reversefile
   @echo on
call nicec --sourcepath=.. -d=. -a reversefile.jar reversefile
   @echo on
java -jar reversefile.jar < input.txt > out.txt


   @echo off
   echo.
cd ..\sieve
   @echo on
call nicec --sourcepath=.. -d=. -a sieve.jar sieve
   @echo on
java -jar sieve.jar 900


   @echo off
   echo.
cd ..\spellcheck
   @echo on
call nicec --sourcepath=.. -d=. -a spellcheck.jar spellcheck
   @echo on
java -jar spellcheck.jar < input.txt


   @echo off
   echo.
cd ..\strcat
   @echo on
call nicec --sourcepath=.. -d=. -a strcat.jar strcat
   @echo on
java -jar strcat.jar 40000


   @echo off
   echo.
cd ..\sumcol
   @echo on
call nicec --sourcepath=.. -d=. -a sumcol.jar sumcol
   @echo on
java -jar sumcol.jar < input.txt


   @echo off
   echo.
cd ..\wc
   @echo on
call nicec --sourcepath=.. -d=. -a wc.jar wc
   @echo on
java -jar wc.jar < input.txt


   @echo off
   echo.
cd ..\wordfreq
   @echo on
call nicec --sourcepath=.. -d=. -a wordfreq.jar wordfreq
   @echo on
java -jar wordfreq.jar < input.txt > out.txt

   @echo off
cd ..