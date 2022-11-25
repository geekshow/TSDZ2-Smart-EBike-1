@echo off
PATH = %PATH%;%~dp0..\..\tools\cygwin\bin;
del /q main.ihx >NUL 2>NUL
make clean || goto FAIL
echo.

:PASS
goto EXIT
:FAIL
echo Cleaning failed!!
pause
:EXIT
exit /b %ERRORLEVEL%