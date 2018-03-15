package seedu.club.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.club.model.Member.Member;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.Member.exceptions.DuplicatePersonException;
import seedu.club.model.Member.exceptions.PersonNotFoundException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Member> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyClubBook newData);

    /** Returns the ClubBook */
    ReadOnlyClubBook getClubBook();

    /** Deletes the given Member. */
    void deletePerson(Member target) throws PersonNotFoundException;

    /** Adds the given Member */
    void addPerson(Member member) throws DuplicatePersonException;

    /**
     * Replaces the given Member {@code target} with {@code editedMember}.
     *
     * @throws DuplicatePersonException if updating the Member's details causes the Member to be equivalent to
     *      another existing Member in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Member target, Member editedMember)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered Member list */
    ObservableList<Member> getFilteredPersonList();

    /**
     * Updates the filter of the filtered Member list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Member> predicate);

    /**
     * Logs In a Member in the club
     */
    boolean logInMemberSuccessful(String username, String password);


    /** Removes the given tag {@code tag} for all persons in the club book. */
    void deleteTag(Tag tag) throws TagNotFoundException;

    /** Returns an unmodifiable view of the filtered tag list */
    ObservableList<Tag> getFilteredTagList();

    /**
     * Updates the filter of the filtered tag list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTagList(Predicate<Tag> predicate);

    void removeGroup(Group toRemove) throws GroupNotFoundException, GroupCannotBeRemovedException;

}
