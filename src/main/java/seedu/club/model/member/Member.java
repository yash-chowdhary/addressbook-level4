package seedu.club.model.member;

import static seedu.club.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.club.model.member.ProfilePhoto.EMPTY_STRING;

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
    private final MatricNumber matricNumber;
    private Group group;
    private final UniqueTaskList tasks;
    private final HashMap<String, Tag> tags;
    private ProfilePhoto profilePhoto;

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
        this.credentials = new Credentials(new Username(matricNumber.value));
        this.profilePhoto = new ProfilePhoto(EMPTY_STRING);
        this.tasks = new UniqueTaskList();
        this.tags = new HashMap<String, Tag>();
        setTags(tags);
    }

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

    //@@author amrut-prabhu

    /**
     * Adds {@code memberTags} to {@code this} member's {@code tags}.
     *
     * @param memberTags Tags to be added to {@code this} member.
     */
    private void setTags(Set<Tag> memberTags) {
        Iterator itr = memberTags.iterator();

        while (itr.hasNext()) {
            Tag tag = (Tag) itr.next();
            tags.put(tag.tagName, tag);
        }
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

    public boolean hasTag(Tag tag) {
        return getTags().contains(tag);
    }

    public ProfilePhoto getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhotoPath(String newPath) {
        profilePhoto.setNewPhotoPath(newPath);
    }

    //@@author
    /**
     * Returns an immutable task set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Task> getTasks() {
        return Collections.unmodifiableSet(tasks.toSet());
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


        return builder.toString();
    }

    public Credentials getCredentials() {
        return credentials;
    }
}
