package seedu.club.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_CONFETTI;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_FOOD;

import org.junit.Test;

import seedu.club.testutil.Assert;

/**
 * Unit tests for Description
 */
public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidDescription = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid name
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only
        assertFalse(Description.isValidDescription("^")); // only non-alphanumeric characters
        assertFalse(Description.isValidDescription("Buy*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Description.isValidDescription("food")); // alphabets only
        assertTrue(Description.isValidDescription("12345")); // numbers only
        assertTrue(Description.isValidDescription("lorem ipsum the 2nd")); // alphanumeric characters
        assertTrue(Description.isValidDescription("Buy Food")); // with capital letters
        assertTrue(Description.isValidDescription("Very long description indeed")); // long descriptions
    }

    @Test
    public void test_hashCode() {
        Description description = new Description(VALID_TASK_DESCRIPTION_FOOD);
        String expectedDescription = VALID_TASK_DESCRIPTION_FOOD;

        assertEquals(expectedDescription.hashCode(), description.hashCode());
    }

    @Test
    public void test_equals() {
        Description descriptionOne = new Description(VALID_TASK_DESCRIPTION_FOOD);
        Description descriptionTwo = new Description(VALID_TASK_DESCRIPTION_CONFETTI);

        assertTrue(descriptionOne.equals(descriptionOne));
        assertFalse(descriptionOne.equals(descriptionTwo));

        assertFalse(descriptionOne.equals(null));
    }
}
