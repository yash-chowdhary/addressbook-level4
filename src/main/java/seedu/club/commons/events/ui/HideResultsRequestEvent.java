package seedu.club.commons.events.ui;

import seedu.club.commons.events.BaseEvent;

/**
 * An event requesting to hide all poll results.
 */
public class HideResultsRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
