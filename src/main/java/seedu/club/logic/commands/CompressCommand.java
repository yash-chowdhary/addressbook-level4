package seedu.club.logic.commands;
//@@author MuhdNurKamal
import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.CompressMembersRequestEvent;
import seedu.club.logic.commands.exceptions.CommandException;

/**
 * Lists all members in the club book to the user.
 */
public class CompressCommand extends Command {

    public static final String COMMAND_WORD = "compress";
    public static final String MESSAGE_SUCCESS = "Member list view compressed.";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "comp")
    );


    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new CompressMembersRequestEvent());
        requireToSignUp();
        requireToLogIn();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
