package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TAG;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TAG;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code RemoveTagCommand}.
 */
public class RemoveTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validTagUnfilteredList_success() throws Exception {
        Tag tagToRemove = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());

        RemoveTagCommand removeTagCommand = prepareCommand(tagToRemove);

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS, tagToRemove);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(tagToRemove);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTagUnfilteredList_throwsCommandException() throws Exception {
        Tag nonExistentTag = new Tag(VALID_TAG_UNUSED);
        RemoveTagCommand removeTagCommand = prepareCommand(nonExistentTag);

        assertCommandFailure(removeTagCommand, model, Messages.MESSAGE_INVALID_TAG);
    }

    @Test
    public void execute_validTagFilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Tag tagToRemove = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());

        RemoveTagCommand removeTagCommand = prepareCommand(tagToRemove);

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS, tagToRemove);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(tagToRemove);
        showNoTag(expectedModel);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTagFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Tag nonExistentTag = new Tag(VALID_TAG_UNUSED);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(!model.getAddressBook().getTagList().contains(nonExistentTag));

        RemoveTagCommand removeTagCommand = prepareCommand(nonExistentTag);

        assertCommandFailure(removeTagCommand, model, Messages.MESSAGE_INVALID_TAG);
    }

    @Test
    public void executeUndoRedo_validTagUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Tag tagToRemove = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());
        RemoveTagCommand removeTagCommand = prepareCommand(tagToRemove);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first tag removed
        removeTagCommand.execute();
        undoRedoStack.push(removeTagCommand);

        // undo -> reverts addressbook back to previous state and filtered tag list to show all tags
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first tag removed again
        expectedModel.removeTag(tagToRemove);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidTagUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Tag nonExistentTag = new Tag(VALID_TAG_UNUSED);
        RemoveTagCommand removeTagCommand = prepareCommand(nonExistentTag);

        // execution failed -> removeTagCommand not pushed into undoRedoStack
        assertCommandFailure(removeTagCommand, model, Messages.MESSAGE_INVALID_TAG);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Removes a {@code Tag} from a filtered list.
     * 2. Undo the removal.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} removes the tag object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validTagFilteredList_samePersonDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Tag tagToRemove = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());
        RemoveTagCommand removeTagCommand = prepareCommand(tagToRemove);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        // remove tag -> removes first tag in unfiltered tag list / filtered tag list
        removeTagCommand.execute();
        undoRedoStack.push(removeTagCommand);

        // undo -> reverts addressbook back to previous state and filtered tag list to show all tags
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.removeTag(tagToRemove);
        // redo -> removes same tag in unfiltered tag list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        RemoveTagCommand removeFirstTagCommand = prepareCommand(model.getFilteredTagList()
                .get(INDEX_FIRST_TAG.getZeroBased()));
        RemoveTagCommand removeSecondTagCommand = prepareCommand(model.getFilteredTagList()
                .get(INDEX_SECOND_TAG.getZeroBased()));

        // same object -> returns true
        assertTrue(removeFirstTagCommand.equals(removeFirstTagCommand));

        // same values -> returns true
        RemoveTagCommand removeFirstTagCommandCopy = prepareCommand(model.getFilteredTagList()
                .get(INDEX_FIRST_TAG.getZeroBased()));
        assertTrue(removeFirstTagCommand.equals(removeFirstTagCommandCopy));

        // different types -> returns false
        assertFalse(removeFirstTagCommand.equals(1));

        // null -> returns false
        assertFalse(removeFirstTagCommand.equals(null));

        // different person -> returns fal

        // se
        assertFalse(removeFirstTagCommand.equals(removeSecondTagCommand));
    }

    /**
     * Returns a {@code RemoveTagCommand} with the parameter {@code tag}.
     */
    private RemoveTagCommand prepareCommand(Tag tag) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(tag);
        removeTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeTagCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no tags.
     */
    private void showNoTag(Model model) {
        model.updateFilteredTagList(p -> false);

        assertTrue(model.getFilteredTagList().isEmpty());
    }

}
