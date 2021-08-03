@echo off
echo v20.1C.3-NEW is temporarily suspended, use v20.1C.1
pause
goto EXIT

SET home_dir=%~dp0
SET release_folder=releases\20.1

    CD src\controller
    CALL compile_and_flash.bat || GOTO :error
    CD %home_dir%                
    COPY .\src\controller\main.ihx %release_folder%\TSDZ2-mb.20beta1.B-PROGRAM.hex

ECHO:
EXIT /b %errorlevel%

@ECHO ON