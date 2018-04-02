//@@author amrut-prabhu
package seedu.club.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.exceptions.DataConversionException;
import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.commons.util.FileUtil;
import seedu.club.model.member.UniqueMemberList;

/**
 * A class to manage storage of ClubBook data as an csv file on the hard disk.
 */
public class CsvClubBookStorage {

    private static final Logger logger = LogsCenter.getLogger(CsvClubBookStorage.class);

    private File file;

    public CsvClubBookStorage() {
        this.file = null;
    }

    public CsvClubBookStorage(File file) {
        setClubBookFile(file);
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

    public UniqueMemberList readClubBook() throws FileNotFoundException, DataConversionException {
        return readClubBook(file);
    }

    /**
     * Similar to {@link #readClubBook()}
     * @param importFile location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public UniqueMemberList readClubBook(File importFile) throws FileNotFoundException, DataConversionException {

        requireNonNull(importFile);
        UniqueMemberList importedMembers = new UniqueMemberList();

        if (!importFile.exists()) {
            logger.info("ClubBook import file "  + importFile + " not found");
            throw new FileNotFoundException();
        }

        try {
            importedMembers = CsvFileStorage.readClubBook(importFile);
        } catch (IllegalValueException ive) {
            logger.warning("Illegal values found in " + importFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
        return importedMembers;
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
