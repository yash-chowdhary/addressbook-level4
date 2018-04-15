package systemtests;
//@@author yash-chowdhary
import static seedu.club.commons.core.Messages.MESSAGE_NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_CLIENT;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.VALID_CLIENT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.club.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_SUBJECT;

import org.apache.commons.lang3.text.WordUtils;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.commands.EmailCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.model.Model;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.member.Member;

public class EmailCommandSystemTest extends ClubBookSystemTest {

    @Test
    public void sendEmail() {
        Model model = getModel();
        ObservableList<Member> memberObservableList = model.getClubBook().getMemberList();
        String logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(0).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);
        model = getModel();


        /*Case: invalid client entered -> rejected*/
        String command = "  " + EmailCommand.COMMAND_WORD + "  " + PREFIX_GROUP + "pr " + PREFIX_CLIENT
                + "YAHOO " + " " + PREFIX_SUBJECT + Subject.TEST_SUBJECT_STRING
                + " " + PREFIX_BODY + Body.TEST_BODY_STRING;
        String expectedResultMessage = Client.MESSAGE_CLIENT_CONSTRAINTS;
        assertCommandFailure(command, expectedResultMessage);

        /*Case: invalid group -> rejected */
        command = "  " + EmailCommand.COMMAND_WORD + "  " + PREFIX_GROUP + INVALID_GROUP + " "
                + PREFIX_CLIENT + INVALID_CLIENT + " " + PREFIX_SUBJECT + Subject.TEST_SUBJECT_STRING
                + " " + PREFIX_BODY + Body.TEST_BODY_STRING;
        expectedResultMessage = Group.MESSAGE_GROUP_CONSTRAINTS;
        assertCommandFailure(command, expectedResultMessage);

        /*Case: invalid group -> rejected */
        command = "  " + EmailCommand.COMMAND_WORD + "  " + PREFIX_GROUP + NON_EXISTENT_GROUP + " "
                + PREFIX_CLIENT + VALID_CLIENT + " " + PREFIX_SUBJECT + Subject.TEST_SUBJECT_STRING
                + " " + PREFIX_BODY + Body.TEST_BODY_STRING;
        expectedResultMessage = String.format(MESSAGE_NON_EXISTENT_GROUP, WordUtils.capitalize(NON_EXISTENT_GROUP));
        assertCommandFailure(command, expectedResultMessage);

        /* Case: valid command entered -> email client opened */
        command = "  " + EmailCommand.COMMAND_WORD + "  " + PREFIX_GROUP + "pr " + PREFIX_CLIENT
                + VALID_CLIENT + " " + PREFIX_SUBJECT + Subject.TEST_SUBJECT_STRING + " "
                + PREFIX_BODY + Body.TEST_BODY_STRING;
        expectedResultMessage = EmailCommand.EMAIL_CLIENT_OPENED;
        assertCommandSuccess(command, model, expectedResultMessage);
    }


    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box has the default style class.<br>
     * 2. Browser url and selected card remain unchanged.<br>
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        expectedModel.updateFilteredMemberList(Model.PREDICATE_SHOW_ALL_MEMBERS);
        assertSelectedCardUnchanged();
    }

    //@@author
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

    //@@author yash-chowdhary

}
