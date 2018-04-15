package seedu.club.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.club.logic.commands.CommandTestUtil.deleteFirstMember;
import static seedu.club.logic.commands.CommandTestUtil.showMemberAtIndex;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.member.Member;
import seedu.club.model.member.exceptions.DeleteCurrentUserException;
import seedu.club.model.member.exceptions.MemberNotFoundException;

public class UndoableCommandTest {
    private DummyCommand dummyCommand;
    private Model model;
    private Model expectedModel;
    private ObservableList<Member> observableList;
    private Member member;

    @Before
    public void setUp() throws CommandException {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalClubBook(), new UserPrefs());
        observableList = model.getClubBook().getMemberList();
        member = observableList.get(1);
        LogInCommand command = new LogInCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
        command.setData(expectedModel, new CommandHistory(), new UndoRedoStack());
        command.execute();
        dummyCommand = new DummyCommand(model);
    }
    @Test
    public void executeUndo() throws Exception {
        model.updateFilteredMemberList(model.PREDICATE_SHOW_ALL_MEMBERS);
        expectedModel.updateFilteredMemberList(expectedModel.PREDICATE_SHOW_ALL_MEMBERS);
        dummyCommand.execute();
        deleteFirstMember(expectedModel);
        assertEquals(expectedModel, model);

        showMemberAtIndex(model, INDEX_FIRST_MEMBER);

        // undo() should cause the model's filtered list to show all members
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalClubBook(), new UserPrefs());
        expectedModel.updateFilteredMemberList(expectedModel.PREDICATE_SHOW_ALL_MEMBERS);
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo() throws DeleteCurrentUserException {
        model.updateFilteredMemberList(model.PREDICATE_SHOW_ALL_MEMBERS);
        showMemberAtIndex(model, INDEX_FIRST_MEMBER);
        expectedModel.updateFilteredMemberList(expectedModel.PREDICATE_SHOW_ALL_MEMBERS);

        // redo() should cause the model's filtered list to show all members
        dummyCommand.redo();
        deleteFirstMember(expectedModel);
        assertEquals(expectedModel, model);
    }

    /**
     * Deletes the first member in the model's filtered list.
     */
    class DummyCommand extends UndoableCommand {
        DummyCommand(Model model) {
            this.model = model;
        }

        @Override
        public CommandResult executeUndoableCommand() throws CommandException {
            Member memberToDelete = model.getFilteredMemberList().get(0);
            try {
                model.deleteMember(memberToDelete);
            } catch (MemberNotFoundException mnfe) {
                fail("Impossible: memberToDelete was retrieved from model.");
            } catch (DeleteCurrentUserException e) {
                fail("Impossible: memberToDelete was retrieved from model.");
            }
            return new CommandResult("");
        }
    }
}
