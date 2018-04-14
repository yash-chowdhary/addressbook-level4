package seedu.club.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.club.logic.commands.CommandTestUtil.MANDATORY_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_HEAD;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.club.model.Model.PREDICATE_SHOW_ALL_MEMBERS;
import static seedu.club.model.member.ProfilePhoto.DEFAULT_PHOTO_PATH;
import static seedu.club.storage.ProfilePhotoStorage.PHOTO_FILE_EXTENSION;
import static seedu.club.storage.ProfilePhotoStorage.SAVE_PHOTO_DIRECTORY;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalMembers.AMY;
import static seedu.club.testutil.TypicalMembers.BENSON;
import static seedu.club.testutil.TypicalMembers.BOB;
import static seedu.club.testutil.TypicalMembers.CARL;
import static seedu.club.testutil.TypicalMembers.DANIEL;
import static seedu.club.testutil.TypicalMembers.ELLE;
import static seedu.club.testutil.TypicalTasks.BOOK_AUDITORIUM;
import static seedu.club.testutil.TypicalTasks.BUY_CONFETTI;
import static seedu.club.testutil.TypicalTasks.BUY_FOOD;

import java.io.File;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.club.commons.events.model.ClubBookChangedEvent;
import seedu.club.commons.events.model.NewExportDataAvailableEvent;
import seedu.club.logic.commands.ViewMyTasksCommand;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.Member;
import seedu.club.model.member.NameContainsKeywordsPredicate;
import seedu.club.model.member.ProfilePhoto;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;
import seedu.club.model.task.Assignee;
import seedu.club.model.task.Assignor;
import seedu.club.model.task.Status;
import seedu.club.model.task.Task;
import seedu.club.model.task.TaskIsRelatedToMemberPredicate;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskAlreadyAssignedException;
import seedu.club.model.task.exceptions.TaskAssigneeUnchangedException;
import seedu.club.model.task.exceptions.TaskCannotBeDeletedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;
import seedu.club.model.task.exceptions.TaskStatusCannotBeEditedException;
import seedu.club.model.task.exceptions.TasksAlreadyListedException;
import seedu.club.testutil.Assert;
import seedu.club.testutil.ClubBookBuilder;
import seedu.club.testutil.MemberBuilder;
import seedu.club.testutil.TaskBuilder;
import seedu.club.ui.testutil.EventsCollectorRule;

