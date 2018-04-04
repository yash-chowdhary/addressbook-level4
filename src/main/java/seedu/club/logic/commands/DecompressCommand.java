package seedu.club.logic.commands;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.core.Messages;
import seedu.club.commons.events.ui.DecompressMembersRequestEvent;

/**
 * Lists all members in the club book to the user.
 */
public class DecompressCommand extends Command {

    public static final String COMMAND_WORD = "decompress";
    public static final String MESSAGE_SUCCESS = "View Decompressed";


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new DecompressMembersRequestEvent());
        if (requireToSignUp()) {
            return new CommandResult(Messages.MESSAGE_REQUIRE_SIGN_UP);
        } else if (requireToLogIn()) {
            return new CommandResult(Messages.MESSAGE_REQUIRE_LOG_IN);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
