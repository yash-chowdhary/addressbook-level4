//@@author amrut-prabhu
package seedu.club.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.exceptions.DataConversionException;
import seedu.club.commons.util.FileUtil;
import seedu.club.model.ClubBook;
import seedu.club.model.ReadOnlyClubBook;

/**
 * A class to manage storage of ClubBook data as an csv file on the hard disk.
 */
public class CsvClubBookStorage {

    private static final Logger logger = LogsCenter.getLogger(CsvClubBookStorage.class);

    private File file;

    public CsvClubBookStorage() {
        this.file = null;
    }

    public File getClubBookFile() {
        return this.file;
    }

    public void setClubBookFile(File file) {
        this.file = file;
        try {
            FileUtil.createIfMissing(file);
        } catch (IOException ioe) {
            logger.warning("Error creating file " + file.getAbsolutePath());
        }
    }

    public Optional<ReadOnlyClubBook> readClubBook() throws DataConversionException, IOException {
        return readClubBook(file);
    }

    /**
     * Similar to {@link #readClubBook()}
     * @param clubBookFile location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyClubBook> readClubBook(File clubBookFile)
            throws DataConversionException, FileNotFoundException {

        requireNonNull(clubBookFile);

        if (!clubBookFile.exists()) {
            logger.info("ClubBook import file "  + clubBookFile + " not found");
            return Optional.empty();
        }

        //XmlSerializableClubBook xmlClubBook = XmlFileStorage.loadDataFromSaveFile(new File(file));
        ClubBook newClubBook = new ClubBook();
        //try {
        return Optional.of(newClubBook);
        /*
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + clubBookFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
        */
    }

    public void saveData(String data) throws IOException {
        saveData(data, file);
    }

    /**
     * @param file location of the data. Cannot be null
     */
    public void saveData(String data, File file) throws IOException {
        requireNonNull(data);
        requireNonNull(file);

        assert file.exists() : "ClubBook export file " + file + " is guaranteed to exist";

        CsvFileStorage.saveDataToFile(file, data);
    }
}
