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
import seedu.club.model.poll.exceptions.DuplicatePollException;
import seedu.club.model.poll.exceptions.PollNotFoundException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskAlreadyAssignedException;
import seedu.club.model.task.exceptions.TaskCannotBeDeletedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;
import seedu.club.model.task.exceptions.TasksAlreadyListedException;

public class ChangeProfilePhotoCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String currentDirectoryPath = ".";
    private File currentDirectory = new File(currentDirectoryPath);

    private String testPhotoPath = "./src/test/resources/photos/testPhoto.png";
    private File testPhotoFile = new File(testPhotoPath);

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
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public String voteInPoll(Poll poll, Index answerIndex) {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void changeStatus(Task taskToEdit, Task editedTask) throws TaskNotFoundException,
                DuplicateTaskException {
            fail("This method should not be called");
        }

        @Override
        public void removeProfilePhoto() {
            fail("This method should not be called.");
        }

        @Override
        public void changeAssignee(Task taskToEdit, Task editedTask) throws MemberNotFoundException,
                DuplicateTaskException, TaskAlreadyAssignedException {
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
        public void assignTask(Task toAdd, MatricNumber matricNumber) throws MemberNotFoundException,
                DuplicateTaskException {
            fail("This method should not be called");
        }

        @Override
        public void signUpMember(Member member) {
            fail("This method should not be called");
        }

        @Override
        public void addMember(Member member) throws DuplicateMatricNumberException {
            fail("This method should not be called");
        }

        @Override
        public boolean getClearConfirmation() {
            fail("This method should not be called");
            return false;
        }

        @Override
        public void setClearConfirmation(Boolean b) {
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
        public void viewAllTasks() throws TasksAlreadyListedException {
            fail("This method should not be called");
        }

        @Override
        public void addProfilePhoto(String originalPhotoPath) throws PhotoReadException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteGroup(Group toRemove) {
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
        public int deleteMember(Member target) throws MemberNotFoundException {
            fail("This method should not be called.");
            return -1;
        }

        @Override
        public int updateMember(Member target, Member editedMember) throws DuplicateMatricNumberException {
            fail("This method should not be called.");
            return -1;
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
                getTagSet("head"));

        @Override
        public void addProfilePhoto(String originalPhotoPath) throws PhotoReadException {
            throw new PhotoReadException();
        }

```
###### \java\seedu\club\logic\commands\ChangeProfilePhotoCommandTest.java
``` java
    }

    /**
     * A Model stub that always accept the path of the profile photo to be added.
     */
    private class ModelStubAcceptingAddProfilePhoto extends ModelStub {
        private final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("logistics"),
                getTagSet("head"));

        @Override
        public void addProfilePhoto(String originalPhotoPath) throws PhotoReadException {
            requireNonNull(originalPhotoPath);
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
import seedu.club.model.task.exceptions.TaskAlreadyAssignedException;
import seedu.club.model.task.exceptions.TaskCannotBeDeletedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;
import seedu.club.model.task.exceptions.TasksAlreadyListedException;

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
        public String voteInPoll(Poll poll, Index answerIndex) throws
                PollNotFoundException, AnswerNotFoundException, UserAlreadyVotedException {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void changeAssignee(Task taskToEdit, Task editedTask) throws MemberNotFoundException,
                DuplicateTaskException, TaskAlreadyAssignedException {
            fail("This method should not be called");
        }

        @Override
        public void removeProfilePhoto() {
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

        public boolean getClearConfirmation() {
            fail("This method should not be called");
            return false;
        }

        @Override
        public void setClearConfirmation(Boolean b) {
            fail("This method should not be called");
        }

        @Override
        public void assignTask(Task toAdd, MatricNumber matricNumber) throws MemberNotFoundException,
                DuplicateTaskException {
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
        public void viewAllTasks() throws TasksAlreadyListedException {
            fail("This method should not be called");
        }

        @Override
        public void addProfilePhoto(String originalPhotoPath) throws PhotoReadException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteGroup(Group toRemove) {
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
        public int deleteMember(Member target) throws MemberNotFoundException {
            fail("This method should not be called.");
            return -1;
        }

        @Override
        public int updateMember(Member target, Member editedMember) throws DuplicateMatricNumberException {
            fail("This method should not be called.");
            return -1;
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
     * A Model stub that always throws an IOException when trying to export to a file.
     */
    private class ModelStubThrowingIoException extends ModelStub {
        final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("logistics"),
                getTagSet("head"));

        @Override
        public void exportClubConnectMembers(File exportFile) throws IOException {
            throw new IOException();
        }
```
###### \java\seedu\club\logic\commands\ExportCommandTest.java
``` java
    }

    /**
     * A Model stub that always accepts the file being exported to.
     */
    private class ModelStubAcceptingExport extends ModelStub {
        final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("logistics"),
                getTagSet("head"));
        @Override
        public void exportClubConnectMembers(File exportFile) throws IOException {
            requireNonNull(exportFile);
        }

```
###### \java\seedu\club\logic\commands\ImportCommandTest.java
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
import seedu.club.model.task.exceptions.TaskAlreadyAssignedException;
import seedu.club.model.task.exceptions.TaskCannotBeDeletedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;
import seedu.club.model.task.exceptions.TasksAlreadyListedException;

public class ImportCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private String currentDirectoryPath = ".";
    private File currentDirectory = new File(currentDirectoryPath);

    @Test
    public void constructor_nullFile_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ImportCommand(null);
    }

    @Test
    public void execute_noMembersImported_success() throws Exception {
        ModelStubAcceptingImportZeroImported modelStub = new ModelStubAcceptingImportZeroImported();

        String validFilePath = testFolder.getRoot().getPath() + "TempClubBook.csv";
        File importFile = new File(validFilePath);

        CommandResult commandResult = getImportCommand(importFile, modelStub).execute();
        assertEquals(String.format(ImportCommand.MESSAGE_MEMBERS_NOT_IMPORTED, importFile),
                commandResult.feedbackToUser);
    }

    @Test
    public void execute_membersImported_success() throws Exception {
        ModelStubAcceptingImport modelStub = new ModelStubAcceptingImport();

        String validFilePath = testFolder.getRoot().getPath() + "TempClubBook.csv";
        File importFile = new File(validFilePath);
        int numberImported = 1;

        CommandResult commandResult = getImportCommand(importFile, modelStub).execute();
        assertEquals(String.format(ImportCommand.MESSAGE_IMPORT_SUCCESS, numberImported, importFile),
                commandResult.feedbackToUser);
    }

    @Test
    public void execute_invalidFilePath_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingIoException();

        String invalidFilePath = testFolder.getRoot().getPath();
        File importFile = new File(invalidFilePath);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(ImportCommand.MESSAGE_IMPORT_FAILURE, importFile));

        getImportCommand(importFile, modelStub).execute();
    }

    @Test
    public void equals() {
        String importFilePath = currentDirectory.getAbsolutePath() + "/importEqualsTest.csv";
        File importFile = new File(importFilePath);

        ImportCommand importCommand = new ImportCommand(importFile);
        ImportCommand sameFileImportCommand = new ImportCommand(importFile);
        ImportCommand differentFileImportCommand = new ImportCommand(currentDirectory);

        // same object -> returns true
        assertTrue(importCommand.equals(importCommand));

        // same file -> returns true
        assertTrue(importCommand.equals(sameFileImportCommand));

        // different types -> returns false
        assertFalse(importCommand.equals(1));

        // null -> returns false
        assertFalse(importCommand.equals(null));

        // different file -> returns false
        assertFalse(importCommand.equals(differentFileImportCommand));
    }

    /**
     * Generates a new ImportCommand with {@code importFile}.
     */
    private ImportCommand getImportCommand(File importFile, Model model) {
        ImportCommand command = new ImportCommand(importFile);
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
        public String voteInPoll(Poll poll, Index answerIndex) throws
                PollNotFoundException, AnswerNotFoundException, UserAlreadyVotedException {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void changeAssignee(Task taskToEdit, Task editedTask) throws MemberNotFoundException,
                DuplicateTaskException, TaskAlreadyAssignedException {
            fail("This method should not be called");
        }

        @Override
        public void removeProfilePhoto() {
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

        public boolean getClearConfirmation() {
            fail("This method should not be called");
            return false;
        }

        @Override
        public void setClearConfirmation(Boolean b) {
            fail("This method should not be called");
        }

        @Override
        public void assignTask(Task toAdd, MatricNumber matricNumber) throws MemberNotFoundException,
                DuplicateTaskException {
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
        public void viewAllTasks() throws TasksAlreadyListedException {
            fail("This method should not be called");
        }

        @Override
        public void addProfilePhoto(String originalPhotoPath) throws PhotoReadException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteGroup(Group toRemove) {
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
        public int deleteMember(Member target) throws MemberNotFoundException {
            fail("This method should not be called.");
            return -1;
        }

        @Override
        public int updateMember(Member target, Member editedMember) throws DuplicateMatricNumberException {
            fail("This method should not be called.");
            return -1;
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
     * A Model stub that always throw a IOException when trying to import a file.
     */
    private class ModelStubThrowingIoException extends ModelStub {
        final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("Exco"),
                getTagSet("head"));

        @Override
        public int importMembers(File importFile) throws IOException {
            throw new IOException();
        }
```
###### \java\seedu\club\logic\commands\ImportCommandTest.java
``` java
    }

    /**
     * A Model stub that never imports any members from the file.
     */
    private class ModelStubAcceptingImportZeroImported extends ModelStub {
        final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("Exco"),
                getTagSet("head"));

        @Override
        public int importMembers(File importFile) throws IOException {
            requireNonNull(importFile);
            return 0;
        }

```
###### \java\seedu\club\logic\commands\ImportCommandTest.java
``` java
    }

    /**
     * A Model stub that never imports any members from the file.
     */
    private class ModelStubAcceptingImport extends ModelStub {
        final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("Exco"),
                getTagSet("head"));

        @Override
        public int importMembers(File importFile) throws IOException {
            requireNonNull(importFile);
            return 1;
        }

```
###### \java\seedu\club\logic\parser\ChangeProfilePhotoCommandParserTest.java
``` java
package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.club.logic.commands.ChangeProfilePhotoCommand;
import seedu.club.model.member.ProfilePhoto;

public class ChangeProfilePhotoCommandParserTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private ChangeProfilePhotoCommandParser parser = new ChangeProfilePhotoCommandParser();
    private String currentDirectoryPath = ".";
    private File currentDirectory = new File(currentDirectoryPath);

    @Test
    public void parse_validArgs_returnsChangeProfilePhotoommand() throws Exception {
        File imageFile = temporaryFolder.newFile("dummy.jpg");
        String expectedImageFilePath = imageFile.getAbsolutePath();
        ProfilePhoto expectedPhoto = new ProfilePhoto(expectedImageFilePath);
        assertParseSuccess(parser, expectedImageFilePath, new ChangeProfilePhotoCommand(expectedPhoto));

        imageFile = temporaryFolder.newFile("dummy.png");
        expectedImageFilePath = imageFile.getAbsolutePath();
        expectedPhoto = new ProfilePhoto(expectedImageFilePath);
        assertParseSuccess(parser, expectedImageFilePath, new ChangeProfilePhotoCommand(expectedPhoto));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //non absolute file path
        assertParseFailure(parser, "./dummyImage.png", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ProfilePhoto.MESSAGE_PHOTO_PATH_CONSTRAINTS + ChangeProfilePhotoCommand.MESSAGE_USAGE));

        //invalid file path
        assertParseFailure(parser, currentDirectory.getAbsolutePath(), String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ProfilePhoto.MESSAGE_PHOTO_PATH_CONSTRAINTS + ChangeProfilePhotoCommand.MESSAGE_USAGE));

        //invalid file type
        assertParseFailure(parser, currentDirectory.getAbsolutePath() + "/dummyImage.gif",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProfilePhoto.MESSAGE_PHOTO_PATH_CONSTRAINTS
                        + ChangeProfilePhotoCommand.MESSAGE_USAGE));
    }
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
        File exportFile = temporaryFolder.newFile("dummy.csv");

        String expectedExportFilePath = exportFile.getAbsolutePath();
        File expectedExportFile = new File(expectedExportFilePath);
        assertParseSuccess(parser, expectedExportFile.getAbsolutePath(), new ExportCommand(expectedExportFile));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //non absolute file path
        assertParseFailure(parser, "data/exportTestFile.csv", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ParserUtil.MESSAGE_INVALID_CSV_PATH + ExportCommand.MESSAGE_USAGE));

        //invalid file path
        assertParseFailure(parser, currentDirectory.getAbsolutePath() + "/data/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ParserUtil.MESSAGE_INVALID_CSV_PATH
                        + ExportCommand.MESSAGE_USAGE));

        //invalid file type
        assertParseFailure(parser, currentDirectory.getAbsolutePath() + "/data/importTestFile.txt",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ParserUtil.MESSAGE_INVALID_CSV_PATH
                        + ExportCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\club\logic\parser\ImportCommandParserTest.java
``` java
package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.club.logic.commands.ImportCommand;

public class ImportCommandParserTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private ImportCommandParser parser = new ImportCommandParser();
    private String currentDirectoryPath = "./";
    private File currentDirectory = new File(currentDirectoryPath);

    @Test
    public void parse_validArgs_returnsImportCommand() throws Exception {
        File importFile = temporaryFolder.newFile("dummy.csv");

        String expectedImportFilePath = importFile.getAbsolutePath();
        File expectedImportFile = new File(expectedImportFilePath);
        assertParseSuccess(parser, expectedImportFile.getAbsolutePath(), new ImportCommand(expectedImportFile));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //non absolute file path
        assertParseFailure(parser, "data/dummy.csv", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ParserUtil.MESSAGE_INVALID_CSV_PATH + ImportCommand.MESSAGE_USAGE));

        //invalid file path
        assertParseFailure(parser, currentDirectory.getAbsolutePath() + "/data/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ParserUtil.MESSAGE_INVALID_CSV_PATH
                        + ImportCommand.MESSAGE_USAGE));

        //invalid file type
        assertParseFailure(parser, currentDirectory.getAbsolutePath() + "/data/importTestFile.txt",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ParserUtil.MESSAGE_INVALID_CSV_PATH
                        + ImportCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\club\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parsePhotoPath_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseProfilePhoto((String) null));
    }

    @Test
    public void parsePhotoPath_invalidValues_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseProfilePhoto(INVALID_PHOTO_PATH_1));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseProfilePhoto(INVALID_PHOTO_PATH_2));
    }

    @Test
    public void parsePhotoPath_validValueWithoutWhitespace_returnsProfilePhoto() throws Exception {
        ProfilePhoto expectedProfilePhoto = new ProfilePhoto(VALID_PHOTO_PATH);
        assertEquals(expectedProfilePhoto, ParserUtil.parseProfilePhoto(VALID_PHOTO_PATH));
    }

    @Test
    public void parsePhotoPath_validValueWithWhitespace_returnsTrimmedProfilePhoto() throws Exception {
        String photoPathWithWhitespace = WHITESPACE + VALID_PHOTO_PATH + WHITESPACE;
        ProfilePhoto expectedProfilePhoto = new ProfilePhoto(VALID_PHOTO_PATH);
        assertEquals(expectedProfilePhoto, ParserUtil.parseProfilePhoto(photoPathWithWhitespace));
    }
}
```
###### \java\seedu\club\model\ClubBookTest.java
``` java
    @Test
    public void updateMember_detailsChanged_clubBookunchanged() throws Exception {
        ClubBook clubBookUpdatedToAmy = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
        try {
            clubBookUpdatedToAmy.updateMember(AMY, BOB);
        } catch (DuplicateMatricNumberException dme) {
            ClubBook expectedClubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
            assertEquals(expectedClubBook, clubBookUpdatedToAmy);
        }
    }
```
###### \java\seedu\club\model\ModelManagerTest.java
``` java
    @Test
    public void addProfilePhoto_eventRaised() throws Exception {
        String photoDirectory = "./src/test/resources/photos/";
        String photoFileName = "testPhoto.png";


        ClubBook clubBook = new ClubBookBuilder().withMember(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(BENSON.getCredentials().getUsername().value,
                BENSON.getCredentials().getPassword().value);

        ProfilePhoto newPhoto = new ProfilePhoto(SAVE_PHOTO_DIRECTORY + BENSON.getMatricNumber()
                + PHOTO_FILE_EXTENSION);

        modelManager.addProfilePhoto(photoDirectory + photoFileName);

        //2 events are raised: ProfilePhotoChangedEvent and ClubBookChangedEvent
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 2);
        //Last event raised is ClubBookChangedEvent
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ClubBookChangedEvent);

        assertEquals(newPhoto, modelManager.getLoggedInMember().getProfilePhoto());
    }

    @Test
    public void removeProfilePhoto_success() {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        ProfilePhoto defaultPhoto = new ProfilePhoto(DEFAULT_PHOTO_PATH);

        modelManager.logsInMember(BENSON.getCredentials().getUsername().value,
                BENSON.getCredentials().getPassword().value);

        modelManager.removeProfilePhoto();

        assertEquals(defaultPhoto, modelManager.getLoggedInMember().getProfilePhoto());
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
```
###### \java\seedu\club\storage\ProfilePhotoStorageTest.java
``` java
package seedu.club.storage;

import static org.junit.Assert.assertTrue;
import static seedu.club.storage.ProfilePhotoStorage.PHOTO_FILE_EXTENSION;
import static seedu.club.storage.ProfilePhotoStorage.SAVE_PHOTO_DIRECTORY;

import java.io.File;
import java.io.InputStream;

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
    public void copyProfilePhoto_validPath_success() throws Exception {
        String photoPath = null;
        String copyName = "testCopy";

        ProfilePhotoStorage profilePhotoStorage = new ProfilePhotoStorage();
        photoPath = testFolder.newFile("testPhoto.png").getAbsolutePath();
        profilePhotoStorage.copyOriginalPhotoFile(photoPath, copyName);
        assertTrue(new File(SAVE_PHOTO_DIRECTORY + copyName + PHOTO_FILE_EXTENSION).exists());
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
     * A Stub class to throw an exception when the createPhotoFileCopy method is called.
     */
    class ProfilePhotoStorageExceptionThrowingStub extends ProfilePhotoStorage {
        @Override
        public void createPhotoFileCopy(InputStream photoInputStream, String newPath) throws PhotoWriteException {
            throw new PhotoWriteException("dummy exception");
        }
    }

}
```
###### \java\seedu\club\storage\StorageManagerTest.java
``` java
    @Test
    public void handleProfilePictureChangedEvent_readExceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that throws an exception when the copy photo method is called.
        Storage storage = new StorageManager(new XmlClubBookStorage("dummy"),
                new JsonUserPrefsStorage("dummy"), new ProfilePhotoStorageReadExceptionThrowingStub(),
                new CsvClubBookStorage());

        File photoFile = new File("./src/test/resources/photos/");
        String photoPath = photoFile.getAbsolutePath();
        storage.handleProfilePictureChangedEvent(new ProfilePhotoChangedEvent(photoPath, "testPhotoCopy.png"));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataReadingExceptionEvent);
    }

    @Test
    public void handleProfilePictureChangedEvent_writeExceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that throws an exception when the copy photo method is called.
        Storage storage = new StorageManager(new XmlClubBookStorage("dummy"),
                new JsonUserPrefsStorage("dummy"), new ProfilePhotoStorageWriteExceptionThrowingStub(),
                new CsvClubBookStorage());

        File photoFile = new File("./src/test/resources/photos/");
        String photoPath = photoFile.getAbsolutePath();
        storage.handleProfilePictureChangedEvent(new ProfilePhotoChangedEvent(photoPath, "testPhotoCopy.png"));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleExportDataEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that throws an exception when the save data method is called.
        Storage storage = new StorageManager(new XmlClubBookStorage("dummy"),
                new JsonUserPrefsStorage("dummy"), new ProfilePhotoStorage(),
                new CsvClubBookStorageExceptionThrowingStub());

        File dummyFile = new File("./src/test/data/CsvClubBookStorageTest/exportFile.csv");
        storage.handleExportDataEvent(new NewExportDataAvailableEvent(dummyFile, "dummy data"));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw a read exception when the copy photo method is called.
     */
    class ProfilePhotoStorageReadExceptionThrowingStub extends ProfilePhotoStorage {

        @Override
        public void copyOriginalPhotoFile(String originalFilePath, String newName)
                throws PhotoReadException, PhotoWriteException {
            throw new PhotoReadException("dummy exception");
        }
    }

    /**
     * A Stub class to throw a write exception when the copy photo method is called.
     */
    class ProfilePhotoStorageWriteExceptionThrowingStub extends ProfilePhotoStorage {

        @Override
        public void copyOriginalPhotoFile(String originalFilePath, String newName)
                throws PhotoReadException, PhotoWriteException {
            throw new PhotoWriteException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save data method is called.
     */
    class CsvClubBookStorageExceptionThrowingStub extends CsvClubBookStorage {

        @Override
        public void saveData(String data) throws IOException {
            throw new IOException("dummy exception");
        }
    }
```
###### \java\systemtests\DeleteTagCommandSystemTest.java
``` java
package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_TAG;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.club.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_HEAD;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_UNUSED_DESC;
import static seedu.club.logic.commands.DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS;
import static seedu.club.logic.commands.DeleteTagCommand.MESSAGE_NON_EXISTENT_TAG;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.commands.DeleteTagCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.RedoCommand;
import seedu.club.logic.commands.UndoCommand;
import seedu.club.model.Model;
import seedu.club.model.member.Member;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;

public class DeleteTagCommandSystemTest extends ClubBookSystemTest {

    @Test
    public void deleteTag() {
        Model expectedModel = getModel();
        Model modelBeforeDeletingGroup = getModel();
        ObservableList<Member> memberObservableList = expectedModel.getClubBook().getMemberList();
        expectedModel.updateFilteredMemberList(expectedModel.PREDICATE_SHOW_ALL_MEMBERS);
        modelBeforeDeletingGroup.updateFilteredMemberList(expectedModel.PREDICATE_SHOW_ALL_MEMBERS);
        Tag deletedTag;
        String command;
        String logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(0).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);

        /* ------------------------ Perform deleteTag operations on the shown unfiltered list -------------------- */

        /* Case: delete a valid tag which is present in the club book */
        command = DeleteTagCommand.COMMAND_WORD + TAG_DESC_FRIEND;
        deletedTag = deleteTagFromModel(expectedModel, VALID_TAG_HEAD);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TAG_SUCCESS, deletedTag);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: undo deleting the tag -> tag restored in relevant members */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingGroup, expectedResultMessage);

        /*Case: redo deleting the tag -> deleted */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete an invalid tag */
        command = DeleteTagCommand.COMMAND_WORD + INVALID_TAG_DESC;
        deletedTag = deleteTagFromModel(expectedModel, INVALID_TAG);
        assertEquals(null, deletedTag);
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: delete a non-existent tag */
        command = DeleteTagCommand.COMMAND_WORD + VALID_TAG_UNUSED_DESC;
        deletedTag = deleteTagFromModel(expectedModel, VALID_TAG_UNUSED);
        assertEquals(null, deletedTag);
        assertCommandFailure(command, MESSAGE_NON_EXISTENT_TAG);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model model, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, model);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        executeCommand(command);
        Model expectedModel = getModel();
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }


    /**
     * Removes the tag from model
     * @param model expected model
     * @param tag new Tag object to be created with this string
     * @return either a valid Tag object if the group has been deleted; null otherwise
     */
    private Tag deleteTagFromModel(Model model, String tag) {
        if (Tag.isValidTagName(tag)) {
            try {
                model.deleteTag(new Tag(tag));
            } catch (TagNotFoundException tnfe) {
                return null;
            }
            return new Tag(tag);
        }
        return null;
    }
}
```
