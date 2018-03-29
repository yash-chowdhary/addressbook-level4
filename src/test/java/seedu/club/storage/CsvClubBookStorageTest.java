//@@author amrut-prabhu
package seedu.club.storage;

import static org.junit.Assert.assertTrue;
import static seedu.club.testutil.TypicalMembers.getTypicalMembers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.club.commons.util.CsvUtil;
import seedu.club.commons.util.FileUtil;
import seedu.club.model.member.Member;

public class CsvClubBookStorageTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/CsvClubBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private File addToTestDataFileIfNotNull(File prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? new File(TEST_DATA_FOLDER + prefsFileInTestDataFolder)
                : null;
    }

    /*@Test
    public void readClubBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readClubBook(null);
    }

    private java.util.Optional<ReadOnlyClubBook> readClubBook(String filePath) throws Exception {
        File file = new File(filePath);
        CsvClubBookStorage csvClubBookStorage = new CsvClubBookStorage();
        csvClubBookStorage.setClubBookFile(file);
        return csvClubBookStorage.readClubBook(addToTestDataFileIfNotNull(file));
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readClubBook("NonExistentFile.csv").isPresent());
    }*/

    /*@Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readClubBook("NotXmlFormatClubBook.xml");

         IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method

    }

    @Test
    public void readClubBook_invalidMemberClubBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readClubBook("invalidMemberClubBook.xml");
    }

    @Test
    public void readClubBook_invalidAndValidMemberClubBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readClubBook("invalidAndValidMemberClubBook.xml");
    }*/

    @Test
    public void readAndSaveClubBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempClubBook.csv";
        File exportFile = new File(filePath);
        List<Member> originalMemberList = getTypicalMembers();
        CsvClubBookStorage csvClubBookStorage = new CsvClubBookStorage();
        csvClubBookStorage.setClubBookFile(exportFile);

        //Save in new file (without headers) and read back
        for (Member member: originalMemberList) {
            csvClubBookStorage.saveData(CsvUtil.toCsvFormat(member));
        }
        assertTrue(exportFile.exists());
        //TODO: Read back data
        /*ReadOnlyClubBook readBack = csvClubBookStorage.readClubBook(filePath).get();
        assertEquals(original, new ClubBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addMember(HOON);
        original.removeMember(ALICE);
        csvClubBookStorage.saveClubBook(original, filePath);
        readBack = xmlClubBookStorage.readClubBook(filePath).get();
        assertEquals(original, new ClubBook(readBack));

        //Save and read without specifying file path
        original.addMember(IDA);
        csvClubBookStorage.saveClubBook(original); //file path not specified
        readBack = xmlClubBookStorage.readClubBook().get(); //file path not specified
        assertEquals(original, new ClubBook(readBack));*/
    }

    @Test
    public void saveData_nullData_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveData(null, new File("SomeFile.csv"));
    }

    @Test
    public void saveData_nullFile_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveData(new String(), null);
    }

    /**
     * Saves {@code data} at the specified {@code file}.
     */
    private void saveData(String data, File file) {
        try {
            CsvClubBookStorage csvClubBookStorage = new CsvClubBookStorage();
            csvClubBookStorage.setClubBookFile(file);
            csvClubBookStorage.saveData(data, addToTestDataFileIfNotNull(file));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

}
