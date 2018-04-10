package seedu.club.commons.events.ui;

import seedu.club.commons.events.BaseEvent;

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
        return null;
    }
}
