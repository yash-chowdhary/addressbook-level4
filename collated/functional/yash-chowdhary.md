# yash-chowdhary
###### \java\seedu\club\commons\events\ui\SendEmailRequestEvent.java
``` java
import seedu.club.commons.events.BaseEvent;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;


/**
 * Event to send an email.
 */
public class SendEmailRequestEvent extends BaseEvent {

    private String recipients;
    private Subject subject;
    private Body body;
    private Client client;

    public SendEmailRequestEvent(String recipients, Subject subject, Body body, Client client) {
        this.recipients = recipients;
        this.subject = subject;
        this.body = body;
        this.client = client;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getRecipients() {
        return recipients;
    }

    public Subject getSubject() {
        return subject;
    }

    public Body getBody() {
        return body;
    }

    public Client getClient() {
        return client;
    }
}
```
###### \java\seedu\club\commons\events\ui\TaskPanelSelectionChangedEvent.java
``` java

import seedu.club.commons.events.BaseEvent;
import seedu.club.ui.TaskCard;

/**
 * Represents a change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {
    private final TaskCard newSelection;

    public TaskPanelSelectionChangedEvent(TaskCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TaskCard getNewSelection() {
        return newSelection;
    }
}
```
###### \java\seedu\club\logic\commands\AddTaskCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;

/**
 * Adds a task to the currently logged-in member's Task list
 */
public class AddTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtask";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "addt", "task")
    );
    public static final String COMMAND_FORMAT = COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "  "
            + PREFIX_TIME + "  "
            + PREFIX_DATE + " ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to your task list.\n"
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Book YIH Function Room 4 "
            + PREFIX_DATE + "02/06/2018 "
            + PREFIX_TIME + "17:00";

    public static final String MESSAGE_SUCCESS = "New task created.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists.";

    private final Task toAdd;

    public AddTaskCommand(Task toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        requireToSignUp();
        requireToLogIn();
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
```
###### \java\seedu\club\logic\commands\ChangeAssigneeCommand.java
``` java
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
```
###### \java\seedu\club\logic\commands\ChangeTaskStatusCommand.java
``` java
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
```
###### \java\seedu\club\logic\commands\DeleteGroupCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.club.commons.core.Messages.MESSAGE_MANDATORY_GROUP;
import static seedu.club.commons.core.Messages.MESSAGE_NON_EXISTENT_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;

/**
 * Removes a group from the Club Book
 */
public class DeleteGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletegroup";
    public static final String COMMAND_FORMAT = "deletegroup g/ ";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "rmgroup", "delgroup")
    );

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a Group from the Club Connect.\n"
            + "Parameters: "
            + PREFIX_GROUP + "GROUP";

    public static final String MESSAGE_SUCCESS = "Deleted group: %1$s";

    private final Group toRemove;

    /**
     * Creates an AddCommand to add the specified {@code member}
     */
    public DeleteGroupCommand(Group group) {
        requireNonNull(group);
        toRemove = group;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        try {
            model.deleteGroup(toRemove);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toRemove));
        } catch (GroupNotFoundException gnfe) {
            throw new CommandException(String.format(MESSAGE_NON_EXISTENT_GROUP, toRemove));
        } catch (GroupCannotBeRemovedException gcbre) {
            throw new CommandException(String.format(MESSAGE_MANDATORY_GROUP, toRemove.toString()));
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupCommand // instanceof handles nulls
                && toRemove.equals(((DeleteGroupCommand) other).toRemove));
    }
}
```
###### \java\seedu\club\logic\commands\DeleteTaskCommand.java
``` java

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
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
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "deltask", "rmtask")
    );

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String COMMAND_FORMAT = COMMAND_WORD + " INDEX";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";
    public static final String MESSAGE_TASK_CANNOT_BE_DELETED = "This task cannot be deleted as you are "
            + " not the Assignor of the task.";
    public static final String MESSAGE_TASK_NOT_FOUND = "This task does not exist in Club Connect.";

    private final Index targetIndex;

    private Task taskToDelete;

    public DeleteTaskCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(taskToDelete);
        requireToSignUp();
        requireToLogIn();
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
        requireToSignUp();
        requireToLogIn();
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
```
###### \java\seedu\club\logic\commands\EmailCommand.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_NON_EXISTENT_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.club.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;

/**
 * Sends an email to the desired recipient(s) in a particular group of the club book.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "mail")
    );
    public static final String COMMAND_FORMAT = "email [g/ ] [t/ ] c/ [s/ ] [b/ ]";

    public static final String COMMAND_USAGE = COMMAND_WORD + ": Sends an email to the desired recipients(s) "
            + "in EITHER a particular group OR a particular tag of the club book.\n"
            + "Parameters: " + " "
            + PREFIX_GROUP + "GROUP" + " [OR] "
            + PREFIX_TAG + "TAG" + " "
            + PREFIX_CLIENT + "EMAIL CLIENT" + " "
            + PREFIX_SUBJECT + "SUBJECT" + " "
            + PREFIX_BODY + "BODY\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP + "Member "
            + PREFIX_CLIENT + "gmail "
            + PREFIX_SUBJECT + "New Club Management application "
            + PREFIX_BODY + "Hi all, I hope you have enjoyed using Club Connect so far. "
            + "Please do share your experience with us. Regards, John Doe";

    public static final String EMAIL_CLIENT_OPENED = "Email client opened.";
    public static final String MESSAGE_NOT_SENT = "Please adhere to the command usage.";

    private Tag tag;
    private Group group;
    private Client client;
    private Subject subject;
    private Body body;

    public EmailCommand(Group group, Tag tag, Client client, Subject subject, Body body) {
        this.group = group;
        this.tag = tag;
        this.client = client;
        this.subject = new Subject(subject.toString().replaceAll("\\s", "+"));
        this.body = new Body(body.toString().replaceAll("\\s", "+"));
    }


    @Override
    public CommandResult execute() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        try {
            String emailRecipients = model.generateEmailRecipients(group, tag);
            model.sendEmail(emailRecipients, client, subject, body);
            return new CommandResult(EMAIL_CLIENT_OPENED);
        } catch (GroupNotFoundException gnfe) {
            throw new CommandException(String.format(MESSAGE_NON_EXISTENT_GROUP, group));
        } catch (TagNotFoundException tnfe) {
            throw new CommandException(DeleteTagCommand.MESSAGE_NON_EXISTENT_TAG);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && (group == ((EmailCommand) other).group || group.equals(((EmailCommand) other).group))
                && (tag == ((EmailCommand) other).tag || tag.equals(((EmailCommand) other).tag))
                && client.equals(((EmailCommand) other).client));
    }
}
```
###### \java\seedu\club\logic\commands\ViewAllTasksCommand.java
``` java
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.task.exceptions.TasksAlreadyListedException;

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
    public static final String MESSAGE_ALREADY_LISTED = "All the tasks in Club Connect are already listed.";

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        try {
            model.viewAllTasks();
        } catch (TasksAlreadyListedException e) {
            throw new CommandException(MESSAGE_ALREADY_LISTED);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\club\logic\commands\ViewMyTasksCommand.java
``` java
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
```
###### \java\seedu\club\logic\parser\AddTaskCommandParser.java
``` java

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TIME;

import java.text.SimpleDateFormat;
import java.util.stream.Stream;

import seedu.club.commons.core.Messages;
import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.AddTaskCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.task.Date;
import seedu.club.model.task.Description;
import seedu.club.model.task.Task;
import seedu.club.model.task.Time;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    @Override
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        try {
            Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());

            long currentTimeMillis = System.currentTimeMillis();
            String enteredDateString = date.getDate() + " " + time.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            long enteredDateMillis = Long.MIN_VALUE;

            try {
                java.util.Date enteredDate = formatter.parse(enteredDateString);
                enteredDateMillis = enteredDate.getTime();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            if (enteredDateMillis < currentTimeMillis) {
                throw new IllegalValueException(Messages.MESSAGE_DATE_ALREADY_PASSED);
            }

            Task newTask = new Task(description, time, date);

            return new AddTaskCommand(newTask);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\club\logic\parser\AssignTaskCommandParser.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TIME;

import java.text.SimpleDateFormat;
import java.util.stream.Stream;

import seedu.club.commons.core.Messages;
import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.AssignTaskCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.task.Date;
import seedu.club.model.task.Description;
import seedu.club.model.task.Task;
import seedu.club.model.task.Time;

/**
 * Parses input arguments and creates a new AssignTaskCommand object
 */
public class AssignTaskCommandParser implements Parser<AssignTaskCommand> {

    @Override
    public AssignTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_TIME, PREFIX_DATE, PREFIX_MATRIC_NUMBER);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_TIME, PREFIX_DATE, PREFIX_MATRIC_NUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE));
        }

        try {
            Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
            MatricNumber matricNumber = ParserUtil.parseMatricNumber(argMultimap.getValue(PREFIX_MATRIC_NUMBER).get());

            long currentTimeMillis = System.currentTimeMillis();
            String enteredDateString = date.getDate() + " " + time.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            long enteredDateMillis = Long.MIN_VALUE;

            try {
                java.util.Date enteredDate = formatter.parse(enteredDateString);
                enteredDateMillis = enteredDate.getTime();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            if (enteredDateMillis < currentTimeMillis) {
                throw new IllegalValueException(Messages.MESSAGE_DATE_ALREADY_PASSED);
            }

            Task newTask = new Task(description, time, date);

            return new AssignTaskCommand(newTask, matricNumber);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\club\logic\parser\ChangeAssigneeCommandParser.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;

import java.util.stream.Stream;

import seedu.club.commons.core.index.Index;
import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.ChangeAssigneeCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.task.Assignee;

/**
 * arses input arguments and creates a new ChangeAssignee object
 */
public class ChangeAssigneeCommandParser implements Parser<ChangeAssigneeCommand> {
    @Override
    public ChangeAssigneeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MATRIC_NUMBER);

        if (!arePrefixesPresent(argMultimap, PREFIX_MATRIC_NUMBER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeAssigneeCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeAssigneeCommand.MESSAGE_USAGE));
        }

        try {
            MatricNumber matricNumber = ParserUtil.parseMatricNumber(argMultimap.getValue(PREFIX_MATRIC_NUMBER)).get();
            Assignee assignee = new Assignee(matricNumber.toString());
            return new ChangeAssigneeCommand(index, assignee);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\club\logic\parser\ChangeTaskStatusCommandParser.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.stream.Stream;

import seedu.club.commons.core.index.Index;
import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.ChangeTaskStatusCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.task.Status;

/**
 * Parses input arguments and creates a new ChangeTaskStatus object
 */
public class ChangeTaskStatusCommandParser implements Parser<ChangeTaskStatusCommand> {

    @Override
    public ChangeTaskStatusCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STATUS);

        if (!arePrefixesPresent(argMultimap, PREFIX_STATUS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeTaskStatusCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeTaskStatusCommand.MESSAGE_USAGE));
        }

        try {
            Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS)).get();
            return new ChangeTaskStatusCommand(index, status);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\club\logic\parser\ClubBookParser.java
