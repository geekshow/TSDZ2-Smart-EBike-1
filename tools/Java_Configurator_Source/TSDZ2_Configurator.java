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

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.*;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.event.ListDataListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.swing.JCheckBox;
import javax.swing.ListModel;

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

    
    
 
public void loadSettings(File f) throws IOException {
    
     		BufferedReader in = new BufferedReader(new FileReader(f));
                RB_36V.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_48V.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_ASS_WITHOUT_PED.setSelected(Boolean.parseBoolean(in.readLine()));
		RB_VLCD6.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_VLCD5.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_XH18.setSelected(Boolean.parseBoolean(in.readLine()));
                TF_ASS_THRES.setText(in.readLine());
                TF_TORQ_PER_STEP.setText(in.readLine());
                TF_MOTOR_ACC.setText(in.readLine());
                TF_BAT_CUR_MAX.setText(in.readLine());
                TF_BATT_CAP.setText(in.readLine());
                TF_BATT_CELLS.setText(in.readLine());
                TF_BATT_CUT.setText(in.readLine());
                TF_WHEEL_CF.setText(in.readLine());
                TF_MAX_SPEED.setText(in.readLine());

		in.close();
	}   

public void AddListItem(File newFile) {
        
        experimentalSettingsFilesModel.add(0, new FileContainer(newFile));
    
       // ListModel<String> Liste = expSet.getModel();
        expSet.repaint();
        JOptionPane.showMessageDialog(null,experimentalSettingsFilesModel.toString(),"Titel", JOptionPane.PLAIN_MESSAGE);
}
    
    public TSDZ2_Configurator() {
        initComponents();

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
         
         
         jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				{

					int n = JOptionPane.showConfirmDialog(
							null,
							"Don't forget to save your changes in notepad!",
							"",
							JOptionPane.OK_CANCEL_OPTION);

					if (n == JOptionPane.YES_OPTION) {
						try {
							Process process = Runtime.getRuntime().exec("cmd /c start edit_advanced");
						} catch (IOException e1) {
							TF_ASS_THRES.setText("Error");
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Goodbye");
					}

					// Saving code here
				}
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
                                        
                                        if (RB_36V.isSelected()) {
						text_to_save = "#define MOTOR_TYPE 1";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_36V.isSelected());

                                        if (RB_48V.isSelected()) {
						text_to_save = "#define MOTOR_TYPE 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_48V.isSelected());

                                        if (RB_ASS_WITHOUT_PED.isSelected()) {
						text_to_save = "#define MOTOR_ASSISTANCE_WITHOUT_PEDAL_ROTATION 1";
						pWriter.println(text_to_save);
					}
                                        else {
						text_to_save = "#define MOTOR_ASSISTANCE_WITHOUT_PEDAL_ROTATION 0";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_ASS_WITHOUT_PED.isSelected());

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
                                        
                                        text_to_save = "#define ASSISTANCE_WITHOUT_PEDAL_ROTATION_THRESHOLD " + TF_ASS_THRES.getText();
                                        iWriter.println(TF_ASS_THRES.getText());
					pWriter.println(text_to_save); 
                                        
                                        text_to_save = "#define PEDAL_TORQUE_PER_10_BIT_ADC_STEP_X100 " + TF_TORQ_PER_STEP.getText();
                                        iWriter.println(TF_TORQ_PER_STEP.getText());
					pWriter.println(text_to_save); 
                                        
                                        text_to_save = "#define MOTOR_ACCELERATION  " + TF_MOTOR_ACC.getText();
                                        iWriter.println(TF_MOTOR_ACC.getText());
					pWriter.println(text_to_save); 
                                        
                                        text_to_save = "#define BATTERY_CURRENT_MAX " + TF_BAT_CUR_MAX.getText();
                                        iWriter.println(TF_BAT_CUR_MAX.getText());
					pWriter.println(text_to_save);                                        
                                        
                                        text_to_save = "#define TARGET_MAX_BATTERY_POWER " + TF_BATT_CAP.getText();
                                        iWriter.println(TF_BATT_CAP.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define BATTERY_CELLS_NUMBER " + TF_BATT_CELLS.getText();
                                        iWriter.println(TF_BATT_CELLS.getText());
					pWriter.println(text_to_save); 
                                        
                                        text_to_save = "#define BATTERY_LOW_VOLTAGE_CUT_OFF " + TF_BATT_CUT.getText();
                                        iWriter.println(TF_BATT_CUT.getText());
					pWriter.println(text_to_save); 
                                        
                                        text_to_save = "#define WHEEL_PERIMETER " + TF_WHEEL_CF.getText();
                                        iWriter.println(TF_WHEEL_CF.getText());
					pWriter.println(text_to_save);
                                        
                                        text_to_save = "#define WHEEL_MAX_SPEED " + TF_MAX_SPEED.getText();
                                        iWriter.println(TF_MAX_SPEED.getText());
					pWriter.println(text_to_save);
                                        
                                        pWriter.println("\r\n#endif /* CONFIG_H_ */");

					iWriter.close();
 				} catch (IOException ioe) {
					ioe.printStackTrace();
				} finally {
					if (pWriter != null) {
						pWriter.flush();
						pWriter.close();

					}
				}  
                                try {
					Process process = Runtime.getRuntime().exec("cmd /c start compile_20");
				} catch (IOException e1) {
					TF_ASS_THRES.setText("Error");
					e1.printStackTrace();
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        Label_Parameter1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        TF_MOTOR_ACC = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        TF_ASS_THRES = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        TF_TORQ_PER_STEP = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        TF_BAT_CUR_MAX = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        TF_BATT_CAP = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        TF_BATT_CELLS = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        TF_BATT_CUT = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        TF_WHEEL_CF = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        TF_MAX_SPEED = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        RB_VLCD6 = new javax.swing.JRadioButton();
        RB_VLCD5 = new javax.swing.JRadioButton();
        RB_XH18 = new javax.swing.JRadioButton();
        RB_36V = new javax.swing.JRadioButton();
        RB_48V = new javax.swing.JRadioButton();
        RB_ASS_WITHOUT_PED = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        TF_ADVANCED_EXAMPLE = new javax.swing.JTextField();
        Label_Param3 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        label1 = new java.awt.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        expSet = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        provSet = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Label_Parameter1.setText("Motortype");

        jLabel3.setText("Motor acceleration");

        TF_MOTOR_ACC.setText("25");

        jLabel4.setText("Assist without pedaling");

        jLabel5.setText("Assist threshold");

        TF_ASS_THRES.setText("20");

        jLabel6.setText("Torque per ADC step");

        TF_TORQ_PER_STEP.setText("67");

        jLabel7.setText("Motor settings");

        jLabel8.setText("Battery settings");

        jLabel9.setText("Battery Current Max");

        TF_BAT_CUR_MAX.setText("17");

        jLabel10.setText("Battery capacity");

        TF_BATT_CAP.setText("630");

        jLabel11.setText("Battery cells number");

        TF_BATT_CELLS.setText("10");

        jLabel12.setText("Battery cut off");

        TF_BATT_CUT.setText("29");

        jLabel13.setText("Bike settings");

        jLabel14.setText("Wheel circumference");

        TF_WHEEL_CF.setText("2280");

        jLabel15.setText("max Speed");

        TF_MAX_SPEED.setText("45");

        jLabel16.setText("Display settings");

        buttonGroup1.add(RB_VLCD6);
        RB_VLCD6.setText("VLCD6");

        buttonGroup1.add(RB_VLCD5);
        RB_VLCD5.setText("VLCD5");

        buttonGroup1.add(RB_XH18);
        RB_XH18.setText("XH18");

        buttonGroup2.add(RB_36V);
        RB_36V.setText("36V");

        buttonGroup2.add(RB_48V);
        RB_48V.setText("48V");

        RB_ASS_WITHOUT_PED.setText("enabled");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(Label_Parameter1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(56, 56, 56)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RB_36V)
                    .addComponent(RB_48V)
                    .addComponent(RB_ASS_WITHOUT_PED)
                    .addComponent(TF_ASS_THRES, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TF_TORQ_PER_STEP, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TF_MOTOR_ACC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TF_BAT_CUR_MAX)
                    .addComponent(TF_BATT_CAP)
                    .addComponent(TF_BATT_CELLS)
                    .addComponent(TF_BATT_CUT, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(94, 94, 94)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RB_XH18)
                    .addComponent(RB_VLCD5)
                    .addComponent(jLabel13)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TF_MAX_SPEED)
                            .addComponent(TF_WHEEL_CF, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)))
                    .addComponent(jLabel16)
                    .addComponent(RB_VLCD6))
                .addGap(61, 61, 61))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel7)
                .addGap(772, 772, 772))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(111, 111, 111))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Label_Parameter1)
                            .addComponent(jLabel14)
                            .addComponent(TF_WHEEL_CF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RB_36V, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(TF_BAT_CUR_MAX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RB_48V, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TF_BATT_CAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(TF_MAX_SPEED, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(RB_ASS_WITHOUT_PED)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TF_BATT_CELLS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(TF_ASS_THRES, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TF_BATT_CUT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(RB_VLCD6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(TF_TORQ_PER_STEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(TF_MOTOR_ACC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(RB_VLCD5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RB_XH18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(107, 107, 107))
        );

        jTabbedPane1.addTab("Basic settings", jPanel1);

        TF_ADVANCED_EXAMPLE.setText("357");

        Label_Param3.setText("Example Parameter");

        jLabel17.setText("Advanced settings are not implemented in the GUI yet, please use the button \"Advanced Settings\" for editing with notepad.");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(Label_Param3)
                        .addGap(37, 37, 37)
                        .addComponent(TF_ADVANCED_EXAMPLE, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(229, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_ADVANCED_EXAMPLE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_Param3))
                .addGap(35, 35, 35)
                .addComponent(jLabel17)
                .addContainerGap(270, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Advanced settings", jPanel4);

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

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Compile & Flash");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setText("Advanced Settings");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 856, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane1))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(127, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(84, 84, 84))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("MotorConfiguration");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
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
    private javax.swing.JLabel Label_Param3;
    private javax.swing.JLabel Label_Parameter1;
    private javax.swing.JRadioButton RB_36V;
    private javax.swing.JRadioButton RB_48V;
    private javax.swing.JRadioButton RB_ASS_WITHOUT_PED;
    private javax.swing.JRadioButton RB_VLCD5;
    private javax.swing.JRadioButton RB_VLCD6;
    private javax.swing.JRadioButton RB_XH18;
    private javax.swing.JTextField TF_ADVANCED_EXAMPLE;
    private javax.swing.JTextField TF_ASS_THRES;
    private javax.swing.JTextField TF_BATT_CAP;
    private javax.swing.JTextField TF_BATT_CELLS;
    private javax.swing.JTextField TF_BATT_CUT;
    private javax.swing.JTextField TF_BAT_CUR_MAX;
    private javax.swing.JTextField TF_MAX_SPEED;
    private javax.swing.JTextField TF_MOTOR_ACC;
    private javax.swing.JTextField TF_TORQ_PER_STEP;
    private javax.swing.JTextField TF_WHEEL_CF;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JList<String> expSet;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private java.awt.Label label1;
    private javax.swing.JList<String> provSet;
    // End of variables declaration//GEN-END:variables
}
