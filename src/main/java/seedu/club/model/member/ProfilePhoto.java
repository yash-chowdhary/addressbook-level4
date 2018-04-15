//@@author amrut-prabhu
package seedu.club.model.member;

import static java.util.Objects.requireNonNull;

/**
 * Represents a member's profile photo in the club book.
 */
public class ProfilePhoto {

    public static final String EMPTY_STRING = "";
    public static final String DEFAULT_PHOTO_PATH = "/images/defaultProfilePhoto.png";
    public static final String MESSAGE_PHOTO_PATH_CONSTRAINTS =
            "The photo path should be an absolute path to a JPG or PNG image file.\n";

    private static final String[] validFileExtensions = {".jpg", ".png"};
    private static final int JPG_INDEX = 0;
    private static final int PNG_INDEX = 1;

    private String profilePhotoPath;

    /**
     * Constructs a {@code ProfilePhoto}.
     */
    public ProfilePhoto(String path) {
        this.profilePhotoPath = path;
    }

    /**
     * Returns true if {@code path} represents the path of a JPG (.jpg) or PNG (.png) file.
     *
     * @param path Path whose validity is to be checked.
     */
    public static boolean isValidPhotoFile(String path) {
        int length = path.length();
        String fileExtension = path.substring(length - validFileExtensions[JPG_INDEX].length());

        return fileExtension.compareToIgnoreCase(validFileExtensions[JPG_INDEX]) == 0
                || fileExtension.compareToIgnoreCase(validFileExtensions[PNG_INDEX]) == 0;
    }

    /**
     * Sets the {@code photoFilePath} to the specified {@code path}.
     * @param path A valid image path.
     */
    public void setNewPhotoPath(String path) {
        requireNonNull(path);
        this.profilePhotoPath = path;
    }

    public String getPhotoPath() {
        return profilePhotoPath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfilePhoto // instanceof handles nulls
                && this.getPhotoPath().equals(((ProfilePhoto) other).getPhotoPath())); // state check
    }

    @Override
    public String toString() {
        return profilePhotoPath;
    }

    @Override
    public int hashCode() {
        return profilePhotoPath.hashCode();
    }

}
