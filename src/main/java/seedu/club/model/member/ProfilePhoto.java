//@@author amrut-prabhu
package seedu.club.model.member;

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.AppUtil.checkArgument;

/**
 * Represents a member's profile photo in the club book.
 */
public class ProfilePhoto {
    public static final String MESSAGE_PHOTO_PATH_CONSTRAINTS =
            "the photo path should follow the format of this example: C://Downloads//...//mypic.png";

    /*
     * The first character of the club must be a single alphabet. It is followed by ":", then "\\",
     * a directory whose name consists of alphabets and/or digits, followed by a "." and the file type.
     */
    public static final String IMAGE_PATH_VALIDATION_REGEX = "(.*/)*.+\\\\.(png|jpg|jpeg|PNG|JPG)$";
    //https://stackoverflow.com/questions/7102890/java-regex-for-image-filename
    //"([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?";  https://stackoverflow.com/questions/4489582/
    // java-regular-expression-to-match-file-path
    //([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)  http://www.mkyong.com/regular-expressions/
    // how-to-validate-image-file-extension-with-regular-expression/

    public final String originalPhotoFilePath;
    //Note: The actual image file used in the application UI is a copy of this image stored in Club Connect's
    // resources as "MATRIC_NUM.png".

    private String profilePhotoPath = "/photos/default.png";

    /**
     * Constructs a {@code ProfilePhoto}.
     *
     * @param path A valid image path.
     */
    public ProfilePhoto(String path) {
        //requireNonNull(path);
        //checkArgument(isValidProfilePhoto(path), IMAGE_PATH_VALIDATION_REGEX);
        this.originalPhotoFilePath = path;
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
     *
     * @param path A valid image path.
     */
    //TODO: make valid image path within ClubConnect Directory for this method.
    public void setPhotoPath(String path) {
        requireNonNull(path);
        checkArgument(isValidProfilePhoto(path), IMAGE_PATH_VALIDATION_REGEX);
        //this.originalPhotoFilePath = path;
    }

    public String getPhotoPath() {
        return originalPhotoFilePath;
    }

    @Override
    public String toString() {
        return originalPhotoFilePath;
    }

    @Override
    public int hashCode() {
        return originalPhotoFilePath.hashCode();
    }

}
