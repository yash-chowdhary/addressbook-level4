package seedu.club.commons.events.ui;

import seedu.club.commons.core.index.Index;
import seedu.club.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of members
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
