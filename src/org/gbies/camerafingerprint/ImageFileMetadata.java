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

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.GpsDirectory;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.openide.util.Exceptions;

public class ImageFileMetadata {

    private final File imageFile;
    private String make;
    private String model;
    private String resolution;
    private double latitude;
    private double longitude;

    public ImageFileMetadata(File imageFile) {
        this.imageFile = imageFile;
    }

    public void readMetadata() {

        try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(imageFile))) {
            Metadata metadata = ImageMetadataReader.readMetadata(bin);
            ExifIFD0Directory devDir = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

            if (devDir != null) {
                make = devDir.getString(ExifIFD0Directory.TAG_MAKE);
                model = devDir.getString(ExifIFD0Directory.TAG_MODEL);
            }

            GpsDirectory gpsDir = metadata.getFirstDirectoryOfType(GpsDirectory.class);
            if (gpsDir != null) {
                GeoLocation loc = gpsDir.getGeoLocation();
                if (loc != null) {
                    latitude = loc.getLatitude();
                    longitude = loc.getLongitude();
                }
            }

        } catch (IOException | ImageProcessingException ex) {
            Exceptions.printStackTrace(ex);
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(imageFile));
            resolution = bufferedImage.getWidth() + " x " + bufferedImage.getHeight();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getResolution() {
        return resolution;
    }

}
