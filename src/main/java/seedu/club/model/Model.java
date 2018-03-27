package seedu.club.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.Member;
import seedu.club.model.member.exceptions.DuplicateMemberException;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.exceptions.DuplicatePollException;
import seedu.club.model.poll.exceptions.PollNotFoundException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskCannotBeDeletedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Member> PREDICATE_SHOW_ALL_MEMBERS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Poll> PREDICATE_SHOW_ALL_POLLS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyClubBook newData);

    /** Returns the ClubBook */
    ReadOnlyClubBook getClubBook();

    /** Deletes the given member. */
    void deleteMember(Member target) throws MemberNotFoundException;

    /** Adds the given member */
    void addMember(Member member) throws DuplicateMemberException;

    /** Adds the given poll */
    void addPoll(Poll poll) throws DuplicatePollException;

    /** Deletes the given member. */
    void deletePoll(Poll poll) throws PollNotFoundException;

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

    /** Returns an unmodifiable view of the filtered poll list */
    ObservableList<Poll> getFilteredPollList();

    /** Returns an unmodifiable view of the filtered member list */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered poll list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPollList(Predicate<Poll> predicate);

    /**
     * Logs In a member in the club
     */
    boolean logInMemberSuccessful(String username, String password);

    /**
     * Returns the member who is currently logged in to Club Connect.
     */
    Member getLoggedInMember();

    /** Removes the given tag {@code tag} for all members in the club book. */
    void deleteTag(Tag tag) throws TagNotFoundException;

    //@@author amrut-prabhu

    /**
     * Returns true if profile photo is successfully changed for the logged in member.
     * @param originalPhotoPath Absolute file path of the original photo.
     */
    boolean addProfilePhoto(String originalPhotoPath);

    //@@author

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

    void addTaskToTaskList(Task toAdd) throws DuplicateTaskException;

    void deleteTask(Task taskToDelete) throws TaskNotFoundException, TaskCannotBeDeletedException;
}
