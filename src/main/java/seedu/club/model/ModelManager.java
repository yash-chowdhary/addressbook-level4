package seedu.club.model;

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.club.storage.ProfilePhotoStorage.PHOTO_FILE_EXTENSION;
import static seedu.club.storage.ProfilePhotoStorage.SAVE_PHOTO_DIRECTORY;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.club.commons.core.ComponentManager;
import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.core.index.Index;
import seedu.club.commons.events.model.ClubBookChangedEvent;
import seedu.club.commons.events.model.NewExportDataAvailableEvent;
import seedu.club.commons.events.model.ProfilePhotoChangedEvent;
import seedu.club.commons.events.ui.SendEmailRequestEvent;
import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.commons.util.CsvUtil;
import seedu.club.logic.commands.ViewMyTasksCommand;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.UniqueMemberList;
import seedu.club.model.member.exceptions.DataToChangeIsNotCurrentlyLoggedInMemberException;
import seedu.club.model.member.exceptions.DuplicateMatricNumberException;
import seedu.club.model.member.exceptions.MemberListNotEmptyException;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.member.exceptions.PasswordIncorrectException;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.PollIsRelevantToMemberPredicate;
import seedu.club.model.poll.exceptions.AnswerNotFoundException;
import seedu.club.model.poll.exceptions.DuplicatePollException;
import seedu.club.model.poll.exceptions.PollNotFoundException;
import seedu.club.model.poll.exceptions.UserAlreadyVotedException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;
import seedu.club.model.task.Assignee;
import seedu.club.model.task.Assignor;
import seedu.club.model.task.Status;
import seedu.club.model.task.Task;
import seedu.club.model.task.TaskIsRelatedToMemberPredicate;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskCannotBeDeletedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;
import seedu.club.model.task.exceptions.TasksAlreadyListedException;
import seedu.club.model.task.exceptions.TasksCannotBeDisplayedException;
import seedu.club.storage.CsvClubBookStorage;

