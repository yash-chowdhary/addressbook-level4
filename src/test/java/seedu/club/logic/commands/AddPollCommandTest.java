package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
import seedu.club.model.ClubBook;
import seedu.club.model.Model;
import seedu.club.model.ReadOnlyClubBook;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
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
import seedu.club.testutil.PollBuilder;

public class AddPollCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPoll_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddPollCommand(null);
    }

    @Test
    public void execute_pollAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPollAdded modelStub = new ModelStubAcceptingPollAdded();
        Poll validPoll = new PollBuilder().build();

        CommandResult commandResult = getAddPollCommandForPoll(validPoll, modelStub).execute();

        assertEquals(String.format(AddPollCommand.MESSAGE_SUCCESS, validPoll), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validPoll), modelStub.pollsAdded);
    }

    @Test
    public void execute_duplicatePoll_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePollException();
        Poll validPoll = new PollBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddPollCommand.MESSAGE_DUPLICATE_POLL);

        getAddPollCommandForPoll(validPoll, modelStub).execute();
    }

    @Test
    public void equals() {
        Poll lovePoll = new PollBuilder().withQuestion("What is love?").build();
        Poll lifePoll = new PollBuilder().withQuestion("What is life?").build();
        AddPollCommand addLovePollCommand = new AddPollCommand(lovePoll);
        AddPollCommand addLifePollCommand = new AddPollCommand(lifePoll);

        // same object -> returns true
        assertTrue(addLovePollCommand.equals(addLovePollCommand));

        // same values -> returns true
        AddPollCommand addAliceCommandCopy = new AddPollCommand(lovePoll);
        assertTrue(addLovePollCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addLovePollCommand.equals(1));

        // null -> returns false
        assertFalse(addLovePollCommand.equals(null));

        // different poll -> returns false
        assertFalse(addLovePollCommand.equals(addLifePollCommand));
    }

    /**
     * Generates a new AddPollCommand with the details of the given poll.
     */
    private AddPollCommand getAddPollCommandForPoll(Poll poll, Model model) {
        AddPollCommand command = new AddPollCommand(poll);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void exportClubConnectMembers(File exportFilePath) {
            fail("This method should not be called.");
        }

        @Override
        public void viewAllTasks() throws TasksCannotBeDisplayedException {
            fail("This method should not be called");
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
        public void logOutMember() {
            fail("This method should not be called.");
        }

        @Override
        public void logsInMember(String username, String password) {
            fail("This method should not be called");
        }


        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void signUpMember(Member member) {
            fail("This method should not be called");
            return;
        }

        @Override
        public FilteredList<Member> getFilteredMemberList() {
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

        @Override
        public void addProfilePhoto(String originalPhotoPath) throws PhotoReadException {
            fail("This method should not be called.");
        }

        @Override
        public Member getLoggedInMember() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void removeGroup(Group toRemove) throws GroupNotFoundException, GroupCannotBeRemovedException {
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
        public void deleteMember(Member member) throws MemberNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateMember(Member member, Member editedMember)
                throws DuplicateMemberException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) throws TagNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Poll> getFilteredPollList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredMemberList(Predicate<Member> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredPollList(Predicate<Poll> poll) {
            fail("This method should not be called.");
        }

        @Override
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
        public String generateEmailRecipients(Group group, Tag tag) throws GroupNotFoundException,
                TagNotFoundException {
            fail("This method should not be called");
            return null;
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
        public void deleteTask(Task taskToDelete) throws TaskNotFoundException, TaskCannotBeDeletedException {
            fail("This method should not be called");
        }
    }

    /**
     * A Model stub that always throw a DuplicatePollException when trying to add a poll.
     */
    private class ModelStubThrowingDuplicatePollException extends ModelStub {
        @Override
        public void addPoll(Poll poll) throws DuplicatePollException {
            throw new DuplicatePollException();
        }

        @Override
        public ReadOnlyClubBook getClubBook() {
            return new ClubBook();
        }
    }

    /**
     * A Model stub that always accept the poll being added.
     */
    private class ModelStubAcceptingPollAdded extends ModelStub {
        private final ArrayList<Poll> pollsAdded = new ArrayList<>();

        @Override
        public void addPoll(Poll poll) throws DuplicatePollException {
            requireNonNull(poll);
            pollsAdded.add(poll);
        }

        @Override
        public ReadOnlyClubBook getClubBook() {
            return new ClubBook();
        }
    }

}
