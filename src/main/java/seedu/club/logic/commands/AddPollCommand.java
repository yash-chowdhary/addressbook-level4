package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.club.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_QUESTION;

import seedu.club.commons.core.Messages;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.exceptions.DuplicatePollException;

/**
 * Adds a poll to the club book.
 */
public class AddPollCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addpoll";
    public static final String COMMAND_FORMAT = "addpoll q/ ans/ [ans/...]";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a poll to the club book. "
            + "Parameters: "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_ANSWER + "ANSWER...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_QUESTION + "What is love? "
            + PREFIX_ANSWER + "Baby don't hurt me "
            + PREFIX_ANSWER + "42 ";

    public static final String MESSAGE_SUCCESS = "New poll added: %1$s";
    public static final String MESSAGE_DUPLICATE_POLL = "This poll already exists in the club book";

    private final Poll toAdd;

    /**
     * Creates an AddPollCommand to add the specified {@code poll}
     */
    public AddPollCommand(Poll poll) {
        requireNonNull(poll);
        toAdd = poll;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            if (requireToSignUp()) {
                return new CommandResult(Messages.MESSAGE_REQUIRE_SIGN_UP);
            } else if (requireToLogIn()) {
                return new CommandResult(Messages.MESSAGE_REQUIRE_LOG_IN);
            }
            model.addPoll(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicatePollException e) {
            throw new CommandException(MESSAGE_DUPLICATE_POLL);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPollCommand // instanceof handles nulls
                && toAdd.equals(((AddPollCommand) other).toAdd));
    }
}
