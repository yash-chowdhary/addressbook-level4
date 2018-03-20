//@@author amrut-prabhu
package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.member.Member;
import seedu.club.model.member.ProfilePhoto;
import seedu.club.model.member.exceptions.DuplicateMemberException;
import seedu.club.model.member.exceptions.MemberNotFoundException;

/**
 * Changes the profile photo of the currently logged in member.
 */
public class ChangeProfilePhotoCommand extends Command {

    public static final String COMMAND_WORD = "changepic";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes your profile photo.\n"
            + "Parameters: PHOTO_FILE_PATH (must be an absolute file path to your new profile photo.)";

    public static final String MESSAGE_DUPLICATE_MEMBER = "This member already exists in the club book";
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
        assert newProfilePhoto.getOriginalPhotoPath() == null : "Photo path should not be null.";

        memberToEdit = model.getLoggedInMember();
        //Defensive programming
        assert this.memberToEdit == null : "ChangeProfilePhotoCommand cannot be called without a logged in member.";

        model.addProfilePhoto(newProfilePhoto.getOriginalPhotoPath(), memberToEdit.getMatricNumber().toString());

        newProfilePhoto.setNewPhotoPath(memberToEdit.getMatricNumber().toString());

        Member editedMember = new Member(memberToEdit.getName(), memberToEdit.getPhone(), memberToEdit.getEmail(),
                memberToEdit.getMatricNumber(), memberToEdit.getGroup(), memberToEdit.getTags(),
                memberToEdit.getUsername(), memberToEdit.getPassword());

        editedMember.setProfilePhoto(newProfilePhoto);

        updateMember(memberToEdit, editedMember);

        return new CommandResult(String.format(MESSAGE_CHANGE_PROFILE_PHOTO_SUCCESS));
    }

    /**
     * Updates the logged in member in Club Connect with new Profile Photo.
     * @param memberToEdit Member whose profile photo is to be changed.
     * @param editedMember Member assigned with new Profile Photo.
     * @throws CommandException when a duplicate member is found.
     */
    private void updateMember(Member memberToEdit, Member editedMember) throws CommandException {
        try {
            model.updateMember(memberToEdit, editedMember);
        } catch (DuplicateMemberException dme) {
            throw new CommandException(MESSAGE_DUPLICATE_MEMBER);
        } catch (MemberNotFoundException mnfe) {
            assert false : "The target member cannot be missing";
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
