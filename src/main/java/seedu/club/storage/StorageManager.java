package seedu.club.storage;

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
    private ProfilePhotoStorage profilePhotoStorage;
    private  CsvClubBookStorage csvClubBookStorage;

    public StorageManager(ClubBookStorage clubBookStorage, UserPrefsStorage userPrefsStorage,
                          ProfilePhotoStorage profilePhotoStorage, CsvClubBookStorage csvClubBookStorage) {
        super();
        this.clubBookStorage = clubBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.profilePhotoStorage = profilePhotoStorage;
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
        profilePhotoStorage.copyOriginalPhotoFile(originalPath, newPhotoName);
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
    //@@author

    //@@author amrut-prabhu
    // ================ CSV Storage methods ==============================

    /**
     * Writes {@code content} to the export file.
     * @param content Data that is to be appended to the export file.
     * @throws IOException when there is an error writing to the file.
     */
    private void exportData(String content) throws IOException {
        logger.fine("Attempting to export data to file: " + csvClubBookStorage.getClubBookFile());
        csvClubBookStorage.saveData(content);
    }

    @Override
    @Subscribe
    public void handleExportDataEvent(NewExportDataAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Adding member data to file"));

        if (event.exportFile != null) {
            csvClubBookStorage.setClubBookFile(event.exportFile);
        }
        try {
            if (event.data != null) {
                exportData(event.data);
            }
        } catch (IOException e) {
            event.setFileChanged(false);
            raise(new DataSavingExceptionEvent(e));
        }
    }
    //@@author
}
