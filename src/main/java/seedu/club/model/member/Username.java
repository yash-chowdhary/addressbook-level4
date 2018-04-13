package seedu.club.model.member;
//@@author th14thmusician
/**
 * Represents a member's username.
 */
public class Username {
    public final String value;

    public Username(String name) {
        this.value = name;
    }

    @Override
    public String toString() {
        return value;
    }
}
//@@author
