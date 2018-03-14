package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Password;
import seedu.address.model.person.Username;



/**
 * Logs in a Member to ClubConnect
 */
public class LogInCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final String MESSAGE_SUCCESS = "login successful!";
    public static final String MESSAGE_FAILURE = "login unsuccessful!";

    private final Username username;
    private final Password password;

    public LogInCommand(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        if (model.logInMemberSuccessful(username.value, password.value)) {
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_FAILURE);
    }
}
