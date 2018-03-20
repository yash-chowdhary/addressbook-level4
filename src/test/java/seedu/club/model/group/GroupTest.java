package seedu.club.model.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_BOB;

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

    @Test
    public void test_toString() {
        Group testGroupOne = new Group(VALID_GROUP_AMY);
        Group testGroupTwo = new Group(VALID_GROUP_BOB);

        assertTrue(testGroupOne.toString().equals(VALID_GROUP_AMY));
        assertTrue(testGroupTwo.toString().equals(VALID_GROUP_BOB));
        assertFalse(testGroupOne.toString().equals(VALID_GROUP_BOB));
        assertFalse(testGroupTwo.toString().equals(VALID_GROUP_AMY));
    }

    @Test
    public void test_hashCode() {
        Group group = new Group(VALID_GROUP_AMY);
        String groupName = VALID_GROUP_AMY;
        assertEquals(groupName.hashCode(), group.hashCode());
    }

    @Test
    public void test_equals() {
        Group firstGroup = new Group(VALID_GROUP_AMY);
        Group secondGroup = new Group(VALID_GROUP_BOB);

        assertTrue(firstGroup.equals(firstGroup));
        assertFalse(firstGroup.equals(secondGroup));

        assertFalse(firstGroup.equals(null));
    }
}
