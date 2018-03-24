package seedu.club.commons.events.ui;

//@@author yash-chowdhary
/**
 * Represents a change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent {
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
