package seedu.club.model.task;

//@@author yash-chowdhary
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.testutil.TypicalTasks.BUY_CONFETTI;
import static seedu.club.testutil.TypicalTasks.BUY_FOOD;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.club.model.task.exceptions.DuplicateTaskException;

public class UniqueTaskListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTaskList.asObservableList().remove(0);
    }

    @Test
    public void equals() throws DuplicateTaskException {
        UniqueTaskList firstTaskList = new UniqueTaskList();
        firstTaskList.add(BUY_FOOD);
        UniqueTaskList secondTaskList = new UniqueTaskList();
        secondTaskList.add(BUY_CONFETTI);

        assertTrue(firstTaskList.equals(firstTaskList));
        assertFalse(firstTaskList.equals(true));
        assertFalse(firstTaskList.equals(secondTaskList));
    }

    @Test
    public void asTaskInsensitiveList_compareSimilarLists_success()
            throws DuplicateTaskException {
        UniqueTaskList firstTaskList = new UniqueTaskList();
        firstTaskList.add(BUY_CONFETTI);
        firstTaskList.add(BUY_FOOD);
        UniqueTaskList secondTaskList = new UniqueTaskList();
        secondTaskList.add(BUY_FOOD);
        secondTaskList.add(BUY_CONFETTI);

        assertTrue(firstTaskList.equalsOrderInsensitive(secondTaskList));
    }

    @Test
    public void asUniqueList_addDuplicateOrder_throwsDuplicateOrderException()
            throws DuplicateTaskException {
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        thrown.expect(DuplicateTaskException.class);
        uniqueTaskList.add(BUY_CONFETTI);
        uniqueTaskList.add(BUY_CONFETTI);
    }

}
