//@@author amrut-prabhu
package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.club.commons.exceptions.PhotoReadException;
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

    public static final String MESSAGE_INVALID_PHOTO_PATH = "Photo path entered is not valid.";
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
    public CommandResult execute() {
        //Defensive programming
        assert newProfilePhoto.getProfilePhotoPath() == null : "Photo path should not be null.";

        try {
            model.addProfilePhoto(newProfilePhoto.getProfilePhotoPath());
            return new CommandResult(String.format(MESSAGE_CHANGE_PROFILE_PHOTO_SUCCESS));
        } catch (PhotoReadException pe) {
            return new CommandResult(String.format(MESSAGE_INVALID_PHOTO_PATH));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        ChangeProfilePhotoCommand e = (ChangeProfilePhotoCommand) other;
        return this.newProfilePhoto.equals(e.newProfilePhoto);
    }
}
