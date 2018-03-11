package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<Person> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setPersons(syncedPersonList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate persons");
        }
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person p) throws DuplicatePersonException {
        Person person = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.add(person);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Person)
     */
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);

        removePersonTags(target);

        Person syncedEditedPerson = syncWithMasterTagList(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        try {
            persons.setPerson(target, syncedEditedPerson);
        } catch (DuplicatePersonException dpe) {
            addTargetPersonTags(target);
            throw new DuplicatePersonException();
        }
    }
    
    /**
     * Re-adds the tags of {@code target} that were removed from {@code tags}.
     */
    private void addTargetPersonTags(Person target) {
        Set<Tag> allTags = new HashSet<>(tags.asObservableList());

        for (Tag tag: target.getTags()) {
            allTags.add(tag);
        }

        tags.setTags(allTags);
    }

    /**
     *  Updates the master tag list to include tags in {@code person} that are not in the list.
     *  @return a copy of this {@code person} such that every tag in this person points to a Tag object in the master
     *  list.
     */
    private Person syncWithMasterTagList(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<String, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag.tagName, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag.tagName)));
        return new Person(
                person.getName(), person.getPhone(), person.getEmail(), person.getMatricNumber(), person.getGroup(),
                    correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException {
        removePersonTags(key);

        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Removes tags from master tag list {@code tags} that are unique to person {@code person}.
     */
    private void removePersonTags(Person person) {
        List<Tag> tagsToCheck = tags.asObservableList().stream().collect(Collectors.toList());
        Set<Tag> newTags = tagsToCheck.stream()
                .filter(t -> !isTagUniqueToPerson(t, person))
                .collect(Collectors.toSet());
        tags.setTags(newTags);
        /*
        Iterator<Tag> itr = tagsToCheck.iterator();
        while (itr.hasNext()) {
            Tag tag = itr.next();
            if (isTagUniqueToPerson(tag, person)) {
                removeTag(tag);
            }
        }*/
    }

    /**
     * Returns true if only {@code key} is tagged with {@code tag}.
     */
    private boolean isTagUniqueToPerson(Tag tag, Person key) {
        for (Person person : persons) {
            if (person.hasTag(tag) && !person.equals(key)) {
                return false;
            }
        }
        return true;
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    /**
     * Removes {@code tag} for all persons in this {@code AddressBook}.
     * @param tag Tag to be removed
     */
    public void removeTag(Tag tag) throws TagNotFoundException {
        List<Tag> tags = new ArrayList<Tag>(getTagList());
        if (!tags.contains(tag)) {
            return;
        }

        setTags(getListWithoutTag(tag));
        try {
            for (Person person : persons) {
                if (person.hasTag(tag)) {
                    removeTagFromPerson(tag, person);
                }
            }
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible: original person is obtained from the address book.");
        }
    }

    /**
     * Returns a list of tags which does not contain {@code tagToRemove}.
     * @param tagToRemove Tag which should not be included in the tagToRemove list
     */
    private Set<Tag> getListWithoutTag(Tag tagToRemove) {
        Set<Tag> newTagsList = new HashSet<>();

        Iterator<Tag> itr = tags.iterator();

        while (itr.hasNext()) {
            Tag tag = itr.next();
            if (!tag.equals(tagToRemove)) {
                newTagsList.add(tag);
            }
        }

        return newTagsList;
    }

    /**
     * Removes {@code tag} from {@code person} in this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code person} is not in this {@code AddressBook}.
     */
    private void removeTagFromPerson(Tag tag, Person person) throws PersonNotFoundException {
        Set<Tag> personTags = new HashSet<>(person.getTags());

        if (!personTags.remove(tag)) {
            return;
        }

        Person newPerson = new Person(person.getName(), person.getPhone(),
                person.getEmail(), person.getMatricNumber(),
                person.getGroup(), personTags);

        try {
            updatePerson(person, newPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Modifying a person's tags only should not result in a duplicate. "
                    + "See Person#equals(Object).");
        }
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
