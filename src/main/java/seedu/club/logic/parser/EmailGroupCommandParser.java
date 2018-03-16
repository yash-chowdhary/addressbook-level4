package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.club.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TO;

import java.util.stream.Stream;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.EmailGroupCommand;
import seedu.club.logic.commands.email.Body;
import seedu.club.logic.commands.email.Client;
import seedu.club.logic.commands.email.Subject;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.group.Group;

/**
 * Parses input arguments and creates a new EmailGroupCommand object
 */
public class EmailGroupCommandParser implements Parser<EmailGroupCommand> {

    @Override
    public EmailGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TO, PREFIX_CLIENT,
                PREFIX_SUBJECT, PREFIX_BODY);

        if (!arePrefixesPresent(argMultimap, PREFIX_TO, PREFIX_CLIENT, PREFIX_SUBJECT, PREFIX_BODY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailGroupCommand.COMMAND_USAGE));
        }

        try {
            Group group = ParserUtil.parseGroup(argMultimap.getValue(PREFIX_TO)).get();
            Client client = ParserUtil.parseClient(argMultimap.getValue(PREFIX_CLIENT)).get();
            Subject subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT)).get();
            Body body = ParserUtil.parseBody(argMultimap.getValue(PREFIX_BODY)).get();

            return new EmailGroupCommand(group, client, subject, body);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
