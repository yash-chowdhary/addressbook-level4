package seedu.club.model.poll;

import static org.junit.Assert.*;
import static seedu.club.logic.commands.CommandTestUtil.VALID_QUESTION;

import org.junit.Test;

import seedu.club.testutil.Assert;

public class QuestionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Question(null));
    }

    @Test
    public void constructor_invalidQuestion_throwsIllegalArgumentException() {
        String invalidQuestion = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Question(invalidQuestion));
    }

    @Test
    public void isValidQuestion() {
        // null question
        Assert.assertThrows(NullPointerException.class, () -> Question.isValidQuestion(null));

        // invalid questions
        assertFalse(Question.isValidQuestion("")); // empty string
        assertFalse(Question.isValidQuestion(" ")); // spaces only

        // valid questions
        assertTrue(Question.isValidQuestion("What is the meaning of life?"));
        assertTrue(Question.isValidQuestion("How to basic123?"));
        assertTrue(Question.isValidQuestion("ARE WE ALONE?"));
        assertTrue(Question.isValidQuestion("Why are you____reading this?"));
        assertTrue(Question.isValidQuestion("ASOIJCNSOJACN"));
    }

    @Test
    public void test_toString() {
        Question testQuestionOne = new Question("WHAT IS LOVE");
        Question testQuestionTwo = new Question("WHAT IS LIFE");

        assertTrue(testQuestionOne.toString().equals("WHAT IS LOVE"));
        assertFalse(testQuestionOne.toString().equals("WHAT IS LIFE"));
        assertTrue(testQuestionTwo.toString().equals("WHAT IS LIFE"));
        assertFalse(testQuestionTwo.toString().equals("WHAT IS LOVE"));
    }

    @Test
    public void test_hashCode() {
        Question testQuestion = new Question("A1234567M");
        String question = "A1234567M";
        assertEquals(question.hashCode(), testQuestion.hashCode());
    }

    @Test
    public void test_equals() {
        Question firstQuestion = new Question(VALID_QUESTION);
        Question secondQuestion = new Question(VALID_QUESTION);

        assertTrue(firstQuestion.equals(firstQuestion));
        assertTrue(firstQuestion.equals(secondQuestion));

        assertFalse(firstQuestion.equals(null));
    }
}