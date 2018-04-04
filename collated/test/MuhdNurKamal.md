# MuhdNurKamal
###### \java\seedu\club\logic\parser\VoteCommandParserTest.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.club.commons.core.index.Index;
import seedu.club.logic.commands.VoteCommand;

public class VoteCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, VoteCommand.MESSAGE_USAGE);

    private VoteCommandParser parser = new VoteCommandParser();

    @Test
    public void parse_twoIndicesPresent_success() {
        assertParseSuccess(parser, "1 2",
                new VoteCommand(Index.fromOneBased(1), Index.fromOneBased(2)));

        assertParseSuccess(parser, "2 99",
                new VoteCommand(Index.fromOneBased(2), Index.fromOneBased(99)));

        assertParseSuccess(parser, "10 1",
                new VoteCommand(Index.fromOneBased(10), Index.fromOneBased(1)));
    }

    @Test
    public void parse_negativeIndices_failure() {
        assertParseFailure(parser, "-1 2", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "1 -2", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "-1 -2", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_oneIndexMissing_failure() {
        assertParseFailure(parser, "2", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "1 ", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "  8  ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_moreThanTwoIndices_failure() {
        assertParseFailure(parser, "2 2  22  11 1", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "2  3   1", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "2 1 11", MESSAGE_INVALID_FORMAT);
    }
}
```
###### \java\seedu\club\storage\XmlAdaptedAnswerTest.java
``` java
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
```
###### \java\seedu\club\storage\XmlAdaptedMatricNumberTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_MATRIC_NUMBER;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_BOB;

import org.junit.Test;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.member.MatricNumber;
import seedu.club.testutil.Assert;

public class XmlAdaptedMatricNumberTest {
    @Test
    public void toModelType_validMatricNumber_returnsMatricNumber() throws Exception {
        MatricNumber matricNumber = new MatricNumber(VALID_MATRIC_NUMBER);
        XmlAdaptedMatricNumber xmlAdaptedMatricNumber = new XmlAdaptedMatricNumber(matricNumber);
        assertEquals(matricNumber, xmlAdaptedMatricNumber.toModelType());
    }

    @Test
    public void toModelType_invalidMatricNumber_throwsIllegalValueException() {
        XmlAdaptedMatricNumber matricNumber =
                new XmlAdaptedMatricNumber(INVALID_MATRIC_NUMBER);
        String expectedMessage = MatricNumber.MESSAGE_MATRIC_NUMBER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, matricNumber::toModelType);
    }

    @Test
    public void equals() {
        XmlAdaptedMatricNumber xmlAdaptedMatricNumberAmy = new XmlAdaptedMatricNumber(VALID_MATRIC_NUMBER_AMY);
        XmlAdaptedMatricNumber xmlAdaptedMatricNumberBob = new XmlAdaptedMatricNumber(VALID_MATRIC_NUMBER_BOB);

        assertEquals(xmlAdaptedMatricNumberAmy, xmlAdaptedMatricNumberAmy);

        assertNotEquals(xmlAdaptedMatricNumberAmy, xmlAdaptedMatricNumberBob);
    }
}
```
###### \java\seedu\club\storage\XmlAdaptedPollTest.java
``` java
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
```
###### \java\seedu\club\ui\MemberListPanelTest.java
``` java
    @Test
    public void handleCompressMembersRequestEvent() {
        postNow(COMPRESS_MEMBERS_REQUEST_EVENT);
        assertTrue(memberListPanel.isDisplayingCompressedMembers());
    }

    @Test
    public void handleDecompressMembersRequestEvent() {
        postNow(DECOMPRESS_MEMBERS_REQUEST_EVENT);
        assertFalse(memberListPanel.isDisplayingCompressedMembers());
    }
```
