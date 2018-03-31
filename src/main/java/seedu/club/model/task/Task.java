package seedu.club.model.task;
//@@author yash-chowdhary
import static seedu.club.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Task assigned to, or created by a Member
 */
public class Task {

    private Description description;
    private Assignor assignor;
    private Assignee assignee;
    private Time time;
    private Date date;
    private Status status;

    public Task(Description description, Time time, Date date) {
        requireAllNonNull(description, time, date);
        this.description = description;
        this.time = time;
        this.date = date;
        this.assignor = new Assignor("");
        this.assignee = new Assignee("");
        this.status = new Status(Status.NOT_STARTED_STATUS);
    }

    public Task(Description description, Time time, Date date, Assignor assignor, Assignee assignee,
                Status status) {
        requireAllNonNull(description, time, date, assignor, assignee);
        this.description = description;
        this.time = time;
        this.date = date;
        this.assignor = assignor;
        this.assignee = assignee;
        this.status = status;
    }

    public Description getDescription() {
        return description;
    }

    public Assignor getAssignor() {
        return assignor;
    }

    public Time getTime() {
        return time;
    }

    public Date getDate() {
        return date;
    }

    public void setAssignor(Assignor assignor) {
        this.assignor = assignor;
    }

    public Assignee getAssignee() {
        return assignee;
    }

    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isTaskCompleted() {
        return status.getStatus().equalsIgnoreCase(Status.COMPLETED_STATUS);
    }

    public boolean isTaskInProgress() {
        return status.getStatus().equalsIgnoreCase(Status.IN_PROGRESS_STATUS);
    }

    public boolean hasTaskNotBegun() {
        return status.getStatus().equalsIgnoreCase(Status.NOT_STARTED_STATUS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Task
                && this.getDescription().equals(((Task) other).getDescription())
                && this.getDate().equals(((Task) other).getDate())
                && this.getTime().equals(((Task) other).getTime())
                && this.getAssignor().equals(((Task) other).getAssignor())
                && this.getAssignee().equals(((Task) other).getAssignee())
                && this.getStatus().equals(((Task) other).getStatus()));
    }
}
