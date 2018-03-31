package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.task.exceptions.TasksCannotBeDisplayedException;

/**
 * Lists all tasks in the club book to the user.
 */
public class ViewAllTasksCommand extends Command {

    public static final String COMMAND_WORD = "viewalltasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all the tasks in the club book";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_CANNOT_VIEW = "You do not have permission to view tasks";

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.viewAllTasks();
        } catch (TasksCannotBeDisplayedException tcbde) {
            throw new CommandException(MESSAGE_CANNOT_VIEW);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
