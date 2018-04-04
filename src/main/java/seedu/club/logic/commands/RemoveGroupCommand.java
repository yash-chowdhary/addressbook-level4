package seedu.club.logic.commands;
//@@author yash-chowdhary
import static java.util.Objects.requireNonNull;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.Messages;
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
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "rmgroup", "delgroup")
    );

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a Group from the Club Connect.\n"
            + "Parameters: "
            + PREFIX_GROUP + "GROUP";

    public static final String MESSAGE_SUCCESS = "Group deleted from Club Connect: %1$s";
    public static final String MESSAGE_NON_EXISTENT_GROUP = "%1$s group does not exist in Club Connect.";
    public static final String MESSAGE_MANDATORY_GROUP = "%1$s group cannot be deleted as it is a mandatory group.";

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
            if (requireToSignUp()) {
                return new CommandResult(Messages.MESSAGE_REQUIRE_SIGN_UP);
            } else if (requireToLogIn()) {
                return new CommandResult(Messages.MESSAGE_REQUIRE_LOG_IN);
            }
            model.removeGroup(toRemove);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toRemove));
        } catch (GroupNotFoundException gnfe) {
            throw new CommandException(String.format(MESSAGE_NON_EXISTENT_GROUP, toRemove.toString()));
        } catch (GroupCannotBeRemovedException gcbre) {
            throw new CommandException(String.format(MESSAGE_MANDATORY_GROUP, toRemove.toString()));
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveGroupCommand // instanceof handles nulls
                && toRemove.equals(((RemoveGroupCommand) other).toRemove));
    }
}
