# MuhdNurKamal
###### \java\seedu\club\logic\commands\AddPollCommandTest.java
``` java
import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
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
        public void changeAssignee(Task taskToEdit, Task editedTask) throws MemberNotFoundException,
                DuplicateTaskException, TaskAlreadyAssignedException {
            fail("This method should not be called");
        }

        @Override
        public void exportClubConnectMembers(File exportFilePath) {
            fail("This method should not be called.");
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
        public void changePassword(String username,
                                   String oldPassword, String newPassword)
                throws PasswordIncorrectException {
            fail("This method should not be called.");
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

        @Override
        public FilteredList<Member> getFilteredMemberList() {
            fail("This method should not be called.");
            return null;
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

        @Override
        public void addProfilePhoto(String originalPhotoPath) throws PhotoReadException {
            fail("This method should not be called.");
        }

        @Override
        public void removeProfilePhoto() {
            fail("This method should not be called.");
        }

        @Override
        public Member getLoggedInMember() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteGroup(Group toRemove) throws GroupNotFoundException, GroupCannotBeRemovedException {
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
        public int deleteMember(Member member) throws MemberNotFoundException {
            fail("This method should not be called.");
            return -1;
        }

        @Override
        public int updateMember(Member member, Member editedMember)
                throws DuplicateMatricNumberException {
            fail("This method should not be called.");
            return -1;
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
        public void deleteTask(Task taskToDelete) throws TaskNotFoundException, TaskCannotBeDeletedException {
            fail("This method should not be called");
        }
    }

    /**
     * A Model stub that always throw a DuplicatePollException when trying to add a poll.
     */
    private class ModelStubThrowingDuplicatePollException extends ModelStub {
        private final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("exco"),
                getTagSet("head"));

        @Override
        public void addPoll(Poll poll) throws DuplicatePollException {
            throw new DuplicatePollException();
        }

```
###### \java\seedu\club\logic\commands\CompressCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.commons.events.ui.CompressMembersRequestEvent;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.member.Member;
import seedu.club.ui.testutil.EventsCollectorRule;

