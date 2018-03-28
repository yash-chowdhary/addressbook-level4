package seedu.club.storage;

import static org.junit.Assert.assertEquals;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_ANSWER;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_MATRIC_NUMBER;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_QUESTION;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_ONE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_TWO;
import static seedu.club.logic.commands.CommandTestUtil.VALID_QUESTION_LIFE;
import static seedu.club.storage.XmlAdaptedPoll.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.club.testutil.TypicalPolls.POLL_WHAT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Question;
import seedu.club.testutil.Assert;

public class XmlAdaptedPollTest {

    private static final int VALID_NUMBER_ANSWERED_FIVE = 5;
    private static final int VALID_NUMBER_ANSWERED_TEN = 10;
    private static final int INVALID_NUMBER_ANSWERED_NEGATIVE_FIVE = -5;
    private static final int INVALID_NUMBER_ANSWERED_NEGATIVE_TEN = -10;

    private static final List<XmlAdaptedAnswer> VALID_ANSWERS = POLL_WHAT.getAnswers().stream()
            .map(XmlAdaptedAnswer::new)
            .collect(Collectors.toList());

    private static final List<XmlAdaptedAnswer> INVALID_ANSWERS_EMPTY_ANSWER = Arrays.asList(
            new XmlAdaptedAnswer(INVALID_ANSWER, VALID_NUMBER_ANSWERED_FIVE),
            new XmlAdaptedAnswer(VALID_ANSWER_ONE, VALID_NUMBER_ANSWERED_TEN)
    );
    private static final List<XmlAdaptedAnswer> INVALID_ANSWERS_NEGATIVE_NUMBER_ANSWERED = Arrays.asList(
            new XmlAdaptedAnswer(VALID_ANSWER_ONE, INVALID_NUMBER_ANSWERED_NEGATIVE_TEN),
            new XmlAdaptedAnswer(VALID_ANSWER_TWO, INVALID_NUMBER_ANSWERED_NEGATIVE_FIVE)
    );

    private static final List<XmlAdaptedMatricNumber> VALID_POLLEES_MATRIC_NUMBERS =
            POLL_WHAT.getPolleesMatricNumbers().stream()
            .map(XmlAdaptedMatricNumber::new)
            .collect(Collectors.toList());

    private static final List<XmlAdaptedMatricNumber> INVALID_POLLEES_MATRIC_NUMBERS = Arrays.asList(
            new XmlAdaptedMatricNumber(INVALID_MATRIC_NUMBER), new XmlAdaptedMatricNumber(INVALID_MATRIC_NUMBER)
    );

    @Test
    public void toModelType_validPollDetails_returnsPoll() throws Exception {
        XmlAdaptedPoll poll = new XmlAdaptedPoll(POLL_WHAT);
        assertEquals(POLL_WHAT, poll.toModelType());
    }

    @Test
    public void toModelType_invalidQuestion_throwsIllegalValueException() {
        XmlAdaptedPoll poll =
                new XmlAdaptedPoll(INVALID_QUESTION, VALID_ANSWERS, VALID_POLLEES_MATRIC_NUMBERS);
        String expectedMessage = Question.MESSAGE_QUESTION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, poll::toModelType);
    }

    @Test
    public void toModelType_nullQuestion_throwsIllegalValueException() {
        XmlAdaptedPoll poll = new XmlAdaptedPoll(null, VALID_ANSWERS, VALID_POLLEES_MATRIC_NUMBERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, poll::toModelType);
    }

    @Test
    public void toModelType_invalidAnswersEmptyAnswer_throwsIllegalValueException() {
        XmlAdaptedPoll poll =
                new XmlAdaptedPoll(VALID_QUESTION_LIFE, INVALID_ANSWERS_EMPTY_ANSWER, VALID_POLLEES_MATRIC_NUMBERS);
        String expectedMessage = Answer.MESSAGE_ANSWER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, poll::toModelType);
    }

    @Test
    public void toModelType_invalidAnswerNegativeNumberAnswered_throwsIllegalValueException() {
        XmlAdaptedPoll poll =
                new XmlAdaptedPoll(VALID_QUESTION_LIFE, INVALID_ANSWERS_NEGATIVE_NUMBER_ANSWERED,
                        VALID_POLLEES_MATRIC_NUMBERS);
        String expectedMessage = Answer.MESSAGE_ANSWER_NUMBER_ANSWERED_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, poll::toModelType);
    }

    @Test
    public void toModelType_nullAnswers_throwsIllegalValueException() {
        XmlAdaptedPoll poll = new XmlAdaptedPoll(VALID_QUESTION_LIFE, null, VALID_POLLEES_MATRIC_NUMBERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, poll::toModelType);
    }

    @Test
    public void toModelType_invalidPolleesMatricNumber_throwsIllegalValueException() {
        XmlAdaptedPoll poll =
                new XmlAdaptedPoll(VALID_QUESTION_LIFE, VALID_ANSWERS, INVALID_POLLEES_MATRIC_NUMBERS);
        String expectedMessage = MatricNumber.MESSAGE_MATRIC_NUMBER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, poll::toModelType);
    }
}
