/*
 * TongSheng TSDZ2 motor controller firmware/
 *
 * Copyright (C) Casainho and Leon, 2019.
 *
 * Released under the GPL License, Version 3
 */

#ifndef _MAIN_H_
#define _MAIN_H_

#include "config.h"
#include "common.h"

//#define DEBUG_UART

// motor 
#define PWM_CYCLES_COUNTER_MAX                                    3125    // 5 erps minimum speed -> 1/5 = 200 ms; 200 ms / 64 us = 3125
#define PWM_CYCLES_SECOND                                         15625   // 1 / 64us(PWM period)
#define PWM_DUTY_CYCLE_MAX                                        254
#define MIDDLE_PWM_DUTY_CYCLE_MAX                                 (PWM_DUTY_CYCLE_MAX / 2)

#define PWM_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_DEFAULT               160     // 160 -> 160 * 64 us for every duty cycle increment
#define PWM_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_MIN                   20      // 20 -> 20 * 64 us for every duty cycle increment

#define PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_DEFAULT             40      // 40 -> 40 * 64 us for every duty cycle decrement
#define PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_MIN     (PWM_DUTY_CYCLE_RAMP_DOWN_MIN_ADDITIONAL + 8) // 8 -> 8 * 64 us for every duty cycle decrement

/*---------------------------------------------------------
  NOTE: regarding duty cycle (PWM) ramping
  
  Do not change these values if not sure of the effects!
  
  A lower value of the duty cycle inverse step will mean
  a faster acceleration. Be careful not to choose too
  low values for acceleration.
---------------------------------------------------------*/



#define MOTOR_ROTOR_OFFSET_ANGLE                                  10
#define MOTOR_ROTOR_ANGLE_90                                      (63  + MOTOR_ROTOR_OFFSET_ANGLE)
#define MOTOR_ROTOR_ANGLE_150                                     (106 + MOTOR_ROTOR_OFFSET_ANGLE)
#define MOTOR_ROTOR_ANGLE_210                                     (148 + MOTOR_ROTOR_OFFSET_ANGLE)
#define MOTOR_ROTOR_ANGLE_270                                     (191 + MOTOR_ROTOR_OFFSET_ANGLE)
#define MOTOR_ROTOR_ANGLE_330                                     (233 + MOTOR_ROTOR_OFFSET_ANGLE)
#define MOTOR_ROTOR_ANGLE_30                                      (20  + MOTOR_ROTOR_OFFSET_ANGLE)

/*---------------------------------------------------------
  NOTE: regarding motor rotor offset 
  
  The motor rotor offset should be as close to 0 as 
  possible. You can try to tune with the wheel in the air,
  full throttle and look at the batttery current. Adjust 
  for the lowest battery current possible.
---------------------------------------------------------*/



#define MOTOR_OVER_SPEED_ERPS                                     520     // motor max speed, protection max value | 30 points for the sinewave at max speed
#define MOTOR_OVER_SPEED_ERPS_EXPERIMENTAL                        700     // experimental motor speed to allow a higher cadence

#define MOTOR_ROTOR_ERPS_START_INTERPOLATION_60_DEGREES           10

/*---------------------------------------------------------
  NOTE: regarding motor start interpolation
  
  This value is the ERPS speed after which a transition 
  happens from sinewave and no interpolation to 
  interpolation 60 degrees. Must be found experimentally 
  but a value of 25 may be good.
---------------------------------------------------------*/


//#define ADC_10_BIT_BATTERY_CURRENT_MAX                            106     // 18 amps
//#define ADC_10_BIT_MOTOR_PHASE_CURRENT_MAX                        177     // 30 amps
#define ADC_10_BIT_BATTERY_CURRENT_MAX                            112     // 18 amps
#define ADC_10_BIT_MOTOR_PHASE_CURRENT_MAX                        187     // 30 amps


/*---------------------------------------------------------
  NOTE: regarding ADC battery current max
  
  This is the maximum current in ADC steps that the motor 
  will be able to draw from the battery. A higher value 
  will give higher torque figures but the limit of the 
  controller is 16 A and it should not be exceeded.
---------------------------------------------------------*/


