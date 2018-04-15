# yash-chowdhary
###### \java\seedu\club\commons\events\ui\SendEmailRequestEventTest.java
``` java
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;


public class SendEmailRequestEventTest {

    @Test
    public void testSendEmailRequestEvent() throws Exception {
        String expectedRecipients = "abc@example.com,xyz@test.com,pqr@gmail.com";
        Subject expectedSubject = new Subject(Subject.TEST_SUBJECT_STRING);
        Body expectedBody = new Body(Body.TEST_BODY_STRING);
        Client expectedClient = new Client(Client.VALID_CLIENT_GMAIL);

        SendEmailRequestEvent sendEmailRequestEvent = new SendEmailRequestEvent(expectedRecipients,
                expectedSubject, expectedBody, expectedClient);
        assertTrue(sendEmailRequestEvent.getRecipients().equals(expectedRecipients));
        assertTrue(sendEmailRequestEvent.getSubject().equals(expectedSubject));
        assertTrue(sendEmailRequestEvent.getBody().equals(expectedBody));
        assertTrue(sendEmailRequestEvent.getClient().equals(expectedClient));
    }
}
```
###### \java\seedu\club\logic\commands\AddTaskCommandTest.java
``` java
import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.club.testutil.TypicalTasks.BOOK_AUDITORIUM;
import static seedu.club.testutil.TypicalTasks.BUY_CONFETTI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
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
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.Email;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.member.Phone;
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
import seedu.club.testutil.TaskBuilder;


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
        public void resetData(ReadOnlyClubBook newData) {
            fail("This method should not be called");
        }

        @Override
        public ReadOnlyClubBook getClubBook() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void viewAllTasks() throws TasksAlreadyListedException {
            fail("This method should not be called");
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
        public void deleteTask(Task taskToDelete) throws TaskNotFoundException, TaskCannotBeDeletedException {
            fail("This method should not be called");
            return;
        }

        @Override
        public int deleteMember(Member target) throws MemberNotFoundException {
            fail("This method should not be called");
            return -1;
        }

        @Override
        public void addMember(Member member) throws DuplicateMatricNumberException {
            fail("This method should not be called");
            return;
        }

        @Override
        public int updateMember(Member target, Member editedMember) throws DuplicateMatricNumberException,
                MemberNotFoundException {
            fail("This method should not be called");
            return -1;
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
        public void deleteGroup(Group toRemove) throws GroupNotFoundException, GroupCannotBeRemovedException {
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
        public void exportClubConnectMembers(File exportFilePath) {
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
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            fail("This method should not be called");
            return;
        }

        @Override
        public void changePassword(String username, String oldPassword, String newPassword)
                throws PasswordIncorrectException {
            fail("This method should not be called");
            return;
        }
        public void signUpMember(Member member) {
            fail("This method should not be called");
            return;
        }

        @Override
        public void clearClubBook() {
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
    }

    /**
     * A Model stub that always throw a DuplicateTaskException when trying to add a task.
     */
    private class ModelStubThrowingDuplicateTaskException extends ModelStub {
        private final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("logistics"),
                getTagSet("head"));

        @Override
        public void addTaskToTaskList(Task toAdd) throws DuplicateTaskException {
            throw new DuplicateTaskException();
        }

```
###### \java\seedu\club\logic\commands\AssignTaskCommandTest.java
``` java
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
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
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
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.Email;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.member.Phone;
import seedu.club.model.member.exceptions.DataToChangeIsNotCurrentlyLoggedInMemberException;
import seedu.club.model.member.exceptions.DuplicateMatricNumberException;
import seedu.club.model.member.exceptions.MemberListNotEmptyException;
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
        MatricNumber validMatricNumber = new MatricNumber(BENSON.getMatricNumber().toString());

        CommandResult commandResult =  getAssignTaskCommandForTask(validTask, validMatricNumber, modelStub)
                .executeUndoableCommand();

        assertEquals(String.format(AssignTaskCommand.MESSAGE_SUCCESS, validMatricNumber), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateTaskException();
        Task validTask = new TaskBuilder().build();
        MatricNumber validMatricNumber = new MatricNumber(BENSON.getMatricNumber().toString());

        thrown.expect(CommandException.class);
        thrown.expectMessage(AssignTaskCommand.MESSAGE_DUPLICATE_TASK);

        getAssignTaskCommandForTask(validTask, validMatricNumber, modelStub).executeUndoableCommand();
    }

    @Test
    public void execute_memberNotFound_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingMemberNotFoundException();
        Task validTask = new TaskBuilder().build();
        MatricNumber validMatricNumber = new MatricNumber(BENSON.getMatricNumber().toString());

        thrown.expect(CommandException.class);
        thrown.expectMessage(AssignTaskCommand.MESSAGE_MEMBER_NOT_FOUND);

        getAssignTaskCommandForTask(validTask, validMatricNumber, modelStub).executeUndoableCommand();
    }

    @Test
    public void equals() {
        Task firstTask = BOOK_AUDITORIUM;
        Task secondTask = BUY_CONFETTI;
        MatricNumber firstMatricNumber = BENSON.getMatricNumber();
        MatricNumber secondMatricNumber = CARL.getMatricNumber();

        AssignTaskCommand firstAssignTaskCommand = new AssignTaskCommand(firstTask, firstMatricNumber);
        AssignTaskCommand secondAssignTaskCommand = new AssignTaskCommand(secondTask, secondMatricNumber);

        assertTrue(firstAssignTaskCommand.equals(firstAssignTaskCommand));
        assertFalse(firstAssignTaskCommand.equals(null));
        assertFalse(firstAssignTaskCommand.equals(true));

        AssignTaskCommand firstCommandCopy = new AssignTaskCommand(firstTask, firstMatricNumber);
        assertTrue(firstAssignTaskCommand.equals(firstCommandCopy));

        assertFalse(firstAssignTaskCommand.equals(secondAssignTaskCommand));
    }

    /**
     * Generates a new AddTaskCommand with the details of the given task.
     */
    private AssignTaskCommand getAssignTaskCommandForTask(Task task, MatricNumber matricNumber, Model model) {
        AssignTaskCommand command = new AssignTaskCommand(task, matricNumber);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

```
###### \java\seedu\club\logic\commands\AssignTaskCommandTest.java
``` java
    /**
     * A Model stub that always throw a DuplicateTaskException when trying to add a task.
     */
    private class ModelStubThrowingDuplicateTaskException extends ModelStub {
        private final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("exco"),
                getTagSet("head"));

        @Override
        public void assignTask(Task toAdd, MatricNumber matricNumber) throws MemberNotFoundException,
                DuplicateTaskException {
            throw new DuplicateTaskException();
        }

```
###### \java\seedu\club\logic\commands\ChangeAssigneeCommandTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.club.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.club.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.club.testutil.TypicalIndexes.INDEX_THIRD_TASK;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalMembers.BENSON;
import static seedu.club.testutil.TypicalMembers.CARL;
import static seedu.club.testutil.TypicalTasks.BOOK_AUDITORIUM;
import static seedu.club.testutil.TypicalTasks.BOOK_AUDITORIUM_COPY;
import static seedu.club.testutil.TypicalTasks.BUY_FOOD;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.model.ClubBook;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.member.Member;
import seedu.club.model.task.Assignee;
import seedu.club.model.task.Task;
import seedu.club.testutil.ClubBookBuilder;
import seedu.club.testutil.MemberBuilder;
import seedu.club.testutil.TaskBuilder;

