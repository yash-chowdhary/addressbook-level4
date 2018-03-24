package seedu.club.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.member.MatricNumber;

/**
 * JAXB-friendly version of the member.
 */
public class XmlAdaptedMatricNumber {

    //TODO
    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Matric number missing";
    @XmlElement(required = true)
    private String matricNumber;

    /**
     * Constructs an XmlAdaptedMember.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMatricNumber() {}

    /**
     * Constructs an {@code XmlAdaptedMember} with the given member details.
     */

    public XmlAdaptedMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }

    /**
     * Converts a given Poll into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPoll
     */
    public XmlAdaptedMatricNumber(MatricNumber source) {
        matricNumber = source.toString();
    }

    /**
     * Converts this jaxb-friendly adapted poll object into the model's poll object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted member
     */
    public MatricNumber toModelType() throws IllegalValueException {
        if (this.matricNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MatricNumber.class.getSimpleName()));
        }
        if (!MatricNumber.isValidMatricNumber(this.matricNumber)) {
            throw new IllegalValueException(MatricNumber.MESSAGE_MATRIC_NUMBER_CONSTRAINTS);
        }
        return new MatricNumber(matricNumber);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMatricNumber)) {
            return false;
        }

        XmlAdaptedMatricNumber otherPoll = (XmlAdaptedMatricNumber) other;
        return Objects.equals(matricNumber, otherPoll.matricNumber);
    }
}
