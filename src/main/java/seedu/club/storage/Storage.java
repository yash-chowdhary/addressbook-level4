package seedu.club.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.club.commons.events.model.ClubBookChangedEvent;
import seedu.club.commons.events.storage.DataSavingExceptionEvent;
import seedu.club.commons.exceptions.DataConversionException;
import seedu.club.model.ReadOnlyClubBook;
import seedu.club.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ClubBookStorage, UserPrefsStorage {

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
}
