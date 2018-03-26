package seedu.club.model.poll;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UniquePollListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePollList uniquePollList = new UniquePollList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePollList.asObservableList().remove(0);
    }
}
