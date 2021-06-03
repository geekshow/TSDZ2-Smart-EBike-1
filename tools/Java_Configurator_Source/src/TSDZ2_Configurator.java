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

    String[] displayDataArray = {"motor temperature", "battery SOC rem. %", "battery voltage", "battery current", "motor power", "adc throttle 8b", "adc torque sensor 10b", "pedal cadence rpm", "human power", "adc pedal torque delta", "consumed Wh"};   
    String[] lightModeArray = {"<br>lights ON", "<br>lights FLASHING", "lights ON and BRAKE-FLASHING brak.", "lights FLASHING and ON when braking", "lights FLASHING BRAKE-FLASHING brak.", "lights ON and ON always braking", "lights ON and BRAKE-FLASHING alw.br.", "lights FLASHING and ON always braking", "lights FLASHING BRAKE-FLASHING alw.br.", "assist without pedal rotation", "assist with sensors error"};

    public int varMaxSpeed;
    public int varStreetSpeed;
    public int varWalkSpeed;
    public int varCruiseSpeed;
    public int varCruiseSpeed1;
    public int varCruiseSpeed2;
    public int varCruiseSpeed3;
    public int varCruiseSpeed4;
    
    public void loadSettings(File f) throws IOException {
    
     		BufferedReader in = new BufferedReader(new FileReader(f));
                RB_MOTOR_36V.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_MOTOR_48V.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_TORQUE_CALIBRATION.setSelected(Boolean.parseBoolean(in.readLine()));
                TF_MOTOR_ACC.setText(in.readLine());
                CB_ASS_WITHOUT_PED.setSelected(Boolean.parseBoolean(in.readLine()));
                TF_ASS_WITHOUT_PED_THRES.setText(in.readLine());
                TF_TORQ_PER_ADC_STEP.setText(in.readLine());
                TF_TORQUE_ADC_MAX.setText(in.readLine());
                TF_BOOST_TORQUE_FACTOR.setText(in.readLine());
                TF_MOTOR_BLOCK_TIME.setText(in.readLine());
                TF_MOTOR_BLOCK_CURR.setText(in.readLine());
                TF_MOTOR_BLOCK_ERPS.setText(in.readLine());
                TF_BOOST_CADENCE_STEP.setText(in.readLine());
                TF_BAT_CUR_MAX.setText(in.readLine());
                TF_BATT_POW_MAX.setText(in.readLine());
                TF_BATT_CAPACITY.setText(in.readLine());
                TF_BATT_NUM_CELLS.setText(in.readLine());
                TF_MOTOR_DEC.setText(in.readLine());
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
                varMaxSpeed = Integer.parseInt(in.readLine());
                CB_LIGHTS.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_WALK_ASSIST.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_BRAKE_SENSOR.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_ADC_OPTION_DIS.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_THROTTLE.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_TEMP_LIMIT.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_STREET_MODE_ON_START.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_SET_PARAM_ON_START.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_ODO_COMPENSATION.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_STARTUP_BOOST_ON_START.setSelected(Boolean.parseBoolean(in.readLine()));
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
                varStreetSpeed = Integer.parseInt(in.readLine());
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
                CB_COASTER_BRAKE.setSelected(Boolean.parseBoolean(in.readLine()));
                TF_COASTER_BRAKE_THRESHOLD.setText(in.readLine());
                CB_AUTO_DISPLAY_DATA.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_MOTOR_FAST_STOP.setSelected(Boolean.parseBoolean(in.readLine()));
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
                varWalkSpeed =  Integer.parseInt(in.readLine());
                CB_WALK_TIME_ENA.setSelected(Boolean.parseBoolean(in.readLine()));
                TF_WALK_ASS_TIME.setText(in.readLine());            
                varCruiseSpeed1 =  Integer.parseInt(in.readLine());
                varCruiseSpeed2 =  Integer.parseInt(in.readLine());
                varCruiseSpeed3 =  Integer.parseInt(in.readLine());
                varCruiseSpeed4 =  Integer.parseInt(in.readLine());
                CB_CRUISE_WHITOUT_PED.setSelected(Boolean.parseBoolean(in.readLine()));
                varCruiseSpeed =  Integer.parseInt(in.readLine());
                TF_TORQ_ADC_OFFSET.setText(in.readLine());
                TF_NUM_DATA_AUTO_DISPLAY.setText(in.readLine());
                RB_UNIT_KILOMETERS.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_UNIT_MILES.setSelected(Boolean.parseBoolean(in.readLine()));
                TF_ASSIST_THROTTLE_MIN.setText(in.readLine());
                TF_ASSIST_THROTTLE_MAX.setText(in.readLine());
                CB_STREET_WALK.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_HYBRID_ON_START.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_STARTUP_NONE.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_STARTUP_SOC.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_STARTUP_VOLTS.setSelected(Boolean.parseBoolean(in.readLine()));
                CB_FIELD_WEAKENING_ON_START.setSelected(Boolean.parseBoolean(in.readLine()));
                
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
                               
                if (RB_UNIT_KILOMETERS.isSelected()) {
                    jLabelMaxSpeed.setText("Max speed offroad mode (km/h)");
                    jLabelStreetSpeed.setText("Street speed limit (km/h)");
                    jLabelWalkSpeed.setText("Walk assist speed (km/h)");
                    jLabelCruiseSpeedUnits.setText("km/h");
                    TF_MAX_SPEED.setText(String.valueOf(varMaxSpeed));
                    TF_STREET_SPEED_LIM.setText(String.valueOf(varStreetSpeed));
                    TF_WALK_ASS_SPEED.setText(String.valueOf(varWalkSpeed));
                    TF_CRUISE_SPEED_ENA.setText(String.valueOf(varCruiseSpeed));
                    TF_CRUISE_ASS_1.setText(String.valueOf(varCruiseSpeed1));
                    TF_CRUISE_ASS_2.setText(String.valueOf(varCruiseSpeed2));
                    TF_CRUISE_ASS_3.setText(String.valueOf(varCruiseSpeed3));
                    TF_CRUISE_ASS_4.setText(String.valueOf(varCruiseSpeed4));
		}   
                
                if (RB_UNIT_MILES.isSelected()) {
                    jLabelMaxSpeed.setText("Max speed offroad mode (mph)");
                    jLabelStreetSpeed.setText("Street speed limit (mph)");
                    jLabelWalkSpeed.setText("Walk assist speed (mph)");
                    jLabelCruiseSpeedUnits.setText("mph");
                    TF_MAX_SPEED.setText(String.valueOf((varMaxSpeed * 10 + 5) / 16));
                    TF_STREET_SPEED_LIM.setText(String.valueOf((varStreetSpeed * 10 + 5) / 16));
                    TF_WALK_ASS_SPEED.setText(String.valueOf((varWalkSpeed * 10 + 5) / 16));
                    TF_CRUISE_SPEED_ENA.setText(String.valueOf((varCruiseSpeed * 10 + 5) / 16));
                    TF_CRUISE_ASS_1.setText(String.valueOf((varCruiseSpeed1 * 10 + 5) / 16));
                    TF_CRUISE_ASS_2.setText(String.valueOf((varCruiseSpeed2 * 10 + 5) / 16));
                    TF_CRUISE_ASS_3.setText(String.valueOf((varCruiseSpeed3 * 10 + 5) / 16));
                    TF_CRUISE_ASS_4.setText(String.valueOf((varCruiseSpeed4 * 10 + 5) / 16));
                }

            try {
		BufferedReader br = new BufferedReader (new FileReader("commits.txt"));
                LB_LAST_COMMIT.setText("<html>" + br.readLine() + "</html>");
                br.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, " " + ex);
            }   
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

                                        if (CB_TORQUE_CALIBRATION.isSelected()) {
						text_to_save = "#define TORQUE_SENSOR_CALIBRATED 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define TORQUE_SENSOR_CALIBRATED 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_TORQUE_CALIBRATION.isSelected());
                                        
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

                                        text_to_save = "#define PEDAL_TORQUE_PER_10_BIT_ADC_STEP_X100 " + TF_TORQ_PER_ADC_STEP.getText();
                                        iWriter.println(TF_TORQ_PER_ADC_STEP.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define PEDAL_TORQUE_ADC_MAX " + TF_TORQUE_ADC_MAX.getText();
                                        iWriter.println(TF_TORQUE_ADC_MAX.getText());
					pWriter.println(text_to_save); 

                                        text_to_save = "#define STARTUP_BOOST_TORQUE_FACTOR " + TF_BOOST_TORQUE_FACTOR.getText();
                                        iWriter.println(TF_BOOST_TORQUE_FACTOR.getText());
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

                                        text_to_save = "#define STARTUP_BOOST_CADENCE_STEP " + TF_BOOST_CADENCE_STEP.getText();
                                        iWriter.println(TF_BOOST_CADENCE_STEP.getText());
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

                                        text_to_save = "#define MOTOR_DECELERATION " + TF_MOTOR_DEC.getText();
                                        iWriter.println(TF_MOTOR_DEC.getText());
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
                                        
                                        text_to_save = "#define WHEEL_MAX_SPEED " + String.valueOf(varMaxSpeed);
                                        iWriter.println(String.valueOf(varMaxSpeed));
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

                                        if (CB_STARTUP_BOOST_ON_START.isSelected()) {
						text_to_save = "#define STARTUP_BOOST_ON_STARTUP 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define STARTUP_BOOST_ON_STARTUP 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_STARTUP_BOOST_ON_START.isSelected());

                                        if (CB_TOR_SENSOR_ADV.isSelected()) {
						text_to_save = "#define TORQUE_SENSOR_ADV_ON_STARTUP 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define TORQUE_SENSOR_ADV_ON_STARTUP 0";
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
                                        
                                        text_to_save = "#define STREET_MODE_SPEED_LIMIT " + String.valueOf(varStreetSpeed);
                                        iWriter.println(String.valueOf(varStreetSpeed));
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
                                        
                                        if (CB_COASTER_BRAKE.isSelected()) {
						text_to_save = "#define COASTER_BRAKE_ENABLED 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define COASTER_BRAKE_ENABLED 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_COASTER_BRAKE.isSelected());
                                        
                                        text_to_save = "#define COASTER_BRAKE_TORQUE_THRESHOLD " + TF_COASTER_BRAKE_THRESHOLD.getText();
                                        iWriter.println(TF_COASTER_BRAKE_THRESHOLD.getText());
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
                                        
                                        if (CB_MOTOR_FAST_STOP.isSelected()) {
						text_to_save = "#define MOTOR_FAST_STOP 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define MOTOR_FAST_STOP 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_MOTOR_FAST_STOP.isSelected());
                                        
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
                                        
                                        text_to_save = "#define WALK_ASSIST_THRESHOLD_SPEED " + String.valueOf(varWalkSpeed);
                                        iWriter.println(String.valueOf(varWalkSpeed));
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
                                        
                                        text_to_save = "#define CRUISE_TARGET_SPEED_LEVEL_1 " + String.valueOf(varCruiseSpeed1);
                                        iWriter.println(String.valueOf(varCruiseSpeed1));
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define CRUISE_TARGET_SPEED_LEVEL_2 " + String.valueOf(varCruiseSpeed2);
                                        iWriter.println(String.valueOf(varCruiseSpeed2));
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define CRUISE_TARGET_SPEED_LEVEL_3 " + String.valueOf(varCruiseSpeed3);
                                        iWriter.println(String.valueOf(varCruiseSpeed3));
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define CRUISE_TARGET_SPEED_LEVEL_4 " + String.valueOf(varCruiseSpeed4);
                                        iWriter.println(String.valueOf(varCruiseSpeed4));
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
                                        
                                        text_to_save = "#define CRUISE_THRESHOLD_SPEED " + String.valueOf(varCruiseSpeed);
                                        iWriter.println(String.valueOf(varCruiseSpeed));
					pWriter.println(text_to_save);

                                        text_to_save = "#define PEDAL_TORQUE_ADC_OFFSET " + TF_TORQ_ADC_OFFSET.getText();
                                        iWriter.println(TF_TORQ_ADC_OFFSET.getText());
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
                                        
                                        text_to_save = "#define ASSIST_THROTTLE_MIN_VALUE " + TF_ASSIST_THROTTLE_MIN.getText();
                                        iWriter.println(TF_ASSIST_THROTTLE_MIN.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define ASSIST_THROTTLE_MAX_VALUE " + TF_ASSIST_THROTTLE_MAX.getText();
                                        iWriter.println(TF_ASSIST_THROTTLE_MAX.getText());
					pWriter.println(text_to_save);
                                        
                                        if (CB_STREET_WALK.isSelected()) {
						text_to_save = "#define STREET_MODE_WALK_ENABLED 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define STREET_MODE_WALK_ENABLED 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_STREET_WALK.isSelected());
                                        
                                        if (RB_HYBRID_ON_START.isSelected()) {
						text_to_save = "#define RIDING_MODE_ON_STARTUP 5";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_HYBRID_ON_START.isSelected());
                                        
                                        if (RB_STARTUP_NONE.isSelected()) {
						text_to_save = "#define DATA_DISPLAY_ON_STARTUP 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_STARTUP_NONE.isSelected());

                                        if (RB_STARTUP_SOC.isSelected()) {
						text_to_save = "#define DATA_DISPLAY_ON_STARTUP 1";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_STARTUP_SOC.isSelected());

                                        if (RB_STARTUP_VOLTS.isSelected()) {
						text_to_save = "#define DATA_DISPLAY_ON_STARTUP 2";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_STARTUP_VOLTS.isSelected());
                                        
                                        if (CB_FIELD_WEAKENING_ON_START.isSelected()) {
						text_to_save = "#define FIELD_WEAKENING_ON_STARTUP 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define FIELD_WEAKENING_ON_STARTUP 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(CB_FIELD_WEAKENING_ON_START.isSelected());

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
                            JOptionPane.showMessageDialog(null, " " + ex);
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
        buttonGroup7 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        Label_Parameter1 = new javax.swing.JLabel();
        RB_MOTOR_36V = new javax.swing.JRadioButton();
        CB_ASS_WITHOUT_PED = new javax.swing.JCheckBox();
        TF_TORQ_PER_ADC_STEP = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        TF_BOOST_TORQUE_FACTOR = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TF_MOTOR_ACC = new javax.swing.JTextField();
        TF_ASS_WITHOUT_PED_THRES = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        TF_BOOST_CADENCE_STEP = new javax.swing.JTextField();
        TF_TORQUE_ADC_MAX = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel_TORQ_ADC_MAX = new javax.swing.JLabel();
        RB_MOTOR_48V = new javax.swing.JRadioButton();
        TF_TORQ_ADC_OFFSET = new javax.swing.JTextField();
        jLabel_TORQ_ADC_OFFSET = new javax.swing.JLabel();
        CB_TORQUE_CALIBRATION = new javax.swing.JCheckBox();
        jLabelMotorFastStop = new javax.swing.JLabel();
        TF_MOTOR_DEC = new javax.swing.JTextField();
        CB_MOTOR_FAST_STOP = new javax.swing.JCheckBox();
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
        jLabelMaxSpeed = new javax.swing.JLabel();
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
        CB_STARTUP_BOOST_ON_START = new javax.swing.JCheckBox();
        CB_TOR_SENSOR_ADV = new javax.swing.JCheckBox();
        jLabel33 = new javax.swing.JLabel();
        CB_AUTO_DISPLAY_DATA = new javax.swing.JCheckBox();
        CB_SET_PARAM_ON_START = new javax.swing.JCheckBox();
        CB_MAX_SPEED_DISPLAY = new javax.swing.JCheckBox();
        CB_COASTER_BRAKE = new javax.swing.JCheckBox();
        CB_FIELD_WEAKENING_ON_START = new javax.swing.JCheckBox();
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
        jLabelWalkSpeed = new javax.swing.JLabel();
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
        jLabelCruiseSpeedUnits = new javax.swing.JLabel();
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
        jPanel22 = new javax.swing.JPanel();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        RB_HYBRID_ON_START = new javax.swing.JRadioButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabelStreetSpeed = new javax.swing.JLabel();
        TF_STREET_SPEED_LIM = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        TF_STREET_POWER_LIM = new javax.swing.JTextField();
        CB_STREET_POWER_LIM = new javax.swing.JCheckBox();
        CB_STREET_THROTTLE = new javax.swing.JCheckBox();
        CB_STREET_CRUISE = new javax.swing.JCheckBox();
        CB_STREET_WALK = new javax.swing.JCheckBox();
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
        TF_DELAY_MENU = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        TF_NUM_DATA_AUTO_DISPLAY = new javax.swing.JTextField();
        jLabel109 = new javax.swing.JLabel();
        TF_ASSIST_THROTTLE_MIN = new javax.swing.JTextField();
        jLabel110 = new javax.swing.JLabel();
        TF_ASSIST_THROTTLE_MAX = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        RB_STARTUP_NONE = new javax.swing.JRadioButton();
        RB_STARTUP_SOC = new javax.swing.JRadioButton();
        RB_STARTUP_VOLTS = new javax.swing.JRadioButton();
        jLabelCoasterBrakeThreshld = new javax.swing.JLabel();
        TF_COASTER_BRAKE_THRESHOLD = new javax.swing.JTextField();
        label1 = new java.awt.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        expSet = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        provSet = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        LB_LAST_COMMIT = new javax.swing.JLabel();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TSDZ2 Parameter Configurator 3.0 for Open Source Firmware v20.1C");
        setPreferredSize(new java.awt.Dimension(1192, 608));
        setResizable(false);
        setSize(new java.awt.Dimension(1192, 608));

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(894, 513));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Motor settings");

        Label_Parameter1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Label_Parameter1.setForeground(new java.awt.Color(255, 0, 0));
        Label_Parameter1.setText("Motor type");

        buttonGroup1.add(RB_MOTOR_36V);
        RB_MOTOR_36V.setText("36V");

        CB_ASS_WITHOUT_PED.setText("Assist without pedaling threshold");
        CB_ASS_WITHOUT_PED.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CB_ASS_WITHOUT_PEDStateChanged(evt);
            }
        });

        TF_TORQ_PER_ADC_STEP.setText("67");
        TF_TORQ_PER_ADC_STEP.setToolTipText("<html>\nDefault value 67<br>\nOptional calibration\n</html>");

        jLabel20.setText("Startup boost torque factor (%)");

        TF_BOOST_TORQUE_FACTOR.setText("300");
        TF_BOOST_TORQUE_FACTOR.setToolTipText("<html>Max value 500<br>\nRecommended range 200 to 300\n</html>");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("Motor acceleration (%)");

        TF_MOTOR_ACC.setText("25");
        TF_MOTOR_ACC.setToolTipText("<html>MAX VALUE<br>\n36 volt motor, 36 volt battery = 35<br>\n36 volt motor, 48 volt battery = 5<br>\n36 volt motor, 52 volt battery = 0<br>\n48 volt motor, 36 volt battery = 45<br>\n48 volt motor, 48 volt battery = 35<br>\n48 volt motor, 52 volt battery = 30\n</html>");

        TF_ASS_WITHOUT_PED_THRES.setText("20");
        TF_ASS_WITHOUT_PED_THRES.setToolTipText("<html>Max value 100<br>\nRecommended range 10 to 30\n</html>");
        TF_ASS_WITHOUT_PED_THRES.setEnabled(CB_ASS_WITHOUT_PED.isSelected());

        jLabel22.setText("Startup boost cadence step (decr.)");

        TF_BOOST_CADENCE_STEP.setText("20");
        TF_BOOST_CADENCE_STEP.setToolTipText("<html>Max value 50<br>\nRecommended range 20 to 30<br>\n(high values short effect)\n</html>");

        TF_TORQUE_ADC_MAX.setText("300");
        TF_TORQUE_ADC_MAX.setToolTipText("<html>\nInsert value read on calibration<br>\nMax 500\n</html>");
        TF_TORQUE_ADC_MAX.setEnabled(CB_TORQUE_CALIBRATION.isSelected());

        jLabel6.setText("Pedal torque ADC step");

        jLabel_TORQ_ADC_MAX.setText("Pedal torque ADC max (max weight)");

        buttonGroup1.add(RB_MOTOR_48V);
        RB_MOTOR_48V.setText("48V");

        TF_TORQ_ADC_OFFSET.setText("150");
        TF_TORQ_ADC_OFFSET.setToolTipText("<html>\nInsert value read on calibration<br>\nMax 250\n</html>");
        TF_TORQ_ADC_OFFSET.setEnabled(CB_TORQUE_CALIBRATION.isSelected());

        jLabel_TORQ_ADC_OFFSET.setText("Pedal torque ADC offset (no weight)");

        CB_TORQUE_CALIBRATION.setText("Torque sensor calibrated");
        CB_TORQUE_CALIBRATION.setToolTipText("Enable after calibration");
        CB_TORQUE_CALIBRATION.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CB_TORQUE_CALIBRATIONStateChanged(evt);
            }
        });

        jLabelMotorFastStop.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelMotorFastStop.setForeground(new java.awt.Color(255, 0, 0));
        jLabelMotorFastStop.setText("Motor deceleration (%)");

        TF_MOTOR_DEC.setText("0");
        TF_MOTOR_DEC.setToolTipText("<html>Max value 100<br>\nRecommended range 0 to 50\n</html>");

        CB_MOTOR_FAST_STOP.setText("Motor fast stop");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(Label_Parameter1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(50, 50, 50))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(RB_MOTOR_36V, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RB_MOTOR_48V, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabelMotorFastStop, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TF_MOTOR_DEC, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel_TORQ_ADC_MAX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TF_MOTOR_ACC, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_BOOST_CADENCE_STEP, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_BOOST_TORQUE_FACTOR, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_TORQUE_ADC_MAX, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_TORQ_PER_ADC_STEP, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addComponent(CB_ASS_WITHOUT_PED)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(TF_ASS_WITHOUT_PED_THRES, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel_TORQ_ADC_OFFSET)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TF_TORQ_ADC_OFFSET, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, 16))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CB_MOTOR_FAST_STOP)
                            .addComponent(CB_TORQUE_CALIBRATION))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Label_Parameter1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(RB_MOTOR_36V)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RB_MOTOR_48V)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_MOTOR_ACC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMotorFastStop, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TF_MOTOR_DEC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CB_MOTOR_FAST_STOP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CB_ASS_WITHOUT_PED)
                    .addComponent(TF_ASS_WITHOUT_PED_THRES, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_TORQ_PER_ADC_STEP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CB_TORQUE_CALIBRATION)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(TF_TORQ_ADC_OFFSET, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_TORQ_ADC_OFFSET))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_TORQUE_ADC_MAX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_TORQ_ADC_MAX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BOOST_TORQUE_FACTOR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_BOOST_CADENCE_STEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(TF_BAT_CUR_MAX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TF_BATT_NUM_CELLS, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TF_BATT_POW_MAX, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TF_BATT_CAPACITY, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(TF_BATT_VOLT_CAL, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TF_BATT_VOLT_CUT_OFF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(TF_BATT_CAPACITY_CAL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
        RB_UNIT_MILES.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                RB_UNIT_MILESStateChanged(evt);
            }
        });

        buttonGroup6.add(RB_UNIT_KILOMETERS);
        RB_UNIT_KILOMETERS.setSelected(true);
        RB_UNIT_KILOMETERS.setText("km/h");
        RB_UNIT_KILOMETERS.setToolTipText("<html>Also set on the display<br>\nIf you set miles in display<br>\nset max wheel available\n</html>");
        RB_UNIT_KILOMETERS.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                RB_UNIT_KILOMETERSStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(Label_Parameter5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addComponent(RB_UNIT_KILOMETERS)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Label_Parameter2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Label_Parameter3)
                                    .addComponent(Label_Parameter4))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RB_DISPLAY_WORK_ON)
                    .addComponent(RB_DISPLAY_ALWAY_ON)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RB_XH18)
                            .addComponent(RB_VLCD5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addComponent(RB_VLCD6, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(RB_UNIT_MILES))
                .addGap(23, 23, 23))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RB_VLCD5)
                    .addComponent(Label_Parameter2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RB_VLCD6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RB_XH18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addComponent(Label_Parameter3)
                        .addGap(9, 9, 9)
                        .addComponent(Label_Parameter4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RB_DISPLAY_WORK_ON)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RB_DISPLAY_ALWAY_ON)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Label_Parameter5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(RB_UNIT_KILOMETERS))
                    .addComponent(RB_UNIT_MILES)))
        );

        jLabelMaxSpeed.setText("Max speed offroad mode");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 0));
        jLabel14.setText("Wheel circumference (mm)");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Bike settings");

        TF_MAX_SPEED.setText("25");
        TF_MAX_SPEED.setToolTipText("<html>km/h or mph<br>\nMax value in EU 25 km/h\n</html>");
        TF_MAX_SPEED.setEnabled(!CB_MAX_SPEED_DISPLAY.isSelected());
        TF_MAX_SPEED.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_MAX_SPEEDKeyReleased(evt);
            }
        });

        TF_WHEEL_CIRCUMF.setText("2260");
        TF_WHEEL_CIRCUMF.setToolTipText("<html>Indicative values:<br>\n26-inch wheel = 2050 mm<br>\n27-inch wheel = 2150 mm<br>\n27.5 inch wheel = 2215 mm<br>\n28-inch wheel = 2250 mm<br>\n29-inch wheel = 2300 mmV\n</html>");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(jLabelMaxSpeed, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(46, 46, 46)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TF_WHEEL_CIRCUMF, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TF_MAX_SPEED, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4))
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
                    .addComponent(jLabelMaxSpeed))
                .addGap(0, 46, Short.MAX_VALUE))
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
        RB_THROTTLE.setEnabled(CB_BRAKE_SENSOR.isSelected());
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

        CB_STREET_MODE_ON_START.setText("Street mode enabled on startup");

        CB_ODO_COMPENSATION.setText("Odometer compensation");

        CB_STARTUP_BOOST_ON_START.setText("Startup boost enabled  on startup");

        CB_TOR_SENSOR_ADV.setText("Torque sensor adv. on startup");

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

        CB_COASTER_BRAKE.setText("Coaster brake");
        CB_COASTER_BRAKE.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CB_COASTER_BRAKEStateChanged(evt);
            }
        });

        CB_FIELD_WEAKENING_ON_START.setText("Field weakening enabled  on startup");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CB_COASTER_BRAKE)
                    .addComponent(CB_MAX_SPEED_DISPLAY)
                    .addComponent(CB_AUTO_DISPLAY_DATA)
                    .addComponent(CB_STARTUP_BOOST_ON_START)
                    .addComponent(CB_TOR_SENSOR_ADV)
                    .addComponent(CB_ODO_COMPENSATION)
                    .addComponent(CB_SET_PARAM_ON_START)
                    .addComponent(CB_FIELD_WEAKENING_ON_START)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CB_STREET_MODE_ON_START)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RB_ADC_OPTION_DIS)
                            .addComponent(RB_THROTTLE)
                            .addComponent(RB_TEMP_LIMIT)))
                    .addComponent(jLabel39)
                    .addComponent(CB_LIGHTS, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CB_WALK_ASSIST, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CB_BRAKE_SENSOR, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 13, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CB_LIGHTS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_WALK_ASSIST)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_BRAKE_SENSOR)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_COASTER_BRAKE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CB_STREET_MODE_ON_START)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CB_STARTUP_BOOST_ON_START)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CB_FIELD_WEAKENING_ON_START)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CB_TOR_SENSOR_ADV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CB_ODO_COMPENSATION)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CB_SET_PARAM_ON_START)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CB_AUTO_DISPLAY_DATA)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CB_MAX_SPEED_DISPLAY))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(RB_ADC_OPTION_DIS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RB_THROTTLE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RB_TEMP_LIMIT)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(32, 32, 32)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Basic settings", jPanel1);

        jPanel4.setPreferredSize(new java.awt.Dimension(844, 552));

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
                        .addComponent(TF_POWER_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_POWER_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_POWER_ASS_4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel58))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TF_POWER_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(TF_TORQUE_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_TORQUE_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_TORQUE_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47)
                            .addComponent(jLabel59))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RB_TORQUE_ON_START)
                            .addComponent(TF_TORQUE_ASS_4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                        .addComponent(TF_CADENCE_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_CADENCE_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel51)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_CADENCE_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel52)
                            .addComponent(jLabel60))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RB_CADENCE_ON_START)
                            .addComponent(TF_CADENCE_ASS_4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                        .addComponent(TF_EMTB_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_EMTB_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel56)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_EMTB_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel57)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RB_EMTB_ON_START)
                            .addComponent(TF_EMTB_ASS_4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
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

        jLabelWalkSpeed.setText("Walk assist speed (km/h)");

        TF_WALK_ASS_SPEED.setText("6");
        TF_WALK_ASS_SPEED.setToolTipText("<html>km/h or mph<br>\nMax value in EU 6 km/h\n</html>");
        TF_WALK_ASS_SPEED.setEnabled(CB_WALK_ASSIST.isSelected());
        TF_WALK_ASS_SPEED.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_WALK_ASS_SPEEDKeyReleased(evt);
            }
        });

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
                    .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(CB_WALK_TIME_ENA)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel68)
                                    .addComponent(jLabelWalkSpeed)
                                    .addComponent(jLabel66)
                                    .addComponent(jLabel65)
                                    .addComponent(jLabel64)
                                    .addComponent(jLabel63))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TF_WALK_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_WALK_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_WALK_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_WALK_ASS_4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_WALK_ASS_SPEED, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_WALK_ASS_TIME, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
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
                    .addComponent(jLabelWalkSpeed)
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
        TF_CRUISE_ASS_1.setToolTipText("<html>km/h or mph<br>\nMax value in EU 25 km/h\n</html>");
        TF_CRUISE_ASS_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_CRUISE_ASS_1KeyReleased(evt);
            }
        });

        jLabel71.setText("Speed level 2 - TOUR");

        TF_CRUISE_ASS_2.setText("18");
        TF_CRUISE_ASS_2.setToolTipText("<html>km/h or mph<br>\nMax value in EU 25 km/h\n</html>");
        TF_CRUISE_ASS_2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_CRUISE_ASS_2KeyReleased(evt);
            }
        });

        jLabel72.setText("Speed level 3 - SPORT");

        TF_CRUISE_ASS_3.setText("21");
        TF_CRUISE_ASS_3.setToolTipText("<html>km/h or mph<br>\nMax value in EU 25 km/h\n</html>");
        TF_CRUISE_ASS_3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_CRUISE_ASS_3KeyReleased(evt);
            }
        });

        TF_CRUISE_ASS_4.setText("24");
        TF_CRUISE_ASS_4.setToolTipText("<html>km/h or mph<br>\nMax value in EU 25 km/h\n</html>");
        TF_CRUISE_ASS_4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_CRUISE_ASS_4KeyReleased(evt);
            }
        });

        jLabel73.setText("Speed level 4 -TURBO");

        jLabel74.setText("Speed cruise enabled");

        TF_CRUISE_SPEED_ENA.setText("10");
        TF_CRUISE_SPEED_ENA.setToolTipText("Min speed to enable cruise (km/h or mph)");
        TF_CRUISE_SPEED_ENA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_CRUISE_SPEED_ENAKeyReleased(evt);
            }
        });

        CB_CRUISE_WHITOUT_PED.setText("Cruise without pedaling");
        CB_CRUISE_WHITOUT_PED.setToolTipText("Only with brake sensors enabled");
        CB_CRUISE_WHITOUT_PED.setEnabled(CB_BRAKE_SENSOR.isSelected());

        jLabelCruiseSpeedUnits.setText("km/h");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel70)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_CRUISE_ASS_1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel71)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_CRUISE_ASS_2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel72)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_CRUISE_ASS_3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel73)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_CRUISE_ASS_4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel74)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TF_CRUISE_SPEED_ENA, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CB_CRUISE_WHITOUT_PED)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelCruiseSpeedUnits)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(3, 3, 3))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCruiseSpeedUnits))
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

        TF_LIGHT_MODE_1.setText("6");
        TF_LIGHT_MODE_1.setToolTipText("<html>With lights button ON<br>\n0 - lights ON<br>\n1 - lights FLASHING<br>\n2 - lights ON and BRAKE-FLASHING when braking<br>\n3 - lights FLASHING and ON when braking<br>\n4 - lights FLASHING and BRAKE-FLASHING when braking<br>\n5 - lights ON and ON when braking, even with the light button OFF<br>\n6 - lights ON and BRAKE-FLASHING when braking, even with the light button OFF<br>\n7 - lights FLASHING and ON when braking, even with the light button OFF<br>\n8 - lights FLASHING and BRAKE-FLASHING when braking, even with the light button OFF\n</html>");
        TF_LIGHT_MODE_1.setEnabled(CB_LIGHTS.isSelected());
        TF_LIGHT_MODE_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_LIGHT_MODE_1KeyReleased(evt);
            }
        });

        jLabelLights2.setText("Lights mode 2");
        jLabelLights2.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        TF_LIGHT_MODE_2.setText("7");
        TF_LIGHT_MODE_2.setToolTipText("<html>With lights button ON<br>\n0 - lights ON<br>\n1 - lights FLASHING<br>\n2 - lights ON and BRAKE-FLASHING when braking<br>\n3 - lights FLASHING and ON when braking<br>\n4 - lights FLASHING and BRAKE-FLASHING when braking<br>\n5 - lights ON and ON when braking, even with the light button OFF<br>\n6 - lights ON and BRAKE-FLASHING when braking, even with the light button OFF<br>\n7 - lights FLASHING and ON when braking, even with the light button OFF<br>\n8 - lights FLASHING and BRAKE-FLASHING when braking, even with the light button OFF<br>\nor alternative option settings<br>\n9 - assistance without pedal rotation\n</html>");
        TF_LIGHT_MODE_2.setEnabled(CB_LIGHTS.isSelected());
        TF_LIGHT_MODE_2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_LIGHT_MODE_2KeyReleased(evt);
            }
        });

        TF_LIGHT_MODE_3.setText("1");
        TF_LIGHT_MODE_3.setToolTipText("<html>With lights button ON<br>\n0 - lights ON<br>\n1 - lights FLASHING<br>\n2 - lights ON and BRAKE-FLASHING when braking<br>\n3 - lights FLASHING and ON when braking<br>\n4 - lights FLASHING and BRAKE-FLASHING when braking<br>\n5 - lights ON and ON when braking, even with the light button OFF<br>\n6 - lights ON and BRAKE-FLASHING when braking, even with the light button OFF<br>\n7 - lights FLASHING and ON when braking, even with the light button OFF<br>\n8 - lights FLASHING and BRAKE-FLASHING when braking, even with the light button OFF<br>\nor alternative option settings<br>\n10 - assistance with sensors error\n</html>");
        TF_LIGHT_MODE_3.setEnabled(CB_LIGHTS.isSelected());
        TF_LIGHT_MODE_3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_LIGHT_MODE_3KeyReleased(evt);
            }
        });

        jLabelLights3.setText("Lights mode 3");
        jLabelLights3.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel92.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel92.setText("Hybrid assist mode");

        jLabel93.setText("Enable on startup");

        buttonGroup5.add(RB_HYBRID_ON_START);
        RB_HYBRID_ON_START.setToolTipText("Torque & Power");
        RB_HYBRID_ON_START.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RB_HYBRID_ON_STARTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jLabel93)
                        .addGap(60, 60, 60)
                        .addComponent(RB_HYBRID_ON_START)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel93, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(RB_HYBRID_ON_START, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

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
                                .addGap(1, 1, 1)
                                .addComponent(TF_LIGHT_MODE_ON_START, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelLights1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelLights2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelLights3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TF_LIGHT_MODE_3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_LIGHT_MODE_2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_LIGHT_MODE_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLights0, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TF_LIGHT_MODE_ON_START, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLights1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TF_LIGHT_MODE_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLights2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TF_LIGHT_MODE_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLights3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TF_LIGHT_MODE_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Street mode");

        jLabelStreetSpeed.setText("Street speed limit");

        TF_STREET_SPEED_LIM.setText("25");
        TF_STREET_SPEED_LIM.setToolTipText("<html>km/h or mph<br>\nMax value in EU 25 km/h\n</html>");
        TF_STREET_SPEED_LIM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_STREET_SPEED_LIMKeyReleased(evt);
            }
        });

        jLabel34.setText("Street power limit (W)");

        TF_STREET_POWER_LIM.setText("500");
        TF_STREET_POWER_LIM.setToolTipText("<html>Max nominal value in EU 250 W<br>\nMax peak value approx. 500 W\n</html>");
        TF_STREET_POWER_LIM.setEnabled(CB_STREET_POWER_LIM.isSelected());

        CB_STREET_POWER_LIM.setText("Street power limit enabled");
        CB_STREET_POWER_LIM.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CB_STREET_POWER_LIMStateChanged(evt);
            }
        });

        CB_STREET_THROTTLE.setText("Throttle on street mode");

        CB_STREET_CRUISE.setText("Cruise on street mode");

        CB_STREET_WALK.setText("Walk assist on street mode");
        CB_STREET_WALK.setEnabled(CB_WALK_ASSIST.isSelected());

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CB_STREET_WALK)
                            .addComponent(CB_STREET_POWER_LIM)
                            .addComponent(CB_STREET_THROTTLE)
                            .addComponent(CB_STREET_CRUISE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabelStreetSpeed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TF_STREET_POWER_LIM, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TF_STREET_SPEED_LIM, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelStreetSpeed)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CB_STREET_WALK)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77))
        );

        jTabbedPane1.addTab("Assistance settings", jPanel4);

        jPanel8.setPreferredSize(new java.awt.Dimension(800, 486));

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
        TF_DELAY_DATA_1.setToolTipText("<html>Max value 255 (0.1 sec)<br>\ncontinuous display at zero value\n</html>");

        jLabelData1.setText("Data 1");

        TF_DATA_1.setText("1");
        TF_DATA_1.setToolTipText("<html>0 - motor temperature (°C)<br>\n  1 - battery SOC remaining (%)<br>\n  2 - battery voltage (V)<br>\n  3 - battery current (A)<br>\n  4 - motor power (Watt/10)<br>\n  5 - adc throttle (8 bit)<br>\n  6 - adc torque sensor (10 bit)<br>\n  7 - pedal cadence (rpm)<br>\n  8 - human power(W/10)<br>\n  9 - pedal torque adc delta<br>\n10 - consumed Wh/10\n</html>");
        TF_DATA_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_DATA_1KeyReleased(evt);
            }
        });

        TF_DATA_2.setText("2");
        TF_DATA_2.setToolTipText("<html>0 - motor temperature (°C)<br>\n  1 - battery SOC remaining (%)<br>\n  2 - battery voltage (V)<br>\n  3 - battery current (A)<br>\n  4 - motor power (Watt/10)<br>\n  5 - adc throttle (8 bit)<br>\n  6 - adc torque sensor (10 bit)<br>\n  7 - pedal cadence (rpm)<br>\n  8 - human power(W/10)<br>\n  9 - pedal torque adc delta<br>\n10 - consumed Wh/10\n</html>");
        TF_DATA_2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_DATA_2KeyReleased(evt);
            }
        });

        jLabelData2.setText("Data 2");

        TF_DATA_3.setText("5");
        TF_DATA_3.setToolTipText("<html>0 - motor temperature (°C)<br>\n  1 - battery SOC remaining (%)<br>\n  2 - battery voltage (V)<br>\n  3 - battery current (A)<br>\n  4 - motor power (Watt/10)<br>\n  5 - adc throttle (8 bit)<br>\n  6 - adc torque sensor (10 bit)<br>\n  7 - pedal cadence (rpm)<br>\n  8 - human power(W/10)<br>\n  9 - pedal torque adc delta<br>\n10 - consumed Wh/10\n</html>");
        TF_DATA_3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_DATA_3KeyReleased(evt);
            }
        });

        jLabelData3.setText("Data 3");

        jLabelData4.setText("Data 4");

        TF_DATA_4.setText("4");
        TF_DATA_4.setToolTipText("<html>0 - motor temperature (°C)<br>\n  1 - battery SOC remaining (%)<br>\n  2 - battery voltage (V)<br>\n  3 - battery current (A)<br>\n  4 - motor power (Watt/10)<br>\n  5 - adc throttle (8 bit)<br>\n  6 - adc torque sensor (10 bit)<br>\n  7 - pedal cadence (rpm)<br>\n  8 - human power(W/10)<br>\n  9 - pedal torque adc delta<br>\n10 - consumed Wh/10\n</html>");
        TF_DATA_4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_DATA_4KeyReleased(evt);
            }
        });

        TF_DATA_5.setText("7");
        TF_DATA_5.setToolTipText("<html>0 - motor temperature (°C)<br>\n  1 - battery SOC remaining (%)<br>\n  2 - battery voltage (V)<br>\n  3 - battery current (A)<br>\n  4 - motor power (Watt/10)<br>\n  5 - adc throttle (8 bit)<br>\n  6 - adc torque sensor (10 bit)<br>\n  7 - pedal cadence (rpm)<br>\n  8 - human power(W/10)<br>\n  9 - pedal torque adc delta<br>\n10 - consumed Wh/10\n</html>");
        TF_DATA_5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_DATA_5KeyReleased(evt);
            }
        });

        jLabelData5.setText("Data 5");

        jLabelData6.setText("Data 6");

        TF_DATA_6.setText("0");
        TF_DATA_6.setToolTipText("<html>0 - motor temperature (°C)<br>\n  1 - battery SOC remaining (%)<br>\n  2 - battery voltage (V)<br>\n  3 - battery current (A)<br>\n  4 - motor power (Watt/10)<br>\n  5 - adc throttle (8 bit)<br>\n  6 - adc torque sensor (10 bit)<br>\n  7 - pedal cadence (rpm)<br>\n  8 - human power(W/10)<br>\n  9 - pedal torque adc delta<br>\n10 - consumed Wh/10\n</html>");
        TF_DATA_6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_DATA_6KeyReleased(evt);
            }
        });

        jLabel96.setText("Time to displayed data 2 (0.1 s)");

        TF_DELAY_DATA_2.setText("50");
        TF_DELAY_DATA_2.setToolTipText("<html>Max value 255 (0.1 sec)<br>\ncontinuous display at zero value\n</html>");

        jLabel97.setText("Time to displayed data 3 (0.1 s)");

        TF_DELAY_DATA_3.setText("50");
        TF_DELAY_DATA_3.setToolTipText("<html>Max value 255 (0.1 sec)<br>\ncontinuous display at zero value\n</html>");

        jLabel98.setText("Time to displayed data 4 (0.1 s)");

        TF_DELAY_DATA_4.setText("50");
        TF_DELAY_DATA_4.setToolTipText("<html>Max value 255 (0.1 sec)<br>\ncontinuous display at zero value\n</html>");

        jLabel99.setText("Time to displayed data 5 (0.1 s)");

        TF_DELAY_DATA_5.setText("50");
        TF_DELAY_DATA_5.setToolTipText("<html>Max value 255 (0.1 sec)<br>\ncontinuous display at zero value\n</html>");

        jLabel100.setText("Time to displayed data 6 (0.1 s)");

        TF_DELAY_DATA_6.setText("50");
        TF_DELAY_DATA_6.setToolTipText("<html>Max value 255 (0.1 sec)<br>\ncontinuous display at zero value\n</html>");

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

        jLabel102.setText("ADC throttle value       min");

        TF_ADC_THROTTLE_MIN.setText("47");
        TF_ADC_THROTTLE_MIN.setToolTipText("Value 40 to 50");
        TF_ADC_THROTTLE_MIN.setEnabled(RB_THROTTLE.isSelected());

        TF_ADC_THROTTLE_MAX.setText("176");
        TF_ADC_THROTTLE_MAX.setToolTipText("Value 170 to 180");
        TF_ADC_THROTTLE_MAX.setEnabled(RB_THROTTLE.isSelected());

        jLabel103.setText("Throttle assist value    min");

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

        TF_DELAY_MENU.setText("50");
        TF_DELAY_MENU.setToolTipText("Max value 60 (0.1 s)");

        jLabel87.setText("Time to menu items (0.1 s)");

        jLabel90.setText("Number of data displayed at lights on");

        TF_NUM_DATA_AUTO_DISPLAY.setText("2");
        TF_NUM_DATA_AUTO_DISPLAY.setToolTipText("Value 1 to 6");
        TF_NUM_DATA_AUTO_DISPLAY.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TF_NUM_DATA_AUTO_DISPLAYKeyReleased(evt);
            }
        });

        jLabel109.setText("max");

        TF_ASSIST_THROTTLE_MIN.setText("0");
        TF_ASSIST_THROTTLE_MIN.setToolTipText("Value 0 to 100");
        TF_ASSIST_THROTTLE_MIN.setEnabled(RB_THROTTLE.isSelected());

        jLabel110.setText("max");

        TF_ASSIST_THROTTLE_MAX.setText("255");
        TF_ASSIST_THROTTLE_MAX.setToolTipText("Value MIN to 255");
        TF_ASSIST_THROTTLE_MAX.setEnabled(RB_THROTTLE.isSelected());

        jLabel91.setText("Data displayed on startup");

        buttonGroup7.add(RB_STARTUP_NONE);
        RB_STARTUP_NONE.setText("None");
        RB_STARTUP_NONE.setToolTipText("");
        RB_STARTUP_NONE.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                RB_STARTUP_NONEStateChanged(evt);
            }
        });

        buttonGroup7.add(RB_STARTUP_SOC);
        RB_STARTUP_SOC.setSelected(true);
        RB_STARTUP_SOC.setText("Soc %");
        RB_STARTUP_SOC.setToolTipText("");
        RB_STARTUP_SOC.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                RB_STARTUP_SOCStateChanged(evt);
            }
        });

        buttonGroup7.add(RB_STARTUP_VOLTS);
        RB_STARTUP_VOLTS.setText("Volts");
        RB_STARTUP_VOLTS.setToolTipText("");
        RB_STARTUP_VOLTS.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                RB_STARTUP_VOLTSStateChanged(evt);
            }
        });

        jLabelCoasterBrakeThreshld.setText("Coaster brake torque threshold");

        TF_COASTER_BRAKE_THRESHOLD.setText("30");
        TF_COASTER_BRAKE_THRESHOLD.setToolTipText("Max value 255 (s)");
        TF_COASTER_BRAKE_THRESHOLD.setEnabled(CB_COASTER_BRAKE.isSelected());

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(CB_TEMP_ERR_MIN_LIM)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel102)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TF_ADC_THROTTLE_MIN, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel109)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TF_ADC_THROTTLE_MAX, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel104, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(TF_TEMP_MIN_LIM, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel105, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(TF_TEMP_MAX_LIM, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TF_MOTOR_BLOCK_TIME, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel107, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TF_MOTOR_BLOCK_CURR, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel108, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(TF_MOTOR_BLOCK_ERPS, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel103)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TF_ASSIST_THROTTLE_MIN, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel110)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TF_ASSIST_THROTTLE_MAX, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(TF_NUM_DATA_AUTO_DISPLAY, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TF_DELAY_MENU, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel87, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(56, 56, 56))
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel91, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelCoasterBrakeThreshld, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TF_COASTER_BRAKE_THRESHOLD, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(RB_STARTUP_NONE)
                        .addGap(26, 26, 26)
                        .addComponent(RB_STARTUP_SOC)
                        .addGap(18, 18, 18)
                        .addComponent(RB_STARTUP_VOLTS))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel101)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel91)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RB_STARTUP_NONE)
                    .addComponent(RB_STARTUP_SOC)
                    .addComponent(RB_STARTUP_VOLTS))
                .addGap(18, 18, 18)
                .addComponent(jLabel101)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_COASTER_BRAKE_THRESHOLD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCoasterBrakeThreshld))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TF_ADC_THROTTLE_MIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel102)
                        .addComponent(TF_ADC_THROTTLE_MAX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel109)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_ASSIST_THROTTLE_MIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TF_ASSIST_THROTTLE_MAX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel110)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addContainerGap(96, Short.MAX_VALUE))
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

        jLabel4.setText("Version (last commits)");

        LB_LAST_COMMIT.setText("<html>Last commit</html>");
        LB_LAST_COMMIT.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                    .addComponent(LB_LAST_COMMIT, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LB_LAST_COMMIT, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("MotorConfiguration");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CB_COASTER_BRAKEStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_COASTER_BRAKEStateChanged
           TF_COASTER_BRAKE_THRESHOLD.setEnabled(CB_COASTER_BRAKE.isSelected());
    }//GEN-LAST:event_CB_COASTER_BRAKEStateChanged

    private void CB_WALK_TIME_ENAStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_WALK_TIME_ENAStateChanged
        TF_WALK_ASS_TIME.setEnabled(CB_WALK_TIME_ENA.isSelected() && CB_BRAKE_SENSOR.isSelected() && CB_WALK_ASSIST.isSelected());
    }//GEN-LAST:event_CB_WALK_TIME_ENAStateChanged

    private void CB_BRAKE_SENSORStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_BRAKE_SENSORStateChanged
        TF_WALK_ASS_TIME.setEnabled(CB_WALK_TIME_ENA.isSelected() && CB_BRAKE_SENSOR.isSelected() && CB_WALK_ASSIST.isSelected());
        CB_CRUISE_WHITOUT_PED.setEnabled(CB_BRAKE_SENSOR.isSelected());
        CB_WALK_TIME_ENA.setEnabled(CB_BRAKE_SENSOR.isSelected() && CB_WALK_ASSIST.isSelected());
        RB_THROTTLE.setEnabled(CB_BRAKE_SENSOR.isSelected());
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
    }//GEN-LAST:event_RB_XH18StateChanged

    private void RB_THROTTLEStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RB_THROTTLEStateChanged
        TF_ADC_THROTTLE_MIN.setEnabled(RB_THROTTLE.isSelected());
        TF_ADC_THROTTLE_MAX.setEnabled(RB_THROTTLE.isSelected());
        TF_ASSIST_THROTTLE_MIN.setEnabled(RB_THROTTLE.isSelected());
        TF_ASSIST_THROTTLE_MAX.setEnabled(RB_THROTTLE.isSelected());
    }//GEN-LAST:event_RB_THROTTLEStateChanged

    private void RB_TEMP_LIMITStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RB_TEMP_LIMITStateChanged
        TF_TEMP_MIN_LIM.setEnabled(RB_TEMP_LIMIT.isSelected());
        TF_TEMP_MAX_LIM.setEnabled(RB_TEMP_LIMIT.isSelected());
        CB_TEMP_ERR_MIN_LIM.setEnabled(RB_TEMP_LIMIT.isSelected());
    }//GEN-LAST:event_RB_TEMP_LIMITStateChanged

    private void RB_ADC_OPTION_DISStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RB_ADC_OPTION_DISStateChanged
        TF_ADC_THROTTLE_MIN.setEnabled(RB_THROTTLE.isSelected());
        TF_ADC_THROTTLE_MAX.setEnabled(RB_THROTTLE.isSelected());
        TF_TEMP_MIN_LIM.setEnabled(RB_TEMP_LIMIT.isSelected());
        TF_TEMP_MAX_LIM.setEnabled(RB_TEMP_LIMIT.isSelected());
        TF_ASSIST_THROTTLE_MIN.setEnabled(RB_THROTTLE.isSelected());
        TF_ASSIST_THROTTLE_MAX.setEnabled(RB_THROTTLE.isSelected());
    }//GEN-LAST:event_RB_ADC_OPTION_DISStateChanged

    private void TF_DATA_1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_DATA_1KeyReleased
        try {
            int index = Integer.parseInt(TF_DATA_1.getText());
            if ((index >= 0)&&(index <= 10)) {
                jLabelData1.setText("Data 1 - " + displayDataArray[index]); }
            else {
                jLabelData1.setText("Data 1");	}
        }
        catch(NumberFormatException ex){
            jLabelData1.setText("Data 1");
        }
    }//GEN-LAST:event_TF_DATA_1KeyReleased

    private void TF_DATA_2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_DATA_2KeyReleased
        try {
        int index = Integer.parseInt(TF_DATA_2.getText());
        if ((index >= 0)&&(index <= 10)) {
            jLabelData2.setText("Data 2 - " + displayDataArray[index]); }
        else {
            jLabelData2.setText("Data 2");	}
        }
        catch(NumberFormatException ex){
            jLabelData2.setText("Data 2");
        }
    }//GEN-LAST:event_TF_DATA_2KeyReleased

    private void TF_DATA_3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_DATA_3KeyReleased
        try {
        int index = Integer.parseInt(TF_DATA_3.getText());
        if ((index >= 0)&&(index <= 10)) {
            jLabelData3.setText("Data 3 - " + displayDataArray[index]); }
        else {
            jLabelData3.setText("Data 3");	}
        }
        catch(NumberFormatException ex){
            jLabelData3.setText("Data 3");
        }
    }//GEN-LAST:event_TF_DATA_3KeyReleased

    private void TF_DATA_4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_DATA_4KeyReleased
        try {
        int index = Integer.parseInt(TF_DATA_4.getText());
        if ((index >= 0)&&(index <= 10)) {
            jLabelData4.setText("Data 4 - " + displayDataArray[index]); }
        else {
            jLabelData4.setText("Data 4");	}
        }
        catch(NumberFormatException ex){
            jLabelData4.setText("Data 4");
        }
    }//GEN-LAST:event_TF_DATA_4KeyReleased

    private void TF_DATA_5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_DATA_5KeyReleased
        try {
        int index = Integer.parseInt(TF_DATA_5.getText());
        if ((index >= 0)&&(index <= 10)) {
            jLabelData5.setText("Data 5 - " + displayDataArray[index]); }
        else {
            jLabelData5.setText("Data 5");	}
        }
        catch(NumberFormatException ex){
            jLabelData5.setText("Data 5");
        }
    }//GEN-LAST:event_TF_DATA_5KeyReleased

    private void TF_DATA_6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_DATA_6KeyReleased
        try {
        int index = Integer.parseInt(TF_DATA_6.getText());
        if ((index >= 0)&&(index <= 10)) {
            jLabelData6.setText("Data 6 - " + displayDataArray[index]); }
        else {
            jLabelData6.setText("Data 6");	}
        }
        catch(NumberFormatException ex){
            jLabelData6.setText("Data 6");
        }
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
    }//GEN-LAST:event_TF_LIGHT_MODE_1KeyReleased

    private void TF_LIGHT_MODE_2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_LIGHT_MODE_2KeyReleased
        try {
        int index = Integer.parseInt(TF_LIGHT_MODE_2.getText());
        if ((index >= 0)&&(index <= 9)) {
            jLabelLights2.setText("<html>Mode 2 - " + lightModeArray[Integer.parseInt(TF_LIGHT_MODE_2.getText())] + "</html>"); }
        else {
            jLabelLights2.setText("Mode 2");	}
        }
        catch(NumberFormatException ex){
            jLabelLights2.setText("Mode 2");
        }
    }//GEN-LAST:event_TF_LIGHT_MODE_2KeyReleased

    private void TF_LIGHT_MODE_3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_LIGHT_MODE_3KeyReleased
        try {
        int index = Integer.parseInt(TF_LIGHT_MODE_3.getText());
        if ((index >= 0)&&(index <= 10)) {
            jLabelLights3.setText("<html>Mode 3 - " + lightModeArray[Integer.parseInt(TF_LIGHT_MODE_3.getText())] + "</html>"); }
        else {
            jLabelLights3.setText("Mode 3");	}
        }
        catch(NumberFormatException ex){
            jLabelLights3.setText("Mode 3");
        }
    }//GEN-LAST:event_TF_LIGHT_MODE_3KeyReleased

    private void CB_STREET_POWER_LIMStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_STREET_POWER_LIMStateChanged
        TF_STREET_POWER_LIM.setEnabled(CB_STREET_POWER_LIM.isSelected());
    }//GEN-LAST:event_CB_STREET_POWER_LIMStateChanged

    private void CB_ASS_WITHOUT_PEDStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_ASS_WITHOUT_PEDStateChanged
        TF_ASS_WITHOUT_PED_THRES.setEnabled(CB_ASS_WITHOUT_PED.isSelected());
    }//GEN-LAST:event_CB_ASS_WITHOUT_PEDStateChanged

    private void CB_MAX_SPEED_DISPLAYStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_MAX_SPEED_DISPLAYStateChanged
        TF_MAX_SPEED.setEnabled(!CB_MAX_SPEED_DISPLAY.isSelected());
    }//GEN-LAST:event_CB_MAX_SPEED_DISPLAYStateChanged

    private void CB_LIGHTSStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_LIGHTSStateChanged
        TF_LIGHT_MODE_ON_START.setEnabled(CB_LIGHTS.isSelected());
        TF_LIGHT_MODE_1.setEnabled(CB_LIGHTS.isSelected());
        TF_LIGHT_MODE_2.setEnabled(CB_LIGHTS.isSelected());
        TF_LIGHT_MODE_3.setEnabled(CB_LIGHTS.isSelected());
    }//GEN-LAST:event_CB_LIGHTSStateChanged

    private void CB_WALK_ASSISTStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_WALK_ASSISTStateChanged
        TF_WALK_ASS_1.setEnabled(CB_WALK_ASSIST.isSelected());
        TF_WALK_ASS_2.setEnabled(CB_WALK_ASSIST.isSelected());
        TF_WALK_ASS_3.setEnabled(CB_WALK_ASSIST.isSelected());
        TF_WALK_ASS_4.setEnabled(CB_WALK_ASSIST.isSelected());
        TF_WALK_ASS_SPEED.setEnabled(CB_WALK_ASSIST.isSelected());
        TF_WALK_ASS_TIME.setEnabled(CB_WALK_TIME_ENA.isSelected() && CB_BRAKE_SENSOR.isSelected() && CB_WALK_ASSIST.isSelected());
        CB_WALK_TIME_ENA.setEnabled(CB_BRAKE_SENSOR.isSelected() && CB_WALK_ASSIST.isSelected());
        CB_STREET_WALK.setEnabled(CB_WALK_ASSIST.isSelected());
    }//GEN-LAST:event_CB_WALK_ASSISTStateChanged

    private void CB_AUTO_DISPLAY_DATAStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_AUTO_DISPLAY_DATAStateChanged
        TF_NUM_DATA_AUTO_DISPLAY.setEnabled(CB_AUTO_DISPLAY_DATA.isSelected());
    }//GEN-LAST:event_CB_AUTO_DISPLAY_DATAStateChanged

    private void RB_UNIT_KILOMETERSStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RB_UNIT_KILOMETERSStateChanged
        if (RB_UNIT_KILOMETERS.isSelected()) {
            jLabelMaxSpeed.setText("Max speed offroad mode (km/h)");
            jLabelStreetSpeed.setText("Street speed limit (km/h)");
            jLabelWalkSpeed.setText("Walk assist speed (km/h)");
            jLabelCruiseSpeedUnits.setText("km/h");
            TF_MAX_SPEED.setText(String.valueOf(varMaxSpeed));
            TF_STREET_SPEED_LIM.setText(String.valueOf(varStreetSpeed));
            TF_WALK_ASS_SPEED.setText(String.valueOf(varWalkSpeed));
            TF_CRUISE_SPEED_ENA.setText(String.valueOf(varCruiseSpeed));
            TF_CRUISE_ASS_1.setText(String.valueOf(varCruiseSpeed1));
            TF_CRUISE_ASS_2.setText(String.valueOf(varCruiseSpeed2));
            TF_CRUISE_ASS_3.setText(String.valueOf(varCruiseSpeed3));
            TF_CRUISE_ASS_4.setText(String.valueOf(varCruiseSpeed4));
        }
    }//GEN-LAST:event_RB_UNIT_KILOMETERSStateChanged

    private void RB_UNIT_MILESStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RB_UNIT_MILESStateChanged
        if (RB_UNIT_MILES.isSelected()) {
            jLabelMaxSpeed.setText("Max speed offroad mode (mph)");
            jLabelStreetSpeed.setText("Street speed limit (mph)");
            jLabelWalkSpeed.setText("Walk assist speed (mph)");
            jLabelCruiseSpeedUnits.setText("mph");
            TF_MAX_SPEED.setText(String.valueOf((varMaxSpeed * 10 + 5) / 16));
            TF_STREET_SPEED_LIM.setText(String.valueOf((varStreetSpeed * 10 + 5) / 16));
            TF_WALK_ASS_SPEED.setText(String.valueOf((varWalkSpeed * 10 + 5) / 16));
            TF_CRUISE_SPEED_ENA.setText(String.valueOf((varCruiseSpeed * 10 + 5) / 16));
            TF_CRUISE_ASS_1.setText(String.valueOf((varCruiseSpeed1 * 10 + 5) / 16));
            TF_CRUISE_ASS_2.setText(String.valueOf((varCruiseSpeed2 * 10 + 5) / 16));
            TF_CRUISE_ASS_3.setText(String.valueOf((varCruiseSpeed3 * 10 + 5) / 16));
            TF_CRUISE_ASS_4.setText(String.valueOf((varCruiseSpeed4 * 10 + 5) / 16));
        }
    }//GEN-LAST:event_RB_UNIT_MILESStateChanged

    private void TF_MAX_SPEEDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_MAX_SPEEDKeyReleased
        if (RB_UNIT_KILOMETERS.isSelected()) {
            varMaxSpeed = Integer.parseInt(TF_MAX_SPEED.getText());
        }
        if (RB_UNIT_MILES.isSelected()) {
            varMaxSpeed = Integer.parseInt(TF_MAX_SPEED.getText()) * 16 / 10;
        }
    }//GEN-LAST:event_TF_MAX_SPEEDKeyReleased

    private void TF_STREET_SPEED_LIMKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_STREET_SPEED_LIMKeyReleased
        if (RB_UNIT_KILOMETERS.isSelected()) {
            varStreetSpeed = Integer.parseInt(TF_STREET_SPEED_LIM.getText());
        }
        if (RB_UNIT_MILES.isSelected()) {
            varStreetSpeed = Integer.parseInt(TF_STREET_SPEED_LIM.getText()) * 16 / 10;
        }
    }//GEN-LAST:event_TF_STREET_SPEED_LIMKeyReleased

    private void TF_NUM_DATA_AUTO_DISPLAYKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_NUM_DATA_AUTO_DISPLAYKeyReleased
        if (Integer.parseInt(TF_NUM_DATA_AUTO_DISPLAY.getText()) > 6) {
            TF_NUM_DATA_AUTO_DISPLAY.setText("6");
        }
        if (Integer.parseInt(TF_NUM_DATA_AUTO_DISPLAY.getText()) == 0) {
            TF_NUM_DATA_AUTO_DISPLAY.setText("1");
        }
    }//GEN-LAST:event_TF_NUM_DATA_AUTO_DISPLAYKeyReleased

    private void CB_TORQUE_CALIBRATIONStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CB_TORQUE_CALIBRATIONStateChanged
        TF_TORQ_ADC_OFFSET.setEnabled(CB_TORQUE_CALIBRATION.isSelected());
        TF_TORQUE_ADC_MAX.setEnabled(CB_TORQUE_CALIBRATION.isSelected());
    }//GEN-LAST:event_CB_TORQUE_CALIBRATIONStateChanged

    private void RB_STARTUP_VOLTSStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RB_STARTUP_VOLTSStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_RB_STARTUP_VOLTSStateChanged

    private void RB_STARTUP_SOCStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RB_STARTUP_SOCStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_RB_STARTUP_SOCStateChanged

    private void RB_STARTUP_NONEStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RB_STARTUP_NONEStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_RB_STARTUP_NONEStateChanged

    private void RB_HYBRID_ON_STARTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RB_HYBRID_ON_STARTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RB_HYBRID_ON_STARTActionPerformed

    private void TF_WALK_ASS_SPEEDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_WALK_ASS_SPEEDKeyReleased
        if (RB_UNIT_KILOMETERS.isSelected()) {
            varWalkSpeed = Integer.parseInt(TF_WALK_ASS_SPEED.getText());
        }
        if (RB_UNIT_MILES.isSelected()) {
            varWalkSpeed = Integer.parseInt(TF_WALK_ASS_SPEED.getText()) * 16 / 10;
        }
    }//GEN-LAST:event_TF_WALK_ASS_SPEEDKeyReleased

    private void TF_CRUISE_ASS_1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_CRUISE_ASS_1KeyReleased
        if (RB_UNIT_KILOMETERS.isSelected()) {
            varCruiseSpeed1 = Integer.parseInt(TF_CRUISE_ASS_1.getText());
        }
        if (RB_UNIT_MILES.isSelected()) {
            varCruiseSpeed1 = Integer.parseInt(TF_CRUISE_ASS_1.getText()) * 16 / 10;
        }
    }//GEN-LAST:event_TF_CRUISE_ASS_1KeyReleased

    private void TF_CRUISE_ASS_2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_CRUISE_ASS_2KeyReleased
        if (RB_UNIT_KILOMETERS.isSelected()) {
            varCruiseSpeed2 = Integer.parseInt(TF_CRUISE_ASS_2.getText());
        }
        if (RB_UNIT_MILES.isSelected()) {
            varCruiseSpeed2 = Integer.parseInt(TF_CRUISE_ASS_2.getText()) * 16 / 10;
        }
    }//GEN-LAST:event_TF_CRUISE_ASS_2KeyReleased

    private void TF_CRUISE_ASS_3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_CRUISE_ASS_3KeyReleased
        if (RB_UNIT_KILOMETERS.isSelected()) {
            varCruiseSpeed3 = Integer.parseInt(TF_CRUISE_ASS_3.getText());
        }
        if (RB_UNIT_MILES.isSelected()) {
            varCruiseSpeed3 = Integer.parseInt(TF_CRUISE_ASS_3.getText()) * 16 / 10;
        }
    }//GEN-LAST:event_TF_CRUISE_ASS_3KeyReleased

    private void TF_CRUISE_ASS_4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_CRUISE_ASS_4KeyReleased
        if (RB_UNIT_KILOMETERS.isSelected()) {
            varCruiseSpeed4 = Integer.parseInt(TF_CRUISE_ASS_4.getText());
        }
        if (RB_UNIT_MILES.isSelected()) {
            varCruiseSpeed4 = Integer.parseInt(TF_CRUISE_ASS_4.getText()) * 16 / 10;
        }
    }//GEN-LAST:event_TF_CRUISE_ASS_4KeyReleased

    private void TF_CRUISE_SPEED_ENAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TF_CRUISE_SPEED_ENAKeyReleased
        if (RB_UNIT_KILOMETERS.isSelected()) {
            varCruiseSpeed = Integer.parseInt(TF_CRUISE_SPEED_ENA.getText());
        }
        if (RB_UNIT_MILES.isSelected()) {
            varCruiseSpeed = Integer.parseInt(TF_CRUISE_SPEED_ENA.getText()) * 16 / 10;
        }
    }//GEN-LAST:event_TF_CRUISE_SPEED_ENAKeyReleased

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
    private javax.swing.JCheckBox CB_COASTER_BRAKE;
    private javax.swing.JCheckBox CB_CRUISE_WHITOUT_PED;
    private javax.swing.JCheckBox CB_FIELD_WEAKENING_ON_START;
    private javax.swing.JCheckBox CB_LIGHTS;
    private javax.swing.JCheckBox CB_MAX_SPEED_DISPLAY;
    private javax.swing.JCheckBox CB_MOTOR_FAST_STOP;
    private javax.swing.JCheckBox CB_ODO_COMPENSATION;
    private javax.swing.JCheckBox CB_SET_PARAM_ON_START;
    private javax.swing.JCheckBox CB_STARTUP_BOOST_ON_START;
    private javax.swing.JCheckBox CB_STREET_CRUISE;
    private javax.swing.JCheckBox CB_STREET_MODE_ON_START;
    private javax.swing.JCheckBox CB_STREET_POWER_LIM;
    private javax.swing.JCheckBox CB_STREET_THROTTLE;
    private javax.swing.JCheckBox CB_STREET_WALK;
    private javax.swing.JCheckBox CB_TEMP_ERR_MIN_LIM;
    private javax.swing.JCheckBox CB_TORQUE_CALIBRATION;
    private javax.swing.JCheckBox CB_TOR_SENSOR_ADV;
    private javax.swing.JCheckBox CB_WALK_ASSIST;
    private javax.swing.JCheckBox CB_WALK_TIME_ENA;
    private javax.swing.JLabel LB_LAST_COMMIT;
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
    private javax.swing.JRadioButton RB_HYBRID_ON_START;
    private javax.swing.JRadioButton RB_MOTOR_36V;
    private javax.swing.JRadioButton RB_MOTOR_48V;
    private javax.swing.JRadioButton RB_POWER_ON_START;
    private javax.swing.JRadioButton RB_STARTUP_NONE;
    private javax.swing.JRadioButton RB_STARTUP_SOC;
    private javax.swing.JRadioButton RB_STARTUP_VOLTS;
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
    private javax.swing.JTextField TF_ASSIST_THROTTLE_MAX;
    private javax.swing.JTextField TF_ASSIST_THROTTLE_MIN;
    private javax.swing.JTextField TF_ASS_WITHOUT_PED_THRES;
    private javax.swing.JTextField TF_BATT_CAPACITY;
    private javax.swing.JTextField TF_BATT_CAPACITY_CAL;
    private javax.swing.JTextField TF_BATT_NUM_CELLS;
    private javax.swing.JTextField TF_BATT_POW_MAX;
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
    private javax.swing.JTextField TF_BOOST_CADENCE_STEP;
    private javax.swing.JTextField TF_BOOST_TORQUE_FACTOR;
    private javax.swing.JTextField TF_CADENCE_ASS_1;
    private javax.swing.JTextField TF_CADENCE_ASS_2;
    private javax.swing.JTextField TF_CADENCE_ASS_3;
    private javax.swing.JTextField TF_CADENCE_ASS_4;
    private javax.swing.JTextField TF_COASTER_BRAKE_THRESHOLD;
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
    private javax.swing.JTextField TF_MOTOR_DEC;
    private javax.swing.JTextField TF_NUM_DATA_AUTO_DISPLAY;
    private javax.swing.JTextField TF_POWER_ASS_1;
    private javax.swing.JTextField TF_POWER_ASS_2;
    private javax.swing.JTextField TF_POWER_ASS_3;
    private javax.swing.JTextField TF_POWER_ASS_4;
    private javax.swing.JTextField TF_STREET_POWER_LIM;
    private javax.swing.JTextField TF_STREET_SPEED_LIM;
    private javax.swing.JTextField TF_TEMP_MAX_LIM;
    private javax.swing.JTextField TF_TEMP_MIN_LIM;
    private javax.swing.JTextField TF_TORQUE_ADC_MAX;
    private javax.swing.JTextField TF_TORQUE_ASS_1;
    private javax.swing.JTextField TF_TORQUE_ASS_2;
    private javax.swing.JTextField TF_TORQUE_ASS_3;
    private javax.swing.JTextField TF_TORQUE_ASS_4;
    private javax.swing.JTextField TF_TORQ_ADC_OFFSET;
    private javax.swing.JTextField TF_TORQ_PER_ADC_STEP;
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
    private javax.swing.ButtonGroup buttonGroup7;
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
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JLabel jLabelCoasterBrakeThreshld;
    private javax.swing.JLabel jLabelCruiseSpeedUnits;
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
    private javax.swing.JLabel jLabelMaxSpeed;
    private javax.swing.JLabel jLabelMotorFastStop;
    private javax.swing.JLabel jLabelStreetSpeed;
    private javax.swing.JLabel jLabelWalkSpeed;
    private javax.swing.JLabel jLabel_TORQ_ADC_MAX;
    private javax.swing.JLabel jLabel_TORQ_ADC_OFFSET;
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
    private javax.swing.JPanel jPanel22;
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
