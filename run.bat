@echo off
rem This file runs the corresponded demo.

if "%OS%" == "Windows_NT" setlocal
set DEMO=FPDemo

if not exist .\%DEMO%.jad (
  echo *** Run this batch file from its location directory only. ***
  goto end
)

if not exist .\%DEMO%.jar (
  echo *** You should build the %DEMO%.jar first. ***
  goto end
)

rem Enter the common 'bin' directory of WTK.
cd ..\..\..\bin

rem Run a WTK with the specified demo.
emulator -Xdescriptor:..\apps\%DEMO%\bin\%DEMO%.jad

rem Return back to 'bin' directory of the demo.
cd ..\apps\%DEMO%\bin

:end
if "%OS%" == "Windows_NT" endlocal

rem do a "pause" always
pause
