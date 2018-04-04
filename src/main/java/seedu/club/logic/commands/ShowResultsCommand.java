package seedu.club.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.core.Messages;
import seedu.club.commons.events.ui.ShowResultsRequestEvent;

/**
 * Shows all poll results in the club book to the user.
 */
public class ShowResultsCommand extends Command {

    public static final String COMMAND_WORD = "showresults";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "showres")
    );
    public static final String MESSAGE_SUCCESS = "Showing poll results";


    @Override
    public CommandResult execute() {
        if (requireToSignUp()) {
            return new CommandResult(Messages.MESSAGE_REQUIRE_SIGN_UP);
        } else if (requireToLogIn()) {
            return new CommandResult(Messages.MESSAGE_REQUIRE_LOG_IN);
        }
        EventsCenter.getInstance().post(new ShowResultsRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
