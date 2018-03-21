//@@author amrut-prabhu
package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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

        String path = args.trim();

        if (path.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeProfilePhotoCommand.MESSAGE_USAGE));
        }

        return new ChangeProfilePhotoCommand(new ProfilePhoto(path));
    }
}

