package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.club.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.UpdateCurrentlyLogInMemberEvent;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;
import seedu.club.model.member.Password;
import seedu.club.model.member.Username;
//@@author th14thmusician
/**
 * Logs in a member to ClubConnect
 */
public class LogInCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "signin")
    );

    public static final String COMMAND_FORMAT = "login u/ pw/ ";

    public static final String MESSAGE_SUCCESS = "Hi %1$s. Welcome to Club Connect!";
    public static final String MESSAGE_FAILURE = "Login unsuccessful."
            + " Incorrect Username or Password entered. Please try again.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows you to log in to Club Connect.\n"
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "A0167855F" + " "
            + PREFIX_PASSWORD + "password";
    private final Username username;
    private final Password password;

    public LogInCommand(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        requireToSignUp();
        requireToLogOut();
        model.logsInMember(username.value, password.value);
        if (model.getLoggedInMember() != null) {
            EventsCenter.getInstance().post(new UpdateCurrentlyLogInMemberEvent(model.getLoggedInMember()));
            return new CommandResult(String.format(MESSAGE_SUCCESS, model.getLoggedInMember().getName().toString()));
        }
        return new CommandResult(MESSAGE_FAILURE);
    }

    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        super.setData(model, history, undoRedoStack);
    }
}
//@@author
