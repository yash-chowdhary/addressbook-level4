package seedu.club.logic.commands;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.core.Messages;
import seedu.club.commons.events.ui.HideResultsRequestEvent;

/**
 * Hides all poll results in the club book to the user.
 */
public class HideResultsCommand extends Command {

    public static final String COMMAND_WORD = "hideresults";
    public static final String MESSAGE_SUCCESS = "Poll results hidden";


    @Override
    public CommandResult execute() {
        if (requireToSignUp()) {
            return new CommandResult(Messages.MESSAGE_REQUIRE_SIGN_UP);
        } else if (requireToLogIn()) {
            return new CommandResult(Messages.MESSAGE_REQUIRE_LOG_IN);
        }
        EventsCenter.getInstance().post(new HideResultsRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
