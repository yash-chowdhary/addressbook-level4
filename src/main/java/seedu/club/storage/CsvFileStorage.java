//@@author amrut-prabhu
package seedu.club.storage;

import java.io.File;
import java.io.IOException;

import seedu.club.commons.exceptions.DataConversionException;
import seedu.club.commons.util.CsvUtil;
import seedu.club.model.member.UniqueMemberList;
import seedu.club.model.member.exceptions.DuplicateMemberException;

/**
 * Stores ClubBook data in a CSV file.
 */
public class CsvFileStorage {

    /**
     * Saves the given clubBook data to the specified file.
     */
    public static void saveDataToFile(File file, String data) throws IOException {
        try {
            CsvUtil.saveDataToFile(file, data);
        } catch (IOException ioe) {
            throw new IOException("Unexpected error " + ioe.getMessage());
        }
    }

    /**
     * Returns club book in the file or an empty club book
     */
    public static UniqueMemberList readClubBook(File file)
            throws IOException, DataConversionException, DuplicateMemberException {
        return CsvUtil.getDataFromFile(file);
    }
}
