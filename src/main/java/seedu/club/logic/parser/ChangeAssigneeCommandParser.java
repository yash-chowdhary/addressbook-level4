package seedu.club.logic.parser;
//@@author yash-chowdhary
import static java.util.Objects.requireNonNull;
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;

import java.util.stream.Stream;

import seedu.club.commons.core.index.Index;
import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.ChangeAssigneeCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.task.Assignee;

/**
 * arses input arguments and creates a new ChangeAssignee object
 */
public class ChangeAssigneeCommandParser implements Parser<ChangeAssigneeCommand> {
    @Override
    public ChangeAssigneeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MATRIC_NUMBER);

        if (!arePrefixesPresent(argMultimap, PREFIX_MATRIC_NUMBER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeAssigneeCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeAssigneeCommand.MESSAGE_USAGE));
        }

        try {
            MatricNumber matricNumber = ParserUtil.parseMatricNumber(argMultimap.getValue(PREFIX_MATRIC_NUMBER)).get();
            Assignee assignee = new Assignee(matricNumber.toString());
            return new ChangeAssigneeCommand(index, assignee);
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
