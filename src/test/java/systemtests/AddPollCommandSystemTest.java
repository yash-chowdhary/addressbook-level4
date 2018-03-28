package systemtests;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_FINE;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_FOUR;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_NOT_GOOD;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_ONE;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_THREE;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_TWO;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_VAMPIRE;
import static seedu.club.logic.commands.CommandTestUtil.ANSWER_DESC_ZOMBIE;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.club.logic.commands.CommandTestUtil.QUESTION_DESC_HOW;
import static seedu.club.logic.commands.CommandTestUtil.QUESTION_DESC_LIFE;
import static seedu.club.logic.commands.CommandTestUtil.QUESTION_DESC_LOVE;
import static seedu.club.logic.commands.CommandTestUtil.QUESTION_DESC_WHAT;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_ONE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_TWO;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_VAMPIRE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_ANSWER_ZOMBIE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_QUESTION_LOVE;
import static seedu.club.logic.commands.CommandTestUtil.VALID_QUESTION_WHAT;
import static seedu.club.testutil.TypicalPolls.POLL_HOW;
import static seedu.club.testutil.TypicalPolls.POLL_WHAT;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.commons.core.Messages;
import seedu.club.logic.commands.AddPollCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.RedoCommand;
import seedu.club.logic.commands.UndoCommand;
import seedu.club.model.Model;
import seedu.club.model.member.Member;
import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.Question;
import seedu.club.model.poll.exceptions.DuplicatePollException;
import seedu.club.testutil.PollBuilder;
import seedu.club.testutil.PollUtil;

public class AddPollCommandSystemTest extends ClubBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        Model modelBeforeAdding = getModel();
        ObservableList<Member> memberObservableList = model.getClubBook().getMemberList();
        String logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(0).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);
        model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a poll without tags to a non-empty club book, command with leading spaces and trailing spaces
         * -> added
         */
        Poll toAdd = POLL_WHAT;
        String command = "   " + AddPollCommand.COMMAND_WORD + "  " + QUESTION_DESC_WHAT + "  "
                + ANSWER_DESC_VAMPIRE + " "
                + ANSWER_DESC_ZOMBIE;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding "What poll" to the list -> "What poll" deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding "What poll" to the list -> "What poll" added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPoll(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a poll answers same as another poll in the club book but different question -> added */
        toAdd = new PollBuilder().withQuestion(VALID_QUESTION_LOVE)
                .withAnswers(VALID_ANSWER_VAMPIRE, VALID_ANSWER_ZOMBIE).build();
        command = AddPollCommand.COMMAND_WORD + QUESTION_DESC_LOVE + ANSWER_DESC_VAMPIRE + ANSWER_DESC_ZOMBIE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a poll with question same as another poll in the club book but with all different answers
         -> added */
        toAdd = new PollBuilder().withQuestion(VALID_QUESTION_WHAT)
                .withAnswers(VALID_ANSWER_ONE, VALID_ANSWER_TWO).build();
        command = AddPollCommand.COMMAND_WORD + QUESTION_DESC_WHAT + ANSWER_DESC_ONE + ANSWER_DESC_TWO;

        /* Case: add a poll with question same as another poll in the club book but with exactly one different answer
         -> added */
        toAdd = new PollBuilder().withQuestion(VALID_QUESTION_WHAT)
                .withAnswers(VALID_ANSWER_ONE, VALID_ANSWER_VAMPIRE).build();
        command = AddPollCommand.COMMAND_WORD + QUESTION_DESC_WHAT + ANSWER_DESC_ONE + ANSWER_DESC_VAMPIRE;

        assertCommandSuccess(command, toAdd);

        /* Case: add a poll with fields in random order -> added */
        toAdd = POLL_HOW;
        command = AddPollCommand.COMMAND_WORD + ANSWER_DESC_FINE + QUESTION_DESC_HOW + ANSWER_DESC_NOT_GOOD;
        assertCommandSuccess(command, toAdd);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate poll -> rejected */
        command = PollUtil.getAddPollCommand(POLL_WHAT);
        assertCommandFailure(command, AddPollCommand.MESSAGE_DUPLICATE_POLL);

        /* Case: missing question -> rejected */
        command = AddPollCommand.COMMAND_WORD + ANSWER_DESC_FOUR + ANSWER_DESC_THREE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPollCommand.MESSAGE_USAGE));

        /* Case: missing answer -> rejected */
        command = AddPollCommand.COMMAND_WORD + QUESTION_DESC_LIFE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPollCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "addpolls " + PollUtil.getPollDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid question -> rejected */
        command = AddPollCommand.COMMAND_WORD + INVALID_QUESTION_DESC + ANSWER_DESC_FOUR + ANSWER_DESC_VAMPIRE;
        assertCommandFailure(command, Question.MESSAGE_QUESTION_CONSTRAINTS);

        /* Case: invalid answer -> rejected */
        command = AddPollCommand.COMMAND_WORD + QUESTION_DESC_LIFE + INVALID_ANSWER_DESC + ANSWER_DESC_ONE;
        assertCommandFailure(command, Answer.MESSAGE_ANSWER_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddPollCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddPollCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PollListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ClubBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Poll toAdd) {
        assertCommandSuccess(PollUtil.getAddPollCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(poll)}. Executes {@code command}
     * instead.
     * @see AddPollCommandSystemTest#assertCommandSuccess(Poll)
     */
    private void assertCommandSuccess(String command, Poll toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addPoll(toAdd);
        } catch (DuplicatePollException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddPollCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, poll)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PollListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddPollCommandSystemTest#assertCommandSuccess(String, Poll)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PollListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
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
