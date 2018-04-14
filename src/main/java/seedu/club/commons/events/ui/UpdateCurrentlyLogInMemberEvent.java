package seedu.club.commons.events.ui;
//@@author th14thmusician
import seedu.club.commons.events.BaseEvent;
import seedu.club.model.member.Member;

/**
 * Represent a change in log in member
 */
public class UpdateCurrentlyLogInMemberEvent extends BaseEvent {
    private Member currentlyLogIn;

    public UpdateCurrentlyLogInMemberEvent(Member member) {
        this.currentlyLogIn = member;
    }

    public Member getCurrentlyLogIn() {
        return currentlyLogIn;
    }

    @Override
    public String toString() {
        return null;
    }
}
//@@author
