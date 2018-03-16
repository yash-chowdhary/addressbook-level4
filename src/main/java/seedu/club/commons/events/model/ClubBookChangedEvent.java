package seedu.club.commons.events.model;

import seedu.club.commons.events.BaseEvent;
import seedu.club.model.ReadOnlyClubBook;

/** Indicates the ClubBook in the model has changed*/
public class ClubBookChangedEvent extends BaseEvent {

    public final ReadOnlyClubBook data;

    public ClubBookChangedEvent(ReadOnlyClubBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of members " + data.getMemberList().size() + ", number of tags " + data.getTagList().size();
    }
}
