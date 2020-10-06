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
import org.sleuthkit.autopsy.ingest.IngestModuleIngestJobSettings;

public class CameraFingerprintModuleIngestJobSettings implements IngestModuleIngestJobSettings {

    private static final long serialVersionUID = 1L;
    private File directoryImages;
    private File fingerprintFile;
    private String fingerprintName;
    private int fingerprintSize;
    private boolean createFingerprint;
    private boolean allFilesOnList;

    
    @Override
    public long getVersionNumber() {
        return serialVersionUID;
    }

    public File getDirectoryImages() {
        return directoryImages;
    }

    public void setDirectoryImages(File directoryImages) {
        this.directoryImages = directoryImages;
    }

    public File getFingerprintFile() {
        return fingerprintFile;
    }

    public void setFingerprintFile(File fingerprintFile) {
        this.fingerprintFile = fingerprintFile;
    }
    
    public String getFingerprintName() {
        return fingerprintName;
    }

    public void setFingerprintName(String fingerprintName) {
        this.fingerprintName = fingerprintName;
    }

    public boolean isCreateFingerprint() {
        return createFingerprint;
    }

    public void setCreateFingerprint(boolean createFingerprint) {
        this.createFingerprint = createFingerprint;
    }

    public boolean isAllFilesOnList() {
        return allFilesOnList;
    }

    public void setAllFilesOnList(boolean allFilesOnList) {
        this.allFilesOnList = allFilesOnList;
    }

    public int getFingerprintSize() {
        return fingerprintSize;
    }

    public void setFingerprintSize(int fingerprintSize) {
        this.fingerprintSize = fingerprintSize;
    }

}
