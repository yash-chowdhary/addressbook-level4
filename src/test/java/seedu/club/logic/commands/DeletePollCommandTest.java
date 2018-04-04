package seedu.club.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.club.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.club.logic.commands.CommandTestUtil.showPollAtIndex;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_POLL;
import static seedu.club.testutil.TypicalIndexes.INDEX_SECOND_POLL;
import static seedu.club.testutil.TypicalMembers.ALICE;

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
import seedu.club.testutil.TypicalPolls;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeletePollCommand}.
 */
public class DeletePollCommandTest {

    private static final String ALICE_DEFAULT_PASSWORD = "password";
    private Model model = new ModelManager(TypicalPolls.getTypicalClubBook(), new UserPrefs());

    @Before
    public void setUp() {
        model.logsInMember(ALICE.getMatricNumber().toString(), ALICE_DEFAULT_PASSWORD);
        model.updateFilteredPollList(new PollIsRelevantToMemberPredicate(ALICE));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Poll pollToDelete = model.getFilteredPollList().get(INDEX_FIRST_POLL.getZeroBased());
        DeletePollCommand deletePollCommand = prepareCommand(INDEX_FIRST_POLL);

        String expectedMessage = String.format(DeletePollCommand.MESSAGE_DELETE_POLL_SUCCESS, pollToDelete);

        ModelManager expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());
        expectedModel.logsInMember(ALICE.getMatricNumber().toString(), ALICE_DEFAULT_PASSWORD);
        expectedModel.deletePoll(pollToDelete);

        assertCommandSuccess(deletePollCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPollList().size() + 1);
        DeletePollCommand deletePollCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deletePollCommand, model, Messages.MESSAGE_INVALID_POLL_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showPollAtIndex(model, INDEX_FIRST_POLL);

        Poll pollToDelete = model.getFilteredPollList().get(INDEX_FIRST_POLL.getZeroBased());
        DeletePollCommand deletePollCommand = prepareCommand(INDEX_FIRST_POLL);

        String expectedMessage = String.format(DeletePollCommand.MESSAGE_DELETE_POLL_SUCCESS, pollToDelete);

        Model expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());
        expectedModel.logsInMember(ALICE.getMatricNumber().toString(), ALICE_DEFAULT_PASSWORD);
        expectedModel.deletePoll(pollToDelete);
        showNoPoll(expectedModel);

        assertCommandSuccess(deletePollCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPollAtIndex(model, INDEX_FIRST_POLL);

        Index outOfBoundIndex = INDEX_SECOND_POLL;
        // ensures that outOfBoundIndex is still in bounds of club book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getClubBook().getPollList().size());

        DeletePollCommand deletePollCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deletePollCommand, model, Messages.MESSAGE_INVALID_POLL_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
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
