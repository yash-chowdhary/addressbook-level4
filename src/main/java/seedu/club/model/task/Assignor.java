package seedu.club.model.task;
//@@author yash-chowdhary

import static java.util.Objects.requireNonNull;

/**
 * Refers to the assignor of a Task
 */
public class Assignor {

    private String assignor;

    public Assignor(String assignor) {
        requireNonNull(assignor);
        this.assignor = assignor;
    }

    public String getAssignor() {
        return assignor;
    }

    @Override
    public int hashCode() {
        return assignor.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this        //short circuit if same object
                || (other instanceof Assignor   //handles nulls
                && this.assignor.equalsIgnoreCase(((Assignor) other).assignor));    //state check
    }
}
