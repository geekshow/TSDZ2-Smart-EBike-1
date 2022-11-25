@ECHO OFF

SET Version = 20.1
SET settings_date=%1

SET release_folder=%~dp0releases\%Version%
SET backup_folder=%~dp0releases\backup

CD src\controller
CALL clean.bat      || GOTO :EXIT
CALL compile.bat    || GOTO :EXIT

ECHO Copying firmware to release folder.
ECHO %release_folder%\TSDZ2-mb.20beta1.B-PROGRAM.hex
MKDIR %release_folder%
COPY ..\..\bin\main.ihx %release_folder%\TSDZ2-mb.20beta1.hex
MKDIR %backup_folder%
COPY ..\..\bin\main.ihx %backup_folder%\TSDZ2-%settings_date%.ihx

echo Press any key to flash... (Ctl+C to stop)
pause > nul
CALL flash.bat
    

:EXIT
EXIT