public class ChangeAssigneeCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;

    @Before
    public void setUp() {
        ClubBook clubBook = new ClubBookBuilder()
                .withMember(ALICE)
                .withMember(BENSON)
                .withTask(BUY_FOOD)
                .withTask(BOOK_AUDITORIUM)
                .withTask(BOOK_AUDITORIUM_COPY)
                .build();
        model = new ModelManager(clubBook, new UserPrefs());
        model.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
    }

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ChangeAssigneeCommand(null, null);
    }

    @Test
    public void execute_validAssignee_success() throws Exception {
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        Member alice = new MemberBuilder(ALICE).build();
        Member benson = new MemberBuilder(BENSON).build();
        Task buyFood = new TaskBuilder().withDescription(BUY_FOOD.getDescription().getDescription())
                .withDate(BUY_FOOD.getDate().getDate())
                .withTime(BUY_FOOD.getTime().getTime())
                .withAssignor(alice.getMatricNumber().toString())
                .withAssignee(benson.getMatricNumber().toString())
                .withStatus(BUY_FOOD.getStatus().getStatus())
                .build();
        Task bookAuditorium = new TaskBuilder(BOOK_AUDITORIUM).build();
        String expectedMessage = String.format(ChangeAssigneeCommand.MESSAGE_CHANGE_SUCCESS,
                buyFood.getDescription().getDescription(), benson.getMatricNumber().toString());

        ClubBook expectedClubBook = new ClubBookBuilder()
                .withMember(alice)
                .withMember(benson)
                .withTask(buyFood)
                .withTask(bookAuditorium)
                .build();

        expectedModel = new ModelManager(expectedClubBook, new UserPrefs());
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        ChangeAssigneeCommand command = prepareCommand(INDEX_THIRD_TASK,
                new Assignee(BENSON.getMatricNumber().toString()));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidAssignee_throwsException() {
        String expectedMessage = ChangeAssigneeCommand.MESSAGE_MEMBER_NOT_FOUND;

        ChangeAssigneeCommand command = prepareCommand(INDEX_FIRST_TASK,
                new Assignee(CARL.getMatricNumber().toString()));

        assertCommandFailure(command, model, expectedMessage);
    }


    @Test
    public void execute_unchangedAssignee_throwsException() {
        String expectedMessage = ChangeAssigneeCommand.MESSAGE_NOT_CHANGED;
        ChangeAssigneeCommand command = prepareCommand(INDEX_FIRST_TASK,
                new Assignee(ALICE.getMatricNumber().toString()));

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_duplicateTask_throwsException() {
        String expectedMessage = ChangeAssigneeCommand.MESSAGE_DUPLICATE_TASK;
        ChangeAssigneeCommand command = prepareCommand(INDEX_FIRST_TASK,
                new Assignee(BENSON.getMatricNumber().toString()));

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).withTask(BUY_FOOD)
                .withTask(BOOK_AUDITORIUM).build();

        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Task taskToEdit = new TaskBuilder(BUY_FOOD).build();
        Task editedTask = new TaskBuilder(taskToEdit).build();
        editedTask.setAssignee(new Assignee(BENSON.getMatricNumber().toString()));
        ChangeAssigneeCommand changeAssigneeCommand = prepareCommand(INDEX_SECOND_TASK,
                new Assignee(BENSON.getMatricNumber().toString()));

        expectedModel = new ModelManager(expectedClubBook, new UserPrefs());
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        changeAssigneeCommand.execute();
        undoRedoStack.push(changeAssigneeCommand);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.changeAssignee(taskToEdit, editedTask);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ChangeAssigneeCommand changeAssigneeCommand = prepareCommand(outOfBoundIndex,
                new Assignee(BENSON.getMatricNumber().toString()));

        assertCommandFailure(changeAssigneeCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ChangeAssigneeCommand changeAssigneeCommand = prepareCommand(outOfBoundIndex,
                new Assignee(BENSON.getMatricNumber().toString()));

        assertCommandFailure(changeAssigneeCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private ChangeAssigneeCommand prepareCommand(Index index, Assignee assignee) {
        ChangeAssigneeCommand changeAssigneeCommand = new ChangeAssigneeCommand(index, assignee);
        changeAssigneeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changeAssigneeCommand;
    }

    @Test
    public void equals() throws Exception {
        ChangeAssigneeCommand changeAssigneeFirstCommand = prepareCommand(INDEX_FIRST_TASK,
                new Assignee(BENSON.getMatricNumber().toString()));
        ChangeAssigneeCommand changeAssigneeSecondCommand = prepareCommand(INDEX_FIRST_TASK,
                new Assignee(CARL.getMatricNumber().toString()));

        changeAssigneeFirstCommand.preprocessUndoableCommand();
        changeAssigneeSecondCommand.preprocessUndoableCommand();

        // same object -> returns true
        assertTrue(changeAssigneeFirstCommand.equals(changeAssigneeFirstCommand));

        // same values -> returns true
        ChangeAssigneeCommand changeAssigneeFirstCommandCopy = prepareCommand(INDEX_FIRST_TASK,
                new Assignee(BENSON.getMatricNumber().toString()));
        assertTrue(changeAssigneeFirstCommand.equals(changeAssigneeFirstCommandCopy));

        // different types -> returns false
        assertFalse(changeAssigneeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(changeAssigneeFirstCommand.equals(null));

        // different member -> returns false
        assertFalse(changeAssigneeFirstCommand.equals(changeAssigneeSecondCommand));
    }

}
```
###### \java\seedu\club\logic\commands\ChangeTaskStatusCommandTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.club.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.club.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalTasks.getTypicalClubBookWithTasks;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.club.commons.core.index.Index;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.task.Status;
import seedu.club.model.task.Task;

public class ChangeTaskStatusCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalClubBookWithTasks(), new UserPrefs());
        model.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
    }

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ChangeTaskStatusCommand(null, null);
    }

    @Test
    public void execute_taskAccepted_changeSuccessful() throws Exception {
        Task taskToEdit = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        ChangeTaskStatusCommand changeTaskStatusCommand = prepareCommand(INDEX_FIRST_TASK,
                new Status(Status.IN_PROGRESS_STATUS));
        changeTaskStatusCommand.preprocessUndoableCommand();

        expectedModel = new ModelManager(getTypicalClubBookWithTasks(), new UserPrefs());
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        String expectedMessage = String.format(ChangeTaskStatusCommand.MESSAGE_CHANGE_SUCCESS,
                taskToEdit.getDescription().getDescription());
        Task editedTask = new Task(taskToEdit);
        editedTask.setStatus(new Status(Status.IN_PROGRESS_STATUS));
        expectedModel.changeStatus(taskToEdit, editedTask);

        assertCommandSuccess(changeTaskStatusCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsException() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ChangeTaskStatusCommand command = prepareCommand(invalidIndex,
                new Status(Status.IN_PROGRESS_STATUS));

        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ChangeTaskStatusCommand changeTaskStatusCommand = prepareCommand(outOfBoundIndex,
                new Status(Status.IN_PROGRESS_STATUS));

        assertCommandFailure(changeTaskStatusCommand, model, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Task taskToEdit = model.getClubBook().getTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task editedTask = new Task(taskToEdit);
        editedTask.setStatus(new Status(Status.COMPLETED_STATUS));
        ChangeTaskStatusCommand changeTaskStatusCommand = prepareCommand(INDEX_FIRST_TASK,
                new Status(Status.IN_PROGRESS_STATUS));

        expectedModel = new ModelManager(getTypicalClubBookWithTasks(), new UserPrefs());
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        changeTaskStatusCommand.execute();
        undoRedoStack.push(changeTaskStatusCommand);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.changeStatus(taskToEdit, editedTask);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ChangeTaskStatusCommand changeTaskStatusCommand = prepareCommand(outOfBoundIndex,
                new Status(Status.IN_PROGRESS_STATUS));

        assertCommandFailure(changeTaskStatusCommand, model, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    private ChangeTaskStatusCommand prepareCommand(Index index, Status status) {
        ChangeTaskStatusCommand changeTaskStatusCommand = new ChangeTaskStatusCommand(index, status);
        changeTaskStatusCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changeTaskStatusCommand;
    }

    @Test
    public void equals() throws Exception {
        ChangeTaskStatusCommand changeTaskStatusFirstCommand = prepareCommand(INDEX_FIRST_TASK,
                new Status(Status.IN_PROGRESS_STATUS));
        ChangeTaskStatusCommand changeTaskStatusSecondCommand = prepareCommand(INDEX_SECOND_TASK,
                new Status(Status.COMPLETED_STATUS));

        changeTaskStatusFirstCommand.preprocessUndoableCommand();
        changeTaskStatusSecondCommand.preprocessUndoableCommand();

        // same object -> returns true
        assertTrue(changeTaskStatusFirstCommand.equals(changeTaskStatusFirstCommand));

        // same values -> returns true
        ChangeTaskStatusCommand changeTaskStatusFirstCommandCopy = prepareCommand(INDEX_FIRST_TASK,
                new Status(Status.IN_PROGRESS_STATUS));
        assertTrue(changeTaskStatusFirstCommand.equals(changeTaskStatusFirstCommandCopy));

        // different types -> returns false
        assertFalse(changeTaskStatusFirstCommand.equals(1));

        // null -> returns false
        assertFalse(changeTaskStatusFirstCommand.equals(null));

        // different member -> returns false
        assertFalse(changeTaskStatusFirstCommand.equals(changeTaskStatusSecondCommand));
    }
}
```
###### \java\seedu\club\logic\commands\DeleteGroupCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.commons.core.Messages.MESSAGE_MANDATORY_GROUP;
import static seedu.club.commons.core.Messages.MESSAGE_NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.MANDATORY_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_TEST;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.club.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.club.testutil.TypicalIndexes.INDEX_FOURTH_MEMBER;
import static seedu.club.testutil.TypicalMembers.DANIEL;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.group.Group;
import seedu.club.model.member.Member;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteGroupCommand}.
 */
public class DeleteGroupCommandTest {
    private Model model;
    private Model expectedModel;
    private ObservableList<Member> observableList;
    private Member member;

```
###### \java\seedu\club\logic\commands\DeleteGroupCommandTest.java
``` java
    @Test
    public void execute_validGroup_success() throws Exception {
        Group groupToDelete = model.getFilteredMemberList().get(INDEX_FOURTH_MEMBER.getZeroBased()).getGroup();
        DeleteGroupCommand deleteGroupCommand = prepareCommand(DANIEL.getGroup());

        String expectedMessage = String.format(DeleteGroupCommand.MESSAGE_SUCCESS, groupToDelete);
        expectedModel.deleteGroup(groupToDelete);


        assertCommandSuccess(deleteGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentGroup_throwsCommandException() {
        Group nonExistentGroup = new Group(NON_EXISTENT_GROUP);
        DeleteGroupCommand deleteGroupCommand = prepareCommand(nonExistentGroup);

        String expectedMessage = String.format(MESSAGE_NON_EXISTENT_GROUP, nonExistentGroup);
        assertCommandFailure(deleteGroupCommand, model, expectedMessage);
    }

    @Test
    public void execute_mandatoryGroup_throwsCommandException() {
        Group mandatoryGroup = new Group(MANDATORY_GROUP);
        DeleteGroupCommand deleteGroupCommand = prepareCommand(mandatoryGroup);
        String expectedMessage = String.format(MESSAGE_MANDATORY_GROUP, mandatoryGroup.toString());
        assertCommandFailure(deleteGroupCommand, model, expectedMessage);
    }

    @Test
    public void executeUndoRedo_validGroup_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Group groupToDelete = model.getFilteredMemberList().get(INDEX_FOURTH_MEMBER.getZeroBased()).getGroup();
        DeleteGroupCommand deleteGroupCommand = prepareCommand(DANIEL.getGroup());
        // remove -> group removed
        deleteGroupCommand.execute();
        undoRedoStack.push(deleteGroupCommand);

        // undo -> reverts Club book back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same group deleted again
        expectedModel.deleteGroup(groupToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_nonExistentGroup_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Group nonExistentGroup = new Group(NON_EXISTENT_GROUP);
        DeleteGroupCommand deleteGroupCommand = prepareCommand(nonExistentGroup);

        // execution failed -> deleteGroupCommand not pushed onto undoRedoStack
        assertCommandFailure(deleteGroupCommand, model,
                String.format(MESSAGE_NON_EXISTENT_GROUP, nonExistentGroup));

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_mandatoryGroup_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Group mandatoryGroup = new Group(MANDATORY_GROUP);
        DeleteGroupCommand deleteGroupCommand = prepareCommand(mandatoryGroup);

        // execution failed -> deleteGroupCommand not pushed onto undoRedoStack
        assertCommandFailure(deleteGroupCommand, model,
                String.format(MESSAGE_MANDATORY_GROUP, mandatoryGroup.toString()));

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_validGroup_sameGroupDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteGroupCommand deleteGroupCommand = prepareCommand(DANIEL.getGroup());
        Group groupToDelete = model.getFilteredMemberList().get(INDEX_FOURTH_MEMBER.getZeroBased()).getGroup();
        // remove -> removes group
        deleteGroupCommand.execute();
        undoRedoStack.push(deleteGroupCommand);

        // undo -> reverts Club book back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.deleteGroup(groupToDelete);
        assertEquals(groupToDelete, model.getFilteredMemberList().get(INDEX_FOURTH_MEMBER.getZeroBased()).getGroup());
        // redo -> removes the same group
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteGroupCommand firstCommand = prepareCommand(new Group(VALID_GROUP_AMY));
        DeleteGroupCommand secondCommand = prepareCommand(new Group(VALID_GROUP_TEST));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(secondCommand.equals(secondCommand));

        // same values -> return true
        DeleteGroupCommand firstCommandCopy = prepareCommand(new Group(VALID_GROUP_AMY));
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(true));

        // null -> returns false
        assertFalse(secondCommand.equals(null));

        // different group -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteGroupCommand prepareCommand(Group group) {
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(group);
        deleteGroupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteGroupCommand;
    }
}
```
###### \java\seedu\club\logic\commands\DeleteTaskCommandTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.club.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.club.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalTasks.getTypicalClubBookWithTasks;

import org.junit.Test;

import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.task.Task;
import seedu.club.model.task.TaskIsRelatedToMemberPredicate;

public class DeleteTaskCommandTest {
    private Model model = new ModelManager(getTypicalClubBookWithTasks(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        model.logsInMember(ALICE.getCredentials().getUsername().value, ALICE.getCredentials().getPassword().value);
        model.updateFilteredTaskList(model.PREDICATE_SHOW_ALL_TASKS);
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS,
                taskToDelete.getDescription().getDescription());

        ModelManager expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        expectedModel.updateFilteredTaskList(expectedModel.PREDICATE_SHOW_ALL_TASKS);
        expectedModel.deleteTask(taskToDelete);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        model.logsInMember(ALICE.getCredentials().getUsername().value, ALICE.getCredentials().getPassword().value);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        model.logsInMember(ALICE.getCredentials().getUsername().value, ALICE.getCredentials().getPassword().value);
        model.updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(ALICE));

        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = prepareCommand(INDEX_FIRST_TASK);
        Model expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        expectedModel.updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(ALICE));

        // delete -> first task deleted
        deleteTaskCommand.execute();
        undoRedoStack.push(deleteTaskCommand);

        // undo -> reverts clubbook back to previous state and filtered task list to show all tasks
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task deleted again
        expectedModel.deleteTask(taskToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        model.logsInMember(ALICE.getCredentials().getUsername().value, ALICE.getCredentials().getPassword().value);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteTaskCommand not pushed into undoRedoStack
        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    private DeleteTaskCommand prepareCommand(Index targetIndex) {
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(targetIndex);
        deleteTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTaskCommand;
    }

    @Test
    public void equals() throws Exception {
        model.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        DeleteTaskCommand deleteTaskFirstCommand = prepareCommand(INDEX_FIRST_TASK);
        DeleteTaskCommand deleteTaskSecondCommand = prepareCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(deleteTaskFirstCommand.equals(deleteTaskFirstCommand));

        // same values -> returns true
        DeleteTaskCommand deleteTaskFirstCommandCopy = prepareCommand(INDEX_FIRST_TASK);
        assertTrue(deleteTaskFirstCommand.equals(deleteTaskFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteTaskFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteTaskFirstCommand.equals(deleteTaskFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteTaskFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteTaskFirstCommand.equals(null));

        // different member -> returns false
        assertFalse(deleteTaskFirstCommand.equals(deleteTaskSecondCommand));
    }


}
```
###### \java\seedu\club\logic\commands\email\BodyTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.club.model.email.Body;

public class BodyTest {

    @Test
    public void equals() {
        Body firstBody = new Body(Body.TEST_BODY_STRING);
        Body secondBody = new Body(Body.TEST_BODY_STRING);

        assertTrue(firstBody.equals(firstBody));
        assertTrue(firstBody.equals(secondBody));

        assertFalse(firstBody.equals(null));
    }

    @Test
    public void test_hashCode() {
        Body body = new Body(Body.TEST_BODY_STRING);
        assertEquals(body.hashCode(), Body.TEST_BODY_STRING.hashCode());
    }
}
```
###### \java\seedu\club\logic\commands\email\ClientTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.club.model.email.Client;

public class ClientTest {

    @Test
    public void equals() {
        Client firstClient = new Client(Client.VALID_CLIENT_GMAIL);
        Client secondClient = new Client(Client.VALID_CLIENT_OUTLOOK);

        assertTrue(firstClient.equals(firstClient));

        Client firstClientCopy = new Client(Client.VALID_CLIENT_GMAIL);
        assertTrue(firstClient.equals(firstClientCopy));

        assertFalse(secondClient.equals(null));
    }

    @Test
    public void test_hashCode() {
        Client client = new Client(Client.VALID_CLIENT_OUTLOOK);
        assertEquals(client.hashCode(), Client.VALID_CLIENT_OUTLOOK.hashCode());
    }

    @Test
    public void test_isValidClient_success() {
        Client gmailClient = new Client(Client.VALID_CLIENT_GMAIL);
        Client outlookClient = new Client(Client.VALID_CLIENT_OUTLOOK);

        assertTrue(Client.isValidClient(gmailClient.toString()));
        assertTrue(Client.isValidClient(outlookClient.toString()));
    }

    @Test
    public void test_isValidClient_failure() {
        Client firstInvalidClient = new Client("yahoo");
        Client secondInvalidClient = new Client("hotmail");

        assertFalse(Client.isValidClient(firstInvalidClient.toString()));
        assertFalse(Client.isValidClient(secondInvalidClient.toString()));
    }
}
```
###### \java\seedu\club\logic\commands\email\SubjectTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.club.model.email.Subject;

public class SubjectTest {

    @Test
    public void equals() {
        Subject firstSubject = new Subject(Subject.TEST_SUBJECT_STRING);
        Subject secondSubject = new Subject(Subject.TEST_SUBJECT_STRING);

        assertTrue(firstSubject.equals(firstSubject));
        assertTrue(firstSubject.equals(secondSubject));

        assertFalse(firstSubject.equals(null));
    }

    @Test
    public void test_hashCode() {
        Subject subject = new Subject(Subject.EMPTY_SUBJECT_STRING);
        assertEquals(subject.hashCode(), Subject.EMPTY_SUBJECT_STRING.hashCode());
    }
}
```
###### \java\seedu\club\logic\commands\EmailCommandTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.commons.core.Messages.MESSAGE_NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_TAG;
import static seedu.club.testutil.TypicalIndexes.INDEX_SECOND_MEMBER;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.member.Member;
import seedu.club.model.tag.Tag;


/**
 * Contains unit tests for {@code EmailCommand}.
 */
public class EmailCommandTest {

    private Client gmailClient = new Client(Client.VALID_CLIENT_GMAIL);
    private Client outlookClient = new Client(Client.VALID_CLIENT_OUTLOOK);
    private Subject testSubject = new Subject(Subject.TEST_SUBJECT_STRING);
    private Subject emptySubject = new Subject(Subject.EMPTY_SUBJECT_STRING);
    private Body testBody = new Body(Body.TEST_BODY_STRING);
    private Body emptyBody = new Body(Body.EMPTY_BODY_STRING);
    private Group groupToEmail;
    private Tag tagToEmail;
    private Model model;
    private Model expectedModel;
    private ObservableList<Member> observableList;
    private Member member;

```
###### \java\seedu\club\logic\commands\EmailCommandTest.java
``` java
    @Test
    public void execute_validCommandToEmailGroupGmail_success() throws Exception {
        groupToEmail = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased()).getGroup();
        tagToEmail = null;

        EmailCommand emailCommand = prepareCommand(groupToEmail, tagToEmail, gmailClient,
                testSubject, testBody);

        String expectedMessage = EmailCommand.EMAIL_CLIENT_OPENED;
        String expectedRecipients = expectedModel.generateEmailRecipients(groupToEmail, tagToEmail);
        expectedModel.sendEmail(expectedRecipients, gmailClient, testSubject, testBody);

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\club\logic\commands\EmailCommandTest.java
``` java
    @Test
    public void execute_validCommandToEmailTagGmail_success() throws Exception {
        groupToEmail = null;
        tagToEmail = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());

        EmailCommand emailCommand = prepareCommand(groupToEmail, tagToEmail, gmailClient,
                testSubject, testBody);

        String expectedMessage = EmailCommand.EMAIL_CLIENT_OPENED;
        String expectedRecipients = expectedModel.generateEmailRecipients(groupToEmail, tagToEmail);
        expectedModel.sendEmail(expectedRecipients, outlookClient, testSubject, testBody);

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\club\logic\commands\EmailCommandTest.java
``` java
    @Test
    public void execute_nonExistentGroup_throwCommandException() {
        Group nonExistentGroup = new Group(NON_EXISTENT_GROUP);
        tagToEmail = null;

        EmailCommand emailCommand = prepareCommand(nonExistentGroup, tagToEmail, gmailClient,
                testSubject, testBody);
        String expectedMessage = String.format(MESSAGE_NON_EXISTENT_GROUP, nonExistentGroup);

        assertCommandFailure(emailCommand, model, expectedMessage);
    }

    @Test
    public void execute_nonExistentTag_throwCommandException() {
        Tag nonExistentTag = new Tag(VALID_TAG_UNUSED);
        groupToEmail = null;

        EmailCommand emailCommand = prepareCommand(groupToEmail, nonExistentTag, outlookClient,
                testSubject, testBody);
        String expectedMessage = DeleteTagCommand.MESSAGE_NON_EXISTENT_TAG;

        assertCommandFailure(emailCommand, model, expectedMessage);
    }

```
###### \java\seedu\club\logic\commands\EmailCommandTest.java
``` java
    @Test
    public void execute_optionalSubjectAndBody_success() throws Exception {
        groupToEmail = null;
        tagToEmail = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());

        EmailCommand emailCommand = prepareCommand(groupToEmail, tagToEmail, outlookClient, emptySubject,
                emptyBody);

        String expectedMessage = EmailCommand.EMAIL_CLIENT_OPENED;
        String expectedRecipients = expectedModel.generateEmailRecipients(groupToEmail, tagToEmail);
        expectedModel.sendEmail(expectedRecipients, gmailClient, emptySubject, emptyBody);

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Group groupToEmailOne = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased()).getGroup();
        Group groupToEmailTwo = model.getFilteredMemberList().get(INDEX_SECOND_MEMBER.getZeroBased()).getGroup();
        Tag tagToEmailOne = new Tag(VALID_TAG_UNUSED);
        Tag tagToEmailTwo = new Tag(VALID_TAG_UNUSED);

        EmailCommand firstCommand = prepareCommand(groupToEmailOne, tagToEmailOne, gmailClient,
                testSubject, testBody);
        EmailCommand secondCommand = prepareCommand(groupToEmailTwo, tagToEmailTwo, gmailClient,
                emptySubject, emptyBody);

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(secondCommand.equals(secondCommand));

        EmailCommand firstCommandCopy = prepareCommand(model.getFilteredMemberList().get(INDEX_FIRST_MEMBER
                .getZeroBased()).getGroup(), tagToEmailOne, gmailClient, testSubject, emptyBody);
        assertTrue(firstCommand.equals(firstCommandCopy));

        assertFalse(secondCommand.equals(true));

        assertFalse(secondCommand.equals(null));

    }
    /**
     * Returns a {@code EmailCommand} object with the parameters {@code group}, {@code tag},
     * {@code client}, {@code subject}, and {@code body}
     */
    private EmailCommand prepareCommand(Group group, Tag tag, Client client, Subject subject, Body body) {
        EmailCommand emailCommand = new EmailCommand(group, tag, client, subject, body);
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return emailCommand;
    }
}
```
###### \java\seedu\club\logic\commands\ViewAllTasksCommandTest.java
``` java
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.testutil.TypicalMembers.DANIEL;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.commons.core.Messages;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.member.Member;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewAllTasksCommand.
 */
public class ViewAllTasksCommandTest {

    private Model model;
    private Model expectedModel;
    private ViewAllTasksCommand viewAllTasksCommand;
    private ObservableList<Member> observableList;
    private Member member;

    @Before
    public void setUp() throws CommandException {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());
        observableList = model.getClubBook().getMemberList();
        member = observableList.get(0);
        LogInCommand command = new LogInCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
        command.setData(expectedModel, new CommandHistory(), new UndoRedoStack());
        command.execute();
        viewAllTasksCommand = new ViewAllTasksCommand();
        viewAllTasksCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }


    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(viewAllTasksCommand, model, ViewAllTasksCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_taskCannotBeDisplayed_noChange() {
        model.logsInMember(DANIEL.getCredentials().getUsername().value,
                DANIEL.getCredentials().getPassword().value);
        model.updateFilteredTaskList(Model.PREDICATE_NOT_SHOW_ALL_TASKS);
        String expectedMessage = Messages.MESSAGE_REQUIRE_EXCO_LOG_IN;
        assertCommandFailure(viewAllTasksCommand, model, expectedMessage);
    }

}
```
###### \java\seedu\club\logic\commands\ViewMyTasksCommandTest.java
``` java
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalTasks.getTypicalClubBookWithTasks;

import org.junit.Before;
import org.junit.Test;

import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.task.TaskIsRelatedToMemberPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewAllTasksCommand.
 */
public class ViewMyTasksCommandTest {

    private Model model;
    private Model expectedModel;
    private ViewMyTasksCommand viewMyTasksCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalClubBookWithTasks(), new UserPrefs());
        expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        viewMyTasksCommand = new ViewMyTasksCommand();
        viewMyTasksCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsMemberTasks() {
        model.logsInMember(ALICE.getCredentials().getUsername().value, ALICE.getCredentials().getPassword().value);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        TaskIsRelatedToMemberPredicate predicateAlice = new TaskIsRelatedToMemberPredicate(ALICE);
        expectedModel.updateFilteredTaskList(predicateAlice);
        assertCommandSuccess(viewMyTasksCommand, model, ViewMyTasksCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listAlreadyFiltered_throwsException() {
        model.logsInMember(ALICE.getCredentials().getUsername().value, ALICE.getCredentials().getPassword().value);
        TaskIsRelatedToMemberPredicate predicateAlice = new TaskIsRelatedToMemberPredicate(ALICE);
        model.updateFilteredTaskList(predicateAlice);
        String expectedMessage = ViewMyTasksCommand.MESSAGE_ALREADY_LISTED;
        assertCommandFailure(viewMyTasksCommand, model, expectedMessage);
    }
}
```
###### \java\seedu\club\logic\parser\AddTaskCommandParserTest.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_DATE_DESC_PASSED;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_FOOD;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DATE_1;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_CONFETTI;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_FOOD;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_STATUS_TO_BEGIN;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_TIME_1;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.club.commons.core.Messages;
import seedu.club.logic.commands.AddTaskCommand;
import seedu.club.model.task.Task;
import seedu.club.testutil.TaskBuilder;

public class AddTaskCommandParserTest {

    private static final String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddTaskCommand.MESSAGE_USAGE);

    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder()
                .withDescription(VALID_TASK_DESCRIPTION_FOOD)
                .withDate(VALID_TASK_DATE_1)
                .withTime(VALID_TASK_TIME_1)
                .withAssignor("")
                .withAssignee("")
                .withStatus(VALID_TASK_STATUS_TO_BEGIN)
                .build();

        assertParseSuccess(parser, " " + TASK_DESCRIPTION_DESC_FOOD + TASK_DATE_DESC_1
                + TASK_TIME_DESC_1,
                new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_dateAlreadyPassed_throwsException() {
        String expectedMessage = Messages.MESSAGE_DATE_ALREADY_PASSED;
        assertParseFailure(parser, " " + TASK_DESCRIPTION_DESC_FOOD + INVALID_DATE_DESC_PASSED
                + TASK_TIME_DESC_1, expectedMessage);

    }

    @Test
    public void parse_fieldsMissing_failure() {
        assertParseFailure(parser, TASK_DESCRIPTION_DESC_FOOD + TASK_DATE_DESC_1
                + VALID_TASK_TIME_1, expectedMessage);

        assertParseFailure(parser, TASK_DATE_DESC_1 + TASK_TIME_DESC_1 + VALID_TASK_DESCRIPTION_CONFETTI,
                expectedMessage);

        assertParseFailure(parser,  TASK_TIME_DESC_1 + TASK_DESCRIPTION_DESC_FOOD + VALID_TASK_DATE_1,
                expectedMessage);

        assertParseFailure(parser, TASK_DATE_DESC_1 + VALID_TASK_DATE_1, expectedMessage);

        assertParseFailure(parser, VALID_TASK_DESCRIPTION_FOOD + VALID_TASK_DATE_1, expectedMessage);

        assertParseFailure(parser, VALID_TASK_DESCRIPTION_FOOD + VALID_TASK_TIME_1, expectedMessage);
    }
}
```
###### \java\seedu\club\logic\parser\AssignTaskCommandParserTest.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_DATE_DESC_PASSED;
import static seedu.club.logic.commands.CommandTestUtil.MATRIC_NUMBER_DESC_BOB;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_2;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_FOOD;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_2;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_BOB;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DATE_1;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_CONFETTI;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_FOOD;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_STATUS_TO_BEGIN;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_TIME_1;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.club.testutil.TypicalMembers.BOB;

import org.junit.Test;

import seedu.club.commons.core.Messages;
import seedu.club.logic.commands.AssignTaskCommand;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.task.Task;
import seedu.club.testutil.TaskBuilder;

public class AssignTaskCommandParserTest {
    private static final String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AssignTaskCommand.MESSAGE_USAGE);

    private AssignTaskCommandParser parser = new AssignTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder()
                .withDescription(VALID_TASK_DESCRIPTION_FOOD)
                .withDate(VALID_TASK_DATE_1)
                .withTime(VALID_TASK_TIME_1)
                .withAssignor("")
                .withAssignee("")
                .withStatus(VALID_TASK_STATUS_TO_BEGIN)
                .build();

        MatricNumber matricNumber = BOB.getMatricNumber();
        assertParseSuccess(parser, " " + TASK_DESCRIPTION_DESC_FOOD + TASK_DATE_DESC_1
                        + TASK_TIME_DESC_1 + MATRIC_NUMBER_DESC_BOB,
                new AssignTaskCommand(expectedTask, matricNumber));
    }

    @Test
    public void parse_dateAlreadyPassed_throwsException() {
        String expectedMessage = Messages.MESSAGE_DATE_ALREADY_PASSED;
        assertParseFailure(parser, " " + TASK_DESCRIPTION_DESC_FOOD + INVALID_DATE_DESC_PASSED
                + TASK_TIME_DESC_1 + MATRIC_NUMBER_DESC_BOB, expectedMessage);

    }

    @Test
    public void parse_fieldsMissing_failure() {
        // missing time prefix
        assertParseFailure(parser, TASK_DESCRIPTION_DESC_FOOD + TASK_DATE_DESC_1
                + VALID_TASK_TIME_1 + MATRIC_NUMBER_DESC_BOB, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, TASK_DATE_DESC_2 + TASK_TIME_DESC_2 + VALID_TASK_DESCRIPTION_CONFETTI
                + MATRIC_NUMBER_DESC_BOB,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser,  TASK_TIME_DESC_1 + TASK_DESCRIPTION_DESC_FOOD + VALID_TASK_DATE_1
                + MATRIC_NUMBER_DESC_BOB,
                expectedMessage);

        // missing matric number prefix
        assertParseFailure(parser,  TASK_TIME_DESC_1 + TASK_DESCRIPTION_DESC_FOOD + TASK_DATE_DESC_1
                + VALID_MATRIC_NUMBER_BOB,
                expectedMessage);

        /*------------------------------------MISSING PARAMETERS------------------------------------------------------*/

        assertParseFailure(parser, TASK_DATE_DESC_1 + VALID_TASK_DATE_1, expectedMessage);

        assertParseFailure(parser, VALID_TASK_DESCRIPTION_FOOD + VALID_TASK_DATE_1, expectedMessage);

        assertParseFailure(parser, VALID_TASK_DESCRIPTION_FOOD + VALID_TASK_TIME_1, expectedMessage);
    }

}
```
###### \java\seedu\club\logic\parser\ChangeAssigneeCommandParserTest.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_MATRIC_NUMBER;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_BOB;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.club.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.Test;

import seedu.club.logic.commands.ChangeAssigneeCommand;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.task.Assignee;

public class ChangeAssigneeCommandParserTest {
    private ChangeAssigneeCommandParser parser = new ChangeAssigneeCommandParser();

    @Test
    public void parse_validArgs_returnsChangeAssigneeCommand() {
        assertParseSuccess(parser, " 1 "
                        + " " + PREFIX_MATRIC_NUMBER + VALID_MATRIC_NUMBER,
                new ChangeAssigneeCommand(INDEX_FIRST_TASK, new Assignee(VALID_MATRIC_NUMBER)));
        assertParseSuccess(parser, " 2 "
                        + " " + PREFIX_MATRIC_NUMBER + VALID_MATRIC_NUMBER_BOB,
                new ChangeAssigneeCommand(INDEX_SECOND_TASK, new Assignee(VALID_MATRIC_NUMBER_BOB)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeAssigneeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " one",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeAssigneeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, " -1" + PREFIX_MATRIC_NUMBER + VALID_MATRIC_NUMBER_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeAssigneeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 0" + PREFIX_MATRIC_NUMBER + VALID_MATRIC_NUMBER_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeAssigneeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStatus_throwsParseException() {
        // invalid matric number
        assertParseFailure(parser, " 1 " + PREFIX_MATRIC_NUMBER + INVALID_MATRIC_NUMBER,
                MatricNumber.MESSAGE_MATRIC_NUMBER_CONSTRAINTS);
    }

}
```
###### \java\seedu\club\logic\parser\DeleteGroupCommandParserTest.java
``` java
public class DeleteGroupCommandParserTest {
    private DeleteGroupCommandParser parser = new DeleteGroupCommandParser();

    @Test
    public void parse_fieldPresent_success() {
        assertParseSuccess(parser, GROUP_DESC_BOB, new DeleteGroupCommand(new Group(VALID_GROUP_BOB)));
        assertParseSuccess(parser, GROUP_DESC_AMY, new DeleteGroupCommand(new Group(VALID_GROUP_AMY)));
    }

    @Test
    public void parse_incorrectField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE);
        assertParseFailure(parser, NAME_DESC_AMY, expectedMessage);
        assertParseFailure(parser, MATRIC_NUMBER_DESC_AMY, expectedMessage);
    }

    @Test
    public void parse_invalidGroupFormat_failure() {
        String expectedMessage = Group.MESSAGE_GROUP_CONSTRAINTS;
        assertParseFailure(parser, INVALID_GROUP_DESC, Group.MESSAGE_GROUP_CONSTRAINTS);
        assertParseFailure(parser, " " + PREFIX_GROUP.toString() + " ", expectedMessage);
    }

    @Test
    public void parse_fieldNotPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE);

        // blank space
        assertParseFailure(parser, " ", expectedMessage);
        // newline character
        assertParseFailure(parser, "\n", expectedMessage);
        // group should be preceded by group prefix 'g/'
        assertParseFailure(parser, VALID_GROUP_AMY, expectedMessage);
        assertParseFailure(parser, VALID_GROUP_BOB, expectedMessage);
    }
}
```
###### \java\seedu\club\logic\parser\DeleteTaskCommandParserTest.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.club.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.Test;

import seedu.club.logic.commands.DeleteTaskCommand;

public class DeleteTaskCommandParserTest {
    private DeleteTaskCommandParser parser = new DeleteTaskCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteTaskCommand() {
        assertParseSuccess(parser, "1", new DeleteTaskCommand(INDEX_FIRST_TASK));
        assertParseSuccess(parser, "2", new DeleteTaskCommand(INDEX_SECOND_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " one",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\club\logic\parser\EmailCommandParserTest.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.commands.CommandTestUtil.GROUP_DESC_AMY;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_GROUP_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.club.logic.commands.CommandTestUtil.MATRIC_NUMBER_DESC_AMY;
import static seedu.club.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.club.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.club.logic.commands.CommandTestUtil.VALID_CLIENT;
import static seedu.club.logic.commands.CommandTestUtil.VALID_CLIENT_DESC;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_HEAD;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.club.logic.commands.EmailCommand;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.tag.Tag;

public class EmailCommandParserTest {
    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parse_fieldNotPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.COMMAND_USAGE);

        //blank space
        assertParseFailure(parser, " ", expectedMessage);

        //newline character
        assertParseFailure(parser, "\n", expectedMessage);

        //group should be preceded with 'g/' prefix
        assertParseFailure(parser, VALID_GROUP_AMY, expectedMessage);
        assertParseFailure(parser, VALID_GROUP_BOB, expectedMessage);

        //tag should be preceded with 't/' prefix
        assertParseFailure(parser, VALID_TAG_HEAD, expectedMessage);
    }

    @Test
    public void parse_incorrectField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.COMMAND_USAGE);
        assertParseFailure(parser, NAME_DESC_AMY, expectedMessage);
        assertParseFailure(parser, MATRIC_NUMBER_DESC_AMY, expectedMessage);
    }

    @Test
    public void parse_invalidGroupFormat_failure() {
        String expectedMessage = Group.MESSAGE_GROUP_CONSTRAINTS;
        String invalidCommandOne = INVALID_GROUP_DESC + VALID_CLIENT_DESC;
        String invalidCommandTwo = " " + PREFIX_GROUP.toString() + " " + VALID_CLIENT_DESC;
        assertParseFailure(parser, invalidCommandOne, expectedMessage);
        assertParseFailure(parser, invalidCommandTwo, expectedMessage);
    }

    @Test
    public void parse_invalidTagFormat_failure() {
        String expectedMessage = Tag.MESSAGE_TAG_CONSTRAINTS;
        String invalidCommandOne = INVALID_TAG_DESC + VALID_CLIENT_DESC;
        String invalidCommandTwo = " " + PREFIX_TAG.toString() + " " + VALID_CLIENT_DESC;
        assertParseFailure(parser, invalidCommandOne, expectedMessage);
        assertParseFailure(parser, invalidCommandTwo, expectedMessage);
    }

    @Test
    public void parse_bothGroupAndTagPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.COMMAND_USAGE);
        String invalidCommand = GROUP_DESC_AMY + TAG_DESC_FRIEND;
        assertParseFailure(parser, invalidCommand, expectedMessage);
    }

    @Test
    public void parse_allFieldPresent_success() {
        String command = VALID_CLIENT_DESC + GROUP_DESC_AMY;
        Tag tagToEmail = null;
        assertParseSuccess(parser, command, new EmailCommand(new Group(VALID_GROUP_AMY),
                tagToEmail, new Client(VALID_CLIENT), new Subject(Subject.TEST_SUBJECT_STRING),
                new Body(Body.TEST_BODY_STRING)));
    }

    @Test
    public void parse_clientMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.COMMAND_USAGE);
        String invalidCommand = GROUP_DESC_AMY;
        assertParseFailure(parser, invalidCommand, expectedMessage);
    }

    @Test
    public void parse_emptyCommand_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.COMMAND_USAGE);
        String invalidCommand = " ";
        assertParseFailure(parser, invalidCommand, expectedMessage);
    }
}
```
###### \java\seedu\club\model\ClubBookTest.java
``` java
    @Test
    public void removeGroup_nonExistentGroup_unchangedClubBook() throws Exception {
        try {
            clubBookWithBobAndAmy.deleteGroup(new Group(NON_EXISTENT_GROUP));
        } catch (GroupNotFoundException gnfe) {
            ClubBook expectedClubBook = new ClubBookBuilder().withMember(BOB).withMember(AMY).build();
            assertEquals(expectedClubBook, clubBookWithBobAndAmy);
        }
    }

    @Test
    public void removeGroup_mandatoryGroup_unchangedClubBook() throws Exception {
        try {
            clubBookWithBobAndAmy.deleteGroup(new Group(MANDATORY_GROUP));
        } catch (GroupCannotBeRemovedException e) {
            ClubBook expectedClubBook = new ClubBookBuilder().withMember(BOB).withMember(AMY).build();
            assertEquals(expectedClubBook, clubBookWithBobAndAmy);
        }
    }

    @Test
    public void removeGroup_atLeastOneMemberInGroup_groupRemoved() throws Exception {
        ClubBook clubBookWithDanielAndElle = new ClubBookBuilder().withMember(DANIEL).withMember(ELLE)
                .build();
        clubBookWithDanielAndElle.deleteGroup(new Group(DANIEL.getGroup().toString()));

        Member bensonNotInPr = new MemberBuilder(DANIEL).withGroup().build();
        Member aliceNotInPr = new MemberBuilder(ELLE).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(bensonNotInPr)
                .withMember(aliceNotInPr).build();
        assertEquals(expectedClubBook, clubBookWithDanielAndElle);
    }

    @Test
    public void deleteMember_validMemberWithTasks_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).withTask(BOOK_AUDITORIUM)
                .withTask(BUY_CONFETTI).build();
        clubBook.removeMember(BENSON);
        clubBook.removeTasksOfMember(BENSON);

        Member alice = new MemberBuilder(ALICE).build();
        Task buyConfetti = new TaskBuilder(BUY_CONFETTI).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(alice).withTask(buyConfetti).build();

        assertEquals(expectedClubBook, clubBook);
    }

    @Test
    public void deleteTask_validTask_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();
        clubBook.deleteTask(BUY_CONFETTI);

        Member amy = new MemberBuilder(AMY).build();
        Task buyFood = new TaskBuilder(BUY_FOOD).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(amy).withTask(buyFood).build();

        assertEquals(expectedClubBook, clubBook);
    }

    @Test
    public void deleteTask_taskNotFound_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();
        try {
            clubBook.deleteTask(BOOK_AUDITORIUM);
        } catch (TaskNotFoundException tnfe) {
            Member amy = new MemberBuilder(AMY).build();
            Task buyFood = new TaskBuilder(BUY_FOOD).build();
            Task buyConfetti = new TaskBuilder(BUY_CONFETTI).build();
            ClubBook expectedClubBook = new ClubBookBuilder()
                    .withMember(amy)
                    .withTask(buyFood)
                    .withTask(buyConfetti)
                    .build();
            assertEquals(expectedClubBook, clubBook);
        }
    }

    @Test
    public void addTask_validTask_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY)
                .withTask(BUY_FOOD).build();
        clubBook.addTaskToTaskList(BUY_CONFETTI);

        Member amy = new MemberBuilder(AMY).build();
        Task buyFood = new TaskBuilder(BUY_FOOD).build();
        Task buyConfetti = new TaskBuilder(BUY_CONFETTI).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(amy).withTask(buyFood)
                .withTask(buyConfetti).build();

        assertEquals(expectedClubBook, clubBook);

    }

```
###### \java\seedu\club\model\ClubBookTest.java
``` java
    @Test
    public void updateTask_validTask_success() {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY)
                .withTask(BUY_CONFETTI).withTask(BUY_FOOD).build();

        Member amy = new MemberBuilder(AMY).build();
        Task buyFood = new TaskBuilder()
                .withDescription(BUY_FOOD.getDescription().getDescription())
                .withAssignor(BUY_FOOD.getAssignor().getValue())
                .withAssignee(BUY_FOOD.getAssignee().getValue())
                .withDate(BUY_FOOD.getDate().getDate())
                .withTime(BUY_FOOD.getTime().getTime())
                .withStatus(Status.IN_PROGRESS_STATUS)
                .build();
        Task buyConfetti = new TaskBuilder(BUY_CONFETTI).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(amy).withTask(buyFood)
                .withTask(buyConfetti).build();

        Task taskToEdit = new Task(BUY_FOOD);
        Task editedTask = new Task(taskToEdit);
        editedTask.setStatus(new Status(Status.IN_PROGRESS_STATUS));

        try {
            clubBook.updateTaskStatus(taskToEdit, editedTask);
        } catch (DuplicateTaskException | TaskNotFoundException e) {
            fail("This will not be executed");
        }

        assertEquals(expectedClubBook, clubBook);
    }

    @Test
    public void updateTask_duplicateTask_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY)
                .withTask(BUY_CONFETTI).withTask(BUY_FOOD).build();

        Member amy = new MemberBuilder(AMY).build();
        Task buyFood = new TaskBuilder(BUY_FOOD).build();
        Task buyConfetti = new TaskBuilder(BUY_CONFETTI).build();

        ClubBook expectedClubBook = new ClubBookBuilder().withMember(amy).withTask(buyFood)
                .withTask(buyConfetti).build();

        Task taskToEdit = new Task(BUY_FOOD);
        Task editedTask = new Task(BUY_FOOD);

        try {
            clubBook.updateTaskStatus(taskToEdit, editedTask);
        } catch (DuplicateTaskException dte) {
            assertEquals(expectedClubBook, clubBook);
        } catch (TaskNotFoundException tnfe) {
            fail("This will not be executed");
        }
    }

    @Test
    public void updateTaskAssignee_validAssignee_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE)
                .withMember(BENSON)
                .withTask(BUY_CONFETTI).withTask(BUY_FOOD).build();

        Task taskToEdit = BUY_FOOD;
        Task editedTask = new TaskBuilder(BUY_FOOD).build();
        editedTask.setAssignee(new Assignee(BENSON.getMatricNumber().toString()));

        ClubBook expectedClubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON)
                .withTask(editedTask).withTask(BUY_CONFETTI).build();

        clubBook.updateTaskAssignee(taskToEdit, editedTask);
        assertEquals(expectedClubBook, clubBook);
    }
```
###### \java\seedu\club\model\group\GroupTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_TEST;

import org.apache.commons.lang3.text.WordUtils;
import org.junit.Test;

public class GroupTest {

    @Test
    public void isValidGroup() {
        // empty group --> returns false
        assertFalse(Group.isValidGroup(" "));

        // null group --> returns false
        assertFalse(Group.isValidGroup(""));

        // string is not alphanumeric --> returns false
        assertFalse(Group.isValidGroup("123@#$"));
        assertFalse(Group.isValidGroup("=-0987"));
        assertFalse(Group.isValidGroup("publicity_main"));

        // string contains space-separated words --> returns false
        assertFalse(Group.isValidGroup("public relations"));
        assertFalse(Group.isValidGroup("executive committee"));
        assertFalse(Group.isValidGroup("logistics member"));

        // valid group names --> returns true
        assertTrue(Group.isValidGroup("logistics"));
        assertTrue(Group.isValidGroup("publicity"));
        assertTrue(Group.isValidGroup("marketing"));
        assertTrue(Group.isValidGroup("operations"));
    }

    @Test
    public void test_toString() {
        Group testGroupOne = new Group(VALID_GROUP_AMY);
        Group testGroupTwo = new Group(VALID_GROUP_BOB);

        assertTrue(testGroupOne.toString().equals(VALID_GROUP_AMY)); //already capitalised
        assertTrue(testGroupTwo.toString().equals(WordUtils.capitalize(VALID_GROUP_BOB)));
        assertFalse(testGroupOne.toString().equals(VALID_GROUP_BOB));
        assertFalse(testGroupTwo.toString().equals(WordUtils.capitalize(VALID_GROUP_AMY.toLowerCase())));
    }

    @Test
    public void test_hashCode() {
        Group group = new Group(VALID_GROUP_AMY);
        String groupName = VALID_GROUP_AMY;
        assertEquals(groupName.hashCode(), group.hashCode());
    }

    @Test
    public void test_equals() {
        Group firstGroup = new Group(VALID_GROUP_AMY);
        Group secondGroup = new Group(VALID_GROUP_TEST);

        assertTrue(firstGroup.equals(firstGroup));
        assertFalse(firstGroup.equals(secondGroup));

        assertFalse(firstGroup.equals(null));
    }
}
```
###### \java\seedu\club\model\ModelManagerTest.java
``` java
    @Test
    public void removeGroup_nonExistentGroup_modelUnchanged() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        try {
            modelManager.deleteGroup(new Group(NON_EXISTENT_GROUP));
        } catch (GroupNotFoundException gnfe) {
            assertEquals(new ModelManager(clubBook, userPrefs), modelManager);
        }
    }

    @Test
    public void removeGroup_mandatoryGroup_modelUnchanged() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        try {
            modelManager.deleteGroup(new Group(MANDATORY_GROUP));
        } catch (GroupCannotBeRemovedException e) {
            assertEquals(new ModelManager(clubBook, userPrefs), modelManager);
        }
    }

    @Test
    public void deleteGroup_atLeastOneMemberInGroup_groupDeleted() throws Exception {

        ClubBook clubBookWithDanielAndElle = new ClubBookBuilder().withMember(DANIEL).withMember(ELLE)
                .build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBookWithDanielAndElle, userPrefs);
        clubBookWithDanielAndElle.deleteGroup(new Group(DANIEL.getGroup().toString()));

        Member bensonNotInPr = new MemberBuilder(DANIEL).withGroup().build();
        Member aliceNotInPr = new MemberBuilder(ELLE).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(aliceNotInPr)
                .withMember(bensonNotInPr).build();

        assertEquals(new ModelManager(expectedClubBook, userPrefs), modelManager);

    }

    @Test
    public void emailGroup_nonExistentGroup_throwsException() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        try {
            String expectedRecipients = modelManager.generateEmailRecipients(new Group(NON_EXISTENT_GROUP),
                    null);
        } catch (GroupNotFoundException gnfe) {
            assertEquals(new ModelManager(clubBook, userPrefs), modelManager);
        }
    }

    @Test
    public void emailTag_nonExistentTag_throwsException() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        try {
            String expectedRecipients = modelManager.generateEmailRecipients(null,
                    new Tag(VALID_TAG_UNUSED));
        } catch (TagNotFoundException tnfe) {
            assertEquals(new ModelManager(clubBook, userPrefs), modelManager);
        }
    }

    @Test
    public void emailGroup_validGroup_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        String expectedRecipients = modelManager.generateEmailRecipients(new Group(VALID_GROUP_AMY),
                null);
        modelManager.sendEmail(expectedRecipients, new Client(Client.VALID_CLIENT_GMAIL),
                new Subject(Subject.TEST_SUBJECT_STRING), new Body(Body.TEST_BODY_STRING));

        assertEquals(new ModelManager(clubBook, userPrefs), modelManager);
    }

    @Test
    public void emailTag_validTag_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        String expectedRecipients = modelManager.generateEmailRecipients(null,
                new Tag(VALID_TAG_HEAD));
        modelManager.sendEmail(expectedRecipients, new Client(Client.VALID_CLIENT_GMAIL),
                new Subject(Subject.TEST_SUBJECT_STRING), new Body(Body.TEST_BODY_STRING));

        assertEquals(new ModelManager(clubBook, userPrefs), modelManager);
    }

    @Test
    public void addTask_validTask_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        modelManager.addTaskToTaskList(BUY_FOOD);

        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        assertEquals(expectedModel, modelManager);
    }

    @Test
    public void addTask_duplicateTask_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        try {
            modelManager.addTaskToTaskList(BUY_CONFETTI);
        } catch (DuplicateTaskException dte) {
            assertEquals(expectedModel, modelManager);
        }
    }

    @Test
    public void assignTask_validTask_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BOB).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        modelManager.assignTask(BUY_FOOD, BOB.getMatricNumber());


        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        boolean isEqual = expectedModel.equals(modelManager);
        assertEquals(expectedModel, modelManager);
    }

    @Test
    public void assignTask_duplicateTask_throwsException() {
        Task buyFood = new TaskBuilder()
                .withDescription("Buy Food")
                .withDate("02/05/2018")
                .withTime("19:00")
                .withAssignor("Alice Pauline")
                .withAssignee("Bob Choo")
                .withStatus("Yet To Begin")
                .build();
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).withTask(buyFood).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        try {
            modelManager.assignTask(BUY_FOOD, BOB.getMatricNumber());
        } catch (DuplicateTaskException dte) {
            assertEquals(expectedModel, modelManager);
        } catch (MemberNotFoundException | TaskAlreadyAssignedException e) {
            fail("This exception should not be caught");
        }
    }

    @Test
    public void assignTask_memberNotFound_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withTask(BUY_FOOD).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        try {
            modelManager.assignTask(BUY_CONFETTI, BOB.getMatricNumber());
        } catch (DuplicateTaskException | TaskAlreadyAssignedException e) {
            fail("This exception should not be caught");
        } catch (MemberNotFoundException mnfe) {
            assertEquals(expectedModel, modelManager);
        }
    }

    @Test
    public void assignTask_invalidPermission_throwsExcpetion() {
        ClubBook clubBook = new ClubBookBuilder().withMember(BOB).withTask(BUY_FOOD).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(BOB.getCredentials().getUsername().value, BOB.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(BOB.getCredentials().getUsername().value, BOB.getCredentials().getPassword().value);

        try {
            modelManager.assignTask(BUY_CONFETTI, BOB.getMatricNumber());
        } catch (DuplicateTaskException | MemberNotFoundException | TaskAlreadyAssignedException e) {
            fail("This exception should not be caught");
        }
    }

    @Test
    public void deleteTask_invalidTask_throwsException() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withTask(BUY_CONFETTI).withTask(BUY_FOOD).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        try {
            modelManager.deleteTask(BOOK_AUDITORIUM);
        } catch (TaskNotFoundException | TaskCannotBeDeletedException e) {
            assertEquals(expectedModel, modelManager);
        }
    }

    @Test
    public void changeTaskStatus_validTask_success() {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        Task taskToEdit = new Task(BUY_FOOD);
        Task editedTask = new Task(taskToEdit);
        editedTask.setStatus(new Status(Status.IN_PROGRESS_STATUS));

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(ALICE).withTask(editedTask).withTask(BUY_CONFETTI)
                .build();
        ModelManager expectedModel = new ModelManager(expectedClubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        try {
            modelManager.changeStatus(taskToEdit, editedTask);
        } catch (TaskNotFoundException | DuplicateTaskException | TaskStatusCannotBeEditedException e) {
            assertEquals(expectedModel, modelManager);
        }

        assertEquals(expectedModel, modelManager);
    }

    @Test
    public void changeTaskStatus_noChangeToStatus_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        Task taskToEdit = new Task(BUY_FOOD);
        Task editedTask = new Task(taskToEdit);

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        try {
            modelManager.changeStatus(taskToEdit, editedTask);
        } catch (DuplicateTaskException | TaskNotFoundException | TaskStatusCannotBeEditedException e) {
            assertEquals(expectedModel, modelManager);
        }
    }

    @Test
    public void changeTaskStatus_invalidPermission_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        Task taskToEdit = new Task(BUY_FOOD);
        Task editedTask = new Task(taskToEdit);
        editedTask.setStatus(new Status(Status.IN_PROGRESS_STATUS));

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);

        try {
            modelManager.changeStatus(taskToEdit, editedTask);
        } catch (TaskStatusCannotBeEditedException e) {
            assertEquals(expectedModel, modelManager);
        } catch (TaskNotFoundException | DuplicateTaskException e) {
            fail("This will not execute");
        }
    }

    @Test
    public void changeAssignee_validAssignee_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE)
                .withMember(BENSON)
                .withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        Task taskToEdit = new Task(BUY_FOOD);
        Task editedTask = new Task(taskToEdit);
        editedTask.setAssignee(new Assignee(BENSON.getMatricNumber().toString()));

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        modelManager.changeAssignee(taskToEdit, editedTask);

        assertEquals(expectedModel, modelManager);
    }

    @Test
    public void changeAssignee_invalidMember_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE)
                .withMember(BENSON)
                .withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        Task taskToEdit = new Task(BUY_FOOD);
        Task editedTask = new Task(taskToEdit);
        editedTask.setAssignee(new Assignee(CARL.getMatricNumber().toString()));

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        try {
            modelManager.changeAssignee(taskToEdit, editedTask);
        } catch (MemberNotFoundException mnfe) {
            assertEquals(expectedModel, modelManager);
        } catch (TaskAlreadyAssignedException | DuplicateTaskException | TaskAssigneeUnchangedException e) {
            return;
        }
    }

    @Test
    public void changeAssignee_unchangedAssignee_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE)
                .withMember(BENSON)
                .withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        Task taskToEdit = new Task(BUY_FOOD);
        Task editedTask = new Task(taskToEdit);

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        try {
            modelManager.changeAssignee(taskToEdit, editedTask);
        } catch (TaskAssigneeUnchangedException e) {
            assertEquals(expectedModel, modelManager);
        } catch (TaskAlreadyAssignedException | DuplicateTaskException | MemberNotFoundException e) {
            return;
        }
    }

    @Test
    public void changeAssignee_taskAlreadyAssigned_throwsException() {
        Task buyFoodModified = new TaskBuilder(BUY_FOOD).build();
        buyFoodModified.setAssignor(new Assignor(BENSON.getMatricNumber().toString()));
        buyFoodModified.setAssignee(new Assignee(BENSON.getMatricNumber().toString()));
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE)
                .withMember(BENSON)
                .withTask(BUY_FOOD).withTask(buyFoodModified).build();
        UserPrefs userPrefs = new UserPrefs();

        Task taskToEdit = new Task(BUY_FOOD);
        Task editedTask = new Task(taskToEdit);
        editedTask.setAssignee(new Assignee(BENSON.getMatricNumber().toString()));

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        try {
            modelManager.changeAssignee(taskToEdit, editedTask);
        } catch (TaskAlreadyAssignedException e) {
            assertEquals(expectedModel, modelManager);
        } catch (MemberNotFoundException | DuplicateTaskException | TaskAssigneeUnchangedException e) {
            return;
        }
    }

    @Test
    public void viewAllTasks_validPermission_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).withTask(BUY_FOOD)
                .withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        modelManager.viewAllTasks();
        assertEquals(expectedModel, modelManager);
    }

    @Test
    public void viewAllTasks_invalidPermission_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON)
                .withTask(BUY_CONFETTI).withTask(BUY_FOOD).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(BENSON.getCredentials().getUsername().value,
                BENSON.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(BENSON.getCredentials().getUsername().value,
                BENSON.getCredentials().getPassword().value);
        try {
            modelManager.viewAllTasks();
        } catch (TasksAlreadyListedException e) {
            assertEquals(expectedModel, modelManager);
        }
    }

    @Test
    public void viewMyTasks_validSwitch_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).withTask(BUY_FOOD)
                .withTask(BOOK_AUDITORIUM).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        expectedModel.updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(ALICE));
        modelManager.viewAllTasks();
        modelManager.viewMyTasks();
        assertEquals(expectedModel, modelManager);
    }

    @Test
    public void viewMyTasks_tasksAlreadyListed_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).withTask(BUY_FOOD)
                .withTask(BOOK_AUDITORIUM).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        modelManager.updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(ALICE));

        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        expectedModel.updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(ALICE));

        String expectedMessage = ViewMyTasksCommand.MESSAGE_ALREADY_LISTED;
        Assert.assertThrows(TasksAlreadyListedException.class, modelManager::viewMyTasks);
        Assert.assertThrows(TasksAlreadyListedException.class, expectedMessage, modelManager::viewMyTasks);
    }

    @Test
    public void deleteMember_validMemberWithTasks_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).withTask(BOOK_AUDITORIUM)
                .withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        modelManager.deleteMember(BENSON);

        ClubBook expectedClubBook = new ClubBookBuilder().withMember(ALICE).withTask(BUY_CONFETTI).build();
        ModelManager expectedModel = new ModelManager(expectedClubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        assertEquals(expectedModel, modelManager);
    }

