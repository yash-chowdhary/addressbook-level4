package seedu.club.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.club.commons.events.model.ClubBookChangedEvent;
import seedu.club.commons.events.model.NewExportDataAvailableEvent;
import seedu.club.commons.events.model.ProfilePhotoChangedEvent;
import seedu.club.commons.events.storage.DataReadingExceptionEvent;
import seedu.club.commons.events.storage.DataSavingExceptionEvent;
import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.commons.exceptions.PhotoWriteException;
import seedu.club.model.ClubBook;
import seedu.club.model.ReadOnlyClubBook;
import seedu.club.model.UserPrefs;
import seedu.club.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;


    @Before
    public void setUp() {
        XmlClubBookStorage clubBookStorage = new XmlClubBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        ProfilePhotoStorage profilePhotoStorage = new ProfilePhotoStorage();
        CsvClubBookStorage csvClubBookStorage = new CsvClubBookStorage();
        storageManager = new StorageManager(clubBookStorage, userPrefsStorage, profilePhotoStorage, csvClubBookStorage);
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void clubBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlClubBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlClubBookStorageTest} class.
         */
        ClubBook original = getTypicalClubBook();
        storageManager.saveClubBook(original);
        ReadOnlyClubBook retrieved = storageManager.readClubBook().get();
        assertEquals(original, new ClubBook(retrieved));
    }

    @Test
    public void getClubBookFilePath() {
        assertNotNull(storageManager.getClubBookFilePath());
    }

    @Test
    public void handleClubBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlClubBookStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"), new ProfilePhotoStorage(),
                                             new CsvClubBookStorage());
        storage.handleClubBookChangedEvent(new ClubBookChangedEvent(new ClubBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    //@@author amrut-prabhu
    @Test
    public void handleProfilePictureChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the copy Photo method is called
        Storage storage = new StorageManager(new XmlClubBookStorage("dummy"),
                new JsonUserPrefsStorage("dummy"), new ProfilePhotoStorageExceptionThrowingStub(),
                new CsvClubBookStorage());

        File photoFile = new File("./src/test/resources/photos/");
        String photoPath = photoFile.getAbsolutePath();
        storage.handleProfilePictureChangedEvent(new ProfilePhotoChangedEvent(photoPath, "testPhotoCopy.png"));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataReadingExceptionEvent);
    }

    @Test
    public void handleExportDataEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the exportData method is called
        Storage storage = new StorageManager(new XmlClubBookStorage("dummy"),
                new JsonUserPrefsStorage("dummy"), new ProfilePhotoStorage(),
                new CsvClubBookStorageExceptionThrowingStub());

        File dummyFile = new File("./src/test/exportFile.csv");
        storage.handleExportDataEvent(new NewExportDataAvailableEvent(dummyFile));
        storage.handleExportDataEvent(new NewExportDataAvailableEvent("dummy data"));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }
    //@@author


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlClubBookStorageExceptionThrowingStub extends XmlClubBookStorage {

        public XmlClubBookStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveClubBook(ReadOnlyClubBook clubBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    //@@author amrut-prabhu
    /**
     * A Stub class to throw an exception when the copy photo method is called
     */
    class ProfilePhotoStorageExceptionThrowingStub extends ProfilePhotoStorage {

        @Override
        public void copyOriginalPhotoFile(String originalFilePath, String newName)
                throws PhotoReadException, PhotoWriteException {
            throw new PhotoReadException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save data method is called
     */
    class CsvClubBookStorageExceptionThrowingStub extends CsvClubBookStorage {

        @Override
        public void saveData(String data) throws IOException {
            throw new IOException("dummy exception");
        }
    }
    //@@author

}
