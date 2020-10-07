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

import org.sleuthkit.autopsy.ingest.DataSourceIngestModule;
import org.sleuthkit.autopsy.ingest.IngestModuleFactory;
import org.sleuthkit.autopsy.ingest.IngestModuleFactoryAdapter;
import org.sleuthkit.autopsy.ingest.IngestModuleIngestJobSettings;
import org.openide.util.lookup.ServiceProvider;
import org.sleuthkit.autopsy.ingest.IngestModuleIngestJobSettingsPanel;

@ServiceProvider(service = IngestModuleFactory.class)
public class CameraFingerprintModuleFactory extends IngestModuleFactoryAdapter {

    private static final String moduleName = "Camera Fingerprint PRNU";
    private final String VERSION_NUMBER = "1.0";    
    private final String displayName = "Camera Fingerprint PRNU";
    private final String description = "Module check if a photo was truly taken by a suspected camera or not.";

    public CameraFingerprintModuleFactory() {
        super();
    }

    public static String getModuleName() {
        return moduleName;
    }

    @Override
    public String getModuleDisplayName() {
        return displayName;
    }

    @Override
    public String getModuleDescription() {
        return description;
    }

    @Override
    public String getModuleVersionNumber() {
        return VERSION_NUMBER;
    }

    @Override
    public boolean hasIngestJobSettingsPanel() {
        return true;
    }

    @Override
    public IngestModuleIngestJobSettings getDefaultIngestJobSettings() {
        return new CameraFingerprintModuleIngestJobSettings();
    }

    @Override
    public IngestModuleIngestJobSettingsPanel getIngestJobSettingsPanel(IngestModuleIngestJobSettings settings) {
        if (!(settings instanceof CameraFingerprintModuleIngestJobSettings)) {
            throw new IllegalArgumentException("Expected settings argument to be instanceof CameraFingerprintModuleIngestJobSettings");
        }
        return new CameraFingerprintModuleIngestJobSettingsPanel((CameraFingerprintModuleIngestJobSettings) settings);
    }

    @Override
    public boolean isDataSourceIngestModuleFactory() {
        return true;
    }

    @Override
    public DataSourceIngestModule createDataSourceIngestModule(IngestModuleIngestJobSettings settings) {
        if (!(settings instanceof CameraFingerprintModuleIngestJobSettings)) {
            throw new IllegalArgumentException("Expected settings argument to be instanceof CameraFingerprintModuleIngestJobSettings");
        }
        return new CameraFingerprintIngestModule((CameraFingerprintModuleIngestJobSettings) settings);
    }

}
