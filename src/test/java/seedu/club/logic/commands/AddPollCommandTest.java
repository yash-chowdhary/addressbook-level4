package seedu.club.logic.commands;
//@@author MuhdNurKamal
import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import javafx.collections.transformation.FilteredList;
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
import seedu.club.model.member.exceptions.DuplicateMatricNumberException;
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
import seedu.club.testutil.PollBuilder;

public class AddPollCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPoll_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddPollCommand(null);
    }

    @Test
    public void execute_pollAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPollAdded modelStub = new ModelStubAcceptingPollAdded();
        Poll validPoll = new PollBuilder().build();

        CommandResult commandResult = getAddPollCommandForPoll(validPoll, modelStub).execute();

        assertEquals(String.format(AddPollCommand.MESSAGE_SUCCESS, validPoll), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validPoll), modelStub.pollsAdded);
    }

    @Test
    public void execute_duplicatePoll_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePollException();
        Poll validPoll = new PollBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddPollCommand.MESSAGE_DUPLICATE_POLL);

        getAddPollCommandForPoll(validPoll, modelStub).execute();
    }

    @Test
    public void equals() {
        Poll lovePoll = new PollBuilder().withQuestion("What is love?").build();
        Poll lifePoll = new PollBuilder().withQuestion("What is life?").build();
        AddPollCommand addLovePollCommand = new AddPollCommand(lovePoll);
        AddPollCommand addLifePollCommand = new AddPollCommand(lifePoll);

        // same object -> returns true
        assertTrue(addLovePollCommand.equals(addLovePollCommand));

        // same values -> returns true
        AddPollCommand addAliceCommandCopy = new AddPollCommand(lovePoll);
        assertTrue(addLovePollCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addLovePollCommand.equals(1));

        // null -> returns false
        assertFalse(addLovePollCommand.equals(null));

        // different poll -> returns false
        assertFalse(addLovePollCommand.equals(addLifePollCommand));
    }

    /**
     * Generates a new AddPollCommand with the details of the given poll.
     */
    private AddPollCommand getAddPollCommandForPoll(Poll poll, Model model) {
        AddPollCommand command = new AddPollCommand(poll);
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
        public void exportClubConnectMembers(File exportFilePath) {
            fail("This method should not be called.");
        }

        @Override
        public void viewAllTasks() throws TasksAlreadyListedException {
            fail("This method should not be called");
        }

        @Override
        public void viewMyTasks() throws TasksAlreadyListedException {
            fail("This method should not be called");
        }

        @Override
        public void assignTask(Task toAdd, MatricNumber matricNumber) throws MemberNotFoundException,
                DuplicateTaskException {
            fail("This method should not be called");
        }

        @Override
        public void logOutMember() {
            fail("This method should not be called.");
        }

        @Override
        public void logsInMember(String username, String password) {
            fail("This method should not be called");
        }


        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void changePassword(String username,
                                   String oldPassword, String newPassword)
                throws PasswordIncorrectException {
            fail("This method should not be called.");
        }

        public void signUpMember(Member member) {
            fail("This method should not be called");
            return;
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
        public FilteredList<Member> getFilteredMemberList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void addMember(Member member) throws DuplicateMatricNumberException {
            fail("This method should not be called.");
        }

        @Override
        public void addPoll(Poll poll) throws DuplicatePollException {
            fail("This method should not be called.");
        }

        @Override
        public void deletePoll(Poll target) throws PollNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addProfilePhoto(String originalPhotoPath) throws PhotoReadException {
            fail("This method should not be called.");
        }

        @Override
        public void removeProfilePhoto() {
            fail("This method should not be called.");
        }

        @Override
        public Member getLoggedInMember() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteGroup(Group toRemove) throws GroupNotFoundException, GroupCannotBeRemovedException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyClubBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyClubBook getClubBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public int deleteMember(Member member) throws MemberNotFoundException {
            fail("This method should not be called.");
            return -1;
        }

        @Override
        public int updateMember(Member member, Member editedMember)
                throws DuplicateMatricNumberException {
            fail("This method should not be called.");
            return -1;
        }

        @Override
        public void deleteTag(Tag tag) throws TagNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Poll> getFilteredPollList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredMemberList(Predicate<Member> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredPollList(Predicate<Poll> poll) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredTagList(Predicate<Tag> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Tag> getFilteredTagList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void sendEmail(String recipients, Client client, Subject subject, Body body) {
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
        public int importMembers(File importFile) throws IOException {
            fail("This method should not be called");
            return 0;
        }

        @Override
        public void addTaskToTaskList(Task toAdd) throws DuplicateTaskException {
            fail("This method should not be called");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void deleteTask(Task taskToDelete) throws TaskNotFoundException, TaskCannotBeDeletedException {
            fail("This method should not be called");
        }
    }

    /**
     * A Model stub that always throw a DuplicatePollException when trying to add a poll.
     */
    private class ModelStubThrowingDuplicatePollException extends ModelStub {
        private final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("exco"),
                getTagSet("head"));

        @Override
        public void addPoll(Poll poll) throws DuplicatePollException {
            throw new DuplicatePollException();
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
     * A Model stub that always accept the poll being added.
     */
    private class ModelStubAcceptingPollAdded extends ModelStub {
        private final ArrayList<Poll> pollsAdded = new ArrayList<>();
        private final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("exco"),
                getTagSet("head"));

        @Override
        public void addPoll(Poll poll) throws DuplicatePollException {
            requireNonNull(poll);
            pollsAdded.add(poll);
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
