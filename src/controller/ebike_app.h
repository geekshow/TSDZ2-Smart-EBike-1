/*
 * TongSheng TSDZ2 motor controller firmware/
 *
 * Copyright (C) Casainho and EndlessCadence and Leon, 2019.
 *
 * Released under the GPL License, Version 3
 */

#ifndef _EBIKE_APP_H_
#define _EBIKE_APP_H_

#include <stdint.h>
#include "main.h"

// for oem display
//extern volatile uint8_t ui8_battery_SOC_percentage_8b;
extern volatile uint8_t ui8_display_ready_flag;
extern volatile uint8_t ui8_display_fault_code;
extern volatile uint8_t ui8_working_status;

// cadence sensor
//extern volatile uint8_t ui8_cadence_sensor_mode;
extern volatile uint16_t ui16_cadence_sensor_ticks_counter_min_speed_adjusted;

typedef struct _configuration_variables
{
  //uint8_t ui8_motor_power_x10; // not used
  uint8_t ui8_battery_current_max; // from  ebike_app.c
  uint16_t ui16_battery_low_voltage_cut_off_x10;
  uint16_t ui16_wheel_perimeter;
  uint8_t ui8_wheel_speed_max;
  uint8_t ui8_motor_type;
  uint8_t ui8_pedal_torque_per_10_bit_ADC_step_x100;
  //uint8_t ui8_target_battery_max_power_div25; // moved to ebike_app.c
  //uint8_t configuration_variables;  // not used
/*uint8_t ui8_startup_motor_power_boost_feature_enabled;
  uint8_t ui8_startup_motor_power_boost_assist_level;
  uint8_t ui8_startup_motor_power_boost_state;
  uint8_t ui8_startup_motor_power_boost_limit_to_max_power;
  uint8_t ui8_startup_motor_power_boost_time;
  uint8_t ui8_startup_motor_power_boost_fade_time;  */
  //uint8_t ui8_optional_ADC_function; // moved to ebike_app.c
  
  // for oem display
  uint8_t ui8_pedal_torque_10_bit_ADC_range;
  uint8_t ui8_odometer_compensation_km_x10;
  uint8_t ui8_battery_SOC_percentage_8b;
  uint8_t ui8_set_parameter_enabled;
  uint8_t ui8_street_mode_enabled;
  uint8_t ui8_riding_mode;
  uint8_t ui8_lights_configuration;
  uint8_t ui8_cadence_sensor_mode;
  uint16_t ui16_cadence_sensor_pulse_high_percentage_x10;
  uint8_t ui8_torque_sensor_mode;
} struct_configuration_variables;


void ebike_app_controller (void);
struct_configuration_variables* get_configuration_variables (void);

// functions for oem display
void ebike_app_init(void);

#endif /* _EBIKE_APP_H_ */
