//@@author amrut-prabhu
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
        //@@author th14thmusician
        @Override
        public ReadOnlyClubBook getClubBook() {
            ClubBook clubBook = new ClubBook();
            try {
                clubBook.addMember(memberStub);
                clubBook.logInMember("A5215090A", "password");
            } catch (DuplicateMatricNumberException e) {
                e.printStackTrace();
            }
            return clubBook;
        }
        @Override
        public Member getLoggedInMember() {
            return memberStub;
        }
        //@@author amrut-prabhu
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

        //@@author th14thmusician
        @Override
        public ReadOnlyClubBook getClubBook() {
            ClubBook clubBook = new ClubBook();
            try {
                clubBook.addMember(memberStub);
                clubBook.logInMember("A5215090A", "password");
            } catch (DuplicateMatricNumberException e) {
                e.printStackTrace();
            }
            return clubBook;
        }
        @Override
        public Member getLoggedInMember() {
            return memberStub;
        }
        //@@author amrut-prabhu
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

        //@@author th14thmusician
        @Override
        public ReadOnlyClubBook getClubBook() {
            ClubBook clubBook = new ClubBook();
            try {
                clubBook.addMember(memberStub);
                clubBook.logInMember("A5215090A", "password");
            } catch (DuplicateMatricNumberException e) {
                e.printStackTrace();
            }
            return clubBook;
        }
        @Override
        public Member getLoggedInMember() {
            return memberStub;
        }
        //@@author
    }
}

