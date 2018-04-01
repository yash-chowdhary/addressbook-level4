//@@author amrut-prabhu
package seedu.club.storage;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.commons.exceptions.PhotoWriteException;

public class ProfilePhotoStorageTest {

    private static final String TEST_PHOTO_PATH = new File("./photos/testPhoto.png").getAbsolutePath();

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
     * @throws PhotoReadException not expected
     * @throws PhotoWriteException not expected
     */
    @Test
    public void copyProfilePhoto_validPath_success() throws PhotoReadException, PhotoWriteException {
        Exception expectedException = null;
        try {
            ProfilePhotoStorage profilePhotoStorage = new ProfilePhotoStorage();
            profilePhotoStorage.copyOriginalPhotoFile(TEST_PHOTO_PATH, "testCopy");
        } catch (Exception e) {

            expectedException = e;
        }
        assertEquals(null, expectedException);
    }

    /**
     * Ensures exception is thrown.
     * @throws PhotoReadException not expected
     * @throws PhotoWriteException not expected
     */
    @Test
    public void copyProfilePhoto_validPath_exceptionThrown() throws PhotoReadException, PhotoWriteException {
        thrown.expect(PhotoWriteException.class);
        ProfilePhotoStorageExceptionThrowingStub profilePhotoStorage = new ProfilePhotoStorageExceptionThrowingStub();
        profilePhotoStorage.copyOriginalPhotoFile(TEST_PHOTO_PATH, "testCopy");
    }

    /**
     * A Stub class to throw an exception when the createPhotoFileCopy method is called.
     */
    class ProfilePhotoStorageExceptionThrowingStub extends ProfilePhotoStorage {

        @Override
        public void createPhotoFileCopy(BufferedImage originalPhoto, File newPath) throws PhotoWriteException {
            throw new PhotoWriteException("dummy exception");
        }
    }
}
