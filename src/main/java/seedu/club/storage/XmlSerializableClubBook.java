package seedu.club.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.ClubBook;
import seedu.club.model.ReadOnlyClubBook;

/**
 * An Immutable ClubBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableClubBook {

    @XmlElement
    private List<XmlAdaptedPerson> persons;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableClubBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableClubBook() {
        persons = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableClubBook(ReadOnlyClubBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code ClubBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} or {@code XmlAdaptedTag}.
     */
    public ClubBook toModelType() throws IllegalValueException {
        ClubBook clubBook = new ClubBook();
        for (XmlAdaptedTag t : tags) {
            clubBook.addTag(t.toModelType());
        }
        for (XmlAdaptedPerson p : persons) {
            clubBook.addPerson(p.toModelType());
        }
        return clubBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableClubBook)) {
            return false;
        }

        XmlSerializableClubBook otherAb = (XmlSerializableClubBook) other;
        return persons.equals(otherAb.persons) && tags.equals(otherAb.tags);
    }
}
