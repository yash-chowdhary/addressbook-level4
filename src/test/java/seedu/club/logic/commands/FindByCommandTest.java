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
 * Contains integration tests (interaction with the Model) for {@code FindByCommand}.
 */
public class FindByCommandTest {

    private static final String[] prefixStrings = {PREFIX_NAME.toString(), PREFIX_EMAIL.toString(), PREFIX_PHONE.toString(), PREFIX_MATRIC_NUMBER.toString(), PREFIX_TAG.toString(), PREFIX_GROUP.toString()};

    private Model model = new ModelManager(getTypicalClubBook(), new UserPrefs());

    @Test
    public void equals_nameFieldType() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), PREFIX_NAME.toString());
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), PREFIX_NAME.toString());

        FindByCommand findByFirstCommand = new FindByCommand(firstPredicate);
        FindByCommand findBySecondCommand = new FindByCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findByFirstCommand.equals(findByFirstCommand));

        // same values -> returns true
        FindByCommand findByFirstCommandCopy = new FindByCommand(firstPredicate);
        assertTrue(findByFirstCommand.equals(findByFirstCommandCopy));

        // different types -> returns false
        assertFalse(findByFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findByFirstCommand.equals(null));

        // different member -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void equals_emailFieldType() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), PREFIX_EMAIL.toString());
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), PREFIX_EMAIL.toString());

        FindByCommand findByFirstCommand = new FindByCommand(firstPredicate);
        FindByCommand findBySecondCommand = new FindByCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findByFirstCommand.equals(findByFirstCommand));

        // same values -> returns true
        FindByCommand findByFirstCommandCopy = new FindByCommand(firstPredicate);
        assertTrue(findByFirstCommand.equals(findByFirstCommandCopy));

        // different types -> returns false
        assertFalse(findByFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findByFirstCommand.equals(null));

        // different member -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void equals_phoneFieldType() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), PREFIX_PHONE.toString());
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), PREFIX_PHONE.toString());

        FindByCommand findByFirstCommand = new FindByCommand(firstPredicate);
        FindByCommand findBySecondCommand = new FindByCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findByFirstCommand.equals(findByFirstCommand));

        // same values -> returns true
        FindByCommand findByFirstCommandCopy = new FindByCommand(firstPredicate);
        assertTrue(findByFirstCommand.equals(findByFirstCommandCopy));

        // different types -> returns false
        assertFalse(findByFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findByFirstCommand.equals(null));

        // different member -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void equals_matricNumberFieldType() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), PREFIX_MATRIC_NUMBER.toString());
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), PREFIX_MATRIC_NUMBER.toString());

        FindByCommand findByFirstCommand = new FindByCommand(firstPredicate);
        FindByCommand findBySecondCommand = new FindByCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findByFirstCommand.equals(findByFirstCommand));

        // same values -> returns true
        FindByCommand findByFirstCommandCopy = new FindByCommand(firstPredicate);
        assertTrue(findByFirstCommand.equals(findByFirstCommandCopy));

        // different types -> returns false
        assertFalse(findByFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findByFirstCommand.equals(null));

        // different member -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void equals_groupFieldType() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), PREFIX_GROUP.toString());
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), PREFIX_GROUP.toString());

        FindByCommand findByFirstCommand = new FindByCommand(firstPredicate);
        FindByCommand findBySecondCommand = new FindByCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findByFirstCommand.equals(findByFirstCommand));

        // same values -> returns true
        FindByCommand findByFirstCommandCopy = new FindByCommand(firstPredicate);
        assertTrue(findByFirstCommand.equals(findByFirstCommandCopy));

        // different types -> returns false
        assertFalse(findByFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findByFirstCommand.equals(null));

        // different member -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void equals_tagFieldType() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), PREFIX_TAG.toString());
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), PREFIX_TAG.toString());

        FindByCommand findByFirstCommand = new FindByCommand(firstPredicate);
        FindByCommand findBySecondCommand = new FindByCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findByFirstCommand.equals(findByFirstCommand));

        // same values -> returns true
        FindByCommand findByFirstCommandCopy = new FindByCommand(firstPredicate);
        assertTrue(findByFirstCommand.equals(findByFirstCommandCopy));

        // different types -> returns false
        assertFalse(findByFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findByFirstCommand.equals(null));

        // different member -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void equals_differentFieldType_returnFalse() {
        String[] fieldTypes = {PREFIX_NAME.toString(), PREFIX_EMAIL.toString(), PREFIX_PHONE.toString(), PREFIX_MATRIC_NUMBER.toString(), PREFIX_TAG.toString(), PREFIX_GROUP.toString()};
        FindByCommand[] commands = new FindByCommand[fieldTypes.length];

        for (int index = 0; index < fieldTypes.length; index++) {
            commands[index] =
                    new FindByCommand(
                            new FieldContainsKeywordsPredicate(Collections.singletonList("first"), fieldTypes[index]));
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
        for (String prefixString : prefixStrings) {
            assertCommandSuccess(prepareCommand(" ", prefixString), expectedMessage, Collections.emptyList());
        }
    }

    @Test
    public void execute_multipleKeywords_multipleMembersFound() {
        String expectedMessage = String.format(MESSAGE_MEMBERS_LISTED_OVERVIEW, 3);
        FindByCommand command = prepareCommand("Kurz Elle Kunz", PREFIX_NAME.toString());
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand("95352563 9482224 9482427", PREFIX_PHONE.toString());
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand("heinz@example.com werner@example.com lydia@example.com", PREFIX_EMAIL.toString());
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand("A6076201A A1932279G A9662042H", PREFIX_MATRIC_NUMBER.toString());
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand("marketing operations", PREFIX_GROUP.toString());
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        expectedMessage = String.format(MESSAGE_MEMBERS_LISTED_OVERVIEW, 7);
        command = prepareCommand("friend friends owesMoney", PREFIX_TAG.toString());
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL,
                DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * Parses {@code userInput} and {@code fieldType} into a {@code FindByCommand}.
     */
    private FindByCommand prepareCommand(String userInput, String fieldType) {
        FindByCommand command =
                new FindByCommand(new FieldContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")),
                        fieldType));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<member>} is equal to {@code expectedList}<br>
     *     - the {@code ClubBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindByCommand command, String expectedMessage, List<Member> expectedList) {
        ClubBook expectedClubBook = new ClubBook(model.getClubBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredMemberList());
        assertEquals(expectedClubBook, model.getClubBook());
    }
}
