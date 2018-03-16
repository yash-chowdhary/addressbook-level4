package seedu.club.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.club.commons.exceptions.DataConversionException;
import seedu.club.commons.util.XmlUtil;

/**
 * Stores clubbook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given clubbook data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableClubBook clubBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, clubBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns club book in the file or an empty club book
     */
    public static XmlSerializableClubBook loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableClubBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
