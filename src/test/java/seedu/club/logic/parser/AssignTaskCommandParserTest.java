package seedu.club.logic.parser;
//@@author yash-chowdhary
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_2;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_FOOD;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_2;
import static seedu.club.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DATE_1;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_CONFETTI;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_FOOD;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_STATUS_TO_BEGIN;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_TIME_1;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.club.testutil.TypicalMembers.BOB;

import org.junit.Test;

import seedu.club.logic.commands.AssignTaskCommand;
import seedu.club.model.member.Name;
import seedu.club.model.task.Task;
import seedu.club.testutil.TaskBuilder;

public class AssignTaskCommandParserTest {
    private static final String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AssignTaskCommand.MESSAGE_USAGE);

    private AssignTaskCommandParser parser = new AssignTaskCommandParser();

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

        Name name = BOB.getName();
        assertParseSuccess(parser, " " + TASK_DESCRIPTION_DESC_FOOD + TASK_DATE_DESC_1
                        + TASK_TIME_DESC_1 + NAME_DESC_BOB,
                new AssignTaskCommand(expectedTask, name));
    }

    @Test
    public void parse_fieldsMissing_failure() {
        assertParseFailure(parser, TASK_DESCRIPTION_DESC_FOOD + TASK_DATE_DESC_1
                + VALID_TASK_TIME_1 + NAME_DESC_BOB, expectedMessage);

        assertParseFailure(parser, TASK_DATE_DESC_2 + TASK_TIME_DESC_2 + VALID_TASK_DESCRIPTION_CONFETTI
                + NAME_DESC_BOB,
                expectedMessage);

        assertParseFailure(parser,  TASK_TIME_DESC_1 + TASK_DESCRIPTION_DESC_FOOD + VALID_TASK_DATE_1
                + NAME_DESC_BOB,
                expectedMessage);

        assertParseFailure(parser,  TASK_TIME_DESC_1 + TASK_DESCRIPTION_DESC_FOOD + TASK_DATE_DESC_1
                + VALID_NAME_BOB,
                expectedMessage);

        assertParseFailure(parser, TASK_DATE_DESC_1 + VALID_TASK_DATE_1, expectedMessage);

        assertParseFailure(parser, VALID_TASK_DESCRIPTION_FOOD + VALID_TASK_DATE_1, expectedMessage);

        assertParseFailure(parser, VALID_TASK_DESCRIPTION_FOOD + VALID_TASK_TIME_1, expectedMessage);
    }

}
