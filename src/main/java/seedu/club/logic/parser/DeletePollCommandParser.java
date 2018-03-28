package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.club.commons.core.index.Index;
import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.DeletePollCommand;
import seedu.club.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeletePollCommand object
 */
public class DeletePollCommandParser implements Parser<DeletePollCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePollCommand
     * and returns a DeletePollCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeletePollCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeletePollCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePollCommand.MESSAGE_USAGE));
        }
    }

}
