package seedu.club.storage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import seedu.club.commons.events.model.ClubBookChangedEvent;
import seedu.club.commons.events.model.NewExportDataAvailableEvent;
import seedu.club.commons.events.model.ProfilePhotoChangedEvent;
import seedu.club.commons.events.storage.DataSavingExceptionEvent;
import seedu.club.commons.exceptions.DataConversionException;
import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.commons.exceptions.PhotoWriteException;
import seedu.club.model.ReadOnlyClubBook;
import seedu.club.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ClubBookStorage, UserPrefsStorage, PhotoStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getClubBookFilePath();

    @Override
    Optional<ReadOnlyClubBook> readClubBook() throws DataConversionException, IOException;

    @Override
    void saveClubBook(ReadOnlyClubBook clubBook) throws IOException;

    /**
     * Saves the current version of the Club Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleClubBookChangedEvent(ClubBookChangedEvent cbce);

    //@@author amrut-prabhu

    /**
     * Makes a copy of the image specified by {@code originalPhotoPath} with the {@code newPhotoName}.
     *
     * @param originalPhotoPath The absolute file path of the {@link seedu.club.model.member.ProfilePhoto}.
     * @param newPhotoName The file name of the copy of the photo specified by {@code originalPhotoPath}.
     *
     * @throws PhotoReadException if the {@code originalPhotoPath} is invalid.
     * @throws PhotoWriteException if there was an error while copying the photo.
     */
    void copyOriginalPhotoFile(String originalPhotoPath, String newPhotoName)
            throws PhotoReadException, PhotoWriteException;

    /**
     * Saves a copy of the newly added photo to Club Connect's resources.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleProfilePictureChangedEvent(ProfilePhotoChangedEvent event);

    /**
     * Writes the {@code data} to the {@code exportFile}.
     *
     * @param data Data that is to be written to the file.
     * @param exportFile File to which data is to be exported.
     * @throws IOException Thrown if there is an error writing to the file.
     */
    void exportDataToFile(String data, File exportFile) throws IOException;

    /**
     * Writes data to a CSV file on the hard disk.
     * Raises {@link DataSavingExceptionEvent} if there was an error during writing.
     */
    void handleExportDataEvent(NewExportDataAvailableEvent event);
    //@@author
}
