package seedu.club.logic.commands;
//@@author MuhdNurKamal
import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.HideResultsRequestEvent;
import seedu.club.logic.commands.exceptions.CommandException;

/**
 * Hides all poll results in the club book to the user.
 */
public class HideResultsCommand extends Command {

    public static final String COMMAND_WORD = "hideresults";
    public static final String MESSAGE_SUCCESS = "Poll results have been hidden.";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "hideres")
    );


    @Override
    public CommandResult execute() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        EventsCenter.getInstance().post(new HideResultsRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
