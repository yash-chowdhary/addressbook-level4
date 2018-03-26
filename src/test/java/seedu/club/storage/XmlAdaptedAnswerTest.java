package seedu.club.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_ANSWER;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_ONE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_TWO;

import org.junit.Test;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.poll.Answer;
import seedu.club.testutil.Assert;

public class XmlAdaptedAnswerTest {

    @Test
    public void toModelType_validAnswer_returnsAnswer() throws Exception {
        Answer answer = new Answer(VALID_ANSWER_ONE);
        XmlAdaptedAnswer xmlAdaptedAnswer = new XmlAdaptedAnswer(answer);
        assertEquals(answer, xmlAdaptedAnswer.toModelType());
    }

    @Test
    public void toModelType_invalidAnswer_throwsIllegalValueException() {
        XmlAdaptedAnswer answer =
                new XmlAdaptedAnswer(INVALID_ANSWER, 5);
        String expectedMessage = Answer.MESSAGE_ANSWER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, answer::toModelType);
    }

    @Test
    public void toModelType_invalidNumberAnswered_throwsIllegalValueException() {
        XmlAdaptedAnswer answer =
                new XmlAdaptedAnswer(VALID_ANSWER_ONE, -10);
        String expectedMessage = Answer.MESSAGE_ANSWER_NUMBER_ANSWERED_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, answer::toModelType);
    }

    @Test
    public void equals() {
        XmlAdaptedAnswer xmlAdaptedAnswerOne = new XmlAdaptedAnswer(VALID_ANSWER_ONE, 5);
        XmlAdaptedAnswer xmlAdaptedAnswerTwo = new XmlAdaptedAnswer(VALID_ANSWER_ONE, 5);
        XmlAdaptedAnswer xmlAdaptedAnswerThree = new XmlAdaptedAnswer(VALID_ANSWER_TWO, 5);
        XmlAdaptedAnswer xmlAdaptedAnswerFour = new XmlAdaptedAnswer(VALID_ANSWER_ONE, 6);

        assertEquals(xmlAdaptedAnswerOne, xmlAdaptedAnswerOne);
        assertEquals(xmlAdaptedAnswerOne, xmlAdaptedAnswerTwo);

        assertNotEquals(xmlAdaptedAnswerOne, xmlAdaptedAnswerThree);
        assertNotEquals(xmlAdaptedAnswerOne, xmlAdaptedAnswerFour);
    }
}
