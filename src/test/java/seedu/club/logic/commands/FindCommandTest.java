package seedu.club.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.commons.core.Messages.MESSAGE_MEMBERS_LISTED_OVERVIEW;
import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.club.testutil.TypicalMembers.ALICE;
import static seedu.club.testutil.TypicalMembers.BENSON;
import static seedu.club.testutil.TypicalMembers.CARL;
import static seedu.club.testutil.TypicalMembers.DANIEL;
import static seedu.club.testutil.TypicalMembers.ELLE;
import static seedu.club.testutil.TypicalMembers.FIONA;
import static seedu.club.testutil.TypicalMembers.GEORGE;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.parser.Prefix;
import seedu.club.model.ClubBook;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.member.FieldContainsKeywordsPredicate;
import seedu.club.model.member.Member;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {

    private static final Prefix[] prefixes = {PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE,
        PREFIX_MATRIC_NUMBER, PREFIX_TAG, PREFIX_GROUP};

    private Model model = new ModelManager(getTypicalClubBook(), new UserPrefs());

    @Test
    public void equals_namePrefix() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), PREFIX_NAME);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), PREFIX_NAME);

        FindCommand findByFirstCommand = new FindCommand(firstPredicate);
        FindCommand findBySecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findByFirstCommand.equals(findByFirstCommand));

        // same values -> returns true
        FindCommand findByFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findByFirstCommand.equals(findByFirstCommandCopy));

        // different types -> returns false
        assertFalse(findByFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findByFirstCommand == null);

        // different member -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void equals_emailPrefix() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), PREFIX_EMAIL);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), PREFIX_EMAIL);

        FindCommand findByFirstCommand = new FindCommand(firstPredicate);
        FindCommand findBySecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findByFirstCommand.equals(findByFirstCommand));

        // same values -> returns true
        FindCommand findByFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findByFirstCommand.equals(findByFirstCommandCopy));

        // different types -> returns false
        assertFalse(findByFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findByFirstCommand == null);

        // different member -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void equals_phonePrefix() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), PREFIX_PHONE);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), PREFIX_PHONE);

        FindCommand findByFirstCommand = new FindCommand(firstPredicate);
        FindCommand findBySecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findByFirstCommand.equals(findByFirstCommand));

        // same values -> returns true
        FindCommand findByFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findByFirstCommand.equals(findByFirstCommandCopy));

        // different types -> returns false
        assertFalse(findByFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findByFirstCommand == null);

        // different member -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void equals_matricNumberPrefix() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), PREFIX_MATRIC_NUMBER);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), PREFIX_MATRIC_NUMBER);

        FindCommand findByFirstCommand = new FindCommand(firstPredicate);
        FindCommand findBySecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findByFirstCommand.equals(findByFirstCommand));

        // same values -> returns true
        FindCommand findByFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findByFirstCommand.equals(findByFirstCommandCopy));

        // different types -> returns false
        assertFalse(findByFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findByFirstCommand == null);

        // different member -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void equals_groupPrefix() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), PREFIX_GROUP);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), PREFIX_GROUP);

        FindCommand findByFirstCommand = new FindCommand(firstPredicate);
        FindCommand findBySecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findByFirstCommand.equals(findByFirstCommand));

        // same values -> returns true
        FindCommand findByFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findByFirstCommand.equals(findByFirstCommandCopy));

        // different types -> returns false
        assertFalse(findByFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findByFirstCommand == null);

        // different member -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void equals_tagPrefix() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), PREFIX_TAG);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), PREFIX_TAG);

        FindCommand findByFirstCommand = new FindCommand(firstPredicate);
        FindCommand findBySecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findByFirstCommand.equals(findByFirstCommand));

        // same values -> returns true
        FindCommand findByFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findByFirstCommand.equals(findByFirstCommandCopy));

        // different types -> returns false
        assertFalse(findByFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findByFirstCommand == null);

        // different member -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void equals_differentPrefix_returnFalse() {
        Prefix[] prefixes = {PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_MATRIC_NUMBER, PREFIX_TAG, PREFIX_GROUP};
        FindCommand[] commands = new FindCommand[prefixes.length];

        for (int index = 0; index < prefixes.length; index++) {
            commands[index] =
                    new FindCommand(
                            new FieldContainsKeywordsPredicate(Collections.singletonList("first"), prefixes[index]));
        }

        // Check across all pairs
        for (int i = 0; i < commands.length; i++) {
            for (int j = 0; j < commands.length; j++) {
                if (i != j) {
                    assertFalse(commands[i].equals(commands[j]));
                }
            }
        }

    }

    @Test
    public void execute_zeroKeywords_noMemberFound() {
        String expectedMessage = String.format(MESSAGE_MEMBERS_LISTED_OVERVIEW, 0);
        for (Prefix prefix : prefixes) {
            assertCommandSuccess(prepareCommand(" ", prefix), expectedMessage, Collections.emptyList());
        }
    }

    @Test
    public void execute_multipleKeywords_multipleMembersFound() {
        String expectedMessage = String.format(MESSAGE_MEMBERS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz", PREFIX_NAME);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand("95352563 9482224 9482427", PREFIX_PHONE);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand("heinz@example.com werner@example.com lydia@example.com", PREFIX_EMAIL);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand("A6076201A A1932279G A9662042H", PREFIX_MATRIC_NUMBER);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand("marketing operations", PREFIX_GROUP);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        expectedMessage = String.format(MESSAGE_MEMBERS_LISTED_OVERVIEW, 7);
        command = prepareCommand("friend friends owesMoney", PREFIX_TAG);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL,
                DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * Parses {@code userInput} and {@code prefix} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput, Prefix prefix) {
        FindCommand command =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")),
                        prefix));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<member>} is equal to {@code expectedList}<br>
     *     - the {@code ClubBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<Member> expectedList) {
        ClubBook expectedClubBook = new ClubBook(model.getClubBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredMemberList());
        assertEquals(expectedClubBook, model.getClubBook());
    }
}
