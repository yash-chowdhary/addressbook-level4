package seedu.club.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.core.index.Index;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.UniqueMemberList;
import seedu.club.model.member.exceptions.DataToChangeIsNotCurrentlyLoggedInMemberException;
import seedu.club.model.member.exceptions.DeleteCurrentUserException;
import seedu.club.model.member.exceptions.DuplicateMatricNumberException;
import seedu.club.model.member.exceptions.MatricNumberNotFoundException;
import seedu.club.model.member.exceptions.MemberListNotEmptyException;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.member.exceptions.PasswordIncorrectException;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.UniquePollList;
import seedu.club.model.poll.exceptions.AnswerNotFoundException;
import seedu.club.model.poll.exceptions.DuplicatePollException;
import seedu.club.model.poll.exceptions.PollNotFoundException;
import seedu.club.model.poll.exceptions.UserAlreadyVotedException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.UniqueTagList;
import seedu.club.model.tag.exceptions.TagNotFoundException;
import seedu.club.model.task.Assignee;
import seedu.club.model.task.Assignor;
import seedu.club.model.task.Task;
import seedu.club.model.task.UniqueTaskList;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskAlreadyAssignedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;

/**
 * Wraps all data at the club-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class ClubBook implements ReadOnlyClubBook {

    private static final int ZERO = 0;
    private final UniqueMemberList members;
    private final UniqueTagList tags;
    private final UniquePollList polls;
    private final UniqueTaskList tasks;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

        /*
        * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
        * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
        *
        * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
        *   among constructors.
        */ {
        members = new UniqueMemberList();
        tags = new UniqueTagList();
        polls = new UniquePollList();
        tasks = new UniqueTaskList();
    }

    public ClubBook() {
    }

    /**
     * Creates an ClubBook using the Members and Tags in the {@code toBeCopied}
     */
    public ClubBook(ReadOnlyClubBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }


    //// list overwrite operations
    public void setMembers(List<Member> members) throws DuplicateMatricNumberException {
        this.members.setMembers(members);
        this.members.fillHashMap();
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code ClubBook} with {@code newData}.
     */
    public void resetData(ReadOnlyClubBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        setPolls(new LinkedHashSet<>(newData.getPollList()));
        setTasks(new HashSet<>(newData.getTaskList()));
        List<Member> syncedMemberList = newData.getMemberList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        setLoggedInMember(newData.getLoggedInMember());

        try {
            setMembers(syncedMemberList);
        } catch (DuplicateMatricNumberException e) {
            throw new AssertionError("ClubConnect should not have duplicate members");
        }
    }


    //// member-level operations

    /**
     * Adds a member to the club book.
     * Also checks the new member's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the member to point to those in {@link #tags}.
     *
     * @throws DuplicateMatricNumberException if a member with the same matriculation number already exists.
     */
    public void addMember(Member m) throws DuplicateMatricNumberException {
        Member member = syncWithMasterTagList(m);
        // @@author amrut-prabhu
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any member
        // in the member list.
        try {
            members.add(member);
        } catch (DuplicateMatricNumberException dmne) {
            deleteTagsUniqueToMember(m);
            throw dmne;
        }
    }

    //@@author MuhdNurKamal
    /**
     * Removes {@code key} from this {@code ClubBook}.
     *
     * @throws PollNotFoundException if the {@code key} is not in this {@code ClubBook}.
     */
    public boolean removePoll(Poll key) throws PollNotFoundException {
        if (polls.remove(key)) {
            return true;
        } else {
            throw new PollNotFoundException();
        }
    }
    //@@author

    /**
     * Replaces the given member {@code target} in the list with {@code editedMember}.
     * {@code ClubBook}'s tag list will be updated with the tags of {@code editedMember}.
     *
     * @throws DuplicateMatricNumberException if updating the member's details causes the member's matriculation number
     *                                  to be equivalent to that of another existing member in the list.
     * @throws MemberNotFoundException  if {@code target} could not be found in the list.
     * @see #syncWithMasterTagList(Member)
     */
    public void updateMember(Member target, Member editedMember)
            throws DuplicateMatricNumberException, MemberNotFoundException {
        requireNonNull(editedMember);

        //@author amrut-prabhu
        deleteTagsUniqueToMember(target);
        Member syncedEditedMember = syncWithMasterTagList(editedMember);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        try {
            members.setMember(target, syncedEditedMember);
        } catch (DuplicateMatricNumberException dme) {
            addMemberTags(target);
            throw dme;
        }
    }

    /**
     * Adds back the tags of {@code target} member that were removed from {@code tags}.
     */
    private void addMemberTags(Member target) {
        Set<Tag> allTags = new HashSet<>(tags.asObservableList());
        allTags.addAll(target.getTags());
        tags.setTags(allTags);
    }

    /**
     * Changes the profile photo of the logged in member to the photo specified by {@code newPhotoPath}.
     *
     * @param newPhotoPath Path to the new photo file of the logged in member.
     */
    public void changeLoggedInMemberProfilePhoto(String newPhotoPath) {
        getLoggedInMember().setProfilePhotoPath(newPhotoPath);
    }
    //@@author
    /**
     * Updates the master tag list to include tags in {@code member} that are not in the list.
     *
     * @return a copy of this {@code member} such that every tag in this member points to a Tag object in the master
     * list.
     */
    private Member syncWithMasterTagList(Member member) {
        final UniqueTagList memberTags = new UniqueTagList(member.getTags());
        tags.mergeFrom(memberTags);

        // Create map with values = tag object references in the master list
        // used for checking member tag references
        final Map<String, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag.tagName, tag));

        // Rebuild the list of member tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        memberTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag.tagName)));
        return new Member(
                member.getName(), member.getPhone(), member.getEmail(), member.getMatricNumber(), member.getGroup(),
                correctTagReferences, member.getCredentials(), member.getProfilePhoto());
    }

    /**
     * Removes {@code key} from this {@code ClubBook}.
     *
     * @throws MemberNotFoundException if the {@code key} is not in this {@code ClubBook}.
     */
    public boolean removeMember(Member key) throws MemberNotFoundException, DeleteCurrentUserException {
        deleteTagsUniqueToMember(key);
        if (members.remove(key)) {
            return true;
        } else {
            throw new MemberNotFoundException();
        }
    }

    //@@author MuhdNurKamal
    public void setPolls(Set<Poll> polls) {
        this.polls.setPolls(polls);
    }

    public void addPoll(Poll poll) throws DuplicatePollException {
        polls.add(poll);
    }

    public String voteInPoll(Poll poll, Index answerIndex, MatricNumber polleeMatricNumber)
            throws PollNotFoundException, AnswerNotFoundException, UserAlreadyVotedException {
        return polls.voteInPoll(poll, answerIndex, polleeMatricNumber);
    }
    //@@author

    //@@author th14thmusician
    /**
     * Logs in a member
     */
    public void logInMember(String inputUsername, String inputPassword) {
        members.fillHashMap();
        members.logsInMember(inputUsername, inputPassword);
    }

    /**
     * logs out a member
     */
    public void logOutMember() {
        members.logout();
    }

    /**
     * Sign up a member if it is a new clubbook
     */
    public void signUpMember(Member p) throws MemberListNotEmptyException {
        Member member = syncWithMasterTagList(p);
        members.signup(member);
    }

    //@@author yash-chowdhary

    /**
     * Removes the Group {@code toRemove} from the Club Book. Every member who was once a part of {@code toRemove}
     * will be assigned the default group - "member".
     */
    public void deleteGroup(Group toRemove) throws GroupCannotBeRemovedException, GroupNotFoundException {
        checkIfGroupIsMemberOrExco(toRemove);
        checkIfGroupIsPresent(toRemove);
        deleteGroupFromClubBook(toRemove);
        logger.fine("Group " + toRemove + " has been removed.");
    }

    /**
     * Removes the Group {@code toRemove} from Club Connect.
     */
    private void deleteGroupFromClubBook(Group toRemove) {
        try {
            for (Member member : members) {
                deleteGroupFromMember(toRemove, member);
            }
        } catch (MemberNotFoundException mnfe) {
            throw new AssertionError("Impossible: original member is obtained from the club book.");
        }
    }

    /**
     * Checks if {@code toRemove} exists in Club Connect.
     * @throws GroupNotFoundException if {@code toRemove} is not found.
     */
    private void checkIfGroupIsPresent(Group toRemove) throws GroupNotFoundException {
        Boolean isPresent = false;

        for (Member member : members) {
            if (member.getGroup().equals(toRemove)) {
                isPresent = true;
            }
        }
        if (!isPresent) {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Checks if {@code toRemove} is "member".
     * @throws GroupCannotBeRemovedException if {@code toRemove} is "member".
     */
    private void checkIfGroupIsMemberOrExco(Group toRemove) throws GroupCannotBeRemovedException {
        Group groupMember = new Group("member");
        Group groupExco = new Group("exco");
        if (toRemove.equals(groupMember) || toRemove.equals(groupExco)) {
            throw new GroupCannotBeRemovedException();
        }
    }

    /**
     * Removes the Group {@code toRemove} from the {@code member} if the member's group matches the one to be removed.
     */
    private void deleteGroupFromMember(Group toRemove, Member member)
            throws MemberNotFoundException {
        if (!member.getGroup().toString().equalsIgnoreCase(toRemove.toString())) {
            return;
        }

        Group defaultGroup = new Group(Group.DEFAULT_GROUP);
        Member newMember = new Member(member.getName(), member.getPhone(), member.getEmail(), member.getMatricNumber(),
                defaultGroup, member.getTags(), member.getCredentials(), member.getProfilePhoto());

        try {
            updateMember(member, newMember);
        } catch (DuplicateMatricNumberException dme) {
            throw new AssertionError("Deleting a member's group only should not result in a duplicate. "
                    + "See member#equals(Object).");
        }
    }

    /**
     * Adds {@code Task toAdd} to the list of tasks.
     */
    public void addTaskToTaskList(Task taskToAdd) throws DuplicateTaskException {
        tasks.add(taskToAdd);
        logger.fine("Task added to task list.");
    }

    /**
     * Deletes {@code targetTask} from the list of tasks.
     * @throws TaskNotFoundException if the task doesn't exist.
     */
    public void deleteTask(Task targetTask) throws TaskNotFoundException {
        tasks.remove(targetTask);
        logger.fine("Task removed from task list.");
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks.setTasks(tasks);
    }
    //@@author


    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //@@author amrut-prabhu
    /**
     * Removes tags from master {@code tags} list that are unique to {@code member}.
     *
     * @param member Member whose tags may be removed from {@code tags}.
     */
    private void deleteTagsUniqueToMember(Member member) {
        List<Tag> tagsToCheck = new ArrayList<>(getTagList());
        Set<Tag> newTags = tagsToCheck.stream()
                .filter(t -> !isTagUniqueToMember(t, member))
                .collect(Collectors.toSet());
        tags.setTags(newTags);
    }
    /**
     * Returns true if only {@code member} is tagged with {@code tag}.
     *
     * @param tag Tag that is to be checked.
     * @param member Member whose tags are to be checked.
     */
    private boolean isTagUniqueToMember(Tag tag, Member member) {
        for (Member m : members) {
            if (m.hasTag(tag) && !m.equals(member)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes {@code tagToDelete} for all members in this {@code ClubBook}.
     *
     * @param tagToDelete Tag to be removed
     * @throws TagNotFoundException if the list of {@code tags} does not contain {@code tagToDelete}.
     */
    public void deleteTag(Tag tagToDelete) throws TagNotFoundException {
        //Update tags list
        List<Tag> tags = new ArrayList<Tag>(getTagList());
        if (!tags.contains(tagToDelete)) {
            throw new TagNotFoundException();
        }
        setTags(getListWithoutTag(tagToDelete));

        //Update members list
        try {
            for (Member member : members) {
                if (member.hasTag(tagToDelete)) {
                    deleteTagFromMember(tagToDelete, member);
                }
            }
        } catch (MemberNotFoundException mnfe) {
            throw new AssertionError("Impossible: original member is obtained from the club book.");
        }
    }

    /**
     * Returns a list of tags which does not contain {@code tagToDelete}.
     *
     * @param tagToDelete Tag which should not be included in the tag list
     */
    private Set<Tag> getListWithoutTag(Tag tagToDelete) {
        List<Tag> tags = new ArrayList<Tag>(this.getTagList());
        return tags.stream()
                .filter(t -> !t.equals(tagToDelete))
                .collect(Collectors.toSet());
    }

    /**
     * Removes {@code tag} from {@code member} in this {@code ClubBook}.
     *
     * @param tag Tag which is to be removed from {@code member}.
     * @param member Member from whom {@code tag} is to be removed.
     * @throws MemberNotFoundException if the {@code member} is not in this {@code ClubBook}.
     */
    private void deleteTagFromMember(Tag tag, Member member) throws MemberNotFoundException {
        Set<Tag> memberTags = new HashSet<>(member.getTags());
        if (!memberTags.remove(tag)) {
            return;
        }

        Member newMember = new Member(member.getName(), member.getPhone(), member.getEmail(), member.getMatricNumber(),
                member.getGroup(), memberTags, member.getCredentials(), member.getProfilePhoto());
        try {
            updateMember(member, newMember);
        } catch (DuplicateMatricNumberException dme) {
            throw new AssertionError("Modifying a member's tags only should not result in a duplicate. "
                    + "See member#equals(Object).");
        }
    }

    //@@author th14thmusician
    /**
     * Change the password of {@code member} in the ClubBook.
     * @param username
     * @param oldpassword
     * @param newPassword
     */
    public void changePassword (String username, String oldpassword, String newPassword)
            throws PasswordIncorrectException, DataToChangeIsNotCurrentlyLoggedInMemberException,
            MatricNumberNotFoundException {
        members.changePassword(username, oldpassword, newPassword);
    }

    @Override
    public void setLoggedInMember(Member target) {
        members.setCurrentlyLogInMember(target);
    }

    @Override
    public Member getLoggedInMember() {
        return members.getCurrentlyLogInMember();
    }

    public void clearClubBook() {
        members.clear();
    }
    //@@author

    //// util methods
    @Override
    public String toString() {
        return members.asObservableList().size() + " members, " + tags.asObservableList().size() + " tags, "
                + polls.asObservableList().size() + " polls, " + tasks.asObservableList().size() + "tasks";
        // TODO: refine later
    }

    @Override
    public ObservableList<Member> getMemberList() {
        return members.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public ObservableList<Poll> getPollList() {
        return polls.asObservableList();
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClubBook // instanceof handles nulls
                && this.members.equals(((ClubBook) other).members)
                && this.tags.equalsOrderInsensitive(((ClubBook) other).tags));
    }



    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(members, tags);
    }

    //@@author yash-chowdhary
    /**
     * Update {@code Task target}'s status with that of {@code Task editedMember}.
     * @throws DuplicateTaskException if updating the tasks's details causes the task to be equivalent to
     *                                  another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void updateTaskStatus(Task taskToEdit, Task editedTask) throws DuplicateTaskException,
            TaskNotFoundException {
        requireNonNull(editedTask);
        tasks.setTask(taskToEdit, editedTask);
        logger.fine("Task status updated to " + editedTask.getStatus().getStatus());
    }

    /**
     * Update {@code Task target}'s Assignee with that of {@code Task editedMember}.
     * @throws DuplicateTaskException if updating the tasks's details causes the task to be equivalent to
     *                                  another existing task in the list, ignoring the status.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void updateTaskAssignee(Task taskToEdit, Task editedTask) throws DuplicateTaskException {
        requireNonNull(editedTask);
        try {
            tasks.setTaskIgnoreStatus(taskToEdit, editedTask);
        } catch (DuplicateTaskException dte) {
            throw new DuplicateTaskException();
        }
        logger.fine("Task assignee updated to " + editedTask.getAssignee().getValue());
    }

    /**
     * Updates the task if there is a change in Matric Number of the target member.
     * @return number of tasks updated.
     * @throws DuplicateTaskException if there is already a task with similar attributes (regardless of status).
     */
    public int updateTaskHelper(Member target, Member editedMember) throws DuplicateTaskException {
        ObservableList<Task> taskObservableList = tasks.asObservableList();
        if (target.getMatricNumber().equals(editedMember.getMatricNumber())) {
            return ZERO;
        }
        int numberOfTasksUpdated = ZERO;
        numberOfTasksUpdated = updateTasks(target, editedMember, taskObservableList, numberOfTasksUpdated);
        logger.info("Updated " + numberOfTasksUpdated + "tasks in task list.");
        return numberOfTasksUpdated;

    }

    /**
     * Updates tasks by looping through the task list.
     * @throws DuplicateTaskException if the update causes a duplicate task.
     */
    private int updateTasks(Member target, Member editedMember, ObservableList<Task> taskObservableList,
                            int numberOfTasksUpdated) throws DuplicateTaskException {
        for (Task task : taskObservableList) {
            Task editedTask = null;
            String editedMemberMatricNumberString = editedMember.getMatricNumber().toString();
            String targetMemberMatricNumberString = target.getMatricNumber().toString();

            if (task.getAssignor().getValue().equalsIgnoreCase(targetMemberMatricNumberString)
                    && task.getAssignee().getValue().equalsIgnoreCase(targetMemberMatricNumberString)) {

                numberOfTasksUpdated = updateWhenSameAssignorAndAssignee(numberOfTasksUpdated, task,
                        editedMemberMatricNumberString);
            } else if (task.getAssignor().getValue().equalsIgnoreCase(targetMemberMatricNumberString)) {

                numberOfTasksUpdated = updateWhenSameAssignor(numberOfTasksUpdated, task,
                        editedMemberMatricNumberString);
            } else if (task.getAssignee().getValue().equalsIgnoreCase(targetMemberMatricNumberString)) {

                numberOfTasksUpdated = updateWhenSameAssignee(numberOfTasksUpdated, task,
                        editedMemberMatricNumberString);
            }
        }
        return numberOfTasksUpdated;
    }

    /**
     * Updates task which has Assignee same as targetMember's Matric Number
     * @throws DuplicateTaskException if the update results in a duplicate task.
     */
    private int updateWhenSameAssignee(int numberOfTasksUpdated, Task task,
                                       String editedMemberMatricNumberString) throws DuplicateTaskException {
        Task editedTask;
        Assignee newAssignee = new Assignee(editedMemberMatricNumberString);
        editedTask = new Task(task.getDescription(), task.getTime(), task.getDate(),
                task.getAssignor(), newAssignee, task.getStatus());
        tasks.setTaskIgnoreStatus(task, editedTask);
        numberOfTasksUpdated++;
        return numberOfTasksUpdated;
    }

    /**
     * Updates task which has Assignor same as targetMember's Matric Number
     * @throws DuplicateTaskException if the update results in a duplicate task.
     */
    private int updateWhenSameAssignor(int numberOfTasksUpdated, Task task,
                                       String editedMemberMatricNumberString) throws DuplicateTaskException {
        Task editedTask;
        Assignor newAssignor = new Assignor(editedMemberMatricNumberString);
        editedTask = new Task(task.getDescription(), task.getTime(), task.getDate(),
                newAssignor, task.getAssignee(), task.getStatus());
        tasks.setTaskIgnoreStatus(task, editedTask);
        numberOfTasksUpdated++;
        return numberOfTasksUpdated;
    }

    /**
     * Updates task which has Assignor and Assignee same as targetMember's Matric Number
     * @throws DuplicateTaskException if the update results in a duplicate task.
     */
    private int updateWhenSameAssignorAndAssignee(int numberOfTasksUpdated, Task task,
                                                  String editedMemberMatricNumberString) throws DuplicateTaskException {
        Task editedTask;
        Assignee newAssignee = new Assignee(editedMemberMatricNumberString);
        Assignor newAssignor = new Assignor(editedMemberMatricNumberString);

        editedTask = new Task(task.getDescription(), task.getTime(), task.getDate(),
                newAssignor, newAssignee, task.getStatus());
        tasks.setTaskIgnoreStatus(task, editedTask);
        numberOfTasksUpdated++;
        return numberOfTasksUpdated;
    }

    /**
     * Removes all tasks that have been assigned to {@code member}.
     */
    public int removeTasksOfMember(Member member) {

        int numberOfTasksRemoved = ZERO;
        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {
            Task task = it.next();
            if (task.getAssignee().getValue().equalsIgnoreCase(member.getMatricNumber().toString())) {
                it.remove();
                numberOfTasksRemoved++;
            }
        }
        logger.info("Removed " + numberOfTasksRemoved + "tasks from task list.");
        return numberOfTasksRemoved;
    }

    /**
     * Checks if a similar task has been assigned to the member by some other exco member.
     * The task's Assignor and Status are ignored in this comparison.
     * @throws TaskAlreadyAssignedException if there exists a task like so.
     */
    public void checkIfTaskIsAlreadyAssigned(Task toAdd) throws TaskAlreadyAssignedException {
        ObservableList<Task> taskObservableList = getTaskList();
        for (Task task : taskObservableList) {
            if (task.getDescription().getDescription().equalsIgnoreCase(toAdd.getDescription().getDescription())
                    && task.getDate().getDate().equalsIgnoreCase(toAdd.getDate().getDate())
                    && task.getTime().getTime().equalsIgnoreCase(toAdd.getTime().getTime())
                    && task.getAssignee().getValue().equalsIgnoreCase(toAdd.getAssignee().getValue())) {
                throw new TaskAlreadyAssignedException();
            }
        }
    }

    /**
     * Checks if a similar task exists.
     * @throws DuplicateTaskException if there exists a duplicate task.
     */
    public void checkIfDuplicateTaskExists(Task toAdd) throws DuplicateTaskException {
        ObservableList<Task> taskObservableList = getTaskList();
        for (Task task : taskObservableList) {
            if (task.getDescription().getDescription().equalsIgnoreCase(toAdd.getDescription().getDescription())
                    && task.getDate().getDate().equalsIgnoreCase(toAdd.getDate().getDate())
                    && task.getTime().getTime().equalsIgnoreCase(toAdd.getTime().getTime())
                    && task.getAssignor().getValue().equalsIgnoreCase(toAdd.getAssignor().getValue())
                    && task.getAssignee().getValue().equalsIgnoreCase(toAdd.getAssignee().getValue())) {
                throw new DuplicateTaskException();
            }
        }
    }
}
