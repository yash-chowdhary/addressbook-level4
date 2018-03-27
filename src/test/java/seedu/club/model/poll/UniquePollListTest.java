package seedu.club.model.poll;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.testutil.TypicalPolls.POLL_HOW;
import static seedu.club.testutil.TypicalPolls.POLL_WHAT;
import static seedu.club.testutil.TypicalPolls.POLL_WHEN;
import static seedu.club.testutil.TypicalPolls.POLL_WHO;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.club.model.poll.exceptions.DuplicatePollException;

public class UniquePollListTest {

    private static final Set<Poll> POLL_SET_ONE = new HashSet<Poll>(Arrays.asList(POLL_WHAT, POLL_HOW, POLL_WHEN));
    private static final Set<Poll> POLL_SET_TWO = new HashSet<Poll>(Arrays.asList(POLL_WHO, POLL_HOW, POLL_WHEN));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePollList uniquePollList = new UniquePollList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePollList.asObservableList().remove(0);
    }

    @Test
    public void add_duplicatePoll_throwsDuplicatePollException() throws DuplicatePollException {
        UniquePollList uniquePollList = new UniquePollList();
        uniquePollList.add(POLL_WHAT);
        thrown.expect(DuplicatePollException.class);
        uniquePollList.add(POLL_WHAT);
    }

    @Test
    public void equals() {
        UniquePollList uniquePollListOne = new UniquePollList(POLL_SET_ONE);
        UniquePollList uniquePollListTwo = new UniquePollList(POLL_SET_TWO);

        assertTrue(uniquePollListOne.equals(uniquePollListOne));

        assertFalse(uniquePollListTwo.equals(uniquePollListOne));
    }
}