```
###### \java\seedu\club\model\task\AssigneeTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import org.junit.Test;

public class AssigneeTest {
    @Test
    public void equals() {

        Assignee firstAssignee = new Assignee(VALID_NAME_AMY);
        Assignee secondAssignee = new Assignee(VALID_NAME_BOB);
        assertTrue(firstAssignee.equals(firstAssignee));

        assertFalse(firstAssignee.equals(null));
        assertFalse(firstAssignee.equals(true));

        assertFalse(firstAssignee.equals(secondAssignee));

        Assignee firstAssigneeCopy = new Assignee(VALID_NAME_AMY);
        assertTrue(firstAssignee.equals(firstAssigneeCopy));
    }

    @Test
    public void test_hashCode() {
        Assignee assignee = new Assignee(VALID_NAME_BOB);
        String expectedAssignee = VALID_NAME_BOB;
        assertEquals(expectedAssignee.hashCode(), assignee.hashCode());
    }

}
```
###### \java\seedu\club\model\task\AssignorTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import org.junit.Test;

public class AssignorTest {

    @Test
    public void equals() {

        Assignor firstAssignor = new Assignor(VALID_NAME_AMY);
        Assignor secondAssignor = new Assignor(VALID_NAME_BOB);
        assertTrue(firstAssignor.equals(firstAssignor));

        assertFalse(firstAssignor.equals(null));
        assertFalse(firstAssignor.equals(true));

        assertFalse(firstAssignor.equals(secondAssignor));

        Assignor firstAssignorCopy = new Assignor(VALID_NAME_AMY);
        assertTrue(firstAssignor.equals(firstAssignorCopy));
    }

