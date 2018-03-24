package seedu.club.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.Question;

/**
 * JAXB-friendly version of the member.
 */
public class XmlAdaptedPoll {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "member's %s field is missing!";

    @XmlElement(required = true)
    private String question;
    @XmlElement(required = true)
    private String pollerMatricNumber;
    @XmlElement
    private List<XmlAdaptedMatricNumber> polleesMatricNumbers = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedAnswer> answers = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedMember.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPoll() {}

    /**
     * Constructs an {@code XmlAdaptedMember} with the given member details.
     */

    public XmlAdaptedPoll(String question, String pollerMatricNumber,
                          List<XmlAdaptedMatricNumber> polleesMatricNumbers, List<XmlAdaptedAnswer> answers) {
        this.question = question;
        this.pollerMatricNumber = pollerMatricNumber;
        if (polleesMatricNumbers != null) {
            this.polleesMatricNumbers = new ArrayList<XmlAdaptedMatricNumber>(polleesMatricNumbers);
        }
        if (answers != null) {
            this.answers = new ArrayList<>(answers);
        }
    }

    /**
     * Converts a given Poll into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPoll
     */
    public XmlAdaptedPoll(Poll source) {
        question = source.getQuestion().getValue();
        pollerMatricNumber = source.getPollerMatricNumber().toString();
        polleesMatricNumbers = new ArrayList<>();
        for (MatricNumber polleeMatricNumber : source.getPolleesMatricNumbers()) {
            polleesMatricNumbers.add(new XmlAdaptedMatricNumber(polleeMatricNumber));
        }
        answers = new ArrayList<>();
        for (Answer answer : source.getAnswers()) {
            answers.add(new XmlAdaptedAnswer(answer));
        }
    }

    /**
     * Converts this jaxb-friendly adapted poll object into the model's poll object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted member
     */
    public Poll toModelType() throws IllegalValueException {
        if (this.answers == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Answer.class.getSimpleName()));
        }

        final List<Answer> answersToReturn = new ArrayList<>();
        for (XmlAdaptedAnswer answer : answers) {
            answersToReturn.add(answer.toModelType());
        }

        if (this.polleesMatricNumbers == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MatricNumber.class.getSimpleName()));
        }

        final Set<MatricNumber> polleesMatricNumbersToReturn = new HashSet<>();
        for (XmlAdaptedMatricNumber matricNumber : polleesMatricNumbers) {
            polleesMatricNumbersToReturn.add(matricNumber.toModelType());
        }

        if (this.question == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Question.class.getSimpleName()));
        }
        if (!Question.isValidQuestion(question)) {
            throw new IllegalValueException(Question.MESSAGE_QUESTION_CONSTRAINTS);
        }
        final Question questionToReturn = new Question(this.question);

        if (this.pollerMatricNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MatricNumber.class.getSimpleName()));
        }
        if (!MatricNumber.isValidMatricNumber(this.pollerMatricNumber)) {
            throw new IllegalValueException(MatricNumber.MESSAGE_MATRIC_NUMBER_CONSTRAINTS);
        }
        final MatricNumber pollerMatricNumberToReturn = new MatricNumber(this.pollerMatricNumber);

        Poll poll = new Poll (questionToReturn, pollerMatricNumberToReturn,
                answersToReturn, polleesMatricNumbersToReturn);
        return poll;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPoll)) {
            return false;
        }

        XmlAdaptedPoll otherPoll = (XmlAdaptedPoll) other;
        return Objects.equals(question, otherPoll.question)
                && Objects.equals(pollerMatricNumber, otherPoll.pollerMatricNumber)
                && Objects.equals(polleesMatricNumbers, otherPoll.polleesMatricNumbers)
                && Objects.equals(answers, otherPoll.answers);
    }
}
