package systemtests;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.club.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.club.logic.commands.DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS;
import static seedu.club.testutil.TestUtil.getTask;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalTasks.getTypicalClubBookWithTasks;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.logic.commands.DeleteTaskCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.member.Member;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.TaskCannotBeDeletedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;

public class DeleteTaskCommandSystemTest extends ClubBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_TASK_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE);

    @Test
    public void deleteTask() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first member in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command;
        ObservableList<Member> memberObservableList = expectedModel.getClubBook().getMemberList();
        String logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(0).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);
        expectedModel = new ModelManager(getTypicalClubBookWithTasks(), new UserPrefs());
        expectedModel.logsInMember(ALICE.getCredentials().getUsername().value,
                ALICE.getCredentials().getPassword().value);

        /* Case: invalid index (0) -> rejected */
        command = DeleteTaskCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_TASK_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteTaskCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_TASK_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getClubBook().getTaskList().size() + 1);
        command = DeleteTaskCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteTaskCommand.COMMAND_WORD + " abc",
                MESSAGE_INVALID_DELETE_TASK_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteTaskCommand.COMMAND_WORD + " 1 abc",
                MESSAGE_INVALID_DELETE_TASK_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETETAsk 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Deletes the member at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Task deletedTask = removeTask(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TASK_SUCCESS, deletedTask);

        assertCommandSuccess(
                DeleteTaskCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel,
                expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see ClubBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Removes the {@code task} at the specified {@code index} in {@code model}'s club book.
     * @return the removed task
     */
    private Task removeTask(Model model, Index index) {
        Task targetTask = getTask(model, index);
        try {
            model.deleteTask(targetTask);
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("targetTask is retrieved from model");
        } catch (TaskCannotBeDeletedException tcbde) {
            throw new AssertionError("targetTask cannot be deleted");
        }
        return targetTask;
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
