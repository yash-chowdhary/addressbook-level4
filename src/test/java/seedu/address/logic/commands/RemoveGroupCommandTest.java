package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.MANDATORY_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.NON_EXISTENT_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;


public class RemoveGroupCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validGroup_success() throws Exception {
        Group groupToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getGroup();
        RemoveGroupCommand removeGroupCommand = prepareCommand(ALICE.getGroup());

        String expectedMessage = String.format(RemoveGroupCommand.MESSAGE_SUCCESS, groupToDelete);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeGroup(groupToDelete);


        assertCommandSuccess(removeGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentGroup_throwsCommandException() {
        Group nonExistentGroup = new Group(NON_EXISTENT_GROUP);
        RemoveGroupCommand removeGroupCommand = prepareCommand(nonExistentGroup);

        assertCommandFailure(removeGroupCommand, model, RemoveGroupCommand.MESSAGE_INVALID_GROUP);
    }

    @Test
    public void execute_mandatoryGroup_throwsCommandException() {
        Group mandatoryGroup = new Group(MANDATORY_GROUP);
        RemoveGroupCommand removeGroupCommand = prepareCommand(mandatoryGroup);

        assertCommandFailure(removeGroupCommand, model, RemoveGroupCommand.MESSAGE_MANDATORY_GROUP);
    }

    @Test
    public void executeUndoRedo_validGroup_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Group groupToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getGroup();
        RemoveGroupCommand removeGroupCommand = prepareCommand(ALICE.getGroup());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // remove -> group removed
        removeGroupCommand.execute();
        undoRedoStack.push(removeGroupCommand);

        // undo -> reverts Club book back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same group deleted again
        expectedModel.removeGroup(groupToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_nonExistentGroup_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Group nonExistentGroup = new Group(NON_EXISTENT_GROUP);
        RemoveGroupCommand removeGroupCommand = prepareCommand(nonExistentGroup);

        // execution failed -> removeGroupCommand not pushed onto undoRedoStack
        assertCommandFailure(removeGroupCommand, model, RemoveGroupCommand.MESSAGE_INVALID_GROUP);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_mandatoryGroup_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Group mandatoryGroup = new Group(MANDATORY_GROUP);
        RemoveGroupCommand removeGroupCommand = prepareCommand(mandatoryGroup);

        // execution failed -> removeGroupCommand not pushed onto undoRedoStack
        assertCommandFailure(removeGroupCommand, model, RemoveGroupCommand.MESSAGE_MANDATORY_GROUP);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_validGroup_sameGroupDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemoveGroupCommand removeGroupCommand = prepareCommand(ALICE.getGroup());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Group groupToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getGroup();
        // remove -> removes group
        removeGroupCommand.execute();
        undoRedoStack.push(removeGroupCommand);

        // undo -> reverts Club book back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.removeGroup(groupToDelete);
        assertEquals(groupToDelete, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getGroup());
        // redo -> removes the same group
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        RemoveGroupCommand firstCommand = prepareCommand(new Group(VALID_GROUP_AMY));
        RemoveGroupCommand secondCommand = prepareCommand(new Group(VALID_GROUP_BOB));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(secondCommand.equals(secondCommand));

        // same values -> return true
        RemoveGroupCommand firstCommandCopy = prepareCommand(new Group(VALID_GROUP_AMY));
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(true));

        // null -> returns false
        assertFalse(secondCommand.equals(null));

        // different group -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private RemoveGroupCommand prepareCommand(Group group) {
        RemoveGroupCommand removeGroupCommand = new RemoveGroupCommand(group);
        removeGroupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeGroupCommand;
    }
}
