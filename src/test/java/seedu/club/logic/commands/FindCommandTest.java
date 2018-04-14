package seedu.club.logic.commands;
//@@author MuhdNurKamal
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

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
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

    private static final Prefix[] FINDABLE_PREFIXES = {PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE,
        PREFIX_MATRIC_NUMBER, PREFIX_TAG, PREFIX_GROUP};

    private Model model;
    private ObservableList<Member> observableList;
    private Member member;

    @Before
    public void setUp() throws CommandException {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        observableList = model.getClubBook().getMemberList();
        member = observableList.get(0);
        LogInCommand command = new LogInCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
    }

    @Test
    public void equals_returnTrue() {
        for (Prefix prefix : FINDABLE_PREFIXES) {
            assertEqualsCorrectForPrefix(prefix);
        }
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
    public void execute_zeroKeywords_noMemberFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_MEMBERS_LISTED_OVERVIEW, 0);
        for (Prefix prefix : FINDABLE_PREFIXES) {
            assertCommandSuccess(prepareCommand(" ", prefix), expectedMessage, Collections.emptyList());
        }
    }

    @Test
    public void execute_multipleKeywords_multipleMembersFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_MEMBERS_LISTED_OVERVIEW, 3);
        String expectedMessage2 = String.format(MESSAGE_MEMBERS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("Kurz Elle Kunz", PREFIX_NAME);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand("95352563 9482224 9482427", PREFIX_PHONE);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand("heinz@example.com werner@example.com lydia@example.com", PREFIX_EMAIL);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand("A6076201A A1932279G A9662042H", PREFIX_MATRIC_NUMBER);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand("marketing operations", PREFIX_GROUP);
        assertCommandSuccess(command, expectedMessage2, Arrays.asList(FIONA));

        expectedMessage = String.format(MESSAGE_MEMBERS_LISTED_OVERVIEW, 7);
        command = prepareCommand("head heads owesMoney", PREFIX_TAG);
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
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<Member> expectedList)
            throws CommandException {
        ClubBook expectedClubBook = new ClubBook(model.getClubBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredMemberList());
        assertEquals(expectedClubBook, model.getClubBook());
    }

    /**
     * Asserts that equals method for FindCommand with prefix is correct
     *
     * @param prefix of field FindCommand finds for
     */
    private void assertEqualsCorrectForPrefix(Prefix prefix) {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), prefix);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), prefix);

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
}
