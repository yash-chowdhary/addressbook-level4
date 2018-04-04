package systemtests;

import static seedu.club.logic.parser.CliSyntax.PREFIX_NEWPASSWORD;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.club.logic.parser.CliSyntax.PREFIX_USERNAME;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.commands.ChangePasswordCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.UndoCommand;
import seedu.club.model.Model;
import seedu.club.model.member.Member;


public class ChangePasswordCommandSystemTest extends ClubBookSystemTest {

    @Test
    public void changepassword() {
        Model model = getModel();
        ObservableList<Member> observableList = model.getClubBook().getMemberList();
        Member member = observableList.get(0);
        String logInCommand = LogInCommand.COMMAND_WORD + " u/" + member.getCredentials().getUsername().value
                + " pw/password";
        executeCommand(logInCommand);
        model = getModel();
        Model modelBeforeChanging = getModel();

        /*Case: Change the currently logged in member sucessfully
         */
        String changePasswordCommand = ChangePasswordCommand.COMMAND_WORD + " "
                + PREFIX_USERNAME + member.getCredentials().getUsername().value + " "
                + PREFIX_PASSWORD + member.getCredentials().getPassword().value + " "
                + PREFIX_NEWPASSWORD + "newpassword";
        String expectedMessage = ChangePasswordCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(changePasswordCommand, model, expectedMessage);

        /*Case: undo changepassword.
         *Should not be able to undo to change password
         */
        String command = UndoCommand.COMMAND_WORD;
        expectedMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedMessage);

        /*Case: Changing other member's password
         *Returns an error message
         */
        Member otherMember = observableList.get(1);
        changePasswordCommand = ChangePasswordCommand.COMMAND_WORD + " "
                + PREFIX_USERNAME + otherMember.getCredentials().getUsername().value + " "
                + PREFIX_PASSWORD + otherMember.getCredentials().getPassword().value + " "
                + PREFIX_NEWPASSWORD + "newpassword";
        expectedMessage = ChangePasswordCommand.MESSAGE_AUTHENTICATION_FAILED;
        assertCommandFailure(changePasswordCommand, expectedMessage);
    }

    /**
     * Executes the {@code ChangePasswordCommand} that changes the password to the model and asserts that:<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code ChangePasswordCommand} with details of
     * {@code newpassword}.<br>
     * 4. {@code Model}, {@code Storage} and {@code MemberListPanel} equal to the corresponding components in
     * the current model added with {@code newpassword}.<br>
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
     * Executes the {@code ChangePasswordCommand} that changes the password to the model and asserts that:<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. {@code Model}, {@code Storage} and {@code MemberListPanel} equal to the corresponding components in
     * the current model added with {@code newpassword}.<br>
     * 4. Browser url and selected card remain unchanged.<br>
     * 5. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
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
