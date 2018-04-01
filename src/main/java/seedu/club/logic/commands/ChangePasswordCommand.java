package seedu.club.logic.commands;

import static seedu.club.logic.parser.CliSyntax.PREFIX_NEWPASSWORD;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.club.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.member.Password;
import seedu.club.model.member.Username;
import seedu.club.model.member.exceptions.PasswordIncorrectException;



//@@author Song Weiyang
/**
 * Changes the password of a member in the ClubBook
 */
public class ChangePasswordCommand extends Command {
    public static final String COMMAND_WORD = "changepass";
    public static final String COMMAND_FORMAT = "changepass u/ pw/ npw/";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the password of a member in the ClubBook"
            + "Parameters: "
            + PREFIX_USERNAME + "username "
            + PREFIX_PASSWORD + "oldpassword "
            + PREFIX_NEWPASSWORD + "newpassword";
    public static final String MESSAGE_SUCCESS = "Password changed successfully!";
    public static final String MESSAGE_FAILURE = "Password is incorrect";
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
        try {
            model.changePassword(username.value, oldPassword.value, newPassword.value);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (PasswordIncorrectException e) {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
