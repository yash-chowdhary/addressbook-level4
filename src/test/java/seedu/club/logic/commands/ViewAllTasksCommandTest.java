package seedu.club.logic.commands;
//@@author yash-chowdhary
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalMembers.BENSON;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import org.junit.Before;
import org.junit.Test;

import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewAllTasksCommand.
 */
public class ViewAllTasksCommandTest {

    private Model model;
    private Model expectedModel;
    private ViewAllTasksCommand viewAllTasksCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        viewAllTasksCommand = new ViewAllTasksCommand();
        viewAllTasksCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        model.logsInMember(ALICE.getCredentials().getUsername().value, ALICE.getCredentials().getPassword().value);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        expectedModel.updateFilteredTaskList(expectedModel.PREDICATE_SHOW_ALL_TASKS);
        assertCommandSuccess(viewAllTasksCommand, model, ViewAllTasksCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_taskCannotBeDisplayed_noChange() {
        model.logsInMember(BENSON.getCredentials().getUsername().value,
                BENSON.getCredentials().getPassword().value);
        model.updateFilteredTaskList(Model.PREDICATE_NOT_SHOW_ALL_TASKS);
        String expectedMessage = ViewAllTasksCommand.MESSAGE_CANNOT_VIEW;
        assertCommandFailure(viewAllTasksCommand, model, expectedMessage);
    }

}
