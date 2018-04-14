package seedu.club.model.member;

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.club.commons.util.CollectionUtil;
import seedu.club.model.member.exceptions.DataToChangeIsNotCurrentlyLoggedInMemberException;
import seedu.club.model.member.exceptions.DeleteCurrentUserException;
import seedu.club.model.member.exceptions.DuplicateMatricNumberException;
import seedu.club.model.member.exceptions.MatricNumberNotFoundException;
import seedu.club.model.member.exceptions.MemberListNotEmptyException;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.member.exceptions.PasswordIncorrectException;

/**
 * A list of members that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Member#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueMemberList implements Iterable<Member> {

    private final ObservableList<Member> internalList = FXCollections.observableArrayList();
    private HashMap<String, Member> usernameCredentialsHashMap = new HashMap<>();
    private HashMap<String, String> usernamePasswordHashMap = new HashMap<>();
    private Member currentlyLogInMember = null;

    /**
     * Returns true if the list contains an equivalent member as the given argument.
     */
    public boolean contains(Member toCheck) {
        requireNonNull(toCheck);
        return containsMatricNumber(toCheck.getMatricNumber());
    }

    // @@author amrut-prabhu
    /**
     * Returns true if {@code internalList} of members contains a member with the same {@code MatricNumber}.
     *
     * @param toCheck Matric Number that is to be checked for uniqueness.
     */
    private boolean containsMatricNumber(MatricNumber toCheck) {
        requireNonNull(toCheck);
        for (Member member: internalList) {
            if (member.getMatricNumber().equals(toCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a member to the list.
     *
     * @throws DuplicateMatricNumberException if a member with the same matriculation number as member to add exists.
     */
    public void add(Member toAdd) throws DuplicateMatricNumberException {
        requireNonNull(toAdd);
        if (this.containsMatricNumber(toAdd.getMatricNumber())) {
            throw new DuplicateMatricNumberException();
        }
        // @@author
        internalList.add(toAdd);
        usernameCredentialsHashMap.put(toAdd.getCredentials().getUsername().value, toAdd);
        usernamePasswordHashMap.put(toAdd.getCredentials().getUsername().value,
                toAdd.getCredentials().getPassword().value);
    }

    /**
     * Replaces the member {@code target} in the list with {@code editedMember}.
     *
     * @throws DuplicateMatricNumberException if the replacement's matriculation number is equivalent to that of
     *  `                               another existing member in the list.
     * @throws MemberNotFoundException if {@code target} could not be found in the list.
     */
    public void setMember(Member target, Member editedMember)
            throws DuplicateMatricNumberException, MemberNotFoundException {
        requireNonNull(editedMember);

        int index = internalList.indexOf(target);
        if (index == -1 || usernamePasswordHashMap.get(target.getCredentials().getUsername().toString()) == null) {
            throw new MemberNotFoundException();
        }

        // @@author amrut-prabhu
        if (!target.equals(editedMember) && this.containsMatricNumber(editedMember.getMatricNumber())
                && !target.getMatricNumber().equals(editedMember.getMatricNumber())) {
            throw new DuplicateMatricNumberException();
        }
        // @@author

        internalList.set(index, editedMember);
        usernamePasswordHashMap.remove(target.getCredentials().getUsername().value);
        usernamePasswordHashMap.put(editedMember.getCredentials().getUsername().value,
                editedMember.getCredentials().getPassword().value);
    }

    /**
     * Removes the equivalent member from the list.
     *
     * @throws MemberNotFoundException if no such member could be found in the list.
     */
    public boolean remove(Member toRemove) throws MemberNotFoundException, DeleteCurrentUserException {
        requireNonNull(toRemove);
        if (toRemove.equals(getCurrentlyLogInMember())) {
            throw new DeleteCurrentUserException();
        }
        final boolean memberFoundAndDeleted = internalList.remove(toRemove);
        if (!memberFoundAndDeleted) {
            throw new MemberNotFoundException();
        }
        usernameCredentialsHashMap.remove(toRemove.getCredentials().getUsername().toString());
        usernamePasswordHashMap.remove(toRemove.getCredentials().getUsername().toString());
        return memberFoundAndDeleted;
    }

    public void setMembers(UniqueMemberList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setMembers(List<Member> members) throws DuplicateMatricNumberException {
        requireAllNonNull(members);
        final UniqueMemberList replacement = new UniqueMemberList();
        for (final Member member : members) {
            replacement.add(member);
        }
        setMembers(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Member> asObservableList() {
        sort();
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Member> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMemberList // instanceof handles nulls
                        && this.internalList.equals(((UniqueMemberList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
    //@@author th14thmusician
    /**
     * Logs in a member successfully and return a true value
     * @return
     */
    public void logsInMember(String username, String password) {
        username = username.toUpperCase();
        Member checkMember = usernameCredentialsHashMap.get(username);
        if (checkMember != null && usernamePasswordHashMap.get(username).equals(password)) {
            currentlyLogInMember = checkMember;
        }
    }

    /**
     * Get the member who is logged in
     * @return
     */
    public Member getCurrentlyLogInMember() {
        return currentlyLogInMember;
    }

    /**
     * Fill the hashmap with username and member, and also username and password
     */
    public void fillHashMap() {
        for (Member anInternalList : internalList) {
            usernameCredentialsHashMap.put(anInternalList.getCredentials().getUsername().value, anInternalList);
            usernamePasswordHashMap.put(anInternalList.getCredentials().getUsername().value,
                    anInternalList.getCredentials().getPassword().value);
        }
    }

    /**
     Sort the list according to alphabetical order
     */
    public void sort() {
        internalList.sort(new Comparator<Member>() {
            @Override
            public int compare(Member otherMember1, Member otherMember2) {
                return otherMember1.getName().toString().compareTo(otherMember2.getName().toString());
            }
        });
    }

    /**
     * Logs out the user
     */
    public void logout() {
        setCurrentlyLogInMember(null);
    }

    public void setCurrentlyLogInMember(Member member) {
        currentlyLogInMember = member;
    }

    /**
     * Changes the password of a member
     */
    public void changePassword (String username, String oldPassword, String newPassword)
            throws PasswordIncorrectException, DataToChangeIsNotCurrentlyLoggedInMemberException,
            MatricNumberNotFoundException {
        Member checkMember = usernameCredentialsHashMap.get(username);
        if (!usernameCredentialsHashMap.containsKey(username)) {
            throw new MatricNumberNotFoundException();
        }
        if (!checkMember.equals(currentlyLogInMember)) {
            throw new DataToChangeIsNotCurrentlyLoggedInMemberException();
        }
        if (usernamePasswordHashMap.get(username).equals(oldPassword)) {
            internalList.get(internalList.indexOf(checkMember)).getCredentials().setPassword(new Password(newPassword));
            usernamePasswordHashMap.remove(username);
            usernamePasswordHashMap.put(username, newPassword);
        } else {
            throw new PasswordIncorrectException();
        }
    }
    /**
     * Sign up a user when the clubbook is empty
     */
    public void signup(Member member) throws MemberListNotEmptyException {
        if (!internalList.isEmpty()) {
            throw new MemberListNotEmptyException();
        }
        internalList.add(member);
        usernameCredentialsHashMap.put(member.getCredentials().getUsername().value, member);
        usernamePasswordHashMap.put(member.getCredentials().getUsername().value,
                member.getCredentials().getPassword().value);
    }

    /**
     * Clears the clubbook
     */
    public void clear() {
        internalList.clear();
        usernamePasswordHashMap.clear();
        usernameCredentialsHashMap.clear();
        setCurrentlyLogInMember(null);
        System.out.println(getCurrentlyLogInMember());
    }
    //@@author
}
