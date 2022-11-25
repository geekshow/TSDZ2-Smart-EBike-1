@ECHO OFF

SET version=20.1C.2-2
SET settings_date=%1

SET release_folder=%~dp0releases
SET backup_folder=%~dp0releases\backup

CD src\controller
CALL clean.bat      || GOTO :EXIT
CALL compile.bat    || GOTO :EXIT

ECHO Copying firmware to release folder.
ECHO %release_folder%\TSDZ2-%version%-PROGRAM.hex
COPY ..\..\bin\main.ihx %release_folder%\TSDZ2-%version%.hex
COPY ..\..\bin\main.ihx %backup_folder%\TSDZ2-%settings_date%.ihx >NUL 2>NUL
COPY ..\..\bin\main.ihx main.ihx >NUL 2>NUL

echo Press any key to flash... (Ctl+C to stop)
pause > nul
CALL flash.bat

:EXIT
EXIT