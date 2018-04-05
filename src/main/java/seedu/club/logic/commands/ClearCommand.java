package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.ClubBook;

/**
 * Clears the club book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Club Connect has been cleared.";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "c", "erase")
    );


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        requireToSignUp();
        requireToLogIn();
        model.resetData(new ClubBook());
        model.clearClubBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
