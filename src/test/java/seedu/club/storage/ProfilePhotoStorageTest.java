//@@author amrut-prabhu
package seedu.club.storage;

import static org.junit.Assert.assertTrue;
import static seedu.club.storage.ProfilePhotoStorage.PHOTO_FILE_EXTENSION;
import static seedu.club.storage.ProfilePhotoStorage.SAVE_PHOTO_DIRECTORY;

import java.io.File;
import java.io.InputStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.commons.exceptions.PhotoWriteException;

public class ProfilePhotoStorageTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void copyProfilePhoto_invalidPath_exceptionThrown() throws Exception {
        thrown.expect(PhotoReadException.class);
        ProfilePhotoStorage profilePhotoStorage = new ProfilePhotoStorage();

        String invalidPhotoPath = testFolder.getRoot().getPath() + "invalidFile.xyz";
        profilePhotoStorage.copyOriginalPhotoFile(invalidPhotoPath, "dummyName");
    }

    /**
     * Ensures no exception is thrown and command happens successfully.
     */
    @Test
    public void copyProfilePhoto_validPath_success() throws Exception {
        String photoPath = null;
        String copyName = "testCopy";

        ProfilePhotoStorage profilePhotoStorage = new ProfilePhotoStorage();
        photoPath = testFolder.newFile("testPhoto.png").getAbsolutePath();
        profilePhotoStorage.copyOriginalPhotoFile(photoPath, copyName);
        assertTrue(new File(SAVE_PHOTO_DIRECTORY + copyName + PHOTO_FILE_EXTENSION).exists());
    }

    /**
     * Ensures exception is thrown.
     */
    @Test
    public void copyProfilePhoto_validPath_exceptionThrown() throws Exception {
        thrown.expect(PhotoWriteException.class);
        ProfilePhotoStorageExceptionThrowingStub profilePhotoStorage = new ProfilePhotoStorageExceptionThrowingStub();
        String photoPath = testFolder.newFile("testPhoto.png").getAbsolutePath();
        profilePhotoStorage.copyOriginalPhotoFile(photoPath, "testCopy");
    }

    /**
     * A Stub class to throw an exception when the createPhotoFileCopy method is called.
     */
    class ProfilePhotoStorageExceptionThrowingStub extends ProfilePhotoStorage {
        @Override
        public void createPhotoFileCopy(InputStream photoInputStream, String newPath) throws PhotoWriteException {
            throw new PhotoWriteException("dummy exception");
        }
    }

}
