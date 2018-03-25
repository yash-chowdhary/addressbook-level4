package seedu.club.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_TIME_1;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_TIME_2;

import org.junit.Test;

public class TimeTest {
    @Test
    public void isValidTime() {
        // invalid time
        assertFalse(Time.isValidTime("00:90"));
        assertFalse(Time.isValidTime("24:00"));

        // incorrect time input format
        assertFalse(Time.isValidTime("10 PM"));
        assertFalse(Time.isValidTime("0800"));
        assertFalse(Time.isValidTime("Eight o'clock"));

        // invalid input
        assertFalse(Time.isValidTime("Random string"));

        // valid time
        assertTrue(Time.isValidTime("00:00"));
        assertTrue(Time.isValidTime("11:30"));
        assertTrue(Time.isValidTime("15:00"));
        assertTrue(Time.isValidTime("17:00"));
        assertTrue(Time.isValidTime("23:59"));
    }

    @Test
    public void equals() {

        Time firstTime = new Time(VALID_TASK_TIME_1);
        Time secondTime = new Time(VALID_TASK_TIME_2);
        assertTrue(firstTime.equals(firstTime));

        assertFalse(firstTime.equals(null));
        assertFalse(firstTime.equals(true));

        assertFalse(firstTime.equals(secondTime));

        Time firstTimeCopy = new Time(VALID_TASK_TIME_1);
        assertTrue(firstTime.equals(firstTimeCopy));
    }

    @Test
    public void test_hashCode() {
        Time time = new Time(VALID_TASK_TIME_1);
        String expectedTime = VALID_TASK_TIME_1;
        assertEquals(expectedTime.hashCode(), time.hashCode());
    }
}
