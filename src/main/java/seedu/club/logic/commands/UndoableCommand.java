package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.club.model.Model.PREDICATE_SHOW_ALL_MEMBERS;
import static seedu.club.model.Model.PREDICATE_SHOW_ALL_POLLS;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.ClubBook;
import seedu.club.model.ReadOnlyClubBook;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyClubBook previousClubBook;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#clubBook}.
     */
    private void saveClubBookSnapshot() {
        requireNonNull(model);
        this.previousClubBook = new ClubBook(model.getClubBook());
    }

    /**
     * This method is called before the execution of {@code UndoableCommand}.
     * {@code UndoableCommand}s that require this preprocessing step should override this method.
     */
    protected void preprocessUndoableCommand() throws CommandException {}

    /**
     * Reverts the ClubBook to the state before this command
     * was executed and updates the filtered member list to
     * show all members.
     */
    protected final void undo() {
        requireAllNonNull(model, previousClubBook);
        model.resetData(previousClubBook);
        model.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        model.updateFilteredPollList(PREDICATE_SHOW_ALL_POLLS);
    }

    /**
     * Executes the command and updates the filtered member
     * list to show all members.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        model.updateFilteredPollList(PREDICATE_SHOW_ALL_POLLS);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveClubBookSnapshot();
        preprocessUndoableCommand();
        return executeUndoableCommand();
    }
}
