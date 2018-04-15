package seedu.club.storage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.club.commons.core.ComponentManager;
import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.events.model.ClubBookChangedEvent;
import seedu.club.commons.events.model.NewExportDataAvailableEvent;
import seedu.club.commons.events.model.ProfilePhotoChangedEvent;
import seedu.club.commons.events.storage.DataReadingExceptionEvent;
import seedu.club.commons.events.storage.DataSavingExceptionEvent;
import seedu.club.commons.exceptions.DataConversionException;
import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.commons.exceptions.PhotoWriteException;
import seedu.club.model.ReadOnlyClubBook;
import seedu.club.model.UserPrefs;

/**
 * Manages storage of ClubBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ClubBookStorage clubBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private PhotoStorage photoStorage;
    private  CsvClubBookStorage csvClubBookStorage;

    public StorageManager(ClubBookStorage clubBookStorage, UserPrefsStorage userPrefsStorage,
                          PhotoStorage photoStorage, CsvClubBookStorage csvClubBookStorage) {
        super();
        this.clubBookStorage = clubBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.photoStorage = photoStorage;
        this.csvClubBookStorage = csvClubBookStorage;
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
    public String getClubBookFilePath() {
        return clubBookStorage.getClubBookFilePath();
    }

    @Override
    public Optional<ReadOnlyClubBook> readClubBook() throws DataConversionException, IOException {
        return readClubBook(clubBookStorage.getClubBookFilePath());
    }

    @Override
    public Optional<ReadOnlyClubBook> readClubBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return clubBookStorage.readClubBook(filePath);
    }

    @Override
    public void saveClubBook(ReadOnlyClubBook clubBook) throws IOException {
        saveClubBook(clubBook, clubBookStorage.getClubBookFilePath());
    }

    @Override
    public void saveClubBook(ReadOnlyClubBook clubBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        clubBookStorage.saveClubBook(clubBook, filePath);
    }

    @Override
    @Subscribe
    public void handleClubBookChangedEvent(ClubBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveClubBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }


    //@@author amrut-prabhu
    // ================ ProfilePhoto methods ==============================

    @Override
    public void copyOriginalPhotoFile(String originalPath, String newPhotoName)
            throws PhotoReadException, PhotoWriteException {
        logger.fine("Attempting to read photo from file: " + originalPath);
        photoStorage.copyOriginalPhotoFile(originalPath, newPhotoName);
    }

    @Override
    @Subscribe
    public void handleProfilePictureChangedEvent(ProfilePhotoChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Profile photo changed, copying file"));
        try {
            copyOriginalPhotoFile(event.originalPhotoPath, event.newFileName);
        } catch (PhotoReadException pre) {
            event.setPhotoChanged(false);
            raise(new DataReadingExceptionEvent(pre));
        } catch (PhotoWriteException pwe) {
            raise(new DataSavingExceptionEvent(pwe));
        }
    }


    // ================ CSV Storage methods ==============================

    @Override
    public void exportDataToFile(String data, File exportFile) throws IOException {
        csvClubBookStorage.setClubBookFile(exportFile);
        logger.fine("Attempting to export data to file: " + csvClubBookStorage.getClubBookFile());
        csvClubBookStorage.saveData(data);
    }

    @Override
    @Subscribe
    public void handleExportDataEvent(NewExportDataAvailableEvent event) {
        assert event.exportFile != null : "exportFile should be pointing to a valid file";

        try {
            exportDataToFile(event.data, event.exportFile);
        } catch (IOException e) {
            event.setDataExported(false);
            raise(new DataSavingExceptionEvent(e));
        }
    }
    //@@author
}
