//@@author amrut-prabhu
package seedu.club.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.UpdateCurrentlyLogInMemberEvent;
import seedu.club.commons.events.ui.UpdateSelectionPanelEvent;
import seedu.club.logic.commands.exceptions.CommandException;

/**
 * Removes the profile photo of the currently logged in member and sets it to the default image.
 */
public class RemoveProfilePhotoCommand extends Command {

    public static final String COMMAND_WORD = "removepic";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "rmpic", "defaultpic", "delpic")
    );
    public static final String COMMAND_FORMAT = COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes your profile photo.\n";
    public static final String MESSAGE_REMOVE_PROFILE_PHOTO_SUCCESS = "Your profile photo has been removed.";

    @Override
    public CommandResult execute() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        model.removeProfilePhoto();
        EventsCenter.getInstance().post(new UpdateSelectionPanelEvent(model.getLoggedInMember(), null,
                false, null, false));
        EventsCenter.getInstance().post(new UpdateCurrentlyLogInMemberEvent(model.getLoggedInMember()));
        return new CommandResult(MESSAGE_REMOVE_PROFILE_PHOTO_SUCCESS);
    }

}
