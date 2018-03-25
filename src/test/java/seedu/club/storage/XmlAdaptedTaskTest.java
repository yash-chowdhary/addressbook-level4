package seedu.club.storage;

//@@author yash-chowdhary
import static org.junit.Assert.assertEquals;
import static seedu.club.storage.XmlAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.club.testutil.TypicalTasks.BUY_CONFETTI;

import org.junit.Test;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.task.Date;
import seedu.club.model.task.Description;
import seedu.club.model.task.Time;
import seedu.club.testutil.Assert;

public class XmlAdaptedTaskTest {
    private static final String INVALID_TASK_DESCRIPTION = "Buy *";
    private static final String INVALID_DATE = "02/13/2009";
    private static final String INVALID_TIME = "24:00";

    private static final String VALID_TASK_DESCRIPTION = BUY_CONFETTI.getDescription().getDescription();
    private static final String VALID_TASK_DATE = BUY_CONFETTI.getDate().getDate();
    private static final String VALID_TASK_TIME = BUY_CONFETTI.getTime().getTime();

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        XmlAdaptedTask xmlAdaptedTask = new XmlAdaptedTask(BUY_CONFETTI);
        assertEquals(BUY_CONFETTI, xmlAdaptedTask.toModelType());
    }

    @Test
    public void toModelType_invalidTaskInformation_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(INVALID_TASK_DESCRIPTION, VALID_TASK_TIME,
                VALID_TASK_DATE);
        String expectedMessage = Description.MESSAGE_DESCRIPTION_CONSTRAINTS;
        Assert.assertThrows(IllegalArgumentException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullTaskInformation_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(null, VALID_TASK_TIME,
                VALID_TASK_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTaskDate_throwsIllegalValueException() {
        XmlAdaptedTask xmlAdaptedTask = new XmlAdaptedTask(VALID_TASK_DESCRIPTION, VALID_TASK_TIME,
                INVALID_DATE);
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, xmlAdaptedTask::toModelType);
    }

    @Test
    public void toModelType_nullTaskDate_throwsIllegalValueException() {
        XmlAdaptedTask xmlAdaptedTask = new XmlAdaptedTask(VALID_TASK_DESCRIPTION, VALID_TASK_TIME,
                null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, xmlAdaptedTask::toModelType);
    }

    @Test
    public void toModelType_invalidTaskTime_throwsIllegalValueException() {
        XmlAdaptedTask xmlAdaptedTask = new XmlAdaptedTask(VALID_TASK_DESCRIPTION, INVALID_TIME,
                VALID_TASK_DATE);
        String expectedMessage = Time.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, xmlAdaptedTask::toModelType);
    }

    @Test
    public void toModelType_nullTaskTime_throwsIllegalValueException() {
        XmlAdaptedTask xmlAdaptedTask = new XmlAdaptedTask(VALID_TASK_DESCRIPTION, null,
                VALID_TASK_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Time.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, xmlAdaptedTask::toModelType);
    }

}
