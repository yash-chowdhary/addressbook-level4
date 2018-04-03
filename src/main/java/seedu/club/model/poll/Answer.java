package seedu.club.model.poll;

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.AppUtil.checkArgument;

/**
 * Represents an answer to a question of a poll
 */
public class Answer {

    public static final String MESSAGE_ANSWER_CONSTRAINTS = "Answer cannot be empty";
    public static final String MESSAGE_ANSWER_NUMBER_ANSWERED_CONSTRAINTS =
            "Number answered for an answer should be non-negative";
    public static final String ANSWER_VALIDATION_REGEX = ".*\\S.*";
    public static final String PREFIX_ANSWER = "Ans: ";
    public static final int NUMBER_ZERO_VOTE_COUNT = 0;

    private String value;
    private int voteCount;

    public Answer(String value) {
        this(value, NUMBER_ZERO_VOTE_COUNT);
    }

    public Answer(String value, int voteCount) {
        requireNonNull(value);
        checkArgument(isValidAnswer(value), MESSAGE_ANSWER_CONSTRAINTS);
        this.value = value;
        this.voteCount = voteCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getValue() {
        return value;
    }

    public void voteThisAnswer() {
        voteCount++;
    }

    public static boolean isValidAnswer(String value) {
        return value.matches(ANSWER_VALIDATION_REGEX);
    }

    public static boolean isValidNoOfMembersAnswered(int noOfMembersAnswered) {
        return noOfMembersAnswered >= NUMBER_ZERO_VOTE_COUNT;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Answer // instanceof handles nulls
                && this.value.equals(((Answer) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return PREFIX_ANSWER + value;
    }
}
