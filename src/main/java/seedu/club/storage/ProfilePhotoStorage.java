//@@author amrut-prabhu
package seedu.club.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.commons.exceptions.PhotoWriteException;
import seedu.club.commons.util.FileUtil;

/**
 * To copy the profile photo to this application's resources.
 */
public class ProfilePhotoStorage implements  PhotoStorage {

    public static final String FILE_EXTENSION = ".png";
    public static final String SAVE_PHOTO_DIRECTORY = "photos/";

    private static final String URL_PREFIX = "file:///";

    private static final Logger logger = LogsCenter.getLogger(ProfilePhotoStorage.class);

    @Override
    public void copyOriginalPhotoFile(String originalPhotoPath, String newPhotoName)
            throws PhotoReadException, PhotoWriteException {
        BufferedImage originalPhoto = null;
        File newPath = null;

        try {
            logger.info("Profile Photo is being read from " + originalPhotoPath);

            URL photoUrl = new URL(URL_PREFIX + originalPhotoPath);
            originalPhoto = ImageIO.read(photoUrl);

            String saveAs = newPhotoName + FILE_EXTENSION;
            newPath = new File(SAVE_PHOTO_DIRECTORY + saveAs);

            createPhotoFileCopy(originalPhoto, newPath);
        } catch (PhotoWriteException pwe) {
            logger.info("Error while writing photo file");
            throw new PhotoWriteException(newPath.getAbsolutePath());
        } catch (IOException ioe) {
            logger.info("Error while reading photo file");
            throw new PhotoReadException(originalPhotoPath);
        }
    }

    /**
     * Creates a copy the given {@code originalPhoto} in the application's resources.
     * @throws PhotoWriteException if there was any problem writing to the file.
     */
    public void createPhotoFileCopy(BufferedImage originalPhoto, File newPath) throws PhotoWriteException {
        logger.info("Profile Photo is being copied to " + newPath);
        try {
            FileUtil.createDirs(new File(SAVE_PHOTO_DIRECTORY));
            ImageIO.write(originalPhoto, "png", newPath);
        } catch (IOException ioe) {
            throw new PhotoWriteException(newPath.getAbsolutePath());
        }
        logger.info("Profile Photo copying successful");
    }
}
