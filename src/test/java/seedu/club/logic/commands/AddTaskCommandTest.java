package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.club.testutil.TypicalTasks.BOOK_AUDITORIUM;
import static seedu.club.testutil.TypicalTasks.BUY_CONFETTI;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
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
import seedu.club.testutil.TaskBuilder;

//@@author yash-chowdhary
public class AddTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddTaskCommand(null);
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = getAddTaskCommandForTask(validTask, modelStub).executeUndoableCommand();

        assertEquals(AddTaskCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateTaskException();
        Task validTask = new TaskBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTaskCommand.MESSAGE_DUPLICATE_TASK);

        getAddTaskCommandForTask(validTask, modelStub).executeUndoableCommand();
    }

    @Test
    public void equals() {
        Task firstTask = BOOK_AUDITORIUM;
        Task secondTask = BUY_CONFETTI;

        AddTaskCommand firstAddTaskCommand = new AddTaskCommand(firstTask);
        AddTaskCommand secondAddTaskCommand = new AddTaskCommand(secondTask);

        assertTrue(firstAddTaskCommand.equals(firstAddTaskCommand));
        assertFalse(firstAddTaskCommand.equals(null));
        assertFalse(firstAddTaskCommand.equals(true));

        AddTaskCommand firstCommandCopy = new AddTaskCommand(firstTask);
        assertTrue(firstAddTaskCommand.equals(firstCommandCopy));

        assertFalse(firstAddTaskCommand.equals(secondAddTaskCommand));
    }


    /**
     * Generates a new AddTaskCommand with the details of the given task.
     */
    private AddTaskCommand getAddTaskCommandForTask(Task task, Model model) {
        AddTaskCommand command = new AddTaskCommand(task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void resetData(ReadOnlyClubBook newData) {
            fail("This method should not be called");
        }

        @Override
        public ReadOnlyClubBook getClubBook() {
            fail("This method should not be called");
            return null;
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
        public void deleteTask(Task taskToDelete) throws TaskNotFoundException, TaskCannotBeDeletedException {
            fail("This method should not be called");
            return;
        }

        @Override
        public void deleteMember(Member target) throws MemberNotFoundException {
            fail("This method should not be called");
            return;
        }

        @Override
        public void addMember(Member member) throws DuplicateMemberException {
            fail("This method should not be called");
            return;
        }

        @Override
        public void updateMember(Member target, Member editedMember) throws DuplicateMemberException,
                MemberNotFoundException {
            fail("This method should not be called");
            return;
        }

        @Override
        /** Adds the given poll */
        public void addPoll(Poll poll) throws DuplicatePollException {
            fail("This method should not be called");
        }

        @Override
        /** Deletes the given member. */
        public void deletePoll(Poll poll) throws PollNotFoundException {
            fail("This method should not be called");
        }

        @Override
        public void updateFilteredPollList(Predicate<Poll> predicate) {
            fail("This method should not be called");
        }

        @Override
        public ObservableList<Member> getFilteredMemberList() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public ObservableList<Poll> getFilteredPollList() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void updateFilteredMemberList(Predicate<Member> predicate) {
            fail("This method should not be called");
            return;
        }

        @Override
        public void logsInMember(String username, String password) {
            fail("This method should not be called");
            return;
        }

        @Override
        public Member getLoggedInMember() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void deleteTag(Tag tag) throws TagNotFoundException {
            fail("This method should not be called");
            return;
        }

        @Override
        public void addProfilePhoto(String originalPhotoPath) throws PhotoReadException {
            fail("This method should not be called");
        }

        @Override
        public ObservableList<Tag> getFilteredTagList() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void updateFilteredTagList(Predicate<Tag> predicate) {
            fail("This method should not be called");
            return;
        }

        @Override
        public void removeGroup(Group toRemove) throws GroupNotFoundException, GroupCannotBeRemovedException {
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
        public void sendEmail(String recipients, Client client, Subject subject, Body body) {
            fail("This method should not be called");
            return;
        }

        @Override
        public void logOutMember() {
            fail("This method should not be called");
        }

        public void exportClubConnectMembers(File exportFilePath) {
            fail("This method should not be called");
        }

        @Override
        public void addTaskToTaskList(Task toAdd) throws DuplicateTaskException {
            fail("This method should not be called");
            return;
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            fail("This method should not be called");
            return;
        }

        @Override
        public void signUpMember(Member member) {
            fail("This method should not be called");
            return;
        }
    }

    /**
     * A Model stub that always throw a DuplicateTaskException when trying to add a task.
     */
    private class ModelStubThrowingDuplicateTaskException extends ModelStub {
        @Override
        public void addTaskToTaskList(Task toAdd) throws DuplicateTaskException {
            throw new DuplicateTaskException();
        }

        @Override
        public ReadOnlyClubBook getClubBook() {
            return new ClubBook();
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();

        @Override
        public void addTaskToTaskList(Task toAdd) throws DuplicateTaskException {
            requireNonNull(toAdd);
            tasksAdded.add(toAdd);
        }

        @Override
        public ReadOnlyClubBook getClubBook() {
            return new ClubBook();
        }
    }
}
