package seedu.club.logic.commands;

import static org.junit.Assert.assertEquals;
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
import seedu.club.model.member.Member;

public class HistoryCommandTest {
    private HistoryCommand historyCommand;
    private CommandHistory history;
    private Model model = new ModelManager(getTypicalClubBook(), new UserPrefs());

    @Before
    public void setUp() throws CommandException {
        ObservableList<Member> observableList = model.getClubBook().getMemberList();
        Member member = observableList.get(0);
        LogInCommand logInCommand = new LogInCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword());
        logInCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        logInCommand.execute();
        history = new CommandHistory();
        historyCommand = new HistoryCommand();
        historyCommand.setData(model, history, new UndoRedoStack());
    }

    @Test
    public void execute() throws CommandException {
        System.out.println(model.getLoggedInMember());
        assertCommandResult(historyCommand, HistoryCommand.MESSAGE_NO_HISTORY);

        String command1 = "clear";
        history.add(command1);
        assertCommandResult(historyCommand, String.format(HistoryCommand.MESSAGE_SUCCESS, command1));

        String command2 = "randomCommand";
        String command3 = "select 1";
        history.add(command2);
        history.add(command3);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", command3, command2, command1));

        assertCommandResult(historyCommand, expectedMessage);
    }

    /**
     * Asserts that the result message from the execution of {@code historyCommand} equals to {@code expectedMessage}
     */
    private void assertCommandResult(HistoryCommand historyCommand, String expectedMessage) throws CommandException {
        assertEquals(expectedMessage, historyCommand.execute().feedbackToUser);
    }
}
