# yash-chowdhary
###### \java\seedu\club\commons\events\ui\SendEmailRequestEvent.java
``` java
/**
 *
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
```
###### \java\seedu\club\logic\commands\DeleteTaskCommand.java
``` java

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
```
###### \java\seedu\club\logic\commands\EmailCommand.java
``` java

import static seedu.club.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.club.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;

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
    public static final String COMMAND_FORMAT = "email [g/ ] [t/ ] c/ [s/ ] [b/ ]";

    public static final String COMMAND_USAGE = COMMAND_WORD + ": Sends an email to the desired recipients(s) "
            + "in EITHER a particular group OR a particular tag of the club book. "
            + "Parameters: " + " "
            + PREFIX_GROUP + "GROUP" + " [OR] "
            + PREFIX_TAG + "TAG" + " "
            + PREFIX_CLIENT + "EMAIL CLIENT" + " "
            + PREFIX_SUBJECT + "SUBJECT" + " "
            + PREFIX_BODY + "BODY\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP + "logistics "
            + PREFIX_CLIENT + "gmail "
            + PREFIX_SUBJECT + "Test Subject "
            + PREFIX_BODY + "Test Body ";

    public static final String EMAIL_CLIENT_OPENED = "Email client opened!";
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
        try {
            String emailRecipients = model.generateEmailRecipients(group, tag);
            model.sendEmail(emailRecipients, client, subject, body);
            return new CommandResult(EMAIL_CLIENT_OPENED);
        } catch (GroupNotFoundException gnfe) {
            throw new CommandException(RemoveGroupCommand.MESSAGE_NON_EXISTENT_GROUP);
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
###### \java\seedu\club\logic\commands\RemoveGroupCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;

/**
 * Removes a group from the Club Book
 */
public class RemoveGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removegroup";
    public static final String COMMAND_FORMAT = "removegroup g/ ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a Group from the Club Book. "
            + "Parameters: "
            + PREFIX_GROUP + "GROUP";

    public static final String MESSAGE_SUCCESS = "Group deleted from Club Book: %1$s";
    public static final String MESSAGE_NON_EXISTENT_GROUP = "This group does not exist in the Club Book";
    public static final String MESSAGE_MANDATORY_GROUP = "This group cannot be deleted as it is a mandatory group.";

    private final Group toRemove;

    /**
     * Creates an AddCommand to add the specified {@code member}
     */
    public RemoveGroupCommand(Group group) {
        requireNonNull(group);
        toRemove = group;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.removeGroup(toRemove);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toRemove));
        } catch (GroupNotFoundException gnfe) {
            throw new CommandException(MESSAGE_NON_EXISTENT_GROUP);
        } catch (GroupCannotBeRemovedException gcbre) {
            throw new CommandException(MESSAGE_MANDATORY_GROUP);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveGroupCommand // instanceof handles nulls
                && toRemove.equals(((RemoveGroupCommand) other).toRemove));
    }
}
```
###### \java\seedu\club\logic\commands\ViewMyTasksCommand.java
``` java
import static java.util.Objects.requireNonNull;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.task.exceptions.TasksAlreadyListedException;

/**
 * Lists all tasks of the currently logged-in member in the club book.
 */
public class ViewMyTasksCommand extends Command {
    public static final String  COMMAND_WORD = "viewmytasks";

    public static final String MESSAGE_SUCCESS = "Listed all your tasks.";
    public static final String MESSAGE_ALREADY_LISTED = "All your tasks are already listed.";

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
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

import java.util.stream.Stream;

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
###### \java\seedu\club\logic\parser\CommandList.java
``` java
import java.util.ArrayList;
import java.util.Collections;