    @Test
    public void test_hashCode() {
        Assignor assignor = new Assignor(VALID_NAME_BOB);
        String expectedAssignor = VALID_NAME_BOB;
        assertEquals(expectedAssignor.hashCode(), assignor.hashCode());
    }

}
```
###### \java\seedu\club\model\task\DateTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DATE_1;

import org.junit.Test;

public class DateTest {
    @Test
    public void isValidDate() {
        // day out-of-bounds
        assertFalse(Date.isValidDate("0/01/2018"));
        assertFalse(Date.isValidDate("31/04/2018"));

        // month out-of-bounds
        assertFalse(Date.isValidDate("11/13/2018"));
        assertFalse(Date.isValidDate("11/00/2018"));

        // year out-of-bounds -> returns false
        assertFalse(Date.isValidDate("01/12/2100"));
        assertFalse(Date.isValidDate("01/12/1899"));

        // invalid format
        assertFalse(Date.isValidDate("21 March 2018"));

        // invalid input
        assertFalse(Date.isValidDate("Random string"));

        // invalid leap day
        assertFalse(Date.isValidDate("29/02/2018"));

        // valid leap day
        assertTrue(Date.isValidDate("29/02/2020"));

        // valid dates
        assertTrue(Date.isValidDate("01/01/2018"));
        assertTrue(Date.isValidDate("01.01.2018"));
        assertTrue(Date.isValidDate("01-01-2019"));

        // valid dates with mixed separators
        assertTrue(Date.isValidDate("01/01.2018"));
        assertTrue(Date.isValidDate("01.01-2018"));
        assertTrue(Date.isValidDate("01-01/2019"));
    }

    @Test
    public void equals() {

        Date firstDate = new Date("01/01/2018");
        Date secondDate = new Date("02/01/2018");
        assertTrue(firstDate.equals(firstDate));

        assertFalse(firstDate.equals(null));
        assertFalse(firstDate.equals(true));

        assertFalse(firstDate.equals(secondDate));

        // same value -> returns true
        Date firstDateCopy = new Date("01/01/2018");
        assertTrue(firstDate.equals(firstDateCopy));
    }

    @Test
    public void test_hashCode() {
        Date date = new Date(VALID_TASK_DATE_1);
        String expectedDate = VALID_TASK_DATE_1;
        assertEquals(expectedDate.hashCode(), date.hashCode());
    }
}
```
###### \java\seedu\club\model\task\DescriptionTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_CONFETTI;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_FOOD;

