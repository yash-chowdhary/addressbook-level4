package seedu.club.logic.commands;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.ClearMemberSelectPanelEvent;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.model.Model;



/**
 * Logs a member out of the clubbook
 */
public class LogOutCommand extends Command {
    public static final String COMMAND_WORD = "logout";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "signout")
    );

    public static final String MESSAGE_SUCCESS = "Logout successful.";
    public static final String MESSAGE_FAILURE = "You are not logged in.";

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
            model.updateFilteredPollList(Model.PREDICATE_NOT_SHOW_ALL_POLLS);
            EventsCenter.getInstance().post(new ClearMemberSelectPanelEvent(true));
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        super.setData(model, history, undoRedoStack);
    }
}
