package seedu.club.model.member;

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.AppUtil.checkArgument;

/**
 * Represents a member's profile photo in the club book.
 */
public class ProfilePhoto {
    public static final String MESSAGE_PHOTO_PATH_CONSTRAINTS =
            "the photo path should follow the format of this example: C://Downloads//mypic.png";

    /*
     * The first character of the club must be a single alphabet. It is followed by ":", then "\\",
     * a directory whose name consists of alphabets and/or digits, followed by a "." and the file type.
     */
    public static final String IMAGE_PATH_VALIDATION_REGEX = "(.*/)*.+\\\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP)$";
    //https://stackoverflow.com/questions/7102890/java-regex-for-image-filename
    //"([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?";  https://stackoverflow.com/questions/4489582/java-regular-expression-to-match-file-path
    //([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)  http://www.mkyong.com/regular-expressions/how-to-validate-image-file-extension-with-regular-expression/

    private String photoFilePath;

    /**
     * Constructs a {@code ProfilePhoto}.
     *
     * @param path A valid image path.
     */
    public ProfilePhoto(String path) {
        requireNonNull(path);
        checkArgument(isValidPhotoPath(path), IMAGE_PATH_VALIDATION_REGEX);
        this.photoFilePath = path;
    }

    /**
     * Returns true if a given string is a valid photo path.
     */
    public static boolean isValidPhotoPath(String test) {
        return test.matches(IMAGE_PATH_VALIDATION_REGEX);
    }

    /**
     * Sets the {@code photoFilePath} to the specified {@code path}.
     * @param path A valid image path
     */
    //TODO: make valid image path within ClubConnect Directory for this method.
    public void setPhotoPath(String path) {
        requireNonNull(path);
        checkArgument(isValidPhotoPath(path), IMAGE_PATH_VALIDATION_REGEX);
        this.photoFilePath = path;
    }

    public String getPhotoPath() {
        return photoFilePath;
    }

    @Override
    public String toString() {
        return photoFilePath;
    }

    @Override
    public int hashCode() {
        return photoFilePath.hashCode();
    }

}
