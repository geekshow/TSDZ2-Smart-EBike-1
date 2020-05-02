/*
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 3 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software Foundation,
Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

/**
 *
 * @author stancecoke
 */

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import javax.swing.JList;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;
import java.util.Arrays;

public class TSDZ2_Configurator extends javax.swing.JFrame {

    /**
     * Creates new form TSDZ2_Configurator
     */
   
    private File experimentalSettingsDir;
    private File lastSettingsFile = null;
    
    DefaultListModel provenSettingsFilesModel = new DefaultListModel();
    DefaultListModel experimentalSettingsFilesModel = new DefaultListModel();
    JList experimentalSettingsList = new JList(experimentalSettingsFilesModel);
                  
    	public class FileContainer {

		public FileContainer(File file) {
			this.file = file;
		}
		public File file;

		@Override
		public String toString() {
			return file.getName();
		}
	}

    String[] displayDataArray = {"motor temperature", "battery SOC rem. %", "battery voltage", "battery current", "motor power", "adc torque sensor 8b", "adc torque sensor 10b", "pedal cadence rpm", "human power", "cadence sensor adv.", "pedal weight", "pedal torque adc conv.", "pedal torque adc range"};   
    String[] lightModeArray = {"<br>lights ON", "<br>lights FLASHING", "lights ON and BRAKE-FLASHING brak.", "lights FLASHING and ON when braking", "lights FLASHING BRAKE-FLASHING brak.", "lights ON and ON always braking", "lights ON and BRAKE-FLASHING alw.br.", "lights FLASHING and ON always braking", "lights FLASHING BRAKE-FLASHING alw.br."};
  
 
    public void loadSettings(File f) throws IOException {
    
     		BufferedReader in = new BufferedReader(new FileReader(f));
                RB_MOTOR_36V.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_MOTOR_48V.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_EXP_HIGH_CAD_MODE.setSelected(Boolean.parseBoolean(in.readLine()));
                TF_MOTOR_ACC.setText(in.readLine());
                CB_ASS_WITHOUT_PED.setSelected(Boolean.parseBoolean(in.readLine()));
                TF_ASS_WITHOUT_PED_THRES.setText(in.readLine());
                TF_TORQ_PER_ADC_STEP_STD.setText(in.readLine());
                TF_TORQ_ADC_RANGE.setText(in.readLine());
                TF_CAD_SENS_HIGH_PER.setText(in.readLine());
                TF_MOTOR_BLOCK_TIME.setText(in.readLine());
                TF_MOTOR_BLOCK_CURR.setText(in.readLine());
                TF_MOTOR_BLOCK_ERPS.setText(in.readLine());
                TF_RAMP_DOWN_ADD.setText(in.readLine());
                TF_BAT_CUR_MAX.setText(in.readLine());
                TF_BATT_POW_MAX.setText(in.readLine());
                TF_BATT_CAPACITY.setText(in.readLine());
                TF_BATT_NUM_CELLS.setText(in.readLine());
                TF_BATT_RESIST.setText(in.readLine());
                TF_BATT_VOLT_CUT_OFF.setText(in.readLine());
                TF_BATT_VOLT_CAL.setText(in.readLine());
                TF_BATT_CAPACITY_CAL.setText(in.readLine());
                TF_BAT_CELL_OVER.setText(in.readLine());
                TF_BAT_CELL_SOC.setText(in.readLine());
                TF_BAT_CELL_FULL.setText(in.readLine());
                TF_BAT_CELL_3_4.setText(in.readLine());
                TF_BAT_CELL_2_4.setText(in.readLine());
                TF_BAT_CELL_1_4.setText(in.readLine());
                TF_BAT_CELL_5_6.setText(in.readLine());
                TF_BAT_CELL_4_6.setText(in.readLine());
                TF_BAT_CELL_3_6.setText(in.readLine());
                TF_BAT_CELL_2_6.setText(in.readLine());
                TF_BAT_CELL_1_6.setText(in.readLine());
                TF_BAT_CELL_EMPTY.setText(in.readLine());
                TF_WHEEL_CIRCUMF.setText(in.readLine());
                TF_MAX_SPEED.setText(in.readLine());
                CB_LIGHTS.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_WALK_ASSIST.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_BRAKE_SENSOR.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_ADC_OPTION_DIS.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_THROTTLE.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_TEMP_LIMIT.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_STREET_MODE_ON_START.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_SET_PARAM_ON_START.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_ODO_COMPENSATION.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_CAD_SENSOR_ADV.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_TOR_SENSOR_ADV.setSelected(Boolean.parseBoolean(in.readLine()));
                TF_LIGHT_MODE_ON_START.setText(in.readLine());
                RB_POWER_ON_START.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_TORQUE_ON_START.setSelected(Boolean.parseBoolean(in.readLine()));
		RB_CADENCE_ON_START.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_EMTB_ON_START.setSelected(Boolean.parseBoolean(in.readLine()));
                TF_LIGHT_MODE_1.setText(in.readLine());
                TF_LIGHT_MODE_2.setText(in.readLine());
                TF_LIGHT_MODE_3.setText(in.readLine());
                CB_STREET_POWER_LIM.setSelected(Boolean.parseBoolean(in.readLine()));
                TF_STREET_POWER_LIM.setText(in.readLine());
                TF_STREET_SPEED_LIM.setText(in.readLine());
                CB_STREET_THROTTLE.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_STREET_CRUISE.setSelected(Boolean.parseBoolean(in.readLine()));
                TF_ADC_THROTTLE_MIN.setText(in.readLine());
                TF_ADC_THROTTLE_MAX.setText(in.readLine());
                TF_TEMP_MIN_LIM.setText(in.readLine());
                TF_TEMP_MAX_LIM.setText(in.readLine());
                CB_TEMP_ERR_MIN_LIM.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_VLCD6.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_VLCD5.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_XH18.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_DISPLAY_WORK_ON.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_DISPLAY_ALWAY_ON.setSelected(Boolean.parseBoolean(in.readLine()));
		CB_MAX_SPEED_DISPLAY.setSelected(Boolean.parseBoolean(in.readLine()));
                TF_DELAY_MENU.setText(in.readLine());
                CB_RET_DISPLAY_MODE.setSelected(Boolean.parseBoolean(in.readLine()));
                TF_RET_DISPLAY_MODE.setText(in.readLine());
                CB_AUTO_DISPLAY_DATA.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_DISPLAY_DOUBLE_DATA.setSelected(Boolean.parseBoolean(in.readLine()));
                TF_DELAY_DATA_1.setText(in.readLine());
                TF_DELAY_DATA_2.setText(in.readLine());
                TF_DELAY_DATA_3.setText(in.readLine());
                TF_DELAY_DATA_4.setText(in.readLine());
                TF_DELAY_DATA_5.setText(in.readLine());
                TF_DELAY_DATA_6.setText(in.readLine());
                TF_DATA_1.setText(in.readLine());
                TF_DATA_2.setText(in.readLine());
                TF_DATA_3.setText(in.readLine());
                TF_DATA_4.setText(in.readLine());
                TF_DATA_5.setText(in.readLine());
                TF_DATA_6.setText(in.readLine());
                TF_POWER_ASS_1.setText(in.readLine());
                TF_POWER_ASS_2.setText(in.readLine());
                TF_POWER_ASS_3.setText(in.readLine());
                TF_POWER_ASS_4.setText(in.readLine());           
                TF_TORQUE_ASS_1.setText(in.readLine());
                TF_TORQUE_ASS_2.setText(in.readLine());
                TF_TORQUE_ASS_3.setText(in.readLine());
                TF_TORQUE_ASS_4.setText(in.readLine());
                TF_CADENCE_ASS_1.setText(in.readLine());
                TF_CADENCE_ASS_2.setText(in.readLine());
                TF_CADENCE_ASS_3.setText(in.readLine());
                TF_CADENCE_ASS_4.setText(in.readLine());
                TF_EMTB_ASS_1.setText(in.readLine());
                TF_EMTB_ASS_2.setText(in.readLine());
                TF_EMTB_ASS_3.setText(in.readLine());
                TF_EMTB_ASS_4.setText(in.readLine());
                TF_WALK_ASS_1.setText(in.readLine());
                TF_WALK_ASS_2.setText(in.readLine());
                TF_WALK_ASS_3.setText(in.readLine());
                TF_WALK_ASS_4.setText(in.readLine());
                TF_WALK_ASS_SPEED.setText(in.readLine());
                CB_WALK_TIME_ENA.setSelected(Boolean.parseBoolean(in.readLine()));
                TF_WALK_ASS_TIME.setText(in.readLine());
                TF_CRUISE_ASS_1.setText(in.readLine());
                TF_CRUISE_ASS_2.setText(in.readLine());
                TF_CRUISE_ASS_3.setText(in.readLine());
                TF_CRUISE_ASS_4.setText(in.readLine());
                CB_CRUISE_WHITOUT_PED.setSelected(Boolean.parseBoolean(in.readLine()));
                TF_CRUISE_SPEED_ENA.setText(in.readLine());
                TF_TORQ_ADC_OFFSET_ADJ.setText(in.readLine());
                TF_NUM_DATA_AUTO_DISPLAY.setText(in.readLine());
                RB_UNIT_KILOMETERS.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_UNIT_MILES.setSelected(Boolean.parseBoolean(in.readLine()));
                
		in.close();
                
                jLabelData1.setText("Data 1 - " + displayDataArray[Integer.parseInt(TF_DATA_1.getText())]);
                jLabelData2.setText("Data 2 - " + displayDataArray[Integer.parseInt(TF_DATA_2.getText())]);
                jLabelData3.setText("Data 3 - " + displayDataArray[Integer.parseInt(TF_DATA_3.getText())]);
                jLabelData4.setText("Data 4 - " + displayDataArray[Integer.parseInt(TF_DATA_4.getText())]);
                jLabelData5.setText("Data 5 - " + displayDataArray[Integer.parseInt(TF_DATA_5.getText())]);
                jLabelData6.setText("Data 6 - " + displayDataArray[Integer.parseInt(TF_DATA_6.getText())]);
                
                jLabelLights0.setText("<html>Lights mode on startup " + lightModeArray[Integer.parseInt(TF_LIGHT_MODE_ON_START.getText())] + "</html>");
                jLabelLights1.setText("<html>Mode 1 - " + lightModeArray[Integer.parseInt(TF_LIGHT_MODE_1.getText())] + "</html>");
                jLabelLights2.setText("<html>Mode 2 - " + lightModeArray[Integer.parseInt(TF_LIGHT_MODE_2.getText())] + "</html>");
                jLabelLights3.setText("<html>Mode 3 - " + lightModeArray[Integer.parseInt(TF_LIGHT_MODE_3.getText())] + "</html>");
    }   

    public void AddListItem(File newFile) {
        
        experimentalSettingsFilesModel.add(0, new FileContainer(newFile));
    
       // ListModel<String> Liste = expSet.getModel();
        expSet.repaint();
        JOptionPane.showMessageDialog(null,experimentalSettingsFilesModel.toString(),"Titel", JOptionPane.PLAIN_MESSAGE);
    }

