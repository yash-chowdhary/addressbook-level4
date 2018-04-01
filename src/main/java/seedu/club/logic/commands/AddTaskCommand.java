package seedu.club.logic.commands;
//@@author yash-chowdhary
import static java.util.Objects.requireNonNull;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;

/**
 * Adds a task to the currently logged-in member's Task list
 */
public class AddTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtask";

    public static final String COMMAND_FORMAT = COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "  "
            + PREFIX_TIME + "  "
            + PREFIX_DATE + " ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Task to the currently logged-in member's"
            + "task list. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Book YIH Function Room 4 "
            + PREFIX_DATE + "02/04/2018 "
            + PREFIX_TIME + "17:00";

    public static final String MESSAGE_SUCCESS = "New task created";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists";

    private final Task toAdd;

    public AddTaskCommand(Task toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addTaskToTaskList(toAdd);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}
