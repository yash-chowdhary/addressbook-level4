package seedu.club.logic.commands.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.club.model.email.Body;

public class BodyTest {

    @Test
    public void equals() {
        Body firstBody = new Body(Body.TEST_BODY_STRING);
        Body secondBody = new Body(Body.TEST_BODY_STRING);

        assertTrue(firstBody.equals(firstBody));
        assertTrue(firstBody.equals(secondBody));

        assertFalse(firstBody.equals(null));
    }

    @Test
    public void test_hashCode() {
        Body body = new Body(Body.TEST_BODY_STRING);
        assertEquals(body.hashCode(), Body.TEST_BODY_STRING.hashCode());
    }
}
