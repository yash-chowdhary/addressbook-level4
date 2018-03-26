package seedu.club.logic.parser.exceptions;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PROFILE_PHOTO;
import static seedu.club.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.club.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.AddCommand;
import seedu.club.logic.commands.AddPollCommand;
import seedu.club.logic.parser.ArgumentMultimap;
import seedu.club.logic.parser.ArgumentTokenizer;
import seedu.club.logic.parser.Parser;
import seedu.club.logic.parser.ParserUtil;
import seedu.club.logic.parser.Prefix;
import seedu.club.model.group.Group;
import seedu.club.model.member.Email;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.member.Password;
import seedu.club.model.member.Phone;
import seedu.club.model.member.Username;
import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.Question;
import seedu.club.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddPollCommand object
 */
public class AddPollCommandParser implements Parser<AddPollCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPollCommand
     * and returns an AddPollCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPollCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUESTION, PREFIX_ANSWER);

        if (!arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_ANSWER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPollCommand.MESSAGE_USAGE));
        }

        try {
            Question question = ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION)).get();
            List<Answer> answerList = ParserUtil.parseAnswers(argMultimap.getAllValues(PREFIX_ANSWER));

            Poll poll = new Poll(question, answerList);

            return new AddPollCommand(poll);
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
