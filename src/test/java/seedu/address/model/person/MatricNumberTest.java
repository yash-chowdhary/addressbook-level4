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
        // null address
        Assert.assertThrows(NullPointerException.class, () -> MatricNumber.isValidMatricNumber(null));

        // invalid addresses
        assertFalse(MatricNumber.isValidMatricNumber("")); // empty string
        assertFalse(MatricNumber.isValidMatricNumber(" ")); // spaces only

        // valid addresses
        assertTrue(MatricNumber.isValidMatricNumber("E1152241G"));
        assertTrue(MatricNumber.isValidMatricNumber("G0152640A")); // one character
        assertTrue(MatricNumber.isValidMatricNumber("H1022206A")); // long address
    }
}
