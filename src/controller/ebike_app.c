	/*
	 * TongSheng TSDZ2 motor controller firmware
	 *
	 * Copyright (C) Casainho and EndlessCadence and Leon, 2019.
	 *
	 * Released under the GPL License, Version 3
	 */

	#include "ebike_app.h"
	#include <stdint.h>
	#include <stdio.h>
	#include "stm8s.h"
	#include "stm8s_gpio.h"
	#include "main.h"
	#include "interrupts.h"
	#include "adc.h"
	#include "motor.h"
	#include "pwm.h"
	#include "uart.h"
	#include "brake.h"
	#include "eeprom.h"
	#include "lights.h"
	#include "common.h"
	#include "advanced.h"

	volatile struct_configuration_variables m_configuration_variables;

	// system
	//static uint8_t    ui8_riding_mode = OFF_MODE;
	static uint8_t    ui8_riding_mode_parameter = 0;
	static uint8_t    ui8_system_state = NO_ERROR;
	static uint8_t    ui8_motor_enabled = 1;
	//static uint8_t    ui8_assist_without_pedal_rotation_threshold = 0;
	volatile uint8_t  ui8_assist_without_pedal_rotation_threshold = ASSIST_WITHOUT_PEDAL_ROTATION_THRESHOLD;
	//static uint8_t    ui8_lights_configuration = 0;
	static uint8_t    ui8_lights_state = 0;


	// power control
	//static uint8_t    ui8_battery_current_max = DEFAULT_VALUE_BATTERY_CURRENT_MAX;
	static uint16_t   ui16_duty_cycle_ramp_up_inverse_step = PWM_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_DEFAULT;
	static uint16_t   ui16_duty_cycle_ramp_up_inverse_step_default = PWM_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_DEFAULT;
	static uint16_t   ui16_duty_cycle_ramp_down_inverse_step = PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_DEFAULT;
	static uint16_t   ui16_battery_voltage_filtered_x1000 = 0;
	static uint8_t    ui8_battery_current_filtered_x10 = 0;
	static uint8_t    ui8_adc_battery_current_max = ADC_10_BIT_BATTERY_CURRENT_MAX;
	static uint8_t    ui8_adc_battery_current_target = 0;
	static uint8_t    ui8_duty_cycle_target = 0;


	// brakes
	static uint8_t ui8_brakes_engaged = 0;


	// cadence sensor
	//volatile uint8_t ui8_cadence_sensor_mode = STANDARD_MODE;
	volatile uint16_t ui16_cadence_sensor_ticks_counter_min_speed_adjusted = CADENCE_SENSOR_TICKS_COUNTER_MIN;
	//static uint16_t ui16_cadence_sensor_pulse_high_percentage_x10 = CADENCE_SENSOR_PULSE_PERCENTAGE_X10_DEFAULT;
	static uint8_t ui8_pedal_cadence_RPM = 0;


	// torque sensor
	volatile uint16_t ui16_adc_pedal_torque = 0;
	static uint16_t   ui16_adc_pedal_torque_delta = 0;
	static uint16_t   ui16_human_power_x10 = 0;
	static uint16_t   ui16_pedal_torque_x100 = 0;


	// wheel speed sensor
	static uint16_t   ui16_wheel_speed_x10 = 0;


	// throttle control
	volatile uint8_t  ui8_adc_throttle = 0;


	// motor temperature control
	static uint16_t ui16_motor_temperature_filtered_x10 = 0;
	//static uint8_t ui8_motor_temperature_max_value_to_limit = 0;
	//static uint8_t ui8_motor_temperature_min_value_to_limit = 0;
	static uint8_t ui8_motor_temperature_max_value_to_limit = MOTOR_TEMPERATURE_MAX_VALUE_LIMIT;
	static uint8_t ui8_motor_temperature_min_value_to_limit = MOTOR_TEMPERATURE_MIN_VALUE_LIMIT;
	static uint8_t ui8_temperature_current_limiting_value = 0;


	// eMTB assist
	#define eMTB_POWER_FUNCTION_ARRAY_SIZE      241

	static const uint8_t ui8_eMTB_power_function_160[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 11, 11, 11, 11, 12, 12, 12, 12, 12, 13, 13, 13, 13, 14, 14, 14, 14, 15, 15, 15, 15, 16, 16, 16, 16, 17, 17, 17, 17, 18, 18, 18, 18, 19, 19, 19, 20, 20, 20, 20, 21, 21, 21, 22, 22, 22, 22, 23, 23, 23, 24, 24, 24, 24, 25, 25, 25, 26, 26, 26, 27, 27, 27, 27, 28, 28, 28, 29, 29, 29, 30, 30, 30, 31, 31, 31, 32, 32, 32, 33, 33, 33, 34, 34, 34, 35, 35, 35, 36, 36, 36, 37, 37, 37, 38, 38, 38, 39, 39, 40, 40, 40, 41, 41, 41, 42, 42, 42, 43, 43, 44, 44, 44, 45, 45, 45, 46, 46, 47, 47, 47, 48, 48, 48, 49, 49, 50, 50, 50, 51, 51, 52, 52, 52, 53, 53, 54, 54, 54, 55, 55, 56, 56, 56, 57, 57, 58, 58, 58, 59, 59, 60, 60, 61, 61, 61, 62, 62, 63, 63, 63, 64, 64 };
	static const uint8_t ui8_eMTB_power_function_165[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 8, 8, 8, 8, 9, 9, 9, 9, 10, 10, 10, 10, 11, 11, 11, 11, 12, 12, 12, 12, 13, 13, 13, 14, 14, 14, 14, 15, 15, 15, 16, 16, 16, 16, 17, 17, 17, 18, 18, 18, 19, 19, 19, 20, 20, 20, 21, 21, 21, 22, 22, 22, 23, 23, 23, 24, 24, 24, 25, 25, 25, 26, 26, 27, 27, 27, 28, 28, 28, 29, 29, 30, 30, 30, 31, 31, 32, 32, 32, 33, 33, 34, 34, 34, 35, 35, 36, 36, 36, 37, 37, 38, 38, 39, 39, 39, 40, 40, 41, 41, 42, 42, 42, 43, 43, 44, 44, 45, 45, 46, 46, 47, 47, 47, 48, 48, 49, 49, 50, 50, 51, 51, 52, 52, 53, 53, 54, 54, 55, 55, 56, 56, 57, 57, 58, 58, 59, 59, 60, 60, 61, 61, 62, 62, 63, 63, 64, 64, 65, 65, 66, 66, 67, 67, 68, 68, 69, 69, 70, 71, 71, 72, 72, 73, 73, 74, 74, 75, 75, 76, 77, 77, 78, 78, 79, 79, 80, 81, 81, 82, 82, 83, 83, 84, 85 };
	static const uint8_t ui8_eMTB_power_function_170[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 9, 9, 9, 9, 10, 10, 10, 11, 11, 11, 11, 12, 12, 12, 13, 13, 13, 14, 14, 14, 15, 15, 15, 16, 16, 16, 17, 17, 18, 18, 18, 19, 19, 19, 20, 20, 21, 21, 21, 22, 22, 23, 23, 23, 24, 24, 25, 25, 26, 26, 26, 27, 27, 28, 28, 29, 29, 30, 30, 30, 31, 31, 32, 32, 33, 33, 34, 34, 35, 35, 36, 36, 37, 37, 38, 38, 39, 39, 40, 40, 41, 41, 42, 42, 43, 43, 44, 45, 45, 46, 46, 47, 47, 48, 48, 49, 49, 50, 51, 51, 52, 52, 53, 53, 54, 55, 55, 56, 56, 57, 58, 58, 59, 59, 60, 61, 61, 62, 63, 63, 64, 64, 65, 66, 66, 67, 68, 68, 69, 70, 70, 71, 71, 72, 73, 73, 74, 75, 75, 76, 77, 77, 78, 79, 80, 80, 81, 82, 82, 83, 84, 84, 85, 86, 87, 87, 88, 89, 89, 90, 91, 92, 92, 93, 94, 94, 95, 96, 97, 97, 98, 99, 100, 100, 101, 102, 103, 103, 104, 105, 106, 107, 107, 108, 109, 110, 110, 111 };
	static const uint8_t ui8_eMTB_power_function_175[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 8, 8, 8, 8, 9, 9, 9, 10, 10, 10, 11, 11, 11, 12, 12, 13, 13, 13, 14, 14, 14, 15, 15, 16, 16, 17, 17, 17, 18, 18, 19, 19, 20, 20, 20, 21, 21, 22, 22, 23, 23, 24, 24, 25, 25, 26, 26, 27, 27, 28, 28, 29, 29, 30, 31, 31, 32, 32, 33, 33, 34, 34, 35, 36, 36, 37, 37, 38, 39, 39, 40, 40, 41, 42, 42, 43, 44, 44, 45, 45, 46, 47, 47, 48, 49, 49, 50, 51, 51, 52, 53, 53, 54, 55, 56, 56, 57, 58, 58, 59, 60, 61, 61, 62, 63, 64, 64, 65, 66, 67, 67, 68, 69, 70, 70, 71, 72, 73, 74, 74, 75, 76, 77, 78, 78, 79, 80, 81, 82, 83, 83, 84, 85, 86, 87, 88, 88, 89, 90, 91, 92, 93, 94, 95, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146 };
	static const uint8_t ui8_eMTB_power_function_180[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 7, 7, 7, 8, 8, 8, 9, 9, 9, 10, 10, 11, 11, 11, 12, 12, 13, 13, 14, 14, 14, 15, 15, 16, 16, 17, 17, 18, 18, 19, 19, 20, 20, 21, 21, 22, 23, 23, 24, 24, 25, 25, 26, 27, 27, 28, 28, 29, 30, 30, 31, 32, 32, 33, 34, 34, 35, 36, 36, 37, 38, 38, 39, 40, 41, 41, 42, 43, 43, 44, 45, 46, 46, 47, 48, 49, 50, 50, 51, 52, 53, 54, 54, 55, 56, 57, 58, 59, 59, 60, 61, 62, 63, 64, 65, 66, 67, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 105, 106, 107, 108, 109, 110, 111, 112, 114, 115, 116, 117, 118, 119, 120, 122, 123, 124, 125, 126, 128, 129, 130, 131, 132, 134, 135, 136, 137, 139, 140, 141, 142, 144, 145, 146, 147, 149, 150, 151, 153, 154, 155, 157, 158, 159, 161, 162, 163, 165, 166, 167, 169, 170, 171, 173, 174, 176, 177, 178, 180, 181, 182, 184, 185, 187, 188, 190, 191, 192 };
	static const uint8_t ui8_eMTB_power_function_185[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 8, 8, 8, 9, 9, 10, 10, 11, 11, 11, 12, 12, 13, 13, 14, 14, 15, 15, 16, 17, 17, 18, 18, 19, 19, 20, 21, 21, 22, 23, 23, 24, 25, 25, 26, 27, 27, 28, 29, 29, 30, 31, 32, 32, 33, 34, 35, 36, 36, 37, 38, 39, 40, 40, 41, 42, 43, 44, 45, 46, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 74, 75, 76, 77, 78, 79, 80, 81, 83, 84, 85, 86, 87, 89, 90, 91, 92, 93, 95, 96, 97, 98, 100, 101, 102, 104, 105, 106, 107, 109, 110, 111, 113, 114, 115, 117, 118, 120, 121, 122, 124, 125, 127, 128, 129, 131, 132, 134, 135, 137, 138, 140, 141, 143, 144, 146, 147, 149, 150, 152, 153, 155, 156, 158, 160, 161, 163, 164, 166, 168, 169, 171, 172, 174, 176, 177, 179, 181, 182, 184, 186, 187, 189, 191, 193, 194, 196, 198, 199, 201, 203, 205, 207, 208, 210, 212, 214, 216, 217, 219, 221, 223, 225, 227, 228, 230, 232, 234, 236, 238, 240, 240, 240, 240, 240, 240, 240, 240 };
	static const uint8_t ui8_eMTB_power_function_190[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14, 15, 16, 16, 17, 18, 18, 19, 20, 20, 21, 22, 22, 23, 24, 25, 25, 26, 27, 28, 29, 29, 30, 31, 32, 33, 34, 35, 36, 37, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 51, 52, 53, 54, 55, 56, 57, 58, 60, 61, 62, 63, 64, 66, 67, 68, 69, 70, 72, 73, 74, 76, 77, 78, 80, 81, 82, 84, 85, 86, 88, 89, 91, 92, 94, 95, 96, 98, 99, 101, 102, 104, 105, 107, 108, 110, 112, 113, 115, 116, 118, 120, 121, 123, 124, 126, 128, 130, 131, 133, 135, 136, 138, 140, 142, 143, 145, 147, 149, 150, 152, 154, 156, 158, 160, 162, 163, 165, 167, 169, 171, 173, 175, 177, 179, 181, 183, 185, 187, 189, 191, 193, 195, 197, 199, 201, 203, 205, 207, 209, 211, 214, 216, 218, 220, 222, 224, 227, 229, 231, 233, 235, 238, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240 };
	static const uint8_t ui8_eMTB_power_function_195[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 5, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 13, 13, 14, 15, 15, 16, 17, 17, 18, 19, 20, 21, 21, 22, 23, 24, 25, 26, 27, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 39, 40, 41, 42, 43, 44, 45, 47, 48, 49, 50, 51, 53, 54, 55, 57, 58, 59, 61, 62, 63, 65, 66, 68, 69, 70, 72, 73, 75, 76, 78, 79, 81, 83, 84, 86, 87, 89, 91, 92, 94, 96, 97, 99, 101, 103, 104, 106, 108, 110, 112, 113, 115, 117, 119, 121, 123, 125, 127, 129, 131, 132, 134, 136, 139, 141, 143, 145, 147, 149, 151, 153, 155, 157, 160, 162, 164, 166, 168, 171, 173, 175, 177, 180, 182, 184, 187, 189, 191, 194, 196, 199, 201, 203, 206, 208, 211, 213, 216, 218, 221, 224, 226, 229, 231, 234, 237, 239, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240 };
	static const uint8_t ui8_eMTB_power_function_200[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 10, 10, 11, 12, 12, 13, 14, 14, 15, 16, 17, 18, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 34, 35, 36, 37, 38, 40, 41, 42, 44, 45, 46, 48, 49, 50, 52, 53, 55, 56, 58, 59, 61, 62, 64, 66, 67, 69, 71, 72, 74, 76, 77, 79, 81, 83, 85, 86, 88, 90, 92, 94, 96, 98, 100, 102, 104, 106, 108, 110, 112, 114, 117, 119, 121, 123, 125, 128, 130, 132, 135, 137, 139, 142, 144, 146, 149, 151, 154, 156, 159, 161, 164, 166, 169, 172, 174, 177, 180, 182, 185, 188, 190, 193, 196, 199, 202, 204, 207, 210, 213, 216, 219, 222, 225, 228, 231, 234, 237, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240 };
	static const uint8_t ui8_eMTB_power_function_205[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 9, 9, 10, 11, 11, 12, 13, 14, 15, 16, 16, 17, 18, 19, 20, 21, 22, 23, 24, 26, 27, 28, 29, 30, 32, 33, 34, 36, 37, 38, 40, 41, 43, 44, 46, 47, 49, 50, 52, 54, 55, 57, 59, 61, 62, 64, 66, 68, 70, 72, 74, 76, 78, 80, 82, 84, 86, 88, 90, 92, 95, 97, 99, 101, 104, 106, 108, 111, 113, 116, 118, 121, 123, 126, 128, 131, 134, 136, 139, 142, 145, 147, 150, 153, 156, 159, 162, 165, 168, 171, 174, 177, 180, 183, 186, 189, 192, 196, 199, 202, 205, 209, 212, 216, 219, 222, 226, 229, 233, 236, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240 };
	static const uint8_t ui8_eMTB_power_function_210[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5, 6, 7, 7, 8, 9, 9, 10, 11, 12, 13, 14, 14, 15, 16, 17, 19, 20, 21, 22, 23, 24, 26, 27, 28, 30, 31, 32, 34, 35, 37, 39, 40, 42, 43, 45, 47, 49, 50, 52, 54, 56, 58, 60, 62, 64, 66, 68, 71, 73, 75, 77, 80, 82, 84, 87, 89, 92, 94, 97, 99, 102, 104, 107, 110, 113, 115, 118, 121, 124, 127, 130, 133, 136, 139, 142, 145, 149, 152, 155, 158, 162, 165, 169, 172, 176, 179, 183, 186, 190, 194, 197, 201, 205, 209, 213, 216, 220, 224, 228, 232, 237, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240 };
	static const uint8_t ui8_eMTB_power_function_215[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 8, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20, 21, 22, 24, 25, 26, 28, 29, 31, 33, 34, 36, 38, 39, 41, 43, 45, 47, 49, 51, 53, 55, 57, 60, 62, 64, 67, 69, 71, 74, 76, 79, 82, 84, 87, 90, 93, 96, 98, 101, 104, 107, 111, 114, 117, 120, 123, 127, 130, 134, 137, 141, 144, 148, 152, 155, 159, 163, 167, 171, 175, 179, 183, 187, 191, 195, 200, 204, 208, 213, 217, 222, 226, 231, 235, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240 };
	static const uint8_t ui8_eMTB_power_function_220[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 5, 6, 7, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18, 19, 20, 22, 23, 25, 27, 28, 30, 32, 33, 35, 37, 39, 41, 43, 46, 48, 50, 52, 55, 57, 60, 62, 65, 67, 70, 73, 76, 79, 82, 85, 88, 91, 94, 97, 101, 104, 108, 111, 115, 118, 122, 126, 130, 133, 137, 141, 145, 150, 154, 158, 162, 167, 171, 176, 180, 185, 190, 194, 199, 204, 209, 214, 219, 224, 230, 235, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240 };
	static const uint8_t ui8_eMTB_power_function_225[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 3, 3, 4, 4, 5, 6, 7, 8, 8, 9, 10, 12, 13, 14, 15, 17, 18, 20, 21, 23, 24, 26, 28, 30, 32, 34, 36, 38, 40, 43, 45, 47, 50, 52, 55, 58, 61, 64, 66, 70, 73, 76, 79, 82, 86, 89, 93, 96, 100, 104, 108, 112, 116, 120, 124, 128, 133, 137, 142, 146, 151, 156, 161, 166, 171, 176, 181, 186, 191, 197, 202, 208, 214, 219, 225, 231, 237, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240 };
	static const uint8_t ui8_eMTB_power_function_230[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 4, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 18, 20, 21, 23, 25, 27, 29, 31, 33, 36, 38, 40, 43, 46, 48, 51, 54, 57, 60, 63, 67, 70, 74, 77, 81, 85, 88, 92, 96, 101, 105, 109, 114, 118, 123, 128, 133, 138, 143, 148, 153, 158, 164, 170, 175, 181, 187, 193, 199, 205, 212, 218, 225, 231, 238, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240 };
	static const uint8_t ui8_eMTB_power_function_235[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 0, 1, 1, 1, 2, 2, 3, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 37, 40, 43, 45, 48, 52, 55, 58, 62, 65, 69, 73, 77, 81, 85, 89, 94, 98, 103, 108, 113, 118, 123, 128, 134, 139, 145, 151, 157, 163, 169, 176, 182, 189, 196, 202, 210, 217, 224, 232, 239, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240 };
	static const uint8_t ui8_eMTB_power_function_240[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 0, 1, 1, 1, 2, 3, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 15, 17, 19, 21, 23, 25, 27, 30, 32, 35, 38, 41, 44, 47, 51, 54, 58, 62, 66, 70, 74, 79, 83, 88, 93, 98, 103, 108, 114, 120, 125, 131, 137, 144, 150, 157, 164, 171, 178, 185, 193, 200, 208, 216, 224, 233, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240 };
	static const uint8_t ui8_eMTB_power_function_245[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 1, 1, 1, 2, 2, 3, 4, 4, 5, 6, 8, 9, 10, 12, 14, 15, 17, 19, 22, 24, 27, 29, 32, 35, 38, 42, 45, 49, 53, 57, 61, 65, 70, 74, 79, 84, 89, 95, 100, 106, 112, 119, 125, 132, 138, 145, 153, 160, 168, 176, 184, 192, 200, 209, 218, 227, 237, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240 };
	static const uint8_t ui8_eMTB_power_function_250[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 1, 1, 1, 2, 2, 3, 4, 5, 6, 7, 9, 10, 12, 14, 16, 18, 20, 23, 25, 28, 31, 34, 38, 41, 45, 49, 54, 58, 63, 67, 72, 78, 83, 89, 95, 101, 108, 114, 121, 128, 136, 144, 151, 160, 168, 177, 186, 195, 204, 214, 224, 235, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240 };
	static const uint8_t ui8_eMTB_power_function_255[eMTB_POWER_FUNCTION_ARRAY_SIZE] = { 0, 0, 0, 0, 0, 1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 10, 12, 14, 16, 18, 21, 24, 26, 30, 33, 37, 41, 45, 49, 54, 58, 64, 69, 75, 80, 87, 93, 100, 107, 114, 122, 130, 138, 146, 155, 164, 174, 184, 194, 204, 215, 226, 238, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240, 240 };


	// cruise
	static uint8_t ui8_cruise_PID_initialize = 1;

