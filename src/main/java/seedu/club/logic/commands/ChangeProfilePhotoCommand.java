//@@author amrut-prabhu
package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.UpdateCurrentlyLogInMemberEvent;
import seedu.club.commons.events.ui.UpdateSelectionPanelEvent;
import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.member.ProfilePhoto;

/**
 * Changes the profile photo of the currently logged in member.
 */
public class ChangeProfilePhotoCommand extends Command {

    public static final String COMMAND_WORD = "changepic";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "pic", "profilepic")
    );
    public static final String COMMAND_FORMAT = COMMAND_WORD + " PATH";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes your profile photo.\n"
            + "Parameters: PHOTO_FILE_PATH (must be an absolute file path to your new profile photo)\n"
            + "Example: " + COMMAND_WORD
            + " C:/Users/John Doe/Desktop/john_doe.jpg";

    public static final String MESSAGE_INVALID_PHOTO_PATH = "Invalid photo path: %1$s";
    public static final String MESSAGE_CHANGE_PROFILE_PHOTO_SUCCESS = "Your profile photo has been changed to: %1$s";

    private ProfilePhoto profilePhoto;

    /**
     * @param profilePhoto of the member
     */
    public ChangeProfilePhotoCommand(ProfilePhoto profilePhoto) {
        requireNonNull(profilePhoto);
        this.profilePhoto = profilePhoto;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert profilePhoto.getPhotoPath() != null : "Photo path should not be null.";
        requireToSignUp();
        requireToLogIn();
        try {
            model.addProfilePhoto(profilePhoto.getPhotoPath());
            EventsCenter.getInstance().post(new UpdateSelectionPanelEvent(model.getLoggedInMember(),
                    model.getLoggedInMember(), false, null, false));
            EventsCenter.getInstance().post(new UpdateCurrentlyLogInMemberEvent(model.getLoggedInMember()));
            return new CommandResult(String.format(MESSAGE_CHANGE_PROFILE_PHOTO_SUCCESS, profilePhoto.getPhotoPath()));
        } catch (PhotoReadException pre) {
            throw new CommandException(String.format(MESSAGE_INVALID_PHOTO_PATH, profilePhoto.getPhotoPath()));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ChangeProfilePhotoCommand)) {
            return false;
        }

        // state check
        ChangeProfilePhotoCommand e = (ChangeProfilePhotoCommand) other;
        return this.profilePhoto.equals(e.profilePhoto);
    }
}
