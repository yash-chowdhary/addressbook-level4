package seedu.club.model.poll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_ONE;
import static seedu.club.model.poll.Answer.PREFIX_ANSWER;

import org.junit.Test;

import seedu.club.testutil.Assert;

public class AnswerTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Answer(null));
    }

    @Test
    public void constructor_invalidAnswer_throwsIllegalArgumentException() {
        String invalidAnswer = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Answer(invalidAnswer));
    }

    @Test
    public void isValidAnswer() {
        // null answer
        Assert.assertThrows(NullPointerException.class, () -> Answer.isValidAnswer(null));

        // invalid answers
        assertFalse(Answer.isValidAnswer("")); // empty string
        assertFalse(Answer.isValidAnswer(" ")); // spaces only

        // valid answers
        assertTrue(Answer.isValidAnswer("42"));
        assertTrue(Answer.isValidAnswer("not empty string"));
        assertTrue(Answer.isValidAnswer("Hello"));
        assertTrue(Answer.isValidAnswer("&*^&*^"));
        assertTrue(Answer.isValidAnswer("i dono"));
    }

    @Test
    public void test_toString() {
        Answer testAnswerOne = new Answer("hello");
        Answer testAnswerTwo = new Answer("world");

        assertTrue(testAnswerOne.toString().equals(PREFIX_ANSWER + "hello"));
        assertFalse(testAnswerOne.toString().equals(PREFIX_ANSWER + "world"));
        assertTrue(testAnswerTwo.toString().equals(PREFIX_ANSWER + "world"));
        assertFalse(testAnswerTwo.toString().equals(PREFIX_ANSWER + "hello"));
    }

    @Test
    public void test_hashCode() {
        Answer testAnswer = new Answer("hello");
        String answer = "hello";
        assertEquals(answer.hashCode(), testAnswer.hashCode());
    }

    @Test
    public void equals() {
        Answer firstAnswer = new Answer(VALID_ANSWER_ONE);
        Answer secondAnswer = new Answer(VALID_ANSWER_ONE);

        assertTrue(firstAnswer.equals(firstAnswer));
        assertTrue(firstAnswer.equals(secondAnswer));

        assertFalse(firstAnswer.equals(null));
    }

    @Test
    public void voteThisAnswer() {
        int initialVote = 0;
        Answer answer = new Answer(VALID_ANSWER_ONE, initialVote);
        assertVoteIncreasedAfterVoting(answer, initialVote);

        initialVote = 5;
        answer = new Answer(VALID_ANSWER_ONE, initialVote);
        assertVoteIncreasedAfterVoting(answer, initialVote);
    }

    private boolean assertVoteIncreasedAfterVoting(Answer answer, int initialVote) {
        answer.voteThisAnswer();
        return ++initialVote == answer.getVoteCount();
    }
}
