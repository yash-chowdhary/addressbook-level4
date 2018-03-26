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
    private List<XmlAdaptedAnswer> answers = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedMatricNumber> polleesMatricNumbers = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedMember.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPoll() {}

    /**
     * Constructs an {@code XmlAdaptedMember} with the given member details.
     */

    public XmlAdaptedPoll(String question, List<XmlAdaptedAnswer> answers,
                          List<XmlAdaptedMatricNumber> polleesMatricNumbers) {
        this.question = question;
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
        answers = new ArrayList<>();
        for (Answer answer : source.getAnswers()) {
            answers.add(new XmlAdaptedAnswer(answer));
        }
        polleesMatricNumbers = new ArrayList<>();
        for (MatricNumber polleeMatricNumber : source.getPolleesMatricNumbers()) {
            polleesMatricNumbers.add(new XmlAdaptedMatricNumber(polleeMatricNumber));
        }
    }

    /**
     * Converts this jaxb-friendly adapted poll object into the model's poll object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted member
     */
    public Poll toModelType() throws IllegalValueException {
        if (this.answers == null || answers.isEmpty()) {
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

        Poll poll = new Poll (questionToReturn, answersToReturn, polleesMatricNumbersToReturn);
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
                && Objects.equals(answers, otherPoll.answers)
                && Objects.equals(polleesMatricNumbers, otherPoll.polleesMatricNumbers);
    }
}
