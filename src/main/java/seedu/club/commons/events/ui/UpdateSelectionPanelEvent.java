package seedu.club.commons.events.ui;
//@@author th14thmusician
import seedu.club.commons.events.BaseEvent;
import seedu.club.model.member.Member;

/**
 *
 */
public class UpdateSelectionPanelEvent extends BaseEvent {
    private Member member;
    private boolean toDelete;

    public UpdateSelectionPanelEvent (Member member, boolean toDelete) {
        this.member = member;
        this.toDelete = toDelete;
    }

    public Member getUpdatedMember() {
        return this.member;
    }

    public boolean isToDelete() {
        return toDelete;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
