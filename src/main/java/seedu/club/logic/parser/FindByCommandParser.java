package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.club.logic.commands.FindByCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.member.FieldContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindByCommand object
 */
public class FindByCommandParser implements Parser<FindByCommand> {

    private static Prefix[] prefixes = {PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE,
        PREFIX_MATRIC_NUMBER, PREFIX_GROUP, PREFIX_TAG};

    /**
     * Parses the given {@code String} of arguments in the context of the FindByCommand
     * and returns an FindByCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindByCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByCommand.MESSAGE_USAGE));
        }

        String[] findArgs = trimmedArgs.split("\\s+");

        if (findArgs.length < 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByCommand.MESSAGE_USAGE));
        } else if (findArgs.length > 1) {
            for (Prefix prefix : prefixes) {
                if (findArgs[0].toLowerCase().equals(prefix.toString())) {
                    return new FindByCommand(new FieldContainsKeywordsPredicate(
                            Arrays.asList(findArgs).subList(1, findArgs.length), findArgs[0]));
                }
            }
        }
        return new FindByCommand(new FieldContainsKeywordsPredicate(
                Arrays.asList(findArgs), null));
    }
}
