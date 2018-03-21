package seedu.club.model.poll;

/**
 * Represents an answer to a poll
 */
public class Answer {
    private String value;
    private int noOfMembersAnswered;

    public Answer(String value) {
        this.value = value;
        noOfMembersAnswered = 0;
    }

    public int getNoOfMembersAnswered() {
        return noOfMembersAnswered;
    }

    public void voteThisAnswer() {
        noOfMembersAnswered++;
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
        return "Ans: " + value;
    }
}
