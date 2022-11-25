[![Build Action](../../actions/workflows/build.yaml/badge.svg)](../../actions/workflows/build.yaml)

This repository is updated by mbrusa.

This fork is based on the TSDZ2-v0.20beta1 adapted for Tongsheng protocol displays, like stock VLCD5, VLCD6, XH18 or other displays with the same protocol and 6-pin Tonsheng connector, SW102, DZ41 from Enerprof or 850C for TSDZ2.
With these last displays, the visualization of data and errors must be checked..

Endless Sphere forum reference thread: [endless-sphere.com.](https://endless-sphere.com/forums/viewtopic.php?f=30&t=110682).

Italian forum reference thread: [jobike.it.](http://www.jobike.it/forum/topic.asp?TOPIC_ID=76426&whichpage=61).


This ebike motor controller firmware project is to be used with the Tongsheng TSDZ2 mid drive motor. 
Note: firmware can't be written to Enerdan sold TSDZ2 motors and controllers because they are equipped with V2 controller with XMC1300 microprocessor instead of STM8.

It has the following benefits compared to the stock firmware:
* The motor runs more efficient therefore it becomes more powerful and consumes less energy.
* The ebike will feel more responsive and agile.
* Using other supported displays and pheriperals will provide more functionality and features. 
* Because this project is in development more features will be added in future.

This project is being developed and maintained for free by a community of users. Some of them are developers who work professionally developing this type of technology for very well known companies.

## Building and flashing
### Windows 
- Install [SDCC](http://sdcc.sourceforge.net/index.php#Download).
- Install [Java](https://www.java.com/endownload/).
- Open JavaConfigurator.jar
- Or use supplied .bat scripts, e.g. `src/controller/compile_and_flash.bat` 

### Linux
```
cd src/controller
make
```

# For more information, go to the [wiki](https://github.com/emmebrusa/TSDZ2-Smart-EBike-1/wiki) instructions.


**IMPORTANT NOTES**
* Installing this firmware will void your warranty of the TSDZ2 mid drive.
* We are not responsible for any personal injuries or accidents caused by use of this firmware.
* There is no guarantee of safety using this firmware, please use it at your own risk.
* We advise you to consult the laws of your country and tailor the motor configuration accordingly.
* Please be aware of your surroundings and maintain a safe riding style.
