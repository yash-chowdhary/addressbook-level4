package seedu.club.logic.commands;

import static seedu.club.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.club.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TO;

import seedu.club.logic.commands.email.Body;
import seedu.club.logic.commands.email.Client;
import seedu.club.logic.commands.email.Subject;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupNotFoundException;

/**
 * Sends an email to the desired recipient(s) in a particular group of the club book.
 */
public class EmailGroupCommand extends Command {

    public static final String COMMAND_WORD = "emailgroup";

    public static final String COMMAND_USAGE = COMMAND_WORD + ": Sends an email to the desired recipients(s) in a "
            + "particular group of the club book. "
            + "Parameters: " + " "
            + PREFIX_TO + "GROUP" + " "
            + PREFIX_CLIENT + "EMAIL CLIENT" + " "
            + PREFIX_SUBJECT + "SUBJECT" + " "
            + PREFIX_BODY + "BODY\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TO + "logistics "
            + PREFIX_CLIENT + "gmail "
            + PREFIX_SUBJECT + "Test Subject "
            + PREFIX_BODY + "Test Body ";

    public static final String EMAIL_CLIENT_OPENED = "Email client opened!";
    public static final String MESSAGE_NOT_SENT = "Please adhere to the command usage.";

    private Group group;
    private Client client;
    private Subject subject;
    private Body body;

    public EmailGroupCommand(Group group, Client client, Subject subject, Body body) {
        this.group = group;
        this.client = client;
        this.subject = new Subject(subject.toString().replaceAll("\\s", "+"));
        this.body = new Body(body.toString().replaceAll("\\s", "+"));
    }


    @Override
    public CommandResult execute() throws CommandException {
        try {
            String emailRecipients = model.generateGroupEmailRecipients(group);
            model.sendEmail(emailRecipients, client, subject, body);
            return new CommandResult(EMAIL_CLIENT_OPENED);
        } catch (GroupNotFoundException gnfe) {
            throw new CommandException(RemoveGroupCommand.MESSAGE_NON_EXISTENT_GROUP);
        }
    }
}
