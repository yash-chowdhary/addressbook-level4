package seedu.club.logic.commands;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.CompressMembersRequestEvent;
import seedu.club.commons.events.ui.ViewResultsRequestEvent;

/**
 * Lists all members in the club book to the user.
 */
public class ViewResultsCommand extends Command {

    public static final String COMMAND_WORD = "viewresults";
    public static final String MESSAGE_SUCCESS = "Viewing poll results";


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ViewResultsRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
