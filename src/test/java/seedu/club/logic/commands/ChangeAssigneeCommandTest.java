package seedu.club.logic.commands;
//@@author yash-chowdhary
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.club.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.club.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.club.testutil.TypicalIndexes.INDEX_THIRD_TASK;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalMembers.BENSON;
import static seedu.club.testutil.TypicalMembers.CARL;
import static seedu.club.testutil.TypicalTasks.BOOK_AUDITORIUM;
import static seedu.club.testutil.TypicalTasks.BOOK_AUDITORIUM_COPY;
import static seedu.club.testutil.TypicalTasks.BUY_FOOD;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.model.ClubBook;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.member.Member;
import seedu.club.model.task.Assignee;
import seedu.club.model.task.Task;
import seedu.club.testutil.ClubBookBuilder;
import seedu.club.testutil.MemberBuilder;
import seedu.club.testutil.TaskBuilder;

public class ChangeAssigneeCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;

    @Before
    public void setUp() {
        ClubBook clubBook = new ClubBookBuilder()
                .withMember(ALICE)
                .withMember(BENSON)
                .withTask(BUY_FOOD)
                .withTask(BOOK_AUDITORIUM)
                .withTask(BOOK_AUDITORIUM_COPY)
                .build();
        model = new ModelManager(clubBook, new UserPrefs());
        model.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
    }

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ChangeAssigneeCommand(null, null);
    }

    @Test
    public void execute_validAssignee_success() throws Exception {
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        Member alice = new MemberBuilder(ALICE).build();
        Member benson = new MemberBuilder(BENSON).build();
        Task buyFood = new TaskBuilder().withDescription(BUY_FOOD.getDescription().getDescription())
                .withDate(BUY_FOOD.getDate().getDate())
                .withTime(BUY_FOOD.getTime().getTime())
                .withAssignor(alice.getMatricNumber().toString())
                .withAssignee(benson.getMatricNumber().toString())
                .withStatus(BUY_FOOD.getStatus().getStatus())
                .build();
        Task bookAuditorium = new TaskBuilder(BOOK_AUDITORIUM).build();
        String expectedMessage = String.format(ChangeAssigneeCommand.MESSAGE_CHANGE_SUCCESS,
                buyFood.getDescription().getDescription(), benson.getMatricNumber().toString());

        ClubBook expectedClubBook = new ClubBookBuilder()
                .withMember(alice)
                .withMember(benson)
                .withTask(buyFood)
                .withTask(bookAuditorium)
                .build();

        expectedModel = new ModelManager(expectedClubBook, new UserPrefs());
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        ChangeAssigneeCommand command = prepareCommand(INDEX_THIRD_TASK,
                new Assignee(BENSON.getMatricNumber().toString()));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidAssignee_throwsException() {
        String expectedMessage = ChangeAssigneeCommand.MESSAGE_MEMBER_NOT_FOUND;

        ChangeAssigneeCommand command = prepareCommand(INDEX_FIRST_TASK,
                new Assignee(CARL.getMatricNumber().toString()));

        assertCommandFailure(command, model, expectedMessage);
    }


    @Test
    public void execute_unchangedAssignee_throwsException() {
        String expectedMessage = ChangeAssigneeCommand.MESSAGE_NOT_CHANGED;
        ChangeAssigneeCommand command = prepareCommand(INDEX_FIRST_TASK,
                new Assignee(ALICE.getMatricNumber().toString()));

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_duplicateTask_throwsException() {
        String expectedMessage = ChangeAssigneeCommand.MESSAGE_DUPLICATE_TASK;
        ChangeAssigneeCommand command = prepareCommand(INDEX_FIRST_TASK,
                new Assignee(BENSON.getMatricNumber().toString()));

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        ClubBook expectedClubBook = new ClubBookBuilder().withMember(ALICE).withMember(BENSON).withTask(BUY_FOOD)
                .withTask(BOOK_AUDITORIUM).build();

        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Task taskToEdit = new TaskBuilder(BUY_FOOD).build();
        Task editedTask = new TaskBuilder(taskToEdit).build();
        editedTask.setAssignee(new Assignee(BENSON.getMatricNumber().toString()));
        ChangeAssigneeCommand changeAssigneeCommand = prepareCommand(INDEX_SECOND_TASK,
                new Assignee(BENSON.getMatricNumber().toString()));

        expectedModel = new ModelManager(expectedClubBook, new UserPrefs());
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        changeAssigneeCommand.execute();
        undoRedoStack.push(changeAssigneeCommand);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.changeAssignee(taskToEdit, editedTask);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ChangeAssigneeCommand changeAssigneeCommand = prepareCommand(outOfBoundIndex,
                new Assignee(BENSON.getMatricNumber().toString()));

        assertCommandFailure(changeAssigneeCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ChangeAssigneeCommand changeAssigneeCommand = prepareCommand(outOfBoundIndex,
                new Assignee(BENSON.getMatricNumber().toString()));

        assertCommandFailure(changeAssigneeCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private ChangeAssigneeCommand prepareCommand(Index index, Assignee assignee) {
        ChangeAssigneeCommand changeAssigneeCommand = new ChangeAssigneeCommand(index, assignee);
        changeAssigneeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changeAssigneeCommand;
    }

    @Test
    public void equals() throws Exception {
        ChangeAssigneeCommand changeAssigneeFirstCommand = prepareCommand(INDEX_FIRST_TASK,
                new Assignee(BENSON.getMatricNumber().toString()));
        ChangeAssigneeCommand changeAssigneeSecondCommand = prepareCommand(INDEX_FIRST_TASK,
                new Assignee(CARL.getMatricNumber().toString()));

        changeAssigneeFirstCommand.preprocessUndoableCommand();
        changeAssigneeSecondCommand.preprocessUndoableCommand();

        // same object -> returns true
        assertTrue(changeAssigneeFirstCommand.equals(changeAssigneeFirstCommand));

        // same values -> returns true
        ChangeAssigneeCommand changeAssigneeFirstCommandCopy = prepareCommand(INDEX_FIRST_TASK,
                new Assignee(BENSON.getMatricNumber().toString()));
        assertTrue(changeAssigneeFirstCommand.equals(changeAssigneeFirstCommandCopy));

        // different types -> returns false
        assertFalse(changeAssigneeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(changeAssigneeFirstCommand.equals(null));

        // different member -> returns false
        assertFalse(changeAssigneeFirstCommand.equals(changeAssigneeSecondCommand));
    }

}
