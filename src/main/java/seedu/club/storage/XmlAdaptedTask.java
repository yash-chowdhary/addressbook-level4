package seedu.club.storage;
//@@author yash-chowdhary
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.task.Assignee;
import seedu.club.model.task.Assignor;
import seedu.club.model.task.Date;
import seedu.club.model.task.Description;
import seedu.club.model.task.Status;
import seedu.club.model.task.Task;
import seedu.club.model.task.Time;

/**
 * JAXB-friendly version of Task
 */
public class XmlAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    @XmlElement
    private String description;
    @XmlElement
    private String time;
    @XmlElement
    private String date;
    @XmlElement
    private String assignor;
    @XmlElement
    private String assignee;
    @XmlElement
    private String status;

    /**
     * Constructs an empty XmlAdaptedTask
     */
    public XmlAdaptedTask() {}

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String description, String time, String date, String assignor, String assignee,
                          String status) {
        this.description = description;
        this.time = time;
        this.date = date;
        this.assignor = assignor;
        this.assignee = assignee;
        this.status = status;
    }

    /**
     * Overloaded Constructor for constructing an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String description, String time, String date) {
        this.description = description;
        this.time = time;
        this.date = date;
        this.assignor = "";
        this.assignee = "";
        this.status = "";
    }

    /**
     * Converts a given Task into XmlAdaptedTask for JAXB use.
     */
    public XmlAdaptedTask(Task source) {
        description = source.getDescription().getDescription();
        time = source.getTime().getTime();
        date = source.getDate().getDate();
        assignor = source.getAssignor().getAssignor();
        assignee = source.getAssignee().getAssignee();
        status = source.getStatus().getStatus();
    }

    /**
     * Converts the jaxb-friendly adaptedTask object into the model's Task object.
     * @throws IllegalValueException if any values do not follow the valid format.
     */
    public Task toModelType() throws IllegalValueException {
        if (this.description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }

        final Description description = new Description(this.description);

        if (this.time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(this.time)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        final Time time = new Time(this.time);

        if (this.date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(this.date)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date date = new Date(this.date);

        if (this.assignor == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Assignor.class.getSimpleName()));
        }
        final Assignor assignor = new Assignor(this.assignor);

        if (this.assignee == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Assignee.class.getSimpleName()));
        }
        final Assignee assignee = new Assignee(this.assignee);

        if (this.status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Status.class.getSimpleName()));
        }
        final Status status = new Status(this.status);

        return new Task(description, time, date, assignor, assignee, status);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTask)) {
            return false;
        }

        XmlAdaptedTask otherTask = (XmlAdaptedTask) other;
        return Objects.equals(description, otherTask.description)
                && Objects.equals(time, otherTask.time)
                && Objects.equals(date, otherTask.date)
                && Objects.equals(assignor, otherTask.assignor)
                && Objects.equals(assignee, otherTask.assignee);
    }
}
