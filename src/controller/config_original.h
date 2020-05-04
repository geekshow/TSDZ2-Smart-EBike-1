/*
 * config.h
 * parameters configuration file
 * for TongSheng TSDZ2 motor controller firmware
 * by (C)Casainho and EndlessCadence and Leon, 20 beta 1 version
 * adapted to stock displays VLCD5 VLCD6 XH18
 * from an idea of marcoq (Jobike forum)
 * Author: mbrusa
 * Version mb.20beta1.B
 */
 

#ifndef CONFIG_H_
#define CONFIG_H_

// --------------------------------------------------------------------------
// DEBUG
// --------------------------------------------------------------------------

#define DEBUG_MODE                                  0
#define DEBUG_DATA                                  0

// --------------------------------------------------------------------------
// MOTOR
// --------------------------------------------------------------------------

// motor type (0=48V 1=36V)
// DO NOT ENTER OTHER VALUES!
#define MOTOR_TYPE                                  1

// experimental high cadence mode (1=ENABLED)
#define EXPERIMENTAL_HIGH_CADENCE_MODE              0

// motor acceleration adjustment
#define MOTOR_ACCELERATION                          0
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

// pedal torque conversion standard mode (optional calibration)
#define PEDAL_TORQUE_PER_10_BIT_ADC_STEP_X100       67

// pedal torque actual range (calibration required)
#define PEDAL_TORQUE_10_BIT_ADC_RANGE               160

// adc torque offset adjustement
#define ADC_TORQUE_OFFSET_ADJUSTMENT			    0

// cadence sensor high percentage (calibration required)
#define CADENCE_SENSOR_PULSE_HIGH_PERCENTAGE_X10    500

// ERROR_MOTOR_BLOCKED parameter
#define MOTOR_BLOCKED_COUNTER_THRESHOLD               2	 // from 1 to 10  (0.1 second)
#define MOTOR_BLOCKED_BATTERY_CURRENT_THRESHOLD_X10   30 // from 10 to 50 (Amp)
#define MOTOR_BLOCKED_ERPS_THRESHOLD                  20 // from 10 to 20 (ERPS)

// duty cycle ramp down additional (0 to 20)
#define PWM_DUTY_CYCLE_RAMP_DOWN_MIN_ADDITIONAL       0 


// --------------------------------------------------------------------------
// BATTERY
// --------------------------------------------------------------------------

// maximum battery current (Amp)
#define BATTERY_CURRENT_MAX                         15

// max battery power (W)
#define TARGET_MAX_BATTERY_POWER                    500

// battery capacity (Wh)
#define TARGET_MAX_BATTERY_CAPACITY                 630

// number of cells in series (7=24V 10=36V 13=48V 14=52V)
#define BATTERY_CELLS_NUMBER                        10

// battery internal resistance (milliohms)
#define BATTERY_PACK_RESISTANCE                     196

// battery low-cut-off voltage (Volt)
#define BATTERY_LOW_VOLTAGE_CUT_OFF                 29

// battery voltage calibration (95% to 105%)
#define ACTUAL_BATTERY_VOLTAGE_PERCENT              100

// battery capacity calibration (max 100%)
#define ACTUAL_BATTERY_CAPACITY_PERCENT				100

// li-ion cell
#define LI_ION_CELL_OVERVOLT                        4.35
#define LI_ION_CELL_RESET_SOC_PERCENT               4.05
// full
#define LI_ION_CELL_VOLTS_FULL                      3.95
// 4 bars
#define LI_ION_CELL_VOLTS_3_OF_4                    3.70
#define LI_ION_CELL_VOLTS_2_OF_4                    3.45
#define LI_ION_CELL_VOLTS_1_OF_4                    3.25
// 6 bars
#define LI_ION_CELL_VOLTS_5_OF_6                    3.85
#define LI_ION_CELL_VOLTS_4_OF_6                    3.70
#define LI_ION_CELL_VOLTS_3_OF_6                    3.55
#define LI_ION_CELL_VOLTS_2_OF_6                    3.40
#define LI_ION_CELL_VOLTS_1_OF_6                    3.25
// empty
#define LI_ION_CELL_VOLTS_EMPTY                     2.90


// --------------------------------------------------------------------------
// BIKE
// --------------------------------------------------------------------------

