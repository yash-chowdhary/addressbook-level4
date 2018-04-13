package seedu.club.commons.events.ui;
//@@author th14thmusician
import seedu.club.commons.events.BaseEvent;
import seedu.club.model.member.Member;
import seedu.club.model.tag.Tag;

/**
 *
 */
public class UpdateSelectionPanelEvent extends BaseEvent {
    private Member toEditMember;
    private Member editedMember;
    private boolean toDelete;
    private Tag tagToDelete;
    private boolean toUndo;

    public UpdateSelectionPanelEvent (Member toEditMember, Member editedMember, boolean toDelete,
                                      Tag removedTag, boolean toUndo) {
        this.toEditMember = toEditMember;
        this.editedMember = editedMember;
        this.toDelete = toDelete;
        this.tagToDelete = removedTag;
        this.toUndo = toUndo;
    }

    public Member getToEditMember() {
        return toEditMember;
    }

    public Member getEditedMember() {
        return editedMember;
    }

    public boolean isToUndo() {
        return toUndo;
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
