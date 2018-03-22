package seedu.club.model.task;

import static seedu.club.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Task assigned to, or created by a Member
 */
public class Task {

    private Description description;
    private Assignor assignor;
    private Time time;
    private Date date;
    private Status status;

    public Task(Description description, Assignor assignor, Time time, Date date) {
        requireAllNonNull(description, assignor, time, date, status);
        this.description = description;
        this.assignor = assignor;
        this.time = time;
        this.date = date;
        this.status = new Status(Status.NOT_STARTED_STATUS);
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

    public boolean isTaskCompleted() {
        return status.getStatus().equalsIgnoreCase(Status.COMPLETED_STATUS);
    }

    public boolean isTaskInProgress() {
        return status.getStatus().equalsIgnoreCase(Status.IN_PROGRESS_STATUS);
    }

    public boolean hasTaskNotBegun() {
        return status.getStatus().equalsIgnoreCase(Status.NOT_STARTED_STATUS);
    }

}
