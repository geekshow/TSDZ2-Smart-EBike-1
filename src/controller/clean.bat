@echo off
PATH = %PATH%;%~dp0..\..\tools\cygwin\bin;
make clean
echo.

:PASS
goto EXIT
:FAIL
echo Cleaning failed!!
exit /b %ERRORLEVEL%
pause
:EXIT