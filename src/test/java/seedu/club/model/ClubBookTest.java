package seedu.club.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.club.logic.commands.CommandTestUtil.MANDATORY_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalMembers.AMY;
import static seedu.club.testutil.TypicalMembers.BOB;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;
import static seedu.club.testutil.TypicalTasks.BOOK_AUDITORIUM;
import static seedu.club.testutil.TypicalTasks.BUY_CONFETTI;
import static seedu.club.testutil.TypicalTasks.BUY_FOOD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.Member;
import seedu.club.model.poll.Poll;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskNotFoundException;
import seedu.club.testutil.ClubBookBuilder;
import seedu.club.testutil.MemberBuilder;
import seedu.club.testutil.TaskBuilder;

public class ClubBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ClubBook clubBook = new ClubBook();
    private final ClubBook clubBookWithBobAndAmy = new ClubBookBuilder().withMember(BOB).withMember(AMY)
            .build();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), clubBook.getMemberList());
        assertEquals(Collections.emptyList(), clubBook.getTagList());
    }

    @Test
    public void removeGroup_nonExistentGroup_unchangedClubBook() throws Exception {
        try {
            clubBookWithBobAndAmy.removeGroup(new Group(NON_EXISTENT_GROUP));
        } catch (GroupNotFoundException gnfe) {
            ClubBook expectedClubBook = new ClubBookBuilder().withMember(BOB).withMember(AMY).build();
            assertEquals(expectedClubBook, clubBookWithBobAndAmy);
        }
    }

    @Test
    public void removeGroup_mandatoryGroup_unchangedClubBook() throws Exception {
        try {
            clubBookWithBobAndAmy.removeGroup(new Group(MANDATORY_GROUP));
        } catch (GroupCannotBeRemovedException e) {
            ClubBook expectedClubBook = new ClubBookBuilder().withMember(BOB).withMember(AMY).build();
            assertEquals(expectedClubBook, clubBookWithBobAndAmy);
        }
    }

    @Test
    public void removeGroup_atLeastOneMemberInGroup_groupRemoved() throws Exception {
        clubBookWithBobAndAmy.removeGroup(new Group(VALID_GROUP_BOB));

        Member bobNotInLogistics = new MemberBuilder(BOB).withGroup().build();
        Member amyNotInLogistics = new MemberBuilder(AMY).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(bobNotInLogistics)
                .withMember(amyNotInLogistics).build();

        assertEquals(expectedClubBook, clubBookWithBobAndAmy);
    }

    @Test
    public void deleteTask_validTask_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();
        clubBook.deleteTask(BUY_CONFETTI);

        Member amy = new MemberBuilder(AMY).build();
        Task buyFood = new TaskBuilder(BUY_FOOD).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(amy).withTask(buyFood).build();

        assertEquals(expectedClubBook, clubBook);
    }

    @Test
    public void deleteTask_taskNotFound_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();
        try {
            clubBook.deleteTask(BOOK_AUDITORIUM);
        } catch (TaskNotFoundException tnfe) {
            Member amy = new MemberBuilder(AMY).build();
            Task buyFood = new TaskBuilder(BUY_FOOD).build();
            Task buyConfetti = new TaskBuilder(BUY_CONFETTI).build();
            ClubBook expectedClubBook = new ClubBookBuilder()
                    .withMember(amy)
                    .withTask(buyFood)
                    .withTask(buyConfetti)
                    .build();
            assertEquals(expectedClubBook, clubBook);
        }
    }

    @Test
    public void addTask_validTask_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY)
                .withTask(BUY_FOOD).build();
        clubBook.addTaskToTaskList(BUY_CONFETTI);

        Member amy = new MemberBuilder(AMY).build();
        Task buyFood = new TaskBuilder(BUY_FOOD).build();
        Task buyConfetti = new TaskBuilder(BUY_CONFETTI).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(amy).withTask(buyFood)
                .withTask(buyConfetti).build();

        assertEquals(expectedClubBook, clubBook);

    }

    @Test
    public void addTask_duplicateTask_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(BOB)
                .withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();

        Task toAdd = new TaskBuilder(BUY_CONFETTI).build();
        try {
            clubBook.addTaskToTaskList(toAdd);
        } catch (DuplicateTaskException dte) {
            Member bob = new MemberBuilder(BOB).build();
            Task buyFood = new TaskBuilder(BUY_FOOD).build();
            Task buyConfetti = new TaskBuilder(BUY_CONFETTI).build();
            ClubBook expectedClubBook = new ClubBookBuilder()
                    .withMember(bob)
                    .withTask(buyFood)
                    .withTask(buyConfetti)
                    .build();
            assertEquals(expectedClubBook, clubBook);
        }
    }

    @Test
    public void updateMember_detailsChanged_memberUpdated() throws Exception {
        ClubBook updatedToBob = new ClubBookBuilder().withMember(AMY).build();
        updatedToBob.updateMember(AMY, BOB);

        ClubBook expectedClubBook = new ClubBookBuilder().withMember(BOB).build();

        assertEquals(expectedClubBook, updatedToBob);
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clubBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyClubBook_replacesData() {
        ClubBook newData = getTypicalClubBook();
        clubBook.resetData(newData);
        assertEquals(newData, clubBook);
    }

    @Test
    public void resetData_withDuplicateMembers_throwsAssertionError() {
        // Repeat ALICE twice
        List<Member> newMembers = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        List<Poll> newPolls = new ArrayList<>();
        ClubBookStub newData = new ClubBookStub(newMembers, newTags, newPolls);

        thrown.expect(AssertionError.class);
        clubBook.resetData(newData);
    }

    @Test
    public void getMemberList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clubBook.getMemberList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clubBook.getTagList().remove(0);
    }

    @Test
    public void updateMember_detailsChanged_membersAndTagsListUpdated() throws Exception {
        ClubBook clubBookUpdatedToAmy = new ClubBookBuilder().withMember(BOB).build();
        clubBookUpdatedToAmy.updateMember(BOB, AMY);

        ClubBook expectedClubBook = new ClubBookBuilder().withMember(AMY).build();

        assertEquals(expectedClubBook, clubBookUpdatedToAmy);
    }

    @Test
    public void deleteTag_nonExistentTag_clubBookUnchanged() {
        try {
            clubBookWithBobAndAmy.deleteTag(new Tag(VALID_TAG_UNUSED));
        } catch (TagNotFoundException tnfe) {
            ClubBook expectedClubBook = new ClubBookBuilder().withMember(BOB).withMember(AMY).build();
            assertEquals(expectedClubBook, clubBookWithBobAndAmy);
        }
    }

    @Test
    public void deleteTag_tagUsedByMultipleMembers_tagRemoved() throws Exception {
        clubBookWithBobAndAmy.deleteTag(new Tag(VALID_TAG_FRIEND));

        Member amyWithoutFriendTag = new MemberBuilder(AMY).withTags().build();
        Member bobWithoutFriendTag = new MemberBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(bobWithoutFriendTag)
                .withMember(amyWithoutFriendTag).build();

        assertEquals(expectedClubBook, clubBookWithBobAndAmy);
    }

    /**
     * A stub ReadOnlyClubBook whose members and tags lists can violate interface constraints.
     */
    private static class ClubBookStub implements ReadOnlyClubBook {
        private final ObservableList<Member> members = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<Poll> polls = FXCollections.observableArrayList();
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        ClubBookStub(Collection<Member> members, Collection<? extends Tag> tags,
                     Collection<? extends Poll> polls) {
            this.members.setAll(members);
            this.tags.setAll(tags);
            this.polls.setAll(polls);
        }

        @Override
        public ObservableList<Member> getMemberList() {
            return members;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }

        @Override
        public ObservableList<Poll> getPollList() {
            return polls;
        }
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public void setPolls(Set<Poll> polls) {
            fail("This method should not be called.");
        }

        @Override
        public void setTasks(Set<Task> tasks) {
            fail("This method should not be called.");
        }
    }

}
