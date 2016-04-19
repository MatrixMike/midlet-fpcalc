@echo off
rem
rem This batch file builds and preverifies the code for the demos.
rem it then packages them in a JAR file appropriately.
rem
if "%OS%" == "Windows_NT" setlocal
set DEMO=FPDemo
set LIB_DIR=..\..\..\lib
set CLDCAPI=%LIB_DIR%\cldcapi11.jar
set MIDPAPI=%LIB_DIR%\midpapi20.jar
set PREVERIFY=..\..\..\bin\preverify

set JAVA_FILES=..\src\calculator\*.java

set JAVAC=javac
set JAR=jar

if not "%JAVA_HOME%" == "" (
    set JAVAC=%JAVA_HOME%\bin\javac
    set JAR=%JAVA_HOME%\bin\jar
)

if not exist .\%DEMO%.jad (
  echo *** Run this batch file from its location directory only. ***
  goto end
)

echo *** Creating directories ***
if not exist ..\tmpclasses md ..\tmpclasses
if not exist ..\classes md ..\classes

echo *** Compiling source files ***
%JAVAC% -bootclasspath %CLDCAPI%;%MIDPAPI% -source 1.8 -target 1.8 -d ..\tmpclasses -classpath ..\tmpclasses %JAVA_FILES%
rem above line was -source 1.3 ; changed to -source 1.8
echo *** Preverifying class files ***

rem WARNING: When running under windows 9x the JAR may be incomplete
rem due to a bug in windows 98. Simply place a pause statement between
rem the preverify and JAR stages and wait 5 seconds before continuing
rem the build.

%PREVERIFY% -classpath %CLDCAPI%;%MIDPAPI%;..\tmpclasses -d ..\classes ..\tmpclasses

echo *** Jaring preverified class files ***
%JAR% cmf MANIFEST.MF %DEMO%.jar -C ..\classes .

if exist ..\res (
    echo *** Jaring resource files ***
    %JAR% uf %DEMO%.jar -C ..\res .
)

echo ***
echo *** Don't forget to update the JAR file size in the JAD file!!! ***
echo ***

:end
if "%OS%" == "Windows_NT" endlocal

rem do a "pause" always
pause
