package seedu.club.logic.commands;
//@@author MuhdNurKamal

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
