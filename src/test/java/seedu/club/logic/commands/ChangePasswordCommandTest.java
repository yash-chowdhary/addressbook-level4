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
import seedu.club.model.member.Password;
import seedu.club.model.member.Username;
import seedu.club.model.member.exceptions.DataToChangeIsNotCurrentlyLoggedInMemberException;
import seedu.club.model.member.exceptions.MatricNumberNotFoundException;
import seedu.club.model.member.exceptions.PasswordIncorrectException;
//@@author th14thmusician

public class ChangePasswordCommandTest {
    private Model model;
    private Model expectedModel;
    private ObservableList<Member> observableList;
    private Password newPassword;
    private Member member;

    @Before
    public void setUp () throws CommandException {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalClubBook(), new UserPrefs());
        observableList = model.getClubBook().getMemberList();
        member = observableList.get(0);
        LogInCommand command = new LogInCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
        command.setData(expectedModel, new CommandHistory(), new UndoRedoStack());
        command.execute();
        newPassword = new Password("test");
    }

    @Test
    public void excecute_changepassword_success ()
            throws PasswordIncorrectException, DataToChangeIsNotCurrentlyLoggedInMemberException,
            MatricNumberNotFoundException {
        Member memberToChangePasswordOf = new Member(member.getName(), member.getPhone(), member.getEmail(),
                member.getMatricNumber(), member.getGroup(), member.getTags());
        expectedModel.changePassword(this.member.getCredentials().getUsername().value,
                this.member.getCredentials().getPassword().value, newPassword.value);
        assertCommandSuccess(prepareCommand(memberToChangePasswordOf, model), model,
                ChangePasswordCommand.MESSAGE_SUCCESS, expectedModel);
        expectedModel.changePassword(member.getCredentials().getUsername().value,
                member.getCredentials().getPassword().value, "password");
    }

    @Test
    public void execute_changepassword_throwscommandexception () throws PasswordIncorrectException {
        assertCommandFailure(prepareCommandThatFails(member, model), model,
                ChangePasswordCommand.MESSAGE_PASSWORD_INCORRECT);
    }
    @Test
    public void execute_changepassword_throwsauthenicationerrorexception () {
        Member othermember = observableList.get(1);
        assertCommandFailure(prepareCommand(othermember, model), model,
                ChangePasswordCommand.MESSAGE_AUTHENTICATION_FAILED);
    }

    @Test
    public void execute_changepassword_throwsincorrectusernameexception () {
        assertCommandFailure(prepareCommandThatThrowsIncorrectUsername(member, model), model,
                ChangePasswordCommand.MESSAGE_USERNAME_NOTFOUND);
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
     * Generates a ChangePasswordCommand upon exceution that throws incorrectPassword
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

    /**
     * Generates a ChangePasswordCommand upon execution that throws IncorrectUsername
     */
    private ChangePasswordCommand prepareCommandThatThrowsIncorrectUsername(Member member, Model model) {
        ChangePasswordCommand command = new ChangePasswordCommand(new Username("A0000000Z"),
                member.getCredentials().getPassword(), newPassword);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
//@@author
