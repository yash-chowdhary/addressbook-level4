package seedu.club.logic.parser;
//@@author MuhdNurKamal
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.club.commons.core.index.Index;
import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.VoteCommand;
import seedu.club.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new VoteCommand object
 */
public class VoteCommandParser implements Parser<VoteCommand> {

    private static final int INDEX_ARGUMENT_POLL = 0;
    private static final int INDEX_ARGUMENT_ANSWER = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the VoteCommand
     * and returns a VoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VoteCommand parse(String args) throws ParseException {
        try {
            List<Index> indexes = ParserUtil.parseIndices(args);
            if (indexes.size() != 2) {
                throw new IllegalValueException(MESSAGE_INVALID_COMMAND_FORMAT);
            }
            return new VoteCommand(indexes.get(INDEX_ARGUMENT_POLL), indexes.get(INDEX_ARGUMENT_ANSWER));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, VoteCommand.MESSAGE_USAGE));
        }
    }
}
