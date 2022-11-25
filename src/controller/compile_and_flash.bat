@echo off
CALL compile.bat
if errorlevel == 1 goto EXIT

echo Press any key to flash... (Ctl+C to stop)
pause > nul
CALL flash.bat

exit /b %ERRORLEVEL%