import org.junit.Test;

import seedu.club.testutil.Assert;

/**
 * Unit tests for Description
 */
public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidDescription = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid name
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only
        assertFalse(Description.isValidDescription("^")); // only non-alphanumeric characters
        assertFalse(Description.isValidDescription("Buy*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Description.isValidDescription("food")); // alphabets only
        assertTrue(Description.isValidDescription("12345")); // numbers only
        assertTrue(Description.isValidDescription("lorem ipsum the 2nd")); // alphanumeric characters
        assertTrue(Description.isValidDescription("Buy Food")); // with capital letters
        assertTrue(Description.isValidDescription("Very long description indeed")); // long descriptions
    }

    @Test
    public void test_hashCode() {
        Description description = new Description(VALID_TASK_DESCRIPTION_FOOD);
        String expectedDescription = VALID_TASK_DESCRIPTION_FOOD;

        assertEquals(expectedDescription.hashCode(), description.hashCode());
    }

    @Test
    public void test_equals() {
        Description descriptionOne = new Description(VALID_TASK_DESCRIPTION_FOOD);
        Description descriptionTwo = new Description(VALID_TASK_DESCRIPTION_CONFETTI);

        assertTrue(descriptionOne.equals(descriptionOne));
        assertFalse(descriptionOne.equals(descriptionTwo));

        assertFalse(descriptionOne.equals(null));
    }
}
```
###### \java\seedu\club\model\task\StatusTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_STATUS_IN_PROGRESS;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_STATUS_TO_BEGIN;

import org.junit.Test;

public class StatusTest {

    @Test
    public void equals() {

        Status firstStatus = new Status(VALID_TASK_STATUS_TO_BEGIN);
        Status secondStatus = new Status(VALID_TASK_STATUS_IN_PROGRESS);
        assertTrue(firstStatus.equals(firstStatus));

        assertFalse(firstStatus.equals(null));
        assertFalse(firstStatus.equals(true));

        assertFalse(firstStatus.equals(secondStatus));

        Status firstStatusCopy = new Status(VALID_TASK_STATUS_TO_BEGIN);
        assertTrue(firstStatus.equals(firstStatusCopy));
    }

    @Test
    public void test_hashCode() {
        Status status = new Status(VALID_TASK_STATUS_IN_PROGRESS);
        String expectedStatus = VALID_TASK_STATUS_IN_PROGRESS;
        assertEquals(expectedStatus.hashCode(), status.hashCode());
    }
}
```
###### \java\seedu\club\model\task\TimeTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_TIME_1;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_TIME_2;

