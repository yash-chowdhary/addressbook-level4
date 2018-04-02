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
    public void copyProfilePhoto_validPath_success() {
        Exception expectedException = null;
        try {
            ProfilePhotoStorageStubAcceptingCreateCopy profilePhotoStorage =
                    new ProfilePhotoStorageStubAcceptingCreateCopy();
            String photoPath = testFolder.newFile("testPhoto.png").getAbsolutePath();
            profilePhotoStorage.copyOriginalPhotoFile(photoPath, "testCopy");
        } catch (Exception e) {
            expectedException = e;
        }
        assertEquals(null, expectedException);
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
     * A Stub class that always accepts the createPhotoFileCopy method.
     */
    private class ProfilePhotoStorageStubAcceptingCreateCopy extends ProfilePhotoStorage {

        @Override
        public void createPhotoFileCopy(BufferedImage originalPhoto, File newPath) throws PhotoWriteException {
            return;
        }

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
