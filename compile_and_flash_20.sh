#!/bin/bash
set -e

version="20.1C.2-2"
settings_date=$1

release_folder=$(pwd)/releases
backup_folder=$(pwd)/releases/backup

cd src/controller

# Clean existing
rm -rf main.ihx || true
make clean || true

# Build firmware
echo Build started...
make all

# Save new firmware
echo Copying firmware to release folder.
echo $release_folder/TSDZ2-$version-$settings_date.hex
mkdir -p $release_folder
yes | cp -rf ../../bin/main.ihx $release_folder/TSDZ2-$version-$settings_date.hex

while true; do
read -p "Do you want to flash the motor ? [Y/n]" yn
case $yn in
	y ) break;;
	n ) exit;;
	* ) break;;
esac
done

# Backup firmware
echo Backup current firmware
mkdir -p $backup_folder
make backup
yes | cp -rf ../../bin/TSDZ_orig_opt.bin $backup_folder/TSDZ_orig_opt-$version-$settings_date.bin
yes | cp -rf ../../bin/TSDZ_orig.bin $backup_folder/TSDZ_orig-$version-$settings_date.bin
yes | cp -rf ../../bin/TSDZ_orig_eeprom.bin $backup_folder/TSDZ_orig_eeprom-$version-$settings_date.bin

# Flash new firmware
make clear_eeprom
make flash

echo All done !
