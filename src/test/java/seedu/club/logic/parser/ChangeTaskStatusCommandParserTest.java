package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.club.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.Test;

import seedu.club.logic.commands.ChangeTaskStatusCommand;
import seedu.club.model.task.Status;

public class ChangeTaskStatusCommandParserTest {
    private ChangeTaskStatusCommandParser parser = new ChangeTaskStatusCommandParser();

    @Test
    public void parse_validArgs_returnsChangeTaskStatusCommand() {
        assertParseSuccess(parser, " 1 "
                        + " " + PREFIX_STATUS + Status.IN_PROGRESS_STATUS,
                new ChangeTaskStatusCommand(INDEX_FIRST_TASK, new Status(Status.IN_PROGRESS_STATUS)));
        assertParseSuccess(parser, " 2 "
                        + " " + PREFIX_STATUS + Status.COMPLETED_STATUS,
                new ChangeTaskStatusCommand(INDEX_SECOND_TASK, new Status(Status.COMPLETED_STATUS)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeTaskStatusCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " one",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeTaskStatusCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, " -1" + PREFIX_STATUS + Status.COMPLETED_STATUS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeTaskStatusCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " -1" + PREFIX_STATUS + Status.IN_PROGRESS_STATUS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeTaskStatusCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStatus_throwsParseException() {
        // no such status
        assertParseFailure(parser, " 1 st/invalid status",
                Status.MESSAGE_INVALID_STATUS);
        // no space between the words
        assertParseFailure(parser, " 1 st/inprogress",
                Status.MESSAGE_INVALID_STATUS);
    }
}
