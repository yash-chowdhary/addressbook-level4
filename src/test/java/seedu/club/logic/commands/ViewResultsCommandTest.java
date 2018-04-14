package seedu.club.logic.commands;
//@@author MuhdNurKamal
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.commons.events.ui.ViewResultsRequestEvent;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.member.Member;
import seedu.club.ui.testutil.EventsCollectorRule;

public class ViewResultsCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    private Model model;
    private ObservableList<Member> observableList;
    private Member member;

    @Before
    public void setUp() throws CommandException {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        observableList = model.getClubBook().getMemberList();
        member = observableList.get(0);
        LogInCommand command = new LogInCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
    }
    @Test
    public void execute_help_success() throws CommandException {
        ViewResultsCommand resultsCommand = new ViewResultsCommand();
        resultsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult result = resultsCommand.execute();
        assertEquals(ViewResultsCommand.MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ViewResultsRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 2);
    }
}