import seedu.club.logic.commands.AddCommand;
import seedu.club.logic.commands.AddPollCommand;
import seedu.club.logic.commands.AddTaskCommand;
import seedu.club.logic.commands.AssignTaskCommand;
import seedu.club.logic.commands.ChangeProfilePhotoCommand;
import seedu.club.logic.commands.ClearCommand;
import seedu.club.logic.commands.CompressCommand;
import seedu.club.logic.commands.DecompressCommand;
import seedu.club.logic.commands.DeleteCommand;
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
import seedu.club.logic.commands.ListCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.LogOutCommand;
import seedu.club.logic.commands.RedoCommand;
import seedu.club.logic.commands.RemoveGroupCommand;
import seedu.club.logic.commands.SelectCommand;
import seedu.club.logic.commands.ShowResultsCommand;
import seedu.club.logic.commands.UndoCommand;
import seedu.club.logic.commands.ViewAllTasksCommand;
import seedu.club.logic.commands.ViewMyTasksCommand;

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
        commandList.add(RemoveGroupCommand.COMMAND_FORMAT);
        commandList.add(SelectCommand.COMMAND_FORMAT);
        commandList.add(UndoCommand.COMMAND_WORD);
        commandList.add(AddTaskCommand.COMMAND_FORMAT);
        commandList.add(DeleteTaskCommand.COMMAND_FORMAT);
        commandList.add(ShowResultsCommand.COMMAND_WORD);
        commandList.add(HideResultsCommand.COMMAND_WORD);
        commandList.add(ViewAllTasksCommand.COMMAND_WORD);
        commandList.add(ViewMyTasksCommand.COMMAND_WORD);
        commandList.add(AssignTaskCommand.COMMAND_FORMAT);
        commandList.add(AddPollCommand.COMMAND_FORMAT);
        commandList.add(DeletePollCommand.COMMAND_FORMAT);
        commandList.add(ExportCommand.COMMAND_FORMAT);
        commandList.add(LogOutCommand.COMMAND_WORD);

        Collections.sort(commandList);
        return commandList;
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
        return new Group(trimmedGroup);
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

