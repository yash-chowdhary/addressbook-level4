package seedu.club.logic.parser;
//@@author yash-chowdhary
import static java.util.Objects.requireNonNull;
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;

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
}
