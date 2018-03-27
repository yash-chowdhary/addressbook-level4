package seedu.club.testutil;

//@@author yash-chowdhary

import seedu.club.model.task.Assignee;
import seedu.club.model.task.Assignor;
import seedu.club.model.task.Date;
import seedu.club.model.task.Description;
import seedu.club.model.task.Status;
import seedu.club.model.task.Task;
import seedu.club.model.task.Time;

/**
 * Utility class to build Task objects
 */
public class TaskBuilder {
    public static final String DEFAULT_DESCRIPTION = "Book Auditorium";
    public static final String DEFAULT_DATE = "01/01/2018";
    public static final String DEFAULT_TIME = "15:00";
    public static final String DEFAULT_ASSIGNOR = "";
    public static final String DEFAULT_ASSIGNEE = "";
    public static final String DEFAULT_STATUS = "Yet To Begin";

    private Description description;
    private Date date;
    private Time time;
    private Assignor assignor;
    private Assignee assignee;
    private Status status;

    public TaskBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        date = new Date(DEFAULT_DATE);
        time = new Time(DEFAULT_TIME);
        assignor = new Assignor(DEFAULT_ASSIGNOR);
        assignee = new Assignee(DEFAULT_ASSIGNEE);
        status = new Status(DEFAULT_STATUS);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code task}
     */
    public TaskBuilder(Task task) {
        description = task.getDescription();
        date = task.getDate();
        time = task.getTime();
        assignor = task.getAssignor();
        assignee = task.getAssignee();
        status = task.getStatus();
    }

    /**
     * Sets the {@code Description} of the {@code Task} that is being built
     */
    public TaskBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Task} that is being built
     */
    public TaskBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Task} that is being built
     */
    public TaskBuilder withTime(String time) {
        this.time = new Time(time);
        return this;
    }

    /**
     * Sets the {@code Assignor} of the {@code Task} that is being built
     */
    public TaskBuilder withAssignor(String assignor) {
        this.assignor = new Assignor(assignor);
        return this;
    }

    /**
     * Sets the {@code Assignee} of the {@code Task} that is being built
     */
    public TaskBuilder withAssignee(String assignee) {
        this.assignee = new Assignee(assignee);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Task} that is being built
     */
    public TaskBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    public Task build() {
        return new Task(description, time, date, assignor, assignee, status);
    }
}