// wheel perimeter(mm)
#define WHEEL_PERIMETER                             2280

// wheel max speed (km/h)
#define WHEEL_MAX_SPEED                             45


// --------------------------------------------------------------------------
// FUNCTION
// --------------------------------------------------------------------------

// enable functions (1=ENABLED)
#define ENABLE_LIGHTS                               1
#define ENABLE_WALK_ASSIST                          1
#define ENABLE_BRAKE_SENSOR                         0
#define ENABLE_THROTTLE                             0
#define ENABLE_TEMPERATURE_LIMIT                    0

// FUNCTIONS ENABLED ON STARTUP
// street mode (0=OFFROAD 1=STREET)
#define ENABLE_STREET_MODE_ON_STARTUP               1

// display mode (0=DISPLAY DATA 1=SET PARAMETER)
#define ENABLE_SET_PARAMETER_ON_STARTUP             1

// odometer compensation (1=ENABLED)
#define ENABLE_ODOMETER_COMPENSATION                1

// cadence sensor mode (0=STANDARD 1=ADVANCED)
#define CADENCE_SENSOR_MODE_ON_STARTUP              1

// torque sensor mode (0=STANDARD 1=ADVANCED)
#define TORQUE_SENSOR_MODE_ON_STARTUP               0

// lights configuration (0 to 8)
#define LIGHTS_CONFIGURATION_ON_STARTUP             0

// ridind mode (1=POWER 2=TORQUE 3=CADENCE 4=EMTB)
#define RIDING_MODE_ON_STARTUP                      1


// lights configuration
#define LIGHTS_CONFIGURATION_1                      1
#define LIGHTS_CONFIGURATION_2                      6
#define LIGHTS_CONFIGURATION_3                      7
/*  NOTE: regarding the various light modes
	(0) lights ON when enabled
    (1) lights FLASHING when enabled
    (2) lights ON when enabled and BRAKE-FLASHING when braking
    (3) lights FLASHING when enabled and ON when braking
    (4) lights FLASHING when enabled and BRAKE-FLASHING when braking
    (5) lights ON when enabled, but ON when braking regardless if lights are enabled
    (6) lights ON when enabled, but BRAKE-FLASHING when braking regardless if lights are enabled
    (7) lights FLASHING when enabled, but ON when braking regardless if lights are enabled
    (8) lights FLASHING when enabled, but BRAKE-FLASHING when braking regardless if lights are enabled
*/
	
// STREET MODE FUNCTION

// street mode power limit (1=ENABLED)
#define STREET_MODE_POWER_LIMIT_ENABLED             1

// street mode power limit value (Watt)
#define STREET_MODE_POWER_LIMIT                     500

// street mode speed limit (km/h)
#define STREET_MODE_SPEED_LIMIT                     25

// street mode enable other functions
#define STREET_MODE_THROTTLE_ENABLED                0
#define STREET_MODE_CRUISE_ENABLED                  0

// throttle ADC values (optional)
#define ADC_THROTTLE_MIN_VALUE                      47
#define ADC_THROTTLE_MAX_VALUE                      176
// throttle assist values
#define ASSIST_THROTTLE_MIN_VALUE 					0
#define ASSIST_THROTTLE_MAX_VALUE 					255

// motor temperature limit (sensor required)
#define MOTOR_TEMPERATURE_MIN_VALUE_LIMIT           65
#define MOTOR_TEMPERATURE_MAX_VALUE_LIMIT           80
// enable temperature error at min limit value
#define ENABLE_TEMPERATURE_ERROR_MIN_LIMIT          1


// --------------------------------------------------------------------------
// DISPLAY
// --------------------------------------------------------------------------

// display type
#define ENABLE_VLCD6                                0
#define ENABLE_VLCD5                                1
#define ENABLE_XH18                                 0

// display parameters
#define ENABLE_DISPLAY_WORKING_FLAG                 1
#define ENABLE_DISPLAY_ALWAYS_ON                    0
#define ENABLE_WHEEL_MAX_SPEED_FROM_DISPLAY         1
#define UNITS_TYPE                                  0
// 0 = km/h and kilometer, 1 mph and miles

// delay menu function (0.1 sec, 40 to 60)
#define DELAY_MENU_ON                               50

