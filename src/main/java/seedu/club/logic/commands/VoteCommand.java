package seedu.club.logic.commands;
//@@author MuhdNurKamal
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.exceptions.AnswerNotFoundException;
import seedu.club.model.poll.exceptions.PollNotFoundException;
import seedu.club.model.poll.exceptions.UserAlreadyVotedException;

/**
 * Votes in a poll of an existing poll in the club book.
 */
public class VoteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "vote";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "vpoll")
    );
    public static final String COMMAND_FORMAT = "vote POLL_INDEX ANSWER_INDEX";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds your vote to the poll identified "
            + "by the index number used in the last poll listing. \n"
            + "Parameters: POLL_INDEX (must be a positive integer) ANSWER_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 3 2";

    public static final String MESSAGE_VOTE_SUCCESS = "Your vote has been recorded.\n%s";
    public static final String MESSAGE_VOTE_FAIL_ALREADY_VOTED = "You have already voted in this poll.";

    private final Index pollIndex;
    private final Index answerIndex;

    private Poll pollToVoteIn;

    /**
     * @param pollIndex   of the poll in the filtered poll list to vote in
     * @param answerIndex of the answer of the poll in the filtered poll list to vote in
     */
    public VoteCommand(Index pollIndex, Index answerIndex) {
        requireNonNull(pollIndex);
        requireNonNull(answerIndex);
        this.pollIndex = pollIndex;
        this.answerIndex = answerIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        String voteDetails;
        try {
            voteDetails = model.voteInPoll(pollToVoteIn, answerIndex);
        } catch (UserAlreadyVotedException userAlreadyVotedException) {
            throw new CommandException(MESSAGE_VOTE_FAIL_ALREADY_VOTED);
        } catch (PollNotFoundException questionNotFoundException) {
            throw new AssertionError("The target poll cannot be missing");
        } catch (AnswerNotFoundException answerNotFoundException) {
            throw new AssertionError("The target answer cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_VOTE_SUCCESS, voteDetails));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        List<Poll> lastShownList = model.getFilteredPollList();

        if (pollIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_POLL_DISPLAYED_INDEX);
        }

        pollToVoteIn = lastShownList.get(pollIndex.getZeroBased());

        if (answerIndex.getZeroBased() >= pollToVoteIn.getAnswers().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ANSWER_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VoteCommand)) {
            return false;
        }

        // state check
        VoteCommand e = (VoteCommand) other;
        return pollIndex.equals(e.pollIndex)
                && answerIndex.equals(e.answerIndex);
    }

}