```
###### \java\seedu\club\logic\parser\RemoveGroupCommandParser.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.stream.Stream;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.RemoveGroupCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.group.Group;

/**
 * Parses input arguments and creates a new RemoveGroupCommand object
 */
public class RemoveGroupCommandParser implements Parser<RemoveGroupCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemoveGroupCommand
     * and returns an RemoveGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_GROUP)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveGroupCommand.MESSAGE_USAGE));
        }

        try {
            Group group = ParserUtil.parseGroup(argumentMultimap.getValue(PREFIX_GROUP).get());

            return new RemoveGroupCommand(group);
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
###### \java\seedu\club\model\ClubBook.java
``` java
    /**
     * Removes the Group {@code toRemove} from the Club Book. Every member who was once a part of {@code toRemove}
     * will be assigned the default group - "member".
     */
    public void removeGroup(Group toRemove) throws GroupCannotBeRemovedException, GroupNotFoundException {
        Group notToBeDeleted = new Group("member");
        if (toRemove.equals(notToBeDeleted)) {
            throw new GroupCannotBeRemovedException();
        }
        Boolean isPresent = false;

        for (Member member : members) {
            if (member.getGroup().equals(toRemove)) {
                isPresent = true;
            }
        }
        try {
            for (Member member : members) {
                removeGroupFromMember(toRemove, member);
            }
        } catch (MemberNotFoundException mnfe) {
            throw new AssertionError("Impossible: original member is obtained from the club book.");
        }
        if (!isPresent) {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Removes the Group {@code toRemove} from the {@code member} if the member's group matches the one to be removed.
     */
    private void removeGroupFromMember(Group toRemove, Member member)
            throws MemberNotFoundException {
        if (!member.getGroup().toString().equalsIgnoreCase(toRemove.toString())) {
            return;
        }

        Group defaultGroup = new Group(Group.DEFAULT_GROUP);
        Member newMember = new Member(member.getName(), member.getPhone(), member.getEmail(), member.getMatricNumber(),
                defaultGroup, member.getTags());

        try {
            updateMember(member, newMember);
        } catch (DuplicateMemberException dme) {
            throw new AssertionError("Deleting a member's group only should not result in a duplicate. "
                    + "See member#equals(Object).");
        }
    }

    /**
     * Adds {@code Task toAdd} to the list of tasks.
     */
    public void addTaskToTaskList(Task taskToAdd) throws DuplicateTaskException {
        tasks.add(taskToAdd);
    }

    public void deleteTask(Task targetTask) throws TaskNotFoundException {
        tasks.remove(targetTask);
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks.setTasks(tasks);
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

    public static final String DEFAULT_GROUP = "member";
    public static final String GROUP_EXCO = "exco";

    public final String groupName;

    /**
     * Constructs a {@code Group}.
     *
     * @param group A valid group.
     */
    public Group(String group) {
        requireNonNull(group);
        checkArgument(isValidGroup(group), MESSAGE_GROUP_CONSTRAINTS);
        this.groupName = group;
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
###### \java\seedu\club\model\ModelManager.java
``` java
    @Override
    public void removeGroup(Group toRemove) throws GroupNotFoundException, GroupCannotBeRemovedException {
        requireNonNull(toRemove);

        clubBook.removeGroup(toRemove);
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
        List<Member> members = new ArrayList<>(clubBook.getMemberList());

        List<String> emailRecipients = new ArrayList<>();
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

        return String.join(",", emailRecipients);
    }

    /**
     * Generates recipient list of all members part of {@code Group toSendEmailTo}
     * @throws GroupNotFoundException if {@code Group toSendEmailTo} doesn't exist in the club book
     */
    private String generateGroupEmailRecipients(Group toSendEmailTo) throws GroupNotFoundException {
        List<Member> members = new ArrayList<>(clubBook.getMemberList());

        List<String> emailRecipients = new ArrayList<>();
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
        return String.join(",", emailRecipients);
    }

    @Override
    public void sendEmail(String recipients, Client client, Subject subject, Body body) {
        raise(new SendEmailRequestEvent(recipients, subject, body, client));
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

    public String getAssignee() {
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

    public String getAssignor() {
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
            + "'-' in the format DD-MM-YYYY";
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
    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS = "Description should a non-empty alphanumeric string";
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
}
```
###### \java\seedu\club\model\task\Task.java
``` java
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
        return member.getName().toString().equalsIgnoreCase(task.getAssignor().getAssignor())
                || member.getName().toString().equalsIgnoreCase(task.getAssignee().getAssignee());
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

    public static final String MESSAGE_TIME_CONSTRAINTS = "Time must be in the format HH:MM and can be separated "
            + "by ':'";
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
        assignor = source.getAssignor().getAssignor();
        assignee = source.getAssignee().getAssignee();
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
###### \java\seedu\club\ui\BrowserPanel.java
``` java
    /**
     * Loads the client page based on {@code client}
     */
    private void callClient(String client, String recipients, String subject, String body) {
        if (client.equalsIgnoreCase(Client.VALID_CLIENT_GMAIL)) {
            String gMailUrl = String.format(GMAIL_URL, recipients, subject, body);
            loadGmailPage(gMailUrl);
        } else if (client.equalsIgnoreCase(Client.VALID_CLIENT_OUTLOOK)) {
            String outlookUrl = String.format(OUTLOOK_URL, recipients, subject, body);
            loadOutlookPage(outlookUrl);
        }
    }

    /**
     * loads the 'Compose Email' page based on the {@code outlookUrl} in Outlook
     * adapted from https://www.codeproject.com/Questions/398241/how-to-open-url-in-java
     */
    private void loadOutlookPage(String outlookUrl) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(URI.create(outlookUrl));
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * loads the 'Compose Email' page based on the {@code gMailUrl} in GMail
     * adapted from https://www.codeproject.com/Questions/398241/how-to-open-url-in-java
     */
    private void loadGmailPage(String gMailUrl) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(URI.create(gMailUrl));
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
```
###### \java\seedu\club\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleSendingEmailEvent(SendEmailRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Sending email via "
                + event.getClient().toString()));
        callClient(event.getClient().toString(), event.getRecipients(), event.getSubject().toString(),
                event.getBody().toString());
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
        assignor.setText("Assigned by: " + task.getAssignor().getAssignor());
        assignee.setText("Assigned to: " + task.getAssignee().getAssignee());
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
import seedu.club.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.club.model.task.Task;

/**
 * Panel containing tasks to be completed by the members.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private static final String DIRECTORY_PATH = "view/";
    private static final String TASK_YET_TO_BEGIN_CSS = DIRECTORY_PATH + "TaskYetToBegin.css";
    private static final String TASK_IN_PROGRESS_CSS = DIRECTORY_PATH + "TaskInProgress.css";
    private static final String TASK_COMPLETED_CSS = DIRECTORY_PATH + "TaskCompleted.css";
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
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
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
###### \resources\view\TaskCompleted.css
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
###### \resources\view\TaskInProgress.css
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

<?import javafx.geometry.Insets?>
<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <HBox alignment="CENTER_LEFT" spacing="5">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="description" styleClass="cell_big_label" text="\$first" />
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

<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="taskListView" VBox.vgrow="ALWAYS" />
</VBox>
```
