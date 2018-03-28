package seedu.club.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DATE_1;

import org.junit.Test;

public class DateTest {
    @Test
    public void isValidDate() {
        // day out-of-bounds
        assertFalse(Date.isValidDate("0/01/2018"));
        assertFalse(Date.isValidDate("31/04/2018"));

        // month out-of-bounds
        assertFalse(Date.isValidDate("11/13/2018"));
        assertFalse(Date.isValidDate("11/00/2018"));

        // year out-of-bounds -> returns false
        assertFalse(Date.isValidDate("01/12/2100"));
        assertFalse(Date.isValidDate("01/12/1899"));

        // invalid format
        assertFalse(Date.isValidDate("21 March 2018"));

        // invalid input
        assertFalse(Date.isValidDate("Random string"));

        // invalid leap day
        assertFalse(Date.isValidDate("29/02/2018"));

        // valid leap day
        assertTrue(Date.isValidDate("29/02/2020"));

        // valid dates
        assertTrue(Date.isValidDate("01/01/2018"));
        assertTrue(Date.isValidDate("01.01.2018"));
        assertTrue(Date.isValidDate("01-01-2019"));

        // valid dates with mixed separators
        assertTrue(Date.isValidDate("01/01.2018"));
        assertTrue(Date.isValidDate("01.01-2018"));
        assertTrue(Date.isValidDate("01-01/2019"));
    }

    @Test
    public void equals() {

        Date firstDate = new Date("01/01/2018");
        Date secondDate = new Date("02/01/2018");
        assertTrue(firstDate.equals(firstDate));

        assertFalse(firstDate.equals(null));
        assertFalse(firstDate.equals(true));

        assertFalse(firstDate.equals(secondDate));

        // same value -> returns true
        Date firstDateCopy = new Date("01/01/2018");
        assertTrue(firstDate.equals(firstDateCopy));
    }

    @Test
    public void test_hashCode() {
        Date date = new Date(VALID_TASK_DATE_1);
        String expectedDate = VALID_TASK_DATE_1;
        assertEquals(expectedDate.hashCode(), date.hashCode());
    }
}
