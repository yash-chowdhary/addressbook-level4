package systemtests;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.club.logic.commands.CommandTestUtil.EMPTY_STRING;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DATE_DESC_2;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_CONFETTI;
import static seedu.club.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_FOOD;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_1;
import static seedu.club.logic.commands.CommandTestUtil.TASK_TIME_DESC_2;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_ASSIGNEE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_ASSIGNOR;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DATE_1;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DATE_2;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_CONFETTI;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_STATUS_TO_BEGIN;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_TIME_1;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TASK_TIME_2;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.club.testutil.TypicalTasks.BOOK_AUDITORIUM;
import static seedu.club.testutil.TypicalTasks.BUY_FOOD;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.commands.AddTaskCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.RedoCommand;
import seedu.club.logic.commands.UndoCommand;
import seedu.club.model.Model;
import seedu.club.model.member.Member;
import seedu.club.model.task.Date;
import seedu.club.model.task.Description;
import seedu.club.model.task.Task;
import seedu.club.model.task.Time;
import seedu.club.testutil.TaskBuilder;

public class AddTaskCommandSystemTest extends ClubBookSystemTest {

    @Test
    public void addTask() throws Exception {
        Model model = getModel();
        Model modelBeforeAdding = getModel();
        ObservableList<Member> memberObservableList = model.getClubBook().getMemberList();
        String logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(0).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);
        model = getModel();
        modelBeforeAdding = getModel();

        Task toAdd = BUY_FOOD;

        /* Case: add a task to a non-empty address book,
         * command with leading spaces and trailing spaces -> added
         */
        String command = "  " + AddTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_FOOD
                + "  " + TASK_DATE_DESC_1 + "  " + TASK_TIME_DESC_1 +  "  ";

        String expectedMessage = AddTaskCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedMessage);

        /* Case:undo adding BUY_FOOD to the list -> BUY_FOOD deleted */
        command = UndoCommand.COMMAND_WORD;
        expectedMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeAdding, expectedMessage);

        /* Case: redo removing BUY_FOOD from the list -> BUY_FOOD re-added */

        command = RedoCommand.COMMAND_WORD;
        expectedMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: add task with all fields same as another task in address book except task description -> added */

        toAdd = new TaskBuilder()
                .withDescription(VALID_TASK_DESCRIPTION_CONFETTI)
                .withDate(VALID_TASK_DATE_1)
                .withTime(VALID_TASK_TIME_1)
                .withAssignor(VALID_TASK_ASSIGNOR)
                .withAssignee(VALID_TASK_ASSIGNEE)
                .withStatus(VALID_TASK_STATUS_TO_BEGIN)
                .build();
        command = AddTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_CONFETTI
                + " " + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_1 +  " ";
        expectedMessage = AddTaskCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: add task with all fields same as another task in address book except task due date -> added */
        toAdd = new TaskBuilder()
                .withDescription(VALID_TASK_DESCRIPTION_CONFETTI)
                .withDate(VALID_TASK_DATE_2)
                .withTime(VALID_TASK_TIME_1)
                .withAssignor(VALID_TASK_ASSIGNOR)
                .withAssignee(VALID_TASK_ASSIGNEE)
                .withStatus(VALID_TASK_STATUS_TO_BEGIN)
                .build();
        command = AddTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_CONFETTI + " "
                + TASK_DATE_DESC_2 + " " + TASK_TIME_DESC_1 + " ";
        expectedMessage = AddTaskCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: add task with all fields same as another task in address book except task time -> added */
        toAdd = new TaskBuilder()
                .withDescription(VALID_TASK_DESCRIPTION_CONFETTI)
                .withDate(VALID_TASK_DATE_1)
                .withTime(VALID_TASK_TIME_2)
                .withAssignor(VALID_TASK_ASSIGNOR)
                .withAssignee(VALID_TASK_ASSIGNEE)
                .withStatus(VALID_TASK_STATUS_TO_BEGIN)
                .build();
        command = AddTaskCommand.COMMAND_WORD + " " + TASK_DESCRIPTION_DESC_CONFETTI + " "
                + TASK_DATE_DESC_1 + " " + TASK_TIME_DESC_2 + " ";
        expectedMessage = AddTaskCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedMessage);

        /* CaseL add task with fields in random order -> added */
        toAdd = BOOK_AUDITORIUM;
        command = AddTaskCommand.COMMAND_WORD + " " + "desc/Book Auditorium "
                + "d/02/04/2018 " + "ti/13:00 ";
        assertCommandSuccess(command, model, expectedMessage);

        /* --------------------- Perform addtask operations on the shown filtered list -------------------------- */

        /* --------------------------------- Perform invalid addtask operations --------------------------------- */

        /* Case: missing description -> rejected */
        command = AddTaskCommand.COMMAND_WORD + " "
                + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = AddTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_CONFETTI + " " + TASK_TIME_DESC_1;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        /* Case: missing time -> rejected */
        command = AddTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_CONFETTI + " " + TASK_DATE_DESC_1;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        /* Case: invalid command word -> rejected */
        command = "addatask" + " " + TASK_DESCRIPTION_DESC_CONFETTI + " "
                + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1;
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid description -> rejected */
        command = AddTaskCommand.COMMAND_WORD + " "
                + PREFIX_DESCRIPTION + EMPTY_STRING + " " + TASK_TIME_DESC_1 + " " + TASK_DATE_DESC_1;
        assertCommandFailure(command, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        command = AddTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_CONFETTI + " " + TASK_TIME_DESC_1 + " " + INVALID_DATE_DESC;
        assertCommandFailure(command, Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid time -> rejected */
        command = AddTaskCommand.COMMAND_WORD + " "
                + TASK_DESCRIPTION_DESC_CONFETTI + " " + INVALID_TIME_DESC + " " + TASK_DATE_DESC_1;
        assertCommandFailure(command, Time.MESSAGE_TIME_CONSTRAINTS);
    }


    /**
     * Executes the {@code AddTaskCommand} that adds {@code toAdd} to the model and asserts that:<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddTaskCommand} with details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
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
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
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
