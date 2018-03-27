package seedu.club.model.task;
//@@author yash-chowdhary

import static java.util.Objects.requireNonNull;

/**
 * Refers to the assignee of a Task
 */
public class Assignee {

    private String assignee;

    public Assignee(String assignee) {
        requireNonNull(assignee);
        this.assignee = assignee;
    }

    public String getAssignee() {
        return assignee;
    }

    @Override
    public int hashCode() {
        return assignee.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this        //short circuit if same object
                || (other instanceof Assignee   //handles nulls
                && this.assignee.equalsIgnoreCase(((Assignee) other).assignee));    //state check
    }
}
