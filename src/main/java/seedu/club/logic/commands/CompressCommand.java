package seedu.club.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.core.Messages;
import seedu.club.commons.events.ui.CompressMembersRequestEvent;

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
    public CommandResult execute() {
        EventsCenter.getInstance().post(new CompressMembersRequestEvent());
        if (requireToSignUp()) {
            return new CommandResult(Messages.MESSAGE_REQUIRE_SIGN_UP);
        } else if (requireToLogIn()) {
            return new CommandResult(Messages.MESSAGE_REQUIRE_LOG_IN);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
