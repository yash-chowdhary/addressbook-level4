package systemtests;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_PERMISSIONS;
import static seedu.club.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.club.logic.commands.CommandTestUtil.EMPTY_STRING;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.club.logic.commands.CommandTestUtil.NAME_DESC_BENSON;
import static seedu.club.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.club.logic.commands.CommandTestUtil.NAME_DESC_CARL;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_2;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_CONFETTI;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_FOOD;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_2;
import static seedu.club.logic.commands.CommandTestUtil.VALID_NAME_BENSON;
import static seedu.club.logic.commands.CommandTestUtil.VALID_NAME_CARL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.commands.AssignTaskCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.LogOutCommand;
import seedu.club.logic.commands.RedoCommand;
import seedu.club.logic.commands.UndoCommand;
import seedu.club.model.Model;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.task.Date;
import seedu.club.model.task.Description;
import seedu.club.model.task.Time;

public class AssignTaskCommandSystemTest extends ClubBookSystemTest {

    @Test
    public void assignTask() throws Exception {
        Model model = getModel();
        Model modelBeforeAdding = getModel();
        ObservableList<Member> memberObservableList = model.getClubBook().getMemberList();
        String logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(0).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);
        model = getModel();
        modelBeforeAdding = getModel();

        /* Case: add a task to a non-empty address book,
         * command with leading spaces and trailing spaces -> added
         */
        String command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_1 + " " + NAME_DESC_BENSON;

        String expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, VALID_NAME_BENSON);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case:undo assigning BUY_FOOD to Benson -> BUY_FOOD deleted */
        command = UndoCommand.COMMAND_WORD;
        expectedMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeAdding, expectedMessage);

        /* Case: redo removing BUY_FOOD from the list -> BUY_FOOD re-added and re-assigned to Benson*/
        command = RedoCommand.COMMAND_WORD;
        expectedMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: assign task with all fields same as another task in address book except task description -> added */
        command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_CONFETTI + " "
                + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_1 + " " + NAME_DESC_BENSON;
        expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, VALID_NAME_BENSON);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: assign task with all fields same as another task in address book except task date -> added */
        command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_DATE_DESC_2 + " " + TASK_TIME_DESC_1 + " " + NAME_DESC_BENSON;
        expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, VALID_NAME_BENSON);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: assign task with all fields same as another task in address book except task time -> added */
        command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_2 + " " + NAME_DESC_BENSON;
        expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, VALID_NAME_BENSON);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: assign task with all fields same as another task in address book except task assignee -> added */
        command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_DATE_DESC_2 + " " + TASK_TIME_DESC_1 + " " + NAME_DESC_CARL;
        expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS, VALID_NAME_CARL);
        assertCommandSuccess(command, model, expectedMessage);

        /* --------------------------------- Perform invalid assigntask operations ------------------------------ */
        /* Case: member not found -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_FOOD + " " + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " "
                + NAME_DESC_BOB;
        assertCommandFailure(command, AssignTaskCommand.MESSAGE_MEMBER_NOT_FOUND);

        command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_1 + " " + NAME_DESC_BENSON;
        assertCommandFailure(command, AssignTaskCommand.MESSAGE_DUPLICATE_TASK);

        /* --------------------- Perform assigntask operations on the shown filtered list ----------------------- */

        /* --------------------------------- Perform invalid assigntask operations ------------------------------ */
        String logoutCommand = " " + LogOutCommand.COMMAND_WORD;
        executeCommand(logoutCommand);

        logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(1).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);

        /* Case: add a task to a non-empty address book,
         * command with leading spaces and trailing spaces -> REJECTED because Benson is not an EXCO member.
         */
        command = " " + AssignTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_1 + " " + NAME_DESC_CARL;

        assertCommandFailure(command, MESSAGE_INVALID_PERMISSIONS);

        logoutCommand = " " + LogOutCommand.COMMAND_WORD;
        executeCommand(logoutCommand);

        logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(0).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);


        /* Case: missing description -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " " + NAME_DESC_BENSON;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_TIME_DESC_1 + " " + TASK_DESCRIPTION_DESC_FOOD + " " + NAME_DESC_BENSON;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE));

        /* Case: missing time -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_FOOD + " " + TASK_DATE_DESC_1 + " " + NAME_DESC_BENSON;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE));

        /* Case: missing assignee -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " " + TASK_DESCRIPTION_DESC_FOOD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE));

        /* Case: missing description -> rejected */
        command = "assignatask" + " " + TASK_DESCRIPTION_DESC_FOOD + " "
                + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " " + NAME_DESC_BENSON;
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid description -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + PREFIX_DESCRIPTION + EMPTY_STRING + " " + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " "
                + NAME_DESC_BENSON;
        assertCommandFailure(command, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_FOOD + " " + TASK_TIME_DESC_1 + " " + INVALID_DATE_DESC + " "
                + NAME_DESC_BENSON;
        assertCommandFailure(command, Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid time -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_FOOD + " " + INVALID_TIME_DESC + " " + TASK_DATE_DESC_1 + " "
                + NAME_DESC_BENSON;
        assertCommandFailure(command, Time.MESSAGE_TIME_CONSTRAINTS

        );

        /* Case: invalid name -> rejected */
        command = AssignTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_FOOD + " " + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1 + " "
                + INVALID_NAME_DESC;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);



    }

    /**
     * Executes the {@code AssignTaskCommand} that adds {@code toAdd} to the model and asserts that:<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddTaskCommand} with details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code TaskListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     *Executes {@code command} and asserts that the:<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code TaskListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
