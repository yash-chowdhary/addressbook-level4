package seedu.club.logic.commands;
//@@author yash-chowdhary
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
