package seedu.club.model.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_BOB;

import org.junit.Test;

public class UsernameTest {
    @Test
    public void testEquals() {
        Username firstUsername = new Username(VALID_MATRIC_NUMBER_AMY);
        Username secondUsername = new Username(VALID_MATRIC_NUMBER_BOB);

        assertEquals(firstUsername, firstUsername);

        assertFalse(firstUsername.equals(null));
        assertFalse(firstUsername.equals(true));

        assertFalse(firstUsername.equals(secondUsername));

        Username firstUsernameCopy = new Username(VALID_MATRIC_NUMBER_AMY);
        assertTrue(firstUsername.equals(firstUsernameCopy));
    }

    @Test
    public void testHashCode() {
        Username username = new Username(VALID_MATRIC_NUMBER_AMY);
        String usernameString = VALID_MATRIC_NUMBER_AMY;
        assertEquals(usernameString.hashCode(), username.hashCode());
    }

}
