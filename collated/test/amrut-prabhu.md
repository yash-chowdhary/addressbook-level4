# amrut-prabhu
###### \java\seedu\club\logic\commands\ChangeProfilePhotoCommandTest.java
``` java
package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.club.commons.core.index.Index;
import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.logic.commands.exceptions.IllegalExecutionException;
import seedu.club.model.ClubBook;
import seedu.club.model.Model;
import seedu.club.model.ReadOnlyClubBook;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.Email;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.member.Phone;
import seedu.club.model.member.ProfilePhoto;
import seedu.club.model.member.exceptions.DataToChangeIsNotCurrentlyLoggedInMemberException;
import seedu.club.model.member.exceptions.DuplicateMatricNumberException;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.member.exceptions.PasswordIncorrectException;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.exceptions.AnswerNotFoundException;
import seedu.club.model.poll.exceptions.DuplicatePollException;
import seedu.club.model.poll.exceptions.PollNotFoundException;
import seedu.club.model.poll.exceptions.UserAlreadyVotedException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskCannotBeDeletedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;
import seedu.club.model.task.exceptions.TasksAlreadyListedException;
import seedu.club.model.task.exceptions.TasksCannotBeDisplayedException;

public class ChangeProfilePhotoCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String currentDirectoryPath = ".";
    private File currentDirectory = new File(currentDirectoryPath);

    private String testPhotoPath = "./src/test/resources/photos/testPhoto.png";
    private File testPhotoFile = new File(testPhotoPath);

    @Test
    public void constructor_nullProfilePhoto_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ChangeProfilePhotoCommand(null);
    }

    @Test
    public void execute_addProfilePhoto_success() throws Exception {
        ModelStubAcceptingAddProfilePhoto modelStub = new ModelStubAcceptingAddProfilePhoto();

        String validPhotoPath = testPhotoFile.getAbsolutePath();

        CommandResult commandResult = getChangeProfilePhotoCommand(validPhotoPath, modelStub).execute();
        assertEquals(String.format(ChangeProfilePhotoCommand.MESSAGE_CHANGE_PROFILE_PHOTO_SUCCESS, validPhotoPath),
                commandResult.feedbackToUser);
    }

    @Test
    public void execute_invalidPath_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingPhotoReadException();

        String invalidPhotoPath = currentDirectory.getAbsolutePath();

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(ChangeProfilePhotoCommand.MESSAGE_INVALID_PHOTO_PATH, invalidPhotoPath));

        getChangeProfilePhotoCommand(invalidPhotoPath, modelStub).execute();
    }

    @Test
    public void equals() {
        String photoFilePath = currentDirectory.getAbsolutePath() + "/testPhoto.png";
        ProfilePhoto profilePhoto = new ProfilePhoto(photoFilePath);

        ProfilePhoto diffProfilePhoto = new ProfilePhoto(testPhotoPath);

        ChangeProfilePhotoCommand changeProfilePhotoCommand = new ChangeProfilePhotoCommand(profilePhoto);
        ChangeProfilePhotoCommand samePathChangeProfilePhotoCommand = new ChangeProfilePhotoCommand(profilePhoto);
        ChangeProfilePhotoCommand differentchangeProfilePhotoCommand = new ChangeProfilePhotoCommand(diffProfilePhoto);

        // same object -> returns true
        assertTrue(changeProfilePhotoCommand.equals(changeProfilePhotoCommand));

        // same photo -> returns true
        assertTrue(changeProfilePhotoCommand.equals(samePathChangeProfilePhotoCommand));

        // different types -> returns false
        assertFalse(changeProfilePhotoCommand.equals(1));

        // null -> returns false
        assertFalse(changeProfilePhotoCommand.equals(null));

        // different photo -> returns false
        assertFalse(changeProfilePhotoCommand.equals(differentchangeProfilePhotoCommand));
    }

    /**
     * Generates a new ExportCommand with {@code exportFile}.
     */
    private ChangeProfilePhotoCommand getChangeProfilePhotoCommand(String photoPath, Model model) {
        ChangeProfilePhotoCommand command = new ChangeProfilePhotoCommand(new ProfilePhoto(photoPath));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
    /**
     * Returns a tag set containing the list of strings given.
     */
    private static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void voteInPoll(Poll poll, Index answerIndex) throws
                PollNotFoundException, AnswerNotFoundException, UserAlreadyVotedException {
            fail("This method should not be called.");
        }

        @Override
        public void changeStatus(Task taskToEdit, Task editedTask) throws TaskNotFoundException,
                DuplicateTaskException {
            fail("This method should not be called");
        }

        @Override
        public FilteredList<Poll> getFilteredPollList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void viewMyTasks() throws TasksAlreadyListedException {
            fail("This method should not be called");
        }

        @Override
        public void assignTask(Task toAdd, Name name) throws MemberNotFoundException, DuplicateTaskException,
                IllegalExecutionException {
            fail("This method should not be called");
        }

        @Override
        public void signUpMember(Member member) {
            fail("This method should not be called");
        }

        @Override
        public void addMember(Member member) throws DuplicateMatricNumberException  {
            fail("This method should not be called");
        }

        @Override
        public void clearClubBook() {
            fail("This method should not be called");
        }

        @Override
        public void addPoll(Poll poll) throws DuplicatePollException {
            fail("This method should not be called.");
        }

        @Override
        public void deletePoll(Poll target) throws PollNotFoundException {
            fail("This method should not be called.");
        }

        public void deleteTask(Task taskToDelete) throws TaskNotFoundException, TaskCannotBeDeletedException {
            fail("This method should not be called");
        }

        @Override
        public void viewAllTasks() throws TasksCannotBeDisplayedException {
            fail("This method should not be called");
        }

        @Override
        public void addProfilePhoto(String originalPhotoPath) throws PhotoReadException {
            fail("This method should not be called.");
        }

        @Override
        public void removeGroup(Group toRemove) {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyClubBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyClubBook getClubBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteMember(Member target) throws MemberNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateMember(Member target, Member editedMember) throws DuplicateMatricNumberException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) throws TagNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Member> getFilteredMemberList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredMemberList(Predicate<Member> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void logsInMember(String username, String password) {
            fail("This method should not be called");
        }

        @Override
        public Member getLoggedInMember() {
            return null;
        }

        @Override
        public void updateFilteredPollList(Predicate<Poll> poll) {
            fail("This method should not be called.");
        }

        public void updateFilteredTagList(Predicate<Tag> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Tag> getFilteredTagList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void sendEmail(String recipients, Client client, Subject subject, Body body) {
            fail("This method should not be called");
            return;
        }

        @Override
        public void logOutMember() {
            fail("This method should not be called");
        }

        @Override
        public String generateEmailRecipients(Group group, Tag tag) throws GroupNotFoundException,
                TagNotFoundException {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void exportClubConnectMembers(File exportFilePath) throws IOException {
            fail("This method should not be called");
        }

        @Override
        public int importMembers(File importFile) throws IOException {
            fail("This method should not be called");
            return 0;
        }

        @Override
        public void addTaskToTaskList(Task toAdd) throws DuplicateTaskException {
            fail("This method should not be called");
            return;
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            fail("This method should not be called");
            return;
        }

        @Override
        public void changePassword(String username, String oldPassword, String newPassword)
                throws PasswordIncorrectException, DataToChangeIsNotCurrentlyLoggedInMemberException {
            fail("This method should not be called");
            return;
        }
    }

    /**
     * A Model stub that always throw a PhotoReadException when trying to add a profile photo.
     */
    private class ModelStubThrowingPhotoReadException extends ModelStub {
        private final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("logistics"),
                getTagSet("friends"));

        @Override
        public void addProfilePhoto(String originalPhotoPath) throws PhotoReadException {
            throw new PhotoReadException();
        }
```
###### \java\seedu\club\logic\commands\ExportCommandTest.java
``` java
package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.club.commons.core.index.Index;
import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.logic.commands.exceptions.IllegalExecutionException;
import seedu.club.model.ClubBook;
import seedu.club.model.Model;
import seedu.club.model.ReadOnlyClubBook;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.Email;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.member.Phone;
import seedu.club.model.member.exceptions.DataToChangeIsNotCurrentlyLoggedInMemberException;
import seedu.club.model.member.exceptions.DuplicateMatricNumberException;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.member.exceptions.PasswordIncorrectException;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.exceptions.AnswerNotFoundException;
import seedu.club.model.poll.exceptions.DuplicatePollException;
import seedu.club.model.poll.exceptions.PollNotFoundException;
import seedu.club.model.poll.exceptions.UserAlreadyVotedException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskCannotBeDeletedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;
import seedu.club.model.task.exceptions.TasksAlreadyListedException;
import seedu.club.model.task.exceptions.TasksCannotBeDisplayedException;

public class ExportCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private String currentDirectoryPath = ".";
    private File currentDirectory = new File(currentDirectoryPath);

    @Test
    public void constructor_nullFile_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ExportCommand(null);
    }

    @Test
    public void execute_exportClubConnectMembers_success() throws Exception {
        ModelStubAcceptingExport modelStub = new ModelStubAcceptingExport();

        String validFilePath = testFolder.getRoot().getPath() + "TempClubBook.csv";
        File exportFile = new File(validFilePath);

        CommandResult commandResult = getExportCommand(exportFile, modelStub).execute();
        assertEquals(String.format(ExportCommand.MESSAGE_EXPORT_SUCCESS, exportFile), commandResult.feedbackToUser);
    }

    @Test
    public void execute_invalidFilePath_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingIoException();

        String invalidFilePath = testFolder.getRoot().getPath();
        File exportFile = new File(invalidFilePath);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(ExportCommand.MESSAGE_EXPORT_FAILURE, exportFile));

        getExportCommand(exportFile, modelStub).execute();
    }

    @Test
    public void equals() {
        String exportFilePath = currentDirectory.getAbsolutePath() + "/exportEqualsTest.csv";
        File exportFile = new File(exportFilePath);

        ExportCommand exportCommand = new ExportCommand(exportFile);
        ExportCommand sameFileExportCommand = new ExportCommand(exportFile);
        ExportCommand differentFileExportCommand = new ExportCommand(currentDirectory);

        // same object -> returns true
        assertTrue(exportCommand.equals(exportCommand));

        // same file -> returns true
        assertTrue(exportCommand.equals(sameFileExportCommand));

        // different types -> returns false
        assertFalse(exportCommand.equals(1));

        // null -> returns false
        assertFalse(exportCommand.equals(null));

        // different file -> returns false
        assertFalse(exportCommand.equals(differentFileExportCommand));
    }

    /**
     * Generates a new ExportCommand with {@code exportFile}.
     */
    private ExportCommand getExportCommand(File exportFile, Model model) {
        ExportCommand command = new ExportCommand(exportFile);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    private static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void voteInPoll(Poll poll, Index answerIndex) throws
                PollNotFoundException, AnswerNotFoundException, UserAlreadyVotedException {
            fail("This method should not be called.");
        }

        @Override
        public void changeStatus(Task taskToEdit, Task editedTask) throws TaskNotFoundException,
                DuplicateTaskException {
            fail("This method should not be called");
        }

        @Override
        public FilteredList<Poll> getFilteredPollList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void signUpMember(Member member) {
            fail("This method should not be called");
        }

        @Override
        public void clearClubBook() {
            fail("This method should not be called");
        }

        @Override
        public void assignTask(Task toAdd, Name name) throws MemberNotFoundException, DuplicateTaskException,
                IllegalExecutionException {
            fail("This method should not be called");
        }

        @Override
        public void viewMyTasks() throws TasksAlreadyListedException {
            fail("This method should not be called");
        }

        @Override
        public void addMember(Member member) throws DuplicateMatricNumberException {
            fail("This method should not be called.");
        }

        @Override
        public void addPoll(Poll poll) throws DuplicatePollException {
            fail("This method should not be called.");
        }

        @Override
        public void deletePoll(Poll target) throws PollNotFoundException {
            fail("This method should not be called.");
        }

        public void deleteTask(Task taskToDelete) throws TaskNotFoundException, TaskCannotBeDeletedException {
            fail("This method should not be called");
            return;
        }

        @Override
        public void viewAllTasks() throws TasksCannotBeDisplayedException {
            fail("This method should not be called");
        }

        @Override
        public void addProfilePhoto(String originalPhotoPath) throws PhotoReadException {
            fail("This method should not be called.");
        }

        @Override
        public void removeGroup(Group toRemove) {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyClubBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyClubBook getClubBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteMember(Member target) throws MemberNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateMember(Member target, Member editedMember) throws DuplicateMatricNumberException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) throws TagNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Member> getFilteredMemberList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredMemberList(Predicate<Member> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void logsInMember(String username, String password) {
            fail("This method should not be called");
        }

        @Override
        public Member getLoggedInMember() {
            return null;
        }

        @Override
        public void updateFilteredPollList(Predicate<Poll> poll) {
            fail("This method should not be called.");
        }

        public void updateFilteredTagList(Predicate<Tag> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Tag> getFilteredTagList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void sendEmail(String recipients, Client client, Subject subject, Body body) {
            fail("This method should not be called");
        }

        @Override
        public void logOutMember() {
            fail("This method should not be called");
        }

        @Override
        public String generateEmailRecipients(Group group, Tag tag) throws GroupNotFoundException,
                TagNotFoundException {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void exportClubConnectMembers(File exportFilePath) throws IOException {
            fail("This method should not be called");
        }

        @Override
        public int importMembers(File importFile) throws IOException {
            fail("This method should not be called");
            return 0;
        }

        @Override
        public void addTaskToTaskList(Task toAdd) throws DuplicateTaskException {
            fail("This method should not be called");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            fail("This method should not be called");
        }

        @Override
        public void changePassword(String username, String oldPassword, String newPassword)
                throws PasswordIncorrectException, DataToChangeIsNotCurrentlyLoggedInMemberException {
            fail("This method should not be called");
            return;
        }
    }

    /**
     * A Model stub that always throw a IOException when trying to export to a file.
     */
    private class ModelStubThrowingIoException extends ModelStub {
        final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("logistics"),
                getTagSet("friends"));

        @Override
        public void exportClubConnectMembers(File exportFile) throws IOException {
            throw new IOException();
        }
```
###### \java\seedu\club\logic\parser\ExportCommandParserTest.java
``` java
package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.club.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private ExportCommandParser parser = new ExportCommandParser();
    private String currentDirectoryPath = ".";
    private File currentDirectory = new File(currentDirectoryPath);

    @Test
    public void parse_validArgs_returnsExportCommand() throws Exception {
        File exportFile = temporaryFolder.newFile("actual.csv");

        String expectedExportFilePath = exportFile.getAbsolutePath();
        File expectedExportFile = new File(expectedExportFilePath);
        assertParseSuccess(parser, expectedExportFile.getAbsolutePath(), new ExportCommand(expectedExportFile));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //non absolute file path
        assertParseFailure(parser, "data/exportTestFile.csv", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCommand.MESSAGE_USAGE));

        //invalid file path
        assertParseFailure(parser, currentDirectory.getAbsolutePath() + "/data/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));

        //invalid file type
        assertParseFailure(parser, currentDirectory.getAbsolutePath() + "/data/exportTestFile.txt",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\club\model\ModelManagerTest.java
``` java
    @Test
    public void addProfilePhoto_eventRaised() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(clubBook, userPrefs);

        modelManager.logsInMember(BENSON.getCredentials().getUsername().value,
                BENSON.getCredentials().getPassword().value);

        String photoDirectory = "./src/test/resources/photos/";
        String photoFileName = "testPhoto.png";
        modelManager.addProfilePhoto(photoDirectory + photoFileName);

        //2 events are raised: ProfilePhotoChangedEvent and ClubBookChangedEvent
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 2);
        //Last event raised is ClubBookChangedEvent
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ClubBookChangedEvent);
    }

    @Test
    public void exportClubConnectMembers_eventRaised() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(clubBook, userPrefs);

        File exportFile = temporaryFolder.newFile("actual.csv");

        modelManager.exportClubConnectMembers(exportFile);

        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof NewExportDataAvailableEvent);
    }
