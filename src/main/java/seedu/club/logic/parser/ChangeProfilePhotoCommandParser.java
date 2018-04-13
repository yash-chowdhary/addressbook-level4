//@@author amrut-prabhu
package seedu.club.logic.parser;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.ChangeProfilePhotoCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.member.ProfilePhoto;

/**
 * Parses input arguments and creates a new ChangeProfilePhotoCommand object
 */
public class ChangeProfilePhotoCommandParser implements Parser<ChangeProfilePhotoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangeProfilePhotoCommand
     * and returns a ChangeProfilePhotoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeProfilePhotoCommand parse(String args) throws ParseException {

        try {
            ProfilePhoto profilePhoto = ParserUtil.parseProfilePhoto(args);
            return new ChangeProfilePhotoCommand(profilePhoto);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

}
