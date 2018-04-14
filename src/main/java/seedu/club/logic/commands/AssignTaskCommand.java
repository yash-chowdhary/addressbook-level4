package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskAlreadyAssignedException;

/**
 * Adds a task to the currently logged-in member's Task list
 */
public class AssignTaskCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "assigntask";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "assignt")
    );

    public static final String COMMAND_FORMAT = COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + " "
            + PREFIX_TIME + " "
            + PREFIX_DATE + " "
            + PREFIX_MATRIC_NUMBER + " ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a task to a member in Club Connect.\n"
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_TIME + "TIME "
            + PREFIX_DATE + "DATE "
            + PREFIX_MATRIC_NUMBER + "MATRICULATION_NUMBER\n"
            + "Example " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Arrange DJ for Music Night "
            + PREFIX_DATE + "10/06/2018 "
            + PREFIX_TIME + "16:00 "
            + PREFIX_MATRIC_NUMBER + "A1234567H";

    public static final String MESSAGE_SUCCESS = "New task created and assigned to %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists.";
    public static final String MESSAGE_ALREADY_ASSIGNED = "This task has already been assigned to this member by "
            + "another Exco member.";
    public static final String MESSAGE_MEMBER_NOT_FOUND = "This member does not exist in Club Connect.";

    private final Task toAdd;
    private final MatricNumber matricNumber;

    public AssignTaskCommand(Task toAdd, MatricNumber matricNumber) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
        this.matricNumber = matricNumber;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        try {
            model.assignTask(toAdd, matricNumber);
            return new CommandResult(String.format(MESSAGE_SUCCESS, matricNumber));
        } catch (MemberNotFoundException mnfe) {
            throw new CommandException(MESSAGE_MEMBER_NOT_FOUND);
        } catch (DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskAlreadyAssignedException e) {
            throw new CommandException(MESSAGE_ALREADY_ASSIGNED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return (other == this)
                || (other instanceof AssignTaskCommand // instanceof handles nulls
                && toAdd.equals(((AssignTaskCommand) other).toAdd)
                && matricNumber.equals(((AssignTaskCommand) other).matricNumber));
    }
}
