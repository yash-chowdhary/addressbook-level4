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
@XmlRootElement(name = "clubbook")
public class XmlSerializableClubBook {

    @XmlElement
    private List<XmlAdaptedMember> members;
    @XmlElement
    private List<XmlAdaptedTag> tags;
    @XmlElement
    private List<XmlAdaptedPoll> polls;
    @XmlElement
    private List<XmlAdaptedTask> tasks;

    /**
     * Creates an empty XmlSerializableClubBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableClubBook() {
        members = new ArrayList<>();
        tags = new ArrayList<>();
        polls = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableClubBook(ReadOnlyClubBook src) {
        this();
        members.addAll(src.getMemberList().stream().map(XmlAdaptedMember::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
        polls.addAll(src.getPollList().stream().map(XmlAdaptedPoll::new).collect(Collectors.toList()));
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this clubbook into the model's {@code ClubBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedMember} or {@code XmlAdaptedTag}.
     */
    public ClubBook toModelType() throws IllegalValueException {
        ClubBook clubBook = new ClubBook();
        for (XmlAdaptedTag t : tags) {
            clubBook.addTag(t.toModelType());
        }
        for (XmlAdaptedMember m : members) {
            clubBook.addMember(m.toModelType());
        }
        for (XmlAdaptedPoll p : polls) {
            clubBook.addPoll(p.toModelType());
        }
        for (XmlAdaptedTask task : tasks) {
            clubBook.addTaskToTaskList(task.toModelType());
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
        return members.equals(otherAb.members) && tags.equals(otherAb.tags)
                && polls.equals(otherAb.polls) && tasks.equals(otherAb.tasks);
    }
}
