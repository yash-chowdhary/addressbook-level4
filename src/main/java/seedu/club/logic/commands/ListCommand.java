package seedu.club.logic.commands;
import static seedu.club.model.Model.PREDICATE_SHOW_ALL_MEMBERS;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.Messages;

/**
 * Lists all members in the club book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "l")
    );

    public static final String MESSAGE_SUCCESS = "Listed all members";


    @Override
    public CommandResult execute() {
        if (requireToSignUp()) {
            return new CommandResult(Messages.MESSAGE_REQUIRE_SIGN_UP);
        } else if (requireToLogIn()) {
            return new CommandResult(Messages.MESSAGE_REQUIRE_LOG_IN);
        }
        model.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
