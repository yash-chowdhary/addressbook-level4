package seedu.club.logic.parser;
//@@author yash-chowdhary
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_MATRIC_NUMBER;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_BOB;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.club.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.Test;

import seedu.club.logic.commands.ChangeAssigneeCommand;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.task.Assignee;

public class ChangeAssigneeCommandParserTest {
    private ChangeAssigneeCommandParser parser = new ChangeAssigneeCommandParser();

    @Test
    public void parse_validArgs_returnsChangeAssigneeCommand() {
        assertParseSuccess(parser, " 1 "
                        + " " + PREFIX_MATRIC_NUMBER + VALID_MATRIC_NUMBER,
                new ChangeAssigneeCommand(INDEX_FIRST_TASK, new Assignee(VALID_MATRIC_NUMBER)));
        assertParseSuccess(parser, " 2 "
                        + " " + PREFIX_MATRIC_NUMBER + VALID_MATRIC_NUMBER_BOB,
                new ChangeAssigneeCommand(INDEX_SECOND_TASK, new Assignee(VALID_MATRIC_NUMBER_BOB)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeAssigneeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " one",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeAssigneeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, " -1" + PREFIX_MATRIC_NUMBER + VALID_MATRIC_NUMBER_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeAssigneeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 0" + PREFIX_MATRIC_NUMBER + VALID_MATRIC_NUMBER_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeAssigneeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStatus_throwsParseException() {
        // invalid matric number
        assertParseFailure(parser, " 1 " + PREFIX_MATRIC_NUMBER + INVALID_MATRIC_NUMBER,
                MatricNumber.MESSAGE_MATRIC_NUMBER_CONSTRAINTS);
    }

}
