package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;

/**
 * Removes a tag from all members in the club book.
 */
public class DeleteTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletetag";
    public static final String COMMAND_FORMAT = "deletetag t/ ";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the tag from all members. "
            + "Parameters: TAG (must be an existing tag)\n"
            + "Example: " + COMMAND_WORD + " t/treasurer";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Tag Removed: %1$s";
    public static final String MESSAGE_NON_EXISTENT_TAG = "The tag name provided does not exist";

    private Tag tagToDelete;

    public DeleteTagCommand(Tag tagToDelete) {
        this.tagToDelete = tagToDelete;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(tagToDelete);

        try {
            model.deleteTag(tagToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, tagToDelete));
        } catch (TagNotFoundException tnfe) {
            throw new CommandException(MESSAGE_NON_EXISTENT_TAG);
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Tag> lastShownList = model.getFilteredTagList();

        if (!getMasterTagList().contains(tagToDelete)) {
            throw new CommandException(MESSAGE_NON_EXISTENT_TAG);
        }

        int targetIndex = lastShownList.indexOf(tagToDelete);
        tagToDelete = lastShownList.get(targetIndex);
    }

    private List<Tag> getMasterTagList() {
        return new ArrayList<>(model.getClubBook().getTagList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTagCommand // instanceof handles nulls
                && this.tagToDelete.equals(((DeleteTagCommand) other).tagToDelete));
    }
}
