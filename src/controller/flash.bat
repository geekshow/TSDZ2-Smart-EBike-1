@echo off
PATH = %PATH%;C:\STMicroelectronics\st_toolset\stvp;"C:\Program Files (x86)\STMicroelectronics\st_toolset\stvp"

STVP_CmdLine -BoardName=ST-LINK -ProgMode=SWIM -Port=USB -Device=STM8S105x6 -FileProg=main.ihx -FileData=data.ihx -verbose -no_loop -verif -no_warn_protect
if errorlevel == 1 goto FAIL

:PASS
goto EXIT
:FAIL
pause
:EXIT
exit
