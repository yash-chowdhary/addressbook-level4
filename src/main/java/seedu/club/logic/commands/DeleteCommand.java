package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.commons.events.ui.UpdateSelectionPanelEvent;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.member.Member;
import seedu.club.model.member.exceptions.DeleteCurrentUserException;
import seedu.club.model.member.exceptions.MemberNotFoundException;

/**
 * Deletes a member identified using it's last displayed index from the club book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_FORMAT = "delete INDEX";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "del", "rm", "remove")
    );

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the member identified by the index number used in the last member listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MEMBER_SUCCESS = "Deleted member: %1$s\n"
            + "Number of tasks deleted from main task list: %2$d.";

    private final Index targetIndex;

    private Member memberToDelete;
    private int numberOfTasksDeleted;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(memberToDelete);
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        try {
            numberOfTasksDeleted = model.deleteMember(memberToDelete);
        } catch (MemberNotFoundException mnfe) {
            throw new AssertionError("The target member cannot be missing");
        } catch (DeleteCurrentUserException e) {
            throw new CommandException(Messages.MESSAGE_UNABLE_TO_DELETE_CURRENT_USER);
        }
        EventsCenter.getInstance().post(new UpdateSelectionPanelEvent(memberToDelete, null, true, null, false));
        return new CommandResult(String.format(MESSAGE_DELETE_MEMBER_SUCCESS, memberToDelete, numberOfTasksDeleted));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        List<Member> lastShownList = model.getFilteredMemberList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
        }

        memberToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex) // state check
                && Objects.equals(this.memberToDelete, ((DeleteCommand) other).memberToDelete));
    }
}
