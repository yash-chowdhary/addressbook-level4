package seedu.club.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.MANDATORY_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.club.model.Model.PREDICATE_SHOW_ALL_MEMBERS;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalMembers.AMY;
import static seedu.club.testutil.TypicalMembers.BENSON;
import static seedu.club.testutil.TypicalMembers.BOB;
import static seedu.club.testutil.TypicalTasks.BOOK_AUDITORIUM;
import static seedu.club.testutil.TypicalTasks.BUY_CONFETTI;
import static seedu.club.testutil.TypicalTasks.BUY_FOOD;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.Member;
import seedu.club.model.member.NameContainsKeywordsPredicate;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskCannotBeDeletedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;
import seedu.club.model.task.exceptions.TasksCannotBeDisplayedException;
import seedu.club.testutil.ClubBookBuilder;
import seedu.club.testutil.MemberBuilder;
import seedu.club.testutil.TaskBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredMemberList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredMemberList().remove(0);
    }

    //@@author yash-chowdhary
    @Test
    public void removeGroup_nonExistentGroup_modelUnchanged() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        try {
            modelManager.removeGroup(new Group(NON_EXISTENT_GROUP));
        } catch (GroupNotFoundException gnfe) {
            assertEquals(new ModelManager(clubBook, userPrefs), modelManager);
        }
    }

    @Test
    public void removeGroup_mandatoryGroup_modelUnchanged() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        try {
            modelManager.removeGroup(new Group(MANDATORY_GROUP));
        } catch (GroupCannotBeRemovedException e) {
            assertEquals(new ModelManager(clubBook, userPrefs), modelManager);
        }
    }

    @Test
    public void removeGroup_atLeastOneMemberInGroup_groupRemoved() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.removeGroup(new Group(VALID_GROUP_AMY));

        Member amyNotInPublicity = new MemberBuilder(AMY).withGroup().build();
        Member bobNotInPublicity = new MemberBuilder(BOB).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(amyNotInPublicity)
                .withMember(bobNotInPublicity).build();

        assertEquals(new ModelManager(expectedClubBook, userPrefs), modelManager);

    }

    @Test
    public void emailGroup_nonExistentGroup_throwsException() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        try {
            String expectedRecipients = modelManager.generateEmailRecipients(new Group(NON_EXISTENT_GROUP),
                    null);
        } catch (GroupNotFoundException gnfe) {
            assertEquals(new ModelManager(clubBook, userPrefs), modelManager);
        }
    }

    @Test
    public void emailTag_nonExistentTag_throwsException() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        try {
            String expectedRecipients = modelManager.generateEmailRecipients(null,
                    new Tag(VALID_TAG_UNUSED));
        } catch (TagNotFoundException tnfe) {
            assertEquals(new ModelManager(clubBook, userPrefs), modelManager);
        }
    }

    @Test
    public void emailGroup_validGroup_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        String expectedRecipients = modelManager.generateEmailRecipients(new Group(VALID_GROUP_AMY),
                null);
        modelManager.sendEmail(expectedRecipients, new Client(Client.VALID_CLIENT_GMAIL),
                new Subject(Subject.TEST_SUBJECT_STRING), new Body(Body.TEST_BODY_STRING));

        assertEquals(new ModelManager(clubBook, userPrefs), modelManager);
    }

    @Test
    public void emailTag_validTag_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        String expectedRecipients = modelManager.generateEmailRecipients(null,
                new Tag(VALID_TAG_FRIEND));
        modelManager.sendEmail(expectedRecipients, new Client(Client.VALID_CLIENT_GMAIL),
                new Subject(Subject.TEST_SUBJECT_STRING), new Body(Body.TEST_BODY_STRING));

        assertEquals(new ModelManager(clubBook, userPrefs), modelManager);
    }

    @Test
    public void addTask_validTask_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        modelManager.addTaskToTaskList(BUY_FOOD);
        Member amy = new MemberBuilder(AMY).build();
        Task buyFood = new TaskBuilder(BUY_FOOD).build();
        Task buyConfetti = new TaskBuilder(BUY_CONFETTI).build();
        ClubBook expectedClubBook = new ClubBookBuilder()
                .withMember(amy)
                .withTask(buyConfetti)
                .withTask(buyFood)
                .build();
        ModelManager expectedModel = new ModelManager(expectedClubBook, userPrefs);
        expectedModel.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        assertEquals(expectedModel, modelManager);
    }

    @Test
    public void addTask_duplicateTask_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        try {
            modelManager.addTaskToTaskList(BUY_CONFETTI);
        } catch (DuplicateTaskException dte) {
            assertEquals(expectedModel, modelManager);
        }
    }

    @Test
    public void deleteTask_invalidTask_throwsException() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withTask(BUY_CONFETTI).withTask(BUY_FOOD).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        try {
            modelManager.deleteTask(BOOK_AUDITORIUM);
        } catch (TaskNotFoundException tnfe) {
            assertEquals(expectedModel, modelManager);
        } catch (TaskCannotBeDeletedException e) {
            assertEquals(expectedModel, modelManager);
        }
    }

    @Test
    public void viewAllTasks_validPermission_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        modelManager.viewAllTasks();
        assertEquals(expectedModel, modelManager);
    }

    @Test
    public void viewAllTasks_invalidPermission_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(BENSON.getCredentials().getUsername().value,
                BENSON.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(BENSON.getCredentials().getUsername().value,
                BENSON.getCredentials().getPassword().value);
        try {
            modelManager.viewAllTasks();
        } catch (TasksCannotBeDisplayedException tdbde) {
            assertEquals(expectedModel, modelManager);
        }
    }
    //@@author

    @Test
    public void deleteTag_nonExistentTag_modelUnchanged() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        try {
            modelManager.deleteTag(new Tag(VALID_TAG_UNUSED));
        } catch (TagNotFoundException tnfe) {
            assertEquals(new ModelManager(clubBook, userPrefs), modelManager);
        }
    }

    @Test
    public void deleteTag_tagUsedByMultipleMembers_tagRemoved() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.updateFilteredMemberList(modelManager.PREDICATE_SHOW_ALL_MEMBERS);
        modelManager.deleteTag(new Tag(VALID_TAG_FRIEND));

        Member amyWithoutFriendTag = new MemberBuilder(AMY).withTags().build();
        Member bobWithoutFriendTag = new MemberBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(amyWithoutFriendTag)
                .withMember(bobWithoutFriendTag).build();
        ModelManager expectedModel = new ModelManager(expectedClubBook, userPrefs);
        expectedModel.updateFilteredMemberList(expectedModel.PREDICATE_SHOW_ALL_MEMBERS);
        assertEquals(expectedModel, modelManager);
    }

    @Test
    public void equals() {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).build();
        ClubBook differentClubBook = new ClubBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(clubBook, userPrefs);
        modelManager.updateFilteredMemberList(modelManager.PREDICATE_SHOW_ALL_MEMBERS);
        modelManagerCopy.updateFilteredMemberList(modelManagerCopy.PREDICATE_SHOW_ALL_MEMBERS);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different clubBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentClubBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredMemberList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(clubBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setClubBookName("differentName");
        ModelManager expectedMode1 = new ModelManager(clubBook, differentUserPrefs);
        expectedMode1.updateFilteredMemberList(expectedMode1.PREDICATE_SHOW_ALL_MEMBERS);
        assertTrue(modelManager.equals(expectedMode1));
    }
}
