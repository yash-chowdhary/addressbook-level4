//@@author amrut-prabhu
package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.UpdateSelectionPanelEvent;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;

/**
 * Removes a tag from all members in the club book.
 */
public class DeleteTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletetag";
    public static final String COMMAND_FORMAT = "deletetag t/ ";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "deltag", "rmtag")
    );

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified tag from all members.\n"
            + "Parameters: TAG (must be an existing tag)\n"
            + "Example: " + COMMAND_WORD + " t/EventHelper";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted tag: %1$s";
    public static final String MESSAGE_NON_EXISTENT_TAG = "This tag does not exist in Club Connect.";

    private Tag tagToDelete;

    public DeleteTagCommand(Tag tagToDelete) {
        this.tagToDelete = tagToDelete;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(tagToDelete);
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        try {
            model.deleteTag(tagToDelete);
            EventsCenter.getInstance().post(new UpdateSelectionPanelEvent(null, null, false, tagToDelete, false));
            return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, tagToDelete));
        } catch (TagNotFoundException tnfe) {
            throw new CommandException(MESSAGE_NON_EXISTENT_TAG);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTagCommand // instanceof handles nulls
                && this.tagToDelete.equals(((DeleteTagCommand) other).tagToDelete));
    }
}