import org.junit.Test;

public class TimeTest {
    @Test
    public void isValidTime() {
        // invalid time
        assertFalse(Time.isValidTime("00:90"));
        assertFalse(Time.isValidTime("24:00"));

        // incorrect time input format
        assertFalse(Time.isValidTime("10 PM"));
        assertFalse(Time.isValidTime("0800"));
        assertFalse(Time.isValidTime("Eight o'clock"));

        // invalid input
        assertFalse(Time.isValidTime("Random string"));

        // valid time
        assertTrue(Time.isValidTime("00:00"));
        assertTrue(Time.isValidTime("11:30"));
        assertTrue(Time.isValidTime("15:00"));
        assertTrue(Time.isValidTime("17:00"));
        assertTrue(Time.isValidTime("23:59"));
    }

    @Test
    public void equals() {

        Time firstTime = new Time(VALID_TASK_TIME_1);
        Time secondTime = new Time(VALID_TASK_TIME_2);
        assertTrue(firstTime.equals(firstTime));

        assertFalse(firstTime.equals(null));
        assertFalse(firstTime.equals(true));

        assertFalse(firstTime.equals(secondTime));

        Time firstTimeCopy = new Time(VALID_TASK_TIME_1);
        assertTrue(firstTime.equals(firstTimeCopy));
    }

    @Test
    public void test_hashCode() {
        Time time = new Time(VALID_TASK_TIME_1);
        String expectedTime = VALID_TASK_TIME_1;
        assertEquals(expectedTime.hashCode(), time.hashCode());
    }
}
```
###### \java\seedu\club\model\task\UniqueTaskListTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.testutil.TypicalTasks.BUY_CONFETTI;
import static seedu.club.testutil.TypicalTasks.BUY_FOOD;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.club.model.task.exceptions.DuplicateTaskException;

public class UniqueTaskListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTaskList.asObservableList().remove(0);
    }

    @Test
    public void equals() throws DuplicateTaskException {
        UniqueTaskList firstTaskList = new UniqueTaskList();
        firstTaskList.add(BUY_FOOD);
        UniqueTaskList secondTaskList = new UniqueTaskList();
        secondTaskList.add(BUY_CONFETTI);

        assertTrue(firstTaskList.equals(firstTaskList));
        assertFalse(firstTaskList.equals(true));
        assertFalse(firstTaskList.equals(secondTaskList));
    }

    @Test
    public void asTaskInsensitiveList_compareSimilarLists_success()
            throws DuplicateTaskException {
        UniqueTaskList firstTaskList = new UniqueTaskList();
        firstTaskList.add(BUY_CONFETTI);
        firstTaskList.add(BUY_FOOD);
        UniqueTaskList secondTaskList = new UniqueTaskList();
        secondTaskList.add(BUY_FOOD);
        secondTaskList.add(BUY_CONFETTI);

        assertTrue(firstTaskList.equalsOrderInsensitive(secondTaskList));
    }

    @Test
    public void asUniqueList_addDuplicateOrder_throwsDuplicateOrderException()
            throws DuplicateTaskException {
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        thrown.expect(DuplicateTaskException.class);
        uniqueTaskList.add(BUY_CONFETTI);
        uniqueTaskList.add(BUY_CONFETTI);
    }

}
```
###### \java\seedu\club\storage\XmlAdaptedTaskTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_ASSIGNEE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_ASSIGNOR;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_STATUS_TO_BEGIN;
import static seedu.club.storage.XmlAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.club.testutil.TypicalTasks.BUY_CONFETTI;
import static seedu.club.testutil.TypicalTasks.BUY_FOOD;

import org.junit.Test;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.task.Assignee;
import seedu.club.model.task.Assignor;
import seedu.club.model.task.Date;
import seedu.club.model.task.Description;
import seedu.club.model.task.Status;
import seedu.club.model.task.Time;
import seedu.club.testutil.Assert;

public class XmlAdaptedTaskTest {
    private static final String INVALID_TASK_DESCRIPTION = "Buy *";
    private static final String INVALID_DATE = "02/13/2009";
    private static final String INVALID_TIME = "24:00";