// enable delay return to default display mode
#define ENABLE_RETURN_DEFAULT_DISPLAY_MODE          1
// delay return to default display mode (seconds)
#define DELAY_DISPLAY_MODE_DEFAULT                  30

// enable auto display data with lights on
#define ENABLE_AUTO_DATA_DISPLAY                    1
// number of data auto displayed
#define AUTO_DATA_NUMBER_DISPLAY                    3

// displays double data (0=3 VALUES 1=6 VALUES)
#define ENABLE_DISPLAY_DOUBLE_DATA                  0
// delay display data (0.1 sec, min DELAY_MENU_ON, max 255)
// first series
#define DELAY_DISPLAY_DATA_1                        50
#define DELAY_DISPLAY_DATA_2                        50
#define DELAY_DISPLAY_DATA_3                        50
// second series
#define DELAY_DISPLAY_DATA_4                        250
#define DELAY_DISPLAY_DATA_5                        50
#define DELAY_DISPLAY_DATA_6                        50

// display data configuration
// first series
#define DISPLAY_DATA_1                              1
#define DISPLAY_DATA_2                              2
#define DISPLAY_DATA_3                              0
// second series
#define DISPLAY_DATA_4                              4
#define DISPLAY_DATA_5                              7
#define DISPLAY_DATA_6                              9
/*  display data code configuration
	0 - motor temperature (°C)
	1 - battery SOC remaining (%)
	2 - battery voltage (Volt)
	3 - battery current (Amp)
	4 - absorbed motor power (Watt/10)
	5 - adc torque sensor (8 bit)
	6 - adc torque sensor (10 bit)
	7 - pedal cadence (rpm)
	8 - human power
	9 - cadence sensor pulse high percentage
   10 - pedal weight
   11 - pedal torque adc conversion
   12 - pedal torque adc range
*/


// --------------------------------------------------------------------------
// ASSIST
// --------------------------------------------------------------------------

// power assist (% max 500)
#define POWER_ASSIST_LEVEL_1                        70
#define POWER_ASSIST_LEVEL_2                        120
#define POWER_ASSIST_LEVEL_3                        210
#define POWER_ASSIST_LEVEL_4                        300

// torque assist (max 254)
#define TORQUE_ASSIST_LEVEL_1                       70
#define TORQUE_ASSIST_LEVEL_2                       100
#define TORQUE_ASSIST_LEVEL_3                       130
#define TORQUE_ASSIST_LEVEL_4                       160

// cadence assist (max 254)
#define CADENCE_ASSIST_LEVEL_1                      70
#define CADENCE_ASSIST_LEVEL_2                      100
#define CADENCE_ASSIST_LEVEL_3                      130
#define CADENCE_ASSIST_LEVEL_4                      160

// eMTB assist (sensitivity 0 to 20)
#define EMTB_ASSIST_LEVEL_1                         6
#define EMTB_ASSIST_LEVEL_2                         9
#define EMTB_ASSIST_LEVEL_3                         12
#define EMTB_ASSIST_LEVEL_4                         15

// walk assist (max 80)
#define WALK_ASSIST_LEVEL_1                         30
#define WALK_ASSIST_LEVEL_2                         40
#define WALK_ASSIST_LEVEL_3                         50
#define WALK_ASSIST_LEVEL_4                         60
// walk assist threshold (speed limit max km/h)
#define WALK_ASSIST_THRESHOLD_SPEED                 6
// walk assist debounce (brake sensor required)
#define WALK_ASSIST_DEBOUNCE_ENABLED                0
// walk assist debounce time (0.1 sec, max 255)
#define WALK_ASSIST_DEBOUNCE_TIME                   60

// cruise level (target km/h, brake sensor required)
#define CRUISE_TARGET_SPEED_LEVEL_1                 15
#define CRUISE_TARGET_SPEED_LEVEL_2                 18
#define CRUISE_TARGET_SPEED_LEVEL_3                 21
#define CRUISE_TARGET_SPEED_LEVEL_4                 24
// cruise function without pedal rotation
#define CRUISE_MODE_WALK_ENABLED                    0
// cruise threshold (speed limit min km/h)
#define CRUISE_THRESHOLD_SPEED                      10

#endif // CONFIG_H_


