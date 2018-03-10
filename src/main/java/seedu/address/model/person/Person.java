package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.group.Group;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;

    private Group group;
    private final HashMap<String, Tag> tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Group group, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, group, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.group = group;
        this.tags = new HashMap<String, Tag>();
        setTags(tags);
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

    public Address getAddress() {
        return address;
    }

    public Group getGroup() {
        return group;
    }

    private void setTags(Set<Tag> personTags) {
        Iterator itr = personTags.iterator();

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
        Set<Tag> personTags = new HashSet<Tag>();

        Set<String> tagNames = tags.keySet();
        Iterator itr = tagNames.iterator();

        while (itr.hasNext()) {
            String key = (String) itr.next();
            personTags.add(tags.get(key));
        }

        return Collections.unmodifiableSet(personTags);
    }

    public boolean hasTag(Tag tag) {
        return getTags().contains(tag);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getPhone().equals(this.getPhone())
                && otherPerson.getEmail().equals(this.getEmail())
                && otherPerson.getAddress().equals(this.getAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, group, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Group: ")
                .append(getGroup())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