public class CompressCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    private ObservableList<Member> observableList;
    private Member member;

    @Before
    public void setUp() throws CommandException {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        observableList = model.getClubBook().getMemberList();
        member = observableList.get(0);
        LogInCommand command = new LogInCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
    }

    @Test
    public void execute_help_success() throws CommandException {
        CompressCommand compressCommand = new CompressCommand();
        compressCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult result = compressCommand.execute();
        assertEquals(CompressCommand.MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof CompressMembersRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 2);
    }
}
```
###### \java\seedu\club\logic\commands\DeletePollCommandTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.club.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.club.logic.commands.CommandTestUtil.showPollAtIndex;
import static seedu.club.testutil.MemberBuilder.DEFAULT_PASSWORD;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_POLL;
import static seedu.club.testutil.TypicalIndexes.INDEX_SECOND_POLL;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalPolls.getTypicalClubBookWithPolls;

import org.junit.Before;
import org.junit.Test;

import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.PollIsRelevantToMemberPredicate;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeletePollCommand}.
 */
public class DeletePollCommandTest {

    private Model model = new ModelManager(getTypicalClubBookWithPolls(), new UserPrefs());

    @Before
    public void setUp() {
        model.logsInMember(ALICE.getMatricNumber().toString(), DEFAULT_PASSWORD);
        model.updateFilteredPollList(new PollIsRelevantToMemberPredicate(ALICE));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        model.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        model.updateFilteredPollList(new PollIsRelevantToMemberPredicate(ALICE));
        Poll pollToDelete = model.getFilteredPollList().get(INDEX_FIRST_POLL.getZeroBased());
        DeletePollCommand deletePollCommand = prepareCommand(INDEX_FIRST_POLL);

        String expectedMessage = String.format(DeletePollCommand.MESSAGE_DELETE_POLL_SUCCESS, pollToDelete);

        Model expectedModel = new ModelManager(getTypicalClubBookWithPolls(), new UserPrefs());
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        expectedModel.updateFilteredPollList(new PollIsRelevantToMemberPredicate(ALICE));
        expectedModel.deletePoll(pollToDelete);
        assertCommandSuccess(deletePollCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        model.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPollList().size() + 1);
        DeletePollCommand deletePollCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deletePollCommand, model, Messages.MESSAGE_INVALID_POLL_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        model.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        model.updateFilteredPollList(new PollIsRelevantToMemberPredicate(ALICE));
        showPollAtIndex(model, INDEX_FIRST_POLL);

        Poll pollToDelete = model.getFilteredPollList().get(INDEX_FIRST_POLL.getZeroBased());
        DeletePollCommand deletePollCommand = prepareCommand(INDEX_FIRST_POLL);

        String expectedMessage = String.format(DeletePollCommand.MESSAGE_DELETE_POLL_SUCCESS, pollToDelete);

        Model expectedModel = new ModelManager(getTypicalClubBookWithPolls(), new UserPrefs());
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        expectedModel.updateFilteredPollList(new PollIsRelevantToMemberPredicate(ALICE));
        expectedModel.deletePoll(pollToDelete);
        assertCommandSuccess(deletePollCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        model.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        model.updateFilteredPollList(new PollIsRelevantToMemberPredicate(ALICE));
        showPollAtIndex(model, INDEX_FIRST_POLL);

        Index outOfBoundIndex = INDEX_SECOND_POLL;
        // ensures that outOfBoundIndex is still in bounds of club book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getClubBook().getPollList().size());

        DeletePollCommand deletePollCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deletePollCommand, model, Messages.MESSAGE_INVALID_POLL_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        model.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPollList().size() + 1);
        DeletePollCommand deletePollCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> DeletePollCommand not pushed into undoRedoStack
        assertCommandFailure(deletePollCommand, model, Messages.MESSAGE_INVALID_POLL_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        model.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        DeletePollCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_POLL);
        DeletePollCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_POLL);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePollCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_POLL);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        model.updateFilteredPollList(new PollIsRelevantToMemberPredicate(ALICE));
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different poll -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeletePollCommand} with the parameter {@code index}.
     */
    private DeletePollCommand prepareCommand(Index index) {
        DeletePollCommand deletePollCommand = new DeletePollCommand(index);
        deletePollCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deletePollCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no polls.
     */
    private void showNoPoll(Model model) {
        model.updateFilteredPollList(p -> false);

        assertTrue(model.getFilteredPollList().isEmpty());
    }
}
```
###### \java\seedu\club\logic\commands\FindCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.commons.core.Messages.MESSAGE_MEMBERS_LISTED_OVERVIEW;
import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalMembers.BENSON;
import static seedu.club.testutil.TypicalMembers.CARL;
import static seedu.club.testutil.TypicalMembers.DANIEL;
import static seedu.club.testutil.TypicalMembers.ELLE;
import static seedu.club.testutil.TypicalMembers.FIONA;
import static seedu.club.testutil.TypicalMembers.GEORGE;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.logic.parser.Prefix;
import seedu.club.model.ClubBook;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.member.FieldContainsKeywordsPredicate;
import seedu.club.model.member.Member;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {

    private static final Prefix[] FINDABLE_PREFIXES = {PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE,
        PREFIX_MATRIC_NUMBER, PREFIX_TAG, PREFIX_GROUP};

    private Model model;
    private ObservableList<Member> observableList;
    private Member member;

    @Before
    public void setUp() throws CommandException {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        observableList = model.getClubBook().getMemberList();
        member = observableList.get(0);
        LogInCommand command = new LogInCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
    }

    @Test
    public void equals_returnTrue() {
        for (Prefix prefix : FINDABLE_PREFIXES) {
            assertEqualsCorrectForPrefix(prefix);
        }
    }

    @Test
    public void equals_differentPrefix_returnFalse() {
        Prefix[] prefixes = {PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_MATRIC_NUMBER, PREFIX_TAG, PREFIX_GROUP};
        FindCommand[] commands = new FindCommand[prefixes.length];

        for (int index = 0; index < prefixes.length; index++) {
            commands[index] =
                    new FindCommand(
                            new FieldContainsKeywordsPredicate(Collections.singletonList("first"), prefixes[index]));
        }

        // Check across all pairs
        for (int i = 0; i < commands.length; i++) {
            for (int j = 0; j < commands.length; j++) {
                if (i != j) {
                    assertFalse(commands[i].equals(commands[j]));
                }
            }
        }
    }

    @Test
    public void execute_zeroKeywords_noMemberFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_MEMBERS_LISTED_OVERVIEW, 0);
        for (Prefix prefix : FINDABLE_PREFIXES) {
            assertCommandSuccess(prepareCommand(" ", prefix), expectedMessage, Collections.emptyList());
        }
    }

    @Test
    public void execute_multipleKeywords_multipleMembersFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_MEMBERS_LISTED_OVERVIEW, 3);
        String expectedMessage2 = String.format(MESSAGE_MEMBERS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("Kurz Elle Kunz", PREFIX_NAME);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand("95352563 9482224 9482427", PREFIX_PHONE);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand("heinz@example.com werner@example.com lydia@example.com", PREFIX_EMAIL);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand("A6076201A A1932279G A9662042H", PREFIX_MATRIC_NUMBER);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand("marketing operations", PREFIX_GROUP);
        assertCommandSuccess(command, expectedMessage2, Arrays.asList(FIONA));

        expectedMessage = String.format(MESSAGE_MEMBERS_LISTED_OVERVIEW, 7);
        command = prepareCommand("head heads owesMoney", PREFIX_TAG);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL,
                DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * Parses {@code userInput} and {@code prefix} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput, Prefix prefix) {
        FindCommand command =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")),
                        prefix));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<member>} is equal to {@code expectedList}<br>
     *     - the {@code ClubBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<Member> expectedList)
            throws CommandException {
        ClubBook expectedClubBook = new ClubBook(model.getClubBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredMemberList());
        assertEquals(expectedClubBook, model.getClubBook());
    }

    /**
     * Asserts that equals method for FindCommand with prefix is correct
     *
     * @param prefix of field FindCommand finds for
     */
    private void assertEqualsCorrectForPrefix(Prefix prefix) {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), prefix);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), prefix);

        FindCommand findByFirstCommand = new FindCommand(firstPredicate);
        FindCommand findBySecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findByFirstCommand.equals(findByFirstCommand));

        // same values -> returns true
        FindCommand findByFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findByFirstCommand.equals(findByFirstCommandCopy));

        // different types -> returns false
        assertFalse(findByFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findByFirstCommand == null);

        // different member -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }
}
```
###### \java\seedu\club\logic\commands\ViewResultsCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.commons.events.ui.ViewResultsRequestEvent;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.member.Member;
import seedu.club.ui.testutil.EventsCollectorRule;

public class ViewResultsCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    private Model model;
    private ObservableList<Member> observableList;
    private Member member;

    @Before
    public void setUp() throws CommandException {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        observableList = model.getClubBook().getMemberList();
        member = observableList.get(0);
        LogInCommand command = new LogInCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
    }
    @Test
    public void execute_help_success() throws CommandException {
        ViewResultsCommand resultsCommand = new ViewResultsCommand();
        resultsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult result = resultsCommand.execute();
        assertEquals(ViewResultsCommand.MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ViewResultsRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 2);
    }
}
```
###### \java\seedu\club\logic\commands\VoteCommandTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_ANSWER_DISPLAYED_INDEX;
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_POLL_DISPLAYED_INDEX;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.club.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.club.logic.commands.VoteCommand.MESSAGE_VOTE_SUCCESS;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_ANSWER;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_POLL;
import static seedu.club.testutil.TypicalIndexes.INDEX_SECOND_ANSWER;
import static seedu.club.testutil.TypicalIndexes.INDEX_SECOND_POLL;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalPolls.getTypicalClubBookWithPolls;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.club.commons.core.index.Index;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.poll.Poll;

