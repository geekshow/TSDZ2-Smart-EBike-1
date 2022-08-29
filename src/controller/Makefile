#Makefile for STM8 Examples with SDCC compiler
#Author:	Saeid Yazdani
#Website:	WWW.EMBEDONIX.COM
#Copyright 2016
#LICENSE:	GNU-LGPL

.PHONY: all clean mkdir

#Compiler
CC = sdcc --verbose
OBJCOPY = stm8-objcopy
SIZE = stm8-size

#Platform
PLATFORM = stm8
DEVICE = STM8S105

#Product name
PNAME = main

ODIR = ../../bin

#Directory for helpers
IDIR = ../common/STM8S_StdPeriph_Lib/inc
SDIR = ../common/STM8S_StdPeriph_Lib/src
SDIR1 = ../common
IDIR1 = ../common

# In case you ever want a different name for the main source file
MAINSRC = $(PNAME).c

ELF_SECTIONS_TO_REMOVE = -R DATA -R INITIALIZED -R SSEG -R .debug_line -R .debug_loc -R .debug_abbrev -R .debug_info -R .debug_pubnames -R .debug_frame

# These are the sources that must be compiled to .rel files:
	EXTRASRCS = \
	$(SDIR)/stm8s_iwdg.c \
	$(SDIR)/stm8s_itc.c \
	$(SDIR)/stm8s_clk.c \
	$(SDIR)/stm8s_gpio.c \
	$(SDIR)/stm8s_uart2.c \
	$(SDIR)/stm8s_tim1.c \
	$(SDIR)/stm8s_tim2.c \
	$(SDIR)/stm8s_tim3.c \
	$(SDIR)/stm8s_exti.c \
	$(SDIR)/stm8s_adc1.c \
	$(SDIR)/stm8s_flash.c \
	$(SDIR1)/common.c \
	watchdog.c \
	torque_sensor.c \
	uart.c \
	pwm.c \
	motor.c \
	wheel_speed_sensor.c \
	brake.c \
	pas.c \
	adc.c \
	timers.c \
	ebike_app.c \
	eeprom.c \
	lights.c \

HEADERS = watchdog.h torque_sensor.h interrupts.h main.h uart.h pwm.h motor.h wheel_speed_sensor.h brake.h pas.h adc.h timers.h \
ebike_app.h pins.h eeprom.h lights.h config.h

# The list of .rel files can be derived from the list of their source files
RELS = $(addprefix $(ODIR)/,$(notdir $(EXTRASRCS:.c=.rel)))

INCLUDES = -I$(IDIR) -I$(IDIR1)
CFLAGS = -m$(PLATFORM) -D$(DEVICE) -Ddouble=float --std-c99 --nolospre 
DEBUG_FLAGS = --out-fmt-ihx --debug
LIBS = 

# This just provides the conventional target name "all"; it is optional
# Note: I assume you set PNAME via some means not exhibited in your original file
all: mkdir $(PNAME)


# How to build the overall program
$(PNAME): $(MAINSRC) $(RELS)
	$(CC) $(INCLUDES) $(CFLAGS) $(DEBUG_FLAGS) $(LIBS) $(MAINSRC) $(RELS) -o$(ODIR)/
# $(SIZE) $(PNAME).elf
# $(OBJCOPY) -O binary $(ELF_SECTIONS_TO_REMOVE) $(PNAME).elf $(PNAME).bin
# $(OBJCOPY) -O ihex $(ELF_SECTIONS_TO_REMOVE) $(PNAME).elf $(PNAME).hex

#Rules for firmware sources
$(ODIR)/%.rel: %.c $(HEADERS)
	$(CC) -c $(INCLUDES) $(CFLAGS) $(DEBUG_FLAGS) $(LIBS) -o$(ODIR)/ $<

$(ODIR)/%.rel: $(SDIR1)/%.c	
	$(CC) -c $(INCLUDES) $(CFLAGS) $(DEBUG_FLAGS) $(LIBS) -o$(ODIR)/ $<
#Rules for device driver sources
$(ODIR)/%.rel: $(SDIR)/%.c	
	$(CC) -c $(INCLUDES) $(CFLAGS) $(DEBUG_FLAGS) $(LIBS) -o$(ODIR)/ $<

# Suffixes appearing in suffix rules we care about.
# Necessary because .rel is not one of the standard suffixes.
.SUFFIXES: .c .rel

# hex:
# 	@cd $(ODIR)
# 	$(OBJCOPY) -O ihex $(ELF_SECTIONS_TO_REMOVE) $(PNAME).elf $(PNAME).ihx

# flash:
# 	stm8flash -cstlinkv2 -pstm8s105?6 -w$(ODIR)/$(PNAME).ihx

clean:
	@echo "Cleaning files..."
	@rm -rf $(ODIR)
	@echo "Done."

mkdir:
	mkdir -p $(ODIR)