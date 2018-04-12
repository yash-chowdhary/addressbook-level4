package seedu.club.commons.events.ui;

import seedu.club.commons.events.BaseEvent;
import seedu.club.model.member.Member;
import seedu.club.model.tag.Tag;

/**
 *
 */
public class UpdateSelectionPanelEvent extends BaseEvent {
    private Member member;
    private boolean toDelete;
    private Tag tagToDelete;

    public UpdateSelectionPanelEvent (Member member, boolean toDelete, Tag removedTag) {
        this.member = member;
        this.toDelete = toDelete;
        this.tagToDelete = removedTag;
    }

    public Member getUpdatedMember() {
        return this.member;
    }

    public Tag getTagToDelete() {
        return tagToDelete;
    }

    public boolean isToDelete() {
        return toDelete;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