/**
 * Represents the in-memory model of the club book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ClubBook clubBook;
    private final FilteredList<Member> filteredMembers;
    private final FilteredList<Tag> filteredTags;
    private final FilteredList<Poll> filteredPolls;
    private final FilteredList<Task> filteredTasks;
    private boolean isConfirmedClear;

    /**
     * Initializes a ModelManager with the given clubBook and userPrefs.
     */
    public ModelManager(ReadOnlyClubBook clubBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(clubBook, userPrefs);

        logger.fine("Initializing with club book: " + clubBook + " and user prefs " + userPrefs);

        this.clubBook = new ClubBook(clubBook);
        isConfirmedClear = false;
        filteredMembers = new FilteredList<>(this.clubBook.getMemberList());
        filteredTags = new FilteredList<>(this.clubBook.getTagList());
        filteredPolls = new FilteredList<>(this.clubBook.getPollList());
        filteredTasks = new FilteredList<>(this.clubBook.getTaskList());
        updateFilteredMemberList(PREDICATE_NOT_SHOW_ALL_MEMBERS);
        updateFilteredTaskList(PREDICATE_NOT_SHOW_ALL_TASKS);
        updateFilteredPollList(new PollIsRelevantToMemberPredicate(getLoggedInMember()));
    }

    public ModelManager() {
        this(new ClubBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyClubBook newData) {
        clubBook.resetData(newData);
        indicateClubBookChanged();
    }

    @Override
    public ReadOnlyClubBook getClubBook() {
        return clubBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateClubBookChanged() {
        raise(new ClubBookChangedEvent(clubBook));
    }

    @Override
    public synchronized void deleteMember(Member target) throws MemberNotFoundException {
        clubBook.removeMember(target);
        filteredMembers.remove(target);
        indicateClubBookChanged();
    }

    @Override
    public synchronized void addMember(Member member) throws DuplicateMatricNumberException {
        clubBook.addMember(member);
        updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        indicateClubBookChanged();
    }

    @Override
    public void updateMember(Member target, Member editedMember)
            throws DuplicateMatricNumberException, MemberNotFoundException {
        requireAllNonNull(target, editedMember);
        clubBook.updateMember(target, editedMember);
        indicateClubBookChanged();
    }

    //@@author MuhdNurKamal
    @Override
    public synchronized void addPoll(Poll poll) throws DuplicatePollException {
        clubBook.addPoll(poll);
        updateFilteredPollList(new PollIsRelevantToMemberPredicate(getLoggedInMember()));
        indicateClubBookChanged();
    }

    @Override
    public void voteInPoll(Poll poll, Index answerIndex)
            throws PollNotFoundException, AnswerNotFoundException, UserAlreadyVotedException {
        requireAllNonNull(poll, answerIndex);

        clubBook.voteInPoll(poll, answerIndex, getLoggedInMember().getMatricNumber());
        indicateClubBookChanged();
    }

    @Override
    public synchronized void deletePoll(Poll target) throws PollNotFoundException {
        clubBook.removePoll(target);
        indicateClubBookChanged();
    }
    //@@author

    //@@author Song Weiyang
    @Override
    public void logsInMember(String username, String password) {
        requireAllNonNull(username, password);
        clubBook.logInMember(username, password);
        if (getLoggedInMember() != null) {
            updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
            updateFilteredPollList(new PollIsRelevantToMemberPredicate(getLoggedInMember()));
            updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(getLoggedInMember()));
        }
    }

    //@@author Song Weiyang
    @Override
    public Member getLoggedInMember() {
        return clubBook.getLoggedInMember();
    }

    //@@author amrut-prabhu
    /** Raises an event to indicate the profile photo of a member has changed */
    private void indicateProfilePhotoChanged(String originalPath, String newFileName) throws PhotoReadException {
        ProfilePhotoChangedEvent profilePhotoChangedEvent = new ProfilePhotoChangedEvent(originalPath, newFileName);
        raise(profilePhotoChangedEvent);
        if (!profilePhotoChangedEvent.isPhotoChanged()) {
            throw new PhotoReadException();
        }
    }

    @Override
    public void addProfilePhoto(String originalPhotoPath) throws PhotoReadException {
        requireNonNull(originalPhotoPath);

        String newFileName = getLoggedInMember().getMatricNumber().toString();
        indicateProfilePhotoChanged(originalPhotoPath, newFileName);
        String newProfilePhotoPath = SAVE_PHOTO_DIRECTORY + newFileName + PHOTO_FILE_EXTENSION;

        getLoggedInMember().setProfilePhotoPath(newProfilePhotoPath);

        updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        indicateClubBookChanged();
    }

    //@@author yash-chowdhary
    @Override
    public void removeGroup(Group toRemove) throws GroupNotFoundException, GroupCannotBeRemovedException {
        requireNonNull(toRemove);

        clubBook.removeGroup(toRemove);
        indicateClubBookChanged();
    }

    //@@author amrut-prabhu
    @Override
    public void deleteTag(Tag tag) throws TagNotFoundException {
        clubBook.deleteTag(tag);
        updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        indicateClubBookChanged();
    }

    //@@author yash-chowdhary
    @Override
    public String generateEmailRecipients(Group group, Tag tag) throws GroupNotFoundException, TagNotFoundException {
        if (group != null) {
            return generateGroupEmailRecipients(group);
        }
        return generateTagEmailRecipients(tag);
    }

    /**
     * Generates recipient list of all members part of {@code Tag toSendEmailTo}
     * @throws TagNotFoundException if {@code Tag toSendEmailTo} doesn't exist in the club book
     */
    private String generateTagEmailRecipients(Tag toSendEmailTo) throws TagNotFoundException {
        List<Member> members = new ArrayList<>(clubBook.getMemberList());

        List<String> emailRecipients = new ArrayList<>();
        checkIfTagExists(toSendEmailTo, members, emailRecipients);

        return String.join(",", emailRecipients);
    }

    /**
     * Checks if the {@code Tag toSendEmailTo} exists in Club Connect.
     * @throws TagNotFoundException if {@code toSendEmailTo} is not found.
     */
    private void checkIfTagExists(Tag toSendEmailTo, List<Member> members, List<String> emailRecipients)
            throws TagNotFoundException {
        Boolean tagFound = false;
        for (Member member : members) {
            Set<Tag> memberTags = member.getTags();
            if (memberTags.contains(toSendEmailTo)) {
                emailRecipients.add(member.getEmail().toString());
                tagFound = true;
            }
        }
        if (!tagFound) {
            throw new TagNotFoundException();
        }
    }

    /**
     * Generates recipient list of all members part of {@code Group toSendEmailTo}
     * @throws GroupNotFoundException if {@code Group toSendEmailTo} doesn't exist in the club book
     */
    private String generateGroupEmailRecipients(Group toSendEmailTo) throws GroupNotFoundException {
        List<Member> members = new ArrayList<>(clubBook.getMemberList());

        List<String> emailRecipients = new ArrayList<>();
        checkIfGroupExists(toSendEmailTo, members, emailRecipients);
        return String.join(",", emailRecipients);
    }

    /**
     * Checks if the {@code Group toSendEmailTo} exists in Club Connect.
     * @throws GroupNotFoundException if {@code toSendEmailTo} is not found.
     */
    private void checkIfGroupExists(Group toSendEmailTo, List<Member> members, List<String> emailRecipients)
            throws GroupNotFoundException {
        Boolean groupFound = false;
        for (Member member : members) {
            if (member.getGroup().equals(toSendEmailTo)) {
                emailRecipients.add(member.getEmail().toString());
                groupFound = true;
            }
        }
        if (!groupFound) {
            throw new GroupNotFoundException();
        }
    }

    @Override
    public void sendEmail(String recipients, Client client, Subject subject, Body body) {
        raise(new SendEmailRequestEvent(recipients, subject, body, client));
    }

    @Override
    public void changeStatus(Task taskToEdit, Task editedTask) throws TaskNotFoundException,
            DuplicateTaskException {
        requireAllNonNull(taskToEdit, editedTask);
        clubBook.updateTask(taskToEdit, editedTask);
        updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(getLoggedInMember()));
        indicateClubBookChanged();
    }


    //@@author Song Weiyang
    @Override
    public void logOutMember() {
        clubBook.logOutMember();
    }

    //@@author yash-chowdhary
    @Override
    public void addTaskToTaskList(Task toAdd) throws DuplicateTaskException {
        try {
            Assignor assignor = new Assignor(clubBook.getLoggedInMember().getMatricNumber().toString());
            Assignee assignee = new Assignee(clubBook.getLoggedInMember().getMatricNumber().toString());
            Status status = new Status(Status.NOT_STARTED_STATUS);
            toAdd.setAssignor(assignor);
            toAdd.setAssignee(assignee);
            toAdd.setStatus(status);
            clubBook.addTaskToTaskList(toAdd);
            updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(getLoggedInMember()));
            indicateClubBookChanged();
        } catch (DuplicateTaskException dte) {
            throw new DuplicateTaskException();
        }
    }

    @Override
    public void assignTask(Task toAdd, MatricNumber matricNumber) throws MemberNotFoundException,
            DuplicateTaskException {
        checkIfMemberExists(matricNumber);
        try {
            Assignor assignor = new Assignor(clubBook.getLoggedInMember().getMatricNumber().toString());
            Assignee assignee = new Assignee(matricNumber.toString());
            Status status = new Status(Status.NOT_STARTED_STATUS);
            toAdd.setAssignor(assignor);
            toAdd.setAssignee(assignee);
            toAdd.setStatus(status);
            clubBook.addTaskToTaskList(toAdd);
            updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(getLoggedInMember()));
            indicateClubBookChanged();
        } catch (DuplicateTaskException dte) {
            throw new DuplicateTaskException();
        }
    }

    /**
     * Checks if the {@code MatricNumber matricNumber} maps to any member in Club Connect.
     * @throws MemberNotFoundException if {@code matricNumber} doesn't map to any member.
     */
    private void checkIfMemberExists(MatricNumber matricNumber) throws MemberNotFoundException {
        boolean found = false;
        for (Member member : clubBook.getMemberList()) {
            if (member.getMatricNumber().equals(matricNumber)) {
                found = true;
            }
        }
        if (!found) {
            throw new MemberNotFoundException();
        }
    }

    @Override
    public void deleteTask(Task targetTask) throws TaskNotFoundException, TaskCannotBeDeletedException {
        Assignor assignor = targetTask.getAssignor();
        Assignee assignee = targetTask.getAssignee();
        String currentMember = getLoggedInMember().getMatricNumber().toString();
        checkIfTaskCanBeDeleted(assignor, assignee, currentMember);
        clubBook.deleteTask(targetTask);
        indicateClubBookChanged();
    }

    /**
     * Checks if the {@code String currentMember} is either the {@code assignor} or the {@code assignee} of the task.
     * @throws TaskCannotBeDeletedException if the task cannot be deleted.
     */
    private void checkIfTaskCanBeDeleted(Assignor assignor, Assignee assignee, String currentMember)
            throws TaskCannotBeDeletedException {
        if (!currentMember.equalsIgnoreCase(assignor.getAssignor())
                && !currentMember.equalsIgnoreCase(assignee.getAssignee())) {
            throw new TaskCannotBeDeletedException();
        }
    }

    @Override
    public void viewAllTasks() throws TasksCannotBeDisplayedException {
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateClubBookChanged();
    }

    @Override
    public void viewMyTasks() throws TasksAlreadyListedException {
        checkIfTasksAreAlreadyListed();
        updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(getLoggedInMember()));
    }

    private void checkIfTasksAreAlreadyListed() throws TasksAlreadyListedException {
        if (filteredTasks.getPredicate().equals(new TaskIsRelatedToMemberPredicate(getLoggedInMember()))) {
            throw new TasksAlreadyListedException(ViewMyTasksCommand.MESSAGE_ALREADY_LISTED);
        }
    }

    //@@author amrut-prabhu
    @Override
    public int importMembers(File importFile) throws IOException {
        CsvClubBookStorage storage = new CsvClubBookStorage(importFile);
        UniqueMemberList importedMembers = storage.readClubBook();
        int numberMembers = 0;

        for (Member member: importedMembers) {
            try {
                clubBook.addMember(member);
                numberMembers++;
            } catch (DuplicateMatricNumberException dmne) {
                logger.info("DuplicateMemberException encountered due to " + member);
            }
        }
        indicateClubBookChanged();
        return numberMembers;
    }

    @Override
    public void exportClubConnectMembers(File exportFile) throws IOException {
        requireNonNull(exportFile);
        indicateNewExport(exportFile);

        exportHeaders(exportFile);
        List<Member> members = new ArrayList<>(clubBook.getMemberList());

        for (Member member: members) {
            exportMember(member);
        }
    }

    /**
     * Raises a {@code NewMemberAvailableEvent} to indicate that new data is ready to be exported.
     *
     * @param data Member data to be added to the file.
     * @throws IOException if there was an error writing to file.
     */
    private void indicateNewExport(String data) throws IOException {
        NewExportDataAvailableEvent newExportDataAvailableEvent = new NewExportDataAvailableEvent(data);
        raise(newExportDataAvailableEvent);
        if (!newExportDataAvailableEvent.isFileChanged()) {
            throw new IOException();
        }
    }

    /**
     * Raises a {@code NewMemberAvailableEvent} to indicate that data is to be written to {@code exportFile}.
     *
     * @param exportFile CSV file to be exported to.
     * @throws IOException if there was an error writing to file.
     */
    private void indicateNewExport(File exportFile) throws IOException {
        NewExportDataAvailableEvent newExportDataAvailableEvent = new NewExportDataAvailableEvent(exportFile);
        raise(newExportDataAvailableEvent);
        if (!newExportDataAvailableEvent.isFileChanged()) {
            throw new IOException();
        }
    }

    /**
     * Returns true if {@code file} is empty.
     */
    private boolean isEmptyFile(File file) {
        return file.length() == 0;
    }

    /**
     * Exports the header fields of {@code Member} object if the file is empty.
     */
    private void exportHeaders(File exportFile) throws IOException {
        if (isEmptyFile(exportFile)) {
            String headers = CsvUtil.getHeaders();
            indicateNewExport(headers);
        }
    }

    /**
     * Exports the information of {@code member} to the file.
     *
     * @param member Member whose data is to be exported.
     */
    private void exportMember(Member member) throws IOException {
        String memberData = convertMemberToCsv(member);
        indicateNewExport(memberData);
    }

    /**
     * Returns the CSV representation of {@code member}.
     *
     * @param member Member who is to be converted to CSV format.
     * @return Member data in CSV format.
     */
    private String convertMemberToCsv(Member member) {
        return CsvUtil.toCsvFormat(member);
    }

    //@@author Song Weiyang
    /**
     * Changes the password of {@code member} in the clubBook
      * @param username
     * @param oldPassword
     * @param newPassword
     */
    public void changePassword (String username, String oldPassword, String newPassword)
            throws PasswordIncorrectException, DataToChangeIsNotCurrentlyLoggedInMemberException {
        clubBook.changePassword(username, oldPassword, newPassword);
        indicateClubBookChanged();
    }

    //@@author amrut-prabhu
    //=========== Filtered Tag List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Tag} backed by the internal list of
     * {@code clubBook}
     */
    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return FXCollections.unmodifiableObservableList(filteredTags);
    }

    @Override
    public void updateFilteredTagList(Predicate<Tag> predicate) {
        requireNonNull(predicate);
        filteredTags.setPredicate(predicate);
    }

    //@@author
    //=========== Filtered member List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code member} backed by the internal list of
     * {@code clubBook}
     */
    @Override
    public ObservableList<Member> getFilteredMemberList() {
        return FXCollections.unmodifiableObservableList(filteredMembers);
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return FXCollections.unmodifiableObservableList(filteredTasks);
    }

    @Override
    public void updateFilteredMemberList(Predicate<Member> predicate) {
        requireNonNull(predicate);
        filteredMembers.setPredicate(predicate);
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    //@@author Song Weiyang
    @Override
    public void signUpMember(Member member) throws MemberListNotEmptyException {
        clubBook.signUpMember(member);
        filteredMembers.setPredicate(PREDICATE_NOT_SHOW_ALL_MEMBERS);
        indicateClubBookChanged();
    }
    //@@author

    @Override
    public void clearClubBook() {
        clubBook.clearClubBook();
        setClearConfirmation(false);
        indicateClubBookChanged();
    }

    @Override
    public boolean getClearConfirmation() {
        return isConfirmedClear;
    }

    @Override
    public void setClearConfirmation(Boolean b) {
        isConfirmedClear = b;
    }


    @Override
    public ObservableList<Poll> getFilteredPollList() {
        return FXCollections.unmodifiableObservableList(filteredPolls);
    }

    @Override
    public void updateFilteredPollList(Predicate<Poll> predicate) {
        requireNonNull(predicate);
        filteredPolls.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return clubBook.equals(other.clubBook)
                && filteredMembers.equals(other.filteredMembers);
    }
}
