package seedu.club.testutil;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.ClubBook;
import seedu.club.model.member.Member;
import seedu.club.model.member.exceptions.DuplicateMemberException;
import seedu.club.model.tag.Tag;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;

/**
 * A utility class to help with building Clubbook objects.
 * Example usage: <br>
 *     {@code ClubBook cb = new ClubBookBuilder().withMember("John", "Doe").withTag("Friend").build();}
 */
public class ClubBookBuilder {

    private ClubBook clubBook;

    public ClubBookBuilder() {
        clubBook = new ClubBook();
    }

    public ClubBookBuilder(ClubBook clubBook) {
        this.clubBook = clubBook;
    }

    /**
     * Adds a new {@code member} to the {@code ClubBook} that we are building.
     */
    public ClubBookBuilder withMember(Member member) {
        try {
            clubBook.addMember(member);
        } catch (DuplicateMemberException dpe) {
            throw new IllegalArgumentException("member is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code ClubBook} that we are building.
     */
    public ClubBookBuilder withTag(String tagName) {
        try {
            clubBook.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    /**
     * Parses {@code task} into a {@code Task} and adds it to the {@code ClubBook} that we are building.
     */
    public ClubBookBuilder withTask(Task task) {
        try {
            clubBook.addTaskToTaskList(task);
        } catch (DuplicateTaskException dte) {
            throw new IllegalArgumentException("task is expected to be unique");
        }
        return this;
    }

    public ClubBook build() {
        return clubBook;
    }
}
