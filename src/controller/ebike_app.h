/*
 * TongSheng TSDZ2 motor controller firmware/
 *
 * Copyright (C) Casainho, Leon, MSpider65 2020.
 *
 * Released under the GPL License, Version 3
 */

#ifndef _EBIKE_APP_H_
#define _EBIKE_APP_H_

#include <stdint.h>
#include "main.h"

// for oem display
extern volatile uint8_t ui8_display_fault_code;

// cadence sensor
extern uint16_t ui16_cadence_sensor_ticks_counter_min_speed_adjusted;

// Torque sensor coaster brake engaged threshold value
extern uint8_t ui8_adc_coaster_brake_threshold;

typedef struct _configuration_variables
{
  //uint8_t ui8_motor_power_x10; // not used
  uint8_t ui8_battery_current_max; // from  ebike_app.c
  uint16_t ui16_battery_low_voltage_cut_off_x10;
  uint16_t ui16_wheel_perimeter;
  uint8_t ui8_wheel_speed_max;
  uint8_t ui8_motor_type;
  uint8_t ui8_pedal_torque_per_10_bit_ADC_step_x100;
  // for oem display
  uint8_t ui8_assist_without_pedal_rotation_enabled;
  uint8_t ui8_assist_whit_error_enabled;
  uint8_t ui8_battery_SOC_percentage_8b;
  uint8_t ui8_set_parameter_enabled;
  uint8_t ui8_street_mode_enabled;
  uint8_t ui8_riding_mode;
  uint8_t ui8_lights_configuration;
  uint8_t ui8_startup_boost_enabled;
  uint8_t ui8_auto_display_data_enabled;
  uint8_t ui8_torque_sensor_adv_enabled; 
  uint8_t ui8_torque_sensor_calibrated; // not used
} struct_configuration_variables;

void ebike_app_controller(void);
struct_configuration_variables* get_configuration_variables(void);

void ebike_app_init(void);

#endif /* _EBIKE_APP_H_ */
