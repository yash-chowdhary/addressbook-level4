package seedu.club.logic.commands;
//@@author yash-chowdhary
import static seedu.club.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.task.Assignee;
import seedu.club.model.task.Assignor;
import seedu.club.model.task.Date;
import seedu.club.model.task.Description;
import seedu.club.model.task.Status;
import seedu.club.model.task.Task;
import seedu.club.model.task.Time;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskAlreadyAssignedException;
import seedu.club.model.task.exceptions.TaskAssigneeUnchangedException;

/**
 * Changes the Assignee of a specified task.
 */
public class ChangeAssigneeCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "changeassignee";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "assignee")
    );
    public static final String COMMAND_FORMAT = COMMAND_WORD + " "
            + " INDEX " + PREFIX_MATRIC_NUMBER + "MATRIC NUMBER";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the assignee of the task identified"
            + " by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_MATRIC_NUMBER + "MATRIC NUMBER\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MATRIC_NUMBER + "A0123456H";

    public static final String MESSAGE_CHANGE_SUCCESS = "Assignee of task - %1$s, changed successfully to "
            + "%2$s";
    public static final String MESSAGE_NOT_CHANGED = "Assignee of task is unchanged as the assignee provided is "
            + "the same as the task's existing assignee.";
    public static final String MESSAGE_DUPLICATE_TASK = "This operation would result in a duplicate task.";
    public static final String MESSAGE_ALREADY_ASSIGNED = "Assignee of task could not be changed as there is an "
            + "identical task assigned to this member.";
    public static final String MESSAGE_MEMBER_NOT_FOUND = "This member does not exist in Club Connect.";

    private final Index index;
    private Task taskToEdit;
    private Task editedTask;
    private final Assignee newAssignee;

    public ChangeAssigneeCommand(Index index, Assignee newAssignee) {
        requireAllNonNull(index, newAssignee);
        this.index = index;
        this.newAssignee = newAssignee;
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size() || index.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }


        taskToEdit = lastShownList.get(index.getZeroBased());
        editedTask = createEditedTask(taskToEdit);

        if (taskToEdit.getAssignee().getValue().equalsIgnoreCase(editedTask.getAssignee().getValue())) {
            throw new CommandException(MESSAGE_NOT_CHANGED);
        }
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.changeAssignee(taskToEdit, editedTask);
        } catch (DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (MemberNotFoundException mnfe) {
            throw new CommandException(MESSAGE_MEMBER_NOT_FOUND);
        } catch (TaskAlreadyAssignedException e) {
            throw new CommandException(MESSAGE_ALREADY_ASSIGNED);
        } catch (TaskAssigneeUnchangedException e) {
            throw new CommandException(MESSAGE_NOT_CHANGED);
        }
        return new CommandResult(String.format(MESSAGE_CHANGE_SUCCESS, editedTask.getDescription().getDescription(),
                newAssignee.getValue()));
    }

    /**
     * Creates and returns a {@code task} with the details of {@code taskToEdit}
     * edited with {@code newAssignee}.
     */
    private Task createEditedTask(Task taskToEdit) {
        assert taskToEdit != null;

        Description description = new Description(taskToEdit.getDescription().getDescription());
        Time time = new Time(taskToEdit.getTime().getTime());
        Date date = new Date(taskToEdit.getDate().getDate());
        Assignor assignor = new Assignor(taskToEdit.getAssignor().getValue());
        Status status = new Status(taskToEdit.getStatus().getStatus());

        return new Task(description, time, date, assignor, newAssignee, status);
    }

    @Override
    public boolean equals(Object other) {
        return (other == this
                || (other instanceof ChangeAssigneeCommand
                && index.equals(((ChangeAssigneeCommand) other).index)
                && newAssignee.equals(((ChangeAssigneeCommand) other).newAssignee)));
    }
}
