package seedu.club.commons.events.ui;

import seedu.club.commons.events.BaseEvent;
import seedu.club.ui.PollCard;

/**
 * Represents a selection change in the poll List Panel
 */
public class PollPanelSelectionChangedEvent extends BaseEvent {


    private final PollCard newSelection;

    public PollPanelSelectionChangedEvent(PollCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PollCard getNewSelection() {
        return newSelection;
    }
}
