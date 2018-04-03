package seedu.club.logic.parser;

//@@author yash-chowdhary

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.stream.Stream;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.AddTaskCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.task.Date;
import seedu.club.model.task.Description;
import seedu.club.model.task.Task;
import seedu.club.model.task.Time;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    @Override
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        try {
            Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());

            Task newTask = new Task(description, time, date);

            return new AddTaskCommand(newTask);
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
