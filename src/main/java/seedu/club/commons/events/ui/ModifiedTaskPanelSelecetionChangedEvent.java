package seedu.club.commons.events.ui;
//@@author th14thmusician
import seedu.club.commons.events.BaseEvent;
import seedu.club.ui.ModifiedTaskCard;

/**
 * Represents a change in the Modified Task List Panel
 */
public class ModifiedTaskPanelSelecetionChangedEvent extends BaseEvent {
    private final ModifiedTaskCard newSelection;

    public ModifiedTaskPanelSelecetionChangedEvent(ModifiedTaskCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ModifiedTaskCard getNewSelection() {
        return newSelection;
    }
}
//@@author
