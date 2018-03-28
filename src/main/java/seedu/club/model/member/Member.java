package seedu.club.model.member;

import static seedu.club.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import seedu.club.model.group.Group;
import seedu.club.model.tag.Tag;
import seedu.club.model.task.Task;
import seedu.club.model.task.UniqueTaskList;

/**
 * Represents a member in the club book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Member {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private Credentials credentials;
    private boolean isLogIn = false;
    private final MatricNumber matricNumber;
    private Group group;
    private final HashMap<String, Tag> tags;
    private ProfilePhoto profilePhoto;
    private final UniqueTaskList tasks;
    /**
     * Every field must be present and not null.
     */

    public Member(Name name, Phone phone, Email email, MatricNumber matricNumber, Group group, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, matricNumber, group, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.matricNumber = matricNumber;
        this.group = group;
        this.tags = new HashMap<String, Tag>();
        this.credentials = new Credentials(new Username(matricNumber.value));
        this.profilePhoto = new ProfilePhoto("");
        setTags(tags);
        this.tasks = new UniqueTaskList();
    }

    public Member(Name name, Phone phone, Email email, MatricNumber matricNumber, Group group, Set<Tag> tags,
                  Credentials credentials, ProfilePhoto profilePhoto, Set<Task> tasks) {
        requireAllNonNull(name, phone, email, matricNumber, group, tags, credentials, tasks);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.matricNumber = matricNumber;
        this.group = group;
        this.tags = new HashMap<>();
        this.profilePhoto = new ProfilePhoto("");
        setTags(tags);
        this.tasks = new UniqueTaskList(tasks);
        this.credentials = credentials;
    }

    //@@author amrut-prabhu
    /**
     * Every field must be present and not null.
     */
    public Member(Name name, Phone phone, Email email, MatricNumber matricNumber, Group group, Set<Tag> tags,
                  Credentials credentials, ProfilePhoto profilePhoto) {
        requireAllNonNull(name, phone, email, matricNumber, group, tags, credentials);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.matricNumber = matricNumber;
        this.group = group;
        this.tags = new HashMap<String, Tag>();
        this.profilePhoto = profilePhoto;
        setTags(tags);
        this.tasks = new UniqueTaskList();
        this.credentials = credentials;
    }

    /**
     * Copy constructor
     * @param member Member whose attributes are to be copied to (@code this}.
     */
    public Member(Member member) {
        this.name = member.name;
        this.phone = member.phone;
        this.email = member.email;
        this.matricNumber = member.matricNumber;
        this.group = member.group;
        this.tags = member.tags;
        this.profilePhoto = member.profilePhoto;
        this.tasks = member.tasks;
        this.credentials = member.credentials;
    }
    //@@author

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public MatricNumber getMatricNumber() {
        return matricNumber;
    }

    public Group getGroup() {
        return group;
    }

    private void setTags(Set<Tag> memberTags) {
        Iterator itr = memberTags.iterator();

        while (itr.hasNext()) {
            Tag tag = (Tag) itr.next();
            tags.put(tag.tagName, tag);
        }
    }

    public ProfilePhoto getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(ProfilePhoto profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setProfilePhotoPath(String newPath) {
        profilePhoto.setNewPhotoPath(newPath);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        Set<Tag> memberTags = new HashSet<Tag>();

        Set<String> tagNames = tags.keySet();
        Iterator itr = tagNames.iterator();

        while (itr.hasNext()) {
            String key = (String) itr.next();
            memberTags.add(tags.get(key));
        }

        return Collections.unmodifiableSet(memberTags);
    }

    /**
     * Returns an immutable task set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Task> getTasks() {
        return Collections.unmodifiableSet(tasks.toSet());
    }

    public boolean hasTag(Tag tag) {
        return getTags().contains(tag);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Member)) {
            return false;
        }

        Member otherMember = (Member) other;
        return otherMember.getName().equals(this.getName())
                && otherMember.getPhone().equals(this.getPhone())
                && otherMember.getEmail().equals(this.getEmail())
                && otherMember.getMatricNumber().equals(this.getMatricNumber());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, matricNumber, group, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" MatricNumber: ")
                .append(getMatricNumber())
                .append(" Group: ")
                .append(getGroup())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append("Tasks: ");
        getTasks().forEach(builder::append);
        return builder.toString();
    }

    /**
     * change the status of the member loggin in
     */

    public void changeLogInStatus() {
        if (!isLogIn) {
            isLogIn = true;
        } else {
            isLogIn = false;
        }
    }

    public Credentials getCredentials() {
        return credentials;
    }
}
