package seedu.club.commons.events.ui;

//@@author yash-chowdhary

import seedu.club.commons.events.BaseEvent;
import seedu.club.ui.TaskCard;

/**
 * Represents a change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {
    private final TaskCard newSelection;

    public TaskPanelSelectionChangedEvent(TaskCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TaskCard getNewSelection() {
        return newSelection;
    }
}
