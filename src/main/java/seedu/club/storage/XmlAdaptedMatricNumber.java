package seedu.club.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlValue;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.member.MatricNumber;

/**
 * JAXB-friendly version of the member.
 */
public class XmlAdaptedMatricNumber {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Matric Number missing";
    @XmlValue
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
     * Converts a given MatricNumber into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedMatricNumber
     */
    public XmlAdaptedMatricNumber(MatricNumber source) {
        matricNumber = source.toString();
    }

    /**
     * Converts this jaxb-friendly adapted matricNumber object into the model's matricNumber object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted member
     */
    public MatricNumber toModelType() throws IllegalValueException {
        if (this.matricNumber == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
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

        XmlAdaptedMatricNumber otherMatricNumber = (XmlAdaptedMatricNumber) other;
        return Objects.equals(matricNumber, otherMatricNumber.matricNumber);
    }
}
