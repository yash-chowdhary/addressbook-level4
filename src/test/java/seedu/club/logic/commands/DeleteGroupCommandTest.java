package seedu.club.logic.commands;
//@@author yash-chowdhary
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.commons.core.Messages.MESSAGE_MANDATORY_GROUP;
import static seedu.club.commons.core.Messages.MESSAGE_NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.MANDATORY_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_TEST;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.club.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.club.testutil.TypicalIndexes.INDEX_FOURTH_MEMBER;
import static seedu.club.testutil.TypicalMembers.DANIEL;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.group.Group;
import seedu.club.model.member.Member;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteGroupCommand}.
 */
public class DeleteGroupCommandTest {
    private Model model;
    private Model expectedModel;
    private ObservableList<Member> observableList;
    private Member member;

    //@@author
    @Before
    public void setUp() throws CommandException {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalClubBook(), new UserPrefs());
        observableList = model.getClubBook().getMemberList();
        member = observableList.get(0);
        LogInCommand command = new LogInCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
        command.setData(expectedModel, new CommandHistory(), new UndoRedoStack());
        command.execute();
    }

    //@@author yash-chowdhary
    @Test
    public void execute_validGroup_success() throws Exception {
        Group groupToDelete = model.getFilteredMemberList().get(INDEX_FOURTH_MEMBER.getZeroBased()).getGroup();
        DeleteGroupCommand deleteGroupCommand = prepareCommand(DANIEL.getGroup());

        String expectedMessage = String.format(DeleteGroupCommand.MESSAGE_SUCCESS, groupToDelete);
        expectedModel.deleteGroup(groupToDelete);


        assertCommandSuccess(deleteGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentGroup_throwsCommandException() {
        Group nonExistentGroup = new Group(NON_EXISTENT_GROUP);
        DeleteGroupCommand deleteGroupCommand = prepareCommand(nonExistentGroup);

        String expectedMessage = String.format(MESSAGE_NON_EXISTENT_GROUP, nonExistentGroup);
        assertCommandFailure(deleteGroupCommand, model, expectedMessage);
    }

    @Test
    public void execute_mandatoryGroup_throwsCommandException() {
        Group mandatoryGroup = new Group(MANDATORY_GROUP);
        DeleteGroupCommand deleteGroupCommand = prepareCommand(mandatoryGroup);
        String expectedMessage = String.format(MESSAGE_MANDATORY_GROUP, mandatoryGroup.toString());
        assertCommandFailure(deleteGroupCommand, model, expectedMessage);
    }

    @Test
    public void executeUndoRedo_validGroup_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Group groupToDelete = model.getFilteredMemberList().get(INDEX_FOURTH_MEMBER.getZeroBased()).getGroup();
        DeleteGroupCommand deleteGroupCommand = prepareCommand(DANIEL.getGroup());
        // remove -> group removed
        deleteGroupCommand.execute();
        undoRedoStack.push(deleteGroupCommand);

        // undo -> reverts Club book back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same group deleted again
        expectedModel.deleteGroup(groupToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_nonExistentGroup_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Group nonExistentGroup = new Group(NON_EXISTENT_GROUP);
        DeleteGroupCommand deleteGroupCommand = prepareCommand(nonExistentGroup);

        // execution failed -> deleteGroupCommand not pushed onto undoRedoStack
        assertCommandFailure(deleteGroupCommand, model,
                String.format(MESSAGE_NON_EXISTENT_GROUP, nonExistentGroup));

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
        DeleteGroupCommand deleteGroupCommand = prepareCommand(mandatoryGroup);

        // execution failed -> deleteGroupCommand not pushed onto undoRedoStack
        assertCommandFailure(deleteGroupCommand, model,
                String.format(MESSAGE_MANDATORY_GROUP, mandatoryGroup.toString()));

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_validGroup_sameGroupDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteGroupCommand deleteGroupCommand = prepareCommand(DANIEL.getGroup());
        Group groupToDelete = model.getFilteredMemberList().get(INDEX_FOURTH_MEMBER.getZeroBased()).getGroup();
        // remove -> removes group
        deleteGroupCommand.execute();
        undoRedoStack.push(deleteGroupCommand);

        // undo -> reverts Club book back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.deleteGroup(groupToDelete);
        assertEquals(groupToDelete, model.getFilteredMemberList().get(INDEX_FOURTH_MEMBER.getZeroBased()).getGroup());
        // redo -> removes the same group
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteGroupCommand firstCommand = prepareCommand(new Group(VALID_GROUP_AMY));
        DeleteGroupCommand secondCommand = prepareCommand(new Group(VALID_GROUP_TEST));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(secondCommand.equals(secondCommand));

        // same values -> return true
        DeleteGroupCommand firstCommandCopy = prepareCommand(new Group(VALID_GROUP_AMY));
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
    private DeleteGroupCommand prepareCommand(Group group) {
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(group);
        deleteGroupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteGroupCommand;
    }
}
