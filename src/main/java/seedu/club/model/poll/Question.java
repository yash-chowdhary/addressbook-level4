package seedu.club.model.poll;

/**
 * Represents an question of a poll
 */
public class Question {

    public static final String MESSAGE_QUESTION_CONSTRAINTS = "Questions not be empty";
    public static final String QUESTION_VALIDATION_REGEX = ".*\\S.*";

    private String value;

    public Question(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValidQuestion(String test) {
        return test.matches(QUESTION_VALIDATION_REGEX);
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
