package seedu.club.commons.events.ui;
//@@author MuhdNurKamal
import seedu.club.commons.events.BaseEvent;

/**
 * An event requesting to show all poll results.
 */
public class ShowResultsRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