// moved to config.h
// throttle ADC values
//#define ADC_THROTTLE_MIN_VALUE                                    47
//#define ADC_THROTTLE_MAX_VALUE                                    176

/*---------------------------------------------------------
  NOTE: regarding throttle ADC values

  Max voltage value for throttle, in ADC 8 bits step, 
  each ADC 8 bits step = (5 V / 256) = 0.0195

---------------------------------------------------------*/



// cadence sensor
#define CADENCE_SENSOR_NUMBER_MAGNETS                             20
#define CADENCE_SENSOR_NUMBER_MAGNETS_X2                          (CADENCE_SENSOR_NUMBER_MAGNETS * 2)

#define CADENCE_SENSOR_TICKS_COUNTER_MAX                          300
#define CADENCE_SENSOR_TICKS_COUNTER_MIN                          10000

#define CADENCE_SENSOR_PULSE_PERCENTAGE_X10_DEFAULT               500
#define CADENCE_SENSOR_PULSE_PERCENTAGE_X10_MAX                   800
#define CADENCE_SENSOR_PULSE_PERCENTAGE_X10_MIN                   200

/*-------------------------------------------------------------------------------
  NOTE: regarding the cadence sensor
  
  CADENCE_SENSOR_NUMBER_MAGNETS = 20, this is the number of magnets used for
  the cadence sensor. Was validated on August 2018 by Casainho and jbalat
  
  x = (1/(150RPM/60)) / (0.000064)
  
  6250 / CADENCE_SENSOR_NUMBER_MAGNETS ≈ 313 -> 150 RPM
  
  93750 / CADENCE_SENSOR_NUMBER_MAGNETS ≈ 4688 -> 10 RPM
  
  CADENCE_SENSOR_TICKS_COUNTER_MAX = x / CADENCE_SENSOR_NUMBER_MAGNETS
  

  
  CADENCE_SENSOR_NUMBER_MAGNETS_X2 = 40, this is the number of transitions 
  in one crank revolution
  
  x = (1/(150RPM/60)) / (0.000064)
  
  6250 / CADENCE_SENSOR_NUMBER_MAGNETS_X2 ≈ 156 -> 150 RPM
  
  93750 / CADENCE_SENSOR_NUMBER_MAGNETS_X2 ≈ 2344 -> 10 RPM, or 5 RPM if set to around 4600
  
  CADENCE_SENSOR_TICKS_COUNTER_MAX = x / CADENCE_SENSOR_NUMBER_MAGNETS_X2
  
  
  Cadence is calculated by counting how much time passes between two 
  transitions. Depending on if all transitions are measured or simply 
  transitions of the same kind it is important to adjust the calculation of 
  pedal cadence. If measuring all transistions it is also important to 
  adjust for the different spacings between the transitions.
-------------------------------------------------------------------------------*/


// Wheel speed sensor
#define WHEEL_SPEED_SENSOR_TICKS_COUNTER_MAX                      135   // something like 200 m/h with a 6'' wheel
#define WHEEL_SPEED_SENSOR_TICKS_COUNTER_MIN                      32767 // could be a bigger number but will make for a slow detection of stopped wheel speed


// deleted for oem display
/*
// default values
#define DEFAULT_VALUE_TARGET_BATTERY_MAX_POWER_X10                50  // (500 watts) NOT USED
#define DEFAULT_VALUE_BATTERY_CURRENT_MAX                         17  // 17 amps
#define DEFAULT_VALUE_BATTERY_LOW_VOLTAGE_CUT_OFF_X10_0           44  // 36 V battery, LVC = 30.0 (3.0 * 13): (44 + (1 << 8))
#define DEFAULT_VALUE_BATTERY_LOW_VOLTAGE_CUT_OFF_X10_1           1
#define DEFAULT_VALUE_WHEEL_PERIMETER_0                           232 // 28 inch wheel: 2280 mm perimeter (232 + (8 << 8))
#define DEFAULT_VALUE_WHEEL_PERIMETER_1                           8
#define DEFAULT_VALUE_WHEEL_SPEED_MAX                             45  // 45 km/h
#define DEFAULT_VALUE_MOTOR_TYPE                                  1   // 36 volt motor
#define DEFAULT_VALUE_PEDAL_TORQUE_PER_10_BIT_ADC_STEP_X100       67
*/

