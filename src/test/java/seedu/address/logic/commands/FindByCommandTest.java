package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FieldContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code FindByCommand}.
 */
public class FindByCommandTest {

    private static final String[] fieldTypes = {"name", "email", "phone", "matric", "tag", "group"};

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals_nameFieldType() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), "name");
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), "name");

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

        // different person -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void equals_emailFieldType() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), "email");
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), "email");

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

        // different person -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void equals_phoneFieldType() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), "phone");
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), "phone");

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

        // different person -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void equals_matricNumberFieldType() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), "matric");
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), "matric");

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

        // different person -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void equals_groupFieldType() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), "group");
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), "group");

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

        // different person -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void equals_tagFieldType() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), "tag");
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), "tag");

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

        // different person -> returns false
        assertFalse(findByFirstCommand.equals(findBySecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        for (String fieldType: fieldTypes) {
            assertCommandSuccess(prepareCommand(" ", fieldType), expectedMessage, Collections.emptyList());
        }
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        for (String fieldType: fieldTypes) {
            assertCommandSuccess(prepareCommand("Kurz Elle Kunz", fieldType),
                    expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
        }
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
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindByCommand command, String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
