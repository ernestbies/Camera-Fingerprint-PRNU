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
import java.io.IOException;
import java.util.logging.Level;
import org.openide.util.Exceptions;
import org.sleuthkit.autopsy.casemodule.Case;
import org.sleuthkit.autopsy.coreutils.ExecUtil;
import org.sleuthkit.autopsy.coreutils.Logger;
import org.sleuthkit.autopsy.ingest.IngestServices;
import org.sleuthkit.datamodel.BlackboardArtifact;
import org.sleuthkit.datamodel.BlackboardAttribute;
import org.sleuthkit.datamodel.SleuthkitCase;
import org.sleuthkit.datamodel.TskCoreException;
import org.sleuthkit.datamodel.TskDataException;

public class CameraFingerprintProcess {

    private final CameraFingerprintModuleIngestJobSettings settings;
    private final Logger logger = IngestServices.getInstance().getLogger(CameraFingerprintModuleFactory.getModuleName());
    private BlackboardArtifact.Type artifactType;
    private int artifactID;

    public CameraFingerprintProcess(CameraFingerprintModuleIngestJobSettings settings) {
        this.settings = settings;
    }

    public void createCameraFingerprint() throws IOException {
        File cameraFingerprintFile = new File(settings.getDirectoryImages(), settings.getFingerprintName() + ".cfp");        
        String outputString = null;
        
        try {
            outputString = createFingerprint(cameraFingerprintFile);
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        }

        if (cameraFingerprintFile.exists()) {
            settings.setFingerprintFile(cameraFingerprintFile);
            settings.setCreateFingerprint(true);
            logger.log(Level.INFO, "Created camera fingerprint: {0}", outputString);
        } else {
            logger.log(Level.SEVERE, "Camera fingerprint could not be created: {0}", outputString);
            throw new CameraFingerprintException("Camera fingerprint could not be create. See log for details!");
        }
    }