/*---------------------------------------------------------

  NOTE: regarding the torque sensor output values

  Torque (force) value needs to be found experimentaly.
  
  One torque sensor ADC 10 bit step is equal to 0.38 kg
  
  Force (Nm) = 1 Kg * 9.81 * 0.17 (0.17 = arm cranks size)
---------------------------------------------------------*/


// ADC battery voltage measurement
#define BATTERY_VOLTAGE_PER_10_BIT_ADC_STEP_X512                  44
#define BATTERY_VOLTAGE_PER_10_BIT_ADC_STEP_X1000                 87  // conversion value verified with a cheap power meter

/*---------------------------------------------------------
  NOTE: regarding ADC battery voltage measurement

  0.344 per ADC 8 bit step:
  
  17.9 V -->  ADC 8 bits value  = 52; 
  40 V   -->  ADC 8 bits value  = 116; 
  
  This signal is atenuated by the opamp 358.
---------------------------------------------------------*/


// ADC battery current measurement
//#define BATTERY_CURRENT_PER_10_BIT_ADC_STEP_X512                  102
#define BATTERY_CURRENT_PER_10_BIT_ADC_STEP_X512                  80 // 1A per 6.4 steps of ADC_10bits (0.156A per each ADC step)
//#define BATTERY_CURRENT_PER_10_BIT_ADC_STEP_X100                  17 // conversion value verified with a cheap power meter
#define BATTERY_CURRENT_PER_10_BIT_ADC_STEP_X100                  16 // 15.62 rounded

// --------------------------------------------------------------------------
// for oem display

// UART
#define UART_RX_BUFFER_LEN   						7
#define RX_CHECK_CODE					(UART_RX_BUFFER_LEN - 1)															
#define UART_TX_BUFFER_LEN							9
#define TX_CHECK_CODE					(UART_TX_BUFFER_LEN - 1)
#define TX_STX										0x43
#define RX_STX										0x59

// parameters for display data
#if UNITS_TYPE          // 1 mph and miles
#define OEM_WHEEL_FACTOR							90
#else                   // 0 = km/h and kilometer
#define OEM_WHEEL_FACTOR							143
#endif

#define DATA_INDEX_ARRAY_DIM						6
#define DATA_VALUE_ARRAY_DIM						14

// delay display mode default (0.1 sec)
#define DELAY_DISPLAY_MODE_DEFAULT_X10	(uint16_t)(DELAY_DISPLAY_MODE_DEFAULT * 10)

// delay lights function (0.1 sec)
#define DELAY_LIGHTS_ON					DELAY_MENU_ON

// delay sensor calibration (0.1 sec)
#define DELAY_CALIBRATION_ON			(uint8_t)(DELAY_MENU_ON * 2)

// assist level 
#define OFF											0
#define ECO											1
#define TOUR										2
#define SPORT										3
#define TURBO										4

// assist pedal level mask
#define ASSIST_PEDAL_LEVEL0							0x10
#define ASSIST_PEDAL_LEVEL1							0x40
#define ASSIST_PEDAL_LEVEL2							0x02
#define ASSIST_PEDAL_LEVEL3							0x04
#define ASSIST_PEDAL_LEVEL4							0x08

// oem display fault & function code
#define CLEAR_DISPLAY								0
#define NO_FUNCTION									0
#define NO_FAULT									0
#define NO_ERROR                                  	0 
#define ERROR_MOTOR_BLOCKED                       	4 // E04
#define ERROR_TORQUE_SENSOR                       	2 // E02
#define ERROR_CADENCE_SENSOR_CALIBRATION          	3 // E03
#define ERROR_OVERTEMPERATURE						6 // E06
#define ERROR_OVERVOLTAGE							8 // E08
#define ERROR_WRITE_EEPROM  					  	9 // E09 (E08 blinking for XH18)


// optional ADC function
#if ENABLE_TEMPERATURE_LIMIT  && ENABLE_THROTTLE
#define OPTIONAL_ADC_FUNCTION                 		NOT_IN_USE
#elif ENABLE_TEMPERATURE_LIMIT
#define OPTIONAL_ADC_FUNCTION                 		TEMPERATURE_CONTROL
#elif ENABLE_THROTTLE && ENABLE_BRAKE_SENSOR
#define OPTIONAL_ADC_FUNCTION                 		THROTTLE_CONTROL
#else
#define OPTIONAL_ADC_FUNCTION                 		NOT_IN_USE
#endif

