package seedu.club.logic.commands;

import seedu.club.commons.core.Messages;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;
import seedu.club.model.group.Group;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    protected CommandHistory history;
    protected UndoRedoStack undoRedoStack;

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of members.
     *
     * @param displaySize used to generate summary
     * @return summary message for members displayed
     */
    public static String getMessageForMemberListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_MEMBERS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        this.model = model;
    }

    //@@author th14thmusician
    /**
     * Requires user to login before proceeding
     */
    protected void requireToLogIn () throws CommandException {
        if (model.getLoggedInMember() == null) {
            throw new CommandException(Messages.MESSAGE_REQUIRE_LOG_IN);
        }
    }

    /**
     * Requires user to Sign Up
     */
    protected void requireToSignUp () throws CommandException {
        if (model.getClubBook().getMemberList().isEmpty()) {
            throw new CommandException(Messages.MESSAGE_REQUIRE_SIGN_UP);
        }
    }

    /**
     * Requires exco access to use the command
     */
    protected void requireExcoLogIn () throws CommandException {
        if (!model.getLoggedInMember().getGroup().groupName.equalsIgnoreCase(Group.GROUP_EXCO)) {
            throw new CommandException(Messages.MESSAGE_REQUIRE_EXCO_LOG_IN);
        }
    }

    /**
     * Requires user to logout
     */
    protected void requireToLogOut () throws CommandException {
        if (model.getLoggedInMember() != null) {
            throw new CommandException(Messages.MESSAGE_REQUIRE_LOG_OUT);
        }
    }
}
