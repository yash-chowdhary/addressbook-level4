package seedu.club.logic.commands;
//@@author yash-chowdhary
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalTasks.getTypicalClubBookWithTasks;

import org.junit.Before;
import org.junit.Test;

import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.task.TaskIsRelatedToMemberPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewAllTasksCommand.
 */
public class ViewMyTasksCommandTest {

    private Model model;
    private Model expectedModel;
    private ViewMyTasksCommand viewMyTasksCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalClubBookWithTasks(), new UserPrefs());
        expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        viewMyTasksCommand = new ViewMyTasksCommand();
        viewMyTasksCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsMemberTasks() {
        model.logsInMember(ALICE.getCredentials().getUsername().value, ALICE.getCredentials().getPassword().value);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);
        TaskIsRelatedToMemberPredicate predicateAlice = new TaskIsRelatedToMemberPredicate(ALICE);
        expectedModel.updateFilteredTaskList(predicateAlice);
        assertCommandSuccess(viewMyTasksCommand, model, ViewMyTasksCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listAlreadyFiltered_throwsException() {
        model.logsInMember(ALICE.getCredentials().getUsername().value, ALICE.getCredentials().getPassword().value);
        TaskIsRelatedToMemberPredicate predicateAlice = new TaskIsRelatedToMemberPredicate(ALICE);
        model.updateFilteredTaskList(predicateAlice);
        String expectedMessage = ViewMyTasksCommand.MESSAGE_ALREADY_LISTED;
        assertCommandFailure(viewMyTasksCommand, model, expectedMessage);
    }
}