// assist without pedal rotation
#if MOTOR_ASSISTANCE_WITHOUT_PEDAL_ROTATION
#define ASSIST_WITHOUT_PEDAL_ROTATION_THRESHOLD		ASSISTANCE_WITHOUT_PEDAL_ROTATION_THRESHOLD
#else	
#define ASSIST_WITHOUT_PEDAL_ROTATION_THRESHOLD		0
#endif

// cruise pid parameter
#if MOTOR_TYPE
// 36 volt motor
#define CRUISE_PID_KP                             	14
#define CRUISE_PID_KI                             	0.7
#else
// 48 volt motor
#define CRUISE_PID_KP                             	12
#define CRUISE_PID_KI                             	1
#endif

// cadence sensor high percentage
#define CADENCE_SENSOR_PULSE_HIGH_PERCENTAGE_X10_0	(uint8_t) (CADENCE_SENSOR_PULSE_HIGH_PERCENTAGE_X10 & 0x00FF)
#define CADENCE_SENSOR_PULSE_HIGH_PERCENTAGE_X10_1	(uint8_t) ((CADENCE_SENSOR_PULSE_HIGH_PERCENTAGE_X10 >> 8) & 0x00FF)

// wheel perimeter
#define WHEEL_PERIMETER_0							(uint8_t) (WHEEL_PERIMETER & 0x00FF)
#define WHEEL_PERIMETER_1							(uint8_t) ((WHEEL_PERIMETER >> 8) & 0x00FF)

// wheel speed parameters
#define OEM_WHEEL_SPEED_DIVISOR						315


// BATTERY PARAMETERS
// battery low voltage cut off
#define BATTERY_LOW_VOLTAGE_CUT_OFF_X10_0		(uint8_t) ((uint16_t)(BATTERY_LOW_VOLTAGE_CUT_OFF * 10) & 0x00FF)
#define BATTERY_LOW_VOLTAGE_CUT_OFF_X10_1		(uint8_t) (((uint16_t)(BATTERY_LOW_VOLTAGE_CUT_OFF * 10) >> 8) & 0x00FF)
// battery voltage to be subtracted from the cut-off 8bit
#define DIFFERENCE_CUT_OFF_SHUTDOWN_8_BIT			24
// battery voltage for saving battery capacity at shutdown
#define BATTERY_VOLTAGE_SHUTDOWN_8_BIT			(uint8_t) ((uint16_t)(BATTERY_LOW_VOLTAGE_CUT_OFF * 250 / BATTERY_VOLTAGE_PER_10_BIT_ADC_STEP_X1000)) - ((uint16_t) DIFFERENCE_CUT_OFF_SHUTDOWN_8_BIT)
// max battery power div25
#define TARGET_MAX_BATTERY_POWER_DIV25			(uint8_t)(TARGET_MAX_BATTERY_POWER / 25)
// power street limit value div25
#define STREET_MODE_POWER_LIMIT_DIV25           (uint8_t)(STREET_MODE_POWER_LIMIT / 25)
// battery voltage reset SOC percentage
#define BATTERY_VOLTAGE_RESET_SOC_PERCENT_X10   (uint16_t)((float)LI_ION_CELL_RESET_SOC_PERCENT * (float)(BATTERY_CELLS_NUMBER * 10))
// battery SOC eeprom value
#define BATTERY_SOC_VALUE						0

