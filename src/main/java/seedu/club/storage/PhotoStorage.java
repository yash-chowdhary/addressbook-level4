//@@author amrut-prabhu
package seedu.club.storage;

import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.commons.exceptions.PhotoWriteException;

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
     * @throws PhotoReadException if the {@code originalPhotoPath} is invalid.
     * @throws PhotoWriteException if there was an error while copying the photo.
     */
    void copyOriginalPhotoFile(String originalPhotoPath, String newPhotoName)
            throws PhotoReadException, PhotoWriteException;

}
