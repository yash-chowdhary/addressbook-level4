package seedu.club.model.poll;

import static org.junit.Assert.assertEquals;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_ONE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_TWO;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_BOB;
import static seedu.club.logic.commands.CommandTestUtil.VALID_QUESTION_LIFE;
import static seedu.club.model.poll.Answer.PREFIX_ANSWER;

import org.junit.Test;

import seedu.club.testutil.PollBuilder;

public class PollTest {

    @Test
    public void test_toString() {
        Poll poll = new PollBuilder()
                .withQuestion(VALID_QUESTION_LIFE)
                .withAnswers(VALID_ANSWER_ONE, VALID_ANSWER_TWO)
                .withPolleesMatricNumbers(VALID_MATRIC_NUMBER_AMY, VALID_MATRIC_NUMBER_BOB)
                .build();
        assertEquals(poll.toString(), "[ " + new Question(VALID_QUESTION_LIFE) + " ]"
                + PREFIX_ANSWER + VALID_ANSWER_ONE + "," + PREFIX_ANSWER + VALID_ANSWER_TWO);
    }
}
