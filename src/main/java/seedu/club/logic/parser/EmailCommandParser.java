package seedu.club.logic.parser;
//@@author yash-chowdhary
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.club.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.stream.Stream;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.EmailCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.tag.Tag;

/**
 * Parses input arguments and creates a new EmailCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    @Override
    public EmailCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GROUP, PREFIX_TAG, PREFIX_CLIENT,
                PREFIX_SUBJECT, PREFIX_BODY);

        if (arePrefixesPresent(argMultimap, PREFIX_GROUP) && arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.COMMAND_USAGE));
        }
        if (!((arePrefixesPresent(argMultimap, PREFIX_GROUP)) || arePrefixesPresent(argMultimap, PREFIX_TAG))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.COMMAND_USAGE));
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_CLIENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.COMMAND_USAGE));
        }

        try {

            Group group = ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP)).orElse(null);
            Tag tag = ParserUtil.parseOptionalTag(argMultimap.getValue(PREFIX_TAG)).orElse(null);

            Client client = ParserUtil.parseClient(argMultimap.getValue(PREFIX_CLIENT)).get();
            Subject subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT))
                    .orElse(new Subject(Subject.EMPTY_SUBJECT_STRING));
            Body body = ParserUtil.parseBody(argMultimap.getValue(PREFIX_BODY))
                    .orElse(new Body(Body.EMPTY_BODY_STRING));

            return new EmailCommand(group, tag, client, subject, body);
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
