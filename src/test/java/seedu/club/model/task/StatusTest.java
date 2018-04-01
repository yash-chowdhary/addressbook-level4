package seedu.club.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_STATUS_IN_PROGRESS;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_STATUS_TO_BEGIN;

import org.junit.Test;

public class StatusTest {

    @Test
    public void equals() {

        Status firstStatus = new Status(VALID_TASK_STATUS_TO_BEGIN);
        Status secondStatus = new Status(VALID_TASK_STATUS_IN_PROGRESS);
        assertTrue(firstStatus.equals(firstStatus));

        assertFalse(firstStatus.equals(null));
        assertFalse(firstStatus.equals(true));

        assertFalse(firstStatus.equals(secondStatus));

        Status firstStatusCopy = new Status(VALID_TASK_STATUS_TO_BEGIN);
        assertTrue(firstStatus.equals(firstStatusCopy));
    }

    @Test
    public void test_hashCode() {
        Status status = new Status(VALID_TASK_STATUS_IN_PROGRESS);
        String expectedStatus = VALID_TASK_STATUS_IN_PROGRESS;
        assertEquals(expectedStatus.hashCode(), status.hashCode());
    }
}
