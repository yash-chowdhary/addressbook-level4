package seedu.club.model.poll;

import static org.junit.Assert.assertEquals;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_ONE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_TWO;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_BOB;
import static seedu.club.logic.commands.CommandTestUtil.VALID_QUESTION;
import static seedu.club.model.poll.Answer.PREFIX_ANSWER;

import org.junit.Test;

import seedu.club.testutil.PollBuilder;

public class PollTest {

    @Test
    public void test_toString() {
        Poll poll = new PollBuilder()
                .withQuestion(VALID_QUESTION)
                .withAnswers(VALID_ANSWER_ONE, VALID_ANSWER_TWO)
                .withPollerMatricNumber(VALID_MATRIC_NUMBER)
                .withPolleesMatricNumbers(VALID_MATRIC_NUMBER_AMY, VALID_MATRIC_NUMBER_BOB)
                .build();
        assertEquals(poll.toString(), "[ " + new Question(VALID_QUESTION) + " ]"
                + PREFIX_ANSWER + VALID_ANSWER_ONE + "," + PREFIX_ANSWER + VALID_ANSWER_TWO);
    }
}
