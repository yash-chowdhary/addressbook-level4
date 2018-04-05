package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.ClubBook;

/**
 * Clears the club book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Club Connect has been cleared.";
    public static final String MESSAGE_FAILURE = "Action to Clear data in Club Connect has been cancelled";
    public static final String MESSAGE_CONFRIMATION = "Confirm clearing all data in Club Connect?"
            + " Type 'clear Y' to confirm and 'clear N' to cancel."
            + " \nWARNING: THIS IS NOT A UNDOABLE COMMAND";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "c", "erase")
    );
    private String args;

    public ClearCommand() {
    }

    public ClearCommand(String args) {
        this.args = args;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        requireToSignUp();
        requireToLogIn();
        if (!model.getClearConfirmation()) {
            model.setClearConfirmation(true);
            return new CommandResult(MESSAGE_CONFRIMATION);
        } else {
            if (args.equals(" Y")) {
                model.resetData(new ClubBook());
                model.clearClubBook();
                return new CommandResult(MESSAGE_SUCCESS);
            } else {
                model.setClearConfirmation(false);
                return new CommandResult(MESSAGE_FAILURE + args);
            }
        }
    }
}
