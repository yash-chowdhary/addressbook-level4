package seedu.club.logic.commands;

import static seedu.club.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.club.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TO;

import seedu.club.logic.commands.exceptions.CommandException;

/**
 * Sends an email to the desired recipient(s) in the club book.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";

    public static final String COMMAND_USAGE = COMMAND_WORD + ": Sends an email to the desired recipients(s) in the"
            + "club book. "
            + "Parameters: "
            + PREFIX_TO + "TAG OR GROUP"
            + PREFIX_CLIENT + "EMAIL CLIENT"
            + PREFIX_SUBJECT + "SUBJECT"
            + PREFIX_BODY + "BODY";

    public static final String MESSAGE_SENT = "Email sent!";
    public static final String MESSAGE_NOT_SENT = "Please adhere to the command usage.";

    @Override
    public CommandResult execute() throws CommandException {
        return null;
    }
}
