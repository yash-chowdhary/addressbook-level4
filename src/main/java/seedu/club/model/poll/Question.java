package seedu.club.model.poll;
//@@author MuhdNurKamal
import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.AppUtil.checkArgument;

/**
 * Represents an question of a poll
 */
public class Question {

    public static final String MESSAGE_QUESTION_CONSTRAINTS = "You need a question for the poll.";
    public static final String QUESTION_VALIDATION_REGEX = ".*\\S.*";
    public static final String QUESTION_LABEL = "Question: ";

    private String value;

    public Question(String value) {
        requireNonNull(value);
        checkArgument(isValidQuestion(value), MESSAGE_QUESTION_CONSTRAINTS);
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
        return QUESTION_LABEL + value;
    }
}
