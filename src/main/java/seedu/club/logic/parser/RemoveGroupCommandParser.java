package seedu.club.logic.parser;
//@@author yash-chowdhary
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.stream.Stream;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.RemoveGroupCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.group.Group;

/**
 * Parses input arguments and creates a new RemoveGroupCommand object
 */
public class RemoveGroupCommandParser implements Parser<RemoveGroupCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemoveGroupCommand
     * and returns an RemoveGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_GROUP)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveGroupCommand.MESSAGE_USAGE));
        }

        try {
            Group group = ParserUtil.parseGroup(argumentMultimap.getValue(PREFIX_GROUP).get());

            return new RemoveGroupCommand(group);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty values in the given
     * {@code ArgumentMultimap}
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
