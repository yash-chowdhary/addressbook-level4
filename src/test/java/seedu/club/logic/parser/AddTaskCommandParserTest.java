package seedu.club.logic.parser;
//@@author yash-chowdhary
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_FOOD;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DATE_1;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_CONFETTI;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_FOOD;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_STATUS_TO_BEGIN;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_TIME_1;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.club.logic.commands.AddTaskCommand;
import seedu.club.model.task.Task;
import seedu.club.testutil.TaskBuilder;

public class AddTaskCommandParserTest {

    private static final String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddTaskCommand.MESSAGE_USAGE);

    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder()
                .withDescription(VALID_TASK_DESCRIPTION_FOOD)
                .withDate(VALID_TASK_DATE_1)
                .withTime(VALID_TASK_TIME_1)
                .withAssignor("")
                .withAssignee("")
                .withStatus(VALID_TASK_STATUS_TO_BEGIN)
                .build();

        assertParseSuccess(parser, " " + TASK_DESCRIPTION_DESC_FOOD + TASK_DATE_DESC_1
                + TASK_TIME_DESC_1,
                new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_fieldsMissing_failure() {
        assertParseFailure(parser, TASK_DESCRIPTION_DESC_FOOD + TASK_DATE_DESC_1
                + VALID_TASK_TIME_1, expectedMessage);

        assertParseFailure(parser, TASK_DATE_DESC_1 + TASK_TIME_DESC_1 + VALID_TASK_DESCRIPTION_CONFETTI,
                expectedMessage);

        assertParseFailure(parser,  TASK_TIME_DESC_1 + TASK_DESCRIPTION_DESC_FOOD + VALID_TASK_DATE_1,
                expectedMessage);

        assertParseFailure(parser, TASK_DATE_DESC_1 + VALID_TASK_DATE_1, expectedMessage);

        assertParseFailure(parser, VALID_TASK_DESCRIPTION_FOOD + VALID_TASK_DATE_1, expectedMessage);

        assertParseFailure(parser, VALID_TASK_DESCRIPTION_FOOD + VALID_TASK_TIME_1, expectedMessage);
    }
}
