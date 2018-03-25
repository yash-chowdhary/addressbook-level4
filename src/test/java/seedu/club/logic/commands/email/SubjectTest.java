package seedu.club.logic.commands.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.club.model.email.Subject;

public class SubjectTest {

    @Test
    public void equals() {
        Subject firstSubject = new Subject(Subject.TEST_SUBJECT_STRING);
        Subject secondSubject = new Subject(Subject.TEST_SUBJECT_STRING);

        assertTrue(firstSubject.equals(firstSubject));
        assertTrue(firstSubject.equals(secondSubject));

        assertFalse(firstSubject.equals(null));
    }

    @Test
    public void test_hashCode() {
        Subject subject = new Subject(Subject.EMPTY_SUBJECT_STRING);
        assertEquals(subject.hashCode(), Subject.EMPTY_SUBJECT_STRING.hashCode());
    }
}
