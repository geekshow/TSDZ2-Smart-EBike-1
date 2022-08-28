@echo off
CALL clean.bat

PATH = %PATH%;%~dp0..\..\tools\cygwin\bin;C:\SDCC\usr\local\bin;C:\SDCC\bin;C:\Program Files\SDCC\usr\local\bin;C:\Program Files\SDCC\bin;
echo Build started...
timeout /t 2 > nul
make all
if errorlevel == 1 goto FAIL

:PASS
goto EXIT
:FAIL
echo Build error!!
pause
:EXIT
exit /b %ERRORLEVEL%