package seedu.club.logic.commands;
// @@author yash-chowdhary
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.club.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.club.testutil.TypicalMembers.BENSON;
import static seedu.club.testutil.TypicalMembers.CARL;
import static seedu.club.testutil.TypicalTasks.BOOK_AUDITORIUM;
import static seedu.club.testutil.TypicalTasks.BUY_CONFETTI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.club.commons.core.index.Index;
import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.ClubBook;
import seedu.club.model.Model;
import seedu.club.model.ReadOnlyClubBook;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.Email;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.member.Phone;
import seedu.club.model.member.exceptions.DataToChangeIsNotCurrentlyLoggedInMemberException;
import seedu.club.model.member.exceptions.DuplicateMatricNumberException;
import seedu.club.model.member.exceptions.MemberListNotEmptyException;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.member.exceptions.PasswordIncorrectException;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.exceptions.DuplicatePollException;
import seedu.club.model.poll.exceptions.PollNotFoundException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskAlreadyAssignedException;
import seedu.club.model.task.exceptions.TaskCannotBeDeletedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;
import seedu.club.model.task.exceptions.TasksAlreadyListedException;
import seedu.club.testutil.TaskBuilder;

public class AssignTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AssignTaskCommand(null, null);
    }

    @Test
    public void execute_taskAcceptedByModel_assignSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();
        MatricNumber validMatricNumber = new MatricNumber(BENSON.getMatricNumber().toString());

        CommandResult commandResult =  getAssignTaskCommandForTask(validTask, validMatricNumber, modelStub)
                .executeUndoableCommand();

        assertEquals(String.format(AssignTaskCommand.MESSAGE_SUCCESS, validMatricNumber), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateTaskException();
        Task validTask = new TaskBuilder().build();
        MatricNumber validMatricNumber = new MatricNumber(BENSON.getMatricNumber().toString());

        thrown.expect(CommandException.class);
        thrown.expectMessage(AssignTaskCommand.MESSAGE_DUPLICATE_TASK);

        getAssignTaskCommandForTask(validTask, validMatricNumber, modelStub).executeUndoableCommand();
    }

    @Test
    public void execute_memberNotFound_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingMemberNotFoundException();
        Task validTask = new TaskBuilder().build();
        MatricNumber validMatricNumber = new MatricNumber(BENSON.getMatricNumber().toString());

        thrown.expect(CommandException.class);
        thrown.expectMessage(AssignTaskCommand.MESSAGE_MEMBER_NOT_FOUND);

        getAssignTaskCommandForTask(validTask, validMatricNumber, modelStub).executeUndoableCommand();
    }

    @Test
    public void equals() {
        Task firstTask = BOOK_AUDITORIUM;
        Task secondTask = BUY_CONFETTI;
        MatricNumber firstMatricNumber = BENSON.getMatricNumber();
        MatricNumber secondMatricNumber = CARL.getMatricNumber();

        AssignTaskCommand firstAssignTaskCommand = new AssignTaskCommand(firstTask, firstMatricNumber);
        AssignTaskCommand secondAssignTaskCommand = new AssignTaskCommand(secondTask, secondMatricNumber);

        assertTrue(firstAssignTaskCommand.equals(firstAssignTaskCommand));
        assertFalse(firstAssignTaskCommand.equals(null));
        assertFalse(firstAssignTaskCommand.equals(true));

        AssignTaskCommand firstCommandCopy = new AssignTaskCommand(firstTask, firstMatricNumber);
        assertTrue(firstAssignTaskCommand.equals(firstCommandCopy));

        assertFalse(firstAssignTaskCommand.equals(secondAssignTaskCommand));
    }

    /**
     * Generates a new AddTaskCommand with the details of the given task.
     */
    private AssignTaskCommand getAssignTaskCommandForTask(Task task, MatricNumber matricNumber, Model model) {
        AssignTaskCommand command = new AssignTaskCommand(task, matricNumber);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

    //@@author
    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public String voteInPoll(Poll poll, Index answerIndex) {
            fail("This method should not be called");
            return null;
        }
        @Override
        public void changeStatus(Task taskToEdit, Task editedTask) throws TaskNotFoundException,
                DuplicateTaskException {
            fail("This method should not be called");
        }

        @Override
        public void changeAssignee(Task taskToEdit, Task editedTask) throws MemberNotFoundException,
                DuplicateTaskException, TaskAlreadyAssignedException {
            fail("This method should not be called");
        }

        @Override
        public void resetData(ReadOnlyClubBook newData) {
            fail("This method should not be called");
        }

        @Override
        public void removeProfilePhoto() {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyClubBook getClubBook() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void viewAllTasks() throws TasksAlreadyListedException {
            fail("This method should not be called");
        }

        @Override
        public void assignTask(Task toAdd, MatricNumber matricNumber) throws MemberNotFoundException,
                DuplicateTaskException {
            fail("This method should not be called");
        }

        @Override
        public void deleteTask(Task taskToDelete) throws TaskNotFoundException, TaskCannotBeDeletedException {
            fail("This method should not be called");
            return;
        }

        @Override
        public void viewMyTasks() throws TasksAlreadyListedException {
            fail("This should not be called");
        }

        @Override
        public int deleteMember(Member target) throws MemberNotFoundException {
            fail("This method should not be called");
            return -1;
        }

        @Override
        public void addMember(Member member) throws DuplicateMatricNumberException {
            fail("This method should not be called");
            return;
        }

        @Override
        public void signUpMember(Member member) throws MemberListNotEmptyException {
            fail("This method should not be called");
        }

        @Override
        public void clearClubBook() {
            fail("This method should not be called");
        }

        @Override
        public boolean getClearConfirmation() {
            fail("This method should not be called");
            return false;
        }

        @Override
        public void setClearConfirmation(Boolean b) {
            fail("This method should not be called");
        }

        @Override
        public int updateMember(Member target, Member editedMember) throws MemberNotFoundException,
                DuplicateTaskException, DuplicateMatricNumberException {
            fail("This method should not be called");
            return -1;
        }

        @Override
        /** Adds the given poll */
        public void addPoll(Poll poll) throws DuplicatePollException {
            fail("This method should not be called");
        }

        @Override
        /** Deletes the given member. */
        public void deletePoll(Poll poll) throws PollNotFoundException {
            fail("This method should not be called");
        }

        @Override
        public void updateFilteredPollList(Predicate<Poll> predicate) {
            fail("This method should not be called");
        }

        @Override
        public ObservableList<Member> getFilteredMemberList() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public ObservableList<Poll> getFilteredPollList() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void updateFilteredMemberList(Predicate<Member> predicate) {
            fail("This method should not be called");
            return;
        }

        @Override
        public void logsInMember(String username, String password) {
            fail("This method should not be called");
            return;
        }

        @Override
        public Member getLoggedInMember() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void deleteTag(Tag tag) throws TagNotFoundException {
            fail("This method should not be called");
            return;
        }

        @Override
        public void addProfilePhoto(String originalPhotoPath) throws PhotoReadException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Tag> getFilteredTagList() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void updateFilteredTagList(Predicate<Tag> predicate) {
            fail("This method should not be called");
            return;
        }

        @Override
        public void deleteGroup(Group toRemove) throws GroupNotFoundException, GroupCannotBeRemovedException {
            fail("This method should not be called");
            return;
        }

        @Override
        public String generateEmailRecipients(Group group, Tag tag) throws GroupNotFoundException,
                TagNotFoundException {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void sendEmail(String recipients, Client client, Subject subject, Body body) {
            fail("This method should not be called");
            return;
        }

        @Override
        public void logOutMember() {
            fail("This method should not be called");
        }

        @Override
        public void exportClubConnectMembers(File exportFilePath) throws IOException {
            fail("This method should not be called");
        }

        @Override
        public int importMembers(File importFile) throws IOException {
            fail("This method should not be called");
            return 0;
        }

        @Override
        public void addTaskToTaskList(Task toAdd) throws DuplicateTaskException {
            fail("This method should not be called");
            return;
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            fail("This method should not be called");
            return;
        }

        @Override
        public void changePassword(String username, String oldPassword, String newPassword)
                throws PasswordIncorrectException, DataToChangeIsNotCurrentlyLoggedInMemberException {
            fail("This method should not be called");
            return;
        }
    }

    //@@author yash-chowdhary
    /**
     * A Model stub that always throw a DuplicateTaskException when trying to add a task.
     */
    private class ModelStubThrowingDuplicateTaskException extends ModelStub {
        private final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("exco"),
                getTagSet("head"));

        @Override
        public void assignTask(Task toAdd, MatricNumber matricNumber) throws MemberNotFoundException,
                DuplicateTaskException {
            throw new DuplicateTaskException();
        }

        //@@author th14thmusician
        @Override
        public ReadOnlyClubBook getClubBook() {
            ClubBook clubBook = new ClubBook();
            try {
                clubBook.addMember(memberStub);
                clubBook.logInMember("A5215090A", "password");
            } catch (DuplicateMatricNumberException e) {
                e.printStackTrace();
            }
            return clubBook;
        }

        @Override
        public Member getLoggedInMember() {
            return memberStub;
        }
        //@@author
    }

    /**
     * A Model stub that always throw a DuplicateTaskException when trying to add a task.
     */
    private class ModelStubThrowingMemberNotFoundException extends ModelStub {
        private final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("exco"),
                getTagSet("head"));

        @Override
        public void assignTask(Task toAdd, MatricNumber matricNumber) throws MemberNotFoundException,
                DuplicateTaskException {
            throw new MemberNotFoundException();
        }

        //@@author th14thmusician
        @Override
        public ReadOnlyClubBook getClubBook() {
            ClubBook clubBook = new ClubBook();
            try {
                clubBook.addMember(memberStub);
                clubBook.logInMember("A5215090A", "password");
            } catch (DuplicateMatricNumberException e) {
                e.printStackTrace();
            }
            return clubBook;
        }

        @Override
        public Member getLoggedInMember() {
            return memberStub;
        }
        //@@author
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();
        private final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("exco"),
                getTagSet("head"));

        @Override
        public void assignTask(Task toAdd, MatricNumber matricNumber) throws MemberNotFoundException,
                DuplicateTaskException {
            requireAllNonNull(toAdd, matricNumber);
            tasksAdded.add(toAdd);
        }

        //@@author th14thmusician
        @Override
        public ReadOnlyClubBook getClubBook() {
            ClubBook clubBook = new ClubBook();
            try {
                clubBook.addMember(memberStub);
                clubBook.logInMember("A5215090A", "password");
            } catch (DuplicateMatricNumberException e) {
                e.printStackTrace();
            }
            return clubBook;
        }

        @Override
        public Member getLoggedInMember() {
            return memberStub;
        }
        //@@author
    }

}
