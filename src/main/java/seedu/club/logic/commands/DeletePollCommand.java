package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.exceptions.PollNotFoundException;

/**
 * Deletes a poll identified using it's last displayed index from the club book.
 */
public class DeletePollCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletepoll";
    public static final String COMMAND_FORMAT = "deletepoll INDEX";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the poll identified by the index number used in the last poll listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_POLL_SUCCESS = "Deleted poll: %1$s";

    private final Index targetIndex;

    private Poll pollToDelete;

    public DeletePollCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(pollToDelete);
        try {
            model.deletePoll(pollToDelete);
        } catch (PollNotFoundException pnfe) {
            throw new AssertionError("The target poll cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_POLL_SUCCESS, pollToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Poll> lastShownList = model.getFilteredPollList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_POLL_DISPLAYED_INDEX);
        }

        pollToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePollCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeletePollCommand) other).targetIndex) // state check
                && Objects.equals(this.pollToDelete, ((DeletePollCommand) other).pollToDelete));
    }
}
