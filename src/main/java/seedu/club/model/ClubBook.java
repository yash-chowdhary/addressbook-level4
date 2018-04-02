package seedu.club.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.Member;
import seedu.club.model.member.UniqueMemberList;
import seedu.club.model.member.exceptions.DuplicateMemberException;
import seedu.club.model.member.exceptions.MemberListNotEmptyException;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.UniquePollList;
import seedu.club.model.poll.exceptions.DuplicatePollException;
import seedu.club.model.poll.exceptions.PollNotFoundException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.UniqueTagList;
import seedu.club.model.tag.exceptions.TagNotFoundException;
import seedu.club.model.task.Task;
import seedu.club.model.task.UniqueTaskList;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskNotFoundException;

/**
 * Wraps all data at the club-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class ClubBook implements ReadOnlyClubBook {

    private final UniqueMemberList members;
    private final UniqueTagList tags;
    private final UniquePollList polls;
    private final UniqueTaskList tasks;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        members = new UniqueMemberList();
        tags = new UniqueTagList();
        polls = new UniquePollList();
        tasks = new UniqueTaskList();
    }

    public ClubBook() {}

    /**
     * Creates an ClubBook using the Members and Tags in the {@code toBeCopied}
     */
    public ClubBook(ReadOnlyClubBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }


    //// list overwrite operations
    public void setMembers(List<Member> members) throws DuplicateMemberException {
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
        setPolls(new HashSet<>(newData.getPollList()));
        setTasks(new HashSet<>(newData.getTaskList()));
        List<Member> syncedMemberList = newData.getMemberList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setMembers(syncedMemberList);
        } catch (DuplicateMemberException e) {
            throw new AssertionError("ClubConnect should not have duplicate members");
        }
    }


    //// member-level operations
    /**
     * Adds a member to the club book.
     * Also checks the new member's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the member to point to those in {@link #tags}.
     *
     * @throws DuplicateMemberException if an equivalent member already exists.
     */
    public void addMember(Member p) throws DuplicateMemberException {
        Member member = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any member
        // in the member list.
        members.add(member);
    }

    /**
     * Removes {@code key} from this {@code ClubBook}.
     * @throws PollNotFoundException if the {@code key} is not in this {@code ClubBook}.
     */
    public boolean removePoll(Poll key) throws PollNotFoundException {
        if (polls.remove(key)) {
            return true;
        } else {
            throw new PollNotFoundException();
        }
    }

    /**
     * Replaces the given member {@code target} in the list with {@code editedMember}.
     * {@code ClubBook}'s tag list will be updated with the tags of {@code editedMember}.
     *
     * @throws DuplicateMemberException if updating the member's details causes the member to be equivalent to
     *      another existing member in the list.
     * @throws MemberNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Member)
     */
    public void updateMember(Member target, Member editedMember)
            throws DuplicateMemberException, MemberNotFoundException {
        requireNonNull(editedMember);

        deleteMemberTags(target);
        Member syncedEditedMember = syncWithMasterTagList(editedMember);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any member
        // in the member list.
        try {
            members.setMember(target, syncedEditedMember);
        } catch (DuplicateMemberException dpe) {
            addTargetMemberTags(target);
            throw new DuplicateMemberException();
        }
    }

    /**
     * Re-adds the tags of {@code target} that were removed from {@code tags}.
     */
    private void addTargetMemberTags(Member target) {
        Set<Tag> allTags = new HashSet<>(tags.asObservableList());
        allTags.addAll(target.getTags());
        tags.setTags(allTags);
    }

    /**
     *  Updates the master tag list to include tags in {@code member} that are not in the list.
     *  @return a copy of this {@code member} such that every tag in this member points to a Tag object in the master
     *  list.
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
     * @throws MemberNotFoundException if the {@code key} is not in this {@code ClubBook}.
     */
    public boolean removeMember(Member key) throws MemberNotFoundException {
        deleteMemberTags(key);
        if (members.remove(key)) {
            return true;
        } else {
            throw new MemberNotFoundException();
        }
    }

    public void setPolls(Set<Poll> polls) {
        this.polls.setPolls(polls);
    }

    public void addPoll(Poll poll) throws DuplicatePollException {
        polls.add(poll);
    }


    //@@author Song Weiyang
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
     * Get the member who is log in, if null, there are no one that is logged in.
     */
    public Member getLoggedInMember() {
        return members.getCurrentlyLogInMember();
    }

    /**
     * Sign up a member if it is a new clubbook
     */
    public void signUpMember(Member p) throws MemberListNotEmptyException {
        Member member = syncWithMasterTagList(p);
        members.signup(member);
    }

    //@@author amrut-prabhu
    /**
     * Removes tags from master tag list {@code tags} that are unique to member {@code member}.
     *
     * @param member Member whose tags may be removed from {@code tags}.
     */
    private void deleteMemberTags(Member member) {
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
    //@@author

    //@@author yash-chowdhary
    /**
     * Removes the Group {@code toRemove} from the Club Book. Every member who was once a part of {@code toRemove}
     * will be assigned the default group - "member".
     */
    public void removeGroup(Group toRemove) throws GroupCannotBeRemovedException, GroupNotFoundException {
        Group notToBeDeleted = new Group("member");
        if (toRemove.equals(notToBeDeleted)) {
            throw new GroupCannotBeRemovedException();
        }
        Boolean isPresent = false;

        for (Member member : members) {
            if (member.getGroup().equals(toRemove)) {
                isPresent = true;
            }
        }
        try {
            for (Member member : members) {
                removeGroupFromMember(toRemove, member);
            }
        } catch (MemberNotFoundException mnfe) {
            throw new AssertionError("Impossible: original member is obtained from the club book.");
        }
        if (!isPresent) {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Removes the Group {@code toRemove} from the {@code member} if the member's group matches the one to be removed.
     */
    private void removeGroupFromMember(Group toRemove, Member member)
            throws MemberNotFoundException {
        if (!member.getGroup().toString().equalsIgnoreCase(toRemove.toString())) {
            return;
        }

        Group defaultGroup = new Group(Group.DEFAULT_GROUP);
        Member newMember = new Member(member.getName(), member.getPhone(), member.getEmail(), member.getMatricNumber(),
                defaultGroup, member.getTags());

        try {
            updateMember(member, newMember);
        } catch (DuplicateMemberException dpe) {
            throw new AssertionError("Deleting a member's group only should not result in a duplicate. "
                    + "See member#equals(Object).");
        }
    }

    /**
     * Adds {@code Task toAdd} to the list of tasks.
     */
    public void addTaskToTaskList(Task taskToAdd) throws DuplicateTaskException {
        tasks.add(taskToAdd);
    }

    public void deleteTask(Task targetTask) throws TaskNotFoundException {
        tasks.remove(targetTask);
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
        } catch (MemberNotFoundException pnfe) {
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
                member.getGroup(), memberTags);
        try {
            updateMember(member, newMember);
        } catch (DuplicateMemberException dpe) {
            throw new AssertionError("Modifying a member's tags only should not result in a duplicate. "
                    + "See member#equals(Object).");
        }
    }
    //@@author

    //// util methods

    @Override
    public String toString() {
        return members.asObservableList().size() + " members, " + tags.asObservableList().size() +  " tags";
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
}
