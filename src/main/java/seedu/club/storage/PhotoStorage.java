//@@author amrut-prabhu
package seedu.club.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import seedu.club.commons.exceptions.PhotoException;

/**
 * Represents a storage for the file specfied by {@link seedu.club.model.member.ProfilePhoto}.
 */
public interface PhotoStorage {

    /**
     * Returns UserPrefs data from storage.
     *
     * @param originalPhotoPath The absolute file path of the {@link seedu.club.model.member.ProfilePhoto}.
     * @param newPhotoName The file name of the copy of the photo specified by {@code originalPhotoPath}.
     *
     * @throws PhotoException if the {@code originalPhotoPath} is invalid.
     */
    void readOriginalPhotoFile(String originalPhotoPath, String newPhotoName)
            throws PhotoException;

    /**
     * Creates a copy the given {@code originalPhoto} in the application's resources.
     * @throws IOException if there was any problem writing to the file.
     */
    void createPhotoFileCopy(BufferedImage originalPhoto, File newPath) throws IOException;

}
