package seedu.club.logic.commands;

import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.member.Member;
import seedu.club.model.member.Password;
import seedu.club.model.member.exceptions.PasswordIncorrectException;

public class ChangePasswordCommandTest {
    private Model model;
    private ObservableList<Member> observableList;
    private Password newPassword;
    private Member member;

    @Before
    public void setUp () {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        observableList = model.getClubBook().getMemberList();
        newPassword = new Password("test");
        member = observableList.get(0);
        LogInCommand logInCommand = new LogInCommand(observableList.get(0).getCredentials().getUsername(),
                observableList.get(0).getCredentials().getPassword());
        logInCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void excecute_changepassword_success () throws PasswordIncorrectException {
        Model expectedModel = model;
        expectedModel.changePassword(this.member.getCredentials().getUsername().value,
                this.member.getCredentials().getPassword().value, newPassword.value);
        assertCommandSuccess(prepareCommand(this.member, model), model,
                ChangePasswordCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_changepassword_throwscommandexception () throws PasswordIncorrectException {
        assertCommandFailure(prepareCommandThatFails(member, model), model,
                ChangePasswordCommand.MESSAGE_FAILURE);
    }

    /**
     * Generates a ChangePasswordCommand upon execution
     * @param member
     * @param model
     * @return
     */
    private ChangePasswordCommand prepareCommand(Member member, Model model) {
        ChangePasswordCommand command = new ChangePasswordCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword(), newPassword);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a ChangePasswordCommand upon exceution that fails
     * @param member
     * @param model
     * @return
     */
    private ChangePasswordCommand prepareCommandThatFails(Member member, Model model) {
        ChangePasswordCommand command = new ChangePasswordCommand(member.getCredentials().getUsername(),
                new Password("fake"), newPassword);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