    public void createArtifact() {
        SleuthkitCase skCase = Case.getCurrentCase().getSleuthkitCase();

        try {
            artifactType = skCase.getArtifactType("TSK_CAMERA_FINGERPRINT");
        } catch (TskCoreException ex) {
            Exceptions.printStackTrace(ex);
        }

        if (artifactType == null) {
            try {
                artifactType = skCase.addBlackboardArtifactType("TSK_CAMERA_FINGERPRINT", "Camera Fingerprint PRNU");
                try {
                    skCase.addArtifactAttributeType("ATTR_CAMERA_FINGERPRINT_NAME", BlackboardAttribute.TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Fingerprint (Size)");
                } catch (TskCoreException | TskDataException ex) {
                    logger.log(Level.SEVERE, "Attribute ATTR_CAMERA_FINGERPRINT_NAME already exists. => {0}", ex.getMessage());
                }
                try {
                    skCase.addArtifactAttributeType("ATTR_CAMERA_FINGERPRINT_MATCHED", BlackboardAttribute.TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Match Result");
                } catch (TskCoreException | TskDataException ex) {
                    logger.log(Level.SEVERE, "Attribute ATTR_CAMERA_FINGERPRINT_MATCHED already exists. => {0}", ex.getMessage());
                }
                try {
                    skCase.addArtifactAttributeType("ATTR_CAMERA_FINGERPRINT_PCE", BlackboardAttribute.TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.DOUBLE, "PCE");
                } catch (TskCoreException | TskDataException ex) {
                    logger.log(Level.SEVERE, "Attribute ATTR_CAMERA_FINGERPRINT_PCE already exists. => {0}", ex.getMessage());
                }
                try {
                    skCase.addArtifactAttributeType("ATTR_CAMERA_FINGERPRINT_CC", BlackboardAttribute.TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.DOUBLE, "Correlation");
                } catch (TskCoreException | TskDataException ex) {
                    logger.log(Level.SEVERE, "Attribute ATTR_CAMERA_FINGERPRINT_CC already exists. => {0}", ex.getMessage());
                }
                try {
                    skCase.addArtifactAttributeType("ATTR_CAMERA_FINGERPRINT_MAKE", BlackboardAttribute.TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Device Make");
                } catch (TskCoreException | TskDataException ex) {
                    logger.log(Level.SEVERE, "Attribute ATTR_CAMERA_FINGERPRINT_MAKE already exists. => {0}", ex.getMessage());
                }
                try {
                    skCase.addArtifactAttributeType("ATTR_CAMERA_FINGERPRINT_MODEL", BlackboardAttribute.TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Device Model");
                } catch (TskCoreException | TskDataException ex) {
                    logger.log(Level.SEVERE, "Attribute ATTR_CAMERA_FINGERPRINT_MODEL already exists. => {0}", ex.getMessage());
                }
                try {
                    skCase.addArtifactAttributeType("ATTR_CAMERA_FINGERPRINT_RESOLUTION", BlackboardAttribute.TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Resolution");
                } catch (TskCoreException | TskDataException ex) {
                    logger.log(Level.SEVERE, "Attribute ATTR_CAMERA_FINGERPRINT_RESOLUTION already exists. => {0}", ex.getMessage());
                }
                try {
                    skCase.addArtifactAttributeType("ATTR_CAMERA_FINGERPRINT_LATITUDE", BlackboardAttribute.ATTRIBUTE_TYPE.TSK_GEO_LATITUDE.getValueType(), "Latitude");
                } catch (TskCoreException | TskDataException ex) {
                    logger.log(Level.SEVERE, "Attribute ATTR_CAMERA_FINGERPRINT_LATITUDE already exists. => {0}", ex.getMessage());
                }
                try {
                    skCase.addArtifactAttributeType("ATTR_CAMERA_FINGERPRINT_LONGITUDE", BlackboardAttribute.ATTRIBUTE_TYPE.TSK_GEO_LONGITUDE.getValueType(), "Longitude");
                } catch (TskCoreException | TskDataException ex) {
                    logger.log(Level.SEVERE, "Attribute ATTR_CAMERA_FINGERPRINT_LONGITUDE already exists. => {0}", ex.getMessage());
                }
                try {
                    skCase.addArtifactAttributeType("ATTR_CAMERA_FINGERPRINT_FILEPATH", BlackboardAttribute.TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "File Path");
                } catch (TskCoreException | TskDataException ex) {
                    logger.log(Level.SEVERE, "Attribute ATTR_CAMERA_FINGERPRINT_FILEPATH already exists. => {0}", ex.getMessage());
                }

                logger.log(Level.INFO, "Adding Artifact for Camera Fingerprint PRNU");

            } catch (TskCoreException | TskDataException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        artifactID = artifactType.getTypeID();
    }

    public int getArtifactID() {
        return artifactID;
    }

    public BlackboardArtifact.Type getArtifactType() {
        return artifactType;
    }
    
    public boolean isAllFilesOnList(){
        return settings.isAllFilesOnList();
    }

    public String getCameraFingerprintName(){
        return settings.getFingerprintName();
    }
    
    public int getCameraFingerprintSize(){
        return settings.getFingerprintSize();
    }
               
    public String createFingerprint(File fingerprintFile) throws IOException, InterruptedException{        
        return processExecution("-c", settings.getDirectoryImages().getAbsolutePath(), "", fingerprintFile.getAbsolutePath(), settings.getFingerprintSize());
    }
    
    public String matchedFile(File matchedFile) throws IOException, InterruptedException{
        String outputString = processExecution("-f", "", matchedFile.getAbsolutePath(), settings.getFingerprintFile().getAbsolutePath(), 0);
        if(outputString.contains("Can't read camera fingerprint")){
            logger.log(Level.SEVERE, outputString);
            throw new CameraFingerprintException("Can't read camera fingerprint!");            
        }
        return outputString;
    }
    
    private String processExecution(String command, String directoryImages, String matchedFilePath, String fingerprintFilePath, int fingerprintSize) throws IOException, InterruptedException{         
        String[] params = {command, directoryImages,  matchedFilePath, fingerprintFilePath, "-m", String.valueOf(fingerprintSize)};
        ExecUtil execUtil = new ExecUtil();
        return execUtil.execute(CameraFingerprintIngestModule.prnuFile.getAbsolutePath(), params);
    }    
}
