//@@author amrut-prabhu
package seedu.club.model.member;

import static java.util.Objects.requireNonNull;

/**
 * Represents a member's profile photo in the club book.
 */
public class ProfilePhoto {

    public static final String MESSAGE_PHOTO_PATH_CONSTRAINTS =
            "the photo path should follow the format of this example: C:/Downloads/.../mypic.png";
    public static final String IMAGE_PATH_VALIDATION_REGEX = ".:(.*/)*.+/.+(png|jpg|jpeg|PNG|JPG)";

    private String profilePhotoPath;

    /**
     * Constructs a {@code ProfilePhoto}.
     *
     * @param path A valid image path.
     */
    public ProfilePhoto(String path) {
        //checkArgument(isValidProfilePhoto(path), IMAGE_PATH_VALIDATION_REGEX);
        this.profilePhotoPath = path;
    }

    /**
     * Returns true if a given string is a valid photo path.
     *
     * @param test Path whose validity is to be checked.
     */
    public static boolean isValidProfilePhoto(String test) {
        return test.matches(IMAGE_PATH_VALIDATION_REGEX);
    }

    /**
     * Sets the {@code photoFilePath} to the specified {@code path}.
     * @param path A valid image path.
     */
    public void setNewPhotoPath(String path) {
        requireNonNull(path);
        //checkArgument(isValidProfilePhoto(path), IMAGE_PATH_VALIDATION_REGEX);
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
