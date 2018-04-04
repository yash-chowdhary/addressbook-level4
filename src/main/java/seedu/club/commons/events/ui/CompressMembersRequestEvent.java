package seedu.club.commons.events.ui;
//@@author MuhdNurKamal
import seedu.club.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class CompressMembersRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
