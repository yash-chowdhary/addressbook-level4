package seedu.club.logic.commands;

import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import org.junit.Before;
import org.junit.Test;

import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DecompressCommand.
 */
public class DecompressCommandTest {

    private Model model;
    private Model expectedModel;
    private DecompressCommand decompressCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());

        decompressCommand = new DecompressCommand();
    }

    @Test
    public void execute() {
        assertCommandSuccess(decompressCommand, model, DecompressCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
