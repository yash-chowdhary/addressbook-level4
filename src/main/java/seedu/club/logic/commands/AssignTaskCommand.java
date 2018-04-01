package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_PERMISSIONS;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.logic.commands.exceptions.IllegalExecutionException;
import seedu.club.model.member.Name;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;

/**
 * Adds a task to the currently logged-in member's Task list
 */
public class AssignTaskCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "assigntask";

    public static final String COMMAND_FORMAT = COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + " "
            + PREFIX_TIME + " "
            + PREFIX_DATE + " "
            + PREFIX_NAME + " ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds and assigns a task to a member in the club book. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_TIME + "TIME "
            + PREFIX_DATE + "DATE "
            + PREFIX_NAME + "NAME\n"
            + "Example " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Arrange DJ for Music Night "
            + PREFIX_DATE + "10/05/2018 "
            + PREFIX_TIME + "16:00 "
            + PREFIX_NAME + "Bernice Yu";

    public static final String MESSAGE_SUCCESS = "New task created and assigned to %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists";
    public static final String MESSAGE_MEMBER_NOT_FOUND = "This member doesn't exist in the club book";

    private final Task toAdd;
    private final Name name;

    public AssignTaskCommand(Task toAdd, Name name) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
        this.name = name;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.assignTask(toAdd, name);
            return new CommandResult(String.format(MESSAGE_SUCCESS, name));
        } catch (MemberNotFoundException mnfe) {
            throw new CommandException(MESSAGE_MEMBER_NOT_FOUND);
        } catch (DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (IllegalExecutionException iee) {
            throw new CommandException(MESSAGE_INVALID_PERMISSIONS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return (other == this)
                || (other instanceof AssignTaskCommand // instanceof handles nulls
                && toAdd.equals(((AssignTaskCommand) other).toAdd)
                && name.equals(((AssignTaskCommand) other).name));
    }
}
