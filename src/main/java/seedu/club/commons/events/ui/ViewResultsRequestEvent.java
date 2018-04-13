package seedu.club.commons.events.ui;
//@@author MuhdNurKamal
import seedu.club.commons.events.BaseEvent;

/**
 * An event requesting to view all poll results.
 */
public class ViewResultsRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
