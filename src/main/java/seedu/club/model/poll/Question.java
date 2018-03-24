package seedu.club.model.poll;

/**
 * Represents an question of a poll
 */
public class Question {
    private String value;

    public Question(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Question // instanceof handles nulls
                && this.value.equals(((Question) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "Question: " + value;
    }
}
