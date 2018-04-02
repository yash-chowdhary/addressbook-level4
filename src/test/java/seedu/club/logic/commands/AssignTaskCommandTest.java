package seedu.club.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.club.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.club.testutil.TypicalMembers.BENSON;
import static seedu.club.testutil.TypicalMembers.CARL;
import static seedu.club.testutil.TypicalTasks.BOOK_AUDITORIUM;
import static seedu.club.testutil.TypicalTasks.BUY_CONFETTI;

import java.io.File;
import java.io.IOException;
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
import seedu.club.model.member.exceptions.MemberListNotEmptyException;
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

public class AssignTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AssignTaskCommand(null, null);
    }

    @Test
    public void execute_taskAcceptedByModel_assignSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();
        Name validName = new Name(BENSON.getName().toString());

        CommandResult commandResult =  getAssignTaskCommandForTask(validTask, validName, modelStub)
                .executeUndoableCommand();

        assertEquals(String.format(AssignTaskCommand.MESSAGE_SUCCESS, validName), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateTaskException();
        Task validTask = new TaskBuilder().build();
        Name validName = new Name(BENSON.getName().toString());

        thrown.expect(CommandException.class);
        thrown.expectMessage(AssignTaskCommand.MESSAGE_DUPLICATE_TASK);

        getAssignTaskCommandForTask(validTask, validName, modelStub).executeUndoableCommand();
    }

    @Test
    public void execute_memberNotFound_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingMemberNotFoundException();
        Task validTask = new TaskBuilder().build();
        Name validName = new Name(BENSON.getName().toString());

        thrown.expect(CommandException.class);
        thrown.expectMessage(AssignTaskCommand.MESSAGE_MEMBER_NOT_FOUND);

        getAssignTaskCommandForTask(validTask, validName, modelStub).executeUndoableCommand();
    }

    @Test
    public void equals() {
        Task firstTask = BOOK_AUDITORIUM;
        Task secondTask = BUY_CONFETTI;
        Name firstName = BENSON.getName();
        Name secondName = CARL.getName();

        AssignTaskCommand firstAssignTaskCommand = new AssignTaskCommand(firstTask, firstName);
        AssignTaskCommand secondAssignTaskCommand = new AssignTaskCommand(secondTask, secondName);

        assertTrue(firstAssignTaskCommand.equals(firstAssignTaskCommand));
        assertFalse(firstAssignTaskCommand.equals(null));
        assertFalse(firstAssignTaskCommand.equals(true));

        AssignTaskCommand firstCommandCopy = new AssignTaskCommand(firstTask, firstName);
        assertTrue(firstAssignTaskCommand.equals(firstCommandCopy));

        assertFalse(firstAssignTaskCommand.equals(secondAssignTaskCommand));
    }

    /**
     * Generates a new AddTaskCommand with the details of the given task.
     */
    private AssignTaskCommand getAssignTaskCommandForTask(Task task, Name name, Model model) {
        AssignTaskCommand command = new AssignTaskCommand(task, name);
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
        public void viewMyTasks() throws TasksAlreadyListedException {
            fail("This should not be called");
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
        public void signUpMember(Member member) throws MemberListNotEmptyException {
            fail("This method should not be called");
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
            fail("This method should not be called.");
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
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            fail("This method should not be called");
            return;
        }
    }


    /**
     * A Model stub that always throw a DuplicateTaskException when trying to add a task.
     */
    private class ModelStubThrowingDuplicateTaskException extends ModelStub {
        @Override
        public void assignTask(Task toAdd, Name name) throws MemberNotFoundException, DuplicateTaskException {
            throw new DuplicateTaskException();
        }

        @Override
        public ReadOnlyClubBook getClubBook() {
            return new ClubBook();
        }
    }

    /**
     * A Model stub that always throw a DuplicateTaskException when trying to add a task.
     */
    private class ModelStubThrowingMemberNotFoundException extends ModelStub {
        @Override
        public void assignTask(Task toAdd, Name name) throws MemberNotFoundException, DuplicateTaskException {
            throw new MemberNotFoundException();
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
        public void assignTask(Task toAdd, Name name) throws MemberNotFoundException, DuplicateTaskException {
            requireAllNonNull(toAdd, name);
            tasksAdded.add(toAdd);
        }

        @Override
        public ReadOnlyClubBook getClubBook() {
            return new ClubBook();
        }
    }

}
