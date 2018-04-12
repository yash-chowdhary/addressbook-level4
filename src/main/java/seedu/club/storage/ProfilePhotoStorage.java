//@@author amrut-prabhu
package seedu.club.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.commons.exceptions.PhotoWriteException;
import seedu.club.commons.util.FileUtil;

/**
 * To copy the profile photo to this application's resources.
 */
public class ProfilePhotoStorage implements  PhotoStorage {

    public static final String PHOTO_FILE_EXTENSION = ".bmp";
    public static final String SAVE_PHOTO_DIRECTORY = "photos/";

    private static final String URL_PREFIX = "file:///";

    private static final Logger logger = LogsCenter.getLogger(ProfilePhotoStorage.class);

    @Override
    public void copyOriginalPhotoFile(String originalPhotoPath, String newPhotoName)
            throws PhotoReadException, PhotoWriteException {
        String newPath = null;
        try {
            logger.info("Profile Photo is being read from " + originalPhotoPath);

            URL photoUrl = new URL(URL_PREFIX + originalPhotoPath);
            newPath = SAVE_PHOTO_DIRECTORY + newPhotoName + PHOTO_FILE_EXTENSION;
            InputStream photoStream = photoUrl.openStream();

            createPhotoFileCopy(photoStream, newPath);
        } catch (PhotoWriteException pwe) {
            logger.info("Error while writing photo file");
            throw new PhotoWriteException(newPath);
        } catch (IOException ioe) {
            logger.info("Error while reading photo file");
            throw new PhotoReadException(originalPhotoPath);
        }
    }

    /**
     * Creates a copy the given {@code originalPhoto} in the application's resources.
     * @throws PhotoWriteException if there was any problem writing to the file.
     */
    public void createPhotoFileCopy(InputStream photoStream, String newPath) throws PhotoWriteException {
        logger.info("Profile Photo is being copied to " + newPath);
        try {
            FileUtil.createDirs(new File(SAVE_PHOTO_DIRECTORY));
            Files.copy(photoStream, Paths.get(newPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new PhotoWriteException(newPath);
        }
        logger.info("Profile Photo copying successful");
    }
}
