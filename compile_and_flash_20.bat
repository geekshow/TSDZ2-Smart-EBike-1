@ECHO OFF

SET home_dir=%~dp0
SET release_folder=releases\20.1

    CD src\controller
    CALL compile_and_flash.bat || GOTO :error
    CD %home_dir%                
    COPY .\bin\main.ihx %release_folder%\TSDZ2-mb.20beta1.B-PROGRAM.hex

ECHO:
EXIT /b %errorlevel%

@ECHO ON