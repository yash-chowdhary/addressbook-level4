//@@author amrut-prabhu
package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.logic.commands.exceptions.IllegalExecutionException;
import seedu.club.model.Model;
import seedu.club.model.ReadOnlyClubBook;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.member.ProfilePhoto;
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
        assertEquals(ChangeProfilePhotoCommand.MESSAGE_CHANGE_PROFILE_PHOTO_SUCCESS, commandResult.feedbackToUser);
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
        public void updateMember(Member target, Member editedMember) throws DuplicateMemberException {
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
     * A Model stub that always throw a PhotoReadException when trying to add a profile photo.
     */
    private class ModelStubThrowingPhotoReadException extends ModelStub {
        @Override
        public void addProfilePhoto(String originalPhotoPath) throws PhotoReadException {
            throw new PhotoReadException();
        }
    }

    /**
     * A Model stub that always accept the path of the profile photo to be added.
     */
    private class ModelStubAcceptingAddProfilePhoto extends ModelStub {
        @Override
        public void addProfilePhoto(String originalPhotoPath) throws PhotoReadException {
            requireNonNull(originalPhotoPath);
        }
    }

}
