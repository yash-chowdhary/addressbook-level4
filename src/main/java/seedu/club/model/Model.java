package seedu.club.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.club.logic.commands.email.Body;
import seedu.club.logic.commands.email.Client;
import seedu.club.logic.commands.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.Member;
import seedu.club.model.member.exceptions.DuplicateMemberException;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Member> PREDICATE_SHOW_ALL_MEMBERS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Member> PREDICATE_NOT_SHOW_ALL_MEMBERS = unused -> false;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyClubBook newData);

    /** Returns the ClubBook */
    ReadOnlyClubBook getClubBook();

    /** Deletes the given member. */
    void deleteMember(Member target) throws MemberNotFoundException;

    /** Adds the given member */
    void addMember(Member member) throws DuplicateMemberException;

    /**
     * Replaces the given member {@code target} with {@code editedMember}.
     *
     * @throws DuplicateMemberException if updating the member's details causes the member to be equivalent to
     *      another existing member in the list.
     * @throws MemberNotFoundException if {@code target} could not be found in the list.
     */
    void updateMember(Member target, Member editedMember)
            throws DuplicateMemberException, MemberNotFoundException;

    /** Returns an unmodifiable view of the filtered member list */
    ObservableList<Member> getFilteredMemberList();

    /**
     * Updates the filter of the filtered member list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMemberList(Predicate<Member> predicate);

    /**
     * Logs In a member in the club
     */
    void logsInMember(String username, String password);

    /**
     * Get the member who is log in, if null, there are no one that is logged in.
     */
    Member getLoggedInMember();

    /** Removes the given tag {@code tag} for all members in the club book. */
    void deleteTag(Tag tag) throws TagNotFoundException;

    /** Returns an unmodifiable view of the filtered tag list */
    ObservableList<Tag> getFilteredTagList();

    /**
     * Updates the filter of the filtered tag list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTagList(Predicate<Tag> predicate);

    void removeGroup(Group toRemove) throws GroupNotFoundException, GroupCannotBeRemovedException;

    String generateEmailRecipients(Group group, Tag tag) throws GroupNotFoundException, TagNotFoundException;

    void sendEmail(String recipients, Client client, Subject subject, Body body);
}
