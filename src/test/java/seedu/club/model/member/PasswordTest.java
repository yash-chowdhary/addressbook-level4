package seedu.club.model.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_PASSWORD;

import org.junit.Test;

public class PasswordTest {

    @Test
    public void testEquals() {
        Password firstPassword = new Password("password");
        Password secondPassword = new Password("PASSWORD");

        assertEquals(firstPassword, firstPassword);

        assertFalse(firstPassword.equals(null));
        assertFalse(firstPassword.equals(true));

        assertFalse(firstPassword.equals(secondPassword));

        Password firstPasswordCopy = new Password("password");
        assertTrue(firstPassword.equals(firstPasswordCopy));
    }

    @Test
    public void testHashCode() {
        Password password = new Password(VALID_PASSWORD);
        String passwordString = VALID_PASSWORD;
        assertEquals(passwordString.hashCode(), password.hashCode());
    }
}
