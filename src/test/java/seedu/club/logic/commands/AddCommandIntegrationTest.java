package seedu.club.logic.commands;

import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
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
import seedu.club.testutil.MemberBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private Model expectedModel;
    private ObservableList<Member> memberObservableList;
    private Member member;

    @Before
    public void setUp() throws CommandException {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalClubBook(), new UserPrefs());
        memberObservableList = model.getClubBook().getMemberList();
        member = memberObservableList.get(0);
        LogInCommand logInCommand = new LogInCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword());
        logInCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        logInCommand.execute();
        logInCommand.setData(expectedModel, new CommandHistory(), new UndoRedoStack());
        logInCommand.execute();
    }

    @Test
    public void execute_newMember_success() throws Exception {
        Member validMember = new MemberBuilder().build();
        expectedModel.addMember(validMember);

        assertCommandSuccess(prepareCommand(validMember, model), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validMember), expectedModel);
    }

    @Test
    public void execute_duplicateMember_throwsCommandException() {
        Member memberInList = model.getClubBook().getMemberList().get(0);
        assertCommandFailure(prepareCommand(memberInList, model), model, AddCommand.MESSAGE_DUPLICATE_MATRIC_NUMBER);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code member} into the {@code model}.
     */
    private AddCommand prepareCommand(Member member, Model model) {
        AddCommand command = new AddCommand(member);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
