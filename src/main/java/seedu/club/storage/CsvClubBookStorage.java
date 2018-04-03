//@@author amrut-prabhu
package seedu.club.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.util.FileUtil;
import seedu.club.model.member.UniqueMemberList;

/**
 * A class to manage storage of ClubBook data as a csv file on the hard disk.
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

    /**
     * Returns data from the file as a {@link UniqueMemberList}.
     *
     * @throws IOException if there was any problem when reading from the storage.
     */
    public UniqueMemberList readClubBook() throws IOException {
        return readClubBook(file);
    }

    /**
     * Similar to {@link #readClubBook()}
     *
     * @param importFile location of the data. Cannot be null.
     */
    public UniqueMemberList readClubBook(File importFile) throws IOException {

        requireNonNull(importFile);

        if (!importFile.exists()) {
            logger.info("ClubBook import file "  + importFile + " not found");
            throw new FileNotFoundException();
        }

        return CsvFileStorage.readClubBook(importFile);
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