// cell bars
#if ENABLE_VLCD6 || ENABLE_XH18
#define LI_ION_CELL_VOLTS_6			(float)LI_ION_CELL_OVERVOLT
#define LI_ION_CELL_VOLTS_5			(float)LI_ION_CELL_RESET_SOC_PERCENT
#define LI_ION_CELL_VOLTS_4			(float)LI_ION_CELL_VOLTS_FULL
#define LI_ION_CELL_VOLTS_3			(float)LI_ION_CELL_VOLTS_3_OF_4
#define LI_ION_CELL_VOLTS_2			(float)LI_ION_CELL_VOLTS_2_OF_4
#define LI_ION_CELL_VOLTS_1			(float)LI_ION_CELL_VOLTS_1_OF_4
#define LI_ION_CELL_VOLTS_0			(float)LI_ION_CELL_VOLTS_EMPTY
#else // ENABLE_VLCD5
#define LI_ION_CELL_VOLTS_8			(float)LI_ION_CELL_OVERVOLT
#define LI_ION_CELL_VOLTS_7			(float)LI_ION_CELL_RESET_SOC_PERCENT
#define LI_ION_CELL_VOLTS_6			(float)LI_ION_CELL_VOLTS_FULL
#define LI_ION_CELL_VOLTS_5			(float)LI_ION_CELL_VOLTS_5_OF_6
#define LI_ION_CELL_VOLTS_4			(float)LI_ION_CELL_VOLTS_4_OF_6
#define LI_ION_CELL_VOLTS_3			(float)LI_ION_CELL_VOLTS_3_OF_6
#define LI_ION_CELL_VOLTS_2			(float)LI_ION_CELL_VOLTS_2_OF_6
#define LI_ION_CELL_VOLTS_1			(float)LI_ION_CELL_VOLTS_1_OF_6
#define LI_ION_CELL_VOLTS_0			(float)LI_ION_CELL_VOLTS_EMPTY
#endif

// assist level 0
#define TORQUE_ASSIST_LEVEL_0        0
#define CADENCE_ASSIST_LEVEL_0       0
#define EMTB_ASSIST_LEVEL_0          0
#define WALK_ASSIST_LEVEL_0          0
#define CRUISE_TARGET_SPEED_LEVEL_0  0

// power assist level
#define POWER_ASSIST_LEVEL_OFF       0
#define POWER_ASSIST_LEVEL_ECO       (uint8_t)(POWER_ASSIST_LEVEL_1 / 10)
#define POWER_ASSIST_LEVEL_TOUR      (uint8_t)(POWER_ASSIST_LEVEL_2 / 10)
#define POWER_ASSIST_LEVEL_SPORT     (uint8_t)(POWER_ASSIST_LEVEL_3 / 10)
#define POWER_ASSIST_LEVEL_TURBO     (uint8_t)(POWER_ASSIST_LEVEL_4 / 10)

// walk assist threshold (speed limit max km/h x10)
#define WALK_ASSIST_THRESHOLD_SPEED_X10	(uint8_t)(WALK_ASSIST_THRESHOLD_SPEED * 10)

// cruise threshold (speed limit min km/h x10)
#define CRUISE_THRESHOLD_SPEED_X10		(uint8_t)(CRUISE_THRESHOLD_SPEED * 10)

// ticks target coefficient for fix overrun
#if PWM_DUTY_CYCLE_RAMP_DOWN_MIN_ADDITIONAL > 15
#define TICKS_TARGET_COEFFICIENT_STD			1
#define TICKS_TARGET_COEFFICIENT_ADV			1
#elif PWM_DUTY_CYCLE_RAMP_DOWN_MIN_ADDITIONAL > 7
#define TICKS_TARGET_COEFFICIENT_STD			2
#define TICKS_TARGET_COEFFICIENT_ADV			1
#elif PWM_DUTY_CYCLE_RAMP_DOWN_MIN_ADDITIONAL > 0
#define TICKS_TARGET_COEFFICIENT_STD			3
#define TICKS_TARGET_COEFFICIENT_ADV			1
#else
#define TICKS_TARGET_COEFFICIENT_STD			4
#define TICKS_TARGET_COEFFICIENT_ADV			2
#endif

// odometer compensation for displayed data (eeprom)
#define ODOMETER_COMPENSATION					0
// zero odometer compensation
#define ZERO_ODOMETER_COMPENSATION				100000000

// torque range adc min for remapping
#define ADC_TORQUE_RANGE_MIN		(uint16_t)(160 - ADC_TORQUE_OFFSET_ADJUSTMENT)


// debug
#ifndef DEBUG_MODE
#define DEBUG_MODE                              0
#define DEBUG_DATA                              0
#endif

#endif // _MAIN_H_