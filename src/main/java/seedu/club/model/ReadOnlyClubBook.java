package seedu.club.model;

import javafx.collections.ObservableList;
import seedu.club.model.member.Member;
import seedu.club.model.poll.Poll;
import seedu.club.model.tag.Tag;

/**
 * Unmodifiable view of an club book
 */
public interface ReadOnlyClubBook {

    /**
     * Returns an unmodifiable view of the members list.
     * This list will not contain any duplicate members.
     */
    ObservableList<Member> getMemberList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    /**
     * Returns an unmodifiable view of the polls list.
     * This list will not contain any duplicate polls.
     */
    ObservableList<Poll> getPollList();

    /**
     * Returns true if tag is present in the tags list.
     */
    //boolean containsTag(Tag tag);
}