public class VoteCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalClubBookWithPolls(), new UserPrefs());
        model.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
    }

    @Test
    public void constructor_nullPollIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new VoteCommand(null, INDEX_FIRST_ANSWER);
    }

    @Test
    public void constructor_nullAnswerIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new VoteCommand(INDEX_FIRST_POLL, null);
    }

    @Test
    public void constructor_nullIndexes_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new VoteCommand(null, null);
    }

    @Test
    public void execute_validIndices_voteSuccess() throws Exception {
        Poll pollToVote = model.getFilteredPollList().get(INDEX_FIRST_POLL.getZeroBased());
        VoteCommand voteCommand = prepareCommand(INDEX_FIRST_POLL,
                INDEX_FIRST_ANSWER);
        voteCommand.preprocessUndoableCommand();

        expectedModel = new ModelManager(getTypicalClubBookWithPolls(), new UserPrefs());
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        String expectedMessage = String.format(MESSAGE_VOTE_SUCCESS, pollToVote.getQuestion() + "\n"
                + pollToVote.getAnswers()
                .get(INDEX_FIRST_ANSWER.getZeroBased()));
        Poll votedPoll = new Poll(pollToVote.getQuestion(), pollToVote.getAnswers(),
                pollToVote.getPolleesMatricNumbers());
        votedPoll.vote(INDEX_FIRST_ANSWER, ALICE.getMatricNumber());
        expectedModel.voteInPoll(pollToVote, INDEX_FIRST_ANSWER);

        assertCommandSuccess(voteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPollIndexUnfilteredList_throwsCommandException() throws CommandException {
        Index outOfBoundPollIndex = Index.fromOneBased(model.getFilteredPollList().size() + 1);
        VoteCommand voteCommand = prepareCommand(outOfBoundPollIndex,
                INDEX_FIRST_ANSWER);

        assertCommandFailure(voteCommand, model, MESSAGE_INVALID_POLL_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidAnswerIndexUnfilteredList_throwsCommandException() throws CommandException {
        Index outOfBoundAnswerIndex = Index.fromOneBased(model.getFilteredPollList().get(0).getAnswers().size() + 1);
        VoteCommand voteCommand = prepareCommand(INDEX_FIRST_POLL,
                outOfBoundAnswerIndex);

        assertCommandFailure(voteCommand, model, MESSAGE_INVALID_ANSWER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndicesUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Poll pollToVote = model.getClubBook().getPollList().get(INDEX_FIRST_POLL.getZeroBased());
        Poll votedPoll = new Poll(pollToVote.getQuestion(), pollToVote.getAnswers(),
                pollToVote.getPolleesMatricNumbers());
        votedPoll.vote(INDEX_FIRST_ANSWER, ALICE.getMatricNumber());
        VoteCommand voteCommand = prepareCommand(INDEX_FIRST_POLL, INDEX_FIRST_ANSWER);

        expectedModel = new ModelManager(getTypicalClubBookWithPolls(), new UserPrefs());
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        voteCommand.execute();
        undoRedoStack.push(voteCommand);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.voteInPoll(pollToVote, INDEX_FIRST_ANSWER);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidPollIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Index outOfBoundPollIndex = Index.fromOneBased(model.getFilteredPollList().size() + 1);
        VoteCommand voteCommand = prepareCommand(outOfBoundPollIndex,
                INDEX_FIRST_ANSWER);

        assertCommandFailure(voteCommand, model, MESSAGE_INVALID_POLL_DISPLAYED_INDEX);

        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_invalidAnswerIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Index outOfBoundAnswerIndex = Index.fromOneBased(model.getFilteredPollList().get(0).getAnswers().size() + 1);
        VoteCommand voteCommand = prepareCommand(INDEX_FIRST_POLL,
                outOfBoundAnswerIndex);

        assertCommandFailure(voteCommand, model, MESSAGE_INVALID_ANSWER_DISPLAYED_INDEX);

        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    private VoteCommand prepareCommand(Index pollIndex, Index answerIndex) {
        VoteCommand voteCommand = new VoteCommand(pollIndex, answerIndex);
        voteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return voteCommand;
    }

    @Test
    public void equals() throws Exception {
        VoteCommand voteFirstCommand = prepareCommand(INDEX_FIRST_POLL, INDEX_FIRST_ANSWER);
        VoteCommand voteSecondCommand = prepareCommand(INDEX_FIRST_POLL, INDEX_SECOND_ANSWER);
        VoteCommand voteThirdCommand = prepareCommand(INDEX_SECOND_POLL, INDEX_FIRST_ANSWER);
        VoteCommand voteFourthCommand = prepareCommand(INDEX_SECOND_POLL, INDEX_SECOND_ANSWER);
        VoteCommand[] voteCommands = {voteFirstCommand, voteSecondCommand, voteThirdCommand, voteFourthCommand};

        voteFirstCommand.preprocessUndoableCommand();
        voteSecondCommand.preprocessUndoableCommand();
        voteThirdCommand.preprocessUndoableCommand();
        voteFourthCommand.preprocessUndoableCommand();

        // same object -> returns true
        assertTrue(voteFirstCommand.equals(voteFirstCommand));

        // same values -> returns true
        VoteCommand voteFirstCommandCopy = prepareCommand(INDEX_FIRST_POLL, INDEX_FIRST_ANSWER);
        assertTrue(voteFirstCommand.equals(voteFirstCommandCopy));

        // different types -> returns false
        assertFalse(voteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(voteFirstCommand.equals(null));

        // different polls and answers -> returns false
        for (int index = 0; index < voteCommands.length; index++) {
            for (int indexPrime = 0; indexPrime < voteCommands.length; indexPrime++) {
                if (index != indexPrime) {
                    assertFalse(voteCommands[index].equals(voteCommands[indexPrime]));
                }
            }
        }
    }
}
```
###### \java\seedu\club\logic\parser\AddPollCommandParserTest.java
``` java

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_FOUR;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_ONE;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_THREE;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_TWO;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.club.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.club.logic.commands.CommandTestUtil.QUESTION_DESC_LIFE;
import static seedu.club.logic.commands.CommandTestUtil.QUESTION_DESC_LOVE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_FOUR;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_ONE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_THREE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_TWO;
import static seedu.club.logic.commands.CommandTestUtil.VALID_QUESTION_LIFE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_QUESTION_LOVE;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.club.logic.commands.AddPollCommand;
import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.Question;
import seedu.club.testutil.PollBuilder;

public class AddPollCommandParserTest {
    private AddPollCommandParser parser = new AddPollCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Poll expectedPoll = new PollBuilder().withQuestion(VALID_QUESTION_LIFE)
                .withAnswers(VALID_ANSWER_ONE, VALID_ANSWER_THREE).withNoPollessMatricNumbers().build();

        // whitespace only preamble
        assertParseSuccess(parser, QUESTION_DESC_LIFE + ANSWER_DESC_ONE + ANSWER_DESC_THREE,
                new AddPollCommand(expectedPoll));

        // multiple questions - last question accepted
        assertParseSuccess(parser, QUESTION_DESC_LOVE + QUESTION_DESC_LIFE
                + ANSWER_DESC_ONE + ANSWER_DESC_THREE, new AddPollCommand(expectedPoll));

        // multiple answers - all accepted
        Poll expectedPollMultipleAnswers = new PollBuilder().withQuestion(VALID_QUESTION_LOVE)
                .withAnswers(VALID_ANSWER_ONE, VALID_ANSWER_TWO, VALID_ANSWER_THREE, VALID_ANSWER_FOUR)
                .withNoPollessMatricNumbers().build();
        assertParseSuccess(parser, QUESTION_DESC_LOVE + ANSWER_DESC_ONE + ANSWER_DESC_TWO
                + ANSWER_DESC_THREE + ANSWER_DESC_FOUR, new AddPollCommand(expectedPollMultipleAnswers));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPollCommand.MESSAGE_USAGE);

        // missing question prefix
        assertParseFailure(parser, VALID_QUESTION_LIFE + ANSWER_DESC_ONE + ANSWER_DESC_TWO,
                expectedMessage);

        // missing answer prefix
        assertParseFailure(parser, QUESTION_DESC_LOVE + VALID_ANSWER_TWO,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid question
        assertParseFailure(parser, INVALID_QUESTION_DESC + ANSWER_DESC_ONE + ANSWER_DESC_TWO,
                Question.MESSAGE_QUESTION_CONSTRAINTS);

        // invalid answer
        assertParseFailure(parser, QUESTION_DESC_LOVE + INVALID_ANSWER_DESC,
                Answer.MESSAGE_ANSWER_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_QUESTION_DESC + INVALID_ANSWER_DESC,
                Question.MESSAGE_QUESTION_CONSTRAINTS);

        assertParseFailure(parser, INVALID_ANSWER_DESC + QUESTION_DESC_LOVE,
                Answer.MESSAGE_ANSWER_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + QUESTION_DESC_LOVE + ANSWER_DESC_ONE + ANSWER_DESC_TWO,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPollCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\club\logic\parser\FindCommandParserTest.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.club.logic.commands.FindCommand;
import seedu.club.model.member.FieldContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_findNameValidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), PREFIX_NAME));
        assertParseSuccess(parser, PREFIX_NAME + "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords and prefix
        assertParseSuccess(parser, PREFIX_NAME + " \n  \t Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_findPhoneValidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("123", "321"), PREFIX_PHONE));
        assertParseSuccess(parser, PREFIX_PHONE + "123 321", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREFIX_PHONE + " \n 123 \n \t 321  \t", expectedFindCommand);
    }

    @Test
    public void parse_findEmailValidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("lalala", "blablabla"), PREFIX_EMAIL));
        assertParseSuccess(parser, PREFIX_EMAIL + "lalala blablabla", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREFIX_EMAIL + " \n  \t lalala \n \t blablabla  \t", expectedFindCommand);
    }

    @Test
    public void parse_findMatricNumberValidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("123", "321"), PREFIX_MATRIC_NUMBER));
        assertParseSuccess(parser, PREFIX_MATRIC_NUMBER + "123 321", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREFIX_MATRIC_NUMBER + " \n  \t 123 \n \t 321  \t", expectedFindCommand);
    }

    @Test
    public void parse_findGroupValidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("klan", "vampyr"), PREFIX_GROUP));
        assertParseSuccess(parser, PREFIX_GROUP + "klan vampyr", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREFIX_GROUP + " \n  \t klan \n \t vampyr  \t", expectedFindCommand);
    }

    @Test
    public void parse_findTagValidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("123", "321"), PREFIX_TAG));
        assertParseSuccess(parser, PREFIX_TAG + "123 321", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREFIX_TAG + " \n  123 \n \t 321  \t", expectedFindCommand);
    }

    @Test
    public void parse_findAllValidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("123", "321"), null));
        assertParseSuccess(parser,  "123 321", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 123 \n \t 321  \t", expectedFindCommand);
    }
}
```
###### \java\seedu\club\logic\parser\VoteCommandParserTest.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.club.commons.core.index.Index;
import seedu.club.logic.commands.VoteCommand;

public class VoteCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, VoteCommand.MESSAGE_USAGE);

    private VoteCommandParser parser = new VoteCommandParser();

    @Test
    public void parse_twoIndicesPresent_success() {
        assertParseSuccess(parser, "1 2",
                new VoteCommand(Index.fromOneBased(1), Index.fromOneBased(2)));

        assertParseSuccess(parser, "2 \t   99",
                new VoteCommand(Index.fromOneBased(2), Index.fromOneBased(99)));

        assertParseSuccess(parser, "\t  10   1   \t    ",
                new VoteCommand(Index.fromOneBased(10), Index.fromOneBased(1)));
    }

    @Test
    public void parse_negativeIndices_failure() {
        assertParseFailure(parser, "-1 2", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "1 \t   -2", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "\t  -1   -2   \t    ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_oneIndexMissing_failure() {
        assertParseFailure(parser, "2", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "2 \t   ", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "\t  8   \t    ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_moreThanTwoIndices_failure() {
        assertParseFailure(parser, "2 2  22  11 1", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "2 \t   3    \t 1", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "\t  2 1  11 \t    ", MESSAGE_INVALID_FORMAT);
    }
}
```
###### \java\seedu\club\storage\XmlAdaptedAnswerTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_ANSWER;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_ONE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_TWO;

