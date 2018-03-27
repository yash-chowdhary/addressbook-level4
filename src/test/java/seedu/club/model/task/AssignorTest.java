package seedu.club.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import org.junit.Test;

public class AssignorTest {

    @Test
    public void equals() {

        Assignor firstAssignor = new Assignor(VALID_NAME_AMY);
        Assignor secondAssignor = new Assignor(VALID_NAME_BOB);
        assertTrue(firstAssignor.equals(firstAssignor));

        assertFalse(firstAssignor.equals(null));
        assertFalse(firstAssignor.equals(true));

        assertFalse(firstAssignor.equals(secondAssignor));

        Assignor firstAssignorCopy = new Assignor(VALID_NAME_AMY);
        assertTrue(firstAssignor.equals(firstAssignorCopy));
    }

    @Test
    public void test_hashCode() {
        Assignor assignor = new Assignor(VALID_NAME_BOB);
        String expectedAssignor = VALID_NAME_BOB;
        assertEquals(expectedAssignor.hashCode(), assignor.hashCode());
    }

}
