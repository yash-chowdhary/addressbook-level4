//@@author amrut-prabhu
package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.member.ProfilePhoto;

/**
 * Changes the profile photo of the currently logged in member.
 */
public class ChangeProfilePhotoCommand extends Command {

    public static final String COMMAND_WORD = "changepic";
    public static final String COMMAND_FORMAT = "changepic PATH";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes your profile photo. "
            + "Parameters: PHOTO_FILE_PATH (must be an absolute file path to your new profile photo)\n"
            + "Example: " + COMMAND_WORD + " C:/Users/John Doe/Desktop/john_doe.jpg";

    public static final String MESSAGE_INVALID_PHOTO_PATH = "Invalid photo path: %1$s";
    public static final String MESSAGE_CHANGE_PROFILE_PHOTO_SUCCESS =
            "Your profile photo has been changed successfully.";

    private ProfilePhoto newProfilePhoto;

    /**
     * @param profilePhoto of the member
     */
    public ChangeProfilePhotoCommand(ProfilePhoto profilePhoto) {
        requireNonNull(profilePhoto);
        this.newProfilePhoto = profilePhoto;
    }

    @Override
    public CommandResult execute() throws CommandException {
        //Defensive programming
        assert newProfilePhoto.getProfilePhotoPath() != null : "Photo path should not be null.";

        try {
            model.addProfilePhoto(newProfilePhoto.getProfilePhotoPath());
            return new CommandResult(String.format(MESSAGE_CHANGE_PROFILE_PHOTO_SUCCESS));
        } catch (PhotoReadException pre) {
            throw new CommandException(String.format(MESSAGE_INVALID_PHOTO_PATH,
                    newProfilePhoto.getProfilePhotoPath()));
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
        return this.newProfilePhoto.equals(e.newProfilePhoto);
    }
}
