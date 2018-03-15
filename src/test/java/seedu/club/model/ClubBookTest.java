package seedu.club.model;

import static org.junit.Assert.assertEquals;
import static seedu.club.logic.commands.CommandTestUtil.MANDATORY_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.club.testutil.TypicalPersons.ALICE;
import static seedu.club.testutil.TypicalPersons.AMY;
import static seedu.club.testutil.TypicalPersons.BOB;
import static seedu.club.testutil.TypicalPersons.getTypicalClubBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.person.Person;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;
import seedu.club.testutil.ClubBookBuilder;
import seedu.club.testutil.PersonBuilder;

public class ClubBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ClubBook clubBook = new ClubBook();
    private final ClubBook clubBookWithBobAndAmy = new ClubBookBuilder().withPerson(BOB).withPerson(AMY)
            .build();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), clubBook.getPersonList());
        assertEquals(Collections.emptyList(), clubBook.getTagList());
    }

    @Test
    public void removeGroup_nonExistentGroup_unchangedAddressBook() throws Exception {
        try {
            clubBookWithBobAndAmy.removeGroup(new Group(NON_EXISTENT_GROUP));
        } catch (GroupNotFoundException gnfe) {
            ClubBook expectedClubBook = new ClubBookBuilder().withPerson(BOB).withPerson(AMY).build();
            assertEquals(expectedClubBook, clubBookWithBobAndAmy);
        }
    }

    @Test
    public void removeGroup_mandatoryGroup_unchangedAddressBook() throws Exception {
        try {
            clubBookWithBobAndAmy.removeGroup(new Group(MANDATORY_GROUP));
        } catch (GroupCannotBeRemovedException e) {
            ClubBook expectedClubBook = new ClubBookBuilder().withPerson(BOB).withPerson(AMY).build();
            assertEquals(expectedClubBook, clubBookWithBobAndAmy);
        }
    }

    @Test
    public void removeGroup_atLeastOnePersonInGroup_groupRemoved() throws Exception {
        clubBookWithBobAndAmy.removeGroup(new Group(VALID_GROUP_BOB));

        Person bobNotInLogistics = new PersonBuilder(BOB).withGroup().build();
        Person amyNotInLogistics = new PersonBuilder(AMY).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withPerson(bobNotInLogistics)
                .withPerson(amyNotInLogistics).build();

        assertEquals(expectedClubBook, clubBookWithBobAndAmy);
    }

    @Test
    public void updatePerson_detailsChanged_personUpdated() throws Exception {
        ClubBook updatedToBob = new ClubBookBuilder().withPerson(AMY).build();
        updatedToBob.updatePerson(AMY, BOB);

        ClubBook expectedClubBook = new ClubBookBuilder().withPerson(BOB).build();

        assertEquals(expectedClubBook, updatedToBob);
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clubBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        ClubBook newData = getTypicalClubBook();
        clubBook.resetData(newData);
        assertEquals(newData, clubBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Person> newPersons = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        ClubBookStub newData = new ClubBookStub(newPersons, newTags);

        thrown.expect(AssertionError.class);
        clubBook.resetData(newData);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clubBook.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clubBook.getTagList().remove(0);
    }

    @Test
    public void updatePerson_detailsChanged_personsAndTagsListUpdated() throws Exception {
        ClubBook clubBookUpdatedToAmy = new ClubBookBuilder().withPerson(BOB).build();
        clubBookUpdatedToAmy.updatePerson(BOB, AMY);

        ClubBook expectedClubBook = new ClubBookBuilder().withPerson(AMY).build();

        assertEquals(expectedClubBook, clubBookUpdatedToAmy);
    }

    @Test
    public void deleteTag_nonExistentTag_addressBookUnchanged() {
        try {
            clubBookWithBobAndAmy.deleteTag(new Tag(VALID_TAG_UNUSED));
        } catch (TagNotFoundException tnfe) {
            ClubBook expectedClubBook = new ClubBookBuilder().withPerson(BOB).withPerson(AMY).build();
            assertEquals(expectedClubBook, clubBookWithBobAndAmy);
        }
    }

    @Test
    public void deleteTag_tagUsedByMultiplePersons_tagRemoved() throws Exception {
        clubBookWithBobAndAmy.deleteTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutFriendTag = new PersonBuilder(AMY).withTags().build();
        Person bobWithoutFriendTag = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withPerson(bobWithoutFriendTag)
                .withPerson(amyWithoutFriendTag).build();

        assertEquals(expectedClubBook, clubBookWithBobAndAmy);
    }

    /**
     * A stub ReadOnlyClubBook whose persons and tags lists can violate interface constraints.
     */
    private static class ClubBookStub implements ReadOnlyClubBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        ClubBookStub(Collection<Person> persons, Collection<? extends Tag> tags) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
