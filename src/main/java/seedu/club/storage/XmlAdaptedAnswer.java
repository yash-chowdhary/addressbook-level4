package seedu.club.storage;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.poll.Answer;

/**
 * JAXB-friendly adapted version of the Answer.
 */
public class XmlAdaptedAnswer {

    @XmlValue
    private String value;
    @XmlAttribute
    private int noOfMembersAnswered;

    /**
     * Constructs an XmlAdaptedAnswer.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAnswer() {}

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code tagName}.
     */
    public XmlAdaptedAnswer(String value, int noOfMembersAnswered) {
        this.value = value;
        this.noOfMembersAnswered = noOfMembersAnswered;
    }

    /**
     * Converts a given Answer into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedAnswer(Answer source) {
        value = source.getValue();
        noOfMembersAnswered = source.getVoteCount();
    }

    /**
     * Converts this jaxb-friendly adapted Answer object into the model's Answer object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted member
     */
    public Answer toModelType() throws IllegalValueException {
        if (!Answer.isValidAnswer(value)) {
            throw new IllegalValueException(Answer.MESSAGE_ANSWER_CONSTRAINTS);
        }
        if (!Answer.isValidNoOfMembersAnswered(noOfMembersAnswered)) {
            throw new IllegalValueException(Answer.MESSAGE_ANSWER_NUMBER_ANSWERED_CONSTRAINTS);
        }
        return new Answer(value, noOfMembersAnswered);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAnswer)) {
            return false;
        }

        return value.equals(((XmlAdaptedAnswer) other).value)
                && noOfMembersAnswered == ((XmlAdaptedAnswer) other).noOfMembersAnswered;
    }
}
