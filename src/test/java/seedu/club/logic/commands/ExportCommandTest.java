//@@author amrut-prabhu
package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.ReadOnlyClubBook;
import seedu.club.model.UserPrefs;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.Member;
import seedu.club.model.member.exceptions.DuplicateMemberException;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.exceptions.DuplicatePollException;
import seedu.club.model.poll.exceptions.PollNotFoundException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskCannotBeDeletedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;

public class ExportCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Model model = new ModelManager(getTypicalClubBook(), new UserPrefs());
    private String currentDirectoryPath = ".";
    private File currentDirectory = new File(currentDirectoryPath);

    @Test
    public void constructor_nullMember_throwsNullPointerException() {
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
    public void execute_duplicateMember_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingIoException();

        String validFilePath = testFolder.getRoot().getPath() + "TempClubBook.csv";
        File exportFile = new File(validFilePath);

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

        // same values -> returns true
        assertTrue(exportCommand.equals(sameFileExportCommand));

        // different types -> returns false
        assertFalse(exportCommand.equals(1));

        // null -> returns false
        assertFalse(exportCommand.equals(null));

        // different member -> returns false
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
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public FilteredList<Poll> getFilteredPollList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void addMember(Member member) throws DuplicateMemberException {
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
        public void addProfilePhoto(String originalPhotoPath) {
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
        public void updateMember(Member target, Member editedMember)
                throws DuplicateMemberException {
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
    }

    /**
     * A Model stub that always throw a IOException when trying to export to a file.
     */
    private class ModelStubThrowingIoException extends ModelStub {
        @Override
        public void exportClubConnectMembers(File exportFile) throws IOException {
            throw new IOException();
        }
    }

    /**
     * A Model stub that always accept the file being exported to.
     */
    private class ModelStubAcceptingExport extends ModelStub {

        @Override
        public void exportClubConnectMembers(File exportFile) throws IOException {
            requireNonNull(exportFile);
        }
    }

}
