package seedu.club.logic.commands;
//@@author MuhdNurKamal
import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.DecompressMembersRequestEvent;

/**
 * Lists all members in the club book to the user.
 */
public class DecompressCommand extends Command {

    public static final String COMMAND_WORD = "decompress";
    public static final String MESSAGE_SUCCESS = "View Decompressed";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "decomp")
    );


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new DecompressMembersRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
