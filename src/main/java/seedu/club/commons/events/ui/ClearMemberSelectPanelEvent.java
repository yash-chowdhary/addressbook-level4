package seedu.club.commons.events.ui;
//@@author th14thmusician
import seedu.club.commons.events.BaseEvent;

/**
 * Requesting to clear or logout removing the details in the members panel
 */
public class ClearMemberSelectPanelEvent extends BaseEvent {
    private boolean toClear;

    public ClearMemberSelectPanelEvent (boolean toClear) {
        this.toClear = toClear;
    }

    public boolean isToClear() {
        return toClear;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
