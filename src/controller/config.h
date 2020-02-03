/*
 * config.h
 * parameters configuration file
 * for TongSheng TSDZ2 motor controller firmware
 * by (C)Casainho and EndlessCadence and Leon, 20 beta 1 version
 * adapted to stock displays VLCD5 VLCD6 XH18
 * from an idea of marcoq (Jobike forum)
 * Author: mbrusa
 * Version mb.20beta1.A
 */
 
#ifndef CONFIG_H_
#define CONFIG_H_

#define DEBUG_MODE                                  0
#define DEBUG_DATA                                  1

// --------------------------------------------------------------------------
// MOTOR
// --------------------------------------------------------------------------

// motor type (0=48V 1=36V)
// DO NOT ENTER OTHER VALUES!
#define MOTOR_TYPE                                  1

// experimental high cadence mode (1=ENABLED)
#define EXPERIMENTAL_HIGH_CADENCE_MODE              0

// motor acceleration adjustment
#define MOTOR_ACCELERATION                          25
/* VALUES NEED VALIDATION FROM USER FEEDBACK
	Default value = 0 %
	36 volt motor, 36 volt battery = 35 %
	36 volt motor, 48 volt battery = 5 %
	36 volt motor, 52 volt battery = 0 %
	48 volt motor, 36 volt battery = 45 %
	48 volt motor, 48 volt battery = 35 %
	48 volt motor, 52 volt battery = 30 % */

// assist without pedal rotation (1=ENABLED)
#define MOTOR_ASSISTANCE_WITHOUT_PEDAL_ROTATION     0

// assist without pedal rotation threshold
// max value 100, recommended range between 10-20
#define ASSISTANCE_WITHOUT_PEDAL_ROTATION_THRESHOLD 20

// pedal torque conversion (optional calibration)
#define PEDAL_TORQUE_PER_10_BIT_ADC_STEP_X100       67

// cadence sensor high percentage (calibration required)
#define CADENCE_SENSOR_PULSE_HIGH_PERCENTAGE_X10    500

  
// --------------------------------------------------------------------------
// BATTERY
// --------------------------------------------------------------------------

// maximum battery current (Amp)
#define BATTERY_CURRENT_MAX                         17

// battery capacity (Watt/h)
#define TARGET_MAX_BATTERY_POWER                    630

// number of cells in series (7=24V 10=36V 13=48V 14=52V)
#define BATTERY_CELLS_NUMBER                        10

// battery low-cut-off voltage (Volt)
#define BATTERY_LOW_VOLTAGE_CUT_OFF                 29




// --------------------------------------------------------------------------
// BIKE
// --------------------------------------------------------------------------

// wheel perimeter(mm)
#define WHEEL_PERIMETER                             2280

// wheel max speed (km/h)
#define WHEEL_MAX_SPEED                             45

// --------------------------------------------------------------------------
// DISPLAY
// --------------------------------------------------------------------------

// display type
#define ENABLE_VLCD6                                0
#define ENABLE_VLCD5                                1
#define ENABLE_XH18                                 0

#endif // CONFIG_H_


