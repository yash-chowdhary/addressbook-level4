//@@author amrut-prabhu
package seedu.club.storage;

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
    void copyOriginalPhotoFile(String originalPhotoPath, String newPhotoName)
            throws PhotoException;

}
