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

    @Override
    public boolean equals(Object other) {
        return other == this    //short circuit if same object
                || (other instanceof Username    //handles nulls
                && this.value.equals(((Username) other).value));   //state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
//@@author
