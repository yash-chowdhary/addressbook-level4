package seedu.club.commons.events.ui;

import seedu.club.commons.events.BaseEvent;
import seedu.club.ui.PersonCard;

/**
 * Represents a selection change in the Member List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final PersonCard newSelection;

    public PersonPanelSelectionChangedEvent(PersonCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PersonCard getNewSelection() {
        return newSelection;
    }
}