    private static final String VALID_TASK_DESCRIPTION = BUY_CONFETTI.getDescription().getDescription();
    private static final String VALID_TASK_DATE = BUY_CONFETTI.getDate().getDate();
    private static final String VALID_TASK_TIME = BUY_CONFETTI.getTime().getTime();

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        XmlAdaptedTask xmlAdaptedTask = new XmlAdaptedTask(BUY_CONFETTI);
        assertEquals(BUY_CONFETTI, xmlAdaptedTask.toModelType());
    }

    @Test
    public void toModelType_invalidTaskInformation_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(INVALID_TASK_DESCRIPTION, VALID_TASK_TIME,
                VALID_TASK_DATE);
        String expectedMessage = Description.MESSAGE_DESCRIPTION_CONSTRAINTS;
        Assert.assertThrows(IllegalArgumentException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullTaskInformation_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(null, VALID_TASK_TIME,
                VALID_TASK_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTaskDate_throwsIllegalValueException() {
        XmlAdaptedTask xmlAdaptedTask = new XmlAdaptedTask(VALID_TASK_DESCRIPTION, VALID_TASK_TIME,
                INVALID_DATE);
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, xmlAdaptedTask::toModelType);
    }

    @Test
    public void toModelType_nullTaskDate_throwsIllegalValueException() {
        XmlAdaptedTask xmlAdaptedTask = new XmlAdaptedTask(VALID_TASK_DESCRIPTION, VALID_TASK_TIME,
                null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, xmlAdaptedTask::toModelType);
    }

    @Test
    public void toModelType_invalidTaskTime_throwsIllegalValueException() {
        XmlAdaptedTask xmlAdaptedTask = new XmlAdaptedTask(VALID_TASK_DESCRIPTION, INVALID_TIME,
                VALID_TASK_DATE);
        String expectedMessage = Time.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, xmlAdaptedTask::toModelType);
    }

    @Test
    public void toModelType_nullTaskTime_throwsIllegalValueException() {
        XmlAdaptedTask xmlAdaptedTask = new XmlAdaptedTask(VALID_TASK_DESCRIPTION, null,
                VALID_TASK_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Time.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, xmlAdaptedTask::toModelType);
    }

    @Test
    public void toModelType_nullAssignor_throwsIllegalValueException() {
        XmlAdaptedTask xmlAdaptedTask = new XmlAdaptedTask(VALID_TASK_DESCRIPTION, VALID_TASK_TIME,
                VALID_TASK_DATE, null, VALID_TASK_ASSIGNEE, VALID_TASK_STATUS_TO_BEGIN);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Assignor.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, xmlAdaptedTask::toModelType);
    }

    @Test
    public void toModelType_nullAssignee_throwsIllegalValueException() {
        XmlAdaptedTask xmlAdaptedTask = new XmlAdaptedTask(VALID_TASK_DESCRIPTION, VALID_TASK_TIME,
                VALID_TASK_DATE, VALID_TASK_ASSIGNOR, null, VALID_TASK_STATUS_TO_BEGIN);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Assignee.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, xmlAdaptedTask::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        XmlAdaptedTask xmlAdaptedTask = new XmlAdaptedTask(VALID_TASK_DESCRIPTION, VALID_TASK_TIME,
                VALID_TASK_DATE, VALID_TASK_ASSIGNOR, VALID_TASK_ASSIGNEE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Status.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, xmlAdaptedTask::toModelType);
    }

    @Test
    public void equals() {

        XmlAdaptedTask firstXmlAdaptedTask = new XmlAdaptedTask(BUY_CONFETTI.getDescription().getDescription(),
                BUY_CONFETTI.getTime().getTime(), BUY_CONFETTI.getDate().getDate(),
                BUY_CONFETTI.getAssignor().getValue(), BUY_CONFETTI.getAssignee().getValue(),
                BUY_CONFETTI.getStatus().getStatus());

        XmlAdaptedTask secondXmlAdaptedTask = new XmlAdaptedTask(BUY_FOOD.getDescription().getDescription(),
                BUY_FOOD.getTime().getTime(), BUY_FOOD.getDate().getDate(),
                BUY_FOOD.getAssignor().getValue(), BUY_FOOD.getAssignee().getValue(),
                BUY_FOOD.getStatus().getStatus());

        assertTrue(firstXmlAdaptedTask.equals(firstXmlAdaptedTask));
        assertFalse(firstXmlAdaptedTask.equals(null));
        assertFalse(firstXmlAdaptedTask.equals(secondXmlAdaptedTask));
    }

}
```
###### \java\seedu\club\testutil\TaskBuilder.java
``` java

import seedu.club.model.task.Assignee;
import seedu.club.model.task.Assignor;
import seedu.club.model.task.Date;
import seedu.club.model.task.Description;
import seedu.club.model.task.Status;
import seedu.club.model.task.Task;
import seedu.club.model.task.Time;

/**
 * Utility class to build Task objects
 */
public class TaskBuilder {
    public static final String DEFAULT_DESCRIPTION = "Book Auditorium";
    public static final String DEFAULT_DATE = "01/05/2018";
    public static final String DEFAULT_TIME = "15:00";
    public static final String DEFAULT_ASSIGNOR = "";
    public static final String DEFAULT_ASSIGNEE = "";
    public static final String DEFAULT_STATUS = "Yet To Begin";

    private Description description;
    private Date date;
    private Time time;
    private Assignor assignor;
    private Assignee assignee;
    private Status status;

    public TaskBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        date = new Date(DEFAULT_DATE);
        time = new Time(DEFAULT_TIME);
        assignor = new Assignor(DEFAULT_ASSIGNOR);
        assignee = new Assignee(DEFAULT_ASSIGNEE);
        status = new Status(DEFAULT_STATUS);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code task}
     */
    public TaskBuilder(Task task) {
        description = task.getDescription();
        date = task.getDate();
        time = task.getTime();
        assignor = task.getAssignor();
        assignee = task.getAssignee();
        status = task.getStatus();
    }

    /**
     * Sets the {@code Description} of the {@code Task} that is being built
     */
    public TaskBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Task} that is being built
     */
    public TaskBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Task} that is being built
     */
    public TaskBuilder withTime(String time) {
        this.time = new Time(time);
        return this;
    }

    /**
     * Sets the {@code Assignor} of the {@code Task} that is being built
     */
    public TaskBuilder withAssignor(String assignor) {
        this.assignor = new Assignor(assignor);
        return this;
    }

    /**
     * Sets the {@code Assignee} of the {@code Task} that is being built
     */
    public TaskBuilder withAssignee(String assignee) {
        this.assignee = new Assignee(assignee);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Task} that is being built
     */
    public TaskBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    public Task build() {
        return new Task(description, time, date, assignor, assignee, status);
    }
}
```
###### \java\seedu\club\testutil\TaskUtil.java
``` java

import static seedu.club.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.club.logic.commands.AddTaskCommand;
import seedu.club.model.task.Task;

/**
 * Utility class for Task
 */
public class TaskUtil {
    public static String getAddTaskCommand(Task task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    private static String getTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DESCRIPTION + task.getDescription().getDescription() + " ");
        sb.append(PREFIX_DATE + task.getDate().getDate() + " ");
        sb.append(PREFIX_TIME + task.getTime().getTime() + " ");
        return sb.toString();
    }
}
```
###### \java\seedu\club\testutil\TypicalTasks.java
``` java

import static seedu.club.testutil.TypicalMembers.ALICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.club.model.ClubBook;
import seedu.club.model.member.exceptions.DuplicateMatricNumberException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;

/**
 * Utility class containing list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task BOOK_AUDITORIUM = new TaskBuilder()
            .withDescription("Book Auditorium")
            .withDate("02/05/2018")
            .withTime("13:00")
            .withAssignor("A8389539B")
            .withAssignee("A8389539B")
            .withStatus("Yet To Begin")
            .build();

    public static final Task BOOK_AUDITORIUM_COPY = new TaskBuilder()
            .withDescription("Book Auditorium")
            .withDate("02/05/2018")
            .withTime("13:00")
            .withAssignor("A8389539B")
            .withAssignee("A9210701B")
            .withStatus("Yet To Begin")
            .build();

    public static final Task BUY_CONFETTI = new TaskBuilder()
            .withDescription("Buy Confetti")
            .withDate("01/05/2018")
            .withTime("17:00")
            .withAssignor("A9210701B")
            .withAssignee("A9210701B")
            .withStatus("Yet To Begin")
            .build();

    public static final Task ADVERTISE_EVENT = new TaskBuilder()
            .withDescription("Advertise event")
            .withDate("12/05/2018")
            .withTime("19:00")
            .withAssignor("A9210701B")
            .withAssignee("A9210701B")
            .withStatus("Yet To Begin")
            .build();

    public static final Task BUY_FOOD = new TaskBuilder()
            .withDescription("Buy Food")
            .withDate("02/05/2018")
            .withTime("19:00")
            .withAssignor("A9210701B")
            .withAssignee("A9210701B")
            .withStatus("Yet To Begin")
            .build();

    private TypicalTasks() {}

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(BOOK_AUDITORIUM, BUY_CONFETTI, ADVERTISE_EVENT, BUY_FOOD));
    }

    /**
     * Returns an {@code AddressBook} with one person and all typical orders.
     */
    public static ClubBook getTypicalClubBookWithTasks() {
        ClubBook clubBook = new ClubBook();

        try {
            clubBook.addMember(ALICE);
        } catch (DuplicateMatricNumberException dmne) {
            throw new AssertionError("not possible");
        }

        for (Task task : getTypicalTasks()) {
            try {
                clubBook.addTaskToTaskList(task);
            } catch (DuplicateTaskException dte) {
                throw new AssertionError("not possible");
            }
        }
        return clubBook;
    }
}
```
###### \java\seedu\club\ui\TaskCardTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.ui.testutil.GuiTestAssert.assertCardDisplaysTask;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.club.model.task.Task;
import seedu.club.testutil.TaskBuilder;

public class TaskCardTest extends GuiUnitTest {
    @Test
    public void equals() {
        Task task = new TaskBuilder().build();
        TaskCard taskCard = new TaskCard(task, 0);

        // same task, same index -> returns true
        TaskCard copy = new TaskCard(task, 0);
        assertTrue(taskCard.equals(copy));

        // same object -> returns true
        assertTrue(taskCard.equals(taskCard));

        // null -> returns false
        assertFalse(taskCard.equals(null));

        // different types -> returns false
        assertFalse(taskCard.equals(0));

        // different task, same index -> returns false
        Task differentTask = new TaskBuilder().withDescription("differentDescription").build();
        assertFalse(taskCard.equals(new TaskCard(differentTask, 0)));

        // same task, different index -> returns false
        assertFalse(taskCard.equals(new TaskCard(task, 1)));
    }

    @Test
    public void display() {
        Task task = new TaskBuilder().build();
        TaskCard taskCard = new TaskCard(task, 1);
        uiPartRule.setUiPart(taskCard);
        assertCardDisplay(taskCard, task, 1);
    }

    /**
     * Asserts that {@code taskCard} displays the details of {@code expectedTask} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(TaskCard taskCard, Task expectedTask, int expectedId) {
        guiRobot.pauseForHuman();

        TaskCardHandle taskCardHandle = new TaskCardHandle(taskCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", taskCardHandle.getId());

        // verify member details are displayed correctly
        assertCardDisplaysTask(expectedTask, taskCardHandle);
    }
}
```
###### \java\seedu\club\ui\TaskListPanelTest.java
``` java
import static org.junit.Assert.assertEquals;
import static seedu.club.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.club.testutil.TypicalTasks.getTypicalTasks;
import static seedu.club.ui.testutil.GuiTestAssert.assertCardDisplaysTask;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.club.commons.events.ui.JumpToListRequestEvent;
import seedu.club.model.task.Task;

public class TaskListPanelTest extends GuiUnitTest {
    private static final ObservableList<Task> TYPICAL_TASKS =
            FXCollections.observableList(getTypicalTasks());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_TASK);

    private TaskListPanelHandle taskListPanelHandle;
    private TaskListPanel taskListPanel;

    @Before
    public void setUp() {
        taskListPanel = new TaskListPanel(TYPICAL_TASKS);
        uiPartRule.setUiPart(taskListPanel);

        taskListPanelHandle = new TaskListPanelHandle(getChildNode(taskListPanel.getRoot(),
                TaskListPanelHandle.TASK_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_TASKS.size(); i++) {
            taskListPanelHandle.navigateToCard(TYPICAL_TASKS.get(i));
            Task expectedTask = TYPICAL_TASKS.get(i);
            TaskCardHandle actualCard = taskListPanelHandle.getTaskCardHandle(i);

            assertCardDisplaysTask(expectedTask, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }
}
```
###### \java\systemtests\AddTaskCommandSystemTest.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.club.logic.commands.CommandTestUtil.EMPTY_STRING;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_2;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_CONFETTI;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_FOOD;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_2;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.commands.AddTaskCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.RedoCommand;
import seedu.club.logic.commands.UndoCommand;
import seedu.club.model.Model;
import seedu.club.model.member.Member;
import seedu.club.model.task.Date;
import seedu.club.model.task.Description;
import seedu.club.model.task.Time;

public class AddTaskCommandSystemTest extends ClubBookSystemTest {

    @Test
    public void addTask() throws Exception {
        Model model = getModel();
        Model modelBeforeAdding = getModel();
        ObservableList<Member> memberObservableList = model.getClubBook().getMemberList();
        String logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(0).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);
        model = getModel();
        modelBeforeAdding = getModel();

        /* Case: add a task to a non-empty address book,
         * command with leading spaces and trailing spaces -> added
         */
        String command = "  " + AddTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD
                + "  " + TASK_DATE_DESC_1 + "  " + TASK_TIME_DESC_1 +  "  ";