import org.junit.Test;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.poll.Answer;
import seedu.club.testutil.Assert;

public class XmlAdaptedAnswerTest {

    @Test
    public void toModelType_validAnswer_returnsAnswer() throws Exception {
        Answer answer = new Answer(VALID_ANSWER_ONE);
        XmlAdaptedAnswer xmlAdaptedAnswer = new XmlAdaptedAnswer(answer);
        assertEquals(answer, xmlAdaptedAnswer.toModelType());
    }

    @Test
    public void toModelType_invalidAnswer_throwsIllegalValueException() {
        XmlAdaptedAnswer answer =
                new XmlAdaptedAnswer(INVALID_ANSWER, 5);
        String expectedMessage = Answer.MESSAGE_ANSWER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, answer::toModelType);
    }

    @Test
    public void toModelType_invalidNumberAnswered_throwsIllegalValueException() {
        XmlAdaptedAnswer answer =
                new XmlAdaptedAnswer(VALID_ANSWER_ONE, -10);
        String expectedMessage = Answer.MESSAGE_ANSWER_NUMBER_ANSWERED_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, answer::toModelType);
    }

    @Test
    public void equals() {
        XmlAdaptedAnswer xmlAdaptedAnswerOne = new XmlAdaptedAnswer(VALID_ANSWER_ONE, 5);
        XmlAdaptedAnswer xmlAdaptedAnswerTwo = new XmlAdaptedAnswer(VALID_ANSWER_ONE, 5);
        XmlAdaptedAnswer xmlAdaptedAnswerThree = new XmlAdaptedAnswer(VALID_ANSWER_TWO, 5);
        XmlAdaptedAnswer xmlAdaptedAnswerFour = new XmlAdaptedAnswer(VALID_ANSWER_ONE, 6);

        assertEquals(xmlAdaptedAnswerOne, xmlAdaptedAnswerOne);
        assertEquals(xmlAdaptedAnswerOne, xmlAdaptedAnswerTwo);

        assertNotEquals(xmlAdaptedAnswerOne, xmlAdaptedAnswerThree);
        assertNotEquals(xmlAdaptedAnswerOne, xmlAdaptedAnswerFour);
    }
}
```
###### \java\seedu\club\storage\XmlAdaptedMatricNumberTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_MATRIC_NUMBER;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_BOB;

import org.junit.Test;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.member.MatricNumber;
import seedu.club.testutil.Assert;

public class XmlAdaptedMatricNumberTest {
    @Test
    public void toModelType_validMatricNumber_returnsMatricNumber() throws Exception {
        MatricNumber matricNumber = new MatricNumber(VALID_MATRIC_NUMBER);
        XmlAdaptedMatricNumber xmlAdaptedMatricNumber = new XmlAdaptedMatricNumber(matricNumber);
        assertEquals(matricNumber, xmlAdaptedMatricNumber.toModelType());
    }

    @Test
    public void toModelType_invalidMatricNumber_throwsIllegalValueException() {
        XmlAdaptedMatricNumber matricNumber =
                new XmlAdaptedMatricNumber(INVALID_MATRIC_NUMBER);
        String expectedMessage = MatricNumber.MESSAGE_MATRIC_NUMBER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, matricNumber::toModelType);
    }

    @Test
    public void equals() {
        XmlAdaptedMatricNumber xmlAdaptedMatricNumberAmy = new XmlAdaptedMatricNumber(VALID_MATRIC_NUMBER_AMY);
        XmlAdaptedMatricNumber xmlAdaptedMatricNumberBob = new XmlAdaptedMatricNumber(VALID_MATRIC_NUMBER_BOB);

        assertEquals(xmlAdaptedMatricNumberAmy, xmlAdaptedMatricNumberAmy);

        assertNotEquals(xmlAdaptedMatricNumberAmy, xmlAdaptedMatricNumberBob);
    }
}
```
###### \java\seedu\club\storage\XmlAdaptedPollTest.java
``` java
import static org.junit.Assert.assertEquals;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_ANSWER;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_MATRIC_NUMBER;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_QUESTION;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_ONE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_TWO;
import static seedu.club.logic.commands.CommandTestUtil.VALID_QUESTION_LIFE;
import static seedu.club.storage.XmlAdaptedPoll.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.club.testutil.TypicalPolls.POLL_WHAT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Question;
import seedu.club.testutil.Assert;

public class XmlAdaptedPollTest {

    private static final int VALID_NUMBER_ANSWERED_FIVE = 5;
    private static final int VALID_NUMBER_ANSWERED_TEN = 10;
    private static final int INVALID_NUMBER_ANSWERED_NEGATIVE_FIVE = -5;
    private static final int INVALID_NUMBER_ANSWERED_NEGATIVE_TEN = -10;

    private static final List<XmlAdaptedAnswer> VALID_ANSWERS = POLL_WHAT.getAnswers().stream()
            .map(XmlAdaptedAnswer::new)
            .collect(Collectors.toList());

    private static final List<XmlAdaptedAnswer> INVALID_ANSWERS_EMPTY_ANSWER = Arrays.asList(
            new XmlAdaptedAnswer(INVALID_ANSWER, VALID_NUMBER_ANSWERED_FIVE),
            new XmlAdaptedAnswer(VALID_ANSWER_ONE, VALID_NUMBER_ANSWERED_TEN)
    );
    private static final List<XmlAdaptedAnswer> INVALID_ANSWERS_NEGATIVE_NUMBER_ANSWERED = Arrays.asList(
            new XmlAdaptedAnswer(VALID_ANSWER_ONE, INVALID_NUMBER_ANSWERED_NEGATIVE_TEN),
            new XmlAdaptedAnswer(VALID_ANSWER_TWO, INVALID_NUMBER_ANSWERED_NEGATIVE_FIVE)
    );

    private static final List<XmlAdaptedMatricNumber> VALID_POLLEES_MATRIC_NUMBERS =
            POLL_WHAT.getPolleesMatricNumbers().stream()
            .map(XmlAdaptedMatricNumber::new)
            .collect(Collectors.toList());

    private static final List<XmlAdaptedMatricNumber> INVALID_POLLEES_MATRIC_NUMBERS = Arrays.asList(
            new XmlAdaptedMatricNumber(INVALID_MATRIC_NUMBER), new XmlAdaptedMatricNumber(INVALID_MATRIC_NUMBER)
    );

    @Test
    public void toModelType_validPollDetails_returnsPoll() throws Exception {
        XmlAdaptedPoll poll = new XmlAdaptedPoll(POLL_WHAT);
        assertEquals(POLL_WHAT, poll.toModelType());
    }

    @Test
    public void toModelType_invalidQuestion_throwsIllegalValueException() {
        XmlAdaptedPoll poll =
                new XmlAdaptedPoll(INVALID_QUESTION, VALID_ANSWERS, VALID_POLLEES_MATRIC_NUMBERS);
        String expectedMessage = Question.MESSAGE_QUESTION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, poll::toModelType);
    }

    @Test
    public void toModelType_nullQuestion_throwsIllegalValueException() {
        XmlAdaptedPoll poll = new XmlAdaptedPoll(null, VALID_ANSWERS, VALID_POLLEES_MATRIC_NUMBERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, poll::toModelType);
    }

    @Test
    public void toModelType_invalidAnswersEmptyAnswer_throwsIllegalValueException() {
        XmlAdaptedPoll poll =
                new XmlAdaptedPoll(VALID_QUESTION_LIFE, INVALID_ANSWERS_EMPTY_ANSWER, VALID_POLLEES_MATRIC_NUMBERS);
        String expectedMessage = Answer.MESSAGE_ANSWER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, poll::toModelType);
    }

    @Test
    public void toModelType_invalidAnswerNegativeNumberAnswered_throwsIllegalValueException() {
        XmlAdaptedPoll poll =
                new XmlAdaptedPoll(VALID_QUESTION_LIFE, INVALID_ANSWERS_NEGATIVE_NUMBER_ANSWERED,
                        VALID_POLLEES_MATRIC_NUMBERS);
        String expectedMessage = Answer.MESSAGE_ANSWER_NUMBER_ANSWERED_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, poll::toModelType);
    }

    @Test
    public void toModelType_nullAnswers_throwsIllegalValueException() {
        XmlAdaptedPoll poll = new XmlAdaptedPoll(VALID_QUESTION_LIFE, null, VALID_POLLEES_MATRIC_NUMBERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, poll::toModelType);
    }

    @Test
    public void toModelType_invalidPolleesMatricNumber_throwsIllegalValueException() {
        XmlAdaptedPoll poll =
                new XmlAdaptedPoll(VALID_QUESTION_LIFE, VALID_ANSWERS, INVALID_POLLEES_MATRIC_NUMBERS);
        String expectedMessage = MatricNumber.MESSAGE_MATRIC_NUMBER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, poll::toModelType);
    }
}
```
###### \java\seedu\club\testutil\PollBuilder.java
``` java

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.club.model.member.MatricNumber;
import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.Question;
import seedu.club.model.util.SampleDataUtil;

/**
 * A utility class to help with building poll objects.
 */
public class PollBuilder {

    public static final String DEFAULT_QUESTION = "What is the meaning of life";
    public static final String DEFAULT_ANSWER_ONE = "Fourty Two";
    public static final String DEFAULT_ANSWER_TWO = "Fourty Three";
    public static final String DEFAULT_POLLEE_MATRIC_NUMBER_ONE = "A1234567B";
    public static final String DEFAULT_POLLEE_MATRIC_NUMBER_TWO = "A1234567C";

    private Question question;
    private List<Answer> answers;
    private Set<MatricNumber> polleesMatricNumbers;

    public PollBuilder() {
        question = new Question(DEFAULT_QUESTION);
        answers = Arrays.asList(new Answer(DEFAULT_ANSWER_ONE), new Answer(DEFAULT_ANSWER_TWO));
        polleesMatricNumbers = new HashSet<>();
        polleesMatricNumbers.add(new MatricNumber(DEFAULT_POLLEE_MATRIC_NUMBER_ONE));
        polleesMatricNumbers.add(new MatricNumber(DEFAULT_POLLEE_MATRIC_NUMBER_TWO));
    }

    /**
     * Initializes the PollBuilder with the data of {@code pollToCopy}.
     */
    public PollBuilder(Poll pollToCopy) {
        question = pollToCopy.getQuestion();
        answers = new ArrayList<>(pollToCopy.getAnswers());
        polleesMatricNumbers = new HashSet<>(pollToCopy.getPolleesMatricNumbers());
    }

    /**
     * Sets the {@code question} of the {@code poll} that we are building.
     */
    public PollBuilder withQuestion(String question) {
        this.question = new Question(question);
        return this;
    }

    /**
     * Parses the {@code answers} into a {@code Set<Tag>} and set it to the {@code poll} that we are building.
     */
    public PollBuilder withAnswers(String ... answers) {
        this.answers = SampleDataUtil.getAnswerList(answers);
        return this;
    }

    /**
     * Sets the {@code pollesMatricNumbers} of the {@code poll} that we are building.
     */
    public PollBuilder withPolleesMatricNumbers(String... polleesMatricNumbers) {
        this.polleesMatricNumbers = SampleDataUtil.getMatricNumberSet(polleesMatricNumbers);
        return this;
    }

    /**
     * Sets the {@code pollesMatricNumbers} of the {@code poll} that we are building to null for non-voted new polls
     */
    public PollBuilder withNoPollessMatricNumbers() {
        this.polleesMatricNumbers = null;
        return this;
    }

    public Poll build() {
        return new Poll(question, answers, polleesMatricNumbers);
    }
}
```
###### \java\seedu\club\testutil\PollUtil.java
``` java

import static seedu.club.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_QUESTION;

import seedu.club.logic.commands.AddPollCommand;
import seedu.club.model.poll.Poll;

/**
 * A utility class for poll.
 */
public class PollUtil {

    /**
     * Returns an addPoll command string for adding the {@code poll}.
     */
    public static String getAddPollCommand(Poll poll) {
        return AddPollCommand.COMMAND_WORD + " " + getPollDetails(poll);
    }

    /**
     * Returns the part of command string for the given {@code poll}'s details.
     */
    public static String getPollDetails(Poll poll) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_QUESTION + poll.getQuestion().getValue() + " ");
        poll.getAnswers().stream().forEach(
            answer -> sb.append(PREFIX_ANSWER + answer.getValue() + " ")
        );
        return sb.toString();
    }
}
```
###### \java\seedu\club\testutil\TypicalPolls.java
``` java

import static seedu.club.testutil.TypicalMembers.ALICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.club.model.ClubBook;
import seedu.club.model.member.exceptions.DuplicateMatricNumberException;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.exceptions.DuplicatePollException;

/**
 * A utility class containing a list of {@code poll} objects
 * to be used in tests.
 */
public class TypicalPolls {

    public static final Poll POLL_WHAT = new PollBuilder().withQuestion("What are you?")
            .withAnswers("A vampire", "A zombie")
            .withPolleesMatricNumbers("A1234567A", "A3333333A").build();

    public static final Poll POLL_HOW = new PollBuilder().withQuestion("How are you?")
            .withAnswers("I'm fine", "Not good man")
            .withPolleesMatricNumbers("A3333333A", "A2222222A").build();

    public static final Poll POLL_WHEN = new PollBuilder().withQuestion("When are you?")
            .withAnswers("I don't get it", "Umm..")
            .withPolleesMatricNumbers("A2222222A", "A1234567A").build();

    public static final Poll POLL_WHO = new PollBuilder().withQuestion("Who are you?")
            .withAnswers("Your father", "Your mom")
            .withPolleesMatricNumbers("A3333333A", "A1111111A").build();

    public static final Poll POLL_WHICH = new PollBuilder().withQuestion("Which one is right?")
            .withAnswers("Left", "Right")
            .withPolleesMatricNumbers("A2222222A", "A1234567A").build();

    public static final Poll POLL_LIFE = new PollBuilder().withQuestion("What is the meaning of life?")
            .withAnswers("42", "i dono")
            .withPolleesMatricNumbers("A3333333A", "A1111111A").build();


    private TypicalPolls() {} // prevents instantiation

    /**
     * Returns an {@code ClubBook} with all the typical polls.
     */
    public static ClubBook getTypicalClubBookWithPolls() {
        ClubBook ab = new ClubBook();
        for (Poll poll : getTypicalPolls()) {
            try {
                ab.addPoll(poll);
            } catch (DuplicatePollException e) {
                throw new AssertionError("not possible");
            }
        }
        try {
            // Alice is an exco member
            ab.addMember(ALICE);
        } catch (DuplicateMatricNumberException e) {
            throw new AssertionError("not possible");
        }
        return ab;
    }

    public static List<Poll> getTypicalPolls() {
        return new ArrayList<>(Arrays.asList(POLL_HOW, POLL_WHAT, POLL_WHEN, POLL_WHO));
    }
}
```
###### \java\seedu\club\ui\MemberListPanelTest.java
``` java
    @Test
    public void handleCompressMembersRequestEvent() {
        postNow(COMPRESS_MEMBERS_REQUEST_EVENT);
        assertTrue(memberListPanel.isDisplayingCompressedMembers());
    }

    @Test
    public void handleDecompressMembersRequestEvent() {
        postNow(DECOMPRESS_MEMBERS_REQUEST_EVENT);
        assertFalse(memberListPanel.isDisplayingCompressedMembers());
    }
```
###### \java\systemtests\AddPollCommandSystemTest.java
``` java

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_FOUR;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_LEFT;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_ONE;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_RIGHT;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_THREE;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_TWO;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_VAMPIRE;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_ZOMBIE;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.club.logic.commands.CommandTestUtil.QUESTION_DESC_LIFE;
import static seedu.club.logic.commands.CommandTestUtil.QUESTION_DESC_LOVE;
import static seedu.club.logic.commands.CommandTestUtil.QUESTION_DESC_WHAT;
import static seedu.club.logic.commands.CommandTestUtil.QUESTION_DESC_WHICH;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_ONE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_TWO;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_VAMPIRE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_ZOMBIE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_QUESTION_LOVE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_QUESTION_WHAT;
import static seedu.club.testutil.TypicalPolls.POLL_LIFE;
import static seedu.club.testutil.TypicalPolls.POLL_WHICH;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.commons.core.Messages;
import seedu.club.logic.commands.AddPollCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.RedoCommand;
import seedu.club.logic.commands.UndoCommand;
import seedu.club.model.Model;
import seedu.club.model.member.Member;
import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.Question;
import seedu.club.model.poll.exceptions.DuplicatePollException;
import seedu.club.testutil.PollBuilder;
import seedu.club.testutil.PollUtil;

public class AddPollCommandSystemTest extends ClubBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        ObservableList<Member> memberObservableList = model.getClubBook().getMemberList();
        String logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(0).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);
        model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a poll without tags to a non-empty club book, command with leading spaces and trailing spaces
         * -> added
         */
        Poll toAdd = POLL_WHICH;
        String command = "   " + AddPollCommand.COMMAND_WORD + "  " + QUESTION_DESC_WHICH + "  "
                + ANSWER_DESC_LEFT + " "
                + ANSWER_DESC_RIGHT;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding "What poll" to the list -> "What poll" deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding "What poll" to the list -> "What poll" added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPoll(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a poll answers same as another poll in the club book but different question -> added */
        toAdd = new PollBuilder().withQuestion(VALID_QUESTION_LOVE)
                .withAnswers(VALID_ANSWER_VAMPIRE, VALID_ANSWER_ZOMBIE).build();
        command = AddPollCommand.COMMAND_WORD + QUESTION_DESC_LOVE + ANSWER_DESC_VAMPIRE + ANSWER_DESC_ZOMBIE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a poll with question same as another poll in the club book but with all different answers
         -> added */
        toAdd = new PollBuilder().withQuestion(VALID_QUESTION_WHAT)
                .withAnswers(VALID_ANSWER_ONE, VALID_ANSWER_TWO).build();
        command = AddPollCommand.COMMAND_WORD + QUESTION_DESC_WHAT + ANSWER_DESC_ONE + ANSWER_DESC_TWO;

        /* Case: add a poll with question same as another poll in the club book but with exactly one different answer
         -> added */
        toAdd = new PollBuilder().withQuestion(VALID_QUESTION_WHAT)
                .withAnswers(VALID_ANSWER_ONE, VALID_ANSWER_VAMPIRE).build();
        command = AddPollCommand.COMMAND_WORD + QUESTION_DESC_WHAT + ANSWER_DESC_ONE + ANSWER_DESC_VAMPIRE;

        assertCommandSuccess(command, toAdd);

        /* Case: add a poll with fields in random order -> added */
        toAdd = POLL_LIFE;
        command = AddPollCommand.COMMAND_WORD + ANSWER_DESC_THREE + QUESTION_DESC_LIFE + ANSWER_DESC_FOUR;
        assertCommandSuccess(command, toAdd);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate poll -> rejected */
        command = PollUtil.getAddPollCommand(POLL_WHICH);
        assertCommandFailure(command, AddPollCommand.MESSAGE_DUPLICATE_POLL);

        /* Case: missing question -> rejected */
        command = AddPollCommand.COMMAND_WORD + ANSWER_DESC_FOUR + ANSWER_DESC_THREE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPollCommand.MESSAGE_USAGE));

        /* Case: missing answer -> rejected */
        command = AddPollCommand.COMMAND_WORD + QUESTION_DESC_LIFE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPollCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "addpolls " + PollUtil.getPollDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid question -> rejected */
        command = AddPollCommand.COMMAND_WORD + INVALID_QUESTION_DESC + ANSWER_DESC_FOUR + ANSWER_DESC_VAMPIRE;
        assertCommandFailure(command, Question.MESSAGE_QUESTION_CONSTRAINTS);

        /* Case: invalid answer -> rejected */
        command = AddPollCommand.COMMAND_WORD + QUESTION_DESC_LIFE + INVALID_ANSWER_DESC + ANSWER_DESC_ONE;
        assertCommandFailure(command, Answer.MESSAGE_ANSWER_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddPollCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddPollCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PollListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Poll toAdd) {
        assertCommandSuccess(PollUtil.getAddPollCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(poll)}. Executes {@code command}
     * instead.
     *
     * @see AddPollCommandSystemTest#assertCommandSuccess(Poll)
     */
    private void assertCommandSuccess(String command, Poll toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addPoll(toAdd);
        } catch (DuplicatePollException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddPollCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, poll)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PollListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see AddPollCommandSystemTest#assertCommandSuccess(String, Poll)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PollListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
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
###### \java\systemtests\DeletePollCommandSystemTest.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_POLL_DISPLAYED_INDEX;
import static seedu.club.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.club.logic.commands.DeletePollCommand.MESSAGE_DELETE_POLL_SUCCESS;
import static seedu.club.testutil.TestUtil.getPoll;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_POLL;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.logic.commands.DeletePollCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.member.Member;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.exceptions.PollNotFoundException;

public class DeletePollCommandSystemTest extends ClubBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_POLL_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeletePollCommand.MESSAGE_USAGE);

    @Test
    public void deletePoll() {
        Model expectedModel = getModel();
        String command;
        ObservableList<Member> memberObservableList = expectedModel.getClubBook().getMemberList();
        String logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(0).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);
        expectedModel = new ModelManager(getTypicalClubBook(), new UserPrefs());
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        /* Case: delete the first poll in the list, command with leading spaces and trailing spaces -> deleted */
        command = DeletePollCommand.COMMAND_WORD + " 1";
        Poll deletedPoll = deletePollInModel(expectedModel, INDEX_FIRST_POLL);
        System.out.println("deletedPoll = " + deletedPoll);
        String expectedResultMessage = String.format(MESSAGE_DELETE_POLL_SUCCESS, deletedPoll);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: invalid index (0) -> rejected */
        command = DeletePollCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_POLL_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeletePollCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_POLL_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getClubBook().getPollList().size() + 1);
        command = DeletePollCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_POLL_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeletePollCommand.COMMAND_WORD + " abc",
                MESSAGE_INVALID_DELETE_POLL_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeletePollCommand.COMMAND_WORD + " 1 abc",
                MESSAGE_INVALID_DELETE_POLL_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DeletePoll 1", MESSAGE_UNKNOWN_COMMAND);
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
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Removes the {@code poll} at the specified {@code index} in {@code model}'s club book.
     * @return the removed poll
     */
    private Poll deletePollInModel(Model model, Index index) {
        Poll targetPoll = getPoll(model, index);
        try {
            model.deletePoll(targetPoll);
        } catch (PollNotFoundException pnfe) {
            throw new AssertionError("targetPoll is retrieved from model");
        }
        return targetPoll;
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
