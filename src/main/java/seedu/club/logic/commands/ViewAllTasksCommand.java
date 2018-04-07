package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.task.exceptions.TasksCannotBeDisplayedException;

/**
 * Lists all tasks in the club book to the user.
 */
public class ViewAllTasksCommand extends Command {

    public static final String COMMAND_WORD = "viewalltasks";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "alltasks")
    );

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all tasks in Club Connect.";

    public static final String MESSAGE_SUCCESS = "All tasks are displayed.";
    public static final String MESSAGE_CANNOT_VIEW = "You do not have permission to view all tasks.";

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        try {
            model.viewAllTasks();
        } catch (TasksCannotBeDisplayedException tcbde) {
            throw new CommandException(MESSAGE_CANNOT_VIEW);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
