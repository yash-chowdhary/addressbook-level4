package seedu.club.logic.commands;
import static java.util.Objects.requireNonNull;

import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.model.Model;



/**
 * Logs a member out of the clubbook
 */
public class LogOutCommand extends Command {
    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_SUCCESS = "logout successful!";
    public static final String MESSAGE_FAILURE = "There are no member currently logged in";

    public LogOutCommand(){
    }

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        if (model.getLoggedInMember() == null) {
            return new CommandResult(MESSAGE_FAILURE);
        } else {
            model.logOutMember();
            model.updateFilteredMemberList(Model.PREDICATE_NOT_SHOW_ALL_MEMBERS);
            model.updateFilteredTaskList(Model.PREDICATE_NOT_SHOW_ALL_TASKS);
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        super.setData(model, history, undoRedoStack);
    }
}
