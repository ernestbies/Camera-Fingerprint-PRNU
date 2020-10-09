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
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import org.openide.modules.InstalledFileLocator;
import org.openide.util.Exceptions;
import org.sleuthkit.autopsy.casemodule.Case;
import org.sleuthkit.autopsy.casemodule.services.FileManager;
import org.sleuthkit.autopsy.coreutils.Logger;
import org.sleuthkit.autopsy.coreutils.MessageNotifyUtil;
import org.sleuthkit.autopsy.ingest.DataSourceIngestModule;
import org.sleuthkit.autopsy.ingest.DataSourceIngestModuleProgress;
import org.sleuthkit.autopsy.ingest.IngestJobContext;
import org.sleuthkit.autopsy.ingest.IngestMessage;
import org.sleuthkit.autopsy.ingest.IngestModule;
import org.sleuthkit.autopsy.ingest.IngestServices;
import org.sleuthkit.datamodel.AbstractFile;
import org.sleuthkit.datamodel.Content;
import org.sleuthkit.datamodel.TskCoreException;

public class CameraFingerprintIngestModule implements DataSourceIngestModule {

    private IngestJobContext context;
    private final Logger logger = IngestServices.getInstance().getLogger(CameraFingerprintModuleFactory.getModuleName());
    private final CameraFingerprintModuleIngestJobSettings settings;
    public static File prnuFile;

    public CameraFingerprintIngestModule(CameraFingerprintModuleIngestJobSettings settings) {
        this.settings = settings;
    }

    @Override
    public void startUp(IngestJobContext context) throws IngestModuleException {
        this.context = context;
        prnuFile = InstalledFileLocator.getDefault().locate("prnu.exe", "org.gbies.camerafingerprint", false);
    }

    @Override
    public ProcessResult process(Content dataSource, DataSourceIngestModuleProgress progressBar) {
        IngestMessage message;

        logger.log(Level.INFO, "Startup Camera Fingerprint PRNU Ingest Module");
        message = IngestMessage.createMessage(IngestMessage.MessageType.DATA, "Camera Fingerprint PRNU", "Start task.");
        IngestServices.getInstance().postMessage(message);
        
        progressBar.switchToIndeterminate();
        FileManager fileManager = Case.getCurrentCase().getServices().getFileManager();        
        CameraFingerprintProcess cameraFingerprintProcess = new CameraFingerprintProcess(settings);

        if (!settings.isCreateFingerprint()) {
            progressBar.progress("Camera Fingerprint PRNU: Create fingerprint... " + settings.getFingerprintName());

            try {
                cameraFingerprintProcess.createCameraFingerprint();
                logger.log(Level.INFO, "Created fingerprint {0}", settings.getFingerprintName());
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
                return ProcessResult.ERROR;
            }
        } else {
            if (!settings.getFingerprintFile().exists()) {
                logger.log(Level.SEVERE, "Can't find camera fingerprint file {0}", settings.getFingerprintFile());
                MessageNotifyUtil.Notify.error("Error Message", "Can't find camera fingerprint file. See log for details!");
                throw new CameraFingerprintException("Can't find camera fingerprint file");
            }
        }

        List<AbstractFile> imageFiles;
        try {
            imageFiles = fileManager.findFiles(dataSource, "%.jpg");
        } catch (TskCoreException ex) {
            logger.log(Level.INFO, "No image files .jpg in DataSource: {0}", ex.getMessage());
            return ProcessResult.OK;
        }

        cameraFingerprintProcess.createArtifact();        
        
        //Matching files in separate threads
        ForkJoinPool executor = ForkJoinPool.commonPool(); 
        
        for (AbstractFile imageFile : imageFiles) {
            
            FileMatchingThread fileMatchingThread = new FileMatchingThread(imageFile, cameraFingerprintProcess);
            executor.execute(fileMatchingThread);

            if (context.dataSourceIngestIsCancelled()) {
                return IngestModule.ProcessResult.OK;
            }
        }
                
        int tasksCount = imageFiles.size();
        progressBar.switchToDeterminate(tasksCount);
        executor.shutdown();
        
        int currentTasks, previousTasks = 0;
        while (executor.getActiveThreadCount() != 0) {
            currentTasks = tasksCount - executor.getQueuedSubmissionCount();
            
            if (currentTasks != previousTasks) {
                String infoProgress = "(" + currentTasks + "/" + tasksCount + ")";
                progressBar.progress("Camera Fingerprint PRNU: Process of matching files... " + infoProgress, currentTasks);
                previousTasks = currentTasks;
            }

            if (context.dataSourceIngestIsCancelled()) {
                return IngestModule.ProcessResult.OK;
            }
        }

        logger.log(Level.INFO, "Matching files is complete.");

        message = IngestMessage.createMessage(IngestMessage.MessageType.DATA, "Camera Fingerprint PRNU", "End task.");
        IngestServices.getInstance().postMessage(message);

        return ProcessResult.OK;
    }

}
