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
import org.sleuthkit.autopsy.coreutils.Logger;
import org.sleuthkit.autopsy.datamodel.ContentUtils;
import org.sleuthkit.autopsy.ingest.IngestServices;
import org.sleuthkit.autopsy.ingest.ModuleDataEvent;
import org.sleuthkit.datamodel.AbstractFile;
import org.sleuthkit.datamodel.BlackboardArtifact;
import org.sleuthkit.datamodel.BlackboardAttribute;
import org.sleuthkit.datamodel.SleuthkitCase;
import org.sleuthkit.datamodel.TskCoreException;

public class FileMatchingThread extends Thread {

    private final AbstractFile imageFile;
    private final CameraFingerprintProcess cameraFingerprintProcess;
    private final Logger logger = IngestServices.getInstance().getLogger(CameraFingerprintModuleFactory.getModuleName());

    public FileMatchingThread(AbstractFile imageFile, CameraFingerprintProcess cameraFingerprintProcess) {
        this.cameraFingerprintProcess = cameraFingerprintProcess;
        this.imageFile = imageFile;
    }

    @Override
    public void run() {
        File outFile = new File(Case.getCurrentCase().getCacheDirectory(), imageFile.getId() + "_" + imageFile.getName());

        try {
            ContentUtils.writeToFile(imageFile, outFile);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        try {
            String result = cameraFingerprintProcess.matchedFile(outFile);                       
            String[] data = result.split("\t");
            logger.log(Level.INFO, "Matching file... {0}", result);

            double pce = 0.0;
            try {
                pce = Double.valueOf(data[1]);
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Error while converting PCE = {0}, {1}", new Object[]{data[1], e.getMessage()});
            }
            double correlation = 0.0;
            try {
                correlation = Double.valueOf(data[2]);
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Error while converting Correlation = {0}, {1}", new Object[]{data[1], e.getMessage()});
            }
                        
            try {
                if (pce > 50) {
                    addInterestingItemsToFile();
                    addArtifactToFile(outFile, pce, correlation, data[3]);
                } else {
                    if (cameraFingerprintProcess.isAllFilesOnList()) {    
                        addArtifactToFile(outFile, pce, correlation, data[3]);
                    }
                }
            } catch (TskCoreException ex) {
                Exceptions.printStackTrace(ex);
            }

        } catch (IOException | InterruptedException ex) {
            logger.log(Level.SEVERE, "Can't match file {0}, {1}", new Object[]{imageFile.getName(), ex.getMessage()});
            Exceptions.printStackTrace(ex);
        }

        outFile.delete();
    }
    
    private void addArtifactToFile(File outFile, double pce, double correlation, String fingerprintSize) throws TskCoreException {
        SleuthkitCase skCase = Case.getCurrentCase().getSleuthkitCase();
        ImageFileMetadata imageFileMetadata = new ImageFileMetadata(outFile);
        imageFileMetadata.readMetadata();

        BlackboardArtifact artifact = imageFile.newArtifact(cameraFingerprintProcess.getArtifactID());
        String cameraFingerprintInfo = cameraFingerprintProcess.getCameraFingerprintName() + " (" + fingerprintSize + ")";
        artifact.addAttribute(new BlackboardAttribute(skCase.getAttributeType("ATTR_CAMERA_FINGERPRINT_NAME"), CameraFingerprintModuleFactory.getModuleName(), cameraFingerprintInfo));
        artifact.addAttribute(new BlackboardAttribute(skCase.getAttributeType("ATTR_CAMERA_FINGERPRINT_MATCHED"), CameraFingerprintModuleFactory.getModuleName(), matchedResult(pce)));
        artifact.addAttribute(new BlackboardAttribute(skCase.getAttributeType("ATTR_CAMERA_FINGERPRINT_PCE"), CameraFingerprintModuleFactory.getModuleName(), pce));
        artifact.addAttribute(new BlackboardAttribute(skCase.getAttributeType("ATTR_CAMERA_FINGERPRINT_CC"), CameraFingerprintModuleFactory.getModuleName(), correlation));
        artifact.addAttribute(new BlackboardAttribute(skCase.getAttributeType("ATTR_CAMERA_FINGERPRINT_MAKE"), CameraFingerprintModuleFactory.getModuleName(), imageFileMetadata.getMake()));
        artifact.addAttribute(new BlackboardAttribute(skCase.getAttributeType("ATTR_CAMERA_FINGERPRINT_MODEL"), CameraFingerprintModuleFactory.getModuleName(), imageFileMetadata.getModel()));
        artifact.addAttribute(new BlackboardAttribute(skCase.getAttributeType("ATTR_CAMERA_FINGERPRINT_RESOLUTION"), CameraFingerprintModuleFactory.getModuleName(), imageFileMetadata.getResolution()));
        if (imageFileMetadata.getLatitude() != 0 || imageFileMetadata.getLongitude() != 0) {
            artifact.addAttribute(new BlackboardAttribute(skCase.getAttributeType("ATTR_CAMERA_FINGERPRINT_LATITUDE"), CameraFingerprintModuleFactory.getModuleName(), imageFileMetadata.getLatitude()));
            artifact.addAttribute(new BlackboardAttribute(skCase.getAttributeType("ATTR_CAMERA_FINGERPRINT_LONGITUDE"), CameraFingerprintModuleFactory.getModuleName(), imageFileMetadata.getLongitude()));
        }
        artifact.addAttribute(new BlackboardAttribute(skCase.getAttributeType("ATTR_CAMERA_FINGERPRINT_FILEPATH"), CameraFingerprintModuleFactory.getModuleName(), imageFile.getLocalPath()));

        ModuleDataEvent event = new ModuleDataEvent(CameraFingerprintModuleFactory.getModuleName(), cameraFingerprintProcess.getArtifactType());        
        IngestServices.getInstance().fireModuleDataEvent(event);        

    }

    private String matchedResult(double pce) {
        String result = "No match";

        if (pce > 50) {
            if (pce < 150) {
                result = "Match: Low probability";
            } else if (pce < 500) {
                result = "Match: High probability";
            } else {
                result = "Match: Very high probability";
            }
        }
        return result;
    }

    private void addInterestingItemsToFile() throws TskCoreException {

        BlackboardArtifact artifactInterestingFile = imageFile.newArtifact(BlackboardArtifact.ARTIFACT_TYPE.TSK_INTERESTING_FILE_HIT);
        BlackboardAttribute attributeInterestingFile = new BlackboardAttribute(BlackboardAttribute.ATTRIBUTE_TYPE.TSK_SET_NAME, CameraFingerprintModuleFactory.getModuleName(), "Camera Fingerprint PRNU: " + cameraFingerprintProcess.getCameraFingerprintName());
        artifactInterestingFile.addAttribute(attributeInterestingFile);

        ModuleDataEvent event = new ModuleDataEvent(CameraFingerprintModuleFactory.getModuleName(), BlackboardArtifact.ARTIFACT_TYPE.TSK_INTERESTING_FILE_HIT);
        IngestServices.getInstance().fireModuleDataEvent(event);
    }

}
