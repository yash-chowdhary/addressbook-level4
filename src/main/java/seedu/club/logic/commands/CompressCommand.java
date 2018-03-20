package seedu.club.logic.commands;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.CompressMembersRequestEvent;

/**
 * Lists all members in the club book to the user.
 */
public class CompressCommand extends Command {

    public static final String COMMAND_WORD = "compress";
    public static final String MESSAGE_SUCCESS = "View Compressed";


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new CompressMembersRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
