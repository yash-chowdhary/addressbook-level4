package seedu.club.logic.commands;
//@@author yash-chowdhary
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.club.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.club.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalTasks.getTypicalClubBookWithTasks;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.club.commons.core.index.Index;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.task.Status;
import seedu.club.model.task.Task;

public class ChangeTaskStatusCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalClubBookWithTasks(), new UserPrefs());
        model.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
    }

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ChangeTaskStatusCommand(null, null);
    }

    @Test
    public void execute_taskAccepted_changeSuccessful() throws Exception {
        Task taskToEdit = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        ChangeTaskStatusCommand changeTaskStatusCommand = prepareCommand(INDEX_FIRST_TASK,
                new Status(Status.IN_PROGRESS_STATUS));
        changeTaskStatusCommand.preprocessUndoableCommand();

        expectedModel = new ModelManager(getTypicalClubBookWithTasks(), new UserPrefs());
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        String expectedMessage = String.format(ChangeTaskStatusCommand.MESSAGE_CHANGE_SUCCESS,
                taskToEdit.getDescription().getDescription());
        Task editedTask = new Task(taskToEdit);
        editedTask.setStatus(new Status(Status.IN_PROGRESS_STATUS));
        expectedModel.changeStatus(taskToEdit, editedTask);

        assertCommandSuccess(changeTaskStatusCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsException() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ChangeTaskStatusCommand command = prepareCommand(invalidIndex,
                new Status(Status.IN_PROGRESS_STATUS));

        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ChangeTaskStatusCommand changeTaskStatusCommand = prepareCommand(outOfBoundIndex,
                new Status(Status.IN_PROGRESS_STATUS));

        assertCommandFailure(changeTaskStatusCommand, model, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Task taskToEdit = model.getClubBook().getTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task editedTask = new Task(taskToEdit);
        editedTask.setStatus(new Status(Status.COMPLETED_STATUS));
        ChangeTaskStatusCommand changeTaskStatusCommand = prepareCommand(INDEX_FIRST_TASK,
                new Status(Status.IN_PROGRESS_STATUS));

        expectedModel = new ModelManager(getTypicalClubBookWithTasks(), new UserPrefs());
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        changeTaskStatusCommand.execute();
        undoRedoStack.push(changeTaskStatusCommand);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.changeStatus(taskToEdit, editedTask);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ChangeTaskStatusCommand changeTaskStatusCommand = prepareCommand(outOfBoundIndex,
                new Status(Status.IN_PROGRESS_STATUS));

        assertCommandFailure(changeTaskStatusCommand, model, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    private ChangeTaskStatusCommand prepareCommand(Index index, Status status) {
        ChangeTaskStatusCommand changeTaskStatusCommand = new ChangeTaskStatusCommand(index, status);
        changeTaskStatusCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changeTaskStatusCommand;
    }

    @Test
    public void equals() throws Exception {
        ChangeTaskStatusCommand changeTaskStatusFirstCommand = prepareCommand(INDEX_FIRST_TASK,
                new Status(Status.IN_PROGRESS_STATUS));
        ChangeTaskStatusCommand changeTaskStatusSecondCommand = prepareCommand(INDEX_SECOND_TASK,
                new Status(Status.COMPLETED_STATUS));

        changeTaskStatusFirstCommand.preprocessUndoableCommand();
        changeTaskStatusSecondCommand.preprocessUndoableCommand();

        // same object -> returns true
        assertTrue(changeTaskStatusFirstCommand.equals(changeTaskStatusFirstCommand));

        // same values -> returns true
        ChangeTaskStatusCommand changeTaskStatusFirstCommandCopy = prepareCommand(INDEX_FIRST_TASK,
                new Status(Status.IN_PROGRESS_STATUS));
        assertTrue(changeTaskStatusFirstCommand.equals(changeTaskStatusFirstCommandCopy));

        // different types -> returns false
        assertFalse(changeTaskStatusFirstCommand.equals(1));

        // null -> returns false
        assertFalse(changeTaskStatusFirstCommand.equals(null));

        // different member -> returns false
        assertFalse(changeTaskStatusFirstCommand.equals(changeTaskStatusSecondCommand));
    }
}
