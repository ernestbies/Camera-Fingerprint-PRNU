/* 
 * Copyright (C) 2020 Grzegorz Bieś, Ernest Bieś
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Camera Fingerprint PRNU Autopsy Module, version  1.0
 *
 */

package org.gbies.camerafingerprint;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FilenameUtils;
import org.sleuthkit.autopsy.ingest.IngestModuleIngestJobSettings;
import org.sleuthkit.autopsy.ingest.IngestModuleIngestJobSettingsPanel;    

public class CameraFingerprintModuleIngestJobSettingsPanel extends IngestModuleIngestJobSettingsPanel {
    private final CameraFingerprintModuleIngestJobSettings settings;
    private final int MIN_FINGERPRINT_SIZE = 720;
    private final int MAX_FINGERPRINT_SIZE = 2120;
        
    public CameraFingerprintModuleIngestJobSettingsPanel(CameraFingerprintModuleIngestJobSettings settings) {
        this.settings = settings;
        
        initComponents();                
    }  

                
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelCreateFingerprint = new javax.swing.JPanel();
        jTextFieldImagesDirectory = new javax.swing.JTextField();
        jTextFieldFingerprintName = new javax.swing.JTextField();
        jButtonImagesDirectory = new javax.swing.JButton();
        jLabelFingerprintName = new javax.swing.JLabel();
        jRadioButtonCreateFingerprint = new javax.swing.JRadioButton();
        jSliderFingerprintSize = new javax.swing.JSlider();
        jLabelFingerprintSize = new javax.swing.JLabel();
        jLabelSpeed = new javax.swing.JLabel();
        jPanelLoadFile = new javax.swing.JPanel();
        jTextFieldCameraFingerprintFile = new javax.swing.JTextField();
        jButtonLoadFile = new javax.swing.JButton();
        jRadioButtonLoadFile = new javax.swing.JRadioButton();
        jCheckBoxAllFilesOnList = new javax.swing.JCheckBox();