```
###### \java\seedu\club\storage\CsvClubBookStorageTest.java
``` java
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
```
###### \java\seedu\club\storage\ProfilePhotoStorageTest.java
``` java
package seedu.club.storage;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.commons.exceptions.PhotoWriteException;

public class ProfilePhotoStorageTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void copyProfilePhoto_invalidPath_exceptionThrown() throws Exception {
        thrown.expect(PhotoReadException.class);
        ProfilePhotoStorage profilePhotoStorage = new ProfilePhotoStorage();

        String invalidPhotoPath = testFolder.getRoot().getPath() + "invalidFile.xyz";
        profilePhotoStorage.copyOriginalPhotoFile(invalidPhotoPath, "dummyName");
    }

    /**
     * Ensures no exception is thrown and command happens successfully.
     */
    @Test
    public void copyProfilePhoto_validPath_success() {
        Exception expectedException = null;
        try {
            ProfilePhotoStorageStubAcceptingCreateCopy profilePhotoStorage =
                    new ProfilePhotoStorageStubAcceptingCreateCopy();
            String photoPath = testFolder.newFile("testPhoto.png").getAbsolutePath();
            profilePhotoStorage.copyOriginalPhotoFile(photoPath, "testCopy");
        } catch (Exception e) {
            expectedException = e;
        }
        assertEquals(null, expectedException);
    }

    /**
     * Ensures exception is thrown.
     */
    @Test
    public void copyProfilePhoto_validPath_exceptionThrown() throws Exception {
        thrown.expect(PhotoWriteException.class);
        ProfilePhotoStorageExceptionThrowingStub profilePhotoStorage = new ProfilePhotoStorageExceptionThrowingStub();
        String photoPath = testFolder.newFile("testPhoto.png").getAbsolutePath();
        profilePhotoStorage.copyOriginalPhotoFile(photoPath, "testCopy");
    }

    /**
     * A Stub class that always accepts the createPhotoFileCopy method.
     */
    private class ProfilePhotoStorageStubAcceptingCreateCopy extends ProfilePhotoStorage {

        @Override
        public void createPhotoFileCopy(BufferedImage originalPhoto, File newPath) throws PhotoWriteException {
            return;
        }

    }

    /**
     * A Stub class to throw an exception when the createPhotoFileCopy method is called.
     */
    class ProfilePhotoStorageExceptionThrowingStub extends ProfilePhotoStorage {

        @Override
        public void createPhotoFileCopy(BufferedImage originalPhoto, File newPath) throws PhotoWriteException {
            throw new PhotoWriteException("dummy exception");
        }

    }
}
```
###### \java\seedu\club\storage\StorageManagerTest.java
``` java
    @Test
    public void handleProfilePictureChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the copy Photo method is called
        Storage storage = new StorageManager(new XmlClubBookStorage("dummy"),
                new JsonUserPrefsStorage("dummy"), new ProfilePhotoStorageExceptionThrowingStub(),
                new CsvClubBookStorage());

        File photoFile = new File("./src/test/resources/photos/");
        String photoPath = photoFile.getAbsolutePath();
        storage.handleProfilePictureChangedEvent(new ProfilePhotoChangedEvent(photoPath, "testPhotoCopy.png"));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataReadingExceptionEvent);
    }

    @Test
    public void handleExportDataEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the exportData method is called
        Storage storage = new StorageManager(new XmlClubBookStorage("dummy"),
                new JsonUserPrefsStorage("dummy"), new ProfilePhotoStorage(),
                new CsvClubBookStorageExceptionThrowingStub());

        File dummyFile = new File("./src/test/exportFile.csv");
        storage.handleExportDataEvent(new NewExportDataAvailableEvent(dummyFile));
        storage.handleExportDataEvent(new NewExportDataAvailableEvent("dummy data"));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }
```
###### \java\seedu\club\storage\StorageManagerTest.java
``` java
    /**
     * A Stub class to throw an exception when the copy photo method is called
     */
    class ProfilePhotoStorageExceptionThrowingStub extends ProfilePhotoStorage {

        @Override
        public void copyOriginalPhotoFile(String originalFilePath, String newName)
                throws PhotoReadException, PhotoWriteException {
            throw new PhotoReadException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save data method is called
     */
    class CsvClubBookStorageExceptionThrowingStub extends CsvClubBookStorage {

        @Override
        public void saveData(String data) throws IOException {
            throw new IOException("dummy exception");
        }
    }
```
