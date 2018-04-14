package seedu.club.model.member;
//@@author th14thmusician
/**
 * Represents a member's password.
 */
public class Password {
    public final String value;

    public Password(String password) {
        this.value = password;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this    //short circuit if same object
                || (other instanceof Password    //handles nulls
                && this.value.equals(((Password) other).value));   //state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
//@@author
