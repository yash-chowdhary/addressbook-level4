package seedu.club.logic.parser;
//@@author yash-chowdhary
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TIME;

import java.text.SimpleDateFormat;
import java.util.stream.Stream;

import seedu.club.commons.core.Messages;
import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.AssignTaskCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.task.Date;
import seedu.club.model.task.Description;
import seedu.club.model.task.Task;
import seedu.club.model.task.Time;

/**
 * Parses input arguments and creates a new AssignTaskCommand object
 */
public class AssignTaskCommandParser implements Parser<AssignTaskCommand> {

    @Override
    public AssignTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_TIME, PREFIX_DATE, PREFIX_MATRIC_NUMBER);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_TIME, PREFIX_DATE, PREFIX_MATRIC_NUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE));
        }

        try {
            Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
            MatricNumber matricNumber = ParserUtil.parseMatricNumber(argMultimap.getValue(PREFIX_MATRIC_NUMBER).get());

            long currentTimeMillis = System.currentTimeMillis();
            String enteredDateString = date.getDate() + " " + time.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            long enteredDateMillis = Long.MIN_VALUE;

            try {
                java.util.Date enteredDate = formatter.parse(enteredDateString);
                enteredDateMillis = enteredDate.getTime();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            if (enteredDateMillis < currentTimeMillis) {
                throw new IllegalValueException(Messages.MESSAGE_DATE_ALREADY_PASSED);
            }

            Task newTask = new Task(description, time, date);

            return new AssignTaskCommand(newTask, matricNumber);
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
