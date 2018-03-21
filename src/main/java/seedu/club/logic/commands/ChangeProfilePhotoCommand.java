//@@author amrut-prabhu
package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.member.Member;
import seedu.club.model.member.ProfilePhoto;

/**
 * Changes the profile photo of the currently logged in member.
 */
public class ChangeProfilePhotoCommand extends Command {

    public static final String COMMAND_WORD = "changepic";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes your profile photo.\n"
            + "Parameters: PHOTO_FILE_PATH (must be an absolute file path to your new profile photo)\n"
            + "Example: " + COMMAND_WORD + " C:/Users/John Doe/Desktop/john_doe.jpg";

    public static final String MESSAGE_INVALID_PHOTO_PATH = "Photo path entered is not valid.";
    public static final String MESSAGE_CHANGE_PROFILE_PHOTO_SUCCESS =
            "Your profile photo has been changed successfully.";

    private Member memberToEdit;

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
        assert newProfilePhoto.getProfilePhotoPath() == null : "Photo path should not be null.";

        memberToEdit = model.getLoggedInMember();
        //Defensive programming
        assert this.memberToEdit == null : "ChangeProfilePhotoCommand cannot be called without a logged in member.";

        Boolean isPhotoChanged = model.addProfilePhoto(newProfilePhoto.getProfilePhotoPath(),
                memberToEdit.getMatricNumber().toString());

        if (isPhotoChanged) {
            return new CommandResult(String.format(MESSAGE_CHANGE_PROFILE_PHOTO_SUCCESS));
        } else {
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
        return memberToEdit.equals(e.memberToEdit) && this.newProfilePhoto.equals(e.newProfilePhoto);
    }
}
