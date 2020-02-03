 static void calc_oem_wheel_speed(void)
{ 
	float f_oem_wheel_speed;
	
	// calc oem wheel speed (wheel turning time)
	if(ui16_wheel_speed_sensor_ticks < WHEEL_SPEED_SENSOR_MIN_PWM_CYCLE_TICKS)
	{
		// display ready:
		if(ui8_display_ready_flag)
		{
			f_oem_wheel_speed = (((float) ui16_wheel_speed_sensor_ticks) * 10.0) / ((float) OEM_WHEEL_SPEED_DIVISOR);
			ui16_oem_wheel_speed = (uint16_t) f_oem_wheel_speed;
		}
		// display not ready:
		else
		{
			// clear wheel speed
			ui16_oem_wheel_speed = 0;

			// if startup done, set display ready
			if(ui8_startup_counter++ >= 40)
			{
				ui8_display_ready_flag = 1;
			}
		}
	}
	else
	{
		ui16_oem_wheel_speed = 0;
	}
} 