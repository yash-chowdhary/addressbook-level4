package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's matric number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatricNumber(String)}
 */
public class MatricNumber {

    public static final String MATRIC_NUMBER_CONSTRAINTS =
            "Person matric number must begin with one letter, digits in the middle and a letter at the end";

    public static final String MATRIC_NUMBER_VALIDATION_REGEX = "^[a-zA-Z][\\d]+[a-zA-Z]$";

    public final String value;

    /**
     * Constructs a {@code MatricNumber}.
     *
     * @param matricNumber A valid matric number.
     */
    public MatricNumber(String matricNumber) {
        requireNonNull(matricNumber);
        checkArgument(isValidMatricNumber(matricNumber), MATRIC_NUMBER_CONSTRAINTS);
        this.value = matricNumber;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidMatricNumber(String test) {
        return test.matches(MATRIC_NUMBER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatricNumber // instanceof handles nulls
                && this.value.equals(((MatricNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