public class ModelManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

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
            modelManager.deleteGroup(new Group(NON_EXISTENT_GROUP));
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
            modelManager.deleteGroup(new Group(MANDATORY_GROUP));
        } catch (GroupCannotBeRemovedException e) {
            assertEquals(new ModelManager(clubBook, userPrefs), modelManager);
        }
    }

    @Test
    public void deleteGroup_atLeastOneMemberInGroup_groupDeleted() throws Exception {

        ClubBook clubBookWithDanielAndElle = new ClubBookBuilder().withMember(DANIEL).withMember(ELLE)
                .build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBookWithDanielAndElle, userPrefs);
        clubBookWithDanielAndElle.deleteGroup(new Group(DANIEL.getGroup().toString()));

        Member bensonNotInPr = new MemberBuilder(DANIEL).withGroup().build();
        Member aliceNotInPr = new MemberBuilder(ELLE).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(aliceNotInPr)
                .withMember(bensonNotInPr).build();

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
                new Tag(VALID_TAG_HEAD));
        modelManager.sendEmail(expectedRecipients, new Client(Client.VALID_CLIENT_GMAIL),
                new Subject(Subject.TEST_SUBJECT_STRING), new Body(Body.TEST_BODY_STRING));

        assertEquals(new ModelManager(clubBook, userPrefs), modelManager);
    }

    @Test
    public void addTask_validTask_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        modelManager.addTaskToTaskList(BUY_FOOD);

        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        assertEquals(expectedModel, modelManager);
    }

    @Test
    public void addTask_duplicateTask_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        try {
            modelManager.addTaskToTaskList(BUY_CONFETTI);
        } catch (DuplicateTaskException dte) {
            assertEquals(expectedModel, modelManager);
        }
    }

    @Test
    public void assignTask_validTask_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BOB).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        modelManager.assignTask(BUY_FOOD, BOB.getMatricNumber());


        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        boolean isEqual = expectedModel.equals(modelManager);
        assertEquals(expectedModel, modelManager);
    }

    @Test
    public void assignTask_duplicateTask_throwsException() {
        Task buyFood = new TaskBuilder()
                .withDescription("Buy Food")
                .withDate("02/05/2018")
                .withTime("19:00")
                .withAssignor("Alice Pauline")
                .withAssignee("Bob Choo")
                .withStatus("Yet To Begin")
                .build();
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withMember(BOB).withTask(buyFood).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        try {
            modelManager.assignTask(BUY_FOOD, BOB.getMatricNumber());
        } catch (DuplicateTaskException dte) {
            assertEquals(expectedModel, modelManager);
        } catch (MemberNotFoundException | TaskAlreadyAssignedException e) {
            fail("This exception should not be caught");
        }
    }

    @Test
    public void assignTask_memberNotFound_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withTask(BUY_FOOD).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        try {
            modelManager.assignTask(BUY_CONFETTI, BOB.getMatricNumber());
        } catch (DuplicateTaskException | TaskAlreadyAssignedException e) {
            fail("This exception should not be caught");
        } catch (MemberNotFoundException mnfe) {
            assertEquals(expectedModel, modelManager);
        }
    }

    @Test
    public void assignTask_invalidPermission_throwsExcpetion() {
        ClubBook clubBook = new ClubBookBuilder().withMember(BOB).withTask(BUY_FOOD).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(BOB.getCredentials().getUsername().value, BOB.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(BOB.getCredentials().getUsername().value, BOB.getCredentials().getPassword().value);

        try {
            modelManager.assignTask(BUY_CONFETTI, BOB.getMatricNumber());
        } catch (DuplicateTaskException | MemberNotFoundException | TaskAlreadyAssignedException e) {
            fail("This exception should not be caught");
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
        } catch (TaskNotFoundException | TaskCannotBeDeletedException e) {
            assertEquals(expectedModel, modelManager);
        }
    }

    @Test
    public void changeTaskStatus_validTask_success() {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        Task taskToEdit = new Task(BUY_FOOD);
        Task editedTask = new Task(taskToEdit);
        editedTask.setStatus(new Status(Status.IN_PROGRESS_STATUS));

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(ALICE).withTask(editedTask).withTask(BUY_CONFETTI)
                .build();
        ModelManager expectedModel = new ModelManager(expectedClubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        try {
            modelManager.changeStatus(taskToEdit, editedTask);
        } catch (TaskNotFoundException | DuplicateTaskException | TaskStatusCannotBeEditedException e) {
            assertEquals(expectedModel, modelManager);
        }

        assertEquals(expectedModel, modelManager);
    }

    @Test
    public void changeTaskStatus_noChangeToStatus_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        Task taskToEdit = new Task(BUY_FOOD);
        Task editedTask = new Task(taskToEdit);

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        try {
            modelManager.changeStatus(taskToEdit, editedTask);
        } catch (DuplicateTaskException | TaskNotFoundException | TaskStatusCannotBeEditedException e) {
            assertEquals(expectedModel, modelManager);
        }
    }

    @Test
    public void changeTaskStatus_invalidPermission_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(AMY).withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        Task taskToEdit = new Task(BUY_FOOD);
        Task editedTask = new Task(taskToEdit);
        editedTask.setStatus(new Status(Status.IN_PROGRESS_STATUS));

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(AMY.getCredentials().getUsername().value, AMY.getCredentials().getPassword().value);

        try {
            modelManager.changeStatus(taskToEdit, editedTask);
        } catch (TaskStatusCannotBeEditedException e) {
            assertEquals(expectedModel, modelManager);
        } catch (TaskNotFoundException | DuplicateTaskException e) {
            fail("This will not execute");
        }
    }

    @Test
    public void changeAssignee_validAssignee_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE)
                .withMember(BENSON)
                .withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        Task taskToEdit = new Task(BUY_FOOD);
        Task editedTask = new Task(taskToEdit);
        editedTask.setAssignee(new Assignee(BENSON.getMatricNumber().toString()));

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        modelManager.changeAssignee(taskToEdit, editedTask);

        assertEquals(expectedModel, modelManager);
    }

    @Test
    public void changeAssignee_invalidMember_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE)
                .withMember(BENSON)
                .withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        Task taskToEdit = new Task(BUY_FOOD);
        Task editedTask = new Task(taskToEdit);
        editedTask.setAssignee(new Assignee(CARL.getMatricNumber().toString()));

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        try {
            modelManager.changeAssignee(taskToEdit, editedTask);
        } catch (MemberNotFoundException mnfe) {
            assertEquals(expectedModel, modelManager);
        } catch (TaskAlreadyAssignedException | DuplicateTaskException | TaskAssigneeUnchangedException e) {
            return;
        }
    }

    @Test
    public void changeAssignee_unchangedAssignee_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE)
                .withMember(BENSON)
                .withTask(BUY_FOOD).withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        Task taskToEdit = new Task(BUY_FOOD);
        Task editedTask = new Task(taskToEdit);

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        try {
            modelManager.changeAssignee(taskToEdit, editedTask);
        } catch (TaskAssigneeUnchangedException e) {
            assertEquals(expectedModel, modelManager);
        } catch (TaskAlreadyAssignedException | DuplicateTaskException | MemberNotFoundException e) {
            return;
        }
    }

    @Test
    public void changeAssignee_taskAlreadyAssigned_throwsException() {
        Task buyFoodModified = new TaskBuilder(BUY_FOOD).build();
        buyFoodModified.setAssignor(new Assignor(BENSON.getMatricNumber().toString()));
        buyFoodModified.setAssignee(new Assignee(BENSON.getMatricNumber().toString()));
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE)
                .withMember(BENSON)
                .withTask(BUY_FOOD).withTask(buyFoodModified).build();
        UserPrefs userPrefs = new UserPrefs();

        Task taskToEdit = new Task(BUY_FOOD);
        Task editedTask = new Task(taskToEdit);
        editedTask.setAssignee(new Assignee(BENSON.getMatricNumber().toString()));

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        try {
            modelManager.changeAssignee(taskToEdit, editedTask);
        } catch (TaskAlreadyAssignedException e) {
            assertEquals(expectedModel, modelManager);
        } catch (MemberNotFoundException | DuplicateTaskException | TaskAssigneeUnchangedException e) {
            return;
        }
    }

    @Test
    public void viewAllTasks_validPermission_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).withTask(BUY_FOOD)
                .withTask(BUY_CONFETTI).build();
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
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON)
                .withTask(BUY_CONFETTI).withTask(BUY_FOOD).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(BENSON.getCredentials().getUsername().value,
                BENSON.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(BENSON.getCredentials().getUsername().value,
                BENSON.getCredentials().getPassword().value);
        try {
            modelManager.viewAllTasks();
        } catch (TasksAlreadyListedException e) {
            assertEquals(expectedModel, modelManager);
        }
    }

    @Test
    public void viewMyTasks_validSwitch_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).withTask(BUY_FOOD)
                .withTask(BOOK_AUDITORIUM).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        expectedModel.updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(ALICE));
        modelManager.viewAllTasks();
        modelManager.viewMyTasks();
        assertEquals(expectedModel, modelManager);
    }

    @Test
    public void viewMyTasks_tasksAlreadyListed_throwsException() {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).withTask(BUY_FOOD)
                .withTask(BOOK_AUDITORIUM).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        modelManager.updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(ALICE));

        ModelManager expectedModel = new ModelManager(clubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        expectedModel.updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(ALICE));

        String expectedMessage = ViewMyTasksCommand.MESSAGE_ALREADY_LISTED;
        Assert.assertThrows(TasksAlreadyListedException.class, modelManager::viewMyTasks);
        Assert.assertThrows(TasksAlreadyListedException.class, expectedMessage, modelManager::viewMyTasks);
    }

    @Test
    public void deleteMember_validMemberWithTasks_success() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).withTask(BOOK_AUDITORIUM)
                .withTask(BUY_CONFETTI).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        modelManager.deleteMember(BENSON);

        ClubBook expectedClubBook = new ClubBookBuilder().withMember(ALICE).withTask(BUY_CONFETTI).build();
        ModelManager expectedModel = new ModelManager(expectedClubBook, userPrefs);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        assertEquals(expectedModel, modelManager);
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
        modelManager.deleteTag(new Tag(VALID_TAG_HEAD));

        Member amyWithoutFriendTag = new MemberBuilder(AMY).withTags().build();
        Member bobWithoutFriendTag = new MemberBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(amyWithoutFriendTag)
                .withMember(bobWithoutFriendTag).build();
        ModelManager expectedModel = new ModelManager(expectedClubBook, userPrefs);
        expectedModel.updateFilteredMemberList(expectedModel.PREDICATE_SHOW_ALL_MEMBERS);
        assertEquals(expectedModel, modelManager);
    }

    //@@author amrut-prabhu
    @Test
    public void addProfilePhoto_eventRaised() throws Exception {
        String photoDirectory = "./src/test/resources/photos/";
        String photoFileName = "testPhoto.png";


        ClubBook clubBook = new ClubBookBuilder().withMember(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        modelManager.logsInMember(BENSON.getCredentials().getUsername().value,
                BENSON.getCredentials().getPassword().value);

        ProfilePhoto newPhoto = new ProfilePhoto(SAVE_PHOTO_DIRECTORY + BENSON.getMatricNumber()
                + PHOTO_FILE_EXTENSION);

        modelManager.addProfilePhoto(photoDirectory + photoFileName);

        //2 events are raised: ProfilePhotoChangedEvent and ClubBookChangedEvent
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 2);
        //Last event raised is ClubBookChangedEvent
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ClubBookChangedEvent);

        assertEquals(newPhoto, modelManager.getLoggedInMember().getProfilePhoto());
    }

    @Test
    public void removeProfilePhoto_success() {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(clubBook, userPrefs);
        ProfilePhoto defaultPhoto = new ProfilePhoto(DEFAULT_PHOTO_PATH);

        modelManager.logsInMember(BENSON.getCredentials().getUsername().value,
                BENSON.getCredentials().getPassword().value);

        modelManager.removeProfilePhoto();

        assertEquals(defaultPhoto, modelManager.getLoggedInMember().getProfilePhoto());
    }

    @Test
    public void exportClubConnectMembers_eventRaised() throws Exception {
        ClubBook clubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(clubBook, userPrefs);

        File exportFile = temporaryFolder.newFile("actual.csv");

        modelManager.exportClubConnectMembers(exportFile);

        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof NewExportDataAvailableEvent);
    }
    //@@author

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
