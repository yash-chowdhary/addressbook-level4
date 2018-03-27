package seedu.club.logic.commands;

//@@author yash-chowdhary

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.TaskCannotBeDeletedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;

/**
 * Deletes a task identified using its last displayed index from the club book.
 */
public class DeleteTaskCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deletetask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String COMMAND_FORMAT = COMMAND_WORD + " INDEX";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_TASK_CANNOT_BE_DELETED = "This task cannot be deleted as you are "
            + " neither the assignor nor the assignee";
    public static final String MESSAGE_TASK_NOT_FOUND = "This task doesn't exist in Club Book";

    private final Index targetIndex;

    private Task taskToDelete;

    public DeleteTaskCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(taskToDelete);
        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException tnfe) {
            throw new CommandException(MESSAGE_TASK_NOT_FOUND);
        } catch (TaskCannotBeDeletedException tcbde) {
            throw new CommandException(MESSAGE_TASK_CANNOT_BE_DELETED);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete.getDescription()
                .getDescription()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTaskCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteTaskCommand) other).targetIndex) // state check
                && Objects.equals(this.taskToDelete, ((DeleteTaskCommand) other).taskToDelete));
    }
}
