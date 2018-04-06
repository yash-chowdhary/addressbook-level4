package seedu.club.logic.commands;
//@@author yash-chowdhary
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.task.exceptions.TasksAlreadyListedException;

/**
 * Lists all tasks of the currently logged-in member in the club book.
 */
public class ViewMyTasksCommand extends Command {
    public static final String  COMMAND_WORD = "viewmytasks";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "mytasks")
    );

    public static final String MESSAGE_SUCCESS = "Your tasks have been listed.";
    public static final String MESSAGE_ALREADY_LISTED = "All your tasks have already been listed.";

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        requireToSignUp();
        requireToLogIn();
        try {
            model.viewMyTasks();
        } catch (TasksAlreadyListedException tale) {
            throw new CommandException(MESSAGE_ALREADY_LISTED);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