    public TSDZ2_Configurator() {
        initComponents();
        
        this.setLocationRelativeTo(null);     
               
        // update lists
        
                        experimentalSettingsDir = new File(Paths.get(".").toAbsolutePath().normalize().toString());
		while (!Arrays.asList(experimentalSettingsDir.list()).contains("experimental settings")) {
			experimentalSettingsDir = experimentalSettingsDir.getParentFile();
		}
		File provenSettingsDir = new File(experimentalSettingsDir.getAbsolutePath() + File.separator + "proven settings");
		experimentalSettingsDir = new File(experimentalSettingsDir.getAbsolutePath() + File.separator + "experimental settings");



		for (File file : provenSettingsDir.listFiles()) {
			provenSettingsFilesModel.addElement(new TSDZ2_Configurator.FileContainer(file));

			if (lastSettingsFile == null) {
				lastSettingsFile = file;
			} else {
				if(file.lastModified()>lastSettingsFile.lastModified()){
					lastSettingsFile = file;
				}
			}
		}
 		

                for (File file : experimentalSettingsDir.listFiles()) {
            experimentalSettingsFilesModel.addElement(new TSDZ2_Configurator.FileContainer(file));
	}
        	experimentalSettingsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		experimentalSettingsList.setLayoutOrientation(JList.VERTICAL);
		experimentalSettingsList.setVisibleRowCount(-1); 
                
                expSet.setModel(experimentalSettingsFilesModel);
        
		JList provenSettingsList = new JList(provenSettingsFilesModel);
		provenSettingsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		provenSettingsList.setLayoutOrientation(JList.VERTICAL);
		provenSettingsList.setVisibleRowCount(-1);
        
        provSet.setModel(provenSettingsFilesModel);
        jScrollPane2.setViewportView(provSet);
        

        expSet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                            	try {
                                int selectedIndex = expSet.getSelectedIndex();
                                experimentalSettingsList.setSelectedIndex(selectedIndex);
					loadSettings(((FileContainer) experimentalSettingsList.getSelectedValue()).file);
					experimentalSettingsList.clearSelection();
				} catch (IOException ex) {
					Logger.getLogger(TSDZ2_Configurator.class.getName()).log(Level.SEVERE, null, ex);
				}
				experimentalSettingsList.clearSelection();
                                
				//updateDependiencies(false);
			}
		});
        
         provSet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                            	try {
                                int selectedIndex = provSet.getSelectedIndex();
                                provenSettingsList.setSelectedIndex(selectedIndex);
					loadSettings(((FileContainer) provenSettingsList.getSelectedValue()).file);
					provenSettingsList.clearSelection();
				} catch (IOException ex) {
					Logger.getLogger(TSDZ2_Configurator.class.getName()).log(Level.SEVERE, null, ex);
				}
				provenSettingsList.clearSelection();
				//updateDependiencies(false);
			}
		});
         
             
          jButton1.addActionListener(new ActionListener() {
          
          public void actionPerformed(ActionEvent arg0) {
          			PrintWriter iWriter = null;
                                PrintWriter pWriter = null;
				try {
					//FileWriter fw = new FileWriter("settings.ini");
					//BufferedWriter bw = new BufferedWriter(fw);
                                        
                                        
					File newFile = new File(experimentalSettingsDir + File.separator + new SimpleDateFormat("yyyyMMdd-HHmmssz").format(new Date()) + ".ini");
					//TSDZ2_Configurator ConfiguratorObject = new TSDZ2_Configurator();
                                        //ConfiguratorObject.AddListItem(newFile);
                                        experimentalSettingsFilesModel.add(0, new FileContainer(newFile)); //hier wird nur die neue Datei in die Liste geschrieben...

					iWriter = new PrintWriter(new BufferedWriter(new FileWriter(newFile)));
					pWriter = new PrintWriter(new BufferedWriter(new FileWriter("src/controller/config.h")));
					pWriter.println("/*\r\n"
							+ " * config.h\r\n"
							+ " *\r\n"
							+ " *  Automatically created by TSDS2 Parameter Configurator\r\n"
							+ " *  Author: stancecoke\r\n"
							+ " */\r\n"
							+ "\r\n"
							+ "#ifndef CONFIG_H_\r\n"
							+ "#define CONFIG_H_\r\n");
                                        String text_to_save="";
                                        
                                        if (RB_MOTOR_36V.isSelected()) {
						text_to_save = "#define MOTOR_TYPE 1";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_MOTOR_36V.isSelected());

                                        if (RB_MOTOR_48V.isSelected()) {
						text_to_save = "#define MOTOR_TYPE 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_MOTOR_48V.isSelected());

                                        if (CB_EXP_HIGH_CAD_MODE.isSelected()) {
						text_to_save = "#define EXPERIMENTAL_HIGH_CADENCE_MODE 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define EXPERIMENTAL_HIGH_CADENCE_MODE 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_EXP_HIGH_CAD_MODE.isSelected());
                                        
                                        text_to_save = "#define MOTOR_ACCELERATION  " + TF_MOTOR_ACC.getText();
                                        iWriter.println(TF_MOTOR_ACC.getText());
					pWriter.println(text_to_save);

                                        if (CB_ASS_WITHOUT_PED.isSelected()) {
						text_to_save = "#define MOTOR_ASSISTANCE_WITHOUT_PEDAL_ROTATION 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define MOTOR_ASSISTANCE_WITHOUT_PEDAL_ROTATION 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_ASS_WITHOUT_PED.isSelected());
                                        
                                        text_to_save = "#define ASSISTANCE_WITHOUT_PEDAL_ROTATION_THRESHOLD " + TF_ASS_WITHOUT_PED_THRES.getText();
                                        iWriter.println(TF_ASS_WITHOUT_PED_THRES.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define PEDAL_TORQUE_PER_10_BIT_ADC_STEP_X100 " + TF_TORQ_PER_ADC_STEP_STD.getText();
                                        iWriter.println(TF_TORQ_PER_ADC_STEP_STD.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define PEDAL_TORQUE_10_BIT_ADC_RANGE " + TF_TORQ_ADC_RANGE.getText();
                                        iWriter.println(TF_TORQ_ADC_RANGE.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define CADENCE_SENSOR_PULSE_HIGH_PERCENTAGE_X10 " + TF_CAD_SENS_HIGH_PER.getText();
                                        iWriter.println(TF_CAD_SENS_HIGH_PER.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define MOTOR_BLOCKED_COUNTER_THRESHOLD " + TF_MOTOR_BLOCK_TIME.getText();
                                        iWriter.println(TF_MOTOR_BLOCK_TIME.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define MOTOR_BLOCKED_BATTERY_CURRENT_THRESHOLD_X10 " + TF_MOTOR_BLOCK_CURR.getText();
                                        iWriter.println(TF_MOTOR_BLOCK_CURR.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define MOTOR_BLOCKED_ERPS_THRESHOLD " + TF_MOTOR_BLOCK_ERPS.getText();
                                        iWriter.println(TF_MOTOR_BLOCK_ERPS.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define PWM_DUTY_CYCLE_RAMP_DOWN_MIN_ADDITIONAL  " + TF_RAMP_DOWN_ADD.getText();
                                        iWriter.println(TF_RAMP_DOWN_ADD.getText());
					pWriter.println(text_to_save); 
                                      
                                        text_to_save = "#define BATTERY_CURRENT_MAX " + TF_BAT_CUR_MAX.getText();
                                        iWriter.println(TF_BAT_CUR_MAX.getText());
					pWriter.println(text_to_save);                                        
                                        
                                        text_to_save = "#define TARGET_MAX_BATTERY_POWER " + TF_BATT_POW_MAX.getText();
                                        iWriter.println(TF_BATT_POW_MAX.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define TARGET_MAX_BATTERY_CAPACITY " + TF_BATT_CAPACITY.getText();
                                        iWriter.println(TF_BATT_CAPACITY.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define BATTERY_CELLS_NUMBER " + TF_BATT_NUM_CELLS.getText();
                                        iWriter.println(TF_BATT_NUM_CELLS.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define BATTERY_PACK_RESISTANCE " + TF_BATT_RESIST.getText();
                                        iWriter.println(TF_BATT_RESIST.getText());
					pWriter.println(text_to_save);

                                        text_to_save = "#define BATTERY_LOW_VOLTAGE_CUT_OFF " + TF_BATT_VOLT_CUT_OFF.getText();
                                        iWriter.println(TF_BATT_VOLT_CUT_OFF.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define ACTUAL_BATTERY_VOLTAGE_PERCENT " + TF_BATT_VOLT_CAL.getText();
                                        iWriter.println(TF_BATT_VOLT_CAL.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define ACTUAL_BATTERY_CAPACITY_PERCENT " + TF_BATT_CAPACITY_CAL.getText();
                                        iWriter.println(TF_BATT_CAPACITY_CAL.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define LI_ION_CELL_OVERVOLT " + TF_BAT_CELL_OVER.getText();
                                        iWriter.println(TF_BAT_CELL_OVER.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define LI_ION_CELL_RESET_SOC_PERCENT " + TF_BAT_CELL_SOC.getText();
                                        iWriter.println(TF_BAT_CELL_SOC.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define LI_ION_CELL_VOLTS_FULL " + TF_BAT_CELL_FULL.getText();
                                        iWriter.println(TF_BAT_CELL_FULL.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define LI_ION_CELL_VOLTS_3_OF_4 " + TF_BAT_CELL_3_4.getText();
                                        iWriter.println(TF_BAT_CELL_3_4.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define LI_ION_CELL_VOLTS_2_OF_4 " + TF_BAT_CELL_2_4.getText();
                                        iWriter.println(TF_BAT_CELL_2_4.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define LI_ION_CELL_VOLTS_1_OF_4 " + TF_BAT_CELL_1_4.getText();
                                        iWriter.println(TF_BAT_CELL_1_4.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define LI_ION_CELL_VOLTS_5_OF_6 " + TF_BAT_CELL_5_6.getText();
                                        iWriter.println(TF_BAT_CELL_5_6.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define LI_ION_CELL_VOLTS_4_OF_6 " + TF_BAT_CELL_4_6.getText();
                                        iWriter.println(TF_BAT_CELL_4_6.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define LI_ION_CELL_VOLTS_3_OF_6 " + TF_BAT_CELL_3_6.getText();
                                        iWriter.println(TF_BAT_CELL_3_6.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define LI_ION_CELL_VOLTS_2_OF_6 " + TF_BAT_CELL_2_6.getText();
                                        iWriter.println(TF_BAT_CELL_2_6.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define LI_ION_CELL_VOLTS_1_OF_6 " + TF_BAT_CELL_1_6.getText();
                                        iWriter.println(TF_BAT_CELL_1_6.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define LI_ION_CELL_VOLTS_EMPTY " + TF_BAT_CELL_EMPTY.getText();
                                        iWriter.println(TF_BAT_CELL_EMPTY.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define WHEEL_PERIMETER " + TF_WHEEL_CIRCUMF.getText();
                                        iWriter.println(TF_WHEEL_CIRCUMF.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define WHEEL_MAX_SPEED " + TF_MAX_SPEED.getText();
                                        iWriter.println(TF_MAX_SPEED.getText());
					pWriter.println(text_to_save);

                                        if (CB_LIGHTS.isSelected()) {
						text_to_save = "#define ENABLE_LIGHTS 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define ENABLE_LIGHTS 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_LIGHTS.isSelected());
                                        
                                        if (CB_WALK_ASSIST.isSelected()) {
						text_to_save = "#define ENABLE_WALK_ASSIST 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define ENABLE_WALK_ASSIST 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_WALK_ASSIST.isSelected());

                                        if (CB_BRAKE_SENSOR.isSelected()) {
						text_to_save = "#define ENABLE_BRAKE_SENSOR 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define ENABLE_BRAKE_SENSOR 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_BRAKE_SENSOR.isSelected());
                                        
                                        if (RB_ADC_OPTION_DIS.isSelected()) {
						text_to_save = "#define ENABLE_THROTTLE 0"+System.getProperty("line.separator")+"#define ENABLE_TEMPERATURE_LIMIT 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_ADC_OPTION_DIS.isSelected());

                                        if (RB_THROTTLE.isSelected()) {
						text_to_save = "#define ENABLE_THROTTLE 1"+System.getProperty("line.separator")+"#define ENABLE_TEMPERATURE_LIMIT 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_THROTTLE.isSelected());

                                        if (RB_TEMP_LIMIT.isSelected()) {
						text_to_save = "#define ENABLE_THROTTLE 0"+System.getProperty("line.separator")+"#define ENABLE_TEMPERATURE_LIMIT 1";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_TEMP_LIMIT.isSelected());

                                        if (CB_STREET_MODE_ON_START.isSelected()) {
						text_to_save = "#define ENABLE_STREET_MODE_ON_STARTUP 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define ENABLE_STREET_MODE_ON_STARTUP 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_STREET_MODE_ON_START.isSelected());

                                        if (CB_SET_PARAM_ON_START.isSelected()) {
						text_to_save = "#define ENABLE_SET_PARAMETER_ON_STARTUP 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define ENABLE_SET_PARAMETER_ON_STARTUP 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_SET_PARAM_ON_START.isSelected());

                                        if (CB_ODO_COMPENSATION.isSelected()) {
						text_to_save = "#define ENABLE_ODOMETER_COMPENSATION 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define ENABLE_ODOMETER_COMPENSATION 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_ODO_COMPENSATION.isSelected());

                                        if (CB_CAD_SENSOR_ADV.isSelected()) {
						text_to_save = "#define CADENCE_SENSOR_MODE_ON_STARTUP 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define CADENCE_SENSOR_MODE_ON_STARTUP 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_CAD_SENSOR_ADV.isSelected());

                                        if (CB_TOR_SENSOR_ADV.isSelected()) {
						text_to_save = "#define TORQUE_SENSOR_MODE_ON_STARTUP 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define TORQUE_SENSOR_MODE_ON_STARTUP 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_TOR_SENSOR_ADV.isSelected());
                                        
                                        text_to_save = "#define LIGHTS_CONFIGURATION_ON_STARTUP " + TF_LIGHT_MODE_ON_START.getText();
                                        iWriter.println(TF_LIGHT_MODE_ON_START.getText());
					pWriter.println(text_to_save);

                                        if (RB_POWER_ON_START.isSelected()) {
						text_to_save = "#define RIDING_MODE_ON_STARTUP 1";
						pWriter.println(text_to_save);
					}   
					iWriter.println(RB_POWER_ON_START.isSelected());

                                        if (RB_TORQUE_ON_START.isSelected()) {
						text_to_save = "#define RIDING_MODE_ON_STARTUP 2";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_TORQUE_ON_START.isSelected());

                                        if (RB_CADENCE_ON_START.isSelected()) {
						text_to_save = "#define RIDING_MODE_ON_STARTUP 3";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_CADENCE_ON_START.isSelected());

                                        if (RB_EMTB_ON_START.isSelected()) {
						text_to_save = "#define RIDING_MODE_ON_STARTUP 4";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_EMTB_ON_START.isSelected());
                                        
                                        text_to_save = "#define LIGHTS_CONFIGURATION_1 " + TF_LIGHT_MODE_1.getText();
                                        iWriter.println(TF_LIGHT_MODE_1.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define LIGHTS_CONFIGURATION_2 " + TF_LIGHT_MODE_2.getText();
                                        iWriter.println(TF_LIGHT_MODE_2.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define LIGHTS_CONFIGURATION_3 " + TF_LIGHT_MODE_3.getText();
                                        iWriter.println(TF_LIGHT_MODE_3.getText());
					pWriter.println(text_to_save);
                                        
                                        if (CB_STREET_POWER_LIM.isSelected()) {
						text_to_save = "#define STREET_MODE_POWER_LIMIT_ENABLED 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define STREET_MODE_POWER_LIMIT_ENABLED 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_STREET_POWER_LIM.isSelected());
                                        
                                        text_to_save = "#define STREET_MODE_POWER_LIMIT " + TF_STREET_POWER_LIM.getText();
                                        iWriter.println(TF_STREET_POWER_LIM.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define STREET_MODE_SPEED_LIMIT " + TF_STREET_SPEED_LIM.getText();
                                        iWriter.println(TF_STREET_SPEED_LIM.getText());
					pWriter.println(text_to_save);
                                        
                                        if (CB_STREET_THROTTLE.isSelected()) {
						text_to_save = "#define STREET_MODE_THROTTLE_ENABLED 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define STREET_MODE_THROTTLE_ENABLED 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_STREET_THROTTLE.isSelected());
                                        
                                        if (CB_STREET_CRUISE.isSelected()) {
						text_to_save = "#define STREET_MODE_CRUISE_ENABLED 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define STREET_MODE_CRUISE_ENABLED 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_STREET_CRUISE.isSelected());
                                        
                                        text_to_save = "#define ADC_THROTTLE_MIN_VALUE " + TF_ADC_THROTTLE_MIN.getText();
                                        iWriter.println(TF_ADC_THROTTLE_MIN.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define ADC_THROTTLE_MAX_VALUE " + TF_ADC_THROTTLE_MAX.getText();
                                        iWriter.println(TF_ADC_THROTTLE_MAX.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define MOTOR_TEMPERATURE_MIN_VALUE_LIMIT " + TF_TEMP_MIN_LIM.getText();
                                        iWriter.println(TF_TEMP_MIN_LIM.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define MOTOR_TEMPERATURE_MAX_VALUE_LIMIT " + TF_TEMP_MAX_LIM.getText();
                                        iWriter.println(TF_TEMP_MAX_LIM.getText());
					pWriter.println(text_to_save);
                                        
                                        if (CB_TEMP_ERR_MIN_LIM.isSelected()) {
						text_to_save = "#define ENABLE_TEMPERATURE_ERROR_MIN_LIMIT 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define ENABLE_TEMPERATURE_ERROR_MIN_LIMIT 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_TEMP_ERR_MIN_LIM.isSelected());
                                       
                                        if (RB_VLCD6.isSelected()) {
						text_to_save = "#define ENABLE_VLCD6 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define ENABLE_VLCD6 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_VLCD6.isSelected());
                                        
                                        if (RB_VLCD5.isSelected()) {
						text_to_save = "#define ENABLE_VLCD5 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define ENABLE_VLCD5 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_VLCD5.isSelected());                                        

                                        if (RB_XH18.isSelected()) {
						text_to_save = "#define ENABLE_XH18 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define ENABLE_XH18 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_XH18.isSelected());

                                        if (RB_DISPLAY_WORK_ON.isSelected()) {
						text_to_save = "#define ENABLE_DISPLAY_WORKING_FLAG 1";
						pWriter.println(text_to_save);
                                        }
                                        else {
                                                text_to_save = "#define ENABLE_DISPLAY_WORKING_FLAG 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_DISPLAY_WORK_ON.isSelected());

                                        if (RB_DISPLAY_ALWAY_ON.isSelected()) {
						text_to_save = "#define ENABLE_DISPLAY_ALWAYS_ON 1";
						pWriter.println(text_to_save);
                                        }
                                        else {
                                                text_to_save = "#define ENABLE_DISPLAY_ALWAYS_ON 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_DISPLAY_ALWAY_ON.isSelected());
                                        
                                        if (CB_MAX_SPEED_DISPLAY.isSelected()) {
						text_to_save = "#define ENABLE_WHEEL_MAX_SPEED_FROM_DISPLAY 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define ENABLE_WHEEL_MAX_SPEED_FROM_DISPLAY 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_MAX_SPEED_DISPLAY.isSelected());
                                        
                                        text_to_save = "#define DELAY_MENU_ON " + TF_DELAY_MENU.getText();
                                        iWriter.println(TF_DELAY_MENU.getText());
					pWriter.println(text_to_save);
                                        
                                        if (CB_RET_DISPLAY_MODE.isSelected()) {
						text_to_save = "#define ENABLE_RETURN_DEFAULT_DISPLAY_MODE 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define ENABLE_RETURN_DEFAULT_DISPLAY_MODE 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_RET_DISPLAY_MODE.isSelected());
                                        
                                        text_to_save = "#define DELAY_DISPLAY_MODE_DEFAULT " + TF_RET_DISPLAY_MODE.getText();
                                        iWriter.println(TF_RET_DISPLAY_MODE.getText());
					pWriter.println(text_to_save);
                                        
                                        if (CB_AUTO_DISPLAY_DATA.isSelected()) {
						text_to_save = "#define ENABLE_AUTO_DATA_DISPLAY 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define ENABLE_AUTO_DATA_DISPLAY 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_AUTO_DISPLAY_DATA.isSelected());
                                        
                                        if (CB_DISPLAY_DOUBLE_DATA.isSelected()) {
						text_to_save = "#define ENABLE_DISPLAY_DOUBLE_DATA 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define ENABLE_DISPLAY_DOUBLE_DATA 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_DISPLAY_DOUBLE_DATA.isSelected());
                                        
                                        text_to_save = "#define DELAY_DISPLAY_DATA_1 " + TF_DELAY_DATA_1.getText();
                                        iWriter.println(TF_DELAY_DATA_1.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define DELAY_DISPLAY_DATA_2 " + TF_DELAY_DATA_2.getText();
                                        iWriter.println(TF_DELAY_DATA_2.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define DELAY_DISPLAY_DATA_3 " + TF_DELAY_DATA_3.getText();
                                        iWriter.println(TF_DELAY_DATA_3.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define DELAY_DISPLAY_DATA_4 " + TF_DELAY_DATA_4.getText();
                                        iWriter.println(TF_DELAY_DATA_4.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define DELAY_DISPLAY_DATA_5 " + TF_DELAY_DATA_5.getText();
                                        iWriter.println(TF_DELAY_DATA_5.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define DELAY_DISPLAY_DATA_6 " + TF_DELAY_DATA_6.getText();
                                        iWriter.println(TF_DELAY_DATA_6.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define DISPLAY_DATA_1 " + TF_DATA_1.getText();
                                        iWriter.println(TF_DATA_1.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define DISPLAY_DATA_2 " + TF_DATA_2.getText();
                                        iWriter.println(TF_DATA_2.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define DISPLAY_DATA_3 " + TF_DATA_3.getText();
                                        iWriter.println(TF_DATA_3.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define DISPLAY_DATA_4 " + TF_DATA_4.getText();
                                        iWriter.println(TF_DATA_4.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define DISPLAY_DATA_5 " + TF_DATA_5.getText();
                                        iWriter.println(TF_DATA_5.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define DISPLAY_DATA_6 " + TF_DATA_6.getText();
                                        iWriter.println(TF_DATA_6.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define POWER_ASSIST_LEVEL_1 " + TF_POWER_ASS_1.getText();
                                        iWriter.println(TF_POWER_ASS_1.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define POWER_ASSIST_LEVEL_2 " + TF_POWER_ASS_2.getText();
                                        iWriter.println(TF_POWER_ASS_2.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define POWER_ASSIST_LEVEL_3 " + TF_POWER_ASS_3.getText();
                                        iWriter.println(TF_POWER_ASS_3.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define POWER_ASSIST_LEVEL_4 " + TF_POWER_ASS_4.getText();
                                        iWriter.println(TF_POWER_ASS_4.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define TORQUE_ASSIST_LEVEL_1 " + TF_TORQUE_ASS_1.getText();
                                        iWriter.println(TF_TORQUE_ASS_1.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define TORQUE_ASSIST_LEVEL_2 " + TF_TORQUE_ASS_2.getText();
                                        iWriter.println(TF_TORQUE_ASS_2.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define TORQUE_ASSIST_LEVEL_3 " + TF_TORQUE_ASS_3.getText();
                                        iWriter.println(TF_TORQUE_ASS_3.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define TORQUE_ASSIST_LEVEL_4 " + TF_TORQUE_ASS_4.getText();
                                        iWriter.println(TF_TORQUE_ASS_4.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define CADENCE_ASSIST_LEVEL_1 " + TF_CADENCE_ASS_1.getText();
                                        iWriter.println(TF_CADENCE_ASS_1.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define CADENCE_ASSIST_LEVEL_2 " + TF_CADENCE_ASS_2.getText();
                                        iWriter.println(TF_CADENCE_ASS_2.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define CADENCE_ASSIST_LEVEL_3 " + TF_CADENCE_ASS_3.getText();
                                        iWriter.println(TF_CADENCE_ASS_3.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define CADENCE_ASSIST_LEVEL_4 " + TF_CADENCE_ASS_4.getText();
                                        iWriter.println(TF_CADENCE_ASS_4.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define EMTB_ASSIST_LEVEL_1 " + TF_EMTB_ASS_1.getText();
                                        iWriter.println(TF_EMTB_ASS_1.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define EMTB_ASSIST_LEVEL_2 " + TF_EMTB_ASS_2.getText();
                                        iWriter.println(TF_EMTB_ASS_2.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define EMTB_ASSIST_LEVEL_3 " + TF_EMTB_ASS_3.getText();
                                        iWriter.println(TF_EMTB_ASS_3.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define EMTB_ASSIST_LEVEL_4 " + TF_EMTB_ASS_4.getText();
                                        iWriter.println(TF_EMTB_ASS_4.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define WALK_ASSIST_LEVEL_1 " + TF_WALK_ASS_1.getText();
                                        iWriter.println(TF_WALK_ASS_1.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define WALK_ASSIST_LEVEL_2 " + TF_WALK_ASS_2.getText();
                                        iWriter.println(TF_WALK_ASS_2.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define WALK_ASSIST_LEVEL_3 " + TF_WALK_ASS_3.getText();
                                        iWriter.println(TF_WALK_ASS_3.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define WALK_ASSIST_LEVEL_4 " + TF_WALK_ASS_4.getText();
                                        iWriter.println(TF_WALK_ASS_4.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define WALK_ASSIST_THRESHOLD_SPEED " + TF_WALK_ASS_SPEED.getText();
                                        iWriter.println(TF_WALK_ASS_SPEED.getText());
					pWriter.println(text_to_save);
                                        
                                        if (CB_WALK_TIME_ENA.isSelected()) {
						text_to_save = "#define WALK_ASSIST_DEBOUNCE_ENABLED 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define WALK_ASSIST_DEBOUNCE_ENABLED 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_WALK_TIME_ENA.isSelected());
                                        
                                        text_to_save = "#define WALK_ASSIST_DEBOUNCE_TIME " + TF_WALK_ASS_TIME.getText();
                                        iWriter.println(TF_WALK_ASS_TIME.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define CRUISE_TARGET_SPEED_LEVEL_1 " + TF_CRUISE_ASS_1.getText();
                                        iWriter.println(TF_CRUISE_ASS_1.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define CRUISE_TARGET_SPEED_LEVEL_2 " + TF_CRUISE_ASS_2.getText();
                                        iWriter.println(TF_CRUISE_ASS_2.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define CRUISE_TARGET_SPEED_LEVEL_3 " + TF_CRUISE_ASS_3.getText();
                                        iWriter.println(TF_CRUISE_ASS_3.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define CRUISE_TARGET_SPEED_LEVEL_4 " + TF_CRUISE_ASS_4.getText();
                                        iWriter.println(TF_CRUISE_ASS_4.getText());
					pWriter.println(text_to_save);
                                        
                                        if (CB_CRUISE_WHITOUT_PED.isSelected()) {
						text_to_save = "#define CRUISE_MODE_WALK_ENABLED 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define CRUISE_MODE_WALK_ENABLED 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_CRUISE_WHITOUT_PED.isSelected());
                                        
                                        text_to_save = "#define CRUISE_THRESHOLD_SPEED " + TF_CRUISE_SPEED_ENA.getText();
                                        iWriter.println(TF_CRUISE_SPEED_ENA.getText());
					pWriter.println(text_to_save);

                                        text_to_save = "#define ADC_TORQUE_OFFSET_ADJUSTMENT " + TF_TORQ_ADC_OFFSET_ADJ.getText();
                                        iWriter.println(TF_TORQ_ADC_OFFSET_ADJ.getText());
					pWriter.println(text_to_save);

                                        text_to_save = "#define AUTO_DATA_NUMBER_DISPLAY " + TF_NUM_DATA_AUTO_DISPLAY.getText();
                                        iWriter.println(TF_NUM_DATA_AUTO_DISPLAY.getText());
					pWriter.println(text_to_save);

                                        if (RB_UNIT_KILOMETERS.isSelected()) {
						text_to_save = "#define UNITS_TYPE 0";
						pWriter.println(text_to_save);
					}   
					iWriter.println(RB_UNIT_KILOMETERS.isSelected());

                                        if (RB_UNIT_MILES.isSelected()) {
						text_to_save = "#define UNITS_TYPE 1";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_UNIT_MILES.isSelected());
                                        
                                        pWriter.println("\r\n#endif /* CONFIG_H_ */");

					iWriter.close();
 				} catch (IOException ioe) {
					ioe.printStackTrace(System.err);
				} finally {
					if (pWriter != null) {
						pWriter.flush();
						pWriter.close();

					}
				}  
                                try {
					Process process = Runtime.getRuntime().exec("cmd /c start compile_and_flash_20");
				} catch (IOException e1) {
					e1.printStackTrace(System.err);
				}
          
          }
          
          
          });
                  
         		if (lastSettingsFile != null) {
			try {
				loadSettings(lastSettingsFile);
			} catch (Exception ex) {

			}
			provenSettingsList.clearSelection();
			experimentalSettingsList.clearSelection();
			//updateDependiencies(false);
		}
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jRadioButton1 = new javax.swing.JRadioButton();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        Label_Parameter1 = new javax.swing.JLabel();
        RB_MOTOR_36V = new javax.swing.JRadioButton();
        CB_EXP_HIGH_CAD_MODE = new javax.swing.JCheckBox();
        CB_ASS_WITHOUT_PED = new javax.swing.JCheckBox();
        TF_TORQ_PER_ADC_STEP_STD = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        TF_CAD_SENS_HIGH_PER = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TF_MOTOR_ACC = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        TF_ASS_WITHOUT_PED_THRES = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        TF_RAMP_DOWN_ADD = new javax.swing.JTextField();
        TF_TORQ_ADC_RANGE = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel_TORQ_ADC_RANGE = new javax.swing.JLabel();
        RB_MOTOR_48V = new javax.swing.JRadioButton();
        TF_TORQ_ADC_OFFSET_ADJ = new javax.swing.JTextField();
        jLabel_TORQ_ADC_OFFSET_ADJ = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        TF_BATT_CAPACITY = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        TF_BAT_CUR_MAX = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        TF_BATT_POW_MAX = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        TF_BATT_NUM_CELLS = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        TF_BATT_RESIST = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        TF_BATT_VOLT_CAL = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        TF_BATT_CAPACITY_CAL = new javax.swing.JTextField();
        TF_BATT_VOLT_CUT_OFF = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        Label_Parameter3 = new javax.swing.JLabel();
        Label_Parameter4 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        RB_VLCD6 = new javax.swing.JRadioButton();
        RB_VLCD5 = new javax.swing.JRadioButton();
        RB_XH18 = new javax.swing.JRadioButton();
        Label_Parameter2 = new javax.swing.JLabel();
        RB_DISPLAY_WORK_ON = new javax.swing.JRadioButton();
        RB_DISPLAY_ALWAY_ON = new javax.swing.JRadioButton();
        Label_Parameter5 = new javax.swing.JLabel();
        RB_UNIT_MILES = new javax.swing.JRadioButton();
        RB_UNIT_KILOMETERS = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        TF_MAX_SPEED = new javax.swing.JTextField();
        TF_WHEEL_CIRCUMF = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        CB_LIGHTS = new javax.swing.JCheckBox();
        CB_WALK_ASSIST = new javax.swing.JCheckBox();
        CB_BRAKE_SENSOR = new javax.swing.JCheckBox();
        jLabel30 = new javax.swing.JLabel();
        RB_ADC_OPTION_DIS = new javax.swing.JRadioButton();
        RB_THROTTLE = new javax.swing.JRadioButton();
        jLabel31 = new javax.swing.JLabel();
        RB_TEMP_LIMIT = new javax.swing.JRadioButton();
        CB_STREET_MODE_ON_START = new javax.swing.JCheckBox();
        CB_ODO_COMPENSATION = new javax.swing.JCheckBox();
        CB_CAD_SENSOR_ADV = new javax.swing.JCheckBox();
        CB_TOR_SENSOR_ADV = new javax.swing.JCheckBox();
        jLabel33 = new javax.swing.JLabel();
        CB_AUTO_DISPLAY_DATA = new javax.swing.JCheckBox();
        CB_SET_PARAM_ON_START = new javax.swing.JCheckBox();
        CB_MAX_SPEED_DISPLAY = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        TF_POWER_ASS_1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        TF_POWER_ASS_2 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        TF_POWER_ASS_3 = new javax.swing.JTextField();
        TF_POWER_ASS_4 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        RB_POWER_ON_START = new javax.swing.JRadioButton();
        jLabel58 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        TF_TORQUE_ASS_1 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        TF_TORQUE_ASS_2 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        TF_TORQUE_ASS_3 = new javax.swing.JTextField();
        TF_TORQUE_ASS_4 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        RB_TORQUE_ON_START = new javax.swing.JRadioButton();
        jLabel59 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        TF_CADENCE_ASS_1 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        TF_CADENCE_ASS_2 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        TF_CADENCE_ASS_3 = new javax.swing.JTextField();
        TF_CADENCE_ASS_4 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        RB_CADENCE_ON_START = new javax.swing.JRadioButton();
        jLabel60 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        TF_EMTB_ASS_1 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        TF_EMTB_ASS_2 = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        TF_EMTB_ASS_3 = new javax.swing.JTextField();
        TF_EMTB_ASS_4 = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        RB_EMTB_ON_START = new javax.swing.JRadioButton();
        jLabel61 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        TF_WALK_ASS_1 = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        TF_WALK_ASS_2 = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        TF_WALK_ASS_3 = new javax.swing.JTextField();
        TF_WALK_ASS_4 = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        TF_WALK_ASS_SPEED = new javax.swing.JTextField();
        TF_WALK_ASS_TIME = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        CB_WALK_TIME_ENA = new javax.swing.JCheckBox();
        jPanel16 = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        TF_CRUISE_ASS_1 = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        TF_CRUISE_ASS_2 = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        TF_CRUISE_ASS_3 = new javax.swing.JTextField();
        TF_CRUISE_ASS_4 = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        TF_CRUISE_SPEED_ENA = new javax.swing.JTextField();
        CB_CRUISE_WHITOUT_PED = new javax.swing.JCheckBox();
        jPanel17 = new javax.swing.JPanel();
        jLabel76 = new javax.swing.JLabel();
        jLabelLights0 = new javax.swing.JLabel();
        TF_LIGHT_MODE_ON_START = new javax.swing.JTextField();
        jLabelLights1 = new javax.swing.JLabel();
        TF_LIGHT_MODE_1 = new javax.swing.JTextField();
        jLabelLights2 = new javax.swing.JLabel();
        TF_LIGHT_MODE_2 = new javax.swing.JTextField();
        TF_LIGHT_MODE_3 = new javax.swing.JTextField();
        jLabelLights3 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        TF_STREET_SPEED_LIM = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        TF_STREET_POWER_LIM = new javax.swing.JTextField();
        CB_STREET_POWER_LIM = new javax.swing.JCheckBox();
        CB_STREET_THROTTLE = new javax.swing.JCheckBox();
        CB_STREET_CRUISE = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        TF_BAT_CELL_FULL = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        TF_BAT_CELL_OVER = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        TF_BAT_CELL_SOC = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        TF_BAT_CELL_3_4 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        TF_BAT_CELL_2_4 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        TF_BAT_CELL_1_4 = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        TF_BAT_CELL_5_6 = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        TF_BAT_CELL_4_6 = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        TF_BAT_CELL_3_6 = new javax.swing.JTextField();
        TF_BAT_CELL_2_6 = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        TF_BAT_CELL_1_6 = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        TF_BAT_CELL_EMPTY = new javax.swing.JTextField();
        jLabel85 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        TF_DELAY_DATA_1 = new javax.swing.JTextField();
        jLabelData1 = new javax.swing.JLabel();
        TF_DATA_1 = new javax.swing.JTextField();
        TF_DATA_2 = new javax.swing.JTextField();
        jLabelData2 = new javax.swing.JLabel();
        TF_DATA_3 = new javax.swing.JTextField();
        jLabelData3 = new javax.swing.JLabel();
        jLabelData4 = new javax.swing.JLabel();
        TF_DATA_4 = new javax.swing.JTextField();
        TF_DATA_5 = new javax.swing.JTextField();
        jLabelData5 = new javax.swing.JLabel();
        jLabelData6 = new javax.swing.JLabel();
        TF_DATA_6 = new javax.swing.JTextField();
        jLabel96 = new javax.swing.JLabel();
        TF_DELAY_DATA_2 = new javax.swing.JTextField();
        jLabel97 = new javax.swing.JLabel();
        TF_DELAY_DATA_3 = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        TF_DELAY_DATA_4 = new javax.swing.JTextField();
        jLabel99 = new javax.swing.JLabel();
        TF_DELAY_DATA_5 = new javax.swing.JTextField();
        jLabel100 = new javax.swing.JLabel();
        TF_DELAY_DATA_6 = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel101 = new javax.swing.JLabel();
        CB_RET_DISPLAY_MODE = new javax.swing.JCheckBox();
        jLabel102 = new javax.swing.JLabel();
        TF_ADC_THROTTLE_MIN = new javax.swing.JTextField();
        TF_ADC_THROTTLE_MAX = new javax.swing.JTextField();
        jLabel103 = new javax.swing.JLabel();
        CB_TEMP_ERR_MIN_LIM = new javax.swing.JCheckBox();
        jLabel104 = new javax.swing.JLabel();
        TF_TEMP_MIN_LIM = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
        TF_TEMP_MAX_LIM = new javax.swing.JTextField();
        jLabel106 = new javax.swing.JLabel();
        TF_MOTOR_BLOCK_TIME = new javax.swing.JTextField();
        jLabel107 = new javax.swing.JLabel();
        TF_MOTOR_BLOCK_CURR = new javax.swing.JTextField();
        jLabel108 = new javax.swing.JLabel();
        TF_MOTOR_BLOCK_ERPS = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        TF_DELAY_MENU = new javax.swing.JTextField();
        TF_RET_DISPLAY_MODE = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        CB_DISPLAY_DOUBLE_DATA = new javax.swing.JCheckBox();
        jLabel90 = new javax.swing.JLabel();
        TF_NUM_DATA_AUTO_DISPLAY = new javax.swing.JTextField();
        label1 = new java.awt.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        expSet = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        provSet = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TSDZ2 Parameter Configurator 2.0 for Open Source Firmware mb.20beta1.B");
        setResizable(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Motor settings");

        Label_Parameter1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Label_Parameter1.setForeground(new java.awt.Color(255, 0, 0));
        Label_Parameter1.setText("Motor type");

        buttonGroup1.add(RB_MOTOR_36V);
        RB_MOTOR_36V.setText("36V");

        CB_EXP_HIGH_CAD_MODE.setText("Experimental high cadence mode");

        CB_ASS_WITHOUT_PED.setText("Start-up assistance without pedaling");
        CB_ASS_WITHOUT_PED.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CB_ASS_WITHOUT_PEDStateChanged(evt);
            }
        });

        TF_TORQ_PER_ADC_STEP_STD.setText("67");
        TF_TORQ_PER_ADC_STEP_STD.setToolTipText("<html>\nDefault value 67<br>\nOptional calibration\n</html>");

        jLabel20.setText("Cadence sensor high percentage");

        TF_CAD_SENS_HIGH_PER.setText("500");
        TF_CAD_SENS_HIGH_PER.setToolTipText("Insert value after calibration");
        TF_CAD_SENS_HIGH_PER.setEnabled(CB_CAD_SENSOR_ADV.isSelected());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("Motor acceleration");

        TF_MOTOR_ACC.setText("25");
        TF_MOTOR_ACC.setToolTipText("<html>MAX VALUE<br>\n36 volt motor, 36 volt battery = 35<br>\n36 volt motor, 48 volt battery = 5<br>\n36 volt motor, 52 volt battery = 0<br>\n48 volt motor, 36 volt battery = 45<br>\n48 volt motor, 48 volt battery = 35<br>\n48 volt motor, 52 volt battery = 30\n</html>");

        jLabel5.setText("Assist without pedaling threshold");

        TF_ASS_WITHOUT_PED_THRES.setText("20");
        TF_ASS_WITHOUT_PED_THRES.setToolTipText("<html>Max value 100<br>\nRecommended range 10 to 30\n</html>");
        TF_ASS_WITHOUT_PED_THRES.setEnabled(CB_ASS_WITHOUT_PED.isSelected());

        jLabel22.setText("Motor ramp down additional");

        TF_RAMP_DOWN_ADD.setText("0");
        TF_RAMP_DOWN_ADD.setToolTipText("Value between 0 to 20");

        TF_TORQ_ADC_RANGE.setText("160");
        TF_TORQ_ADC_RANGE.setToolTipText("<html>\nInsert value after calibration<br>\nMax 255\n</html>");
        TF_TORQ_ADC_RANGE.setEnabled(CB_TOR_SENSOR_ADV.isSelected());

        jLabel6.setText("Pedal torque ADC step");

        jLabel_TORQ_ADC_RANGE.setText("Pedal torque ADC range");

        buttonGroup1.add(RB_MOTOR_48V);
        RB_MOTOR_48V.setText("48V");

        TF_TORQ_ADC_OFFSET_ADJ.setText("0");
        TF_TORQ_ADC_OFFSET_ADJ.setToolTipText("Max value 20");

        jLabel_TORQ_ADC_OFFSET_ADJ.setText("Pedal torque ADC offset adjust. (-)");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(CB_EXP_HIGH_CAD_MODE)
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(Label_Parameter1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(56, 56, 56))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CB_ASS_WITHOUT_PED)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(RB_MOTOR_36V, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RB_MOTOR_48V, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel_TORQ_ADC_OFFSET_ADJ, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TF_TORQ_ADC_OFFSET_ADJ, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel_TORQ_ADC_RANGE, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TF_ASS_WITHOUT_PED_THRES, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_MOTOR_ACC, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_RAMP_DOWN_ADD, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_CAD_SENS_HIGH_PER, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_TORQ_ADC_RANGE, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_TORQ_PER_ADC_STEP_STD, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(14, 14, 14))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Label_Parameter1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RB_MOTOR_36V)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RB_MOTOR_48V)))
                .addComponent(CB_EXP_HIGH_CAD_MODE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_ASS_WITHOUT_PED)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_ASS_WITHOUT_PED_THRES, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(4, 4, 4)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_TORQ_PER_ADC_STEP_STD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_TORQ_ADC_RANGE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_TORQ_ADC_RANGE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_TORQ_ADC_OFFSET_ADJ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_TORQ_ADC_OFFSET_ADJ))
                .addGap(7, 7, 7)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_CAD_SENS_HIGH_PER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_MOTOR_ACC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_RAMP_DOWN_ADD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)))
        );

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Battery settings");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 0, 0));
        jLabel21.setText("Battery capacity (Wh)");

        TF_BATT_CAPACITY.setText("630");
        TF_BATT_CAPACITY.setToolTipText("<html>To calculate<br>\nBattery Volt x Ah\n</html>\n");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setText("Battery current max (A)");

        TF_BAT_CUR_MAX.setText("17");
        TF_BAT_CUR_MAX.setToolTipText("<html>Max value<br>\n17 A for 36 V<br>\n12 A for 48 V\n</html>");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jLabel10.setText("Battery power max (W)");

        TF_BATT_POW_MAX.setText("500");
        TF_BATT_POW_MAX.setToolTipText("<html>Motor power limit in offroad mode<br>\nMax value depends on the rated<br>\nmotor power and the battery capacity\n</html>");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 0));
        jLabel11.setText("Battery cells number");

        TF_BATT_NUM_CELLS.setText("10");
        TF_BATT_NUM_CELLS.setToolTipText("<html> 7 for 24 V battery<br>\n10 for 36 V battery<br>\n13 for 48 V battery<br>\n14 for 52 V battery\n</html>");

        jLabel23.setText("Battery resistance (milliOhms)");

        TF_BATT_RESIST.setText("196");
        TF_BATT_RESIST.setToolTipText("<html>Indicative value 100 to 300<br>\nIt depends on the<br>\ncharacteristics of the battery\n</html>");

        jLabel24.setText("Battery voltage calibration (%)");

        TF_BATT_VOLT_CAL.setText("100");
        TF_BATT_VOLT_CAL.setToolTipText("<html>For calibrate voltage displayed<br>\nIndicative value 95 to 105\n</html>");

        jLabel25.setText("Battery capacity calibration (%)");

        TF_BATT_CAPACITY_CAL.setText("100");
        TF_BATT_CAPACITY_CAL.setToolTipText("<html>Starting to 100%<br>\nwith the% remaining when battery is low<br>\ncalculate the actual%\n</html>");

        TF_BATT_VOLT_CUT_OFF.setText("29");
        TF_BATT_VOLT_CUT_OFF.setToolTipText("<html>Indicative value 29 for 36 V<br>\n38 for 48 V, It depends on the<br>\ncharacteristics of the battery\n</html>");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 0));
        jLabel12.setText("Battery voltage cut off (V)");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(TF_BAT_CUR_MAX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TF_BATT_NUM_CELLS, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TF_BATT_VOLT_CAL, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TF_BATT_POW_MAX, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TF_BATT_CAPACITY, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(TF_BATT_CAPACITY_CAL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(TF_BATT_VOLT_CUT_OFF, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(TF_BATT_RESIST, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BAT_CUR_MAX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BATT_POW_MAX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BATT_CAPACITY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BATT_NUM_CELLS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BATT_RESIST)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BATT_VOLT_CUT_OFF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BATT_VOLT_CAL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BATT_CAPACITY_CAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        Label_Parameter3.setText("Display working on");

        Label_Parameter4.setText("Display always on");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Display settings");

        buttonGroup2.add(RB_VLCD6);
        RB_VLCD6.setText("VLCD6");
        RB_VLCD6.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                RB_VLCD6StateChanged(evt);
            }
        });

        buttonGroup2.add(RB_VLCD5);
        RB_VLCD5.setText("VLCD5");
        RB_VLCD5.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                RB_VLCD5StateChanged(evt);
            }
        });

        buttonGroup2.add(RB_XH18);
        RB_XH18.setText("XH18");
        RB_XH18.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                RB_XH18StateChanged(evt);
            }
        });

        Label_Parameter2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Label_Parameter2.setForeground(new java.awt.Color(255, 0, 0));
        Label_Parameter2.setText("Display type");

        buttonGroup4.add(RB_DISPLAY_WORK_ON);

        buttonGroup4.add(RB_DISPLAY_ALWAY_ON);

        Label_Parameter5.setText("Units type");

        buttonGroup6.add(RB_UNIT_MILES);
        RB_UNIT_MILES.setText("mph");
        RB_UNIT_MILES.setToolTipText("<html>Also set on the display<br>\nIf you set miles in display<br>\nset max wheel available\n</html>");

        buttonGroup6.add(RB_UNIT_KILOMETERS);
        RB_UNIT_KILOMETERS.setText("kph");
        RB_UNIT_KILOMETERS.setToolTipText("<html>Also set on the display<br>\nIf you set miles in display<br>\nset max wheel available\n</html>");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(Label_Parameter2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                            .addComponent(Label_Parameter3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Label_Parameter4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(Label_Parameter5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(RB_UNIT_KILOMETERS)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RB_XH18)
                            .addComponent(RB_VLCD5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addComponent(RB_VLCD6, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(RB_DISPLAY_WORK_ON)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RB_UNIT_MILES)
                            .addComponent(RB_DISPLAY_ALWAY_ON))
                        .addGap(35, 35, 35)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(RB_VLCD5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RB_VLCD6)
                    .addComponent(Label_Parameter2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RB_XH18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Label_Parameter3)
                    .addComponent(RB_DISPLAY_WORK_ON))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(RB_DISPLAY_ALWAY_ON)
                    .addComponent(Label_Parameter4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Label_Parameter5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(RB_UNIT_KILOMETERS))
                    .addComponent(RB_UNIT_MILES)))
        );

        jLabel15.setText("Max speed (km/h)");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 0));
        jLabel14.setText("Wheel circumference (mm)");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Bike settings");

        TF_MAX_SPEED.setText("45");
        TF_MAX_SPEED.setToolTipText("Max value in EU 25 km/h");
        TF_MAX_SPEED.setEnabled(!CB_MAX_SPEED_DISPLAY.isSelected());

        TF_WHEEL_CIRCUMF.setText("2280");
        TF_WHEEL_CIRCUMF.setToolTipText("<html>Indicative values:<br>\n26-inch wheel = 2050 mm<br>\n27-inch wheel = 2150 mm<br>\n27.5 inch wheel = 2215 mm<br>\n28-inch wheel = 2250 mm<br>\n29-inch wheel = 2300 mmV\n</html>");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TF_WHEEL_CIRCUMF, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TF_MAX_SPEED, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_WHEEL_CIRCUMF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(4, 4, 4)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_MAX_SPEED, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel39.setText("Function settings");

        CB_LIGHTS.setText("Lights");
        CB_LIGHTS.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CB_LIGHTSStateChanged(evt);
            }
        });

        CB_WALK_ASSIST.setText("Walk assist");
        CB_WALK_ASSIST.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CB_WALK_ASSISTStateChanged(evt);
            }
        });

        CB_BRAKE_SENSOR.setText("Brake sensor");
        CB_BRAKE_SENSOR.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CB_BRAKE_SENSORStateChanged(evt);
            }
        });

        jLabel30.setText("ADC optional disabled");

        buttonGroup3.add(RB_ADC_OPTION_DIS);
        RB_ADC_OPTION_DIS.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                RB_ADC_OPTION_DISStateChanged(evt);
            }
        });

        buttonGroup3.add(RB_THROTTLE);
        RB_THROTTLE.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                RB_THROTTLEStateChanged(evt);
            }
        });

        jLabel31.setText("Throttle");

        buttonGroup3.add(RB_TEMP_LIMIT);
        RB_TEMP_LIMIT.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                RB_TEMP_LIMITStateChanged(evt);
            }
        });

        CB_STREET_MODE_ON_START.setText("Street mode on startup");

        CB_ODO_COMPENSATION.setText("Odometer compensation");

        CB_CAD_SENSOR_ADV.setText("Cadence sensor adv. on startup");
        CB_CAD_SENSOR_ADV.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CB_CAD_SENSOR_ADVStateChanged(evt);
            }
        });

        CB_TOR_SENSOR_ADV.setText("Torque sensor adv. on startup");
        CB_TOR_SENSOR_ADV.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CB_TOR_SENSOR_ADVStateChanged(evt);
            }
        });

        jLabel33.setText("Temperature sensor");

        CB_AUTO_DISPLAY_DATA.setText("Auto display data with lights on");
        CB_AUTO_DISPLAY_DATA.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CB_AUTO_DISPLAY_DATAStateChanged(evt);
            }
        });

        CB_SET_PARAM_ON_START.setText("Set parameters on startup");

        CB_MAX_SPEED_DISPLAY.setText("Set max speed from display");
        CB_MAX_SPEED_DISPLAY.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CB_MAX_SPEED_DISPLAYStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CB_MAX_SPEED_DISPLAY)
                    .addComponent(CB_AUTO_DISPLAY_DATA)
                    .addComponent(CB_STREET_MODE_ON_START)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RB_ADC_OPTION_DIS)
                            .addComponent(RB_THROTTLE)
                            .addComponent(RB_TEMP_LIMIT)))
                    .addComponent(jLabel39)
                    .addComponent(CB_LIGHTS, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CB_WALK_ASSIST, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CB_BRAKE_SENSOR, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CB_CAD_SENSOR_ADV)
                    .addComponent(CB_TOR_SENSOR_ADV)
                    .addComponent(CB_ODO_COMPENSATION)
                    .addComponent(CB_SET_PARAM_ON_START))
                .addGap(0, 14, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_LIGHTS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_WALK_ASSIST)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_BRAKE_SENSOR)
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RB_ADC_OPTION_DIS)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(RB_THROTTLE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(RB_TEMP_LIMIT))
                .addGap(20, 20, 20)
                .addComponent(CB_STREET_MODE_ON_START)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_CAD_SENSOR_ADV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_TOR_SENSOR_ADV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_ODO_COMPENSATION)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_SET_PARAM_ON_START)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_AUTO_DISPLAY_DATA)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_MAX_SPEED_DISPLAY)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(40, 40, 40)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Basic settings", jPanel1);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Power assist mode");

        jLabel26.setText("Assist level 1 - ECO");

        TF_POWER_ASS_1.setText("70");
        TF_POWER_ASS_1.setToolTipText("<html>% Human power<br>\nMax value 500\n</html>");

        jLabel27.setText("Assist level 2 - TOUR");

        TF_POWER_ASS_2.setText("120");
        TF_POWER_ASS_2.setToolTipText("<html>% Human power<br>\nMax value 500\n</html>");

        jLabel28.setText("Assist level 3 - SPORT");

        TF_POWER_ASS_3.setText("210");
        TF_POWER_ASS_3.setToolTipText("<html>% Human power<br>\nMax value 500\n</html>");

        TF_POWER_ASS_4.setText("300");
        TF_POWER_ASS_4.setToolTipText("<html>% Human power<br>\nMax value 500\n</html>");

        jLabel29.setText("Assist level 4 -TURBO");

        buttonGroup5.add(RB_POWER_ON_START);

        jLabel58.setText("Enable on startup");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_POWER_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_POWER_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_POWER_ASS_4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel58))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TF_POWER_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(RB_POWER_ON_START)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(TF_POWER_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(TF_POWER_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(TF_POWER_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(TF_POWER_ASS_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(RB_POWER_ON_START)
                    .addComponent(jLabel58))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel43.setText("Torque assist mode");

        jLabel44.setText("Assist level 1 - ECO");

        TF_TORQUE_ASS_1.setText("70");
        TF_TORQUE_ASS_1.setToolTipText("Max value 254");

        jLabel45.setText("Assist level 2 - TOUR");

        TF_TORQUE_ASS_2.setText("100");
        TF_TORQUE_ASS_2.setToolTipText("Max value 254");

        jLabel46.setText("Assist level 3 - SPORT");

        TF_TORQUE_ASS_3.setText("130");
        TF_TORQUE_ASS_3.setToolTipText("Max value 254");

        TF_TORQUE_ASS_4.setText("160");
        TF_TORQUE_ASS_4.setToolTipText("Max value 254");

        jLabel47.setText("Assist level 4 -TURBO");

        buttonGroup5.add(RB_TORQUE_ON_START);

        jLabel59.setText("Enable on startup");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_TORQUE_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_TORQUE_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addComponent(TF_TORQUE_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47)
                            .addComponent(jLabel59))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RB_TORQUE_ON_START)
                            .addComponent(TF_TORQUE_ASS_4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(TF_TORQUE_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(TF_TORQUE_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(TF_TORQUE_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(TF_TORQUE_ASS_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(RB_TORQUE_ON_START)
                    .addComponent(jLabel59))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setText("Cadence assist mode");

        jLabel49.setText("Assist level 1 - ECO");

        TF_CADENCE_ASS_1.setText("70");
        TF_CADENCE_ASS_1.setToolTipText("Max value 254");

        jLabel50.setText("Assist level 2 - TOUR");

        TF_CADENCE_ASS_2.setText("100");
        TF_CADENCE_ASS_2.setToolTipText("Max value 254");

        jLabel51.setText("Assist level 3 - SPORT");

        TF_CADENCE_ASS_3.setText("130");
        TF_CADENCE_ASS_3.setToolTipText("Max value 254");

        TF_CADENCE_ASS_4.setText("160");
        TF_CADENCE_ASS_4.setToolTipText("Max value 254");

        jLabel52.setText("Assist level 4 -TURBO");

        buttonGroup5.add(RB_CADENCE_ON_START);

        jLabel60.setText("Enable on startup");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_CADENCE_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_CADENCE_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel51)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addComponent(TF_CADENCE_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel52)
                            .addComponent(jLabel60))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RB_CADENCE_ON_START)
                            .addComponent(TF_CADENCE_ASS_4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(TF_CADENCE_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(TF_CADENCE_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(TF_CADENCE_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(TF_CADENCE_ASS_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(RB_CADENCE_ON_START)
                    .addComponent(jLabel60))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel53.setText("eMTB assist mode");

        jLabel54.setText("Assist level 1 - ECO");

        TF_EMTB_ASS_1.setText("6");
        TF_EMTB_ASS_1.setToolTipText("<html>Sensitivity<br>\nbetween 0 to 20\n</html>");

        jLabel55.setText("Assist level 2 - TOUR");

        TF_EMTB_ASS_2.setText("9");
        TF_EMTB_ASS_2.setToolTipText("<html>Sensitivity<br>\nbetween 0 to 20\n</html>");

        jLabel56.setText("Assist level 3 - SPORT");

        TF_EMTB_ASS_3.setText("12");
        TF_EMTB_ASS_3.setToolTipText("<html>Sensitivity<br>\nbetween 0 to 20\n</html>");

        TF_EMTB_ASS_4.setText("15");
        TF_EMTB_ASS_4.setToolTipText("<html>Sensitivity<br>\nbetween 0 to 20\n</html>");

        jLabel57.setText("Assist level 4 -TURBO");

        buttonGroup5.add(RB_EMTB_ON_START);

        jLabel61.setText("Enable on startup");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_EMTB_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_EMTB_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel56)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(TF_EMTB_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel57)
                            .addComponent(jLabel61))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RB_EMTB_ON_START)
                            .addComponent(TF_EMTB_ASS_4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(TF_EMTB_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(TF_EMTB_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(TF_EMTB_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(TF_EMTB_ASS_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(RB_EMTB_ON_START, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel61))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel62.setText("Walk assist mode");

        jLabel63.setText("Assist level 1 - ECO");

        TF_WALK_ASS_1.setText("30");
        TF_WALK_ASS_1.setToolTipText("Max value 80");
        TF_WALK_ASS_1.setEnabled(CB_WALK_ASSIST.isSelected());

        jLabel64.setText("Assist level 2 - TOUR");

        TF_WALK_ASS_2.setText("40");
        TF_WALK_ASS_2.setToolTipText("Max value 80");
        TF_WALK_ASS_2.setEnabled(CB_WALK_ASSIST.isSelected());

        jLabel65.setText("Assist level 3 - SPORT");

        TF_WALK_ASS_3.setText("50");
        TF_WALK_ASS_3.setToolTipText("Max value 80");
        TF_WALK_ASS_3.setEnabled(CB_WALK_ASSIST.isSelected());

        TF_WALK_ASS_4.setText("60");
        TF_WALK_ASS_4.setToolTipText("Max value 80");
        TF_WALK_ASS_4.setEnabled(CB_WALK_ASSIST.isSelected());

        jLabel66.setText("Assist level 4 -TURBO");

        jLabel67.setText("Walk assist speed limit");

        TF_WALK_ASS_SPEED.setText("6");
        TF_WALK_ASS_SPEED.setToolTipText("Max value in EU 6 km/h");
        TF_WALK_ASS_SPEED.setEnabled(CB_WALK_ASSIST.isSelected());

        TF_WALK_ASS_TIME.setText("60");
        TF_WALK_ASS_TIME.setToolTipText("Max value 255 (0.1 s)\n\n");
        TF_WALK_ASS_TIME.setEnabled(CB_WALK_TIME_ENA.isSelected() && CB_BRAKE_SENSOR.isSelected() && CB_WALK_ASSIST.isSelected());

        jLabel68.setText("Walk assist deb. time");

        CB_WALK_TIME_ENA.setText("Walk assist debounce time");
        CB_WALK_TIME_ENA.setToolTipText("Only with brake sensors enabled");
        CB_WALK_TIME_ENA.setEnabled(CB_BRAKE_SENSOR.isSelected() && CB_WALK_ASSIST.isSelected());
        CB_WALK_TIME_ENA.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CB_WALK_TIME_ENAStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(CB_WALK_TIME_ENA)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel63)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_WALK_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel64)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_WALK_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel65)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_WALK_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_WALK_ASS_4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel67)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(TF_WALK_ASS_SPEED, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel68)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_WALK_ASS_TIME, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(TF_WALK_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(TF_WALK_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(TF_WALK_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(TF_WALK_ASS_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(TF_WALK_ASS_SPEED, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(TF_WALK_ASS_TIME, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CB_WALK_TIME_ENA)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel69.setText("Cruise mode");

        jLabel70.setText("Speed level 1 - ECO");

        TF_CRUISE_ASS_1.setText("15");
        TF_CRUISE_ASS_1.setToolTipText("Max value in EU 25 km/h");

        jLabel71.setText("Speed level 2 - TOUR");

        TF_CRUISE_ASS_2.setText("18");
        TF_CRUISE_ASS_2.setToolTipText("Max value in EU 25 km/h");

        jLabel72.setText("Speed level 3 - SPORT");

        TF_CRUISE_ASS_3.setText("21");
        TF_CRUISE_ASS_3.setToolTipText("Max value in EU 25 km/h");

        TF_CRUISE_ASS_4.setText("24");
        TF_CRUISE_ASS_4.setToolTipText("Max value in EU 25 km/h");

        jLabel73.setText("Speed level 4 -TURBO");

        jLabel74.setText("Speed cruise enabled");

        TF_CRUISE_SPEED_ENA.setText("10");
        TF_CRUISE_SPEED_ENA.setToolTipText("Min speed to enable cruise (km/h)");

        CB_CRUISE_WHITOUT_PED.setText("Cruise without pedaling");
        CB_CRUISE_WHITOUT_PED.setToolTipText("Only with brake sensors enabled");
        CB_CRUISE_WHITOUT_PED.setEnabled(CB_BRAKE_SENSOR.isSelected());

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(CB_CRUISE_WHITOUT_PED)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel70)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_CRUISE_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel71)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_CRUISE_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel72)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_CRUISE_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel73)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_CRUISE_ASS_4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel74)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_CRUISE_SPEED_ENA, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(TF_CRUISE_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(TF_CRUISE_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel72)
                    .addComponent(TF_CRUISE_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel73)
                    .addComponent(TF_CRUISE_ASS_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel74)
                    .addComponent(TF_CRUISE_SPEED_ENA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CB_CRUISE_WHITOUT_PED)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel76.setText("Lights configuration");

        jLabelLights0.setText("Lights mode on startup");
        jLabelLights0.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        TF_LIGHT_MODE_ON_START.setText("0");
        TF_LIGHT_MODE_ON_START.setToolTipText("<html>With lights button ON<br>\n0 - lights ON<br>\n1 - lights FLASHING<br>\n2 - lights ON and BRAKE-FLASHING when braking<br>\n3 - lights FLASHING and ON when braking<br>\n4 - lights FLASHING and BRAKE-FLASHING when braking<br>\n5 - lights ON and ON when braking, even with the light button OFF<br>\n6 - lights ON and BRAKE-FLASHING when braking, even with the light button OFF<br>\n7 - lights FLASHING and ON when braking, even with the light button OFF<br>\n8 - lights FLASHING and BRAKE-FLASHING when braking, even with the light button OFF\n</html>");
        TF_LIGHT_MODE_ON_START.setEnabled(CB_LIGHTS.isSelected());
        TF_LIGHT_MODE_ON_START.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_LIGHT_MODE_ON_STARTKeyReleased(evt);
            }
        });

        jLabelLights1.setText("Lights mode 1");
        jLabelLights1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        TF_LIGHT_MODE_1.setText("1");
        TF_LIGHT_MODE_1.setToolTipText("<html>With lights button ON<br>\n0 - lights ON<br>\n1 - lights FLASHING<br>\n2 - lights ON and BRAKE-FLASHING when braking<br>\n3 - lights FLASHING and ON when braking<br>\n4 - lights FLASHING and BRAKE-FLASHING when braking<br>\n5 - lights ON and ON when braking, even with the light button OFF<br>\n6 - lights ON and BRAKE-FLASHING when braking, even with the light button OFF<br>\n7 - lights FLASHING and ON when braking, even with the light button OFF<br>\n8 - lights FLASHING and BRAKE-FLASHING when braking, even with the light button OFF\n</html>");
        TF_LIGHT_MODE_1.setEnabled(CB_LIGHTS.isSelected());
        TF_LIGHT_MODE_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_LIGHT_MODE_1KeyReleased(evt);
            }
        });

        jLabelLights2.setText("Lights mode 2");
        jLabelLights2.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        TF_LIGHT_MODE_2.setText("6");
        TF_LIGHT_MODE_2.setToolTipText("<html>With lights button ON<br>\n0 - lights ON<br>\n1 - lights FLASHING<br>\n2 - lights ON and BRAKE-FLASHING when braking<br>\n3 - lights FLASHING and ON when braking<br>\n4 - lights FLASHING and BRAKE-FLASHING when braking<br>\n5 - lights ON and ON when braking, even with the light button OFF<br>\n6 - lights ON and BRAKE-FLASHING when braking, even with the light button OFF<br>\n7 - lights FLASHING and ON when braking, even with the light button OFF<br>\n8 - lights FLASHING and BRAKE-FLASHING when braking, even with the light button OFF\n</html>");
        TF_LIGHT_MODE_2.setEnabled(CB_LIGHTS.isSelected());
        TF_LIGHT_MODE_2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_LIGHT_MODE_2KeyReleased(evt);
            }
        });

        TF_LIGHT_MODE_3.setText("7");
        TF_LIGHT_MODE_3.setToolTipText("<html>With lights button ON<br>\n0 - lights ON<br>\n1 - lights FLASHING<br>\n2 - lights ON and BRAKE-FLASHING when braking<br>\n3 - lights FLASHING and ON when braking<br>\n4 - lights FLASHING and BRAKE-FLASHING when braking<br>\n5 - lights ON and ON when braking, even with the light button OFF<br>\n6 - lights ON and BRAKE-FLASHING when braking, even with the light button OFF<br>\n7 - lights FLASHING and ON when braking, even with the light button OFF<br>\n8 - lights FLASHING and BRAKE-FLASHING when braking, even with the light button OFF\n</html>");
        TF_LIGHT_MODE_3.setEnabled(CB_LIGHTS.isSelected());
        TF_LIGHT_MODE_3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_LIGHT_MODE_3KeyReleased(evt);
            }
        });

        jLabelLights3.setText("Lights mode 3");
        jLabelLights3.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabelLights0, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TF_LIGHT_MODE_ON_START, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelLights1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelLights2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelLights3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TF_LIGHT_MODE_3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_LIGHT_MODE_2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_LIGHT_MODE_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLights0, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TF_LIGHT_MODE_ON_START, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLights1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TF_LIGHT_MODE_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLights2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TF_LIGHT_MODE_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLights3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TF_LIGHT_MODE_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Street mode");

        jLabel32.setText("Street speed limit (km/h)");

        TF_STREET_SPEED_LIM.setText("25");
        TF_STREET_SPEED_LIM.setToolTipText("Max value in EU 25 km/h");

        jLabel34.setText("Street power limit (W)");

        TF_STREET_POWER_LIM.setText("500");
        TF_STREET_POWER_LIM.setToolTipText("<html>Max nominal value in EU 250 W<br>\nMax peak value approx. 500 W\n</html>");
        TF_STREET_POWER_LIM.setEnabled(CB_STREET_POWER_LIM.isSelected());

        CB_STREET_POWER_LIM.setText("Street power limit enebled");
        CB_STREET_POWER_LIM.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CB_STREET_POWER_LIMStateChanged(evt);
            }
        });

        CB_STREET_THROTTLE.setText("Throttle on street mode");

        CB_STREET_CRUISE.setText("Cruise on street mode");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_STREET_POWER_LIM, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(TF_STREET_SPEED_LIM, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CB_STREET_POWER_LIM)
                            .addComponent(CB_STREET_THROTTLE)
                            .addComponent(CB_STREET_CRUISE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(TF_STREET_SPEED_LIM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(TF_STREET_POWER_LIM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_STREET_POWER_LIM)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_STREET_THROTTLE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_STREET_CRUISE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Assistance settings", jPanel4);

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setText("Battery cells settings");

        jLabel36.setText("Cell voltage full (V)");

        TF_BAT_CELL_FULL.setText("3.95");
        TF_BAT_CELL_FULL.setToolTipText("Value 3.90 to 4.00");

        jLabel37.setText("Overvoltage (V)");

        TF_BAT_CELL_OVER.setText("4.35");
        TF_BAT_CELL_OVER.setToolTipText("Value 4.25 to 4.35");

        jLabel38.setText("Reset SOC percentage (V)");

        TF_BAT_CELL_SOC.setText("4.05");
        TF_BAT_CELL_SOC.setToolTipText("Value 4.00 to 4.10");

        jLabel40.setText("Cell voltage 3/4 (V)");

        TF_BAT_CELL_3_4.setText("3.70");
        TF_BAT_CELL_3_4.setToolTipText("Value empty to full");
        TF_BAT_CELL_3_4.setEnabled(!(RB_VLCD5.isSelected()));

        jLabel41.setText("Cell voltage 2/4 (V)");

        TF_BAT_CELL_2_4.setText("3.45");
        TF_BAT_CELL_2_4.setToolTipText("Value empty to full");
        TF_BAT_CELL_2_4.setEnabled(!(RB_VLCD5.isSelected()));

        jLabel42.setText("Cell voltage 1/4 (V)");

        TF_BAT_CELL_1_4.setText("3.25");
        TF_BAT_CELL_1_4.setToolTipText("Value empty to full");
        TF_BAT_CELL_1_4.setEnabled(!(RB_VLCD5.isSelected()));

        jLabel75.setText("Cell voltage 5/6 (V)");

        TF_BAT_CELL_5_6.setText("3.85");
        TF_BAT_CELL_5_6.setToolTipText("Value empty to full");
        TF_BAT_CELL_5_6.setEnabled(RB_VLCD5.isSelected());

        jLabel81.setText("Cell voltage 4/6 (V)");

        TF_BAT_CELL_4_6.setText("3.70");
        TF_BAT_CELL_4_6.setToolTipText("Value empty to full");
        TF_BAT_CELL_4_6.setEnabled(RB_VLCD5.isSelected());

        jLabel82.setText("Cell voltage 3/6 (V)");

        TF_BAT_CELL_3_6.setText("3.55");
        TF_BAT_CELL_3_6.setToolTipText("Value empty to full");
        TF_BAT_CELL_3_6.setEnabled(RB_VLCD5.isSelected());

        TF_BAT_CELL_2_6.setText("3.40");
        TF_BAT_CELL_2_6.setToolTipText("Value empty to full");
        TF_BAT_CELL_2_6.setEnabled(RB_VLCD5.isSelected());

        jLabel83.setText("Cell voltage 2/6 (V)");

        TF_BAT_CELL_1_6.setText("3.25");
        TF_BAT_CELL_1_6.setToolTipText("Value empty to full");
        TF_BAT_CELL_1_6.setEnabled(RB_VLCD5.isSelected());

        jLabel84.setText("Cell voltage 1/6 (V)");

        TF_BAT_CELL_EMPTY.setText("2.90");
        TF_BAT_CELL_EMPTY.setToolTipText("<html>Indicative value 2.90<br>\nIt depends on the<br>\ncharacteristics of the cells\n</html>");

        jLabel85.setText("Cell voltage empty (V)");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(64, 64, 64))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel83, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel85, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TF_BAT_CELL_OVER, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(TF_BAT_CELL_3_4, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(TF_BAT_CELL_2_4, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(TF_BAT_CELL_1_4, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(TF_BAT_CELL_5_6, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(TF_BAT_CELL_SOC, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(TF_BAT_CELL_FULL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(TF_BAT_CELL_4_6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TF_BAT_CELL_3_6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TF_BAT_CELL_2_6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TF_BAT_CELL_1_6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TF_BAT_CELL_EMPTY, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BAT_CELL_OVER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BAT_CELL_SOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BAT_CELL_FULL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BAT_CELL_3_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BAT_CELL_2_4)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BAT_CELL_1_4)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BAT_CELL_5_6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel75))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BAT_CELL_4_6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BAT_CELL_3_6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BAT_CELL_2_6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BAT_CELL_1_6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BAT_CELL_EMPTY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        jLabel86.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel86.setText("Display advanced settings");

        jLabel89.setText("Time to displayed data 1 (0.1 s)");

        TF_DELAY_DATA_1.setText("50");
        TF_DELAY_DATA_1.setToolTipText("Max value 255 (0.1 sec)");

        jLabelData1.setText("Data 1");

        TF_DATA_1.setText("1");
        TF_DATA_1.setToolTipText("<html>0 - motor temperature (°C)<br>\n1 - battery SOC remaining (%)<br>\n2 - battery voltage (V)<br>\n3 - battery current (A)<br>\n4 - motor power (Watt/10)<br>\n5 - adc torque sensor (8 bit)<br>\n6 - adc torque sensor (10 bit)<br>\n7 - pedal cadence (rpm)<br>\n8 - human power(W/10)<br>\n9 - cadence sensor adv.(%)<br>\n10 - pedal weight (kg)<br>\n11 - pedal torque adc conver.<br>\n12 - pedal torque adc range\n</html>");
        TF_DATA_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_DATA_1KeyReleased(evt);
            }
        });

        TF_DATA_2.setText("2");
        TF_DATA_2.setToolTipText("<html>0 - motor temperature (°C)<br>\n1 - battery SOC remaining (%)<br>\n2 - battery voltage (V)<br>\n3 - battery current (A)<br>\n4 - motor power (Watt/10)<br>\n5 - adc torque sensor (8 bit)<br>\n6 - adc torque sensor (10 bit)<br>\n7 - pedal cadence (rpm)<br>\n8 - human power(W/10)<br>\n9 - cadence sensor adv.(%)<br>\n10 - pedal weight (kg)<br>\n11 - pedal torque adc conver.<br>\n12 - pedal torque adc range\n</html>");
        TF_DATA_2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_DATA_2KeyReleased(evt);
            }
        });

        jLabelData2.setText("Data 2");

        TF_DATA_3.setText("5");
        TF_DATA_3.setToolTipText("<html>0 - motor temperature (°C)<br>\n1 - battery SOC remaining (%)<br>\n2 - battery voltage (V)<br>\n3 - battery current (A)<br>\n4 - motor power (Watt/10)<br>\n5 - adc torque sensor (8 bit)<br>\n6 - adc torque sensor (10 bit)<br>\n7 - pedal cadence (rpm)<br>\n8 - human power(W/10)<br>\n9 - cadence sensor adv.(%)<br>\n10 - pedal weight (kg)<br>\n11 - pedal torque adc conver.<br>\n12 - pedal torque adc range\n</html>");
        TF_DATA_3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_DATA_3KeyReleased(evt);
            }
        });

        jLabelData3.setText("Data 3");

        jLabelData4.setText("Data 4");

        TF_DATA_4.setText("4");
        TF_DATA_4.setToolTipText("<html>0 - motor temperature (°C)<br>\n1 - battery SOC remaining (%)<br>\n2 - battery voltage (V)<br>\n3 - battery current (A)<br>\n4 - motor power (Watt/10)<br>\n5 - adc torque sensor (8 bit)<br>\n6 - adc torque sensor (10 bit)<br>\n7 - pedal cadence (rpm)<br>\n8 - human power(W/10)<br>\n9 - cadence sensor adv.(%)<br>\n10 - pedal weight (kg)<br>\n11 - pedal torque adc conver.<br>\n12 - pedal torque adc range\n</html>");
        TF_DATA_4.setEnabled(CB_DISPLAY_DOUBLE_DATA.isSelected());
        TF_DATA_4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_DATA_4KeyReleased(evt);
            }
        });

        TF_DATA_5.setText("7");
        TF_DATA_5.setToolTipText("<html>0 - motor temperature (°C)<br>\n1 - battery SOC remaining (%)<br>\n2 - battery voltage (V)<br>\n3 - battery current (A)<br>\n4 - motor power (Watt/10)<br>\n5 - adc torque sensor (8 bit)<br>\n6 - adc torque sensor (10 bit)<br>\n7 - pedal cadence (rpm)<br>\n8 - human power(W/10)<br>\n9 - cadence sensor adv.(%)<br>\n10 - pedal weight (kg)<br>\n11 - pedal torque adc conver.<br>\n12 - pedal torque adc range\n</html>");
        TF_DATA_5.setEnabled(CB_DISPLAY_DOUBLE_DATA.isSelected());
        TF_DATA_5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_DATA_5KeyReleased(evt);
            }
        });

        jLabelData5.setText("Data 5");

        jLabelData6.setText("Data 6");

        TF_DATA_6.setText("0");
        TF_DATA_6.setToolTipText("<html>0 - motor temperature (°C)<br>\n1 - battery SOC remaining (%)<br>\n2 - battery voltage (V)<br>\n3 - battery current (A)<br>\n4 - motor power (Watt/10)<br>\n5 - adc torque sensor (8 bit)<br>\n6 - adc torque sensor (10 bit)<br>\n7 - pedal cadence (rpm)<br>\n8 - human power(W/10)<br>\n9 - cadence sensor adv.(%)<br>\n10 - pedal weight (kg)<br>\n11 - pedal torque adc conver.<br>\n12 - pedal torque adc range\n</html>");
        TF_DATA_6.setEnabled(CB_DISPLAY_DOUBLE_DATA.isSelected());
        TF_DATA_6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_DATA_6KeyReleased(evt);
            }
        });

        jLabel96.setText("Time to displayed data 2 (0.1 s)");

        TF_DELAY_DATA_2.setText("50");
        TF_DELAY_DATA_2.setToolTipText("Max value 255 (0.1 sec)");

        jLabel97.setText("Time to displayed data 3 (0.1 s)");

        TF_DELAY_DATA_3.setText("50");
        TF_DELAY_DATA_3.setToolTipText("Max value 255 (0.1 sec)");

        jLabel98.setText("Time to displayed data 4 (0.1 s)");

        TF_DELAY_DATA_4.setText("50");
        TF_DELAY_DATA_4.setToolTipText("Max value 255 (0.1 sec)");
        TF_DELAY_DATA_4.setEnabled(CB_DISPLAY_DOUBLE_DATA.isSelected());

        jLabel99.setText("Time to displayed data 5 (0.1 s)");

        TF_DELAY_DATA_5.setText("50");
        TF_DELAY_DATA_5.setToolTipText("Max value 255 (0.1 sec)");
        TF_DELAY_DATA_5.setEnabled(CB_DISPLAY_DOUBLE_DATA.isSelected());

        jLabel100.setText("Time to displayed data 6 (0.1 s)");

        TF_DELAY_DATA_6.setText("50");
        TF_DELAY_DATA_6.setToolTipText("Max value 255 (0.1 sec)");
        TF_DELAY_DATA_6.setEnabled(CB_DISPLAY_DOUBLE_DATA.isSelected());

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel89, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(TF_DELAY_DATA_1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabelData1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(TF_DATA_1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel86)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabelData2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(TF_DATA_2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabelData3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(TF_DATA_3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabelData4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(TF_DATA_4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabelData5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(TF_DATA_5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabelData6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(TF_DATA_6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel96, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(TF_DELAY_DATA_2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel97, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(TF_DELAY_DATA_3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel98, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(TF_DELAY_DATA_4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel99, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(TF_DELAY_DATA_5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel100, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(TF_DELAY_DATA_6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel86)
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_DATA_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelData1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_DATA_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelData2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_DATA_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelData3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_DATA_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelData4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_DATA_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelData5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_DATA_6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelData6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_DELAY_DATA_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel89))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_DELAY_DATA_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel96))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_DELAY_DATA_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel97))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_DELAY_DATA_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel98))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_DELAY_DATA_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel99))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_DELAY_DATA_6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel100))
                .addContainerGap())
        );

        jLabel101.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel101.setText("Other function settings");

        CB_RET_DISPLAY_MODE.setText("Return to default display mode");
        CB_RET_DISPLAY_MODE.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CB_RET_DISPLAY_MODEStateChanged(evt);
            }
        });

        jLabel102.setText("ADC throttle min value");

        TF_ADC_THROTTLE_MIN.setText("47");
        TF_ADC_THROTTLE_MIN.setToolTipText("Value 40 to 50");
        TF_ADC_THROTTLE_MIN.setEnabled(RB_THROTTLE.isSelected());

        TF_ADC_THROTTLE_MAX.setText("176");
        TF_ADC_THROTTLE_MAX.setToolTipText("Value 170 to 180");
        TF_ADC_THROTTLE_MAX.setEnabled(RB_THROTTLE.isSelected());

        jLabel103.setText("ADC throttle max value");

        CB_TEMP_ERR_MIN_LIM.setText("Temperature error with min limit");
        CB_TEMP_ERR_MIN_LIM.setEnabled(RB_TEMP_LIMIT.isSelected());

        jLabel104.setText("Motor temperature min limit");

        TF_TEMP_MIN_LIM.setText("65");
        TF_TEMP_MIN_LIM.setToolTipText("Max value 75 (°C)");
        TF_TEMP_MIN_LIM.setEnabled(RB_TEMP_LIMIT.isSelected());

        jLabel105.setText("Motor temperature max limit");

        TF_TEMP_MAX_LIM.setText("80");
        TF_TEMP_MAX_LIM.setToolTipText("Max value 85 (°C)");
        TF_TEMP_MAX_LIM.setEnabled(RB_TEMP_LIMIT.isSelected());

        jLabel106.setText("Motor blocked error - threshold time");

        TF_MOTOR_BLOCK_TIME.setText("2");
        TF_MOTOR_BLOCK_TIME.setToolTipText("Value 1 to 10 (0.1 s)");

        jLabel107.setText("Motor blocked error - threshold current");

        TF_MOTOR_BLOCK_CURR.setText("30");
        TF_MOTOR_BLOCK_CURR.setToolTipText("Value 1 to 5 (0.1 A)");

        jLabel108.setText("Motor blocked error - threshold ERPS");

        TF_MOTOR_BLOCK_ERPS.setText("20");
        TF_MOTOR_BLOCK_ERPS.setToolTipText("Value 10 to 30 (ERPS)");

        jLabel88.setText("Time to return default display mode (s)");

        TF_DELAY_MENU.setText("50");
        TF_DELAY_MENU.setToolTipText("Max value 60 (0.1 s)");

        TF_RET_DISPLAY_MODE.setText("30");
        TF_RET_DISPLAY_MODE.setToolTipText("Max value 255 (s)");
        TF_RET_DISPLAY_MODE.setEnabled(CB_RET_DISPLAY_MODE.isSelected());

        jLabel87.setText("Time to menu items (0.1 s)");

        CB_DISPLAY_DOUBLE_DATA.setText("Display second serie data (4 to 6)");
        CB_DISPLAY_DOUBLE_DATA.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CB_DISPLAY_DOUBLE_DATAStateChanged(evt);
            }
        });

        jLabel90.setText("Number of data auto displayed");

        TF_NUM_DATA_AUTO_DISPLAY.setText("3");
        TF_NUM_DATA_AUTO_DISPLAY.setToolTipText("<html>Max value<br>\n3 - Display second serie data DISABLED<br>\n6 - Display second serie data ENABLED\n</html>");
        TF_NUM_DATA_AUTO_DISPLAY.setEnabled(CB_AUTO_DISPLAY_DATA.isSelected());

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(CB_DISPLAY_DOUBLE_DATA)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel19Layout.createSequentialGroup()
                                        .addComponent(jLabel87, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(8, 8, 8))
                                    .addGroup(jPanel19Layout.createSequentialGroup()
                                        .addComponent(jLabel88, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TF_RET_DISPLAY_MODE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_DELAY_MENU, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(8, 8, 8)
                                .addComponent(TF_NUM_DATA_AUTO_DISPLAY, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CB_TEMP_ERR_MIN_LIM)
                                    .addComponent(jLabel101)
                                    .addComponent(CB_RET_DISPLAY_MODE))
                                .addGap(65, 65, 65))
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel19Layout.createSequentialGroup()
                                    .addComponent(jLabel102, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(8, 8, 8)
                                    .addComponent(TF_ADC_THROTTLE_MIN, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel19Layout.createSequentialGroup()
                                    .addComponent(jLabel103, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(8, 8, 8)
                                    .addComponent(TF_ADC_THROTTLE_MAX, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                    .addComponent(jLabel104, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(TF_TEMP_MIN_LIM, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel19Layout.createSequentialGroup()
                                    .addComponent(jLabel105, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(TF_TEMP_MAX_LIM, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel19Layout.createSequentialGroup()
                                    .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(TF_MOTOR_BLOCK_TIME, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel19Layout.createSequentialGroup()
                                    .addComponent(jLabel107, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(TF_MOTOR_BLOCK_CURR, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel19Layout.createSequentialGroup()
                                    .addComponent(jLabel108, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(TF_MOTOR_BLOCK_ERPS, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18))))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_NUM_DATA_AUTO_DISPLAY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel90))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_DELAY_MENU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel87))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_RET_DISPLAY_MODE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel88))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CB_RET_DISPLAY_MODE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CB_DISPLAY_DOUBLE_DATA)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jLabel101)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_ADC_THROTTLE_MIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel102))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_ADC_THROTTLE_MAX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel103))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_TEMP_ERR_MIN_LIM)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_TEMP_MIN_LIM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel104))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_TEMP_MAX_LIM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel105))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_MOTOR_BLOCK_TIME, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel106))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_MOTOR_BLOCK_CURR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel107))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_MOTOR_BLOCK_ERPS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel108))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Advanced settings", jPanel8);

        label1.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        label1.setText("TSDZ2 Parameter Configurator");

        expSet.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(expSet);

        jLabel1.setText("Experimental Settings");

        provSet.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(provSet);

        jLabel2.setText("Proven Settings");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("Compile & Flash");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 856, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane2)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("MotorConfiguration");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CB_RET_DISPLAY_MODEStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_RET_DISPLAY_MODEStateChanged
           TF_RET_DISPLAY_MODE.setEnabled(CB_RET_DISPLAY_MODE.isSelected());
        // TODO add your handling code here:
    }//GEN-LAST:event_CB_RET_DISPLAY_MODEStateChanged

    private void CB_DISPLAY_DOUBLE_DATAStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_DISPLAY_DOUBLE_DATAStateChanged
        TF_DATA_4.setEnabled(CB_DISPLAY_DOUBLE_DATA.isSelected());
        TF_DATA_5.setEnabled(CB_DISPLAY_DOUBLE_DATA.isSelected());
        TF_DATA_6.setEnabled(CB_DISPLAY_DOUBLE_DATA.isSelected());
        TF_DELAY_DATA_4.setEnabled(CB_DISPLAY_DOUBLE_DATA.isSelected());
        TF_DELAY_DATA_5.setEnabled(CB_DISPLAY_DOUBLE_DATA.isSelected());
        TF_DELAY_DATA_6.setEnabled(CB_DISPLAY_DOUBLE_DATA.isSelected());
        // TODO add your handling code here:
    }//GEN-LAST:event_CB_DISPLAY_DOUBLE_DATAStateChanged

    private void CB_WALK_TIME_ENAStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_WALK_TIME_ENAStateChanged
        TF_WALK_ASS_TIME.setEnabled(CB_WALK_TIME_ENA.isSelected() && CB_BRAKE_SENSOR.isSelected() && CB_WALK_ASSIST.isSelected());
        // TODO add your handling code here:
    }//GEN-LAST:event_CB_WALK_TIME_ENAStateChanged

    private void CB_BRAKE_SENSORStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_BRAKE_SENSORStateChanged
        TF_WALK_ASS_TIME.setEnabled(CB_WALK_TIME_ENA.isSelected() && CB_BRAKE_SENSOR.isSelected() && CB_WALK_ASSIST.isSelected());
        CB_CRUISE_WHITOUT_PED.setEnabled(CB_BRAKE_SENSOR.isSelected());
        CB_WALK_TIME_ENA.setEnabled(CB_BRAKE_SENSOR.isSelected() && CB_WALK_ASSIST.isSelected());
        // TODO add your handling code here:
    }//GEN-LAST:event_CB_BRAKE_SENSORStateChanged

    private void RB_VLCD5StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RB_VLCD5StateChanged
        TF_BAT_CELL_5_6.setEnabled(RB_VLCD5.isSelected());
        TF_BAT_CELL_4_6.setEnabled(RB_VLCD5.isSelected());
        TF_BAT_CELL_3_6.setEnabled(RB_VLCD5.isSelected());
        TF_BAT_CELL_2_6.setEnabled(RB_VLCD5.isSelected());
        TF_BAT_CELL_1_6.setEnabled(RB_VLCD5.isSelected());
        TF_BAT_CELL_3_4.setEnabled(!(RB_VLCD5.isSelected()));
        TF_BAT_CELL_2_4.setEnabled(!(RB_VLCD5.isSelected()));
        TF_BAT_CELL_1_4.setEnabled(!(RB_VLCD5.isSelected()));
        // TODO add your handling code here:
    }//GEN-LAST:event_RB_VLCD5StateChanged

    private void RB_VLCD6StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RB_VLCD6StateChanged
        TF_BAT_CELL_5_6.setEnabled(RB_VLCD5.isSelected());
        TF_BAT_CELL_4_6.setEnabled(RB_VLCD5.isSelected());
        TF_BAT_CELL_3_6.setEnabled(RB_VLCD5.isSelected());
        TF_BAT_CELL_2_6.setEnabled(RB_VLCD5.isSelected());
        TF_BAT_CELL_1_6.setEnabled(RB_VLCD5.isSelected());
        TF_BAT_CELL_3_4.setEnabled(!(RB_VLCD5.isSelected()));
        TF_BAT_CELL_2_4.setEnabled(!(RB_VLCD5.isSelected()));
        TF_BAT_CELL_1_4.setEnabled(!(RB_VLCD5.isSelected()));
        // TODO add your handling code here:
    }//GEN-LAST:event_RB_VLCD6StateChanged

    private void RB_XH18StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RB_XH18StateChanged
        TF_BAT_CELL_5_6.setEnabled(RB_VLCD5.isSelected());
        TF_BAT_CELL_4_6.setEnabled(RB_VLCD5.isSelected());
        TF_BAT_CELL_3_6.setEnabled(RB_VLCD5.isSelected());
        TF_BAT_CELL_2_6.setEnabled(RB_VLCD5.isSelected());
        TF_BAT_CELL_1_6.setEnabled(RB_VLCD5.isSelected());
        TF_BAT_CELL_3_4.setEnabled(!(RB_VLCD5.isSelected()));
        TF_BAT_CELL_2_4.setEnabled(!(RB_VLCD5.isSelected()));
        TF_BAT_CELL_1_4.setEnabled(!(RB_VLCD5.isSelected()));     
        // TODO add your handling code here:
    }//GEN-LAST:event_RB_XH18StateChanged

    private void RB_THROTTLEStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RB_THROTTLEStateChanged
        TF_ADC_THROTTLE_MIN.setEnabled(RB_THROTTLE.isSelected());
        TF_ADC_THROTTLE_MAX.setEnabled(RB_THROTTLE.isSelected());
        // TODO add your handling code here:
    }//GEN-LAST:event_RB_THROTTLEStateChanged

    private void RB_TEMP_LIMITStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RB_TEMP_LIMITStateChanged
        TF_TEMP_MIN_LIM.setEnabled(RB_TEMP_LIMIT.isSelected());
        TF_TEMP_MAX_LIM.setEnabled(RB_TEMP_LIMIT.isSelected());
        CB_TEMP_ERR_MIN_LIM.setEnabled(RB_TEMP_LIMIT.isSelected());
        // TODO add your handling code here:
    }//GEN-LAST:event_RB_TEMP_LIMITStateChanged

    private void RB_ADC_OPTION_DISStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RB_ADC_OPTION_DISStateChanged
        TF_ADC_THROTTLE_MIN.setEnabled(RB_THROTTLE.isSelected());
        TF_ADC_THROTTLE_MAX.setEnabled(RB_THROTTLE.isSelected());
        TF_TEMP_MIN_LIM.setEnabled(RB_TEMP_LIMIT.isSelected());
        TF_TEMP_MAX_LIM.setEnabled(RB_TEMP_LIMIT.isSelected());
        // TODO add your handling code here:
    }//GEN-LAST:event_RB_ADC_OPTION_DISStateChanged

    private void TF_DATA_1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_DATA_1KeyReleased
        try {
            int index = Integer.parseInt(TF_DATA_1.getText());
            if ((index >= 0)&&(index <= 12)) {
                jLabelData1.setText("Data 1 - " + displayDataArray[index]); }
            else {
                jLabelData1.setText("Data 1");	}
        }
        catch(NumberFormatException ex){
            jLabelData1.setText("Data 1");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_DATA_1KeyReleased

    private void TF_DATA_2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_DATA_2KeyReleased
        try {
        int index = Integer.parseInt(TF_DATA_2.getText());
        if ((index >= 0)&&(index <= 12)) {
            jLabelData2.setText("Data 2 - " + displayDataArray[index]); }
        else {
            jLabelData2.setText("Data 2");	}
        }
        catch(NumberFormatException ex){
            jLabelData1.setText("Data 2");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_DATA_2KeyReleased

    private void TF_DATA_3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_DATA_3KeyReleased
        try {
        int index = Integer.parseInt(TF_DATA_3.getText());
        if ((index >= 0)&&(index <= 12)) {
            jLabelData3.setText("Data 3 - " + displayDataArray[index]); }
        else {
            jLabelData3.setText("Data 3");	}
        }
        catch(NumberFormatException ex){
            jLabelData3.setText("Data 3");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_DATA_3KeyReleased

    private void TF_DATA_4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_DATA_4KeyReleased
        try {
        int index = Integer.parseInt(TF_DATA_4.getText());
        if ((index >= 0)&&(index <= 12)) {
            jLabelData4.setText("Data 4 - " + displayDataArray[index]); }
        else {
            jLabelData4.setText("Data 4");	}
        }
        catch(NumberFormatException ex){
            jLabelData4.setText("Data 4");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_DATA_4KeyReleased

    private void TF_DATA_5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_DATA_5KeyReleased
        try {
        int index = Integer.parseInt(TF_DATA_5.getText());
        if ((index >= 0)&&(index <= 12)) {
            jLabelData5.setText("Data 5 - " + displayDataArray[index]); }
        else {
            jLabelData5.setText("Data 5");	}
        }
        catch(NumberFormatException ex){
            jLabelData5.setText("Data 5");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_DATA_5KeyReleased

    private void TF_DATA_6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_DATA_6KeyReleased
        try {
        int index = Integer.parseInt(TF_DATA_6.getText());
        if ((index >= 0)&&(index <= 12)) {
            jLabelData6.setText("Data 6 - " + displayDataArray[index]); }
        else {
            jLabelData6.setText("Data 6");	}
        }
        catch(NumberFormatException ex){
            jLabelData6.setText("Data 6");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_DATA_6KeyReleased

    private void TF_LIGHT_MODE_ON_STARTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_LIGHT_MODE_ON_STARTKeyReleased
        try {
        int index = Integer.parseInt(TF_LIGHT_MODE_ON_START.getText());
        if ((index >= 0)&&(index <= 8)) {
            jLabelLights0.setText("<html>Lights mode on startup " + lightModeArray[Integer.parseInt(TF_LIGHT_MODE_ON_START.getText())] + "</html>"); }
        else {
            jLabelLights0.setText("Lights mode on startup");	}
        }
        catch(NumberFormatException ex){
            jLabelLights0.setText("Lights mode on startup");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_LIGHT_MODE_ON_STARTKeyReleased

    private void TF_LIGHT_MODE_1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_LIGHT_MODE_1KeyReleased
        try {
        int index = Integer.parseInt(TF_LIGHT_MODE_1.getText());
        if ((index >= 0)&&(index <= 8)) {
            jLabelLights1.setText("<html>Mode 1 - " + lightModeArray[Integer.parseInt(TF_LIGHT_MODE_1.getText())] + "</html>"); }
        else {
            jLabelLights1.setText("Mode 1");	}
        }
        catch(NumberFormatException ex){
            jLabelLights1.setText("Mode 1");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_LIGHT_MODE_1KeyReleased

    private void TF_LIGHT_MODE_2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_LIGHT_MODE_2KeyReleased
        try {
        int index = Integer.parseInt(TF_LIGHT_MODE_2.getText());
        if ((index >= 0)&&(index <= 8)) {
            jLabelLights2.setText("<html>Mode 2 - " + lightModeArray[Integer.parseInt(TF_LIGHT_MODE_2.getText())] + "</html>"); }
        else {
            jLabelLights2.setText("Mode 2");	}
        }
        catch(NumberFormatException ex){
            jLabelLights2.setText("Mode 2");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_LIGHT_MODE_2KeyReleased

    private void TF_LIGHT_MODE_3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_LIGHT_MODE_3KeyReleased
        try {
        int index = Integer.parseInt(TF_LIGHT_MODE_3.getText());
        if ((index >= 0)&&(index <= 8)) {
            jLabelLights3.setText("<html>Mode 3 - " + lightModeArray[Integer.parseInt(TF_LIGHT_MODE_3.getText())] + "</html>"); }
        else {
            jLabelLights3.setText("Mode 3");	}
        }
        catch(NumberFormatException ex){
            jLabelLights3.setText("Mode 3");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_LIGHT_MODE_3KeyReleased

    private void CB_STREET_POWER_LIMStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_STREET_POWER_LIMStateChanged
        TF_STREET_POWER_LIM.setEnabled(CB_STREET_POWER_LIM.isSelected());
        // TODO add your handling code here:
    }//GEN-LAST:event_CB_STREET_POWER_LIMStateChanged

    private void CB_ASS_WITHOUT_PEDStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_ASS_WITHOUT_PEDStateChanged
        TF_ASS_WITHOUT_PED_THRES.setEnabled(CB_ASS_WITHOUT_PED.isSelected());
        // TODO add your handling code here:
    }//GEN-LAST:event_CB_ASS_WITHOUT_PEDStateChanged

    private void CB_MAX_SPEED_DISPLAYStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_MAX_SPEED_DISPLAYStateChanged
        TF_MAX_SPEED.setEnabled(!CB_MAX_SPEED_DISPLAY.isSelected());
        // TODO add your handling code here:
    }//GEN-LAST:event_CB_MAX_SPEED_DISPLAYStateChanged

    private void CB_LIGHTSStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_LIGHTSStateChanged
        TF_LIGHT_MODE_ON_START.setEnabled(CB_LIGHTS.isSelected());
        TF_LIGHT_MODE_1.setEnabled(CB_LIGHTS.isSelected());
        TF_LIGHT_MODE_2.setEnabled(CB_LIGHTS.isSelected());
        TF_LIGHT_MODE_3.setEnabled(CB_LIGHTS.isSelected());
        // TODO add your handling code here:
    }//GEN-LAST:event_CB_LIGHTSStateChanged

    private void CB_WALK_ASSISTStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_WALK_ASSISTStateChanged
        TF_WALK_ASS_1.setEnabled(CB_WALK_ASSIST.isSelected());
        TF_WALK_ASS_2.setEnabled(CB_WALK_ASSIST.isSelected());
        TF_WALK_ASS_3.setEnabled(CB_WALK_ASSIST.isSelected());
        TF_WALK_ASS_4.setEnabled(CB_WALK_ASSIST.isSelected());
        TF_WALK_ASS_SPEED.setEnabled(CB_WALK_ASSIST.isSelected());
        TF_WALK_ASS_TIME.setEnabled(CB_WALK_TIME_ENA.isSelected() && CB_BRAKE_SENSOR.isSelected() && CB_WALK_ASSIST.isSelected());
        CB_WALK_TIME_ENA.setEnabled(CB_BRAKE_SENSOR.isSelected() && CB_WALK_ASSIST.isSelected());
        // TODO add your handling code here:
    }//GEN-LAST:event_CB_WALK_ASSISTStateChanged

    private void CB_TOR_SENSOR_ADVStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_TOR_SENSOR_ADVStateChanged
        TF_TORQ_ADC_RANGE.setEnabled(CB_TOR_SENSOR_ADV.isSelected());
        // TODO add your handling code here:
    }//GEN-LAST:event_CB_TOR_SENSOR_ADVStateChanged

    private void CB_AUTO_DISPLAY_DATAStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_AUTO_DISPLAY_DATAStateChanged
        TF_NUM_DATA_AUTO_DISPLAY.setEnabled(CB_AUTO_DISPLAY_DATA.isSelected());
        // TODO add your handling code here:
    }//GEN-LAST:event_CB_AUTO_DISPLAY_DATAStateChanged

    private void CB_CAD_SENSOR_ADVStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_CAD_SENSOR_ADVStateChanged
        TF_CAD_SENS_HIGH_PER.setEnabled(CB_CAD_SENSOR_ADV.isSelected());
        // TODO add your handling code here:
    }//GEN-LAST:event_CB_CAD_SENSOR_ADVStateChanged

    /*
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TSDZ2_Configurator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TSDZ2_Configurator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TSDZ2_Configurator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TSDZ2_Configurator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TSDZ2_Configurator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CB_ASS_WITHOUT_PED;
    private javax.swing.JCheckBox CB_AUTO_DISPLAY_DATA;
    private javax.swing.JCheckBox CB_BRAKE_SENSOR;
    private javax.swing.JCheckBox CB_CAD_SENSOR_ADV;
    private javax.swing.JCheckBox CB_CRUISE_WHITOUT_PED;
    private javax.swing.JCheckBox CB_DISPLAY_DOUBLE_DATA;
    private javax.swing.JCheckBox CB_EXP_HIGH_CAD_MODE;
    private javax.swing.JCheckBox CB_LIGHTS;
    private javax.swing.JCheckBox CB_MAX_SPEED_DISPLAY;
    private javax.swing.JCheckBox CB_ODO_COMPENSATION;
    private javax.swing.JCheckBox CB_RET_DISPLAY_MODE;
    private javax.swing.JCheckBox CB_SET_PARAM_ON_START;
    private javax.swing.JCheckBox CB_STREET_CRUISE;
    private javax.swing.JCheckBox CB_STREET_MODE_ON_START;
    private javax.swing.JCheckBox CB_STREET_POWER_LIM;
    private javax.swing.JCheckBox CB_STREET_THROTTLE;
    private javax.swing.JCheckBox CB_TEMP_ERR_MIN_LIM;
    private javax.swing.JCheckBox CB_TOR_SENSOR_ADV;
    private javax.swing.JCheckBox CB_WALK_ASSIST;
    private javax.swing.JCheckBox CB_WALK_TIME_ENA;
    private javax.swing.JLabel Label_Parameter1;
    private javax.swing.JLabel Label_Parameter2;
    private javax.swing.JLabel Label_Parameter3;
    private javax.swing.JLabel Label_Parameter4;
    private javax.swing.JLabel Label_Parameter5;
    private javax.swing.JRadioButton RB_ADC_OPTION_DIS;
    private javax.swing.JRadioButton RB_CADENCE_ON_START;
    private javax.swing.JRadioButton RB_DISPLAY_ALWAY_ON;
    private javax.swing.JRadioButton RB_DISPLAY_WORK_ON;
    private javax.swing.JRadioButton RB_EMTB_ON_START;
    private javax.swing.JRadioButton RB_MOTOR_36V;
    private javax.swing.JRadioButton RB_MOTOR_48V;
    private javax.swing.JRadioButton RB_POWER_ON_START;
    private javax.swing.JRadioButton RB_TEMP_LIMIT;
    private javax.swing.JRadioButton RB_THROTTLE;
    private javax.swing.JRadioButton RB_TORQUE_ON_START;
    private javax.swing.JRadioButton RB_UNIT_KILOMETERS;
    private javax.swing.JRadioButton RB_UNIT_MILES;
    private javax.swing.JRadioButton RB_VLCD5;
    private javax.swing.JRadioButton RB_VLCD6;
    private javax.swing.JRadioButton RB_XH18;
    private javax.swing.JTextField TF_ADC_THROTTLE_MAX;
    private javax.swing.JTextField TF_ADC_THROTTLE_MIN;
    private javax.swing.JTextField TF_ASS_WITHOUT_PED_THRES;
    private javax.swing.JTextField TF_BATT_CAPACITY;
    private javax.swing.JTextField TF_BATT_CAPACITY_CAL;
    private javax.swing.JTextField TF_BATT_NUM_CELLS;
    private javax.swing.JTextField TF_BATT_POW_MAX;
    private javax.swing.JTextField TF_BATT_RESIST;
    private javax.swing.JTextField TF_BATT_VOLT_CAL;
    private javax.swing.JTextField TF_BATT_VOLT_CUT_OFF;
    private javax.swing.JTextField TF_BAT_CELL_1_4;
    private javax.swing.JTextField TF_BAT_CELL_1_6;
    private javax.swing.JTextField TF_BAT_CELL_2_4;
    private javax.swing.JTextField TF_BAT_CELL_2_6;
    private javax.swing.JTextField TF_BAT_CELL_3_4;
    private javax.swing.JTextField TF_BAT_CELL_3_6;
    private javax.swing.JTextField TF_BAT_CELL_4_6;
    private javax.swing.JTextField TF_BAT_CELL_5_6;
    private javax.swing.JTextField TF_BAT_CELL_EMPTY;
    private javax.swing.JTextField TF_BAT_CELL_FULL;
    private javax.swing.JTextField TF_BAT_CELL_OVER;
    private javax.swing.JTextField TF_BAT_CELL_SOC;
    private javax.swing.JTextField TF_BAT_CUR_MAX;
    private javax.swing.JTextField TF_CADENCE_ASS_1;
    private javax.swing.JTextField TF_CADENCE_ASS_2;
    private javax.swing.JTextField TF_CADENCE_ASS_3;
    private javax.swing.JTextField TF_CADENCE_ASS_4;
    private javax.swing.JTextField TF_CAD_SENS_HIGH_PER;
    private javax.swing.JTextField TF_CRUISE_ASS_1;
    private javax.swing.JTextField TF_CRUISE_ASS_2;
    private javax.swing.JTextField TF_CRUISE_ASS_3;
    private javax.swing.JTextField TF_CRUISE_ASS_4;
    private javax.swing.JTextField TF_CRUISE_SPEED_ENA;
    private javax.swing.JTextField TF_DATA_1;
    private javax.swing.JTextField TF_DATA_2;
    private javax.swing.JTextField TF_DATA_3;
    private javax.swing.JTextField TF_DATA_4;
    private javax.swing.JTextField TF_DATA_5;
    private javax.swing.JTextField TF_DATA_6;
    private javax.swing.JTextField TF_DELAY_DATA_1;
    private javax.swing.JTextField TF_DELAY_DATA_2;
    private javax.swing.JTextField TF_DELAY_DATA_3;
    private javax.swing.JTextField TF_DELAY_DATA_4;
    private javax.swing.JTextField TF_DELAY_DATA_5;
    private javax.swing.JTextField TF_DELAY_DATA_6;
    private javax.swing.JTextField TF_DELAY_MENU;
    private javax.swing.JTextField TF_EMTB_ASS_1;
    private javax.swing.JTextField TF_EMTB_ASS_2;
    private javax.swing.JTextField TF_EMTB_ASS_3;
    private javax.swing.JTextField TF_EMTB_ASS_4;
    private javax.swing.JTextField TF_LIGHT_MODE_1;
    private javax.swing.JTextField TF_LIGHT_MODE_2;
    private javax.swing.JTextField TF_LIGHT_MODE_3;
    private javax.swing.JTextField TF_LIGHT_MODE_ON_START;
    private javax.swing.JTextField TF_MAX_SPEED;
    private javax.swing.JTextField TF_MOTOR_ACC;
    private javax.swing.JTextField TF_MOTOR_BLOCK_CURR;
    private javax.swing.JTextField TF_MOTOR_BLOCK_ERPS;
    private javax.swing.JTextField TF_MOTOR_BLOCK_TIME;
    private javax.swing.JTextField TF_NUM_DATA_AUTO_DISPLAY;
    private javax.swing.JTextField TF_POWER_ASS_1;
    private javax.swing.JTextField TF_POWER_ASS_2;
    private javax.swing.JTextField TF_POWER_ASS_3;
    private javax.swing.JTextField TF_POWER_ASS_4;
    private javax.swing.JTextField TF_RAMP_DOWN_ADD;
    private javax.swing.JTextField TF_RET_DISPLAY_MODE;
    private javax.swing.JTextField TF_STREET_POWER_LIM;
    private javax.swing.JTextField TF_STREET_SPEED_LIM;
    private javax.swing.JTextField TF_TEMP_MAX_LIM;
    private javax.swing.JTextField TF_TEMP_MIN_LIM;
    private javax.swing.JTextField TF_TORQUE_ASS_1;
    private javax.swing.JTextField TF_TORQUE_ASS_2;
    private javax.swing.JTextField TF_TORQUE_ASS_3;
    private javax.swing.JTextField TF_TORQUE_ASS_4;
    private javax.swing.JTextField TF_TORQ_ADC_OFFSET_ADJ;
    private javax.swing.JTextField TF_TORQ_ADC_RANGE;
    private javax.swing.JTextField TF_TORQ_PER_ADC_STEP_STD;
    private javax.swing.JTextField TF_WALK_ASS_1;
    private javax.swing.JTextField TF_WALK_ASS_2;
    private javax.swing.JTextField TF_WALK_ASS_3;
    private javax.swing.JTextField TF_WALK_ASS_4;
    private javax.swing.JTextField TF_WALK_ASS_SPEED;
    private javax.swing.JTextField TF_WALK_ASS_TIME;
    private javax.swing.JTextField TF_WHEEL_CIRCUMF;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.JList<String> expSet;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JLabel jLabelData1;
    private javax.swing.JLabel jLabelData2;
    private javax.swing.JLabel jLabelData3;
    private javax.swing.JLabel jLabelData4;
    private javax.swing.JLabel jLabelData5;
    private javax.swing.JLabel jLabelData6;
    private javax.swing.JLabel jLabelLights0;
    private javax.swing.JLabel jLabelLights1;
    private javax.swing.JLabel jLabelLights2;
    private javax.swing.JLabel jLabelLights3;
    private javax.swing.JLabel jLabel_TORQ_ADC_OFFSET_ADJ;
    private javax.swing.JLabel jLabel_TORQ_ADC_RANGE;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private java.awt.Label label1;
    private javax.swing.JList<String> provSet;
    // End of variables declaration//GEN-END:variables
}