/*
	// boost
	uint8_t   ui8_startup_boost_enable = 0;
	uint8_t   ui8_startup_boost_fade_enable = 0;
	uint8_t   ui8_m_startup_boost_state_machine = 0;
	uint8_t   ui8_startup_boost_no_torque = 0;
	uint8_t   ui8_startup_boost_timer = 0;
	uint8_t   ui8_startup_boost_fade_steps = 0;
	uint16_t  ui16_startup_boost_fade_variable_x256;
	uint16_t  ui16_startup_boost_fade_variable_step_amount_x256;
	static void     boost_run_statemachine (void);
	static uint8_t  boost(uint8_t ui8_max_current_boost_state);
	static void     apply_boost_fade_out();
	uint8_t ui8_boost_enabled_and_applied = 0;
	static void apply_boost();
*/

	// UART
	
	// deleted for oem display
	//#define UART_NUMBER_DATA_BYTES_TO_RECEIVE   7   // change this value depending on how many data bytes there are to receive ( Package = one start byte + data bytes + two bytes 16 bit CRC )
	//#define UART_NUMBER_DATA_BYTES_TO_SEND      26  // change this value depending on how many data bytes there are to send ( Package = one start byte + data bytes + two bytes 16 bit CRC )

	volatile uint8_t ui8_received_package_flag = 0;
	//volatile uint8_t ui8_rx_buffer[UART_NUMBER_DATA_BYTES_TO_RECEIVE + 3];
	volatile uint8_t ui8_rx_buffer[UART_RX_BUFFER_LEN];
	volatile uint8_t ui8_rx_counter = 0;
	//volatile uint8_t ui8_tx_buffer[UART_NUMBER_DATA_BYTES_TO_SEND + 3];
	volatile uint8_t ui8_tx_buffer[UART_TX_BUFFER_LEN];
	volatile uint8_t ui8_i;
	volatile uint8_t ui8_byte_received;
	volatile uint8_t ui8_state_machine = 0;
	//static uint16_t  ui16_crc_rx;
	//static uint16_t  ui16_crc_tx;
	//volatile uint8_t ui8_message_ID = 0;

	static void communications_controller (void);
	static void uart_receive_package (void);
	static void uart_send_package (void);


	// system functions
	static void get_battery_voltage_filtered(void);
	static void get_battery_current_filtered(void);
	static void get_pedal_torque(void);
	static void calc_wheel_speed(void);
	static void calc_cadence(void);

	static void ebike_control_lights(void);
	static void ebike_control_motor(void);
	static void check_system(void);
	static void check_brakes(void);

	static void apply_power_assist();
	static void apply_torque_assist();
	static void apply_cadence_assist();
	static void apply_emtb_assist();
	static void apply_walk_assist();
	static void apply_cruise();
	static void apply_cadence_sensor_calibration();
	static void apply_throttle();
	static void apply_temperature_limiting();
	static void apply_speed_limit();

	//----------------------------------------------------------------------------------
	// variables for oem display

	// from LCD3
	volatile uint8_t ui8_motor_acceleration_adjustment = MOTOR_ACCELERATION;
	
	// from typedef ebike_app.h
	volatile uint8_t ui8_target_battery_max_power_div25 = TARGET_MAX_BATTERY_POWER_DIV25;
	volatile uint8_t ui8_optional_ADC_function = OPTIONAL_ADC_FUNCTION;
	
	// riding mode
	volatile uint8_t ui8_optional_ADC_function_temp = OPTIONAL_ADC_FUNCTION;
	volatile uint8_t ui8_riding_mode_parameter_cruise = 0;
	volatile uint8_t ui8_riding_mode_cruise = 0;
	volatile uint8_t ui8_riding_mode_cruise_temp = 0;
	volatile uint8_t ui8_cruise_counter = 0;
	volatile uint8_t ui8_walk_assist_counter = 0;
	volatile uint8_t ui8_walk_assist_level = 0;
	
	// display menu
	volatile uint8_t ui8_assist_level = ECO;
	volatile uint8_t ui8_assist_level_temp = ECO;
	volatile uint8_t ui8_walk_assist_flag = 0;
	volatile uint8_t ui8_actual_riding_mode = 0;
	volatile uint8_t ui8_lights_flag = 0;
	volatile uint8_t ui8_lights_on_5s = 0;
	volatile uint8_t ui8_menu_flag = 0;
	volatile uint8_t ui8_menu_index = 0;
	volatile uint8_t ui8_data_index = 0;
	volatile uint8_t ui8_double_data_flag = 0;
	volatile uint8_t ui8_double_data_index = 0;
	volatile uint8_t ui8_lights_counter = 0;
	volatile uint8_t ui8_menu_counter = 0;
	volatile uint16_t ui16_display_mode_default_counter = 0;
	volatile uint8_t ui8_display_function_code = 0;
	volatile uint8_t ui8_menu_function_enabled = 0;
	volatile uint8_t ui8_display_data_enabled = 1;
	volatile uint16_t ui16_display_data = 0;
	volatile uint16_t ui16_display_data_factor = 0;
	volatile uint8_t ui8_display_blinking_code = 0;
	volatile uint8_t ui8_delay_display_function = DELAY_MENU_ON;
	
	// uart send
	volatile uint8_t ui8_working_status = 0;
	volatile uint8_t ui8_display_fault_code = 0;
	
	// check battery
	volatile uint8_t ui8_battery_state_of_charge = 0;
	volatile uint8_t ui8_timer_counter = 0;

	// battery
	volatile uint16_t ui16_battery_voltage_calibrated_x10 = 0;
	volatile uint16_t ui16_battery_voltage_soc_filtered_x10 = 0;															  
	volatile uint16_t ui16_battery_power_filtered_x10 = 0;
	volatile uint32_t ui32_actual_battery_capacity = ((uint32_t) TARGET_MAX_BATTERY_POWER * ACTUAL_BATTERY_CAPACITY_PERCENT) / 100;
	volatile uint32_t ui32_wh_x10 = 0;
	volatile uint32_t ui32_wh_sum_x10 = 0;
	volatile uint32_t ui32_wh_x10_offset = 0;
	volatile uint32_t ui32_wh_since_power_on_x10 = 0;
	volatile uint16_t ui16_battery_SOC_percentage_x10 = 0;
	volatile uint8_t ui8_battery_SOC_init_flag = 0;
	
	// wheel speed
	volatile uint8_t ui8_display_ready_flag = 0;
	volatile uint8_t ui8_startup_counter = 0;
	volatile uint16_t ui16_oem_wheel_speed = 0;
	volatile uint16_t ui16_oem_wheel_speed_max = 0;
	volatile uint8_t ui8_oem_wheel_diameter = 0;
	volatile uint32_t ui32_odometer_compensation_mm = ZERO_ODOMETER_COMPENSATION;
	
	// array for oem display
	static uint16_t ui16_data_value_array[DATA_VALUE_ARRAY_DIM];
	static uint8_t ui8_data_index_array[DATA_INDEX_ARRAY_DIM] = {DISPLAY_DATA_1,DISPLAY_DATA_2,DISPLAY_DATA_3,DISPLAY_DATA_4,DISPLAY_DATA_5,DISPLAY_DATA_6};
	static uint8_t ui8_display_time_array[DATA_INDEX_ARRAY_DIM] = {DELAY_DISPLAY_DATA_1,DELAY_DISPLAY_DATA_2,DELAY_DISPLAY_DATA_3,DELAY_DISPLAY_DATA_4,DELAY_DISPLAY_DATA_5,DELAY_DISPLAY_DATA_6};
		
	// array for riding parameters
	static uint8_t  ui8_riding_mode_parameter_array[7][5] = {
		{POWER_ASSIST_LEVEL_OFF, POWER_ASSIST_LEVEL_ECO, POWER_ASSIST_LEVEL_TOUR, POWER_ASSIST_LEVEL_SPORT, POWER_ASSIST_LEVEL_TURBO},
		{TORQUE_ASSIST_LEVEL_0, TORQUE_ASSIST_LEVEL_1, TORQUE_ASSIST_LEVEL_2, TORQUE_ASSIST_LEVEL_3, TORQUE_ASSIST_LEVEL_4},
		{CADENCE_ASSIST_LEVEL_0, CADENCE_ASSIST_LEVEL_1, CADENCE_ASSIST_LEVEL_2, CADENCE_ASSIST_LEVEL_3, CADENCE_ASSIST_LEVEL_4},
		{EMTB_ASSIST_LEVEL_0, EMTB_ASSIST_LEVEL_1, EMTB_ASSIST_LEVEL_2, EMTB_ASSIST_LEVEL_3, EMTB_ASSIST_LEVEL_4},
		{WALK_ASSIST_LEVEL_OFF, WALK_ASSIST_LEVEL_ECO, WALK_ASSIST_LEVEL_TOUR, WALK_ASSIST_LEVEL_SPORT, WALK_ASSIST_LEVEL_TURBO},
		{CRUISE_TARGET_SPEED_LEVEL_0, CRUISE_TARGET_SPEED_LEVEL_1, CRUISE_TARGET_SPEED_LEVEL_2, CRUISE_TARGET_SPEED_LEVEL_3, CRUISE_TARGET_SPEED_LEVEL_4},
		{0, 0, 0, 0, 0}
		};

	// functions for oem display
	static void check_battery_soc();
	static void calc_oem_wheel_speed();
	
	
	void ebike_app_init(void)
	{
		#if EXPERIMENTAL_HIGH_CADENCE_MODE
		// type of motor (36 volt, 48 volt, experimental type)
		m_configuration_variables.ui8_motor_type = MOTOR_TYPE + 2;
		#else
		// type of motor (36 volt, 48 volt, type)
		m_configuration_variables.ui8_motor_type = MOTOR_TYPE;
		#endif

		// set low voltage cutoff (8 bit)  300>86
		ui8_adc_battery_voltage_cut_off = ((uint32_t) m_configuration_variables.ui16_battery_low_voltage_cut_off_x10 * 25) / BATTERY_VOLTAGE_PER_10_BIT_ADC_STEP_X1000;

		// check if assist without pedal rotation threshold is valid (safety)
		if (ui8_assist_without_pedal_rotation_threshold > 100)
			ui8_assist_without_pedal_rotation_threshold = 0;

		// set duty cycle ramp up inverse step default
		ui16_duty_cycle_ramp_up_inverse_step_default = map((uint32_t) ui8_motor_acceleration_adjustment,
															(uint32_t) 0,
															(uint32_t) 100,
															(uint32_t) PWM_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_DEFAULT,
															(uint32_t) PWM_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_MIN);

		// percentage remaining battery capacity x10 at power on
		ui16_battery_SOC_percentage_x10 = ((uint16_t) m_configuration_variables.ui8_battery_SOC_percentage_8b) << 2;
	
		// calculate watt-hours x10 at power on
		ui32_wh_x10_offset = ((uint32_t)(1000 - ui16_battery_SOC_percentage_x10) * ui32_actual_battery_capacity) / 100;
	
		#if SAVE_ODOMETER_COMPENSATION
		// odometer compensation remaining at power on
		ui32_odometer_compensation_mm = (((uint32_t) m_configuration_variables.ui8_odometer_compensation_km_x10 * 100000) + ZERO_ODOMETER_COMPENSATION);
		#endif
	}
		
	//----------------------------------------------------------------------------------

	void ebike_app_controller (void)
	{ 
	  calc_wheel_speed();               // calculate the wheel speed
	  calc_cadence();                   // calculate the cadence and set limits from wheel speed
	  
	  get_battery_voltage_filtered();   // get filtered voltage from FOC calculations
	  get_battery_current_filtered();   // get filtered current from FOC calculations
	  get_pedal_torque();               // get pedal torque
	  
	  check_system();                   // check if there are any errors for motor control 
	  check_brakes();                   // check if brakes are enabled for motor control
	  
	  communications_controller();      // get data to use for motor control and also send new data
	  ebike_control_lights();           // use received data and sensor input to control external lights
	  ebike_control_motor();            // use received data and sensor input to control motor
	  
	  /*------------------------------------------------------------------------
	  
		NOTE: regarding function call order
		
		Do not change order of functions if not absolutely sure it will 
		not cause any undesirable consequences.
		
	  ------------------------------------------------------------------------*/
	}



	static void ebike_control_motor (void)
	{
	  // reset control variables (safety)
	  ui16_duty_cycle_ramp_up_inverse_step = PWM_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_DEFAULT;
	  ui16_duty_cycle_ramp_down_inverse_step = PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_DEFAULT;
	  ui8_adc_battery_current_target = 0;
	  ui8_duty_cycle_target = 0;

	  // reset initialization of Cruise PID controller
	  if (m_configuration_variables.ui8_riding_mode != CRUISE_MODE) { ui8_cruise_PID_initialize = 1; }
	  
	  // select riding mode
	  switch (m_configuration_variables.ui8_riding_mode)
	  {
		case POWER_ASSIST_MODE: apply_power_assist(); break;
		
		case TORQUE_ASSIST_MODE: apply_torque_assist(); break;
		
		case CADENCE_ASSIST_MODE: apply_cadence_assist(); break;
		
		case eMTB_ASSIST_MODE: apply_emtb_assist(); break;
		
		case WALK_ASSIST_MODE: apply_walk_assist(); break;
		
		case CRUISE_MODE: apply_cruise(); break;

		case CADENCE_SENSOR_CALIBRATION_MODE: apply_cadence_sensor_calibration(); break;
	  }
	  
	  // select optional ADC function
	  switch (ui8_optional_ADC_function)
	  {
		case THROTTLE_CONTROL: apply_throttle(); break;
		
		case TEMPERATURE_CONTROL: apply_temperature_limiting(); break;
	  }
	  
	  // speed limit
	  apply_speed_limit();
	  
	  // check if to enable the motor
	  if ((!ui8_motor_enabled) &&
		  (ui16_motor_get_motor_speed_erps() == 0) && // only enable motor if stopped, else something bad can happen due to high currents/regen or similar
		  (ui8_adc_battery_current_target) &&
		  (!ui8_brakes_engaged))
	  {
		ui8_motor_enabled = 1;
		ui8_g_duty_cycle = 0;
		motor_enable_pwm();
	  }

	  // check if to disable the motor
	  if ((ui8_motor_enabled) &&
		  (ui16_motor_get_motor_speed_erps() == 0) &&
		  (!ui8_adc_battery_current_target) &&
		  (!ui8_g_duty_cycle))
	  {
		ui8_motor_enabled = 0;
		motor_disable_pwm();
	  }
	  
	  // reset control parameters if... (safety)
	  //if (ui8_brakes_engaged || ui8_system_state != NO_ERROR || !ui8_motor_enabled)
	  if (ui8_brakes_engaged || ui8_system_state != NO_ERROR || !ui8_motor_enabled || ui8_riding_mode_parameter == 0)
	  {
		ui16_controller_duty_cycle_ramp_up_inverse_step = PWM_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_DEFAULT;
		ui16_controller_duty_cycle_ramp_down_inverse_step = PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_MIN;
		ui8_controller_adc_battery_current_target = 0;
		ui8_controller_duty_cycle_target = 0;
	  }
	  else
	  {
		// limit max current if higher than configured hardware limit (safety)
		if (ui8_adc_battery_current_max > ADC_10_BIT_BATTERY_CURRENT_MAX) { ui8_adc_battery_current_max = ADC_10_BIT_BATTERY_CURRENT_MAX; }
		
		// limit target current if higher than max value (safety)
		if (ui8_adc_battery_current_target > ui8_adc_battery_current_max) { ui8_adc_battery_current_target = ui8_adc_battery_current_max; }
		
		// limit target duty cycle if higher than max value
		if (ui8_duty_cycle_target > PWM_DUTY_CYCLE_MAX) { ui8_duty_cycle_target = PWM_DUTY_CYCLE_MAX; }
		
		// limit target duty cycle ramp up inverse step if lower than min value (safety)
		if (ui16_duty_cycle_ramp_up_inverse_step < PWM_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_MIN) { ui16_duty_cycle_ramp_up_inverse_step = PWM_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_MIN; } 
		
		// limit target duty cycle ramp down inverse step if lower than min value (safety)
		if (ui16_duty_cycle_ramp_down_inverse_step < PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_MIN) { ui16_duty_cycle_ramp_down_inverse_step = PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_MIN; } 
		
		// set duty cycle ramp up in controller
		ui16_controller_duty_cycle_ramp_up_inverse_step = ui16_duty_cycle_ramp_up_inverse_step;
		
		// set duty cycle ramp down in controller
		ui16_controller_duty_cycle_ramp_down_inverse_step = ui16_duty_cycle_ramp_down_inverse_step;
		
		// set target battery current in controller
		ui8_controller_adc_battery_current_target = ui8_adc_battery_current_target;
		
		// set target duty cycle in controller
		ui8_controller_duty_cycle_target = ui8_duty_cycle_target;
	  }
	}



	static void apply_power_assist()
	{
	  uint8_t ui8_power_assist_multiplier_x10 = ui8_riding_mode_parameter;
	  
	  // check for assist without pedal rotation threshold when there is no pedal rotation and standing still
	  if (ui8_assist_without_pedal_rotation_threshold && !ui8_pedal_cadence_RPM && !ui16_wheel_speed_x10)
	  {
		if (ui16_adc_pedal_torque_delta > (110 - ui8_assist_without_pedal_rotation_threshold)) { ui8_pedal_cadence_RPM = 4; }
	  }
	  
	  // calculate power assist by multiplying human power with the power assist multiplier
	  uint32_t ui32_power_assist_x100 = ((uint32_t) ui16_pedal_torque_x100 * ui8_pedal_cadence_RPM * ui8_power_assist_multiplier_x10) / 96; // see note below
	  
	  /*------------------------------------------------------------------------

		NOTE: regarding the human power calculation
		
		(1) Formula: power = torque * rotations per second * 2 * pi
		(2) Formula: power = torque * rotations per minute * 2 * pi / 60
		(3) Formula: power = torque * rotations per minute * 0.1047
		(4) Formula: power = torque * 100 * rotations per minute * 0.001047
		(5) Formula: power = torque * 100 * rotations per minute / 955
		(6) Formula: power * 10  =  torque * 100 * rotations per minute / 96
		
	  ------------------------------------------------------------------------*/
	  
	  // calculate target current
	  uint16_t ui16_battery_current_target_x100 = (ui32_power_assist_x100 * 1000) / ui16_battery_voltage_filtered_x1000;
	  
	  // set battery current target in ADC steps
	  uint16_t ui16_adc_battery_current_target = ui16_battery_current_target_x100 / BATTERY_CURRENT_PER_10_BIT_ADC_STEP_X100;
	  
	  // set motor acceleration
	  ui16_duty_cycle_ramp_up_inverse_step = map((uint32_t) ui16_wheel_speed_x10,
												 (uint32_t) 40, // 40 -> 4 kph
												 (uint32_t) 200, // 200 -> 20 kph
												 (uint32_t) ui16_duty_cycle_ramp_up_inverse_step_default,
												 (uint32_t) PWM_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_MIN);
												 
	  ui16_duty_cycle_ramp_down_inverse_step = map((uint32_t) ui16_wheel_speed_x10,
												   (uint32_t) 40, // 40 -> 4 kph
												   (uint32_t) 200, // 200 -> 20 kph
												   (uint32_t) PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_DEFAULT,
												   (uint32_t) PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_MIN);
												   
	  // set battery current target
	  if (ui16_adc_battery_current_target > ui8_adc_battery_current_max) { ui8_adc_battery_current_target = ui8_adc_battery_current_max; }
	  else { ui8_adc_battery_current_target = ui16_adc_battery_current_target; }
	  
	  // set duty cycle target
	  if (ui8_adc_battery_current_target) { ui8_duty_cycle_target = PWM_DUTY_CYCLE_MAX; }
	  else { ui8_duty_cycle_target = 0; }
	}



	static void apply_torque_assist()
	{
	  #define TORQUE_ASSIST_FACTOR_DENOMINATOR      110   // scale the torque assist target current
	  
	  // check for assist without pedal rotation threshold when there is no pedal rotation and standing still
	  if (ui8_assist_without_pedal_rotation_threshold && !ui8_pedal_cadence_RPM && !ui16_wheel_speed_x10)
	  {
		if (ui16_adc_pedal_torque_delta > (110 - ui8_assist_without_pedal_rotation_threshold)) { ui8_pedal_cadence_RPM = 1; }
	  }
	  
	  // calculate torque assistance
	  if (ui16_adc_pedal_torque_delta && ui8_pedal_cadence_RPM)
	  {
		// get the torque assist factor
		uint8_t ui8_torque_assist_factor = ui8_riding_mode_parameter;
		
		// calculate torque assist target current
		uint16_t ui16_adc_battery_current_target_torque_assist = ((uint16_t) ui16_adc_pedal_torque_delta * ui8_torque_assist_factor) / TORQUE_ASSIST_FACTOR_DENOMINATOR;
	  
		// set motor acceleration
		ui16_duty_cycle_ramp_up_inverse_step = map((uint32_t) ui16_wheel_speed_x10,
												   (uint32_t) 40, // 40 -> 4 kph
												   (uint32_t) 200, // 200 -> 20 kph
												   (uint32_t) ui16_duty_cycle_ramp_up_inverse_step_default,
												   (uint32_t) PWM_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_MIN);
												   
		ui16_duty_cycle_ramp_down_inverse_step = map((uint32_t) ui16_wheel_speed_x10,
													 (uint32_t) 40, // 40 -> 4 kph
													 (uint32_t) 200, // 200 -> 20 kph
													 (uint32_t) PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_DEFAULT,
													 (uint32_t) PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_MIN);
													 
		// set battery current target
		if (ui16_adc_battery_current_target_torque_assist > ui8_adc_battery_current_max) { ui8_adc_battery_current_target = ui8_adc_battery_current_max; }
		else { ui8_adc_battery_current_target = ui16_adc_battery_current_target_torque_assist; }

		// set duty cycle target
		if (ui8_adc_battery_current_target) { ui8_duty_cycle_target = PWM_DUTY_CYCLE_MAX; }
		else { ui8_duty_cycle_target = 0; }
	  }
	}



	static void apply_cadence_assist()
	{
	  #define CADENCE_ASSIST_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_OFFSET   50
	  
	  if (ui8_pedal_cadence_RPM)
	  {
		// get the cadence assist duty cycle target
		//uint8_t ui8_cadence_assist_duty_cycle_target = ui8_riding_mode_parameter;
		uint8_t ui8_cadence_assist_duty_cycle_target = ui8_riding_mode_parameter + ui8_pedal_cadence_RPM;
		
		// limit cadence assist duty cycle target
		if (ui8_cadence_assist_duty_cycle_target > PWM_DUTY_CYCLE_MAX) { ui8_cadence_assist_duty_cycle_target = PWM_DUTY_CYCLE_MAX; }
		
		// set motor acceleration
		ui16_duty_cycle_ramp_up_inverse_step = map((uint32_t) ui16_wheel_speed_x10,
												   (uint32_t) 40, // 40 -> 4 kph
												   (uint32_t) 200, // 200 -> 20 kph
												   (uint32_t) ui16_duty_cycle_ramp_up_inverse_step_default + CADENCE_ASSIST_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_OFFSET,
												   (uint32_t) PWM_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_MIN);
												   
		ui16_duty_cycle_ramp_down_inverse_step = map((uint32_t) ui16_wheel_speed_x10,
													 (uint32_t) 40, // 40 -> 4 kph
													 (uint32_t) 200, // 200 -> 20 kph
													 (uint32_t) PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_DEFAULT,
													 (uint32_t) PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_MIN);
													 
		// set battery current target
		ui8_adc_battery_current_target = ui8_adc_battery_current_max;
		
		// set duty cycle target
		ui8_duty_cycle_target = ui8_cadence_assist_duty_cycle_target;
	  }
	}



	static void apply_emtb_assist()
	{
	  #define eMTB_ASSIST_ADC_TORQUE_OFFSET    10
	  
	  // check for assist without pedal rotation threshold when there is no pedal rotation and standing still
	  if (ui8_assist_without_pedal_rotation_threshold && !ui8_pedal_cadence_RPM && !ui16_wheel_speed_x10)
	  {
		if (ui16_adc_pedal_torque_delta > (110 - ui8_assist_without_pedal_rotation_threshold)) { ui8_pedal_cadence_RPM = 1; }
	  }
	  
	  if ((ui16_adc_pedal_torque_delta > 0) && 
		  (ui16_adc_pedal_torque_delta < (eMTB_POWER_FUNCTION_ARRAY_SIZE - eMTB_ASSIST_ADC_TORQUE_OFFSET)) &&
		  (ui8_pedal_cadence_RPM))
	  {
		// initialize eMTB assist target current
		uint8_t ui8_adc_battery_current_target_eMTB_assist = 0;
		
		// get the eMTB assist sensitivity
		uint8_t ui8_eMTB_assist_sensitivity = ui8_riding_mode_parameter;
		
		switch (ui8_eMTB_assist_sensitivity)
		{
		  case 0: ui8_adc_battery_current_target_eMTB_assist = 0;
		  case 1: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_160[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 2: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_165[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 3: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_170[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 4: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_175[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 5: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_180[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 6: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_185[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 7: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_190[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 8: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_195[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 9: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_200[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 10: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_205[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 11: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_210[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 12: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_215[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 13: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_220[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 14: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_225[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 15: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_230[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 16: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_235[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 17: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_240[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 18: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_245[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 19: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_250[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		  case 20: ui8_adc_battery_current_target_eMTB_assist = ui8_eMTB_power_function_255[ui16_adc_pedal_torque_delta + eMTB_ASSIST_ADC_TORQUE_OFFSET]; break;
		}
		
		// set motor acceleration
		ui16_duty_cycle_ramp_up_inverse_step = map((uint32_t) ui16_wheel_speed_x10,
												   (uint32_t) 40, // 40 -> 4 kph
												   (uint32_t) 200, // 200 -> 20 kph
												   (uint32_t) ui16_duty_cycle_ramp_up_inverse_step_default,
												   (uint32_t) PWM_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_MIN);
												   
		ui16_duty_cycle_ramp_down_inverse_step = map((uint32_t) ui16_wheel_speed_x10,
													 (uint32_t) 40, // 40 -> 4 kph
													 (uint32_t) 200, // 200 -> 20 kph
													 (uint32_t) PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_DEFAULT,
													 (uint32_t) PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_MIN);
													 
		// set battery current target
		if (ui8_adc_battery_current_target_eMTB_assist > ui8_adc_battery_current_max) { ui8_adc_battery_current_target = ui8_adc_battery_current_max; }
		else { ui8_adc_battery_current_target = ui8_adc_battery_current_target_eMTB_assist; }

		// set duty cycle target
		if (ui8_adc_battery_current_target) { ui8_duty_cycle_target = PWM_DUTY_CYCLE_MAX; }
		else { ui8_duty_cycle_target = 0; }
	  }
	}



	static void apply_walk_assist()
	{
	  #define WALK_ASSIST_DUTY_CYCLE_RAMP_UP_INVERSE_STEP     200
	  #define WALK_ASSIST_DUTY_CYCLE_MAX                      80
	  #define WALK_ASSIST_ADC_BATTERY_CURRENT_MAX             80
	  
	  if (ui16_wheel_speed_x10 < WALK_ASSIST_THRESHOLD_SPEED_X10)
	  {
		// get the walk assist duty cycle target
		uint8_t ui8_walk_assist_duty_cycle_target = ui8_riding_mode_parameter;
		
		// check so that walk assist level factor is not too large (too powerful), if it is -> limit the value
		if (ui8_walk_assist_duty_cycle_target > WALK_ASSIST_DUTY_CYCLE_MAX) { ui8_walk_assist_duty_cycle_target = WALK_ASSIST_DUTY_CYCLE_MAX; }
		
		// set motor acceleration
		ui16_duty_cycle_ramp_up_inverse_step = WALK_ASSIST_DUTY_CYCLE_RAMP_UP_INVERSE_STEP;
		ui16_duty_cycle_ramp_down_inverse_step = PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_DEFAULT;
		
		// set battery current target
		ui8_adc_battery_current_target = ui8_min(WALK_ASSIST_ADC_BATTERY_CURRENT_MAX, ui8_adc_battery_current_max);
		
		// set duty cycle target
		ui8_duty_cycle_target = ui8_walk_assist_duty_cycle_target;
	  }
	}


	// changed for oem display
	static void apply_cruise()
	{
		// moved to main.h
		//#define CRUISE_PID_KP                             14    // 48 volt motor: 12, 36 volt motor: 14
		//#define CRUISE_PID_KI                             0.7   // 48 volt motor: 1, 36 volt motor: 0.7
		
		#define CRUISE_PID_INTEGRAL_LIMIT                 1000
		#define CRUISE_PID_KD                             0
		#define CRUISE_DUTY_CYCLE_RAMP_UP_INVERSE_STEP    80

		// verify speed target change
		if(ui8_riding_mode_parameter_cruise != ui8_riding_mode_parameter)
			// restart cruise counter
			ui8_cruise_counter = 0;
			
		// for next verify speed target change
		ui8_riding_mode_parameter_cruise = ui8_riding_mode_parameter;
	  
		// verify riding mode change
		if(ui8_riding_mode_cruise_temp != ui8_riding_mode_cruise)
		{	  
			// restart cruise counter
			ui8_cruise_counter = 0;
			// for next verify riding mode change
			ui8_riding_mode_cruise_temp = ui8_riding_mode_cruise;
		}	
		
		#if STREET_MODE_CRUISE_ENABLED
			#if CRUISE_MODE_CADENCE_ENABLED
			if((ui16_wheel_speed_x10 > CRUISE_THRESHOLD_SPEED_X10)&&(ui8_cruise_counter > 1)&&(ui8_pedal_cadence_RPM))
			#else
				#if ENABLE_BRAKE_SENSOR
				if((ui16_wheel_speed_x10 > CRUISE_THRESHOLD_SPEED_X10)&&(ui8_cruise_counter > 1))
				#else
				if((ui16_wheel_speed_x10 > CRUISE_THRESHOLD_SPEED_X10)&&(ui8_cruise_counter > 1)&&(ui8_pedal_cadence_RPM))
				#endif
			#endif
		#else
			#if CRUISE_MODE_CADENCE_ENABLED
			if((ui16_wheel_speed_x10 > CRUISE_THRESHOLD_SPEED_X10)&&(ui8_cruise_counter > 1)&&(!m_configuration_variables.ui8_street_mode_enabled)&&(ui8_pedal_cadence_RPM))
			#else
				#if ENABLE_BRAKE_SENSOR
				if((ui16_wheel_speed_x10 > CRUISE_THRESHOLD_SPEED_X10)&&(ui8_cruise_counter > 1)&&(!m_configuration_variables.ui8_street_mode_enabled))
				#else
				if((ui16_wheel_speed_x10 > CRUISE_THRESHOLD_SPEED_X10)&&(ui8_cruise_counter > 1)&&(!m_configuration_variables.ui8_street_mode_enabled)&&(ui8_pedal_cadence_RPM))
				#endif
			#endif
		#endif
		{		  
			static int16_t i16_error;
			static int16_t i16_last_error;
			static int16_t i16_integral;
			static int16_t i16_derivative;
			static int16_t i16_control_output;
			static uint16_t ui16_wheel_speed_target_x10;
		
			// for verify riding mode change
			ui8_riding_mode_cruise = CRUISE_MODE;
		
			// initialize cruise PID controller
			if (ui8_cruise_PID_initialize)
			{
				ui8_cruise_PID_initialize = 0;
		  
				// reset PID variables
				i16_error = 0;
				i16_last_error = 0;
				i16_integral = 320; // initialize integral to a value so the motor does not start from zero
				i16_derivative = 0;
				i16_control_output = 0;
		  
				// check what target wheel speed to use (received or current)
				uint16_t ui16_wheel_speed_target_received_x10 = (uint16_t) ui8_riding_mode_parameter * 10;
		  
				if (ui16_wheel_speed_target_received_x10 > 0)
				{
					// set received target wheel speed to target wheel speed
					ui16_wheel_speed_target_x10 = ui16_wheel_speed_target_received_x10;
				}
				else
				{
				// set current wheel speed to maintain
				ui16_wheel_speed_target_x10 = ui16_wheel_speed_x10;
				}
			}
		
			// calculate error
			i16_error = (ui16_wheel_speed_target_x10 - ui16_wheel_speed_x10);
		
			// calculate integral
			i16_integral = i16_integral + i16_error;
		
			// limit integral
			if (i16_integral > CRUISE_PID_INTEGRAL_LIMIT)
			{
				i16_integral = CRUISE_PID_INTEGRAL_LIMIT; 
			}
			else if (i16_integral < 0)
			{
				i16_integral = 0;
			}
		
			// calculate derivative
			i16_derivative = i16_error - i16_last_error;

			// save error to last error
			i16_last_error = i16_error;
		
			// calculate control output ( output =  P I D )
			i16_control_output = (CRUISE_PID_KP * i16_error) + (CRUISE_PID_KI * i16_integral) + (CRUISE_PID_KD * i16_derivative);
		
			// limit control output to just positive values
			if (i16_control_output < 0) { i16_control_output = 0; }
		
			// limit control output to the maximum value
			if (i16_control_output > 1000) { i16_control_output = 1000; }
		
			// set motor acceleration
			ui16_duty_cycle_ramp_up_inverse_step = CRUISE_DUTY_CYCLE_RAMP_UP_INVERSE_STEP;
			ui16_duty_cycle_ramp_down_inverse_step = PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_DEFAULT;
		
			// set battery current target
				ui8_adc_battery_current_target = ui8_adc_battery_current_max;
		
			// set duty cycle target  |  map the control output to an appropriate target PWM value
			ui8_duty_cycle_target = map((uint32_t) i16_control_output,
										(uint32_t) 0,                     // minimum control output from PID
										(uint32_t) 1000,                  // maximum control output from PID
										(uint32_t) 0,                     // minimum duty cycle
										(uint32_t) PWM_DUTY_CYCLE_MAX);   // maximum duty cycle
		
		}
		else
		{
			// for verify riding mode change
			ui8_riding_mode_cruise = CADENCE_ASSIST_MODE;
				
			// applies cadence assist up to cruise speed threshold
			ui8_riding_mode_parameter = ui8_riding_mode_parameter_array[CADENCE_ASSIST_MODE - 1][ui8_assist_level];
			apply_cadence_assist();
				
			// restore cruise riding mode parameter
			ui8_riding_mode_parameter = ui8_riding_mode_parameter_cruise;
			ui8_cruise_PID_initialize = 1;
		}
	}



	static void apply_cadence_sensor_calibration()
	{
	  #define CADENCE_SENSOR_CALIBRATION_MODE_DUTY_CYCLE_RAMP_UP_INVERSE_STEP     200
	  #define CADENCE_SENSOR_CALIBRATION_MODE_ADC_BATTERY_CURRENT_TARGET          8   // 8 -> 8 * 0.2 = 1.6 A
	  #define CADENCE_SENSOR_CALIBRATION_MODE_DUTY_CYCLE_TARGET                   24
	  
	  // get the ticks counter interrupt values for the different states
	  uint32_t ui32_high_state = ui16_cadence_sensor_ticks_counter_min_high;
	  uint32_t ui32_low_state = ui16_cadence_sensor_ticks_counter_min_low;
	  
	  // avoid zero division
	  if ((ui32_high_state > 0) && (ui32_low_state > 0))
	  {
		// calculate the cadence sensor pulse high percentage
		uint16_t ui16_cadence_sensor_pulse_high_percentage_x10_temp = (ui32_high_state * 1000) / (ui32_high_state + ui32_low_state);
		
		// limit the cadence sensor pulse high
		if (ui16_cadence_sensor_pulse_high_percentage_x10_temp > CADENCE_SENSOR_PULSE_PERCENTAGE_X10_MAX) { ui16_cadence_sensor_pulse_high_percentage_x10_temp = CADENCE_SENSOR_PULSE_PERCENTAGE_X10_MAX; }
		if (ui16_cadence_sensor_pulse_high_percentage_x10_temp < CADENCE_SENSOR_PULSE_PERCENTAGE_X10_MIN) { ui16_cadence_sensor_pulse_high_percentage_x10_temp = CADENCE_SENSOR_PULSE_PERCENTAGE_X10_MIN; }
		
		// filter the cadence sensor pulse high percentage
		m_configuration_variables.ui16_cadence_sensor_pulse_high_percentage_x10 = filter(ui16_cadence_sensor_pulse_high_percentage_x10_temp, m_configuration_variables.ui16_cadence_sensor_pulse_high_percentage_x10, 90);
	  }
	  
	  // set motor acceleration
	  ui16_duty_cycle_ramp_up_inverse_step = CADENCE_SENSOR_CALIBRATION_MODE_DUTY_CYCLE_RAMP_UP_INVERSE_STEP;
	  ui16_duty_cycle_ramp_down_inverse_step = PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_DEFAULT;

	  // set battery current target
	  ui8_adc_battery_current_target = CADENCE_SENSOR_CALIBRATION_MODE_ADC_BATTERY_CURRENT_TARGET;
	  
	  // set duty cycle target
	  ui8_duty_cycle_target = CADENCE_SENSOR_CALIBRATION_MODE_DUTY_CYCLE_TARGET;
	}



	static void apply_throttle()
	{
	  #define THROTTLE_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_DEFAULT    80
	  #define THROTTLE_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_MIN        40
	  
	  // map value from 0 to 255
	  ui8_adc_throttle = map((uint8_t) UI8_ADC_THROTTLE,
							 (uint8_t) ADC_THROTTLE_MIN_VALUE,
							 (uint8_t) ADC_THROTTLE_MAX_VALUE,
							 (uint8_t) 0,
							 (uint8_t) 255);
							 
	  // map ADC throttle value from 0 to max battery current
	  uint8_t ui8_adc_battery_current_target_throttle = map((uint8_t) ui8_adc_throttle,
															(uint8_t) 0,
															(uint8_t) 255,
															(uint8_t) 0,
															(uint8_t) ui8_adc_battery_current_max);
															
	  if (ui8_adc_battery_current_target_throttle > ui8_adc_battery_current_target)
	  {
		// set motor acceleration
		ui16_duty_cycle_ramp_up_inverse_step = map((uint32_t) ui16_wheel_speed_x10,
												   (uint32_t) 40,
												   (uint32_t) 400,
												   (uint32_t) THROTTLE_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_DEFAULT,
												   (uint32_t) THROTTLE_DUTY_CYCLE_RAMP_UP_INVERSE_STEP_MIN);
												   
		ui16_duty_cycle_ramp_down_inverse_step = map((uint32_t) ui16_wheel_speed_x10,
													 (uint32_t) 40,
													 (uint32_t) 400,
													 (uint32_t) PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_DEFAULT,
													 (uint32_t) PWM_DUTY_CYCLE_RAMP_DOWN_INVERSE_STEP_MIN);
													 
		// set battery current target
		ui8_adc_battery_current_target = ui8_adc_battery_current_target_throttle;
		
		// set duty cycle target
		ui8_duty_cycle_target = PWM_DUTY_CYCLE_MAX;
	  }
	}



	static void apply_temperature_limiting()
	{
	  static uint16_t ui16_adc_motor_temperature_filtered;
	  
	  // get ADC measurement
	  volatile uint16_t ui16_temp = UI16_ADC_10_BIT_THROTTLE;
	  
	  // filter ADC measurement to motor temperature variable
	  ui16_adc_motor_temperature_filtered = filter(ui16_temp, ui16_adc_motor_temperature_filtered, 80);
	  
	  // convert ADC value
	  ui16_motor_temperature_filtered_x10 = ((uint32_t) ui16_adc_motor_temperature_filtered * 10000) / 2048;
	  
	  // min temperature value can not be equal or higher than max temperature value
	  if (ui8_motor_temperature_min_value_to_limit >= ui8_motor_temperature_max_value_to_limit)
	  {
		ui8_adc_battery_current_target = 0;
		ui8_temperature_current_limiting_value = 0;
	  }
	  else
	  {
		// adjust target current if motor over temperature limit
		ui8_adc_battery_current_target = map((uint32_t) ui16_motor_temperature_filtered_x10,
											 (uint32_t) ui8_motor_temperature_min_value_to_limit * 10,
											 (uint32_t) ui8_motor_temperature_max_value_to_limit * 10,
											 (uint32_t) ui8_adc_battery_current_target,
											 (uint32_t) 0);
											 
		// get a value linear to the current limitation, just to show to user
		ui8_temperature_current_limiting_value = map((uint32_t) ui16_motor_temperature_filtered_x10,
													 (uint32_t) ui8_motor_temperature_min_value_to_limit * 10,
													 (uint32_t) ui8_motor_temperature_max_value_to_limit * 10,
													 (uint32_t) 255,
													 (uint32_t) 0);
	  }
	}



	static void apply_speed_limit()
	{
	  if (m_configuration_variables.ui8_wheel_speed_max > 0)
	  {
		// set battery current target 
		ui8_adc_battery_current_target = map((uint32_t) ui16_wheel_speed_x10,
											 (uint32_t) ((m_configuration_variables.ui8_wheel_speed_max * 10) - 20),
											 (uint32_t) ((m_configuration_variables.ui8_wheel_speed_max * 10) + 20),
											 (uint32_t) ui8_adc_battery_current_target,
											 (uint32_t) 0);
	  }
	}

	


	static void calc_wheel_speed(void)
	{ 
	  // calc wheel speed in km/h
	  if (ui16_wheel_speed_sensor_ticks)
	  {
		float f_wheel_speed_x10 = (float) PWM_CYCLES_SECOND / ui16_wheel_speed_sensor_ticks; // rps
		ui16_wheel_speed_x10 = f_wheel_speed_x10 * m_configuration_variables.ui16_wheel_perimeter * 0.036; // rps * millimeters per second * ((3600 / (1000 * 1000)) * 10) kms per hour * 10
	  }
	  else
	  {
		ui16_wheel_speed_x10 = 0;
	  }
	}



	static void calc_cadence(void)
	{
	  #define CADENCE_SENSOR_TICKS_COUNTER_MIN_AT_SPEED       800
	  
	  // get the cadence sensor ticks
	  uint16_t ui16_cadence_sensor_ticks_temp = ui16_cadence_sensor_ticks;
	  
	  // get the cadence sensor pulse state
	  uint8_t ui8_cadence_sensor_pulse_state_temp = ui8_cadence_sensor_pulse_state;
	  
	  // adjust cadence sensor ticks counter min depending on wheel speed
	  ui16_cadence_sensor_ticks_counter_min_speed_adjusted = map((uint32_t) ui16_wheel_speed_x10,
																 (uint32_t) 40,
																 (uint32_t) 400,
																 (uint32_t) CADENCE_SENSOR_TICKS_COUNTER_MIN,
																 (uint32_t) CADENCE_SENSOR_TICKS_COUNTER_MIN_AT_SPEED);
																 
	  // select cadence sensor mode
	  switch (m_configuration_variables.ui8_cadence_sensor_mode)
	  {
		case STANDARD_MODE:
		
		  // calculate cadence in RPM and avoid zero division
		  if (ui16_cadence_sensor_ticks_temp)
		  {
			ui8_pedal_cadence_RPM = 46875 / ui16_cadence_sensor_ticks_temp;
		  }
		  else
		  {
			ui8_pedal_cadence_RPM = 0;
		  }
		  
		  /*-------------------------------------------------------------------------------------------------
		  
			NOTE: regarding the cadence calculation
			
			Cadence in standard mode is calculated by counting how many ticks there are between two 
			transitions of LOW to HIGH.
			
			Formula for calculating the cadence in RPM:
			
			(1) Cadence in RPM = 60 / (ticks * CADENCE_SENSOR_NUMBER_MAGNETS * 0.000064)
			
			(2) Cadence in RPM = 60 / (ticks * 0.00128)
			
			(3) Cadence in RPM = 46875 / ticks
			
		  -------------------------------------------------------------------------------------------------*/
		
		break;
		
		case ADVANCED_MODE:
		
		  // set the pulse duty cycle in ticks
		  ui16_cadence_sensor_ticks_counter_min_high = ((uint32_t) m_configuration_variables.ui16_cadence_sensor_pulse_high_percentage_x10 * ui16_cadence_sensor_ticks_counter_min_speed_adjusted) / 1000;
		  ui16_cadence_sensor_ticks_counter_min_low = ((uint32_t) (1000 - m_configuration_variables.ui16_cadence_sensor_pulse_high_percentage_x10) * ui16_cadence_sensor_ticks_counter_min_speed_adjusted) / 1000;
		  
		  // calculate cadence in RPM and avoid zero division
		  if (ui16_cadence_sensor_ticks_temp)
		  {
			// adjust cadence calculation depending on pulse state
			if (ui8_cadence_sensor_pulse_state_temp)
			{
			  ui8_pedal_cadence_RPM = ((uint32_t) (1000 - m_configuration_variables.ui16_cadence_sensor_pulse_high_percentage_x10) * 46875) / ((uint32_t) ui16_cadence_sensor_ticks_temp * 1000);
			}
			else
			{
			  ui8_pedal_cadence_RPM = ((uint32_t) m_configuration_variables.ui16_cadence_sensor_pulse_high_percentage_x10 * 46875) / ((uint32_t) ui16_cadence_sensor_ticks_temp * 1000);
			}
		  }
		  else
		  {
			ui8_pedal_cadence_RPM = 0;
		  }
		  
		  /*-------------------------------------------------------------------------------------------------
		  
			NOTE: regarding the cadence calculation
			
			Cadence in advanced mode is calculated by counting how many ticks there are between all 
			transitions of any kind. 
			
			By measuring all transitions it is possible to double the cadence 
			resolution or to half the response time. 
			
			When using the advanced mode it is important to adjust for the different spacings between 
			different kind of transitions. This is why there is a conversion factor.
			
			Formula for calculating the cadence in RPM using the advanced mode with 
			double the transitions:
			
			(1) Cadence in RPM = 6000 / (ticks * pulse_duty_cycle * CADENCE_SENSOR_NUMBER_MAGNETS * 0.000064)

			(2) Cadence in RPM = 6000 / (ticks * pulse_duty_cycle * 0.00128)
			
			(3) Cadence in RPM = 4687500 / (ticks * pulse_duty_cycle)


			(1) Cadence in RPM * 2 = 60 / (ticks * CADENCE_SENSOR_NUMBER_MAGNETS * 0.000064)
			
			(2) Cadence in RPM * 2 = 60 / (ticks * 0.00128)
			
			(3) Cadence in RPM * 2 = 4687500 / ticks

			
		  -------------------------------------------------------------------------------------------------*/
	  
		break;
		
		case CALIBRATION_MODE:
		  
		  // set the pedal cadence to zero because calibration is taking place
		  ui8_pedal_cadence_RPM = 0;
		
		break;
	  }
	}



	static void get_battery_voltage_filtered(void)
	{
	  ui16_battery_voltage_filtered_x1000 = ui16_adc_battery_voltage_filtered * BATTERY_VOLTAGE_PER_10_BIT_ADC_STEP_X1000;
	}



	static void get_battery_current_filtered(void)
	{
	  ui8_battery_current_filtered_x10 = ((uint16_t) ui8_adc_battery_current_filtered * BATTERY_CURRENT_PER_10_BIT_ADC_STEP_X100) / 10;
	}



	static void get_pedal_torque(void)
	{
	  // get adc pedal torque
	  ui16_adc_pedal_torque = UI16_ADC_10_BIT_TORQUE_SENSOR;
	  
	  // calculate the delta value of adc pedal torque and the adc pedal torque offset from calibration
	  if (ui16_adc_pedal_torque > ui16_adc_pedal_torque_offset)
	  {
		ui16_adc_pedal_torque_delta = ui16_adc_pedal_torque - ui16_adc_pedal_torque_offset;
	  }
	  else
	  {
		ui16_adc_pedal_torque_delta = 0;
	  }
	  
	  // calculate torque on pedals
	  ui16_pedal_torque_x100 = ui16_adc_pedal_torque_delta * m_configuration_variables.ui8_pedal_torque_per_10_bit_ADC_step_x100;

	  // calculate human power
	  ui16_human_power_x10 = ((uint32_t) ui16_pedal_torque_x100 * ui8_pedal_cadence_RPM) / 96; // see note below
	  
	  /*------------------------------------------------------------------------

		NOTE: regarding the human power calculation
		
		(1) Formula: power = torque * rotations per second * 2 * pi
		(2) Formula: power = torque * rotations per minute * 2 * pi / 60
		(3) Formula: power = torque * rotations per minute * 0.1047
		(4) Formula: power = torque * 100 * rotations per minute * 0.001047
		(5) Formula: power = torque * 100 * rotations per minute / 955
		(6) Formula: power * 10  =  torque * 100 * rotations per minute / 96
		
	  ------------------------------------------------------------------------*/
	}



	struct_configuration_variables* get_configuration_variables (void)
	{
	  return &m_configuration_variables;
	}



	static void check_brakes()
	{
	  ui8_brakes_engaged = ui8_brake_state;
	}



	static void check_system()
	{
	  #define MOTOR_BLOCKED_COUNTER_THRESHOLD               10    // 10  =>  1.0 second
	  #define MOTOR_BLOCKED_BATTERY_CURRENT_THRESHOLD_X10   50    // 50  =>  5.0 amps
	  #define MOTOR_BLOCKED_ERPS_THRESHOLD                  10    // 10 ERPS
	  #define MOTOR_BLOCKED_RESET_COUNTER_THRESHOLD         100   // 100  =>  10 seconds
	  
	  static uint8_t ui8_motor_blocked_counter;
	  static uint8_t ui8_motor_blocked_reset_counter;

	  // if the motor blocked error is enabled start resetting it
	  if (ui8_system_state == ERROR_MOTOR_BLOCKED)
	  {
		// increment motor blocked reset counter with 100 milliseconds
		ui8_motor_blocked_reset_counter++;
		
		// check if the counter has counted to the set threshold for reset
		if (ui8_motor_blocked_reset_counter > MOTOR_BLOCKED_RESET_COUNTER_THRESHOLD)
		{
		  // reset motor blocked error code
		  if (ui8_system_state == ERROR_MOTOR_BLOCKED) { ui8_system_state = NO_ERROR; }
		  
		  // reset the counter that clears the motor blocked error
		  ui8_motor_blocked_reset_counter = 0;
		}
	  }
	  else
	  {
		// if battery current is over the current threshold and the motor ERPS is below threshold start setting motor blocked error code
		if ((ui8_battery_current_filtered_x10 > MOTOR_BLOCKED_BATTERY_CURRENT_THRESHOLD_X10) && (ui16_motor_get_motor_speed_erps() < MOTOR_BLOCKED_ERPS_THRESHOLD))
		{
		  // increment motor blocked counter with 100 milliseconds
		  ++ui8_motor_blocked_counter;
		  
		  // check if motor is blocked for more than some safe threshold
		  if (ui8_motor_blocked_counter > MOTOR_BLOCKED_COUNTER_THRESHOLD)
		  {
			// set error code
			ui8_system_state = ERROR_MOTOR_BLOCKED;
			
			// reset motor blocked counter as the error code is set
			ui8_motor_blocked_counter = 0;
		  }
		}
		else
		{
		  // current is below the threshold and/or motor ERPS is above the threshold so reset the counter
		  ui8_motor_blocked_counter = 0;
		}
	  }
	  
	  
	  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	  
	  
	  // check torque sensor
	  if (((ui16_adc_pedal_torque_offset > 300) || (ui16_adc_pedal_torque_offset < 10) || (ui16_adc_pedal_torque > 500)) &&
		  ((m_configuration_variables.ui8_riding_mode == POWER_ASSIST_MODE) || (m_configuration_variables.ui8_riding_mode == TORQUE_ASSIST_MODE) || (m_configuration_variables.ui8_riding_mode == eMTB_ASSIST_MODE)))
	  {
		// set error code
		ui8_system_state = ERROR_TORQUE_SENSOR;
	  }
	  else if (ui8_system_state == ERROR_TORQUE_SENSOR)
	  {
		// reset error code
		ui8_system_state = NO_ERROR;
	  }
	  
	  
	  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	  
	  
	  // check cadence sensor calibration
	  if ((m_configuration_variables.ui8_cadence_sensor_mode == ADVANCED_MODE) &&
		  ((m_configuration_variables.ui16_cadence_sensor_pulse_high_percentage_x10 == CADENCE_SENSOR_PULSE_PERCENTAGE_X10_DEFAULT) ||
		   (m_configuration_variables.ui16_cadence_sensor_pulse_high_percentage_x10 > CADENCE_SENSOR_PULSE_PERCENTAGE_X10_MAX) ||
		   (m_configuration_variables.ui16_cadence_sensor_pulse_high_percentage_x10 < CADENCE_SENSOR_PULSE_PERCENTAGE_X10_MIN)))
	  {
		// set error code
		ui8_system_state = ERROR_CADENCE_SENSOR_CALIBRATION;
	  }
	  else if (ui8_system_state == ERROR_CADENCE_SENSOR_CALIBRATION)
	  {
		// reset error code
		ui8_system_state = NO_ERROR;
	  }
	}



	void ebike_control_lights(void)
	{
	  #define DEFAULT_FLASH_ON_COUNTER_MAX      3
	  //#define DEFAULT_FLASH_OFF_COUNTER_MAX     1
	  #define DEFAULT_FLASH_OFF_COUNTER_MAX     2
	  #define BRAKING_FLASH_ON_COUNTER_MAX      1
	  #define BRAKING_FLASH_OFF_COUNTER_MAX     1
	  
	  static uint8_t ui8_default_flash_state;
	  static uint8_t ui8_default_flash_state_counter; // increments every function call -> 100 ms
	  static uint8_t ui8_braking_flash_state;
	  static uint8_t ui8_braking_flash_state_counter; // increments every function call -> 100 ms
	  
	  
	  /****************************************************************************/
	  
	  
	  // increment flash counters
	  ++ui8_default_flash_state_counter;
	  ++ui8_braking_flash_state_counter;
	  
	  
	  /****************************************************************************/
	  
	  
	  // set default flash state
	  if ((ui8_default_flash_state) && (ui8_default_flash_state_counter > DEFAULT_FLASH_ON_COUNTER_MAX))
	  {
		// reset flash state counter
		ui8_default_flash_state_counter = 0;
		
		// toggle flash state
		ui8_default_flash_state = 0;
	  }
	  else if ((!ui8_default_flash_state) && (ui8_default_flash_state_counter > DEFAULT_FLASH_OFF_COUNTER_MAX))
	  {
		// reset flash state counter
		ui8_default_flash_state_counter = 0;
		
		// toggle flash state
		ui8_default_flash_state = 1;
	  }
	  
	  
	  /****************************************************************************/
	  
	  
	  // set braking flash state
	  if ((ui8_braking_flash_state) && (ui8_braking_flash_state_counter > BRAKING_FLASH_ON_COUNTER_MAX))
	  {
		// reset flash state counter
		ui8_braking_flash_state_counter = 0;
		
		// toggle flash state
		ui8_braking_flash_state = 0;
	  }
	  else if ((!ui8_braking_flash_state) && (ui8_braking_flash_state_counter > BRAKING_FLASH_OFF_COUNTER_MAX))
	  {
		// reset flash state counter
		ui8_braking_flash_state_counter = 0;
		
		// toggle flash state
		ui8_braking_flash_state = 1;
	  }
	  
	  
	  /****************************************************************************/
	  
	  
	  // select lights configuration
	  switch (m_configuration_variables.ui8_lights_configuration)
	  {
		case 0:
		
		  // set lights
		  lights_set_state(ui8_lights_state);
		  
		break;

		case 1:
		  
		  // check lights state
		  if (ui8_lights_state)
		  {
			// set lights
			lights_set_state(ui8_default_flash_state);
		  }
		  else
		  {
			// set lights
			lights_set_state(ui8_lights_state);
		  }
		  
		break;
		
		case 2:
		  
		  // check light and brake state
		  if (ui8_lights_state && ui8_brakes_engaged)
		  {
			// set lights
			lights_set_state(ui8_braking_flash_state);
		  }
		  else
		  {
			// set lights
			lights_set_state(ui8_lights_state);
		  }
		  
		break;
		
		case 3:
		  
		  // check light and brake state
		  if (ui8_lights_state && ui8_brakes_engaged)
		  {
			// set lights
			lights_set_state(ui8_brakes_engaged);
		  }
		  else if (ui8_lights_state)
		  {
			// set lights
			lights_set_state(ui8_default_flash_state);
		  }
		  else
		  {
			// set lights
			lights_set_state(ui8_lights_state);
		  }
		  
		break;
		
		case 4:
		  
		  // check light and brake state
		  if (ui8_lights_state && ui8_brakes_engaged)
		  {
			// set lights
			lights_set_state(ui8_braking_flash_state);
		  }
		  else if (ui8_lights_state)
		  {
			// set lights
			lights_set_state(ui8_default_flash_state);
		  }
		  else
		  {
			// set lights
			lights_set_state(ui8_lights_state);
		  }
		  
		break;
		
		case 5:
		  
		  // check brake state
		  if (ui8_brakes_engaged)
		  {
			// set lights
			lights_set_state(ui8_brakes_engaged);
		  }
		  else
		  {
			// set lights
			lights_set_state(ui8_lights_state);
		  }
		  
		break;
		
		case 6:
		  
		  // check brake state
		  if (ui8_brakes_engaged)
		  {
			// set lights
			lights_set_state(ui8_braking_flash_state);
		  }
		  else
		  {
			// set lights
			lights_set_state(ui8_lights_state);
		  }
		  
		break;
		
		case 7:
		  
		  // check brake state
		  if (ui8_brakes_engaged)
		  {
			// set lights
			lights_set_state(ui8_brakes_engaged);
		  }
		  else if (ui8_lights_state)
		  {
			// set lights
			lights_set_state(ui8_default_flash_state);
		  }
		  else
		  {
			// set lights
			lights_set_state(ui8_lights_state);
		  }
		  
		break;
		
		case 8:
		  
		  // check brake state
		  if (ui8_brakes_engaged)
		  {
			// set lights
			lights_set_state(ui8_braking_flash_state);
		  }
		  else if (ui8_lights_state)
		  {
			// set lights
			lights_set_state(ui8_default_flash_state);
		  }
		  else
		  {
			// set lights
			lights_set_state(ui8_lights_state);
		  }
		  
		break;
		
		default:
		
		  // set lights
		  lights_set_state(ui8_lights_state);
		  
		break;
	  }
	  
	  /*------------------------------------------------------------------------------------------------------------------

		NOTE: regarding the various light modes
		
		(0) lights ON when enabled
		(1) lights FLASHING when enabled
		
		(2) lights ON when enabled and BRAKE-FLASHING when braking
		(3) lights FLASHING when enabled and ON when braking
		(4) lights FLASHING when enabled and BRAKE-FLASHING when braking
		
		(5) lights ON when enabled, but ON when braking regardless if lights are enabled
		(6) lights ON when enabled, but BRAKE-FLASHING when braking regardless if lights are enabled
		
		(7) lights FLASHING when enabled, but ON when braking regardless if lights are enabled
		(8) lights FLASHING when enabled, but BRAKE-FLASHING when braking regardless if lights are enabled
		
	  ------------------------------------------------------------------------------------------------------------------*/

		// display blinking code function
		ui8_display_blinking_code = ui8_default_flash_state;
	  
	 }


	// This is the interrupt that happens when UART2 receives data. We need it to be the fastest possible and so
	// we do: receive every byte and assembly as a package, finally, signal that we have a package to process (on main slow loop)
	// and disable the interrupt. The interrupt should be enable again on main loop, after the package being processed
	void UART2_IRQHandler(void) __interrupt(UART2_IRQHANDLER)
	{
		if(UART2_GetFlagStatus(UART2_FLAG_RXNE) == SET)
	  {
		UART2->SR &= (uint8_t)~(UART2_FLAG_RXNE); // this may be redundant

		ui8_byte_received = UART2_ReceiveData8();

		switch(ui8_state_machine)
		{
		  case 0:
		  if(ui8_byte_received == RX_STX) // see if we get start package byte
		  {
			ui8_rx_buffer[ui8_rx_counter] = ui8_byte_received;
			ui8_rx_counter++;
			ui8_state_machine = 1;
		  }
		  else
		  {
			ui8_rx_counter = 0;
			ui8_state_machine = 0;
		  }
		  break;

		  case 1:
		  ui8_rx_buffer[ui8_rx_counter] = ui8_byte_received;
		  ui8_rx_counter++;

		  // see if is the last byte of the package
		  if(ui8_rx_counter > UART_RX_BUFFER_LEN)
		  {
			ui8_rx_counter = 0;
			ui8_state_machine = 0;
			ui8_received_package_flag = 1; // signal that we have a full package to be processed
			UART2->CR2 &= ~(1 << 5); // disable UART2 receive interrupt
		  }			
		  break;

		  default:
		  break;
		}
	  }
	}

	// changed for oem display
	static void communications_controller (void)
	{
		#ifndef DEBUG_UART

		// reset riding mode (safety)
		//m_configuration_variables.ui8_riding_mode = OFF_MODE;
	  
		uart_receive_package ();
		check_battery_soc();
		calc_oem_wheel_speed();
		uart_send_package ();

		#endif
	}

	// changed for oem display
	static void uart_receive_package(void)
	{
		uint8_t ui8_rx_check_code;
		uint8_t ui8_assist_level_mask;
		uint8_t ui8_street_mode_enabled_temp;
		uint8_t ui8_set_parameter_enabled_temp;
		uint8_t ui8_riding_mode_temp;
		
		// increment walk assist counter
		ui8_walk_assist_counter++;
		
		// increment cruise counter
		ui8_cruise_counter++;
		
		// increment display mode default counter
		ui16_display_mode_default_counter++;
		
		// increment lights_counter
		ui8_lights_counter++;
		
		// increment display menu counter
		ui8_menu_counter++;

		if(ui8_received_package_flag)
		{
			// verify check code of the package
			ui8_rx_check_code = 0x00;
			for(ui8_i = 0; ui8_i < RX_CHECK_CODE; ui8_i++)
			{
				ui8_rx_check_code += ui8_rx_buffer[ui8_i];
			}

			// see if check code is ok...
			if(ui8_rx_check_code == ui8_rx_buffer[RX_CHECK_CODE])
			{
				// mask assist level from display
				ui8_assist_level_mask = ui8_rx_buffer[1] & 0x5E; // mask: 01011110

				// set assist level
				switch(ui8_assist_level_mask)
				{
					case ASSIST_PEDAL_LEVEL0: ui8_assist_level = OFF; break;
					case ASSIST_PEDAL_LEVEL1: ui8_assist_level = ECO; break;
					case ASSIST_PEDAL_LEVEL2: ui8_assist_level = TOUR; break;
					case ASSIST_PEDAL_LEVEL3: ui8_assist_level = SPORT; break;
					case ASSIST_PEDAL_LEVEL4: ui8_assist_level = TURBO; break;
				}
				
				// display lights button pressed:
				if(ui8_rx_buffer[1] & 0x01)
				{
					// lights off:
					if(!ui8_lights_flag)
					{
						// lights 5s on
						ui8_lights_on_5s = 1;
						
						// menu flag cleared
						if((!ui8_menu_flag) && (ui8_display_fault_code == NO_FAULT))
						{
							// set menu flag
							ui8_menu_flag = 1;
								
							// set menu index
							if(++ui8_menu_index > 3)
								ui8_menu_index = 1;
							
							// display function code enabled (E02, E03,E04)
							ui8_display_function_code = ui8_menu_index + 1;
							
							#if !ENABLE_XH18 && ENABLE_DISPLAY_DOUBLE_DATA
							// display data code enabled (E02, E03,E04,E05, E06,E07)
							if(ui8_display_data_enabled)
							{	
								ui8_display_function_code = ui8_data_index + 2;
							}
							#endif
							
							// restart lights counter
							ui8_lights_counter = 0;	
							// restart menu counter
							ui8_menu_counter = 0;					
						}
						// after some seconds: switch on lights (if enabled) and abort function
						if((ui8_lights_counter >= DELAY_LIGHTS_ON)||((ui8_assist_level != ui8_assist_level_temp)&&(!ui8_display_data_enabled))) 
						{
							#if ENABLE_LIGHTS
							// set lights flag
							ui8_lights_flag = 1;
							// lights 5s off		
							ui8_lights_on_5s = 0;
							#endif
							// clear menu flag
							ui8_menu_flag = 0;
							// clear menu index
							ui8_menu_index = 0;
							// display function code disabled
							ui8_display_function_code = NO_FUNCTION;
						}
					}
				}
				else
				{
					// lights off:
					if(!ui8_lights_flag)
					{
						// menu flag active:
						if(ui8_menu_flag)
						{
							// clear menu flag
							ui8_menu_flag = 0;
								
							// lights 5s off		
							ui8_lights_on_5s = 0;	
								
							// restart menu counter
							ui8_menu_counter = 0;
								
							// menu function enabled
							ui8_menu_function_enabled = 1;
						}
					}
					// lights on:
					else
					{
						// clear lights flag
						ui8_lights_flag = 0;
					}
				}

				// restart menu display function
				if((ui8_menu_counter >= ui8_delay_display_function)||(ui8_assist_level != ui8_assist_level_temp))
				{	
					// clear menu flag
					ui8_menu_flag = 0;
					// clear menu index
					ui8_menu_index = 0;
					// menu function disabled
					ui8_menu_function_enabled = 0;
					// display function code disabled
					ui8_display_function_code = NO_FUNCTION;
					// clear data index
					ui8_data_index = 0;
					// clear double data
					ui8_double_data_flag = 0;
					ui8_double_data_index = 0;
					// display data function disabled
					ui8_display_data_enabled = 0;
					// battery SOC checked at power on
					ui8_battery_SOC_init_flag = 1;
				}
				
				// assist level temp, to stop operation at change of level
				ui8_assist_level_temp = ui8_assist_level;
				
				// display menu function
				if(ui8_menu_function_enabled)
				{
					// display ready
					if(ui8_display_ready_flag)
					{
						// set display parameter
						if((m_configuration_variables.ui8_set_parameter_enabled)||(ui8_assist_level == OFF))
						{
							switch(ui8_assist_level)
							{	
								case OFF:
									// set parameter/data mode
									ui8_set_parameter_enabled_temp = m_configuration_variables.ui8_set_parameter_enabled;
									switch(ui8_menu_index)
									{
										case 1:
											#if ENABLE_SET_PARAMETER_ON_STARTUP
											m_configuration_variables.ui8_set_parameter_enabled = 0;
											#else
											m_configuration_variables.ui8_set_parameter_enabled = 1;
											#endif
											// restart display mode default counter
											ui16_display_mode_default_counter = 0;
										break;
										case 2:
											#if ENABLE_SET_PARAMETER_ON_STARTUP
											m_configuration_variables.ui8_set_parameter_enabled = 1;
											#else
											m_configuration_variables.ui8_set_parameter_enabled = 0;
											#endif
										break;
										case 3:
											m_configuration_variables.ui8_set_parameter_enabled = ui8_set_parameter_enabled_temp;
											EEPROM_controller(WRITE_TO_MEMORY, EEPROM_BYTES_INIT_OEM_DISPLAY);
										break;
									}
								break;
		
								case ECO:
									// set street/offroad mode
									ui8_street_mode_enabled_temp = m_configuration_variables.ui8_street_mode_enabled;
									switch(ui8_menu_index)
									{
										case 1:
											#if ENABLE_STREET_MODE_ON_STARTUP
											m_configuration_variables.ui8_street_mode_enabled =  0;
											#else
											m_configuration_variables.ui8_street_mode_enabled =  1;
											#endif	
										break;
										case 2:
											#if ENABLE_STREET_MODE_ON_STARTUP
											m_configuration_variables.ui8_street_mode_enabled =  1;
											#else
											m_configuration_variables.ui8_street_mode_enabled =  0;
											#endif
										break;
										case 3:
											m_configuration_variables.ui8_street_mode_enabled = ui8_street_mode_enabled_temp;
											m_configuration_variables.ui8_cadence_sensor_mode = STANDARD_MODE;
										break;
									}
								break;
		
								case TOUR:
									// set riding mode 1
									switch(ui8_menu_index)
									{
										case 1: m_configuration_variables.ui8_riding_mode = POWER_ASSIST_MODE; break;
										case 2: m_configuration_variables.ui8_riding_mode = TORQUE_ASSIST_MODE; break;
										case 3: m_configuration_variables.ui8_riding_mode = CADENCE_ASSIST_MODE; break;
									}
								break;

								case SPORT:
									// set riding mode 2
									ui8_riding_mode_temp = m_configuration_variables.ui8_riding_mode;
									switch(ui8_menu_index)
									{
										case 1: m_configuration_variables.ui8_riding_mode = eMTB_ASSIST_MODE; break;
										case 2: m_configuration_variables.ui8_riding_mode = CRUISE_MODE; break;
										case 3:
											m_configuration_variables.ui8_cadence_sensor_mode = ADVANCED_MODE;
											//m_configuration_variables.ui8_cadence_sensor_mode = CALIBRATION_MODE;
											//m_configuration_variables.ui8_riding_mode = CADENCE_SENSOR_CALIBRATION_MODE;
											m_configuration_variables.ui8_riding_mode = ui8_riding_mode_temp;
										break;
									}
								break;

								case TURBO:
									// set lights mode
									switch(ui8_menu_index)
									{
										case 1: m_configuration_variables.ui8_lights_configuration = LIGHTS_CONFIGURATION_1; break;
										case 2: m_configuration_variables.ui8_lights_configuration = LIGHTS_CONFIGURATION_2; break;
										case 3: m_configuration_variables.ui8_lights_configuration = LIGHTS_CONFIGURATION_3; break;
									}
								break;
							}
							// delay menu function
							ui8_delay_display_function  = DELAY_MENU_ON;
						}
						else
						{
							// display data function enabled
							ui8_display_data_enabled = 1;
						
							#if ENABLE_DISPLAY_DOUBLE_DATA				
							// set double data index
							if((!ui8_double_data_flag)&&(ui8_menu_index == 1))
							{
								ui8_double_data_index = 0;
							}
							if((!ui8_double_data_flag)&&(ui8_menu_index == 3))
							{
								ui8_double_data_flag = 1;
							}
							if((ui8_double_data_flag)&&(ui8_menu_index == 1))
							{
								ui8_double_data_index = 3;
							}
							if((ui8_double_data_flag)&&(ui8_menu_index == 3)&&(ui8_double_data_index == 3))
							{
								ui8_double_data_flag = 0;
							}
							#endif
							
							// set data index
							ui8_data_index = ui8_menu_index + ui8_double_data_index - 1;
							
							// delay data function
							ui8_delay_display_function  = ui8_display_time_array[ui8_data_index];
							
							// display function code disabled
							ui8_display_function_code = NO_FUNCTION;
						}
					}
					else
					{
						// manually set battery percentage x10 (full charge) within 4 seconds of power on
						if(ui8_assist_level == TURBO)
						{
							ui16_battery_SOC_percentage_x10 = 1000;
							ui32_wh_x10_offset = 0;
							ui8_display_data_enabled = 1;
						}
					}
				}
				
				#if ENABLE_RETURN_DEFAULT_DISPLAY_MODE
				// delay display mode default
				if (ui16_display_mode_default_counter > DELAY_DISPLAY_MODE_DEFAULT_X10)
				{
					m_configuration_variables.ui8_set_parameter_enabled = ENABLE_SET_PARAMETER_ON_STARTUP;
				}
				#endif
				
				// menu function disabled
				ui8_menu_function_enabled = 0;
				
				// walk assist mode
				#if ENABLE_WALK_ASSIST
				// walk assist button pressed
				if((ui8_rx_buffer[1] & 0x20)&&(ui8_display_ready_flag))
				{							
					if(!ui8_walk_assist_flag)
					{
						// set walk assist flag
						ui8_walk_assist_flag = 1;
						// actual riding mode
						ui8_actual_riding_mode = m_configuration_variables.ui8_riding_mode;
						// walk assist level
						ui8_walk_assist_level = ui8_assist_level;
						// set walk assist mode
						m_configuration_variables.ui8_riding_mode = WALK_ASSIST_MODE;
					}
					
					#if WALK_ASSIST_DEBOUNCE_ENABLED && ENABLE_BRAKE_SENSOR
					// restart walk assist counter
					ui8_walk_assist_counter = 0;
					#endif
				}
				else
				{
					#if WALK_ASSIST_DEBOUNCE_ENABLED && ENABLE_BRAKE_SENSOR
					if(ui8_walk_assist_counter < WALK_ASSIST_DEBOUNCE_TIME)
					{
						// stop walk assist during debounce time
						#if ENABLE_XH18
						// XH18 level up button
						if(ui8_assist_level > ui8_walk_assist_level)
						{	
							if(ui8_walk_assist_flag)
								// restore previous riding mode
								m_configuration_variables.ui8_riding_mode = ui8_actual_riding_mode;
									
							// clear walk assist flag
							ui8_walk_assist_flag = 0;
						}
						#else
						// VLCD5 VLCD6 level up or down button
						if(ui8_assist_level != ui8_walk_assist_level)
						{	
							if(ui8_walk_assist_flag)
								// restore previous riding mode
								m_configuration_variables.ui8_riding_mode = ui8_actual_riding_mode;
									
							// clear walk assist flag
							ui8_walk_assist_flag = 0;
						}
						#endif
					}
					else
					{
						// restore previous riding mode
						if(ui8_walk_assist_flag)
							m_configuration_variables.ui8_riding_mode = ui8_actual_riding_mode;
						
						// clear walk assist flag
						ui8_walk_assist_flag = 0;
					}
					#else
					// restore previous riding mode
					if(ui8_walk_assist_flag)
						m_configuration_variables.ui8_riding_mode = ui8_actual_riding_mode;
						
					// clear walk assist flag
					ui8_walk_assist_flag = 0;
					#endif
				}
				#endif
			
				// set assist parameter
				ui8_riding_mode_parameter = ui8_riding_mode_parameter_array[m_configuration_variables.ui8_riding_mode - 1][ui8_assist_level];
				
				// set lights
				#if ENABLE_LIGHTS
				// switch on/switch off lights
				if((ui8_lights_flag)||(ui8_lights_on_5s))
					ui8_lights_state = 1;
				else
					ui8_lights_state = 0;
				#endif
				
				// get wheel diameter from display
				ui8_oem_wheel_diameter = ui8_rx_buffer[3];
				
				// factor to calculate the value of the data to be displayed
				// min valid value wheel diameter 21 inch and Km/h oem display setting!
				ui16_display_data_factor = OEM_WHEEL_FACTOR * ui8_oem_wheel_diameter;
				
				// get wheel max speed from display
				ui16_oem_wheel_speed_max = ui8_rx_buffer[5];
						
				// street limit
				if(m_configuration_variables.ui8_street_mode_enabled)
				{	
					#if STREET_MODE_POWER_LIMIT_ENABLED
					// battery power street limit
					ui8_target_battery_max_power_div25 = STREET_MODE_POWER_LIMIT_DIV25;
					#else
					// battery max power
					ui8_target_battery_max_power_div25 = TARGET_MAX_BATTERY_POWER_DIV25;
					#endif
					
					#if ENABLE_WHEEL_MAX_SPEED_FROM_DISPLAY	
					if(ui16_oem_wheel_speed_max > STREET_MODE_SPEED_LIMIT)
					{
						// wheel speed street limit
						m_configuration_variables.ui8_wheel_speed_max = STREET_MODE_SPEED_LIMIT;
					}
					else
					{
						// wheel max speed from display
						m_configuration_variables.ui8_wheel_speed_max = ui16_oem_wheel_speed_max;
					}
					#else
					// wheel speed street limit
					m_configuration_variables.ui8_wheel_speed_max = STREET_MODE_SPEED_LIMIT;
					#endif
					
					// street cruise mode
					#if STREET_MODE_CRUISE_ENABLED
					// cruise mode speed limit
					if((m_configuration_variables.ui8_riding_mode == CRUISE_MODE)&&(ui8_riding_mode_parameter > STREET_MODE_SPEED_LIMIT))
					{	
						ui8_riding_mode_parameter = STREET_MODE_SPEED_LIMIT;;
					}
					#endif
					
					// disable throttle
					#if !STREET_MODE_THROTTLE_ENABLED
					if(ui8_optional_ADC_function == THROTTLE_CONTROL)
					{
						ui8_optional_ADC_function = NOT_IN_USE;
					}
					#endif
				}
				else
				{	
					// battery power limit
					ui8_target_battery_max_power_div25 = TARGET_MAX_BATTERY_POWER_DIV25;
					
					#if ENABLE_WHEEL_MAX_SPEED_FROM_DISPLAY
					// wheel max speed from display
					m_configuration_variables.ui8_wheel_speed_max = ui16_oem_wheel_speed_max;
					#else
					// wheel max speed
					m_configuration_variables.ui8_wheel_speed_max = WHEEL_MAX_SPEED;
					#endif
					
					// enable throttle
					if(ui8_optional_ADC_function_temp == THROTTLE_CONTROL)
						ui8_optional_ADC_function = THROTTLE_CONTROL;
				}
				
				// calculate max battery current in ADC steps from the received battery current limit
				uint8_t ui8_adc_battery_current_max_temp_1 = ((uint16_t) m_configuration_variables.ui8_battery_current_max * 100) / BATTERY_CURRENT_PER_10_BIT_ADC_STEP_X100;
				// calculate max battery current in ADC steps from the received power limit
				uint32_t ui32_battery_current_max_x100 = ((uint32_t) ui8_target_battery_max_power_div25 * 2500000) / ui16_battery_voltage_filtered_x1000;
				uint8_t ui8_adc_battery_current_max_temp_2 = ui32_battery_current_max_x100 / BATTERY_CURRENT_PER_10_BIT_ADC_STEP_X100;  
				// set max battery current
				ui8_adc_battery_current_max = ui8_min(ui8_adc_battery_current_max_temp_1, ui8_adc_battery_current_max_temp_2);
			}
			
			// signal that we processed the full package
			ui8_received_package_flag = 0;
			
			// enable UART2 receive interrupt as we are now ready to receive a new package
			UART2->CR2 |= (1 << 5);
		}

	}

	// changed for oem display
	static void uart_send_package(void)
	{	
		uint8_t ui8_i;
		uint8_t ui8_tx_check_code;

		// send the data to the LCD
		// start up byte
		ui8_tx_buffer[0] = TX_STX;

		// clear fault code
		ui8_display_fault_code = NO_FAULT;

		// initialize working status
		ui8_working_status &= 0xFE; // bit0 = 0 (battery normal)

		#if ENABLE_VLCD6 || ENABLE_XH18
		switch(ui8_battery_state_of_charge)
		{
			case 0:
				ui8_working_status |= 0x01; // bit0 = 1 (battery undervoltage)
				ui8_tx_buffer[1] = 0x00;
				break;
			case 1:
				ui8_tx_buffer[1] = 0x00; // Battery 0/4 (empty and blinking)
				break;
			case 2:
				ui8_tx_buffer[1] = 0x02; // Battery 1/4
				break;
			case 3:
				ui8_tx_buffer[1] = 0x06; // Battery 2/4
				break;
			case 4:
				ui8_tx_buffer[1] = 0x09; // Battery 3/4
				break;
			case 5:
				ui8_tx_buffer[1] = 0x0C; // Battery 4/4 (full)
				break;
			case 6:
				ui8_tx_buffer[1] = 0x0C; // Battery 4/4 (full)
				ui8_display_fault_code = ERROR_OVERVOLTAGE; // Fault overvoltage
				break;
		}
		#endif

		#if ENABLE_VLCD5
		switch(ui8_battery_state_of_charge)
		{
			case 0:
				ui8_working_status |= 0x01; // bit0 = 1 (battery undervoltage)
				ui8_tx_buffer[1] = 0x00;
				break;
			case 1:
				ui8_tx_buffer[1] = 0x00; // Battery 0/6 (empty and blinking)
				break;
			case 2:
				ui8_tx_buffer[1] = 0x02; // Battery 1/6
				break;
			case 3:
				ui8_tx_buffer[1] = 0x04; // Battery 2/6
				break;
			case 4:
				ui8_tx_buffer[1] = 0x06; // Battery 3/6
				break;
			case 5:
				ui8_tx_buffer[1] = 0x08; // Battery 4/6
				break;
			case 6:
				ui8_tx_buffer[1] = 0x0A; // Battery 5/6
				break;
			case 7:
				ui8_tx_buffer[1] = 0x0C; // Battery 6/6 (full)
				break;
			case 8:
				ui8_tx_buffer[1] = 0x0C; // Battery 6/6 (full)
				ui8_display_fault_code = ERROR_OVERVOLTAGE; // Fault overvoltage
				break;
		}
		#endif

		// reserved
		ui8_tx_buffer[3] = 0x46;
		ui8_tx_buffer[4] = 0x46;

		// fault temperature limit
		#if ENABLE_TEMPERATURE_ERROR_MIN_LIMIT
		// temperature error at min limit value
		if(((uint8_t) ui16_motor_temperature_filtered_x10 / 10) >= ui8_motor_temperature_min_value_to_limit)
		{
			ui8_display_fault_code = ERROR_OVERTEMPERATURE;
		}
		#else
		// temperature error at max limit value
		if(((uint8_t) ui16_motor_temperature_filtered_x10 / 10) >= ui8_motor_temperature_max_value_to_limit)
		{
			ui8_display_fault_code = ERROR_OVERTEMPERATURE;
		}
		#endif
	
		// blocked motor error has priority
		if(ui8_system_state == ERROR_MOTOR_BLOCKED)
		{	
			ui8_display_fault_code = ERROR_MOTOR_BLOCKED;
		}	
		else
		{	
			if((ui8_system_state != NO_ERROR)&&(ui8_display_fault_code == NO_FAULT))
				ui8_display_fault_code = ui8_system_state;
		}
		
		// transmit to display function code or fault code
		if(ui8_display_fault_code != NO_FAULT)
		{
			#if ENABLE_XH18
			if(ui8_display_fault_code == ERROR_WRITE_EEPROM)
			{	
				// instead of E09, display blinking E08
				if(ui8_display_blinking_code)
					ui8_tx_buffer[5] = ui8_display_fault_code - 1;
			}
			else	
			{
				// fault code
				ui8_tx_buffer[5] = ui8_display_fault_code;
			}
			#else
			// fault code
			ui8_tx_buffer[5] = ui8_display_fault_code;
			#endif
		}
		else if(ui8_display_function_code != NO_FUNCTION)
		{
			// function code
			if((!ui8_menu_flag) && (ui8_menu_index)&&((m_configuration_variables.ui8_set_parameter_enabled)||(ui8_assist_level == OFF)))
			{
				// display blinking parameter function code
				if(ui8_display_blinking_code)
					ui8_tx_buffer[5] = ui8_display_function_code;
				else
					// clear code
					ui8_tx_buffer[5] = CLEAR_DISPLAY;
			}
			else
			{
				// display data function code
				ui8_tx_buffer[5] = ui8_display_function_code;
			}
		}
		else
		{
			// clear code
			ui8_tx_buffer[5] = CLEAR_DISPLAY;
		}
				
		// transmit to display data value or wheel speed
		if(ui8_display_data_enabled)
		{
			// data values
			ui16_data_value_array[0] = (uint16_t) ui16_display_data_factor / (ui16_motor_temperature_filtered_x10 / 10);
			ui16_data_value_array[1] = (uint16_t) ui16_display_data_factor / (ui16_battery_SOC_percentage_x10 / 10);
			ui16_data_value_array[2] = (uint16_t) ui16_display_data_factor / (ui16_battery_voltage_calibrated_x10 / 10);
			ui16_data_value_array[3] = (uint16_t) ui16_display_data_factor / (ui8_battery_current_filtered_x10 / 10);
			ui16_data_value_array[4] = (uint16_t) ui16_display_data_factor / (ui16_battery_power_filtered_x10 / 100);
			ui16_data_value_array[5] = (uint16_t) ui16_display_data_factor / UI8_ADC_TORQUE_SENSOR;
			ui16_data_value_array[6] = (uint16_t) ui16_display_data_factor / UI16_ADC_10_BIT_TORQUE_SENSOR;
			ui16_data_value_array[7] = (uint16_t) ui16_display_data_factor / ui8_pedal_cadence_RPM;

			// display data
			if(!ui8_battery_SOC_init_flag)
				ui16_display_data = ui16_data_value_array[1];
			else
				ui16_display_data = ui16_data_value_array[ui8_data_index_array[ui8_data_index]];
	
			ui8_tx_buffer[6] = (uint8_t) (ui16_display_data & 0xFF);
			ui8_tx_buffer[7] = (uint8_t) (ui16_display_data >> 8);
		}
		else
		{	
			// wheel speed
			if(ui16_oem_wheel_speed == 0)
			{
				ui8_tx_buffer[6] = 0x07;
				ui8_tx_buffer[7] = 0x07;

				#if ENABLE_DISPLAY_WORKING_FLAG
				// bit7 = 0 (wheel not turning)
				ui8_working_status &= 0x7F;
				#endif
			}
			else
			{
				ui8_tx_buffer[6] = (uint8_t) (ui16_oem_wheel_speed & 0xFF);
				ui8_tx_buffer[7] = (uint8_t) (ui16_oem_wheel_speed >> 8);
					
				#if ENABLE_DISPLAY_WORKING_FLAG
				// bit7 = 1 (wheel turning)
				ui8_working_status |= 0x80;
				#endif
			}
		}	
 
		// set working flag
		#if ENABLE_DISPLAY_ALWAYS_ON
		// set working flag used to hold display always on
		ui8_working_status |= 0x04;
		#else
		// motor working or wheel turning?
		if(ui8_working_status & 0xC0)
		{
			// set working flag used by display
			ui8_working_status |= 0x04;
		}
		else
		{
			// clear working flag used by display
			ui8_working_status &= 0xFB;
		}
		#endif

		// working status
		ui8_tx_buffer[2] = (ui8_working_status & 0x1F);
		
		// clear motor working, wheel turning and working flags
		ui8_working_status &= 0x3B;	

		#if DEBUG_MODE
		uint16_t ui16_display_debug_value = 0;
		uint16_t ui16_debug_value_array[10];
		uint8_t ui8_debug_index = DEBUG_DATA;
		
		ui16_debug_value_array[0] = (uint16_t) m_configuration_variables.ui8_riding_mode;
		ui16_debug_value_array[1] = (uint16_t) ui8_riding_mode_parameter;
		ui16_debug_value_array[2] = (uint16_t) ui8_assist_level;
		ui16_debug_value_array[3] = (uint16_t) ui8_walk_assist_counter;
		ui16_debug_value_array[4] = (uint16_t) ui8_riding_mode_cruise;
		ui16_debug_value_array[5] = (uint16_t) ui8_riding_mode_parameter_cruise;
		ui16_debug_value_array[6] = (uint16_t) ui8_cruise_counter;
		ui16_debug_value_array[7] = (uint16_t) ui8_data_index;
		ui16_debug_value_array[8] = (uint16_t) ui16_oem_wheel_speed;
		ui16_debug_value_array[9] = (uint16_t) m_configuration_variables.ui8_odometer_compensation_km_x10;
		
		ui16_display_debug_value =  (uint16_t) ui16_display_data_factor / (ui16_debug_value_array[ui8_debug_index] * 10);
		if(ui16_display_debug_value < 40)
			ui16_display_debug_value =  (uint16_t) ui16_display_data_factor / ui16_debug_value_array[ui8_debug_index];
		if(ui16_display_debug_value < 40)
			ui16_display_debug_value =  (uint16_t) ui16_display_data_factor / (ui16_debug_value_array[ui8_debug_index] / 10);
		if(ui16_display_debug_value < 40)
			ui16_display_debug_value =  (uint16_t) ui16_display_data_factor / (ui16_debug_value_array[ui8_debug_index] / 100);
		
		ui8_tx_buffer[6] = (uint8_t) (ui16_display_debug_value & 0xFF);
		ui8_tx_buffer[7] = (uint8_t) (ui16_display_debug_value >> 8);
		#endif
		
		// prepare check code of the package
		ui8_tx_check_code = 0x00;
		for(ui8_i = 0; ui8_i < TX_CHECK_CODE; ui8_i++)
		{
			ui8_tx_check_code += ui8_tx_buffer[ui8_i];
		}
		ui8_tx_buffer[TX_CHECK_CODE] = ui8_tx_check_code;

		// send the full package to UART
		for(ui8_i = 0; ui8_i < UART_TX_BUFFER_LEN; ui8_i++)
		{
			putchar(ui8_tx_buffer[ui8_i]);
		}
	}

	
	// function for oem display
	 static void calc_oem_wheel_speed(void)
	{ 
		float f_oem_wheel_speed;
		float f_oem_wheel_perimeter;
			
		// display ready:
		if(ui8_display_ready_flag)
		{
			// calc oem wheel speed (wheel turning time)
			if(ui16_wheel_speed_sensor_ticks)
			{
				f_oem_wheel_speed = (((float) ui16_wheel_speed_sensor_ticks) * 10.0) / ((float) OEM_WHEEL_SPEED_DIVISOR);
				
				// speed conversion for different perimeter			
				f_oem_wheel_perimeter = ((float) ui8_oem_wheel_diameter * 25.4 * 3.14);
				f_oem_wheel_speed *= f_oem_wheel_perimeter;
				f_oem_wheel_speed /= (float) m_configuration_variables.ui16_wheel_perimeter;
				
				// oem wheel speed (wheel turning time)
				ui16_oem_wheel_speed = (uint16_t) f_oem_wheel_speed;
			}
			else
			{
				ui16_oem_wheel_speed = 0;
			}
		}
		// display not ready:
		else
		{
			// clear wheel speed
			ui16_oem_wheel_speed = 0;

			// if startup done, set display ready
			if(ui8_startup_counter++ >= DELAY_DISPLAY_READY)
				ui8_display_ready_flag = 1;
		}

		
		#if ENABLE_ODOMETER_COMPENSATION
		uint16_t ui16_wheel_speed;
		uint16_t ui16_data_speed;
		uint16_t ui16_speed_difference;
		
		// calc wheel speed  mm/0.1 sec
		if(ui16_oem_wheel_speed)
			ui16_wheel_speed = (uint16_t)((ui16_display_data_factor / ui16_oem_wheel_speed) * ((uint16_t) 1000 / 36));
		else
			ui16_wheel_speed = 0;
		
		// calc data speed  mm/0.1 sec
		if(ui16_display_data)	
			ui16_data_speed = (uint16_t)((ui16_display_data_factor / ui16_display_data) * ((uint16_t) 1000 / 36));
		else
			ui16_data_speed = 0;
		
		// calc odometer difference
		if(ui8_display_data_enabled)
		{	
			if(ui16_data_speed > ui16_wheel_speed)
			{	
				// calc + speed difference mm/0.1 sec
				ui16_speed_difference = ui16_data_speed - ui16_wheel_speed;
				// add difference to odometer
				ui32_odometer_compensation_mm += (uint32_t) ui16_speed_difference;
			}
			else
			{	
				// calc - speed difference mm/0.1 sec
				ui16_speed_difference = ui16_wheel_speed - ui16_data_speed;

				// subtracts difference from odometer
				ui32_odometer_compensation_mm -= (uint32_t) ui16_speed_difference;
			}
		}
		else
		{	
			// odometer compensation
			if((ui16_wheel_speed)&&(ui32_odometer_compensation_mm > ZERO_ODOMETER_COMPENSATION))
			{
				ui32_odometer_compensation_mm -= (uint32_t) ui16_wheel_speed;
				ui16_oem_wheel_speed = 0;
			}
		}
		
		#if SAVE_ODOMETER_COMPENSATION
		// odometer compensation km x10 limit
		if((ui32_odometer_compensation_mm > ZERO_ODOMETER_COMPENSATION)&&(ui32_odometer_compensation_mm < (ZERO_ODOMETER_COMPENSATION + 25500000)))
		{
			// odometer compensation km x10 to be saved in eeprom at shutdown
			m_configuration_variables.ui8_odometer_compensation_km_x10 = (uint8_t)(((uint32_t) ui32_odometer_compensation_mm - ZERO_ODOMETER_COMPENSATION) / 100000);
		}
		#endif
		#endif
	} 

	
	// function for oem display
	static void check_battery_soc(void)
	{
		uint16_t ui16_fluctuate_battery_voltage_x10;
		uint16_t ui16_battery_voltage_filtered_x10;											
		uint16_t ui16_battery_voltage_soc_x10;
		uint8_t ui8_battery_cells_number_x10;

		uint32_t ui32_battery_SOC_temp_x10;
		uint16_t ui16_battery_power_x10;

		// battery voltage x10
		ui16_battery_voltage_filtered_x10 = ui16_battery_voltage_filtered_x1000 / 100;
		
		// calculate flutuate voltage, that depends on the current and battery pack resistance
		ui16_fluctuate_battery_voltage_x10 = (uint16_t) ((((uint32_t) BATTERY_PACK_RESISTANCE) * ((uint32_t) ui8_battery_current_filtered_x10)) / ((uint32_t) 1000));

		// now add fluctuate voltage value
		ui16_battery_voltage_soc_x10 = ui16_battery_voltage_filtered_x10 + ui16_fluctuate_battery_voltage_x10;

		// filter battery voltage soc x10
		ui16_battery_voltage_soc_filtered_x10 = filter(ui16_battery_voltage_soc_x10, ui16_battery_voltage_soc_filtered_x10, 15);

		// to keep same scale as voltage of x10
		ui8_battery_cells_number_x10 = BATTERY_CELLS_NUMBER * 10;

		// update battery level value only at minimun every 100ms and this helps to visual filter the fast changing values
		if(ui8_timer_counter++ >= 1)
		{
			ui8_timer_counter = 0;

			#if ENABLE_VLCD6 || ENABLE_XH18
			if(ui16_battery_voltage_soc_filtered_x10 > ((uint16_t) ((float) ui8_battery_cells_number_x10 * LI_ION_CELL_VOLTS_5))) {ui8_battery_state_of_charge = 6;}			// 4 bars --> full + overvoltage
			else if(ui16_battery_voltage_soc_filtered_x10 > ((uint16_t) ((float) ui8_battery_cells_number_x10 * LI_ION_CELL_VOLTS_4))) {ui8_battery_state_of_charge = 5;}	// 4 bars --> full
			else if(ui16_battery_voltage_soc_filtered_x10 > ((uint16_t) ((float) ui8_battery_cells_number_x10 * LI_ION_CELL_VOLTS_3))) {ui8_battery_state_of_charge = 4;}	// 3 bars
			else if(ui16_battery_voltage_soc_filtered_x10 > ((uint16_t) ((float) ui8_battery_cells_number_x10 * LI_ION_CELL_VOLTS_2))) {ui8_battery_state_of_charge = 3;}	// 2 bars
			else if(ui16_battery_voltage_soc_filtered_x10 > ((uint16_t) ((float) ui8_battery_cells_number_x10 * LI_ION_CELL_VOLTS_1))) {ui8_battery_state_of_charge = 2;}	// 1 bar
			else if(ui16_battery_voltage_soc_filtered_x10 > ((uint16_t) ((float) ui8_battery_cells_number_x10 * LI_ION_CELL_VOLTS_0))) {ui8_battery_state_of_charge = 1;}	// blink --> empty
			else{ui8_battery_state_of_charge = 0;} // undervoltage
			#else // ENABLE_VLCD5D5
			if(ui16_battery_voltage_soc_filtered_x10 > ((uint16_t) ((float) ui8_battery_cells_number_x10 * LI_ION_CELL_VOLTS_7))) {ui8_battery_state_of_charge = 8;}   		// 6 bars --> full + overvoltage
			else if(ui16_battery_voltage_soc_filtered_x10 > ((uint16_t) ((float) ui8_battery_cells_number_x10 * LI_ION_CELL_VOLTS_6))) {ui8_battery_state_of_charge = 7;}	// 6 bars --> full
			else if(ui16_battery_voltage_soc_filtered_x10 > ((uint16_t) ((float) ui8_battery_cells_number_x10 * LI_ION_CELL_VOLTS_5))) {ui8_battery_state_of_charge = 6;}	// 5 bars
			else if(ui16_battery_voltage_soc_filtered_x10 > ((uint16_t) ((float) ui8_battery_cells_number_x10 * LI_ION_CELL_VOLTS_4))) {ui8_battery_state_of_charge = 5;}	// 4 bars
			else if(ui16_battery_voltage_soc_filtered_x10 > ((uint16_t) ((float) ui8_battery_cells_number_x10 * LI_ION_CELL_VOLTS_3))) {ui8_battery_state_of_charge = 4;}	// 3 bars
			else if(ui16_battery_voltage_soc_filtered_x10 > ((uint16_t) ((float) ui8_battery_cells_number_x10 * LI_ION_CELL_VOLTS_2))) {ui8_battery_state_of_charge = 3;}	// 2 bars
			else if(ui16_battery_voltage_soc_filtered_x10 > ((uint16_t) ((float) ui8_battery_cells_number_x10 * LI_ION_CELL_VOLTS_1))) {ui8_battery_state_of_charge = 2;}	// 1 bar
			else if(ui16_battery_voltage_soc_filtered_x10 > ((uint16_t) ((float) ui8_battery_cells_number_x10 * LI_ION_CELL_VOLTS_0))) {ui8_battery_state_of_charge = 1;}	// blink --> empty
			else{ui8_battery_state_of_charge = 0;} // undervoltage
			#endif
		}

		// battery voltage calibrated x10 for display data
		ui16_battery_voltage_calibrated_x10 = ui16_battery_voltage_filtered_x10 * ACTUAL_BATTERY_VOLTAGE_PERCENT / 100;
		
		// battery power x 10
		ui16_battery_power_x10 = (uint16_t)((uint32_t) ui16_battery_voltage_filtered_x10 * ui8_battery_current_filtered_x10) / 10;
		// battery power filtered x 10 for display data
		ui16_battery_power_filtered_x10 = filter(ui16_battery_power_x10, ui16_battery_power_filtered_x10, 40);
		
		// consumed watt-hours
		 ui32_wh_sum_x10 += ui16_battery_power_x10;
		// calculate watt-hours X10 since power on
		ui32_wh_since_power_on_x10 = ui32_wh_sum_x10 / 36000;
		// calculate watt-hours X10 since last full charge
		ui32_wh_x10 = ui32_wh_x10_offset + ui32_wh_since_power_on_x10;

		// calculate percentage remaining battery capacity (from 100 to 0) x10
		ui32_battery_SOC_temp_x10 = ui32_wh_x10 * 100 / ui32_actual_battery_capacity;

		// limit percentage to 100 x10
		if (ui32_battery_SOC_temp_x10 > 1000)
			ui32_battery_SOC_temp_x10 = 1000;

		// calculate and set remaining percentage x10
		ui16_battery_SOC_percentage_x10 = 1000 - ui32_battery_SOC_temp_x10;
		// convert remaining percentage x10 to 8 bit
		m_configuration_variables.ui8_battery_SOC_percentage_8b = (uint8_t)(ui16_battery_SOC_percentage_x10 >> 2);

		// automatic set battery percentage x10 (full charge)
		if((!ui8_battery_SOC_init_flag)&&(ui8_display_ready_flag))
		{
			if(ui16_battery_voltage_filtered_x10 > BATTERY_VOLTAGE_RESET_SOC_PERCENT_X10)
			{
				ui16_battery_SOC_percentage_x10 = 1000;
				ui32_wh_x10_offset = 0;
				ui8_battery_SOC_init_flag = 1;
			}
		}
	}

