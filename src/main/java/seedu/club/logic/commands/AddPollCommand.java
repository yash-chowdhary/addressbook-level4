package seedu.club.logic.commands;
//@@author MuhdNurKamal
import static java.util.Objects.requireNonNull;
import static seedu.club.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_QUESTION;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.exceptions.DuplicatePollException;

/**
 * Adds a poll to the club book.
 */
public class AddPollCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addpoll";
    public static final String COMMAND_FORMAT = "addpoll q/ ans/ [ans/...]";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "addp", "poll")
    );

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a poll for members to respond to on Club Connect.\n"
            + "Parameters: "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_ANSWER + "ANSWER...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_QUESTION + "When should the annual Appreciation Dinner be held? "
            + PREFIX_ANSWER + "April 13 "
            + PREFIX_ANSWER + "April 14 "
            + PREFIX_ANSWER + "April 21 ";

    public static final String MESSAGE_SUCCESS = "New poll added:\n%1$s";
    public static final String MESSAGE_DUPLICATE_POLL = "This poll already exists in Club Connect.";

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
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        try {
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
