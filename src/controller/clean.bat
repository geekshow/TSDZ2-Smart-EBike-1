@echo off
PATH = %PATH%;%~dp0..\..\tools\cygwin\bin;
make clean || goto FAIL
echo.

:PASS
goto EXIT
:FAIL
echo Cleaning failed!!
pause
:EXIT
exit /b %ERRORLEVEL%