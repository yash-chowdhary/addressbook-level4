package seedu.club.model.task;
//@@author yash-chowdhary
import static java.util.Objects.requireNonNull;

/**
 * Represents the status of a Task
 */
public class Status {

    public static final String NOT_STARTED_STATUS = "Yet To Begin";
    public static final String IN_PROGRESS_STATUS = "In Progress";
    public static final String COMPLETED_STATUS = "Completed";

    private String status;

    public Status(String status) {
        requireNonNull(status);
        this.status = status;
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this        //short circuit if same object
                || (other instanceof Status   //handles nulls
                && this.status.equalsIgnoreCase(((Status) other).status));    //state check
    }
}
