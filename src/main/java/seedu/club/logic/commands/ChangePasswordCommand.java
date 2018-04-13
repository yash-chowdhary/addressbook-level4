package seedu.club.logic.commands;

import static seedu.club.logic.parser.CliSyntax.PREFIX_NEWPASSWORD;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.club.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;
import seedu.club.model.member.Password;
import seedu.club.model.member.Username;
import seedu.club.model.member.exceptions.DataToChangeIsNotCurrentlyLoggedInMemberException;
import seedu.club.model.member.exceptions.MatricNumberNotFoundException;
import seedu.club.model.member.exceptions.PasswordIncorrectException;

//@@author th14thmusician
/**
 * Changes the password of a member in the ClubBook
 */
public class ChangePasswordCommand extends Command {
    public static final String COMMAND_WORD = "changepass";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "changepw")
    );
    public static final String COMMAND_FORMAT = "changepass u/ pw/ npw/";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes your password.\n"
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "OLD_PASSWORD "
            + PREFIX_NEWPASSWORD + "NEW_PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "A0164589X "
            + PREFIX_PASSWORD + "password "
            + PREFIX_NEWPASSWORD + "iLovecats18";
    public static final String MESSAGE_SUCCESS = "Password changed successfully.";
    public static final String MESSAGE_PASSWORD_INCORRECT = "The old password entered is incorrect.";
    public static final String MESSAGE_AUTHENTICATION_FAILED = "You can only change your own password.";
    public static final String MESSAGE_USERNAME_NOTFOUND = "This username does not exist.";
    private Username username;
    private Password oldPassword;
    private Password newPassword;

    public ChangePasswordCommand (Username username, Password oldPassword, Password newPassword) {
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        try {
            model.changePassword(username.value, oldPassword.value, newPassword.value);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (PasswordIncorrectException e) {
            throw new CommandException(MESSAGE_PASSWORD_INCORRECT);
        } catch (DataToChangeIsNotCurrentlyLoggedInMemberException dataToChangeIsNotCurrentlyLoggedInMemberException) {
            throw new CommandException(MESSAGE_AUTHENTICATION_FAILED);
        } catch (MatricNumberNotFoundException e) {
            throw new CommandException(MESSAGE_USERNAME_NOTFOUND);
        }
    }

    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        super.setData(model, history, undoRedoStack);
    }
}
//@@author
