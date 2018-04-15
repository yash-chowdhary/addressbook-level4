package seedu.club.logic.commands;
//@@author yash-chowdhary
import static seedu.club.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;

import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.task.Assignee;
import seedu.club.model.task.Assignor;
import seedu.club.model.task.Date;
import seedu.club.model.task.Description;
import seedu.club.model.task.Status;
import seedu.club.model.task.Task;
import seedu.club.model.task.Time;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskNotFoundException;
import seedu.club.model.task.exceptions.TaskStatusCannotBeEditedException;

/**
 * Edits the status of a existing task in the club book.
 */
public class ChangeTaskStatusCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "changetaskstatus";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "status", "changestatus", "cts")
    );
    public static final String COMMAND_FORMAT = COMMAND_WORD + " INDEX " + PREFIX_STATUS + "STATUS";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Modifies the status of the task identified "
            + "by the index number used in the last task listing. "
            + "The existing status will be overwritten by the status provided in the command.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "st/STATUS\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_STATUS + "Completed";

    public static final String MESSAGE_INVALID_PERMISSION = "This task's status cannot be updated "
            + "as you are neither the assignor nor the assignee of the task.";
    public static final String MESSAGE_CHANGE_SUCCESS = "Status of task - %1$s, successfully changed.";
    public static final String MESSAGE_NOT_CHANGED = "Status of task is unchanged as the status provided is the "
            + "same as the task's existing status.";

    private final Index index;
    private Task taskToEdit;
    private Task editedTask;
    private final Status newStatus;

    public ChangeTaskStatusCommand(Index index, Status newStatus) {
        this.index = index;
        String status = newStatus.getStatus();
        String capitalizedStatus = WordUtils.capitalize(status);
        this.newStatus = new Status(capitalizedStatus);

    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToEdit = lastShownList.get(index.getZeroBased());
        editedTask = createEditedTask(taskToEdit);
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        try {
            requireToSignUp();
            requireToLogIn();
            model.changeStatus(taskToEdit, editedTask);
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("The target task cannot be missing");
        } catch (DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_NOT_CHANGED);
        } catch (TaskStatusCannotBeEditedException e) {
            throw new CommandException(MESSAGE_INVALID_PERMISSION);
        }
        return new CommandResult(String.format(MESSAGE_CHANGE_SUCCESS, editedTask.getDescription().getDescription()));
    }

    /**
     * Creates and returns a {@code task} with the details of {@code taskToEdit}
     * edited with {@code newStatus}.
     */
    private Task createEditedTask(Task taskToEdit) {
        assert taskToEdit != null;

        Description description = new Description(taskToEdit.getDescription().getDescription());
        Time time = new Time(taskToEdit.getTime().getTime());
        Date date = new Date(taskToEdit.getDate().getDate());
        Assignor assignor = new Assignor(taskToEdit.getAssignor().getValue());
        Assignee assignee = new Assignee(taskToEdit.getAssignee().getValue());

        return new Task(description, time, date, assignor, assignee, newStatus);
    }

    @Override
    public boolean equals(Object other) {
        return (other == this
                || (other instanceof ChangeTaskStatusCommand
                && index.equals(((ChangeTaskStatusCommand) other).index)
                && newStatus.equals(((ChangeTaskStatusCommand) other).newStatus)));
    }
}
