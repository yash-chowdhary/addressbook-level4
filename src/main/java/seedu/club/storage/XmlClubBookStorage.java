package seedu.club.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.exceptions.DataConversionException;
import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.commons.util.FileUtil;
import seedu.club.model.ReadOnlyClubBook;

/**
 * A class to access ClubBook data stored as an xml file on the hard disk.
 */
public class XmlClubBookStorage implements ClubBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlClubBookStorage.class);

    private String filePath;

    public XmlClubBookStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getClubBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyClubBook> readClubBook() throws DataConversionException, IOException {
        return readClubBook(filePath);
    }

    /**
     * Similar to {@link #readClubBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyClubBook> readClubBook(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        File clubBookFile = new File(filePath);

        if (!clubBookFile.exists()) {
            logger.info("ClubBook file "  + clubBookFile + " not found");
            return Optional.empty();
        }

        XmlSerializableClubBook xmlClubBook = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlClubBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + clubBookFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveClubBook(ReadOnlyClubBook clubBook) throws IOException {
        saveClubBook(clubBook, filePath);
    }

    /**
     * Similar to {@link #saveClubBook(ReadOnlyClubBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveClubBook(ReadOnlyClubBook clubBook, String filePath) throws IOException {
        requireNonNull(clubBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableClubBook(clubBook));
    }

}
