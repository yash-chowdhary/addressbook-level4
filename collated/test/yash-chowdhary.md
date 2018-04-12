# yash-chowdhary
###### \java\seedu\club\commons\events\ui\SendEmailRequestEventTest.java
``` java
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
        public void voteInPoll(Poll poll, Index answerIndex) {
            fail("This method should not be called");
        }

        @Override
        public void changeStatus(Task taskToEdit, Task editedTask) throws TaskNotFoundException,
                DuplicateTaskException {
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
        public void addMember(Member member) throws DuplicateMatricNumberException {
            fail("This method should not be called");
            return;
        }

        @Override
        public void updateMember(Member target, Member editedMember) throws DuplicateMatricNumberException,
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
    }

    /**
     * A Model stub that always throw a DuplicateTaskException when trying to add a task.
     */
    private class ModelStubThrowingDuplicateTaskException extends ModelStub {
        private final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("logistics"),
                getTagSet("friends"));

        @Override
        public void addTaskToTaskList(Task toAdd) throws DuplicateTaskException {
            throw new DuplicateTaskException();
        }

```
###### \java\seedu\club\logic\commands\ChangeTaskStatusCommandTest.java
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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.member.Member;
import seedu.club.model.task.Status;
import seedu.club.model.task.Task;

public class ChangeTaskStatusCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;
    private ObservableList<Task> taskList;
    private ObservableList<Member> memberList;
    private Member member;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalClubBookWithTasks(), new UserPrefs());
        model.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
    }

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AssignTaskCommand(null, null);
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
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ChangeTaskStatusCommand changeTaskStatusCommand = prepareCommand(outOfBoundIndex,
                new Status(Status.IN_PROGRESS_STATUS));

        assertCommandFailure(changeTaskStatusCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
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

        assertCommandFailure(changeTaskStatusCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

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
###### \java\seedu\club\logic\commands\EmailCommandTest.java
``` java
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

    @Before
    public void setUp() throws CommandException {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalClubBook(), new UserPrefs());
        observableList = model.getClubBook().getMemberList();
        member = observableList.get(0);
        LogInCommand command = new LogInCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
        command.setData(expectedModel, new CommandHistory(), new UndoRedoStack());
        command.execute();
    }

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

    @Test
    public void execute_validCommandToEmailGroupOutlook_success() throws Exception {
        groupToEmail = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased()).getGroup();
        tagToEmail = null;

        EmailCommand emailCommand = prepareCommand(groupToEmail, tagToEmail, outlookClient,
                testSubject, testBody);

        String expectedMessage = EmailCommand.EMAIL_CLIENT_OPENED;
        String expectedRecipients = expectedModel.generateEmailRecipients(groupToEmail, tagToEmail);
        expectedModel.sendEmail(expectedRecipients, gmailClient, testSubject, testBody);

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

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

    @Test
    public void execute_validCommandToEmailTagOutlook_success() throws Exception {
        groupToEmail = null;
        tagToEmail = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());

        EmailCommand emailCommand = prepareCommand(groupToEmail, tagToEmail, outlookClient,
                testSubject, testBody);

        String expectedMessage = EmailCommand.EMAIL_CLIENT_OPENED;
        String expectedRecipients = expectedModel.generateEmailRecipients(groupToEmail, tagToEmail);
        expectedModel.sendEmail(expectedRecipients, outlookClient, testSubject, testBody);

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

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

    @Test
    public void execute_optionalSubject_success() throws Exception {
        groupToEmail = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased()).getGroup();
        tagToEmail = null;

        EmailCommand emailCommand = prepareCommand(groupToEmail, tagToEmail, gmailClient, emptySubject,
                testBody);

        String expectedMessage = EmailCommand.EMAIL_CLIENT_OPENED;
        String expectedRecipients = expectedModel.generateEmailRecipients(groupToEmail, tagToEmail);
        expectedModel.sendEmail(expectedRecipients, gmailClient, emptySubject, testBody);

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_optionalBody_success() throws Exception {
        groupToEmail = null;
        tagToEmail = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());

        EmailCommand emailCommand = prepareCommand(groupToEmail, tagToEmail, outlookClient, testSubject,
                emptyBody);

        String expectedMessage = EmailCommand.EMAIL_CLIENT_OPENED;
        String expectedRecipients = expectedModel.generateEmailRecipients(groupToEmail, tagToEmail);
        expectedModel.sendEmail(expectedRecipients, gmailClient, testSubject, emptyBody);

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

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

        assertFalse(firstCommand.equals(secondCommand));

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
###### \java\seedu\club\logic\commands\RemoveGroupCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code RemoveGroupCommand}.
 */
public class RemoveGroupCommandTest {
    private Model model;
    private Model expectedModel;
    private ObservableList<Member> observableList;
    private Member member;

    @Before
    public void setUp() throws CommandException {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalClubBook(), new UserPrefs());
        observableList = model.getClubBook().getMemberList();
        member = observableList.get(0);
        LogInCommand command = new LogInCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
        command.setData(expectedModel, new CommandHistory(), new UndoRedoStack());
        command.execute();
    }

    @Test
    public void execute_validGroup_success() throws Exception {
        Group groupToDelete = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased()).getGroup();
        RemoveGroupCommand deleteGroupCommand = prepareCommand(ALICE.getGroup());

        String expectedMessage = String.format(RemoveGroupCommand.MESSAGE_SUCCESS, groupToDelete);
        expectedModel.removeGroup(groupToDelete);


        assertCommandSuccess(deleteGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentGroup_throwsCommandException() {
        Group nonExistentGroup = new Group(NON_EXISTENT_GROUP);
        RemoveGroupCommand deleteGroupCommand = prepareCommand(nonExistentGroup);

        String expectedMessage = String.format(MESSAGE_NON_EXISTENT_GROUP, nonExistentGroup);
        assertCommandFailure(deleteGroupCommand, model, expectedMessage);
    }

    @Test
    public void execute_mandatoryGroup_throwsCommandException() {
        Group mandatoryGroup = new Group(MANDATORY_GROUP);
        RemoveGroupCommand deleteGroupCommand = prepareCommand(mandatoryGroup);
        String expectedMessage = String.format(MESSAGE_MANDATORY_GROUP, mandatoryGroup.toString());
        assertCommandFailure(deleteGroupCommand, model, expectedMessage);
    }

    @Test
    public void executeUndoRedo_validGroup_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Group groupToDelete = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased()).getGroup();
        RemoveGroupCommand deleteGroupCommand = prepareCommand(ALICE.getGroup());
        // remove -> group removed
        deleteGroupCommand.execute();
        undoRedoStack.push(deleteGroupCommand);

        // undo -> reverts Club book back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same group deleted again
        expectedModel.removeGroup(groupToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_nonExistentGroup_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Group nonExistentGroup = new Group(NON_EXISTENT_GROUP);
        RemoveGroupCommand deleteGroupCommand = prepareCommand(nonExistentGroup);

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
        RemoveGroupCommand deleteGroupCommand = prepareCommand(mandatoryGroup);

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
        RemoveGroupCommand deleteGroupCommand = prepareCommand(ALICE.getGroup());
        Group groupToDelete = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased()).getGroup();
        // remove -> removes group
        deleteGroupCommand.execute();
        undoRedoStack.push(deleteGroupCommand);

        // undo -> reverts Club book back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.removeGroup(groupToDelete);
        assertEquals(groupToDelete, model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased()).getGroup());
        // redo -> removes the same group
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        RemoveGroupCommand firstCommand = prepareCommand(new Group(VALID_GROUP_AMY));
        RemoveGroupCommand secondCommand = prepareCommand(new Group(VALID_GROUP_BOB));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(secondCommand.equals(secondCommand));

        // same values -> return true
        RemoveGroupCommand firstCommandCopy = prepareCommand(new Group(VALID_GROUP_AMY));
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
    private RemoveGroupCommand prepareCommand(Group group) {
        RemoveGroupCommand deleteGroupCommand = new RemoveGroupCommand(group);
        deleteGroupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteGroupCommand;
    }
}
```
###### \java\seedu\club\logic\commands\ViewAllTasksCommandTest.java
``` java
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.testutil.TypicalMembers.BENSON;
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
        model.logsInMember(BENSON.getCredentials().getUsername().value,
                BENSON.getCredentials().getPassword().value);
        model.updateFilteredTaskList(Model.PREDICATE_NOT_SHOW_ALL_TASKS);
        String expectedMessage = ViewAllTasksCommand.MESSAGE_CANNOT_VIEW;
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
import static seedu.club.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_2;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_FOOD;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_2;
import static seedu.club.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DATE_1;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_CONFETTI;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_FOOD;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_STATUS_TO_BEGIN;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_TIME_1;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.club.testutil.TypicalMembers.BOB;

import org.junit.Test;

import seedu.club.logic.commands.AssignTaskCommand;
import seedu.club.model.member.Name;
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

        Name name = BOB.getName();
        assertParseSuccess(parser, " " + TASK_DESCRIPTION_DESC_FOOD + TASK_DATE_DESC_1
                        + TASK_TIME_DESC_1 + NAME_DESC_BOB,
                new AssignTaskCommand(expectedTask, name));
    }

    @Test
    public void parse_fieldsMissing_failure() {
        assertParseFailure(parser, TASK_DESCRIPTION_DESC_FOOD + TASK_DATE_DESC_1
                + VALID_TASK_TIME_1 + NAME_DESC_BOB, expectedMessage);

        assertParseFailure(parser, TASK_DATE_DESC_2 + TASK_TIME_DESC_2 + VALID_TASK_DESCRIPTION_CONFETTI
                + NAME_DESC_BOB,
                expectedMessage);

        assertParseFailure(parser,  TASK_TIME_DESC_1 + TASK_DESCRIPTION_DESC_FOOD + VALID_TASK_DATE_1
                + NAME_DESC_BOB,
                expectedMessage);

        assertParseFailure(parser,  TASK_TIME_DESC_1 + TASK_DESCRIPTION_DESC_FOOD + TASK_DATE_DESC_1
                + VALID_NAME_BOB,
                expectedMessage);

        assertParseFailure(parser, TASK_DATE_DESC_1 + VALID_TASK_DATE_1, expectedMessage);

        assertParseFailure(parser, VALID_TASK_DESCRIPTION_FOOD + VALID_TASK_DATE_1, expectedMessage);

        assertParseFailure(parser, VALID_TASK_DESCRIPTION_FOOD + VALID_TASK_TIME_1, expectedMessage);
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
###### \java\seedu\club\logic\parser\RemoveGroupCommandParserTest.java
``` java
public class RemoveGroupCommandParserTest {
    private RemoveGroupCommandParser parser = new RemoveGroupCommandParser();

    @Test
    public void parse_fieldPresent_success() {
        assertParseSuccess(parser, GROUP_DESC_BOB, new RemoveGroupCommand(new Group(VALID_GROUP_BOB)));
        assertParseSuccess(parser, GROUP_DESC_AMY, new RemoveGroupCommand(new Group(VALID_GROUP_AMY)));
    }

    @Test
    public void parse_incorrectField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveGroupCommand.MESSAGE_USAGE);
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
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveGroupCommand.MESSAGE_USAGE);

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
###### \java\seedu\club\model\ClubBookTest.java
``` java
    @Test
    public void removeGroup_nonExistentGroup_unchangedClubBook() throws Exception {
        try {
            clubBookWithBobAndAmy.removeGroup(new Group(NON_EXISTENT_GROUP));
        } catch (GroupNotFoundException gnfe) {
            ClubBook expectedClubBook = new ClubBookBuilder().withMember(BOB).withMember(AMY).build();
            assertEquals(expectedClubBook, clubBookWithBobAndAmy);
        }
    }

    @Test
    public void removeGroup_mandatoryGroup_unchangedClubBook() throws Exception {
        try {
            clubBookWithBobAndAmy.removeGroup(new Group(MANDATORY_GROUP));
        } catch (GroupCannotBeRemovedException e) {
            ClubBook expectedClubBook = new ClubBookBuilder().withMember(BOB).withMember(AMY).build();
            assertEquals(expectedClubBook, clubBookWithBobAndAmy);
        }
    }

    @Test
    public void removeGroup_atLeastOneMemberInGroup_groupRemoved() throws Exception {
        clubBookWithBobAndAmy.removeGroup(new Group(VALID_GROUP_BOB));

        Member bobNotInLogistics = new MemberBuilder(BOB).withGroup().build();
        Member amyNotInLogistics = new MemberBuilder(AMY).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(bobNotInLogistics)
                .withMember(amyNotInLogistics).build();

        assertEquals(expectedClubBook, clubBookWithBobAndAmy);
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

    @Test
    public void addTask_duplicateTask_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(BOB)
                .withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();

        Task toAdd = new TaskBuilder(BUY_CONFETTI).build();
        try {
            clubBook.addTaskToTaskList(toAdd);
        } catch (DuplicateTaskException dte) {
            Member bob = new MemberBuilder(BOB).build();
            Task buyFood = new TaskBuilder(BUY_FOOD).build();
            Task buyConfetti = new TaskBuilder(BUY_CONFETTI).build();
            ClubBook expectedClubBook = new ClubBookBuilder()
                    .withMember(bob)
                    .withTask(buyFood)
                    .withTask(buyConfetti)
                    .build();
            assertEquals(expectedClubBook, clubBook);
        }
    }

    @Test
    public void updateTask_validTask_success() {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY)
                .withTask(BUY_CONFETTI).withTask(BUY_FOOD).build();

        Member amy = new MemberBuilder(AMY).build();
        Task buyFood = new TaskBuilder()
                .withDescription(BUY_FOOD.getDescription().getDescription())
                .withAssignor(BUY_FOOD.getAssignor().getAssignor())
                .withAssignee(BUY_FOOD.getAssignee().getAssignee())
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
            clubBook.updateTask(taskToEdit, editedTask);
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
            clubBook.updateTask(taskToEdit, editedTask);
        } catch (DuplicateTaskException dte) {
            assertEquals(expectedClubBook, clubBook);
        } catch (TaskNotFoundException tnfe) {
            fail("This will not be executed");
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
            modelManager.removeGroup(new Group(NON_EXISTENT_GROUP));
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
            modelManager.removeGroup(new Group(MANDATORY_GROUP));
        } catch (GroupCannotBeRemovedException e) {
            assertEquals(new ModelManager(clubBook, userPrefs), modelManager);
        }
    }

    @Test
    public void removeGroup_atLeastOneMemberInGroup_groupRemoved() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.removeGroup(new Group(VALID_GROUP_AMY));

        Member amyNotInPublicity = new MemberBuilder(AMY).withGroup().build();
        Member bobNotInPublicity = new MemberBuilder(BOB).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(amyNotInPublicity)
                .withMember(bobNotInPublicity).build();

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
                new Tag(VALID_TAG_FRIEND));
        modelManager.sendEmail(expectedRecipients, new Client(Client.VALID_CLIENT_GMAIL),
                new Subject(Subject.TEST_SUBJECT_STRING), new Body(Body.TEST_BODY_STRING));

        assertEquals(new ModelManager(clubBook, userPrefs), modelManager);
    }

    @Test
    public void addTask_validTask_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        modelManager.addTaskToTaskList(BUY_FOOD);

        Member amy = new MemberBuilder(AMY).build();
        Task buyFood = new TaskBuilder(BUY_FOOD).build();
        Task buyConfetti = new TaskBuilder(BUY_CONFETTI).build();
        ClubBook expectedClubBook = new ClubBookBuilder()
                .withMember(amy)
                .withTask(buyConfetti)
                .withTask(buyFood)
                .build();
        ModelManager expectedModel = new ModelManager(expectedClubBook, userPrefs);
        expectedModel.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        assertEquals(expectedModel, modelManager);
    }

    @Test
    public void addTask_duplicateTask_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        try {
            modelManager.addTaskToTaskList(BUY_CONFETTI);
        } catch (DuplicateTaskException dte) {
            assertEquals(expectedModel, modelManager);
        }
    }

    @Test
    public void assignTask_validTask_throwsException() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(AMY.getCredentials().getUsername().value,
                AMY.getCredentials().getPassword().value);
        modelManager.assignTask(BUY_FOOD, BOB.getName());

        Member amy = new MemberBuilder(AMY).build();
        Member bob = new MemberBuilder(BOB).build();
        Task buyFood = new TaskBuilder()
                .withDescription("Buy Food")
                .withDate("02/05/2018")
                .withTime("19:00")
                .withAssignor("Alice Pauline")
                .withAssignee("Bob Choo")
                .withStatus("Yet To Begin")
                .build();
        Task buyConfetti = new TaskBuilder(BUY_CONFETTI).build();
        ClubBook expectedClubBook = new ClubBookBuilder()
                .withMember(amy)
                .withMember(bob)
                .withTask(buyConfetti)
                .withTask(buyFood)
                .build();

        ModelManager expectedModel = new ModelManager(expectedClubBook, userPrefs);
        expectedModel.logsInMember(AMY.getCredentials().getUsername().value,
                AMY.getCredentials().getPassword().value);
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
            modelManager.assignTask(BUY_FOOD, BOB.getName());
        } catch (DuplicateTaskException dte) {
            assertEquals(expectedModel, modelManager);
        } catch (MemberNotFoundException mnfe) {
            fail("This exception should not be caught");
        } catch (IllegalExecutionException iee) {
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
            modelManager.assignTask(BUY_CONFETTI, BOB.getName());
        } catch (DuplicateTaskException dte) {
            fail("This exception should not be caught");
        } catch (MemberNotFoundException mnfe) {
            assertEquals(expectedModel, modelManager);
        } catch (IllegalExecutionException iee) {
            fail("This exception should not be caught");
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
            modelManager.assignTask(BUY_CONFETTI, AMY.getName());
        } catch (DuplicateTaskException dte) {
            fail("This exception should not be caught");
        } catch (MemberNotFoundException mnfe) {
            fail("This exception should not be caught");
        } catch (IllegalExecutionException iee) {
            assertEquals(expectedModel, modelManager);
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
        } catch (TaskNotFoundException | DuplicateTaskException | IllegalExecutionException e) {
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
        } catch (DuplicateTaskException | IllegalExecutionException | TaskNotFoundException e) {
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
        } catch (IllegalExecutionException iee) {
            assertEquals(expectedModel, modelManager);
        } catch (TaskNotFoundException | DuplicateTaskException e) {
            fail("This will not be executed");
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
        } catch (TasksCannotBeDisplayedException tdbde) {
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
                BUY_CONFETTI.getAssignor().getAssignor(), BUY_CONFETTI.getAssignee().getAssignee(),
                BUY_CONFETTI.getStatus().getStatus());

        XmlAdaptedTask secondXmlAdaptedTask = new XmlAdaptedTask(BUY_FOOD.getDescription().getDescription(),
                BUY_FOOD.getTime().getTime(), BUY_FOOD.getDate().getDate(),
                BUY_FOOD.getAssignor().getAssignor(), BUY_FOOD.getAssignee().getAssignee(),
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
    public static final String DEFAULT_DATE = "01/01/2018";
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
            .withDate("02/04/2018")
            .withTime("13:00")
            .withAssignor("Benson Meier")
            .withAssignee("Benson Meier")
            .withStatus("Yet To Begin")
            .build();

    public static final Task BUY_CONFETTI = new TaskBuilder()
            .withDescription("Buy Confetti")
            .withDate("01/04/2018")
            .withTime("17:00")
            .withAssignor("Alice Pauline")
            .withAssignee("Alice Pauline")
            .withStatus("Yet To Begin")
            .build();

    public static final Task ADVERTISE_EVENT = new TaskBuilder()
            .withDescription("Advertise event")
            .withDate("31/03/2018")
            .withTime("19:00")
            .withAssignor("Alice Pauline")
            .withAssignee("Alice Pauline")
            .withStatus("Yet To Begin")
            .build();

    public static final Task BUY_FOOD = new TaskBuilder()
            .withDescription("Buy Food")
            .withDate("02/05/2018")
            .withTime("19:00")
            .withAssignor("Alice Pauline")
            .withAssignee("Alice Pauline")
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
                + "d/02/04/2018 " + "ti/13:00 ";
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
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_PERMISSIONS;
import static seedu.club.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.club.logic.commands.CommandTestUtil.EMPTY_STRING;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.club.logic.commands.CommandTestUtil.NAME_DESC_BENSON;
import static seedu.club.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.club.logic.commands.CommandTestUtil.NAME_DESC_CARL;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_2;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_CONFETTI;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_FOOD;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_2;
import static seedu.club.logic.commands.CommandTestUtil.VALID_NAME_BENSON;
import static seedu.club.logic.commands.CommandTestUtil.VALID_NAME_CARL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.commands.AssignTaskCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.LogOutCommand;
import seedu.club.logic.commands.RedoCommand;
import seedu.club.logic.commands.UndoCommand;
import seedu.club.model.Model;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
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
                + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_1 + " " + NAME_DESC_BENSON;

        String expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, VALID_NAME_BENSON);
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
                + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_1 + " " + NAME_DESC_BENSON;
        expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, VALID_NAME_BENSON);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: assign task with all fields same as another task in address book except task date -> added */
        command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_DATE_DESC_2 + " " + TASK_TIME_DESC_1 + " " + NAME_DESC_BENSON;
        expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, VALID_NAME_BENSON);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: assign task with all fields same as another task in address book except task time -> added */
        command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_2 + " " + NAME_DESC_BENSON;
        expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, VALID_NAME_BENSON);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: assign task with all fields same as another task in address book except task assignee -> added */
        command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_DATE_DESC_2 + " " + TASK_TIME_DESC_1 + " " + NAME_DESC_CARL;
        expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, VALID_NAME_CARL);
        assertCommandSuccess(command, model, expectedMessage);

        /* --------------------------------- Perform invalid assigntask operations ------------------------------ */
        /* Case: member not found -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_FOOD + " " + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " "
                + NAME_DESC_BOB;
        assertCommandFailure(command, AssignTaskCommand.MESSAGE_MEMBER_NOT_FOUND);

        command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_1 + " " + NAME_DESC_BENSON;
        assertCommandFailure(command, AssignTaskCommand.MESSAGE_DUPLICATE_TASK);

        /* --------------------- Perform assigntask operations on the shown filtered list ----------------------- */

        /* --------------------------------- Perform invalid assigntask operations ------------------------------ */
        String logoutCommand = " " + LogOutCommand.COMMAND_WORD;
        executeCommand(logoutCommand);

        logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(1).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);

        /* Case: add a task to a non-empty address book,
         * command with leading spaces and trailing spaces -> REJECTED because Benson is not an EXCO member.
         */
        command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_1 + " " + NAME_DESC_CARL;

        assertCommandFailure(command, MESSAGE_INVALID_PERMISSIONS);

        logoutCommand = " " + LogOutCommand.COMMAND_WORD;
        executeCommand(logoutCommand);

        logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(0).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);


        /* Case: missing description -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " " + NAME_DESC_BENSON;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_TIME_DESC_1 + " " + TASK_DESCRIPTION_DESC_FOOD + " " + NAME_DESC_BENSON;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE));

        /* Case: missing time -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_FOOD + " " + TASK_DATE_DESC_1 + " " + NAME_DESC_BENSON;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE));

        /* Case: missing assignee -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " " + TASK_DESCRIPTION_DESC_FOOD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE));

        /* Case: missing description -> rejected */
        command = "assignatask" + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " " + NAME_DESC_BENSON;
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid description -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + PREFIX_DESCRIPTION + EMPTY_STRING + " " + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " "
                + NAME_DESC_BENSON;
        assertCommandFailure(command, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_FOOD + " " + TASK_TIME_DESC_1 + " " + INVALID_DATE_DESC + " "
                + NAME_DESC_BENSON;
        assertCommandFailure(command, Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid time -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_FOOD + " " + INVALID_TIME_DESC + " " + TASK_DATE_DESC_1 + " "
                + NAME_DESC_BENSON;
        assertCommandFailure(command, Time.MESSAGE_TIME_CONSTRAINTS

        );

        /* Case: invalid name -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_FOOD + " " + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " "
                + INVALID_NAME_DESC;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);



    }

    /**
     * Executes the {@code AssignTaskCommand} that adds {@code toAdd} to the model and asserts that:<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddTaskCommand} with details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code TaskListPanel} equal to the corresponding components in
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
     * 4. {@code Model}, {@code Storage} and {@code TaskListPanel} remain unchanged.<br>
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
###### \java\systemtests\DeleteTaskCommandSystemTest.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.club.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.club.logic.commands.DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS;
import static seedu.club.testutil.TestUtil.getTask;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalTasks.getTypicalClubBookWithTasks;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.logic.commands.DeleteTaskCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.member.Member;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.TaskCannotBeDeletedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;

public class DeleteTaskCommandSystemTest extends ClubBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_TASK_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE);

    @Test
    public void deleteTask() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first member in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command;
        ObservableList<Member> memberObservableList = expectedModel.getClubBook().getMemberList();
        String logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(0).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);
        expectedModel = new ModelManager(getTypicalClubBookWithTasks(), new UserPrefs());
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        /* Case: invalid index (0) -> rejected */
        command = DeleteTaskCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_TASK_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteTaskCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_TASK_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getClubBook().getTaskList().size() + 1);
        command = DeleteTaskCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteTaskCommand.COMMAND_WORD + " abc",
                MESSAGE_INVALID_DELETE_TASK_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteTaskCommand.COMMAND_WORD + " 1 abc",
                MESSAGE_INVALID_DELETE_TASK_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETETAsk 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Deletes the member at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Task deletedTask = removeTask(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TASK_SUCCESS, deletedTask);

        assertCommandSuccess(
                DeleteTaskCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel,
                expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see ClubBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Removes the {@code task} at the specified {@code index} in {@code model}'s club book.
     * @return the removed task
     */
    private Task removeTask(Model model, Index index) {
        Task targetTask = getTask(model, index);
        try {
            model.deleteTask(targetTask);
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("targetTask is retrieved from model");
        } catch (TaskCannotBeDeletedException tcbde) {
            throw new AssertionError("targetTask cannot be deleted");
        }
        return targetTask;
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
        expectedResultMessage = String.format(MESSAGE_NON_EXISTENT_GROUP, NON_EXISTENT_GROUP);
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
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();
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
###### \java\systemtests\RemoveGroupCommandSystemTest.java
``` java
import static org.junit.Assert.assertEquals;
import static seedu.club.commons.core.Messages.MESSAGE_MANDATORY_GROUP;
import static seedu.club.commons.core.Messages.MESSAGE_NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.GROUP_DESC_AMY;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_GROUP_DESC;
import static seedu.club.logic.commands.CommandTestUtil.MANDATORY_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.MANDATORY_GROUP_DESC;
import static seedu.club.logic.commands.CommandTestUtil.NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.NON_EXISTENT_GROUP_DESC;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_AMY;
import static seedu.club.logic.commands.DeleteGroupCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.RedoCommand;
import seedu.club.logic.commands.DeleteGroupCommand;
import seedu.club.logic.commands.UndoCommand;
import seedu.club.model.Model;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.Member;

public class RemoveGroupCommandSystemTest extends ClubBookSystemTest {

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
        command = " " + RemoveGroupCommand.COMMAND_WORD + " " + GROUP_DESC_AMY + " ";
        deletedGroup = deleteGroup(expectedModel, VALID_GROUP_AMY);
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
        command = " " + RemoveGroupCommand.COMMAND_WORD + " " + INVALID_GROUP_DESC + " ";
        deletedGroup = deleteGroup(expectedModel, INVALID_GROUP);
        assertEquals(null, deletedGroup);
        assertCommandFailure(command, Group.MESSAGE_GROUP_CONSTRAINTS);

        /* Case: delete a mandatory group */
        command = " " + RemoveGroupCommand.COMMAND_WORD + " " + MANDATORY_GROUP_DESC + " ";
        deletedGroup = deleteGroup(expectedModel, MANDATORY_GROUP);
        assertEquals(null, deletedGroup);
        assertCommandFailure(command, String.format(MESSAGE_MANDATORY_GROUP, MANDATORY_GROUP));

        /* Case: delete a non-existent group */
        command = " " + RemoveGroupCommand.COMMAND_WORD + " " + NON_EXISTENT_GROUP_DESC + " ";
        deletedGroup = deleteGroup(expectedModel, NON_EXISTENT_GROUP);
        assertEquals(null, deletedGroup);
        assertCommandFailure(command, String.format(MESSAGE_NON_EXISTENT_GROUP, NON_EXISTENT_GROUP));
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
                model.removeGroup(new Group(group));
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
