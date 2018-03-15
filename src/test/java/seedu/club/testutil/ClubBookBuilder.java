package seedu.club.testutil;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.ClubBook;
import seedu.club.model.person.Person;
import seedu.club.model.person.exceptions.DuplicatePersonException;
import seedu.club.model.tag.Tag;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ClubBook ab = new ClubBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
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
     * Adds a new {@code Person} to the {@code ClubBook} that we are building.
     */
    public ClubBookBuilder withPerson(Person person) {
        try {
            clubBook.addPerson(person);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
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

    public ClubBook build() {
        return clubBook;
    }
}
