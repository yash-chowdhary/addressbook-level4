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

/**
 * Represents a member in the club book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Member {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Password password;
    private final Username username;
    private boolean isLogIn = false;
    private final MatricNumber matricNumber;
    private Group group;
    private final HashMap<String, Tag> tags;
    private ProfilePhoto profilePhoto;

    /**
     * Every field must be present and not null.
     */
    public Member(Name name, Phone phone, Email email, MatricNumber matricNumber, Group group, Set<Tag> tags,
                  Username username, Password password) {
        requireAllNonNull(name, phone, email, matricNumber, group, tags,
                username, password);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.matricNumber = matricNumber;
        this.group = group;
        this.tags = new HashMap<String, Tag>();
        this.username = username;
        this.password = password;
        this.profilePhoto = new ProfilePhoto("");
        setTags(tags);
    }

    //@@author amrut-prabhu

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
        this.username = member.username;
        this.password = member.password;
        this.profilePhoto = member.profilePhoto;
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

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public boolean isLogIn() {
        return isLogIn;
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
        return builder.toString();
    }

    /**
     * Returns {@code this} Member's data in the format of a CSV record.
     * @return {@code String} containing the data in CSV format.
     */
    public String toCsvFormat() {
        String csvFieldSurrounder = "\"";
        String csvValueSeparator = ",";
        String lineBreak = "\n";

        final StringBuilder builder = new StringBuilder();

        addCsvField(builder, getName().toString());
        addCsvField(builder, getPhone().toString());
        addCsvField(builder, getEmail().toString());
        addCsvField(builder, getMatricNumber().toString());
        addCsvField(builder, getGroup().toString());
        addCsvField(builder, getProfilePhoto().toString());
        addCsvField(builder, getUsername().toString());
        addCsvField(builder, getPassword().toString());

        builder.append(csvFieldSurrounder);
        getTags().forEach(tag -> builder.append(tag)
                .append(csvValueSeparator)); //Results in an extra "," at end of tag list.
        builder.append(csvFieldSurrounder);

        builder.append(lineBreak);

        return builder.toString();
    }

    /**
     * Appends (@code builder} with {@code field} in CSV format.
     * @param builder StringBuilder which is to be appended.
     * @param field Field value that is to be appended.
     */
    private void addCsvField(StringBuilder builder, String field) {
        assert field != null : "Field cannot be null in Member object";

        String csvFieldSeparator = ",";
        String csvFieldSurrounder = "\"";

        builder.append(csvFieldSurrounder)
                .append(field)
                .append(csvFieldSurrounder)
                .append(csvFieldSeparator);
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
}
