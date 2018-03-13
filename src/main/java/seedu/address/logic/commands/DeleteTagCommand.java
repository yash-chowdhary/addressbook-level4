package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Removes a tag from all persons in the address book.
 */
public class DeleteTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the tag from all persons.\n"
            + "Parameters: TAG (must be an existing tag)\n"
            + "Example: " + COMMAND_WORD + " subcommittee";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Tag Removed: %1$s";

    private Tag tagToDelete;

    public DeleteTagCommand(Tag tagToDelete) {
        this.tagToDelete = tagToDelete;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(tagToDelete);

        try {
            model.removeTag(tagToDelete);
        } catch (TagNotFoundException tnfe) {
            throw new AssertionError("The tag to be deleted cannot be missing");
        }

        return new CommandResult((String.format(MESSAGE_DELETE_TAG_SUCCESS, tagToDelete)));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Tag> lastShownList = model.getFilteredTagList();

        if (!getMasterTagList().contains(tagToDelete)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG);
        }

        int targetIndex = lastShownList.indexOf(tagToDelete);
        tagToDelete = lastShownList.get(targetIndex);
    }

    private List<Tag> getMasterTagList() {
        return new ArrayList<>(model.getAddressBook().getTagList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTagCommand // instanceof handles nulls
                && this.tagToDelete.equals(((DeleteTagCommand) other).tagToDelete));
    }
}
