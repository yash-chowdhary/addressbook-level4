//@@author amrut-prabhu
package seedu.club.model.member;

import static java.util.Objects.requireNonNull;

import seedu.club.storage.ProfilePhotoStorage;

/**
 * Represents a member's profile photo in the club book.
 */
public class ProfilePhoto {

    public static final String MESSAGE_PHOTO_PATH_CONSTRAINTS =
            "the photo path should follow the format of this example: C:/Downloads/.../mypic.png";

    /*
     * The first character of the club must be a single alphabet. It is followed by ":", then "\\",
     * a directory whose name consists of alphabets and/or digits, followed by a "." and the file type.
     */
    public static final String IMAGE_PATH_VALIDATION_REGEX = ".:(.*/)*.+/.+(png|jpg|jpeg|PNG|JPG)";
    //Matches C:/Users/Amrut Prabhu/Desktop/My Timetable (1).png
    //https://www.freeformatter.com/java-regex-tester.html#ad-output
    public static final String DEFAULT_PHOTO_NAME = "default";

    private String profilePhotoPath;

    /**
     * Constructs a {@code ProfilePhoto}.
     */
    public ProfilePhoto() {
        this(ProfilePhotoStorage.SAVE_PHOTO_DIRECTORY + DEFAULT_PHOTO_NAME + ProfilePhotoStorage.FILE_EXTENSION);
    }

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

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfilePhoto // instanceof handles nulls
                && this.getProfilePhotoPath().equals(((ProfilePhoto) other).getProfilePhotoPath())); // state check
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
