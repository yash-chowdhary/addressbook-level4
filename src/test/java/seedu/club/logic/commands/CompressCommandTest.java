package seedu.club.logic.commands;

import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import org.junit.Before;
import org.junit.Test;

import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for CompressCommand.
 */
public class CompressCommandTest {

    private Model model;
    private Model expectedModel;
    private CompressCommand compressCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());

        compressCommand = new CompressCommand();
    }

    @Test
    public void execute() {
        assertCommandSuccess(compressCommand, model, CompressCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