        String expectedMessage = AddTaskCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedMessage);

        /* Case:undo adding BUY_FOOD to the list -> BUY_FOOD deleted */
        command = UndoCommand.COMMAND_WORD;
        expectedMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeAdding, expectedMessage);

        /* Case: redo removing BUY_FOOD from the list -> BUY_FOOD re-added */

        command = RedoCommand.COMMAND_WORD;
        expectedMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: add task with all fields same as another task in address book except task description -> added */
        command = AddTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_CONFETTI
                + " " + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_1 +  " ";
        expectedMessage = AddTaskCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: add task with all fields same as another task in address book except task due date -> added */
        command = AddTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_CONFETTI + " "
                + TASK_DATE_DESC_2 + " " + TASK_TIME_DESC_1 + " ";
        expectedMessage = AddTaskCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: add task with all fields same as another task in address book except task time -> added */
        command = AddTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_CONFETTI + " "
                + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_2 + " ";
        expectedMessage = AddTaskCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedMessage);

        /* Case add task with fields in random order -> added */
        command = AddTaskCommand.COMMAND_WORD + " " + "desc/Book Auditorium "
                + "d/02/05/2018 " + "ti/13:00 ";
        assertCommandSuccess(command, model, expectedMessage);

        /* --------------------- Perform addtask operations on the shown filtered list -------------------------- */

        /* --------------------------------- Perform invalid addtask operations --------------------------------- */

        /* Case: missing description -> rejected */
        command = AddTaskCommand.COMMAND_WORD + " "
                + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = AddTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_CONFETTI + " " + TASK_TIME_DESC_1;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        /* Case: missing time -> rejected */
        command = AddTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_CONFETTI + " " + TASK_DATE_DESC_1;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        /* Case: invalid command word -> rejected */
        command = "addatask" + " " + TASK_DESCRIPTION_DESC_CONFETTI + " "
                + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1;
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid description -> rejected */
        command = AddTaskCommand.COMMAND_WORD + " "
                + PREFIX_DESCRIPTION + EMPTY_STRING + " " + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1;
        assertCommandFailure(command, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        command = AddTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_CONFETTI + " " + TASK_TIME_DESC_1 + " " + INVALID_DATE_DESC;
        assertCommandFailure(command, Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid time -> rejected */
        command = AddTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_CONFETTI + " " + INVALID_TIME_DESC + " " + TASK_DATE_DESC_1;
        assertCommandFailure(command, Time.MESSAGE_TIME_CONSTRAINTS);
    }


    /**
     * Executes the {@code AddTaskCommand} that adds {@code toAdd} to the model and asserts that:<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddTaskCommand} with details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     *Executes {@code command} and asserts that the:<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\AssignTaskCommandSystemTest.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.club.logic.commands.CommandTestUtil.EMPTY_STRING;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_MATRIC_NUMBER_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.club.logic.commands.CommandTestUtil.MATRIC_NUMBER_DESC_BENSON;
import static seedu.club.logic.commands.CommandTestUtil.MATRIC_NUMBER_DESC_BOB;
import static seedu.club.logic.commands.CommandTestUtil.MATRIC_NUMBER_DESC_CARL;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_2;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_CONFETTI;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_FOOD;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_2;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_BENSON;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_CARL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.commons.core.Messages;
import seedu.club.logic.commands.AssignTaskCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.LogOutCommand;
import seedu.club.logic.commands.RedoCommand;
import seedu.club.logic.commands.UndoCommand;
import seedu.club.model.Model;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.task.Date;
import seedu.club.model.task.Description;
import seedu.club.model.task.Time;

public class AssignTaskCommandSystemTest extends ClubBookSystemTest {

    @Test
    public void assignTask() throws Exception {
        Model model = getModel();
        Model modelBeforeAdding = getModel();
        ObservableList<Member> memberObservableList = model.getClubBook().getMemberList();
        String logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(0).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);
        model = getModel();
        modelBeforeAdding = getModel();

        /* Case: add a task to a non-empty address book,
         * command with leading spaces and trailing spaces -> added
         */
        String command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_1 + " " + MATRIC_NUMBER_DESC_BENSON;

        String expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, VALID_MATRIC_NUMBER_BENSON);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case:undo assigning BUY_FOOD to Benson -> BUY_FOOD deleted */
        command = UndoCommand.COMMAND_WORD;
        expectedMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeAdding, expectedMessage);

        /* Case: redo removing BUY_FOOD from the list -> BUY_FOOD re-added and re-assigned to Benson*/
        command = RedoCommand.COMMAND_WORD;
        expectedMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: assign task with all fields same as another task in address book except task description -> added */
        command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_CONFETTI + " "
                + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_1 + " " + MATRIC_NUMBER_DESC_BENSON;
        expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, VALID_MATRIC_NUMBER_BENSON);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: assign task with all fields same as another task in address book except task date -> added */
        command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_DATE_DESC_2 + " " + TASK_TIME_DESC_1 + " " + MATRIC_NUMBER_DESC_BENSON;
        expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, VALID_MATRIC_NUMBER_BENSON);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: assign task with all fields same as another task in address book except task time -> added */
        command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_2 + " " + MATRIC_NUMBER_DESC_BENSON;
        expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, VALID_MATRIC_NUMBER_BENSON);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: assign task with all fields same as another task in address book except task assignee -> added */
        command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_DATE_DESC_2 + " " + TASK_TIME_DESC_1 + " " + MATRIC_NUMBER_DESC_CARL;
        expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, VALID_MATRIC_NUMBER_CARL);
        assertCommandSuccess(command, model, expectedMessage);

        /* --------------------------------- Perform invalid assigntask operations ------------------------------ */
        /* Case: member not found -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_FOOD + " " + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " "
                + MATRIC_NUMBER_DESC_BOB;
        assertCommandFailure(command, AssignTaskCommand.MESSAGE_MEMBER_NOT_FOUND);

        command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_1 + " " + MATRIC_NUMBER_DESC_BENSON;
        assertCommandFailure(command, AssignTaskCommand.MESSAGE_DUPLICATE_TASK);

        /* --------------------- Perform assigntask operations on the shown filtered list ----------------------- */

        /* --------------------------------- Perform invalid assigntask operations ------------------------------ */
        String logoutCommand = " " + LogOutCommand.COMMAND_WORD;
        executeCommand(logoutCommand);

        // login Benson
        logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(3).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);

        /* Case: add a task to a non-empty address book,
         * command with leading spaces and trailing spaces -> REJECTED because Benson is not an EXCO member.
         */
        command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_1 + " " + MATRIC_NUMBER_DESC_CARL;

        assertCommandFailure(command, Messages.MESSAGE_REQUIRE_EXCO_LOG_IN);

        logoutCommand = " " + LogOutCommand.COMMAND_WORD;
        executeCommand(logoutCommand);

        // login Alice
        logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(0).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);


        /* Case: missing description -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " " + MATRIC_NUMBER_DESC_BENSON;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_TIME_DESC_1 + " " + TASK_DESCRIPTION_DESC_FOOD + " " + MATRIC_NUMBER_DESC_BENSON;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE));

        /* Case: missing time -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_FOOD + " " + TASK_DATE_DESC_1 + " " + MATRIC_NUMBER_DESC_BENSON;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE));

        /* Case: missing assignee -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " " + TASK_DESCRIPTION_DESC_FOOD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE));

        /* Case: invalid command word -> rejected */
        command = "assignatask" + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " " + MATRIC_NUMBER_DESC_BENSON;
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid description -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + PREFIX_DESCRIPTION + EMPTY_STRING + " " + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " "
                + MATRIC_NUMBER_DESC_BENSON;
        assertCommandFailure(command, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_FOOD + " " + TASK_TIME_DESC_1 + " " + INVALID_DATE_DESC + " "
                + MATRIC_NUMBER_DESC_BENSON;
        assertCommandFailure(command, Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid time -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_FOOD + " " + INVALID_TIME_DESC + " " + TASK_DATE_DESC_1 + " "
                + MATRIC_NUMBER_DESC_BENSON;
        assertCommandFailure(command, Time.MESSAGE_TIME_CONSTRAINTS

        );

        /* Case: invalid matric number -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_FOOD + " " + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " "
                + INVALID_MATRIC_NUMBER_DESC;
        assertCommandFailure(command, MatricNumber.MESSAGE_MATRIC_NUMBER_CONSTRAINTS);

    }

```
###### \java\systemtests\DeleteGroupCommandSystemTest.java
``` java
import static org.junit.Assert.assertEquals;
import static seedu.club.commons.core.Messages.MESSAGE_MANDATORY_GROUP;
import static seedu.club.commons.core.Messages.MESSAGE_NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.GROUP_DESC_TEST;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_GROUP_DESC;
import static seedu.club.logic.commands.CommandTestUtil.MANDATORY_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.MANDATORY_GROUP_DESC;
import static seedu.club.logic.commands.CommandTestUtil.NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.NON_EXISTENT_GROUP_DESC;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_TEST;
import static seedu.club.logic.commands.DeleteGroupCommand.MESSAGE_SUCCESS;

import org.apache.commons.lang3.text.WordUtils;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.commands.DeleteGroupCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.RedoCommand;
import seedu.club.logic.commands.UndoCommand;
import seedu.club.model.Model;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.Member;

public class DeleteGroupCommandSystemTest extends ClubBookSystemTest {

    @Test
    public void removeGroup() {
        Model expectedModel = getModel();
        Model modelBeforeDeletingGroup = getModel();
        ObservableList<Member> memberObservableList = expectedModel.getClubBook().getMemberList();
        String logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(0).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);
        expectedModel.updateFilteredMemberList(expectedModel.PREDICATE_SHOW_ALL_MEMBERS);
        modelBeforeDeletingGroup.updateFilteredMemberList(modelBeforeDeletingGroup.PREDICATE_SHOW_ALL_MEMBERS);
        Group deletedGroup;
        String command;
        /* ------------------------ Perform removegroup operations on the shown unfiltered list -------------------- */

        /* Case: delete a valid group which is present in the club book */
        command = " " + DeleteGroupCommand.COMMAND_WORD + " " + GROUP_DESC_TEST + " ";
        deletedGroup = deleteGroup(expectedModel, VALID_GROUP_TEST);
        String expectedResultMessage = String.format(MESSAGE_SUCCESS, deletedGroup);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: undo deleting the group -> group restored in relevant members */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingGroup, expectedResultMessage);

        /*Case: redo deleting the group -> deleted */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete an invalid group */
        command = " " + DeleteGroupCommand.COMMAND_WORD + " " + INVALID_GROUP_DESC + " ";
        deletedGroup = deleteGroup(expectedModel, INVALID_GROUP);
        assertEquals(null, deletedGroup);
        assertCommandFailure(command, Group.MESSAGE_GROUP_CONSTRAINTS);

        /* Case: delete a mandatory group */
        command = " " + DeleteGroupCommand.COMMAND_WORD + " " + MANDATORY_GROUP_DESC + " ";
        deletedGroup = deleteGroup(expectedModel, MANDATORY_GROUP);
        assertEquals(null, deletedGroup);
        assertCommandFailure(command, String.format(MESSAGE_MANDATORY_GROUP, MANDATORY_GROUP));

        /* Case: delete a non-existent group */
        command = " " + DeleteGroupCommand.COMMAND_WORD + " " + NON_EXISTENT_GROUP_DESC + " ";
        deletedGroup = deleteGroup(expectedModel, NON_EXISTENT_GROUP);
        assertEquals(null, deletedGroup);
        assertCommandFailure(command, String.format(MESSAGE_NON_EXISTENT_GROUP,
                WordUtils.capitalize(NON_EXISTENT_GROUP)));
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the command box has the error style.<br>
     *
     *
     * Verifications 1 to 3 are performed by
     * {@code ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        executeCommand(command);
        Model expectedModel = getModel();
        expectedModel.updateFilteredMemberList(expectedModel.PREDICATE_SHOW_ALL_MEMBERS);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
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
     * Removes the group from model
     * @param model expected model
     * @param group new Group object to be created with this string
     * @return either a valid Group object if the group has been deleted; null otherwise
     */
    private Group deleteGroup(Model model, String group) {
        if (Group.isValidGroup(group)) {
            try {
                model.deleteGroup(new Group(group));
            } catch (GroupNotFoundException gnfe) {
                return null;
            } catch (GroupCannotBeRemovedException e) {
                return null;
            }

            return new Group(group);
        }
        return null;
    }
}
```
###### \java\systemtests\EmailCommandSystemTest.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_CLIENT;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.VALID_CLIENT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.club.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_SUBJECT;

import org.apache.commons.lang3.text.WordUtils;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.commands.EmailCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.model.Model;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.member.Member;

public class EmailCommandSystemTest extends ClubBookSystemTest {

    @Test
    public void sendEmail() {
        Model model = getModel();
        ObservableList<Member> memberObservableList = model.getClubBook().getMemberList();
        String logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(0).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);
        model = getModel();


        /*Case: invalid client entered -> rejected*/
        String command = "  " + EmailCommand.COMMAND_WORD + "  " + PREFIX_GROUP + "pr " + PREFIX_CLIENT
                + "YAHOO " + " " + PREFIX_SUBJECT + Subject.TEST_SUBJECT_STRING
                + " " + PREFIX_BODY + Body.TEST_BODY_STRING;
        String expectedResultMessage = Client.MESSAGE_CLIENT_CONSTRAINTS;
        assertCommandFailure(command, expectedResultMessage);

        /*Case: invalid group -> rejected */
        command = "  " + EmailCommand.COMMAND_WORD + "  " + PREFIX_GROUP + INVALID_GROUP + " "
                + PREFIX_CLIENT + INVALID_CLIENT + " " + PREFIX_SUBJECT + Subject.TEST_SUBJECT_STRING
                + " " + PREFIX_BODY + Body.TEST_BODY_STRING;
        expectedResultMessage = Group.MESSAGE_GROUP_CONSTRAINTS;
        assertCommandFailure(command, expectedResultMessage);

        /*Case: invalid group -> rejected */
        command = "  " + EmailCommand.COMMAND_WORD + "  " + PREFIX_GROUP + NON_EXISTENT_GROUP + " "
                + PREFIX_CLIENT + VALID_CLIENT + " " + PREFIX_SUBJECT + Subject.TEST_SUBJECT_STRING
                + " " + PREFIX_BODY + Body.TEST_BODY_STRING;
        expectedResultMessage = String.format(MESSAGE_NON_EXISTENT_GROUP, WordUtils.capitalize(NON_EXISTENT_GROUP));
        assertCommandFailure(command, expectedResultMessage);

        /* Case: valid command entered -> email client opened */
        command = "  " + EmailCommand.COMMAND_WORD + "  " + PREFIX_GROUP + "pr " + PREFIX_CLIENT
                + VALID_CLIENT + " " + PREFIX_SUBJECT + Subject.TEST_SUBJECT_STRING + " "
                + PREFIX_BODY + Body.TEST_BODY_STRING;
        expectedResultMessage = EmailCommand.EMAIL_CLIENT_OPENED;
        assertCommandSuccess(command, model, expectedResultMessage);
    }


    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box has the default style class.<br>
     * 2. Browser url and selected card remain unchanged.<br>
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        expectedModel.updateFilteredMemberList(Model.PREDICATE_SHOW_ALL_MEMBERS);
        assertSelectedCardUnchanged();
    }

```
###### \java\systemtests\EmailCommandSystemTest.java
``` java

}
```
