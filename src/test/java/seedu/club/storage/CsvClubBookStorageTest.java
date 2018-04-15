//@@author amrut-prabhu
package seedu.club.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalMembers.HOON;
import static seedu.club.testutil.TypicalMembers.IDA;
import static seedu.club.testutil.TypicalMembers.getTypicalMembers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.club.commons.events.storage.DataReadingExceptionEvent;
import seedu.club.commons.util.CsvUtil;
import seedu.club.commons.util.FileUtil;
import seedu.club.model.member.Member;
import seedu.club.model.member.UniqueMemberList;
import seedu.club.ui.testutil.EventsCollectorRule;

public class CsvClubBookStorageTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/CsvClubBookStorageTest/");
    private static final String FILE_NAME = "TempClubBook.csv";

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    private File addToTestDataFileIfNotNull(File prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? new File(TEST_DATA_FOLDER + prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readClubBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readClubBook(null);
    }

    /**
     * Returns a {@code UniqueMemberList} by parsing the data in the file specified by {@code filePath}.
     */
    private UniqueMemberList readClubBook(String filePath) throws Exception {
        File file = new File(filePath);
        CsvClubBookStorage csvClubBookStorage = new CsvClubBookStorage();
        csvClubBookStorage.setClubBookFile(file);
        return csvClubBookStorage.readClubBook(addToTestDataFileIfNotNull(file));
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        thrown.expect(FileNotFoundException.class);
        readClubBook("NonExistentFile.csv");
    }

    @Test
    public void read_missingFile_eventRaised() throws Exception {
        File exportFile = new File("NonExistentFile.csv");
        CsvClubBookStorage csvClubBookStorage = new CsvClubBookStorage();
        csvClubBookStorage.setClubBookFile(exportFile);

        thrown.expect(FileNotFoundException.class);
        csvClubBookStorage.readClubBook(); //file path not specified
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataReadingExceptionEvent);
    }

    @Test
    public void read_notCsvFormat_noMembersImported() throws Exception {
        UniqueMemberList importedMembers = readClubBook("NotCsvFormatClubBook.csv");
        assertTrue(importedMembers.asObservableList().size() == 0); //No members imported
    }

    @Test
    public void readClubBook_invalidMemberClubBook_noMembersImported() throws Exception {
        CsvClubBookStorage csvClubBookStorage =
                new CsvClubBookStorage(new File(TEST_DATA_FOLDER + "invalidMemberClubBook.csv"));
        UniqueMemberList importedMembers = csvClubBookStorage.readClubBook();
        //UniqueMemberList importedMembers = readClubBook("invalidMemberClubBook.csv");
        assertTrue(importedMembers.asObservableList().size() == 0); //No members imported
    }

    @Test
    public void readClubBook_invalidAndValidMemberClubBook_someMembersImported() throws Exception {
        UniqueMemberList importedMembers = readClubBook("invalidAndValidMemberClubBook.csv");
        assertTrue(importedMembers.asObservableList().size() == 1); //No members imported
    }

    @Test
    public void readAndSaveClubBook_allInOrder_success() throws Exception {
        File exportFile = temp.newFile(FILE_NAME);
        List<Member> originalMemberList = getTypicalMembers();
        StringBuilder dataToExport = new StringBuilder();
        CsvClubBookStorage csvClubBookStorage = new CsvClubBookStorage();
        csvClubBookStorage.setClubBookFile(exportFile);

        //Save in new file and read back
        originalMemberList.forEach(member -> dataToExport.append(CsvUtil.toCsvFormat(member)));
        csvClubBookStorage.saveData(dataToExport.toString());
        UniqueMemberList readBack = csvClubBookStorage.readClubBook(exportFile);
        assertEquals(originalMemberList, readBack.asObservableList());

        //Modify data, overwrite exiting file, and read back
        originalMemberList.add(HOON);
        originalMemberList.remove(ALICE);
        dataToExport.setLength(0); //Clear buffer
        dataToExport.trimToSize();
        originalMemberList.forEach(member -> dataToExport.append(CsvUtil.toCsvFormat(member)));
        csvClubBookStorage.saveData(dataToExport.toString());
        readBack = csvClubBookStorage.readClubBook(exportFile);
        assertEquals(originalMemberList, readBack.asObservableList());

        //Save and read without specifying file path
        originalMemberList.add(IDA);
        originalMemberList.forEach(member -> dataToExport.append(CsvUtil.toCsvFormat(member)));
        csvClubBookStorage.saveData(dataToExport.toString()); //file path not specified
        readBack = csvClubBookStorage.readClubBook(); //file path not specified
        assertEquals(originalMemberList, readBack.asObservableList());
    }

    @Test
    public void saveData_nullData_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveData(null, new File("SomeFile.csv"));
    }

    @Test
    public void saveData_nullFile_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveData("dummy data", null);
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
