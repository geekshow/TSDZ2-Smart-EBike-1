@echo off
PATH = %PATH%;%~dp0..\..\tools\cygwin\bin;%~dp0..\..\tools\tool-stm8flash;

make flash
@REM make clear_eeprom
if errorlevel == 1 goto FAIL

:PASS
goto EXIT
:FAIL
echo Flashing error!!
:EXIT
pause
exit /b %ERRORLEVEL%