package seedu.club.logic.commands;
//@@author yash-chowdhary
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
