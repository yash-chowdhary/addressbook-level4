package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.club.logic.commands.FindCommand;
import seedu.club.model.member.FieldContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_findNameValidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), null));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_findPhoneValidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("123", "321"), PREFIX_PHONE));
        assertParseSuccess(parser, PREFIX_PHONE + " 123 321", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREFIX_PHONE + " \n 123 \n \t 321  \t", expectedFindCommand);
    }

    @Test
    public void parse_findEmailValidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("lalala", "blablabla"), PREFIX_EMAIL));
        assertParseSuccess(parser, PREFIX_EMAIL + " lalala blablabla", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREFIX_EMAIL + " \n lalala \n \t blablabla  \t", expectedFindCommand);
    }

    @Test
    public void parse_findMatricNumberValidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("123", "321"), PREFIX_MATRIC_NUMBER));
        assertParseSuccess(parser, PREFIX_MATRIC_NUMBER + " 123 321", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREFIX_MATRIC_NUMBER + " \n 123 \n \t 321  \t", expectedFindCommand);
    }

    @Test
    public void parse_findGroupValidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("klan", "vampyr"), PREFIX_GROUP));
        assertParseSuccess(parser, PREFIX_GROUP + " klan vampyr", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREFIX_GROUP + " \n klan \n \t vampyr  \t", expectedFindCommand);
    }

    @Test
    public void parse_findTagValidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("123", "321"), PREFIX_TAG));
        assertParseSuccess(parser, PREFIX_TAG + " 123 321", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREFIX_TAG + " \n 123 \n \t 321  \t", expectedFindCommand);
    }

    @Test
    public void parse_findAllValidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("123", "321"), null));
        assertParseSuccess(parser,  " 123 321", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 123 \n \t 321  \t", expectedFindCommand);
    }
}
