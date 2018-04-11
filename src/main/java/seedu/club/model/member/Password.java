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
}
//@@author
