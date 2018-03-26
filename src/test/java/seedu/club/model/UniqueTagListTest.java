package seedu.club.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.club.model.tag.Tag;
import seedu.club.model.tag.UniqueTagList;

public class UniqueTagListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTagList uniqueTagList = new UniqueTagList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTagList.asObservableList().remove(0);
    }

    @Test
    public void equals() throws UniqueTagList.DuplicateTagException {
        UniqueTagList firstTagList = new UniqueTagList();
        firstTagList.add(new Tag(VALID_TAG_HUSBAND));
        UniqueTagList secondTagList = new UniqueTagList();
        secondTagList.add(new Tag(VALID_TAG_FRIEND));

        assertTrue(firstTagList.equals(firstTagList));
        assertFalse(firstTagList.equals(true));
        assertFalse(firstTagList.equals(secondTagList));
    }

    @Test
    public void asTaskInsensitiveList_compareSimilarLists_success()
            throws UniqueTagList.DuplicateTagException {
        UniqueTagList firstTagList = new UniqueTagList();
        firstTagList.add(new Tag(VALID_TAG_FRIEND));
        firstTagList.add(new Tag(VALID_TAG_HUSBAND));
        UniqueTagList secondTagList = new UniqueTagList();
        secondTagList.add(new Tag(VALID_TAG_HUSBAND));
        secondTagList.add(new Tag(VALID_TAG_FRIEND));

        assertTrue(firstTagList.equalsOrderInsensitive(secondTagList));
    }

    @Test
    public void asUniqueList_addDuplicateOrder_throwsDuplicateOrderException()
            throws UniqueTagList.DuplicateTagException {
        UniqueTagList uniqueTagList = new UniqueTagList();
        thrown.expect(UniqueTagList.DuplicateTagException.class);
        uniqueTagList.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList.add(new Tag(VALID_TAG_FRIEND));
    }
}
