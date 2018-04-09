package seedu.club.model;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.club.commons.core.index.Index;
import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.exceptions.DataToChangeIsNotCurrentlyLoggedInMemberException;
import seedu.club.model.member.exceptions.DuplicateMatricNumberException;
import seedu.club.model.member.exceptions.MatricNumberNotFoundException;
import seedu.club.model.member.exceptions.MemberListNotEmptyException;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.member.exceptions.PasswordIncorrectException;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.exceptions.AnswerNotFoundException;
import seedu.club.model.poll.exceptions.DuplicatePollException;
import seedu.club.model.poll.exceptions.PollNotFoundException;
import seedu.club.model.poll.exceptions.UserAlreadyVotedException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskCannotBeDeletedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;
import seedu.club.model.task.exceptions.TasksAlreadyListedException;
import seedu.club.model.task.exceptions.TasksCannotBeDisplayedException;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Member> PREDICATE_SHOW_ALL_MEMBERS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to false
     */
    Predicate<Task> PREDICATE_NOT_SHOW_ALL_TASKS = unused -> false;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Member> PREDICATE_NOT_SHOW_ALL_MEMBERS = unused -> false;

    /**
     * {@code Predicate} that always evaluate to false
     */
    Predicate<Poll> PREDICATE_NOT_SHOW_ALL_POLLS = unused -> false;

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyClubBook newData);

    /**
     * Returns the ClubBook
     */
    ReadOnlyClubBook getClubBook();

    /**
     * Deletes the given member.
     */
    void deleteMember(Member target) throws MemberNotFoundException;

    /**
     * Adds the given member
     */
    void addMember(Member member) throws DuplicateMatricNumberException;

    /**
     * Adds the given poll
     */
    void addPoll(Poll poll) throws DuplicatePollException;

    /**
     * Deletes the given member.
     */
    void deletePoll(Poll poll) throws PollNotFoundException;

    /**
     * Votes current user in the given {@code poll} for the answer
     * specified by {@code answerIndex} in the answer list of the poll.
     */
    void voteInPoll(Poll poll, Index answerIndex) throws
            PollNotFoundException, AnswerNotFoundException, UserAlreadyVotedException;

    /**
     * Replaces the given member {@code target} with {@code editedMember}.
     *
     * @throws DuplicateMatricNumberException if updating the member's details causes the member's matriculation number
     *                                  to be equivalent to that of another existing member in the list.
     * @throws MemberNotFoundException  if {@code target} could not be found in the list.
     */
    void updateMember(Member target, Member editedMember)
            throws DuplicateMatricNumberException, MemberNotFoundException;

    /**
     * Returns an unmodifiable view of the filtered member list
     */
    ObservableList<Member> getFilteredMemberList();

    /**
     * Updates the filter of the filtered member list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMemberList(Predicate<Member> predicate);

    /**
     * Returns an unmodifiable view of the filtered poll list
     */
    ObservableList<Poll> getFilteredPollList();

    /**
     * Returns an unmodifiable view of the filtered member list
     */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered poll list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPollList(Predicate<Poll> predicate);

    //@@author Song Weiyang
    /**
     * Logs In a member in the club
     */
    void logsInMember(String username, String password);

    //@@author Song Weiyang
    /**
     * Returns the member who is currently logged in to Club Connect.
     */
    Member getLoggedInMember();

    //@@author amrut-prabhu

    /**
     * Removes the given tag {@code tag} for all members in the club book.
     */
    void deleteTag(Tag tag) throws TagNotFoundException;

    /**
     * Changes profile photo for the currently logged in member.
     *
     * @param originalPhotoPath Absolute file path of the original photo.
     * @throws PhotoReadException if the {@code originalPhotoPath} is invalid.
     */
    void addProfilePhoto(String originalPhotoPath) throws PhotoReadException;

    /**
     * Exports Club Connect's members' details to the specified file.
     *
     * @param exportFile File to which data is exported.
     * @throws IOException if there was an error writing to file.
     */
    void exportClubConnectMembers(File exportFile) throws IOException;

    /**
     * Imports details of members from the specified file.
     *
     * @param importFile File from which data is imported.
     * @return Number of members added from the import file.
     * @throws IOException if there was an error reading from file.
     */
    int importMembers(File importFile) throws IOException;

    /**
     * Returns an unmodifiable view of the filtered tag list
     */
    ObservableList<Tag> getFilteredTagList();

    /**
     * Updates the filter of the filtered tag list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTagList(Predicate<Tag> predicate);

    //@@author yash-chowdhary
    void removeGroup(Group toRemove) throws GroupNotFoundException, GroupCannotBeRemovedException;

    String generateEmailRecipients(Group group, Tag tag) throws GroupNotFoundException, TagNotFoundException;

    void sendEmail(String recipients, Client client, Subject subject, Body body);

    //@@author Song Weiyang
    /**
     * Logs out a member from clubbook
     */
    void logOutMember();

    void addTaskToTaskList(Task toAdd) throws DuplicateTaskException;

    void deleteTask(Task taskToDelete) throws TaskNotFoundException, TaskCannotBeDeletedException;

    void updateFilteredTaskList(Predicate<Task> predicate);
    //@@author Song Weiyang
    /**
     * Changes the password of the member in that list
     * @param username
     * @param oldPassword
     * @param newPassword
     * @throws PasswordIncorrectException
     */
    void changePassword(String username, String oldPassword, String newPassword)
            throws PasswordIncorrectException, DataToChangeIsNotCurrentlyLoggedInMemberException,
            MatricNumberNotFoundException;

    /**
     * Signs up a member if the clubbook is empty
     * @param member
     */
    void signUpMember(Member member) throws MemberListNotEmptyException;

    void clearClubBook();

    boolean getClearConfirmation();

    void setClearConfirmation(Boolean b);
    //@@author

    void viewAllTasks() throws TasksCannotBeDisplayedException;

    void assignTask(Task toAdd, MatricNumber matricNumber) throws MemberNotFoundException, DuplicateTaskException;

    void viewMyTasks() throws TasksAlreadyListedException;

    void changeStatus(Task taskToEdit, Task editedTask) throws TaskNotFoundException, DuplicateTaskException;
}