``` java
        if (isAddCommand(commandWord)) {
            return new AddCommandParser().parse(arguments);
        } else if (isAddPollCommand(commandWord)) {
            return new AddPollCommandParser().parse(arguments);
        } else if (isAddTaskCommand(commandWord)) {
            return new AddTaskCommandParser().parse(arguments);
        } else if (isAssignTaskCommand(commandWord)) {
            return new AssignTaskCommandParser().parse(arguments);
        } else if (isChangeAssigneeCommand(commandWord)) {
            return new ChangeAssigneeCommandParser().parse(arguments);
        } else if (isChangePasswordCommand(commandWord)) {
            return new ChangePasswordCommandParser().parse(arguments);
        } else if (isChangePicCommand(commandWord)) {
            return new ChangeProfilePhotoCommandParser().parse(arguments);
        } else if (isChangeTaskStatusCommand(commandWord)) {
            return new ChangeTaskStatusCommandParser().parse(arguments);
        } else if (isClearCommand(commandWord)) {
            return new ClearCommandParser().parse(arguments);
        } else if (isCompressCommand(commandWord)) {
            return new CompressCommand();
        } else if (isDecompressCommand(commandWord)) {
            return new DecompressCommand();
        } else if (isDeleteCommand(commandWord)) {
            return new DeleteCommandParser().parse(arguments);
        } else if (isDeletePollCommand(commandWord)) {
            return new DeletePollCommandParser().parse(arguments);
        } else if (isDeleteTagCommand(commandWord)) {
            return new DeleteTagCommandParser().parse(arguments);
        } else if (isDeleteTaskCommand(commandWord)) {
            return new DeleteTaskCommandParser().parse(arguments);
        } else if (isEditCommand(commandWord)) {
            return new EditCommandParser().parse(arguments);
        } else if (isEmailCommand(commandWord)) {
            return new EmailCommandParser().parse(arguments);
        } else if (isExitCommand(commandWord)) {
            return new ExitCommand();
        } else if (isExportCommand(commandWord)) {
            return new ExportCommandParser().parse(arguments);
        } else if (isFindCommand(commandWord)) {
            return new FindCommandParser().parse(arguments);
        } else if (isHelpCommand(commandWord)) {
            return new HelpCommand();
        } else if (isHideResults(commandWord)) {
            return new HideResultsCommand();
        } else if (isHistoryCommand(commandWord)) {
            return new HistoryCommand();
        } else if (isImportCommand(commandWord)) {
            return new ImportCommandParser().parse(arguments);
        } else if (isListCommand(commandWord)) {
            return new ListCommand();
        } else if (isLoginCommand(commandWord)) {
            return new LoginCommandParser().parse(arguments);
        } else if (isLogoutCommand(commandWord)) {
            return new LogOutCommand();
        } else if (isRedoCommand(commandWord)) {
            return new RedoCommand();
        } else if (isRemovePicCommand(commandWord)) {
            return new RemoveProfilePhotoCommand();
        } else if (isDeleteGroupCommand(commandWord)) {
            return new DeleteGroupCommandParser().parse(arguments);
        } else if (isSelectCommand(commandWord)) {
            return new SelectCommandParser().parse(arguments);
        } else if (isViewResultsCommand(commandWord)) {
            return new ViewResultsCommand();
        } else if (isSignUpCommand(commandWord)) {
            return new SignUpCommandParser().parse(arguments);
        } else if (isUndoCommand(commandWord)) {
            return new UndoCommand();
        } else if (isViewAllTasksCommand(commandWord)) {
            return new ViewAllTasksCommand();
        } else if (isViewMyTasksCommand(commandWord)) {
            return new ViewMyTasksCommand();
        } else if (isVoteCommand(commandWord)) {
            return new VoteCommandParser().parse(arguments);
        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Returns true if {@code commandWord} matches any of ChangeAssigneeCommand's aliases
     */
    private boolean isChangeAssigneeCommand(String commandWord) {
        for (String commandAlias : ChangeAssigneeCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ChangeTaskStatusCommand's aliases
     */
    private boolean isChangeTaskStatusCommand(String commandWord) {
        for (String commandAlias : ChangeTaskStatusCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of VoteCommand's aliases
     */
    private boolean isVoteCommand(String commandWord) {
        for (String commandAlias : VoteCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ChangePasswordCommand's aliases
     */
    private boolean isChangePasswordCommand(String commandWord) {
        for (String commandAlias : ChangePasswordCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ViewMyTasksCommand's aliases
     */
    private boolean isViewMyTasksCommand(String commandWord) {
        for (String commandAlias : ViewMyTasksCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ViewAllTasksCommand's aliases
     */
    private boolean isViewAllTasksCommand(String commandWord) {
        for (String commandAlias : ViewAllTasksCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of UndoCommand's aliases
     */
    private boolean isUndoCommand(String commandWord) {
        for (String commandAlias : UndoCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of SignUpCommand's aliases
     */
    private boolean isSignUpCommand(String commandWord) {
        for (String commandAlias : SignUpCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ViewResultsCommand's aliases
     */
    private boolean isViewResultsCommand(String commandWord) {
        for (String commandAlias : ViewResultsCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of SelectCommand's aliases
     */
    private boolean isSelectCommand(String commandWord) {
        for (String commandAlias : SelectCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of DeleteGroupCommand's aliases
     */
    private boolean isDeleteGroupCommand(String commandWord) {
        for (String commandAlias : DeleteGroupCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of RedoCommand's aliases
     */
    private boolean isRedoCommand(String commandWord) {
        for (String commandAlias : RedoCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of LogoutCommand's aliases
     */
    private boolean isLogoutCommand(String commandWord) {
        for (String commandAlias : LogOutCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of LoginCommand's aliases
     */
    private boolean isLoginCommand(String commandWord) {
        for (String commandAlias : LogInCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ListCommand's aliases
     */
    private boolean isListCommand(String commandWord) {
        for (String commandAlias : ListCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of HistoryCommand's aliases
     */
    private boolean isHistoryCommand(String commandWord) {
        for (String commandAlias : HistoryCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of HideResultsCommand's aliases
     */
    private boolean isHideResults(String commandWord) {
        for (String commandAlias : HideResultsCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of HelpCommand's aliases
     */
    private boolean isHelpCommand(String commandWord) {
        for (String commandAlias : HelpCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of FindCommand's aliases
     */
    private boolean isFindCommand(String commandWord) {
        for (String commandAlias : FindCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ImportCommand's aliases
     */
    private boolean isImportCommand(String commandWord) {
        for (String commandAlias : ImportCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ExportCommand's aliases
     */
    private boolean isExportCommand(String commandWord) {
        for (String commandAlias : ExportCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ExitCommand's aliases
     */
    private boolean isExitCommand(String commandWord) {
        for (String commandAlias : ExitCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of EmailCommand's aliases
     */
    private boolean isEmailCommand(String commandWord) {
        for (String commandAlias : EmailCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of EditCommand's aliases
     */
    private boolean isEditCommand(String commandWord) {
        for (String commandAlias : EditCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of DeleteTaskCommand's aliases
     */
    private boolean isDeleteTaskCommand(String commandWord) {
        for (String commandAlias : DeleteTaskCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of DeleteTagCommand's aliases
     */
    private boolean isDeleteTagCommand(String commandWord) {
        for (String commandAlias : DeleteTagCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of DeletePollCommand's aliases
     */
    private boolean isDeletePollCommand(String commandWord) {
        for (String commandAlias : DeletePollCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of DecompressCommand's aliases
     */
    private boolean isDeleteCommand(String commandWord) {
        for (String commandAlias : DeleteCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of DecompressCommand's aliases
     */
    private boolean isDecompressCommand(String commandWord) {
        for (String commandAlias : DecompressCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of CompressCommand's aliases
     */
    private boolean isCompressCommand(String commandWord) {
        for (String commandAlias : CompressCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ClearCommand's aliases
     */
    private boolean isClearCommand(String commandWord) {
        for (String commandAlias : ClearCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ChangeProfilePhotoCommand's aliases
     */
    private boolean isChangePicCommand(String commandWord) {
        for (String commandAlias : ChangeProfilePhotoCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of AssignTaskCommand's aliases
     */
    private boolean isAssignTaskCommand(String commandWord) {
        for (String commandAlias : AssignTaskCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of AddPollCommand's aliases
     */
    private boolean isAddTaskCommand(String commandWord) {
        for (String commandAlias : AddTaskCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of AddPollCommand's aliases
     */
    private boolean isAddPollCommand(String commandWord) {
        for (String commandAlias : AddPollCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of AddCommand's aliases
     */
    private boolean isAddCommand(String commandWord) {
        for (String commandAlias : AddCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of RemoveProfilePhotoCommand's aliases
     */
    private boolean isRemovePicCommand(String commandWord) {
        for (String commandAlias : RemoveProfilePhotoCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }
```
###### \java\seedu\club\logic\parser\CommandList.java
``` java

import java.util.ArrayList;
import java.util.Collections;

import seedu.club.logic.commands.AddCommand;
import seedu.club.logic.commands.AddPollCommand;
import seedu.club.logic.commands.AddTaskCommand;
import seedu.club.logic.commands.AssignTaskCommand;
import seedu.club.logic.commands.ChangeAssigneeCommand;
import seedu.club.logic.commands.ChangePasswordCommand;
import seedu.club.logic.commands.ChangeProfilePhotoCommand;
import seedu.club.logic.commands.ChangeTaskStatusCommand;
import seedu.club.logic.commands.ClearCommand;
import seedu.club.logic.commands.CompressCommand;
import seedu.club.logic.commands.DecompressCommand;
import seedu.club.logic.commands.DeleteCommand;
import seedu.club.logic.commands.DeleteGroupCommand;
import seedu.club.logic.commands.DeletePollCommand;
import seedu.club.logic.commands.DeleteTagCommand;
import seedu.club.logic.commands.DeleteTaskCommand;
import seedu.club.logic.commands.EditCommand;
import seedu.club.logic.commands.EmailCommand;
import seedu.club.logic.commands.ExitCommand;
import seedu.club.logic.commands.ExportCommand;
import seedu.club.logic.commands.FindCommand;
import seedu.club.logic.commands.HelpCommand;
import seedu.club.logic.commands.HideResultsCommand;
import seedu.club.logic.commands.HistoryCommand;
import seedu.club.logic.commands.ImportCommand;
import seedu.club.logic.commands.ListCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.LogOutCommand;
import seedu.club.logic.commands.RedoCommand;
import seedu.club.logic.commands.RemoveProfilePhotoCommand;
import seedu.club.logic.commands.SelectCommand;
import seedu.club.logic.commands.SignUpCommand;
import seedu.club.logic.commands.UndoCommand;
import seedu.club.logic.commands.ViewAllTasksCommand;
import seedu.club.logic.commands.ViewMyTasksCommand;
import seedu.club.logic.commands.ViewResultsCommand;
import seedu.club.logic.commands.VoteCommand;

/**
 * Stores list of commands
 */
public class CommandList {

    private final ArrayList<String> commandList = new ArrayList<>();

    public ArrayList<String> getCommandList() {
        commandList.add(AddCommand.COMMAND_FORMAT);
        commandList.add(ChangeProfilePhotoCommand.COMMAND_FORMAT);
        commandList.add(ClearCommand.COMMAND_WORD);
        commandList.add(CompressCommand.COMMAND_WORD);
        commandList.add(ChangeTaskStatusCommand.COMMAND_FORMAT);
        commandList.add(DecompressCommand.COMMAND_WORD);
        commandList.add(DeleteCommand.COMMAND_FORMAT);
        commandList.add(DeleteTagCommand.COMMAND_FORMAT);
        commandList.add(EditCommand.COMMAND_FORMAT);
        commandList.add(EmailCommand.COMMAND_FORMAT);
        commandList.add(ExitCommand.COMMAND_WORD);
        commandList.add(FindCommand.COMMAND_FORMAT);
        commandList.add(HelpCommand.COMMAND_WORD);
        commandList.add(ListCommand.COMMAND_WORD);
        commandList.add(LogInCommand.COMMAND_FORMAT);
        commandList.add(RedoCommand.COMMAND_WORD);
        commandList.add(DeleteGroupCommand.COMMAND_FORMAT);
        commandList.add(SelectCommand.COMMAND_FORMAT);
        commandList.add(UndoCommand.COMMAND_WORD);
        commandList.add(AddTaskCommand.COMMAND_FORMAT);
        commandList.add(DeleteTaskCommand.COMMAND_FORMAT);
        commandList.add(ViewResultsCommand.COMMAND_WORD);
        commandList.add(HideResultsCommand.COMMAND_WORD);
        commandList.add(ViewAllTasksCommand.COMMAND_WORD);
        commandList.add(ViewMyTasksCommand.COMMAND_WORD);
        commandList.add(AssignTaskCommand.COMMAND_FORMAT);
        commandList.add(AddPollCommand.COMMAND_FORMAT);
        commandList.add(DeletePollCommand.COMMAND_FORMAT);
        commandList.add(ExportCommand.COMMAND_FORMAT);
        commandList.add(LogOutCommand.COMMAND_WORD);
        commandList.add(VoteCommand.COMMAND_WORD);
        commandList.add(SignUpCommand.COMMAND_FORMAT);
        commandList.add(ChangeTaskStatusCommand.COMMAND_FORMAT);
        commandList.add(ImportCommand.COMMAND_FORMAT);
        commandList.add(ChangePasswordCommand.COMMAND_FORMAT);
        commandList.add(HistoryCommand.COMMAND_WORD);
        commandList.add(ChangeAssigneeCommand.COMMAND_FORMAT);
        commandList.add(RemoveProfilePhotoCommand.COMMAND_FORMAT);

        Collections.sort(commandList);
        return commandList;
    }
}
```
###### \java\seedu\club\logic\parser\DeleteGroupCommandParser.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.stream.Stream;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.DeleteGroupCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.group.Group;

