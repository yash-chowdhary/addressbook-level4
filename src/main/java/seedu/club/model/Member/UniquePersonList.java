package seedu.club.model.Member;

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.club.commons.util.CollectionUtil;
import seedu.club.model.Member.exceptions.DuplicatePersonException;
import seedu.club.model.Member.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Member#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniquePersonList implements Iterable<Member> {

    private final ObservableList<Member> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent Member as the given argument.
     */
    public boolean contains(Member toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Member to the list.
     *
     * @throws DuplicatePersonException if the Member to add is a duplicate of an existing Member in the list.
     */
    public void add(Member toAdd) throws DuplicatePersonException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Member {@code target} in the list with {@code editedMember}.
     *
     * @throws DuplicatePersonException if the replacement is equivalent to another existing Member in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    public void setPerson(Member target, Member editedMember)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedMember);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.equals(editedMember) && internalList.contains(editedMember)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedMember);
    }

    /**
     * Removes the equivalent Member from the list.
     *
     * @throws PersonNotFoundException if no such Member could be found in the list.
     */
    public boolean remove(Member toRemove) throws PersonNotFoundException {
        requireNonNull(toRemove);
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return personFoundAndDeleted;
    }

    public void setPersons(UniquePersonList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPersons(List<Member> members) throws DuplicatePersonException {
        requireAllNonNull(members);
        final UniquePersonList replacement = new UniquePersonList();
        for (final Member member : members) {
            replacement.add(member);
        }
        setPersons(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Member> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Member> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && this.internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Logs in a member successfully and return a true value
     * @param username
     * @param password
     * @return
     */
    public boolean logInMemberSuccessful(String username, String password) {
        if (!areThereAnyMemberLogedIn()) {
            for (int i = 0; i < internalList.size(); i++) {
                if (internalList.get(i).getUsername().toString().equals(username)) {
                    return internalList.get(i).getPassword().toString().equals(password);
                }
            }
        }
        return false;
    }

    /**
     * Check that whether there are anyone being log in currently.
     */
    public boolean areThereAnyMemberLogedIn() {
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).isLogIn()) {
                return true;
            }
        }
        return false;
    }
}
