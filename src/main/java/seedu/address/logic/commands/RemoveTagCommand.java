package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Removes a tag from all persons in the address book.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removeTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the tag from all persons.\n"
            + "Parameters: TAG (must be an existing tag)\n"
            + "Example: " + COMMAND_WORD + " subcommittee";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Tag Removed: %1$s";

    private Tag tagToRemove;

    public RemoveTagCommand(Tag tagToRemove) { this.tagToRemove = tagToRemove; }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(tagToRemove);

        try {
            model.removeTag(tagToRemove);
        } catch (TagNotFoundException tnfe) {
            throw new AssertionError("The tag to be deleted cannot be missing");
        }

        return new CommandResult((String.format(MESSAGE_REMOVE_TAG_SUCCESS, tagToRemove)));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (!getMasterTagList().contains(tagToRemove)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG);
        }
    }

    private List<Tag> getMasterTagList() {
        return new ArrayList<Tag>(model.getAddressBook().getTagList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && this.tagToRemove.equals(((RemoveTagCommand) other).tagToRemove));
    }
}