/**
 * Parses input arguments and creates a new DeleteGroupCommand object
 */
public class DeleteGroupCommandParser implements Parser<DeleteGroupCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteGroupCommand
     * and returns an DeleteGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_GROUP)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE));
        }

        try {
            Group group = ParserUtil.parseGroup(argumentMultimap.getValue(PREFIX_GROUP).get());

            return new DeleteGroupCommand(group);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty values in the given
     * {@code ArgumentMultimap}
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\club\logic\parser\DeleteTaskCommandParser.java
``` java

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.club.commons.core.index.Index;
import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.DeleteTaskCommand;
import seedu.club.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteTaskCommand object
 */
public class DeleteTaskCommandParser implements Parser<DeleteTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTaskCommand
     * and returns an DeleteTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteTaskCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteTaskCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteTaskCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\club\logic\parser\EmailCommandParser.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.club.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.stream.Stream;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.EmailCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.tag.Tag;

/**
 * Parses input arguments and creates a new EmailCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    @Override
    public EmailCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GROUP, PREFIX_TAG, PREFIX_CLIENT,
                PREFIX_SUBJECT, PREFIX_BODY);

        if (arePrefixesPresent(argMultimap, PREFIX_GROUP) && arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.COMMAND_USAGE));
        }
        if (!((arePrefixesPresent(argMultimap, PREFIX_GROUP)) || arePrefixesPresent(argMultimap, PREFIX_TAG))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.COMMAND_USAGE));
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_CLIENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.COMMAND_USAGE));
        }

        try {

            Group group = ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP)).orElse(null);
            Tag tag = ParserUtil.parseOptionalTag(argMultimap.getValue(PREFIX_TAG)).orElse(null);

            Client client = ParserUtil.parseClient(argMultimap.getValue(PREFIX_CLIENT)).get();
            Subject subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT))
                    .orElse(new Subject(Subject.EMPTY_SUBJECT_STRING));
            Body body = ParserUtil.parseBody(argMultimap.getValue(PREFIX_BODY))
                    .orElse(new Body(Body.EMPTY_BODY_STRING));

            return new EmailCommand(group, tag, client, subject, body);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\club\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String group} into a {@code Group}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code group} is invalid.
     */
    public static Group parseGroup(String group) throws IllegalValueException {
        requireNonNull(group);
        String trimmedGroup = group.trim();
        if (!Group.isValidGroup(trimmedGroup)) {
            throw new IllegalValueException(Group.MESSAGE_GROUP_CONSTRAINTS);
        }
        return new Group(trimmedGroup.toLowerCase());
    }

    /**
     * Parses a {@code Optional<String> group} into a {@code Optional<Group>} if {@code group} is present.
     */
    public static Optional<Group> parseGroup(Optional<String> group) throws  IllegalValueException {
        requireNonNull(group);
        return group.isPresent() ? Optional.of(parseGroup(group.get())) : Optional.empty();
    }
```
###### \java\seedu\club\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String client} into a {@code Client}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code client} is invalid.
     */
    public static Client parseClient(String client) throws IllegalValueException {
        requireNonNull(client);
        String trimmedClient = client.trim();
        if (!Client.isValidClient(trimmedClient)) {
            throw new IllegalValueException(Client.MESSAGE_CLIENT_CONSTRAINTS);
        }
        return new Client(trimmedClient);
    }

    /**
     * Parses a {@code Optional<String> client} into a {@code Optional<Client>} if {@code client} is present.
     */
    public static Optional<Client> parseClient(Optional<String> client) throws IllegalValueException {
        requireNonNull(client);
        return client.isPresent() ? Optional.of(parseClient(client.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String subject} into a {@code Subject}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code subject} is invalid.
     */
    public static Subject parseSubject(String subject) {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        return new Subject(trimmedSubject);
    }

    /**
     * Parses a {@code Optional<String> subject} into a {@code Optional<Subject>} if {@code subject} is present.
     */
    public static Optional<Subject> parseSubject(Optional<String> subject) {
        requireNonNull(subject);
        return subject.isPresent() ? Optional.of(parseSubject(subject.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String body} into a {@code Body}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code body} is invalid.
     */
    public static Body parseBody(String body) {
        requireNonNull(body);
        String trimmedBody = body.trim();
        return new Body(trimmedBody);
    }

    /**
     * Parses a {@code Optional<String> body} into a {@code Optional<Body>} if {@code body} is present.
     */
    public static Optional<Body> parseBody(Optional<String> body) {
        requireNonNull(body);
        return body.isPresent() ? Optional.of(parseBody(body.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws IllegalValueException {
        logger.info(description);
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new IllegalValueException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code Optional<String> description} into a {@code Optional<Description>} if {@code description}
     * is present.
     */
    public static Optional<Description> parseDescription(Optional<String> description) throws IllegalValueException {
        requireNonNull(description);
        return description.isPresent() ? Optional.of(parseDescription(description.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code Optional<String> date} into a {@code Optional<Date>} if {@code date} is present.
     */
    public static Optional<Date> parseDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(parseDate(date.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String time} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code time} is invalid.
     */
    public static Time parseTime(String time) throws IllegalValueException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!Time.isValidTime(trimmedTime)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        return new Time(trimmedTime);
    }

    /**
     * Parses a {@code Optional<String> time} into a {@code Optional<Time>} if {@code time} is present.
     */
    public static Optional<Time> parseTime(Optional<String> time) throws IllegalValueException {
        requireNonNull(time);
        return time.isPresent() ? Optional.of(parseTime(time.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> status} into a {@code Optional<Status>} if {@code status} is present.
     */
    public static Optional<Status> parseStatus(Optional<String> status) throws IllegalValueException {
        requireNonNull(status);
        return status.isPresent() ? Optional.of(parseStatus(status.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String status} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code status} is invalid.
     */
    public static Status parseStatus(String status) throws IllegalValueException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        if (!Status.isValidStatus(trimmedStatus)) {
            throw new IllegalValueException(Status.MESSAGE_INVALID_STATUS);
        }
        return new Status(trimmedStatus);
    }

```
###### \java\seedu\club\model\ClubBook.java
``` java

    /**
     * Removes the Group {@code toRemove} from the Club Book. Every member who was once a part of {@code toRemove}
     * will be assigned the default group - "member".
     */
    public void deleteGroup(Group toRemove) throws GroupCannotBeRemovedException, GroupNotFoundException {
        checkIfGroupIsMemberOrExco(toRemove);
        checkIfGroupIsPresent(toRemove);
        deleteGroupFromClubBook(toRemove);
        logger.fine("Group " + toRemove + " has been removed.");
    }

    /**
     * Removes the Group {@code toRemove} from Club Connect.
     */
    private void deleteGroupFromClubBook(Group toRemove) {
        try {
            for (Member member : members) {
                deleteGroupFromMember(toRemove, member);
            }
        } catch (MemberNotFoundException mnfe) {
            throw new AssertionError("Impossible: original member is obtained from the club book.");
        }
    }

    /**
     * Checks if {@code toRemove} exists in Club Connect.
     * @throws GroupNotFoundException if {@code toRemove} is not found.
     */
    private void checkIfGroupIsPresent(Group toRemove) throws GroupNotFoundException {
        Boolean isPresent = false;

        for (Member member : members) {
            if (member.getGroup().equals(toRemove)) {
                isPresent = true;
            }
        }
        if (!isPresent) {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Checks if {@code toRemove} is "member".
     * @throws GroupCannotBeRemovedException if {@code toRemove} is "member".
     */
    private void checkIfGroupIsMemberOrExco(Group toRemove) throws GroupCannotBeRemovedException {
        Group groupMember = new Group("member");
        Group groupExco = new Group("exco");
        if (toRemove.equals(groupMember) || toRemove.equals(groupExco)) {
            throw new GroupCannotBeRemovedException();
        }
    }

    /**
     * Removes the Group {@code toRemove} from the {@code member} if the member's group matches the one to be removed.
     */
    private void deleteGroupFromMember(Group toRemove, Member member)
            throws MemberNotFoundException {
        if (!member.getGroup().toString().equalsIgnoreCase(toRemove.toString())) {
            return;
        }

        Group defaultGroup = new Group(Group.DEFAULT_GROUP);
        Member newMember = new Member(member.getName(), member.getPhone(), member.getEmail(), member.getMatricNumber(),
                defaultGroup, member.getTags(), member.getCredentials(), member.getProfilePhoto());

        try {
            updateMember(member, newMember);
        } catch (DuplicateMatricNumberException dme) {
            throw new AssertionError("Deleting a member's group only should not result in a duplicate. "
                    + "See member#equals(Object).");
        }
    }

    /**
     * Adds {@code Task toAdd} to the list of tasks.
     */
    public void addTaskToTaskList(Task taskToAdd) throws DuplicateTaskException {
        tasks.add(taskToAdd);
        logger.fine("Task added to task list.");
    }

    /**
     * Deletes {@code targetTask} from the list of tasks.
     * @throws TaskNotFoundException if the task doesn't exist.
     */
    public void deleteTask(Task targetTask) throws TaskNotFoundException {
        tasks.remove(targetTask);
        logger.fine("Task removed from task list.");
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks.setTasks(tasks);
    }
```
###### \java\seedu\club\model\ClubBook.java
``` java
    /**
     * Update {@code Task target}'s status with that of {@code Task editedMember}.
     * @throws DuplicateTaskException if updating the tasks's details causes the task to be equivalent to
     *                                  another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void updateTaskStatus(Task taskToEdit, Task editedTask) throws DuplicateTaskException,
            TaskNotFoundException {
        requireNonNull(editedTask);
        tasks.setTask(taskToEdit, editedTask);
        logger.fine("Task status updated to " + editedTask.getStatus().getStatus());
    }

    /**
     * Update {@code Task target}'s Assignee with that of {@code Task editedMember}.
     * @throws DuplicateTaskException if updating the tasks's details causes the task to be equivalent to
     *                                  another existing task in the list, ignoring the status.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void updateTaskAssignee(Task taskToEdit, Task editedTask) throws DuplicateTaskException {
        requireNonNull(editedTask);
        try {
            tasks.setTaskIgnoreStatus(taskToEdit, editedTask);
        } catch (DuplicateTaskException dte) {
            throw new DuplicateTaskException();
        }
        logger.fine("Task assignee updated to " + editedTask.getAssignee().getValue());
    }

    /**
     * Updates the task if there is a change in Matric Number of the target member.
     * @return number of tasks updated.
     * @throws DuplicateTaskException if there is already a task with similar attributes (regardless of status).
     */
    public int updateTaskHelper(Member target, Member editedMember) throws DuplicateTaskException {
        ObservableList<Task> taskObservableList = tasks.asObservableList();
        if (target.getMatricNumber().equals(editedMember.getMatricNumber())) {
            return ZERO;
        }
        int numberOfTasksUpdated = ZERO;
        numberOfTasksUpdated = updateTasks(target, editedMember, taskObservableList, numberOfTasksUpdated);
        logger.info("Updated " + numberOfTasksUpdated + "tasks in task list.");
        return numberOfTasksUpdated;

    }

    /**
     * Updates tasks by looping through the task list.
     * @throws DuplicateTaskException if the update causes a duplicate task.
     */
    private int updateTasks(Member target, Member editedMember, ObservableList<Task> taskObservableList,
                            int numberOfTasksUpdated) throws DuplicateTaskException {
        for (Task task : taskObservableList) {
            Task editedTask = null;
            String editedMemberMatricNumberString = editedMember.getMatricNumber().toString();
            String targetMemberMatricNumberString = target.getMatricNumber().toString();

            if (task.getAssignor().getValue().equalsIgnoreCase(targetMemberMatricNumberString)
                    && task.getAssignee().getValue().equalsIgnoreCase(targetMemberMatricNumberString)) {

                numberOfTasksUpdated = updateWhenSameAssignorAndAssignee(numberOfTasksUpdated, task,
                        editedMemberMatricNumberString);
            } else if (task.getAssignor().getValue().equalsIgnoreCase(targetMemberMatricNumberString)) {

                numberOfTasksUpdated = updateWhenSameAssignor(numberOfTasksUpdated, task,
                        editedMemberMatricNumberString);
            } else if (task.getAssignee().getValue().equalsIgnoreCase(targetMemberMatricNumberString)) {

                numberOfTasksUpdated = updateWhenSameAssignee(numberOfTasksUpdated, task,
                        editedMemberMatricNumberString);
            }
        }
        return numberOfTasksUpdated;
    }

    /**
     * Updates task which has Assignee same as targetMember's Matric Number
     * @throws DuplicateTaskException if the update results in a duplicate task.
     */
    private int updateWhenSameAssignee(int numberOfTasksUpdated, Task task,
                                       String editedMemberMatricNumberString) throws DuplicateTaskException {
        Task editedTask;
        Assignee newAssignee = new Assignee(editedMemberMatricNumberString);
        editedTask = new Task(task.getDescription(), task.getTime(), task.getDate(),
                task.getAssignor(), newAssignee, task.getStatus());
        tasks.setTaskIgnoreStatus(task, editedTask);
        numberOfTasksUpdated++;
        return numberOfTasksUpdated;
    }

    /**
     * Updates task which has Assignor same as targetMember's Matric Number
     * @throws DuplicateTaskException if the update results in a duplicate task.
     */
    private int updateWhenSameAssignor(int numberOfTasksUpdated, Task task,
                                       String editedMemberMatricNumberString) throws DuplicateTaskException {
        Task editedTask;
        Assignor newAssignor = new Assignor(editedMemberMatricNumberString);
        editedTask = new Task(task.getDescription(), task.getTime(), task.getDate(),
                newAssignor, task.getAssignee(), task.getStatus());
        tasks.setTaskIgnoreStatus(task, editedTask);
        numberOfTasksUpdated++;
        return numberOfTasksUpdated;
    }

    /**
     * Updates task which has Assignor and Assignee same as targetMember's Matric Number
     * @throws DuplicateTaskException if the update results in a duplicate task.
     */
    private int updateWhenSameAssignorAndAssignee(int numberOfTasksUpdated, Task task,
                                                  String editedMemberMatricNumberString) throws DuplicateTaskException {
        Task editedTask;
        Assignee newAssignee = new Assignee(editedMemberMatricNumberString);
        Assignor newAssignor = new Assignor(editedMemberMatricNumberString);

        editedTask = new Task(task.getDescription(), task.getTime(), task.getDate(),
                newAssignor, newAssignee, task.getStatus());
        tasks.setTaskIgnoreStatus(task, editedTask);
        numberOfTasksUpdated++;
        return numberOfTasksUpdated;
    }

    /**
     * Removes all tasks that have been assigned to {@code member}.
     */
    public int removeTasksOfMember(Member member) {

        int numberOfTasksRemoved = ZERO;
        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {
            Task task = it.next();
            if (task.getAssignee().getValue().equalsIgnoreCase(member.getMatricNumber().toString())) {
                it.remove();
                numberOfTasksRemoved++;
            }
        }
        logger.info("Removed " + numberOfTasksRemoved + "tasks from task list.");
        return numberOfTasksRemoved;
    }

    /**
     * Checks if a similar task has been assigned to the member by some other exco member.
     * The task's Assignor and Status are ignored in this comparison.
     * @throws TaskAlreadyAssignedException if there exists a task like so.
     */
    public void checkIfTaskIsAlreadyAssigned(Task toAdd) throws TaskAlreadyAssignedException {
        ObservableList<Task> taskObservableList = getTaskList();
        for (Task task : taskObservableList) {
            if (task.getDescription().getDescription().equalsIgnoreCase(toAdd.getDescription().getDescription())
                    && task.getDate().getDate().equalsIgnoreCase(toAdd.getDate().getDate())
                    && task.getTime().getTime().equalsIgnoreCase(toAdd.getTime().getTime())
                    && task.getAssignee().getValue().equalsIgnoreCase(toAdd.getAssignee().getValue())) {
                throw new TaskAlreadyAssignedException();
            }
        }
    }

    /**
     * Checks if a similar task exists.
     * @throws DuplicateTaskException if there exists a duplicate task.
     */
    public void checkIfDuplicateTaskExists(Task toAdd) throws DuplicateTaskException {
        ObservableList<Task> taskObservableList = getTaskList();
        for (Task task : taskObservableList) {
            if (task.getDescription().getDescription().equalsIgnoreCase(toAdd.getDescription().getDescription())
                    && task.getDate().getDate().equalsIgnoreCase(toAdd.getDate().getDate())
                    && task.getTime().getTime().equalsIgnoreCase(toAdd.getTime().getTime())
                    && task.getAssignor().getValue().equalsIgnoreCase(toAdd.getAssignor().getValue())
                    && task.getAssignee().getValue().equalsIgnoreCase(toAdd.getAssignee().getValue())) {
                throw new DuplicateTaskException();
            }
        }
    }
}
```
###### \java\seedu\club\model\email\Body.java
``` java

import static java.util.Objects.requireNonNull;

/**
 * Refers to the body of an email
 */
public class Body {

    public static final String EMPTY_BODY_STRING = "";
    public static final String TEST_BODY_STRING = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sit.";
    private String body;

    public Body(String body) {
        requireNonNull(body);
        this.body = body.trim();
    }

    @Override
    public int hashCode() {
        return body.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this        //short circuit if same object
                || (other instanceof Body   //handles nulls
                && this.body.equalsIgnoreCase(((Body) other).body));    //state check
    }

    @Override
    public String toString() {
        return this.body;
    }
}
```
###### \java\seedu\club\model\email\Client.java
``` java

import static java.util.Objects.requireNonNull;

/**
 * Refers to the email client chosen by the member to send the email.
 */
public class Client {

    public static final String VALID_CLIENT_GMAIL = "gmail";
    public static final String VALID_CLIENT_OUTLOOK = "outlook";
    public static final String MESSAGE_CLIENT_CONSTRAINTS = "Only GMail and Outlook email clients are supported";

    private String client;

    public Client(String client) {
        requireNonNull(client);
        this.client = client.trim();
    }

    @Override
    public String toString() {
        return this.client;
    }

    @Override
    public int hashCode() {
        return client.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this    //short circuit if same object
                || (other instanceof Client     //instanceof handles nulls
                && this.client.equalsIgnoreCase(((Client) other).client));  //state check
    }

    public static boolean isValidClient(String test) {
        return (test.equalsIgnoreCase(VALID_CLIENT_GMAIL) || test.equalsIgnoreCase(VALID_CLIENT_OUTLOOK));
    }
}
```
###### \java\seedu\club\model\email\Subject.java
``` java

import static java.util.Objects.requireNonNull;

/**
 * Refers to the subject of an email
 */
public class Subject {

    public static final String EMPTY_SUBJECT_STRING = "";
    public static final String TEST_SUBJECT_STRING = "Lorem Ipsum";
    private String subject;

    public Subject(String subject) {
        requireNonNull(subject);
        this.subject = subject.trim();
    }

    @Override
    public int hashCode() {
        return subject.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this    //short circuit if same object
                || (other instanceof Subject    //handles nulls
                && this.subject.equalsIgnoreCase(((Subject) other).subject));   //state check
    }

    @Override
    public String toString() {
        return this.subject;
    }
}
```
###### \java\seedu\club\model\group\exceptions\GroupCannotBeRemovedException.java
``` java
/**
 * Signals that the desired Group cannot be removed as it is a mandatory one.
 */
public class GroupCannotBeRemovedException extends Exception {
}
```
###### \java\seedu\club\model\group\exceptions\GroupNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the required Group
 */
public class GroupNotFoundException extends Exception {
}
```
###### \java\seedu\club\model\group\Group.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.AppUtil.checkArgument;

import org.apache.commons.lang3.text.WordUtils;

/**
 * Represents a member's Group in the club book
 * Guarantees: immutable; is valid as declared in {@link #isValidGroup(String)}
 */
public class Group {
    public static final String MESSAGE_GROUP_CONSTRAINTS =
            "Groups should only contain alphanumeric characters, and it should not be blank";

    /*
     * The first character of the group must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String GROUP_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}]*";

    public static final String DEFAULT_GROUP = "Member";
    public static final String GROUP_EXCO = "Exco";

    public final String groupName;

    /**
     * Constructs a {@code Group}.
     *
     * @param group A valid group.
     */
    public Group(String group) {
        requireNonNull(group);
        checkArgument(isValidGroup(group), MESSAGE_GROUP_CONSTRAINTS);
        this.groupName = WordUtils.capitalize(group);
    }

    /**
     * Returns true if given string is a valid member group.
     */
    public static Boolean isValidGroup(String test) {
        return test.matches(GROUP_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return groupName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this    // short circuit if same object
                || (other instanceof Group  // instanceof handles nulls
                && this.groupName.equalsIgnoreCase(((Group) other).groupName));   // state check
    }

    @Override
    public int hashCode() {
        return groupName.hashCode();
    }
}
```
###### \java\seedu\club\model\Model.java
``` java
    void deleteGroup(Group toRemove) throws GroupNotFoundException, GroupCannotBeRemovedException;

    String generateEmailRecipients(Group group, Tag tag) throws GroupNotFoundException, TagNotFoundException;

    void sendEmail(String recipients, Client client, Subject subject, Body body);

```
###### \java\seedu\club\model\ModelManager.java
``` java
    @Override
    public void deleteGroup(Group toRemove) throws GroupNotFoundException, GroupCannotBeRemovedException {
        requireNonNull(toRemove);
        clubBook.deleteGroup(toRemove);
        indicateClubBookChanged();
    }

```
###### \java\seedu\club\model\ModelManager.java
``` java
    @Override
    public String generateEmailRecipients(Group group, Tag tag) throws GroupNotFoundException, TagNotFoundException {
        if (group != null) {
            return generateGroupEmailRecipients(group);
        }
        return generateTagEmailRecipients(tag);
    }

    /**
     * Generates recipient list of all members part of {@code Tag toSendEmailTo}
     * @throws TagNotFoundException if {@code Tag toSendEmailTo} doesn't exist in the club book
     */
    private String generateTagEmailRecipients(Tag toSendEmailTo) throws TagNotFoundException {
        assert toSendEmailTo != null : "Null value of Tag";
        List<Member> members = new ArrayList<>(clubBook.getMemberList());

        List<String> emailRecipients = new ArrayList<>();
        checkIfTagExists(toSendEmailTo, members, emailRecipients);

        return String.join(",", emailRecipients);
    }

    /**
     * Checks if the {@code Tag toSendEmailTo} exists in Club Connect.
     * @throws TagNotFoundException if {@code toSendEmailTo} is not found.
     */
    private void checkIfTagExists(Tag toSendEmailTo, List<Member> members, List<String> emailRecipients)
            throws TagNotFoundException {
        Boolean tagFound = false;
        for (Member member : members) {
            Set<Tag> memberTags = member.getTags();
            if (memberTags.contains(toSendEmailTo)) {
                emailRecipients.add(member.getEmail().toString());
                tagFound = true;
            }
        }
        if (!tagFound) {
            throw new TagNotFoundException();
        }
    }

    /**
     * Generates recipient list of all members part of {@code Group toSendEmailTo}
     * @throws GroupNotFoundException if {@code Group toSendEmailTo} doesn't exist in the club book
     */
    private String generateGroupEmailRecipients(Group toSendEmailTo) throws GroupNotFoundException {
        assert toSendEmailTo != null : "Null value of Group";
        List<Member> members = new ArrayList<>(clubBook.getMemberList());

        List<String> emailRecipients = new ArrayList<>();
        checkIfGroupExists(toSendEmailTo, members, emailRecipients);
        return String.join(",", emailRecipients);
    }

    /**
     * Checks if the {@code Group toSendEmailTo} exists in Club Connect.
     * @throws GroupNotFoundException if {@code toSendEmailTo} is not found.
     */
    private void checkIfGroupExists(Group toSendEmailTo, List<Member> members, List<String> emailRecipients)
            throws GroupNotFoundException {
        Boolean groupFound = false;
        for (Member member : members) {
            if (member.getGroup().equals(toSendEmailTo)) {
                emailRecipients.add(member.getEmail().toString());
                groupFound = true;
            }
        }
        if (!groupFound) {
            throw new GroupNotFoundException();
        }
    }

    @Override
    public void sendEmail(String recipients, Client client, Subject subject, Body body) {
        raise(new SendEmailRequestEvent(recipients, subject, body, client));
    }

    @Override
    public void changeStatus(Task taskToEdit, Task editedTask) throws TaskNotFoundException,
            DuplicateTaskException, TaskStatusCannotBeEditedException {
        requireAllNonNull(taskToEdit, editedTask);
        String currentMember = getLoggedInMember().getMatricNumber().toString();
        checkIfStatusCanBeEdited(taskToEdit, currentMember);
        clubBook.updateTaskStatus(taskToEdit, editedTask);
        updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(getLoggedInMember()));
        indicateClubBookChanged();
    }

    /**
     * Checks if status can be edited based on the current member's matric number.
     * @throws TaskStatusCannotBeEditedException if the task status cannot be edited.
     */
    private void checkIfStatusCanBeEdited(Task taskToEdit, String currentMember)
            throws TaskStatusCannotBeEditedException {
        assert currentMember != null : "Null value of currentMember";
        if (!currentMember.equalsIgnoreCase(taskToEdit.getAssignor().getValue())
                && !currentMember.equalsIgnoreCase(taskToEdit.getAssignee().getValue())) {
            throw new TaskStatusCannotBeEditedException();
        }
    }

    @Override
    public void changeAssignee(Task taskToEdit, Task editedTask) throws DuplicateTaskException,
            MemberNotFoundException, TaskAlreadyAssignedException, TaskAssigneeUnchangedException {
        requireAllNonNull(taskToEdit, editedTask);
        MatricNumber newAssigneeMatricNumber = new MatricNumber(editedTask.getAssignee().getValue());
        checkIfMemberExists(newAssigneeMatricNumber);
        checkIfDuplicateTaskExists(editedTask);
        checkIfTaskIsAlreadyAssigned(editedTask);
        checkIfInputAssigneeIsSame(taskToEdit, editedTask);
        try {
            clubBook.updateTaskAssignee(taskToEdit, editedTask);
        } catch (DuplicateTaskException dte) {
            throw new AssertionError("Impossible. This check has already been made");
        }
        updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(getLoggedInMember()));
        indicateClubBookChanged();
    }

    private void checkIfInputAssigneeIsSame(Task taskToEdit, Task editedTask) throws TaskAssigneeUnchangedException {
        if (taskToEdit.getAssignee().equals(editedTask.getAssignee())) {
            throw new TaskAssigneeUnchangedException();
        }
    }

```
###### \java\seedu\club\model\ModelManager.java
``` java
    @Override
    public void addTaskToTaskList(Task toAdd) throws DuplicateTaskException {
        try {
            Assignor assignor = new Assignor(clubBook.getLoggedInMember().getMatricNumber().toString());
            Assignee assignee = new Assignee(clubBook.getLoggedInMember().getMatricNumber().toString());
            Status status = new Status(Status.NOT_STARTED_STATUS);
            toAdd.setAssignor(assignor);
            toAdd.setAssignee(assignee);
            toAdd.setStatus(status);
            clubBook.addTaskToTaskList(toAdd);
            updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(getLoggedInMember()));
            indicateClubBookChanged();
        } catch (DuplicateTaskException dte) {
            throw new DuplicateTaskException();
        }
    }

    @Override
    public void assignTask(Task toAdd, MatricNumber matricNumber) throws MemberNotFoundException,
            DuplicateTaskException, TaskAlreadyAssignedException {
        checkIfMemberExists(matricNumber);
        try {
            Assignor assignor = new Assignor(clubBook.getLoggedInMember().getMatricNumber().toString());
            Assignee assignee = new Assignee(matricNumber.toString());
            Status status = new Status(Status.NOT_STARTED_STATUS);
            toAdd.setAssignor(assignor);
            toAdd.setAssignee(assignee);
            toAdd.setStatus(status);
            checkIfDuplicateTaskExists(toAdd);
            checkIfTaskIsAlreadyAssigned(toAdd);
            try {
                clubBook.addTaskToTaskList(toAdd);
            } catch (DuplicateTaskException dte) {
                throw new AssertionError("Already caught before.");
            }
            updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(getLoggedInMember()));
            indicateClubBookChanged();
        } catch (DuplicateTaskException dte) {
            throw new DuplicateTaskException();
        } catch (TaskAlreadyAssignedException e) {
            throw new TaskAlreadyAssignedException();
        }
    }

    private void checkIfDuplicateTaskExists(Task toAdd) throws DuplicateTaskException {
        clubBook.checkIfDuplicateTaskExists(toAdd);
    }

    private void checkIfTaskIsAlreadyAssigned(Task toAdd) throws TaskAlreadyAssignedException {
        clubBook.checkIfTaskIsAlreadyAssigned(toAdd);
    }

    /**
     * Checks if the {@code MatricNumber matricNumber} maps to any member in Club Connect.
     * @throws MemberNotFoundException if {@code matricNumber} doesn't map to any member.
     */
    private void checkIfMemberExists(MatricNumber matricNumber) throws MemberNotFoundException {
        assert matricNumber != null : "Null value of matricNumber";
        boolean found = false;
        for (Member member : clubBook.getMemberList()) {
            if (member.getMatricNumber().equals(matricNumber)) {
                found = true;
            }
        }
        if (!found) {
            throw new MemberNotFoundException();
        }
    }

    @Override
    public void deleteTask(Task targetTask) throws TaskNotFoundException, TaskCannotBeDeletedException {
        Assignor assignor = targetTask.getAssignor();
        String currentMember = getLoggedInMember().getMatricNumber().toString();
        checkIfTaskCanBeDeleted(assignor, currentMember);
        clubBook.deleteTask(targetTask);
        indicateClubBookChanged();
    }

    /**
     * Checks if the {@code String currentMember} is the {@code assignor} of the task.
     * @throws TaskCannotBeDeletedException if the task cannot be deleted.
     */
    private void checkIfTaskCanBeDeleted(Assignor assignor, String currentMember)
            throws TaskCannotBeDeletedException {
        assert assignor != null : "Null value of Assignor";
        assert currentMember != null : "Null value of currentMember";
        if (!currentMember.equalsIgnoreCase(assignor.getValue())) {
            throw new TaskCannotBeDeletedException();
        }
    }

    @Override
    public void viewAllTasks() throws TasksAlreadyListedException {
        checkIfAllTasksAlreadyListed();
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateClubBookChanged();
    }

    private void checkIfAllTasksAlreadyListed() throws TasksAlreadyListedException {
        if (filteredTasks.getPredicate().equals(PREDICATE_SHOW_ALL_TASKS)) {
            throw new TasksAlreadyListedException(ViewAllTasksCommand.MESSAGE_ALREADY_LISTED);
        }
    }

    @Override
    public void viewMyTasks() throws TasksAlreadyListedException {
        checkIfTasksAreAlreadyListed();
        updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(getLoggedInMember()));
    }

    private void checkIfTasksAreAlreadyListed() throws TasksAlreadyListedException {
        if (filteredTasks.getPredicate().equals(new TaskIsRelatedToMemberPredicate(getLoggedInMember()))) {
            throw new TasksAlreadyListedException(ViewMyTasksCommand.MESSAGE_ALREADY_LISTED);
        }
    }

```
###### \java\seedu\club\model\task\Assignee.java
``` java

import static java.util.Objects.requireNonNull;

/**
 * Refers to the assignee of a Task
 */
public class Assignee {

    private String assignee;

    public Assignee(String assignee) {
        requireNonNull(assignee);
        this.assignee = assignee;
    }

    public String getValue() {
        return assignee;
    }

    @Override
    public int hashCode() {
        return assignee.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this        //short circuit if same object
                || (other instanceof Assignee   //handles nulls
                && this.assignee.equalsIgnoreCase(((Assignee) other).assignee));    //state check
    }
}
```
###### \java\seedu\club\model\task\Assignor.java
``` java

import static java.util.Objects.requireNonNull;

/**
 * Refers to the assignor of a Task
 */
public class Assignor {

    private String assignor;

    public Assignor(String assignor) {
        requireNonNull(assignor);
        this.assignor = assignor;
    }

    public String getValue() {
        return assignor;
    }

    @Override
    public int hashCode() {
        return assignor.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this        //short circuit if same object
                || (other instanceof Assignor   //handles nulls
                && this.assignor.equalsIgnoreCase(((Assignor) other).assignor));    //state check
    }
}
```
###### \java\seedu\club\model\task\Date.java
``` java

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.AppUtil.checkArgument;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Refers to the Due Date of a Task
 */
public class Date {

    public static final String DATE_SPLITTER = "[///./-]";
    public static final String DATE_SEPARATOR = "/";
    public static final String MESSAGE_DATE_CONSTRAINTS = "Date should be a string separated by '.', '/', or "
            + "'-' in the format DD-MM-YYYY\nClub Connect detects invalid leap days.\nThe valid range of Year is "
            + "1900-2099.";

    /**
     * Adapted from {@linktourl http://www.mkyong.com/regular-expressions/how-to-validate-date-with-regular-expression/}
     */
    public static final String DATE_VALIDATION_REGEX = "(0[1-9]|[1-9]|1[0-9]|2[0-9]|3[01])[///./-]"
            + "(0[1-9]|1[0-2]|[1-9])[///./-](19|20)[0-9][0-9]";

    public final String date;

    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        this.date = date;
    }

    /**
     * Returns true if the given string {@code date} follows the appropriate format and is a valid date.
     * Adapted from {@linktourl https://stackoverflow.com/questions/32204953/validating-java-8-dates?utm_medium=organic
     * &utm_source=* google_rich_qa&utm_campaign=google_rich_qa}
     */
    public static boolean isValidDate(String date) {
        if (!date.matches(DATE_VALIDATION_REGEX)) {
            return false;
        }
        String[] dateFields = date.split(DATE_SPLITTER);

        final int day = Integer.parseInt(dateFields[0]);
        final int month = Integer.parseInt(dateFields[1]);
        final int year = Integer.parseInt(dateFields[2]);

        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * Returns the date in a standard '/'-separated, 'DD-MM-YYYY' format.
     */
    public String standardizeDate(String date) {
        String[] dateFields = date.split(DATE_SPLITTER);
        StringBuilder sb = new StringBuilder();

        final int day = Integer.parseInt(dateFields[0]);
        if (day < 10) {
            sb.append(0);
        }
        sb.append(day);
        sb.append(DATE_SEPARATOR);
        final int month = Integer.parseInt(dateFields[1]);
        if (month < 10) {
            sb.append(0);
        }
        sb.append(month);
        sb.append(DATE_SEPARATOR);
        final int year = Integer.parseInt(dateFields[2]);
        sb.append(year);

        return sb.toString();
    }

    public String getDate() {
        return standardizeDate(date);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this        //short circuit if same object
                || (other instanceof Date   //handles nulls
                && this.date.equalsIgnoreCase(((Date) other).date));    //state check
    }
}
```
###### \java\seedu\club\model\task\Description.java
``` java

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.AppUtil.checkArgument;

/**
 * Refers to the Description of a Task
 */
public class Description {

    public static final String DESCRIPTION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS = "Description should a non-empty alphanumeric string.";
    public final String description;

    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_DESCRIPTION_CONSTRAINTS);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns true if the given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this        //short circuit if same object
                || (other instanceof Description   //handles nulls
                && this.description.equalsIgnoreCase(((Description) other).description));    //state check
    }
}
```
###### \java\seedu\club\model\task\Status.java
``` java
import static java.util.Objects.requireNonNull;

/**
 * Represents the status of a Task
 */
public class Status {

    public static final String NOT_STARTED_STATUS = "Yet To Begin";
    public static final String IN_PROGRESS_STATUS = "In Progress";
    public static final String COMPLETED_STATUS = "Completed";
    public static final String MESSAGE_INVALID_STATUS = "Invalid task status value entered.";

    private String status;

    public Status(String status) {
        requireNonNull(status);
        this.status = status;
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this        //short circuit if same object
                || (other instanceof Status   //handles nulls
                && this.status.equalsIgnoreCase(((Status) other).status));    //state check
    }

    /**
     * Checks if the given status {@code test} is a valid status
     */
    public static boolean isValidStatus(String test) {
        return test.equalsIgnoreCase(NOT_STARTED_STATUS)
                || test.equalsIgnoreCase(IN_PROGRESS_STATUS)
                || test.equalsIgnoreCase(COMPLETED_STATUS);
    }
}
```
###### \java\seedu\club\model\task\Task.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Task assigned to, or created by a Member
 */
public class Task {

    private Description description;
    private Assignor assignor;
    private Assignee assignee;
    private Time time;
    private Date date;
    private Status status;

    public Task(Description description, Time time, Date date) {
        requireAllNonNull(description, time, date);
        this.description = description;
        this.time = time;
        this.date = date;
        this.assignor = new Assignor("");
        this.assignee = new Assignee("");
        this.status = new Status(Status.NOT_STARTED_STATUS);
    }

    public Task(Task other) {
        requireNonNull(other);
        this.description = other.description;
        this.time = other.time;
        this.date = other.date;
        this.assignor = other.assignor;
        this.assignee = other.assignee;
        this.status = other.status;
    }

    public Task(Description description, Time time, Date date, Assignor assignor, Assignee assignee,
                Status status) {
        requireAllNonNull(description, time, date, assignor, assignee);
        this.description = description;
        this.time = time;
        this.date = date;
        this.assignor = assignor;
        this.assignee = assignee;
        this.status = status;
    }

    public Description getDescription() {
        return description;
    }

    public Assignor getAssignor() {
        return assignor;
    }

    public Time getTime() {
        return time;
    }

    public Date getDate() {
        return date;
    }

    public void setAssignor(Assignor assignor) {
        this.assignor = assignor;
    }

    public Assignee getAssignee() {
        return assignee;
    }

    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isTaskCompleted() {
        return status.getStatus().equalsIgnoreCase(Status.COMPLETED_STATUS);
    }

    public boolean isTaskInProgress() {
        return status.getStatus().equalsIgnoreCase(Status.IN_PROGRESS_STATUS);
    }

    public boolean hasTaskNotBegun() {
        return status.getStatus().equalsIgnoreCase(Status.NOT_STARTED_STATUS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Task
                && this.getDescription().equals(((Task) other).getDescription())
                && this.getDate().equals(((Task) other).getDate())
                && this.getTime().equals(((Task) other).getTime())
                && this.getAssignor().equals(((Task) other).getAssignor())
                && this.getAssignee().equals(((Task) other).getAssignee())
                && this.getStatus().equals(((Task) other).getStatus()));
    }
}
```
###### \java\seedu\club\model\task\TaskIsRelatedToMemberPredicate.java
``` java
import java.util.function.Predicate;

import seedu.club.model.member.Member;

/**
 * Tests that a {@code task} is related to (Assignor or Assignee) the {@code member}.
 */
public class TaskIsRelatedToMemberPredicate implements Predicate<Task> {

    private final Member member;

    public TaskIsRelatedToMemberPredicate(Member member) {
        this.member = member;
    }

    @Override
    public boolean test(Task task) {
        return member.getMatricNumber().toString().equalsIgnoreCase(task.getAssignor().getValue())
                || member.getMatricNumber().toString().equalsIgnoreCase(task.getAssignee().getValue());
    }

    public Member getMember() {
        return member;
    }

    @Override
    public boolean equals(Object other) {
        return (other == this)  // short circuit if same object
                || (other instanceof TaskIsRelatedToMemberPredicate     // handles nulls
                && this.member.equals(((TaskIsRelatedToMemberPredicate) other).getMember()));   // state check
    }
}
```
###### \java\seedu\club\model\task\Time.java
``` java

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.AppUtil.checkArgument;

/**
 * Refers to the time (deadline) the Task should be completed by.
 */
public class Time {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Time must be in 24-Hour format (HH:MM) and can be separated"
            + " by ':'";
    /**
     * Adapted from {@linktourl http://www.mkyong.com/regular-expressions/how-to-validate-time-in-24-hours-format-
     * with-regular-expression/}
     */
    public static final String TIME_VALIDATION_REGEX = "(0[0-9]|1[0-9]|2[0-3]):"
            + "(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])";
    public static final String TIME_SPLITTER = ":";

    public final String time;

    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);
        this.time = time;
    }

    /**
     * Returns true if the given string {@code test} is a valid test.
     */
    public static boolean isValidTime(String test) {
        if (!test.matches(TIME_VALIDATION_REGEX)) {
            return false;
        }
        String[] timeFields = test.split(TIME_SPLITTER);

        final int hour = Integer.parseInt(timeFields[0]);
        final int minute = Integer.parseInt(timeFields[1]);
        return true;
    }

    /**
     * Returns the time in a standard ':'-separated, 'HH:MM' format.
     */
    public String standardizeTime(String time) {
        StringBuilder sb = new StringBuilder();
        String[] timeFields = time.split(TIME_SPLITTER);

        final int hour = Integer.parseInt(timeFields[0]);
        sb.append(hour);
        sb.append(TIME_SPLITTER);
        final int minute = Integer.parseInt(timeFields[1]);
        if (minute < 10) {
            sb.append(0);
        }
        sb.append(minute);

        return sb.toString();
    }

    public String getTime() {
        return standardizeTime(time);
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this        //short circuit if same object
                || (other instanceof Time   //handles nulls
                && this.time.equalsIgnoreCase(((Time) other).time));    //state check
    }
}
```
###### \java\seedu\club\model\task\UniqueTaskList.java
``` java

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.club.commons.util.CollectionUtil;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskNotFoundException;

/**
 *
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs an empty UniqueTaskList.
     */
    public UniqueTaskList() {}

    /**
     * Constructs a UniqueTaskList using given tasks.
     * Enforces no nulls
     */
    public UniqueTaskList(Set<Task> tasks) {
        requireAllNonNull(tasks);
        internalList.addAll(tasks);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all tasks in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Task> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Tasks in this list with those in the argument order list.
     */
    public void setTasks(Set<Task> tasks) {
        requireAllNonNull(tasks);
        internalList.setAll(tasks);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     *
     * @throws DuplicateTaskException if the replacement is equivalent to an existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void setTask(Task target, Task editedTask) throws DuplicateTaskException, TaskNotFoundException {
        requireNonNull(editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        if (internalList.contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        internalList.set(index, editedTask);
    }

    public void setTaskIgnoreStatus(Task target, Task editedTask) throws DuplicateTaskException {
        requireNonNull(editedTask);

        int index = internalList.indexOf(target);

        for (Task task : internalList) {
            if (task.getDescription().getDescription().equalsIgnoreCase(editedTask.getDescription().getDescription())
                    && task.getTime().getTime().equalsIgnoreCase(editedTask.getTime().getTime())
                    && task.getDate().getDate().equalsIgnoreCase(editedTask.getDate().getDate())
                    && task.getAssignor().getValue().equalsIgnoreCase(editedTask.getAssignor().getValue())
                    && task.getAssignee().getValue().equalsIgnoreCase(editedTask.getAssignee().getValue())) {
                throw new DuplicateTaskException();
            }
        }

        internalList.set(index, editedTask);

    }

    /**
     * Ensures every task in the argument list exists in this object.
     */
    public void mergeFrom(UniqueTaskList from) {
        final Set<Task> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(task -> !alreadyInside.contains(task))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Task as the given argument.
     */
    public boolean contains(Task toCheck) {
        requireNonNull(toCheck);
        for (Task task : internalList) {
            if (task.equals(toCheck)) {

            }
        }
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Task to the list.
     *
     * @throws DuplicateTaskException if the Task to add is a duplicate of an existing Task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes {@code Task toRemove} from the list if it exists.
     */
    public void remove(Task toRemove) throws TaskNotFoundException {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new TaskNotFoundException();
        }
        internalList.remove(toRemove);
    }

    @Override
    public Iterator<Task> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        sortList();
        return FXCollections.unmodifiableObservableList(internalList);
    }



    /**
     * Sorts the list of tasks according to alphabetical order of description.
     */
    private void sortList() {
        internalList.sort(new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                if (task1.getDate().getDate().compareTo(task2.getDate().getDate()) == 0) {
                    if (task1.getTime().getTime().compareTo(task2.getTime().getTime()) == 0) {
                        return task1.getDescription().getDescription()
                                .compareTo(task2.getDescription().getDescription());
                    }
                    return task1.getTime().getTime().compareTo(task2.getTime().getTime());
                }
                return task1.getDate().getDate().compareTo(task2.getDate().getDate());
            }
        });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(((UniqueTaskList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueTaskList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }
}
```
###### \java\seedu\club\storage\XmlAdaptedTask.java
``` java
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.task.Assignee;
import seedu.club.model.task.Assignor;
import seedu.club.model.task.Date;
import seedu.club.model.task.Description;
import seedu.club.model.task.Status;
import seedu.club.model.task.Task;
import seedu.club.model.task.Time;

/**
 * JAXB-friendly version of Task
 */
public class XmlAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    @XmlElement
    private String description;
    @XmlElement
    private String time;
    @XmlElement
    private String date;
    @XmlElement
    private String assignor;
    @XmlElement
    private String assignee;
    @XmlElement
    private String status;

    /**
     * Constructs an empty XmlAdaptedTask
     */
    public XmlAdaptedTask() {}

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String description, String time, String date, String assignor, String assignee,
                          String status) {
        this.description = description;
        this.time = time;
        this.date = date;
        this.assignor = assignor;
        this.assignee = assignee;
        this.status = status;
    }

    /**
     * Overloaded Constructor for constructing an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String description, String time, String date) {
        this.description = description;
        this.time = time;
        this.date = date;
        this.assignor = "";
        this.assignee = "";
        this.status = "";
    }

    /**
     * Converts a given Task into XmlAdaptedTask for JAXB use.
     */
    public XmlAdaptedTask(Task source) {
        description = source.getDescription().getDescription();
        time = source.getTime().getTime();
        date = source.getDate().getDate();
        assignor = source.getAssignor().getValue();
        assignee = source.getAssignee().getValue();
        status = source.getStatus().getStatus();
    }

    /**
     * Converts the jaxb-friendly adaptedTask object into the model's Task object.
     * @throws IllegalValueException if any values do not follow the valid format.
     */
    public Task toModelType() throws IllegalValueException {
        if (this.description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }

        final Description description = new Description(this.description);

        if (this.time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(this.time)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        final Time time = new Time(this.time);

        if (this.date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(this.date)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date date = new Date(this.date);

        if (this.assignor == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Assignor.class.getSimpleName()));
        }
        final Assignor assignor = new Assignor(this.assignor);

        if (this.assignee == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Assignee.class.getSimpleName()));
        }
        final Assignee assignee = new Assignee(this.assignee);

        if (this.status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Status.class.getSimpleName()));
        }
        final Status status = new Status(this.status);

        return new Task(description, time, date, assignor, assignee, status);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTask)) {
            return false;
        }

        XmlAdaptedTask otherTask = (XmlAdaptedTask) other;
        return Objects.equals(description, otherTask.description)
                && Objects.equals(time, otherTask.time)
                && Objects.equals(date, otherTask.date)
                && Objects.equals(assignor, otherTask.assignor)
                && Objects.equals(assignee, otherTask.assignee);
    }
}
```
###### \java\seedu\club\ui\MemberCard.java
``` java
    /**
     * Creates the labels for tags by randomly generating a color from `TAG_COLORS`
     */
    private void createTags(Member member) {
        member.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(returnColor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     * Returns a color chosen uniformly at random from TAG_COLORS
     */
    private String returnColor(String tag) {
        return TAG_COLORS[Math.abs(tag.hashCode()) % TAG_COLORS.length];
    }
```
###### \java\seedu\club\ui\MemberOverviewPanel.java
``` java
    @Subscribe
    private void handleSendingEmailEvent(SendEmailRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Sending email via "
                + event.getClient().toString()));
        callClient(event.getClient().toString(), event.getRecipients(), event.getSubject().toString(),
                event.getBody().toString());
    }

```
###### \java\seedu\club\ui\MemberOverviewPanel.java
``` java
    /**
     * Creates the labels for tags by randomly generating a color from `TAG_COLORS`
     */
    private void createTags(Member member) {
        tags.getChildren().clear();
        member.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(returnColor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
        tags.setAlignment(Pos.CENTER);
    }

    /**
     * Returns a color chosen uniformly at random from TAG_COLORS
     */
    private String returnColor(String tag) {
        return TAG_COLORS[Math.abs(tag.hashCode()) % TAG_COLORS.length];
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<ModifiedTaskCard> {

        @Override
        protected void updateItem(ModifiedTaskCard task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
                return;
            }

            this.getStylesheets().clear();
            logger.info("Status: " + task.task.getStatus().getStatus());
            if (task.isTaskYetToBegin()) {
                logger.info("In here");
                this.getStylesheets().add(TASK_YET_TO_BEGIN_CSS);
            } else if (task.isTaskInProgress()) {
                this.getStylesheets().add(TASK_IN_PROGRESS_CSS);
            } else if (task.isTaskCompleted()) {
                this.getStylesheets().add(TASK_COMPLETED_CSS);
            }
            setGraphic(task.getRoot());
        }
    }
```
###### \java\seedu\club\ui\TaskCard.java
``` java

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.club.model.task.Task;

/**
 * A UI component that displays information of an {@code Order}.
 */
public class TaskCard extends UiPart<Region> {
    private static final String FXML = "TaskListCard.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;

    @FXML
    private Label description;

    @FXML
    private Label id;

    @FXML
    private Label time;

    @FXML
    private Label date;

    @FXML
    private Label assignor;

    @FXML
    private Label assignee;

    @FXML
    private Label status;

    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        description.setText(task.getDescription().getDescription());
        date.setText("Due Date: " + task.getDate().getDate());
        time.setText("Time: " + task.getTime().getTime());
        assignor.setText("Assigned by: " + task.getAssignor().getValue());
        assignee.setText("Assigned to: " + task.getAssignee().getValue());
        status.setText("Status: " + task.getStatus().getStatus());
    }

    public boolean isTaskYetToBegin() {
        return task.hasTaskNotBegun();
    }

    public boolean isTaskInProgress() {
        return task.isTaskInProgress();
    }

    public boolean isTaskCompleted() {
        return task.isTaskCompleted();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
```
###### \java\seedu\club\ui\TaskListPanel.java
``` java
import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.club.commons.core.LogsCenter;
import seedu.club.model.task.Task;

/**
 * Panel containing tasks to be completed by the members.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private static final String DIRECTORY_PATH = "view/";
    private static final String TASK_YET_TO_BEGIN_CSS = DIRECTORY_PATH + "LightTaskYetToBegin.css";
    private static final String TASK_IN_PROGRESS_CSS = DIRECTORY_PATH + "LightTaskInProgress.css";
    private static final String TASK_COMPLETED_CSS = DIRECTORY_PATH + "LightTaskCompleted.css";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    @FXML
    private ListView<TaskCard> taskListView;

    public TaskListPanel(ObservableList<Task> taskList) {
        super(FXML);
        setConnections(taskList);
        registerAsAnEventHandler(this);
    }

    public void setConnections(ObservableList<Task> taskList) {
        setMemberListView(taskList);
    }

    public void setMemberListView(ObservableList<Task> taskList) {
        ObservableList<TaskCard> mappedList = EasyBind.map(
                taskList, (task) -> new TaskCard(task, taskList.indexOf(task) + 1));
        taskListView.setItems(mappedList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

    /**
     * Scrolls to the {@code TaskCard} at {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<TaskCard> {

        @Override
        protected void updateItem(TaskCard task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
                return;
            }

            this.getStylesheets().clear();
            logger.info("Status: " + task.task.getStatus().getStatus());
            if (task.isTaskYetToBegin()) {
                logger.info("In here");
                this.getStylesheets().add(TASK_YET_TO_BEGIN_CSS);
            } else if (task.isTaskInProgress()) {
                this.getStylesheets().add(TASK_IN_PROGRESS_CSS);
            } else if (task.isTaskCompleted()) {
                this.getStylesheets().add(TASK_COMPLETED_CSS);
            }
            setGraphic(task.getRoot());
        }
    }
}
```
###### \resources\view\DarkTaskCompleted.css
``` css
.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-background-radius: 0 0 0 0;
    -fx-border-radius: 0 0 0 0;
    -fx-padding: 10px;
    -fx-background-insets:5px, 5px;
    -fx-background-color: transparent, -fx-background;
}

.list-cell:filled:even {
    -fx-background-color: derive(#006400, 0%);
}

.list-cell:filled:odd {
    -fx-background-color: derive(#006400, 0%);
}

.list-cell:filled:selected {
    -fx-background-color: derive(#006400, +40%);
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: derive(#006400, +80%);
    -fx-border-width: 3;
    -fx-border-radius: 0 0 0 0;
}

.list-cell .label {
    -fx-text-fill: white;
}
```
###### \resources\view\DarkTaskInProgress.css
``` css
.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-background-radius: 0 0 0 0;
    -fx-border-radius: 0 0 0 0;
    -fx-padding: 10px;
    -fx-background-insets: 5px, 5px;
    -fx-background-color: transparent, -fx-background;
}

.list-cell:filled:even {
    -fx-background-color: derive(#ff8c00, 0%);
}

.list-cell:filled:odd {
    -fx-background-color: derive(#ff8c00, 0%);
}

.list-cell:filled:selected {
    -fx-background-color: derive(#ff8c00, +40%);
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: derive(#ff8c00, +80%);
    -fx-border-width: 3;
    -fx-border-radius: 0 0 0 0;
}

.list-cell .label {
    -fx-text-fill: white;
}
```
###### \resources\view\LightTaskCompleted.css
``` css
.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-background-radius: 0 0 0 0;
    -fx-border-radius: 0 0 0 0;
    -fx-padding: 0px;
    -fx-background-color: cccccc;
}

.list-cell:filled:even {
    -fx-background-color: derive(#006400, 75%);
}

.list-cell:filled:odd {
    -fx-background-color: derive(#006400, 75%);
}

.list-cell:filled:selected {
    -fx-background-color: derive(#006400, +40%);
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: derive(#006400, +80%);
    -fx-border-width: 3;
    -fx-border-radius: 0 0 0 0;
}

.list-cell .label {
    -fx-text-fill: black;
}
```
###### \resources\view\LightTaskInProgress.css
``` css
.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-background-radius: 0 0 0 0;
    -fx-border-radius: 0 0 0 0;
    -fx-padding: 0px;
    -fx-background-color: cccccc;
}

.list-cell:filled:even {
    -fx-background-color: derive(#ff9711, 30%);
}

.list-cell:filled:odd {
    -fx-background-color: derive(#ff9711, 30%);
}

.list-cell:filled:selected {
    -fx-background-color: derive(#ffae00, +40%);
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: derive(#ffae00, +80%);
    -fx-border-width: 3;
    -fx-border-radius: 0 0 0 0;
}

.list-cell .label {
    -fx-text-fill: black;
}
```
###### \resources\view\TaskListCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="cardPane" fx:id="cardPane" maxHeight="1.7976931348623157E308" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
<GridPane maxHeight="1.7976931348623157E308" HBox.hgrow="ALWAYS">
    <columnConstraints>
        <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
    </columnConstraints>
    <VBox alignment="CENTER_LEFT" maxHeight="1.7976931348623157E308" minHeight="105" GridPane.columnIndex="0">
        <padding>
            <Insets bottom="5" left="15" right="5" top="5" />
        </padding>
        <HBox alignment="CENTER_LEFT" maxHeight="1.7976931348623157E308">
            <Label fx:id="id" styleClass="cell_big_label">
                <minWidth>
                    <!-- Ensures that the label text is never truncated -->
                    <Region fx:constant="USE_PREF_SIZE" />
                </minWidth>
            </Label>
            <Label fx:id="description" maxHeight="1.7976931348623157E308" wrapText="true" styleClass="cell_big_label" text="\$first" />
        </HBox>
        <Label fx:id="date" styleClass="cell_small_label" text="\$date" />
        <Label fx:id="time" styleClass="cell_small_label" text="\$time" />
        <Label fx:id="assignor" styleClass="cell_small_label" text="\$assignor" />
        <Label fx:id="assignee" styleClass="cell_small_label" text="\$assignee" />
        <Label fx:id="status" styleClass="cell_small_label" text="\$status" />
    </VBox>
    <rowConstraints>
        <RowConstraints />
    </rowConstraints>
</GridPane>
</HBox>
```
###### \resources\view\TaskListPanel.fxml
``` fxml
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<?import javafx.scene.text.TextFlow?>
<?import javafx.scene.text.Text?>
<?import javafx.geometry.Insets?>
<VBox xmlns="http://javafx.com/javafx/8" maxHeight="1.7976931348623157E308" xmlns:fx="http://javafx.com/fxml/1">
```
###### \resources\view\TaskListPanel.fxml
``` fxml
    <ListView fx:id="taskListView" maxHeight="1.7976931348623157E308" VBox.vgrow="ALWAYS" />
</VBox>
```
