package seedu.club.logic.parser;
//@@author yash-chowdhary
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_DATE_DESC_PASSED;
import static seedu.club.logic.commands.CommandTestUtil.MATRIC_NUMBER_DESC_BOB;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_2;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_FOOD;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_2;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_BOB;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DATE_1;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_CONFETTI;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_FOOD;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_STATUS_TO_BEGIN;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_TIME_1;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.club.testutil.TypicalMembers.BOB;

import org.junit.Test;

import seedu.club.commons.core.Messages;
import seedu.club.logic.commands.AssignTaskCommand;
import seedu.club.model.member.MatricNumber;
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

        MatricNumber matricNumber = BOB.getMatricNumber();
        assertParseSuccess(parser, " " + TASK_DESCRIPTION_DESC_FOOD + TASK_DATE_DESC_1
                        + TASK_TIME_DESC_1 + MATRIC_NUMBER_DESC_BOB,
                new AssignTaskCommand(expectedTask, matricNumber));
    }

    @Test
    public void parse_dateAlreadyPassed_throwsException() {
        String expectedMessage = Messages.MESSAGE_DATE_ALREADY_PASSED;
        assertParseFailure(parser, " " + TASK_DESCRIPTION_DESC_FOOD + INVALID_DATE_DESC_PASSED
                + TASK_TIME_DESC_1 + MATRIC_NUMBER_DESC_BOB, expectedMessage);

    }

    @Test
    public void parse_fieldsMissing_failure() {
        // missing time prefix
        assertParseFailure(parser, TASK_DESCRIPTION_DESC_FOOD + TASK_DATE_DESC_1
                + VALID_TASK_TIME_1 + MATRIC_NUMBER_DESC_BOB, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, TASK_DATE_DESC_2 + TASK_TIME_DESC_2 + VALID_TASK_DESCRIPTION_CONFETTI
                + MATRIC_NUMBER_DESC_BOB,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser,  TASK_TIME_DESC_1 + TASK_DESCRIPTION_DESC_FOOD + VALID_TASK_DATE_1
                + MATRIC_NUMBER_DESC_BOB,
                expectedMessage);

        // missing matric number prefix
        assertParseFailure(parser,  TASK_TIME_DESC_1 + TASK_DESCRIPTION_DESC_FOOD + TASK_DATE_DESC_1
                + VALID_MATRIC_NUMBER_BOB,
                expectedMessage);

        /*------------------------------------MISSING PARAMETERS------------------------------------------------------*/

        assertParseFailure(parser, TASK_DATE_DESC_1 + VALID_TASK_DATE_1, expectedMessage);

        assertParseFailure(parser, VALID_TASK_DESCRIPTION_FOOD + VALID_TASK_DATE_1, expectedMessage);

        assertParseFailure(parser, VALID_TASK_DESCRIPTION_FOOD + VALID_TASK_TIME_1, expectedMessage);
    }

}
