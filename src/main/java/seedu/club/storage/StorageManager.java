package seedu.club.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.club.commons.core.ComponentManager;
import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.events.model.ClubBookChangedEvent;
import seedu.club.commons.events.storage.DataSavingExceptionEvent;
import seedu.club.commons.exceptions.DataConversionException;
import seedu.club.model.ReadOnlyClubBook;
import seedu.club.model.UserPrefs;

/**
 * Manages storage of ClubBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ClubBookStorage clubBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ClubBookStorage clubBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.clubBookStorage = clubBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public String getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ ClubBook methods ==============================

    @Override
    public String getAddressBookFilePath() {
        return clubBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyClubBook> readClubBook() throws DataConversionException, IOException {
        return readClubBook(clubBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyClubBook> readClubBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return clubBookStorage.readClubBook(filePath);
    }

    @Override
    public void saveClubBook(ReadOnlyClubBook addressBook) throws IOException {
        saveClubBook(addressBook, clubBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveClubBook(ReadOnlyClubBook addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        clubBookStorage.saveClubBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(ClubBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveClubBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
