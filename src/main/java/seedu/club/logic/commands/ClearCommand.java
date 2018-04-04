package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.club.commons.core.Messages;
import seedu.club.model.ClubBook;

/**
 * Clears the club book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Club Connect data has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        if (requireToSignUp()) {
            return new CommandResult(Messages.MESSAGE_REQUIRE_SIGN_UP);
        } else if (requireToLogIn()) {
            return new CommandResult(Messages.MESSAGE_REQUIRE_LOG_IN);
        }
        model.resetData(new ClubBook());
        model.clearClubBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
