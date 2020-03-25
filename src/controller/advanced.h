/*
 * advanced.h
 * parameters configuration file
 * for TongSheng TSDZ2 motor controller firmware
 * by (C)Casainho and EndlessCadence and Leon, 20 beta 1 version
 * adapted to stock displays VLCD5 VLCD6 XH18
 * from an idea of marcoq (Jobike forum)
 * Author: mbrusa
 * Version mb.20beta1.A
 */
 
#ifndef ADVANCED_H_
#define ADVANCED_H_

#define DEBUG_MODE                                  0
#define DEBUG_DATA                                  1

// experimental high cadence mode (1=ENABLED)
#define EXPERIMENTAL_HIGH_CADENCE_MODE              0

// cadence sensor high percentage (calibration required)
#define CADENCE_SENSOR_PULSE_HIGH_PERCENTAGE_X10    500

// battery internal resistance (milliohms)
#define BATTERY_PACK_RESISTANCE                     196


// battery voltage calibration (95% to 105%)
#define ACTUAL_BATTERY_VOLTAGE_PERCENT              100

// battery capacity calibration (max 100%)
#define ACTUAL_BATTERY_CAPACITY_PERCENT				100

// li-ion cell
#define LI_ION_CELL_OVERVOLT                        4.30
#define LI_ION_CELL_RESET_SOC_PERCENT               4.10
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
#define ENABLE_SET_PARAMETER_ON_STARTUP             0

// odometer compensation (1=ENABLED)
#define ENABLE_ODOMETER_COMPENSATION                1

// cadence sensor mode (0=STANDARD 1=ADVANCED)
#define CADENCE_SENSOR_MODE_ON_STARTUP              0

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

// motor temperature limit (sensor required)
#define MOTOR_TEMPERATURE_MIN_VALUE_LIMIT           65
#define MOTOR_TEMPERATURE_MAX_VALUE_LIMIT           80
// enable temperature error at min limit value
#define ENABLE_TEMPERATURE_ERROR_MIN_LIMIT          1


// --------------------------------------------------------------------------
// DISPLAY
// --------------------------------------------------------------------------


// display parameters
#define ENABLE_DISPLAY_WORKING_FLAG                 1
#define ENABLE_DISPLAY_ALWAYS_ON                    0
#define ENABLE_WHEEL_MAX_SPEED_FROM_DISPLAY         0

// delay menu function (0.1 sec)
#define DELAY_MENU_ON                               50

// enable delay return to default display mode
#define ENABLE_RETURN_DEFAULT_DISPLAY_MODE          1
// delay return to default display mode (seconds)
#define DELAY_DISPLAY_MODE_DEFAULT                  30

// displays double data (0=3 VALUES 1=6 VALUES)
#define ENABLE_DISPLAY_DOUBLE_DATA                  0
// delay display data (0.1 sec, max 255)
// first series
#define DELAY_DISPLAY_DATA_1                        50
#define DELAY_DISPLAY_DATA_2                        50
#define DELAY_DISPLAY_DATA_3                        250
// second series
#define DELAY_DISPLAY_DATA_4                        250
#define DELAY_DISPLAY_DATA_5                        50
#define DELAY_DISPLAY_DATA_6                        50

// display data configuration
// first series
#define DISPLAY_DATA_1                              1
#define DISPLAY_DATA_2                              2
#define DISPLAY_DATA_3                              5
// second series
#define DISPLAY_DATA_4                              4
#define DISPLAY_DATA_5                              7
#define DISPLAY_DATA_6                              0
/*  display data code configuration
	0 - motor temperature (°C)
	1 - battery SOC remaining (%)
	2 - battery voltage (Volt)
	3 - battery current (Amp)
	4 - absorbed motor power (Watt/10)
	5 - adc torque sensor (8 bit)
	6 - adc torque sensor (10 bit)
	7 - pedal cadence (rpm)
*/

// --------------------------------------------------------------------------
// ASSIST
// --------------------------------------------------------------------------
// CAUTION: all assist values _LEVEL_0 for safety must be set to zero

// power assist (% max 500)
#define POWER_ASSIST_LEVEL_0                        0
#define POWER_ASSIST_LEVEL_1                        70
#define POWER_ASSIST_LEVEL_2                        120
#define POWER_ASSIST_LEVEL_3                        210
#define POWER_ASSIST_LEVEL_4                        300

// torque assist (max 254)
#define TORQUE_ASSIST_LEVEL_0                       0
#define TORQUE_ASSIST_LEVEL_1                       70
#define TORQUE_ASSIST_LEVEL_2                       100
#define TORQUE_ASSIST_LEVEL_3                       130
#define TORQUE_ASSIST_LEVEL_4                       160

// cadence assist (max 254)
#define CADENCE_ASSIST_LEVEL_0                      0
#define CADENCE_ASSIST_LEVEL_1                      70
#define CADENCE_ASSIST_LEVEL_2                      100
#define CADENCE_ASSIST_LEVEL_3                      130
#define CADENCE_ASSIST_LEVEL_4                      160

// eMTB assist (sensitivity 0 to 20)
#define EMTB_ASSIST_LEVEL_0                         0
#define EMTB_ASSIST_LEVEL_1                         6
#define EMTB_ASSIST_LEVEL_2                         9
#define EMTB_ASSIST_LEVEL_3                         12
#define EMTB_ASSIST_LEVEL_4                         15

// walk assist (max 100)
#define WALK_ASSIST_LEVEL_0                         0
#define WALK_ASSIST_LEVEL_1                         30
#define WALK_ASSIST_LEVEL_2                         40
#define WALK_ASSIST_LEVEL_3                         50
#define WALK_ASSIST_LEVEL_4                         60
// walk assist threshold (speed limit max km/h)
#define WALK_ASSIST_THRESHOLD_SPEED                 6
// walk assist debounce (brake sensor required)
#define WALK_ASSIST_DEBOUNCE_ENABLED                0
// walk assist debounce time (0.1 sec, max 255)
#define WALK_ASSIST_DEBOUNCE_TIME                   50

// cruise level (target km/h, brake sensor required)
#define CRUISE_TARGET_SPEED_LEVEL_0                 0
#define CRUISE_TARGET_SPEED_LEVEL_1                 15
#define CRUISE_TARGET_SPEED_LEVEL_2                 18
#define CRUISE_TARGET_SPEED_LEVEL_3                 21
#define CRUISE_TARGET_SPEED_LEVEL_4                 24
// cruise function with pedal rotation
#define CRUISE_MODE_CADENCE_ENABLED                 1
// cruise threshold (speed limit min km/h)
#define CRUISE_THRESHOLD_SPEED                      10

#endif // ADVANCED_H_


