package systemtests;

import static seedu.club.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.club.logic.parser.CliSyntax.PREFIX_USERNAME;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.commands.ClearCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.member.Member;

public class ClearCommandSystemTest extends ClubBookSystemTest {
    private ObservableList<Member> observableList;
    private Member member;

    @Test
    public void clear() {
        final Model defaultModel = getModel();
        observableList = defaultModel.getClubBook().getMemberList();
        member = observableList.get(0);
        String logInCommand = LogInCommand.COMMAND_WORD + " "
                + PREFIX_USERNAME + member.getCredentials().getUsername().value + " "
                + PREFIX_PASSWORD + member.getCredentials().getPassword().value;
        executeCommand(logInCommand);
        /* Case: clear non-empty club book, command with leading spaces and trailing alphanumeric characters and
         * spaces -> cleared
         */
        assertCommandSuccess("   " + ClearCommand.COMMAND_WORD + " ab12   ");
        assertSelectedCardUnchanged();

        /* Case: undo clearing club book -> original club book restored */
        /*String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command,  expectedResultMessage, defaultModel);
        assertSelectedCardUnchanged();*/

        /* Case: redo clearing club book -> cleared */
        /*command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, new ModelManager());
        assertSelectedCardUnchanged();*/

        /* Case: selects first card in member list and clears club book -> cleared and no card selected */
        /*executeCommand(UndoCommand.COMMAND_WORD); // restores the original club book
        selectMember(Index.fromOneBased(1));
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedCardDeselected();*/

        /* Case: filters the member list before clearing -> entire club book cleared */
        /*executeCommand(UndoCommand.COMMAND_WORD); // restores the original club book
        showMembersWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedCardUnchanged();*/

        /* Case: clear empty club book -> cleared *//*
        assertCommandFailure("clear", Messages.MESSAGE_REQUIRE_SIGN_UP);
        assertSelectedCardUnchanged();*/

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ClEaR", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
     * These verifications are done by
     * {@code ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        assertCommandSuccess(command, ClearCommand.MESSAGE_SUCCESS, new ModelManager());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ClearCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
