package seedu.club.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.ViewResultsRequestEvent;
import seedu.club.logic.commands.exceptions.CommandException;

/**
 * Shows all poll results in the club book to the user.
 */
public class ViewResultsCommand extends Command {

    public static final String COMMAND_WORD = "viewresults";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "viewres")
    );
    public static final String MESSAGE_SUCCESS = "Poll results displayed.";


    @Override
    public CommandResult execute() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        EventsCenter.getInstance().post(new ViewResultsRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
