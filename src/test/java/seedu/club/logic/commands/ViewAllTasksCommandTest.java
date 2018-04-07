package seedu.club.logic.commands;
//@@author yash-chowdhary
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.testutil.TypicalMembers.BENSON;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.commons.core.Messages;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.member.Member;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewAllTasksCommand.
 */
public class ViewAllTasksCommandTest {

    private Model model;
    private Model expectedModel;
    private ViewAllTasksCommand viewAllTasksCommand;
    private ObservableList<Member> observableList;
    private Member member;

    @Before
    public void setUp() throws CommandException {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());
        observableList = model.getClubBook().getMemberList();
        member = observableList.get(0);
        LogInCommand command = new LogInCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
        command.setData(expectedModel, new CommandHistory(), new UndoRedoStack());
        command.execute();
        viewAllTasksCommand = new ViewAllTasksCommand();
        viewAllTasksCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }


    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(viewAllTasksCommand, model, ViewAllTasksCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_taskCannotBeDisplayed_noChange() {
        model.logsInMember(BENSON.getCredentials().getUsername().value,
                BENSON.getCredentials().getPassword().value);
        model.updateFilteredTaskList(Model.PREDICATE_NOT_SHOW_ALL_TASKS);
        String expectedMessage = Messages.MESSAGE_REQUIRE_EXCO_LOG_IN;
        assertCommandFailure(viewAllTasksCommand, model, expectedMessage);
    }

}
