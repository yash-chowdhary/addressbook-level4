package seedu.address.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GroupTest {

    @Test
    public void isValidGroup() {
        // empty group --> returns false
        assertFalse(Group.isValidGroup(" "));

        // null group --> returns false
        assertFalse(Group.isValidGroup(""));

        // string is not alphanumeric --> returns false
        assertFalse(Group.isValidGroup("123@#$"));
        assertFalse(Group.isValidGroup("=-0987"));
        assertFalse(Group.isValidGroup("publicity_main"));

        // string contains space-separated words --> returns false
        assertFalse(Group.isValidGroup("public relations"));
        assertFalse(Group.isValidGroup("executive committee"));
        assertFalse(Group.isValidGroup("logistics member"));

        // valid group names --> returns true
        assertTrue(Group.isValidGroup("logistics"));
        assertTrue(Group.isValidGroup("publicity"));
        assertTrue(Group.isValidGroup("marketing"));
        assertTrue(Group.isValidGroup("operations"));
    }
}
