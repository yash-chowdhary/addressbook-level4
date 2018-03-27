package seedu.club.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import org.junit.Test;

public class AssigneeTest {
    @Test
    public void equals() {

        Assignee firstAssignee = new Assignee(VALID_NAME_AMY);
        Assignee secondAssignee = new Assignee(VALID_NAME_BOB);
        assertTrue(firstAssignee.equals(firstAssignee));

        assertFalse(firstAssignee.equals(null));
        assertFalse(firstAssignee.equals(true));

        assertFalse(firstAssignee.equals(secondAssignee));

        Assignee firstAssigneeCopy = new Assignee(VALID_NAME_AMY);
        assertTrue(firstAssignee.equals(firstAssigneeCopy));
    }

    @Test
    public void test_hashCode() {
        Assignee assignee = new Assignee(VALID_NAME_BOB);
        String expectedAssignee = VALID_NAME_BOB;
        assertEquals(expectedAssignee.hashCode(), assignee.hashCode());
    }

}
