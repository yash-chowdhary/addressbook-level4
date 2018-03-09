package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MatricNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MatricNumber(null));
    }

    @Test
    public void constructor_invalidMatricNumber_throwsIllegalArgumentException() {
        String invalidMatricNumber = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MatricNumber(invalidMatricNumber));
    }

    @Test
    public void isValidMatricNumber() {
        // null matric number
        Assert.assertThrows(NullPointerException.class, () -> MatricNumber.isValidMatricNumber(null));

        // invalid matric numbers
        assertFalse(MatricNumber.isValidMatricNumber("")); // empty string
        assertFalse(MatricNumber.isValidMatricNumber(" ")); // spaces only
        assertFalse(MatricNumber.isValidMatricNumber("A1234567"));
        assertFalse(MatricNumber.isValidMatricNumber("1234567A"));
        assertFalse(MatricNumber.isValidMatricNumber("987645321"));


        // valid matric numbers
        assertTrue(MatricNumber.isValidMatricNumber("E1152241G"));
        assertTrue(MatricNumber.isValidMatricNumber("G0152640A"));
        assertTrue(MatricNumber.isValidMatricNumber("P1902205L"));
        assertTrue(MatricNumber.isValidMatricNumber("U1024509A"));
        assertTrue(MatricNumber.isValidMatricNumber("E1122206M"));
    }
}
