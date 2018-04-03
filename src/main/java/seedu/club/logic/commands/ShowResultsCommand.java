package seedu.club.logic.commands;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.ShowResultsRequestEvent;

/**
 * Shows all poll results in the club book to the user.
 */
public class ShowResultsCommand extends Command {

    public static final String COMMAND_WORD = "showresults";
    public static final String MESSAGE_SUCCESS = "Showing poll results";


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowResultsRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