        jPanelCreateFingerprint.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), org.openide.util.NbBundle.getMessage(CameraFingerprintModuleIngestJobSettingsPanel.class, "CameraFingerprintModuleIngestJobSettingsPanel.jPanelCreateFingerprint.border.title"))); // NOI18N

        jTextFieldImagesDirectory.setEditable(false);
        jTextFieldImagesDirectory.setText(org.openide.util.NbBundle.getMessage(CameraFingerprintModuleIngestJobSettingsPanel.class, "CameraFingerprintModuleIngestJobSettingsPanel.jTextFieldImagesDirectory.text")); // NOI18N

        jTextFieldFingerprintName.setText(org.openide.util.NbBundle.getMessage(CameraFingerprintModuleIngestJobSettingsPanel.class, "CameraFingerprintModuleIngestJobSettingsPanel.jTextFieldFingerprintName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButtonImagesDirectory, org.openide.util.NbBundle.getMessage(CameraFingerprintModuleIngestJobSettingsPanel.class, "CameraFingerprintModuleIngestJobSettingsPanel.jButtonImagesDirectory.text")); // NOI18N
        jButtonImagesDirectory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImagesDirectoryActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabelFingerprintName, org.openide.util.NbBundle.getMessage(CameraFingerprintModuleIngestJobSettingsPanel.class, "CameraFingerprintModuleIngestJobSettingsPanel.jLabelFingerprintName.text")); // NOI18N

        jRadioButtonCreateFingerprint.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButtonCreateFingerprint, org.openide.util.NbBundle.getMessage(CameraFingerprintModuleIngestJobSettingsPanel.class, "CameraFingerprintModuleIngestJobSettingsPanel.jRadioButtonCreateFingerprint.text")); // NOI18N
        jRadioButtonCreateFingerprint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonCreateFingerprintActionPerformed(evt);
            }
        });

        jSliderFingerprintSize.setMinorTickSpacing(5);
        jSliderFingerprintSize.setSnapToTicks(true);
        jSliderFingerprintSize.setValue(0);

        org.openide.awt.Mnemonics.setLocalizedText(jLabelFingerprintSize, org.openide.util.NbBundle.getMessage(CameraFingerprintModuleIngestJobSettingsPanel.class, "CameraFingerprintModuleIngestJobSettingsPanel.jLabelFingerprintSize.text")); // NOI18N

        jLabelSpeed.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabelSpeed, org.openide.util.NbBundle.getMessage(CameraFingerprintModuleIngestJobSettingsPanel.class, "CameraFingerprintModuleIngestJobSettingsPanel.jLabelSpeed.text")); // NOI18N

        javax.swing.GroupLayout jPanelCreateFingerprintLayout = new javax.swing.GroupLayout(jPanelCreateFingerprint);
        jPanelCreateFingerprint.setLayout(jPanelCreateFingerprintLayout);
        jPanelCreateFingerprintLayout.setHorizontalGroup(
            jPanelCreateFingerprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCreateFingerprintLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButtonCreateFingerprint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelCreateFingerprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCreateFingerprintLayout.createSequentialGroup()
                        .addComponent(jLabelFingerprintName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldFingerprintName, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelCreateFingerprintLayout.createSequentialGroup()
                        .addComponent(jTextFieldImagesDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonImagesDirectory))
                    .addGroup(jPanelCreateFingerprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelCreateFingerprintLayout.createSequentialGroup()
                            .addComponent(jLabelFingerprintSize)
                            .addGap(18, 18, 18)
                            .addComponent(jSliderFingerprintSize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanelCreateFingerprintLayout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(jLabelSpeed))))
                .addGap(51, 51, 51))
        );
        jPanelCreateFingerprintLayout.setVerticalGroup(
            jPanelCreateFingerprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCreateFingerprintLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelCreateFingerprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButtonCreateFingerprint)
                    .addGroup(jPanelCreateFingerprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldImagesDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonImagesDirectory)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCreateFingerprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFingerprintName)
                    .addComponent(jTextFieldFingerprintName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanelCreateFingerprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelFingerprintSize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSliderFingerprintSize, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSpeed))
        );

        jPanelLoadFile.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), org.openide.util.NbBundle.getMessage(CameraFingerprintModuleIngestJobSettingsPanel.class, "CameraFingerprintModuleIngestJobSettingsPanel.jPanelLoadFile.border.title"))); // NOI18N
        jPanelLoadFile.setEnabled(false);

        jTextFieldCameraFingerprintFile.setEditable(false);
        jTextFieldCameraFingerprintFile.setText(org.openide.util.NbBundle.getMessage(CameraFingerprintModuleIngestJobSettingsPanel.class, "CameraFingerprintModuleIngestJobSettingsPanel.jTextFieldCameraFingerprintFile.text")); // NOI18N
        jTextFieldCameraFingerprintFile.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(jButtonLoadFile, org.openide.util.NbBundle.getMessage(CameraFingerprintModuleIngestJobSettingsPanel.class, "CameraFingerprintModuleIngestJobSettingsPanel.jButtonLoadFile.text")); // NOI18N
        jButtonLoadFile.setEnabled(false);
        jButtonLoadFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoadFileActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jRadioButtonLoadFile, org.openide.util.NbBundle.getMessage(CameraFingerprintModuleIngestJobSettingsPanel.class, "CameraFingerprintModuleIngestJobSettingsPanel.jRadioButtonLoadFile.text")); // NOI18N
        jRadioButtonLoadFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonLoadFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLoadFileLayout = new javax.swing.GroupLayout(jPanelLoadFile);
        jPanelLoadFile.setLayout(jPanelLoadFileLayout);
        jPanelLoadFileLayout.setHorizontalGroup(
            jPanelLoadFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoadFileLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButtonLoadFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldCameraFingerprintFile, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonLoadFile, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelLoadFileLayout.setVerticalGroup(
            jPanelLoadFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoadFileLayout.createSequentialGroup()
                .addGroup(jPanelLoadFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLoadFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldCameraFingerprintFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonLoadFile))
                    .addComponent(jRadioButtonLoadFile))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jCheckBoxAllFilesOnList.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxAllFilesOnList, org.openide.util.NbBundle.getMessage(CameraFingerprintModuleIngestJobSettingsPanel.class, "CameraFingerprintModuleIngestJobSettingsPanel.jCheckBoxAllFilesOnList.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jCheckBoxAllFilesOnList)
            .addComponent(jPanelLoadFile, javax.swing.GroupLayout.PREFERRED_SIZE, 321, Short.MAX_VALUE)
            .addComponent(jPanelCreateFingerprint, javax.swing.GroupLayout.PREFERRED_SIZE, 321, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelCreateFingerprint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelLoadFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jCheckBoxAllFilesOnList)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonImagesDirectoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImagesDirectoryActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);        
        
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {            
            String imagesDirectory = chooser.getSelectedFile().getAbsolutePath();
            jTextFieldImagesDirectory.setText(imagesDirectory);            
        }
        
    }//GEN-LAST:event_jButtonImagesDirectoryActionPerformed

    private void jButtonLoadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoadFileActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        FileFilter filter = new FileNameExtensionFilter("CFP Camera fingerprint", "cfp");        
        chooser.addChoosableFileFilter(filter);
        chooser.setFileFilter(filter);
        
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {            
            String fingerprintFile = chooser.getSelectedFile().getAbsolutePath();
            jTextFieldCameraFingerprintFile.setText(fingerprintFile);                        
        }
    }//GEN-LAST:event_jButtonLoadFileActionPerformed

    private void jRadioButtonCreateFingerprintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonCreateFingerprintActionPerformed
        setComponents(true);
    }//GEN-LAST:event_jRadioButtonCreateFingerprintActionPerformed

    private void jRadioButtonLoadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonLoadFileActionPerformed
        setComponents(false);
    }//GEN-LAST:event_jRadioButtonLoadFileActionPerformed

    @Override
    public IngestModuleIngestJobSettings getSettings() {
        
        settings.setAllFilesOnList(jCheckBoxAllFilesOnList.isSelected());
        settings.setFingerprintSize(calculateFingerprintSize());                
        settings.setDirectoryImages(new File(jTextFieldImagesDirectory.getText()));
        settings.setFingerprintFile(new File(jTextFieldCameraFingerprintFile.getText()));
        if(jRadioButtonCreateFingerprint.isSelected()){            
            if("".equals(jTextFieldFingerprintName.getText())){
                settings.setFingerprintName("My camera");
            } else {
                settings.setFingerprintName(jTextFieldFingerprintName.getText());
            } 
            settings.setCreateFingerprint(false);
        } else {
            settings.setFingerprintName(FilenameUtils.getBaseName(jTextFieldCameraFingerprintFile.getText()));            
            settings.setCreateFingerprint(true);
        }
        return settings;
    }
    
    private void setComponents(boolean isCreateCameraFingerprintPanel){
        if (isCreateCameraFingerprintPanel) {
            jPanelCreateFingerprint.setEnabled(true);
            jButtonImagesDirectory.setEnabled(true);
            jLabelFingerprintName.setEnabled(true);
            jTextFieldFingerprintName.setEditable(true);
            jRadioButtonCreateFingerprint.setSelected(true);
            jRadioButtonLoadFile.setSelected(false);
            jPanelLoadFile.setEnabled(false);
            jTextFieldCameraFingerprintFile.setEditable(false);
            jButtonLoadFile.setEnabled(false);
            jTextFieldImagesDirectory.setText("");
            jSliderFingerprintSize.setEnabled(true);
            jLabelFingerprintSize.setEnabled(true);
            jLabelSpeed.setEnabled(true);
            
        } else {
            jPanelCreateFingerprint.setEnabled(false);
            jButtonImagesDirectory.setEnabled(false);
            jLabelFingerprintName.setEnabled(false);
            jTextFieldFingerprintName.setEditable(false);
            jRadioButtonCreateFingerprint.setSelected(false);
            jRadioButtonLoadFile.setSelected(true);
            jPanelLoadFile.setEnabled(true);
            jButtonLoadFile.setEnabled(true);
            jTextFieldCameraFingerprintFile.setText("");
            jSliderFingerprintSize.setEnabled(false);
            jLabelFingerprintSize.setEnabled(false);
            jLabelSpeed.setEnabled(false);
        }
    }
        
    private int calculateFingerprintSize(){
        return MIN_FINGERPRINT_SIZE + (jSliderFingerprintSize.getValue() * (MAX_FINGERPRINT_SIZE - MIN_FINGERPRINT_SIZE) / 100);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonImagesDirectory;
    private javax.swing.JButton jButtonLoadFile;
    private javax.swing.JCheckBox jCheckBoxAllFilesOnList;
    private javax.swing.JLabel jLabelFingerprintName;
    private javax.swing.JLabel jLabelFingerprintSize;
    private javax.swing.JLabel jLabelSpeed;
    private javax.swing.JPanel jPanelCreateFingerprint;
    private javax.swing.JPanel jPanelLoadFile;
    private javax.swing.JRadioButton jRadioButtonCreateFingerprint;
    private javax.swing.JRadioButton jRadioButtonLoadFile;
    private javax.swing.JSlider jSliderFingerprintSize;
    private javax.swing.JTextField jTextFieldCameraFingerprintFile;
    private javax.swing.JTextField jTextFieldFingerprintName;
    private javax.swing.JTextField jTextFieldImagesDirectory;
    // End of variables declaration//GEN-END:variables
}
