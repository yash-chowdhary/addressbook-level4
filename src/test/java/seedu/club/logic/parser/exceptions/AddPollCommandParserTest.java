package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_FOUR;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_ONE;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_THREE;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_TWO;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.club.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.club.logic.commands.CommandTestUtil.QUESTION_DESC_LIFE;
import static seedu.club.logic.commands.CommandTestUtil.QUESTION_DESC_LOVE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_FOUR;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_ONE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_THREE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_TWO;
import static seedu.club.logic.commands.CommandTestUtil.VALID_QUESTION_LIFE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_QUESTION_LOVE;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.club.logic.commands.AddPollCommand;
import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.Question;
import seedu.club.testutil.PollBuilder;

public class AddPollCommandParserTest {
    private AddPollCommandParser parser = new AddPollCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Poll expectedPoll = new PollBuilder().withQuestion(VALID_QUESTION_LIFE)
                .withAnswers(VALID_ANSWER_ONE, VALID_ANSWER_THREE).build();

        // whitespace only preamble
        assertParseSuccess(parser, QUESTION_DESC_LIFE + ANSWER_DESC_ONE + ANSWER_DESC_THREE,
                new AddPollCommand(expectedPoll));

        // multiple questions - last question accepted
        assertParseSuccess(parser, QUESTION_DESC_LOVE + QUESTION_DESC_LIFE
                + ANSWER_DESC_ONE + ANSWER_DESC_THREE, new AddPollCommand(expectedPoll));

        // multiple answers - all accepted
        Poll expectedPollMultipleAnswers = new PollBuilder().withQuestion(VALID_QUESTION_LOVE)
                .withAnswers(VALID_ANSWER_ONE, VALID_ANSWER_TWO, VALID_ANSWER_THREE, VALID_ANSWER_FOUR).build();
        assertParseSuccess(parser, QUESTION_DESC_LOVE + ANSWER_DESC_ONE + ANSWER_DESC_TWO
                + ANSWER_DESC_THREE + ANSWER_DESC_FOUR, new AddPollCommand(expectedPollMultipleAnswers));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPollCommand.MESSAGE_USAGE);

        // missing question prefix
        assertParseFailure(parser, VALID_QUESTION_LIFE + ANSWER_DESC_ONE + ANSWER_DESC_TWO,
                expectedMessage);

        // missing answer prefix
        assertParseFailure(parser, QUESTION_DESC_LOVE + VALID_ANSWER_TWO,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid question
        assertParseFailure(parser, INVALID_QUESTION_DESC + ANSWER_DESC_ONE + ANSWER_DESC_TWO,
                Question.MESSAGE_QUESTION_CONSTRAINTS);

        // invalid answer
        assertParseFailure(parser, QUESTION_DESC_LOVE + INVALID_ANSWER_DESC,
                Answer.MESSAGE_ANSWER_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_QUESTION_DESC + INVALID_ANSWER_DESC,
                Question.MESSAGE_QUESTION_CONSTRAINTS);

        assertParseFailure(parser, INVALID_ANSWER_DESC + QUESTION_DESC_LOVE,
                Answer.MESSAGE_ANSWER_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + QUESTION_DESC_LOVE + ANSWER_DESC_ONE + ANSWER_DESC_TWO,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPollCommand.MESSAGE_USAGE));
    }
}
