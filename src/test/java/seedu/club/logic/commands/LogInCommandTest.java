package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Test;

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
import seedu.club.model.member.Email;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.member.Phone;
import seedu.club.model.member.exceptions.DuplicateMatricNumberException;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.member.exceptions.PasswordIncorrectException;
import seedu.club.model.poll.Poll;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskAlreadyAssignedException;
import seedu.club.model.task.exceptions.TaskCannotBeDeletedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;
import seedu.club.model.task.exceptions.TasksAlreadyListedException;

public class LogInCommandTest {
    private final Member member = new Member(new Name("Alex Yeoh"),
            new Phone("87438807"), new Email("alexyeoh@example.com"),
            new MatricNumber("A5215090A"), new Group("logistics"),
            getTagSet("head"));

    @Test
    public void executeMemberSuccessfullyLogIn() throws CommandException {
        ModelStubAcceptingMemberLoggingIn modelStubAcceptingMemberLoggingIn = new ModelStubAcceptingMemberLoggingIn();
        CommandResult commandResult = getLogInCommandForMember(member, modelStubAcceptingMemberLoggingIn).execute();
        assertEquals(String.format(LogInCommand.MESSAGE_SUCCESS, member.getName().toString()),
                commandResult.feedbackToUser);
    }

    @Test
    public void executeMemberUnsuccessfullyLogIn() throws CommandException {
        ModelStubRejectingMemberLoggingIn modelStubRejectingMemberLoggingIn = new ModelStubRejectingMemberLoggingIn();
        CommandResult commandResult = getLogInCommandForMember(member, modelStubRejectingMemberLoggingIn).execute();
        assertEquals(LogInCommand.MESSAGE_FAILURE, commandResult.feedbackToUser);
    }

    /**
     * Generates a new LogInCommand with the details of the given member.
     */
    private LogInCommand getLogInCommandForMember(Member member, Model model) {
        LogInCommand command = new LogInCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
    /**
     * Returns a tag set containing the list of strings given.
     */
    private static Set<Tag> getTagSet(String... strings) {
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
        public void updateFilteredPollList(Predicate<Poll> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void addPoll(Poll poll) {
            fail("This method should not be called.");
        }

        @Override
        public void deletePoll(Poll poll) {
            fail("This method should not be called.");
        }

        @Override
        public void addMember(Member member) throws DuplicateMatricNumberException {
            fail("This method should not be called.");
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
        public void viewMyTasks() throws TasksAlreadyListedException {
            fail("This method should not be called");
        }

        @Override
        public void deleteGroup(Group toRemove) {
            fail("This method should not be called.");
        }

        @Override
        public String generateEmailRecipients(Group group, Tag tag) {
            return null;
        }

        @Override
        public void sendEmail(String recipients, Client client, Subject subject, Body body) {

        }

        @Override
        public void addTaskToTaskList(Task toAdd) throws DuplicateTaskException {

        }

        @Override
        public void deleteTask(Task taskToDelete) throws TaskNotFoundException, TaskCannotBeDeletedException {

        }

        @Override
        public void logOutMember() {
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
        public int deleteMember(Member target) throws MemberNotFoundException {
            fail("This method should not be called.");
            return -1;
        }

        @Override
        public int updateMember(Member target, Member editedMember)
                throws DuplicateMatricNumberException {
            fail("This method should not be called.");
            return -1;
        }

        @Override
        public int importMembers(File importFile) throws IOException {
            fail("This method should not be called");
            return 0;
        }

        @Override
        public void deleteTag(Tag tag) throws TagNotFoundException {
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
        public void exportClubConnectMembers(File exportFile) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Member> getFilteredMemberList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Poll> getFilteredPollList() {
            return null;
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            return null;
        }

        @Override
        public void updateFilteredMemberList(Predicate<Member> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void logsInMember(String username, String password) {
            fail("This method should not be called");
        }

        @Override
        public Member getLoggedInMember() {
            return null;
        }

        public void updateFilteredTagList(Predicate<Tag> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Tag> getFilteredTagList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            fail("This method should not be called.");
            return;
        }

        @Override
        public void changePassword(String username, String oldPassword, String newPassword)
                throws PasswordIncorrectException {
            fail("This method should not be called.");
            return;
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
    }
    /**
     * A Model stub that always accept the member being added.
     */
    private class ModelStubAcceptingMemberLoggingIn extends ModelStub {
        private HashMap<String, Member> usernameMemberHashMap = new HashMap<>();
        private HashMap<String, String> usernamePasswordHashMap = new HashMap<>();
        private Member currentlyLoggedIn = null;
        private final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("logistics"),
                getTagSet("head"));

        @Override
        public void logsInMember(String username, String password) {
            requireNonNull(username, password);
            addMember(member);
            Member checkMember = usernameMemberHashMap.get(username);
            if (checkMember != null && usernamePasswordHashMap.get(username).equals(password)) {
                currentlyLoggedIn = checkMember;
            }
        }

        @Override
        public void addMember(Member member) {
            requireNonNull(member);
            usernameMemberHashMap.put(member.getCredentials().getUsername().value, member);
            usernamePasswordHashMap.put(member.getCredentials().getUsername().value,
                    member.getCredentials().getPassword().value);
        }

        //@@author th14thmusician
        @Override
        public ReadOnlyClubBook getClubBook() {
            ClubBook clubBook = new ClubBook();
            try {
                clubBook.addMember(memberStub);
            } catch (DuplicateMatricNumberException e) {
                e.printStackTrace();
            }
            return clubBook;
        }

        @Override
        public Member getLoggedInMember() {
            return currentlyLoggedIn;
        }
        //@@author
    }

    /**
     * A Model stub that always rejects the member to log in.
     */
    private class ModelStubRejectingMemberLoggingIn extends ModelStub {
        private HashMap<String, Member> usernameMemberHashMap = new HashMap<>();
        private HashMap<String, String> usernamePasswordHashMap = new HashMap<>();
        private Member currentlyLoggedIn = null;
        private final Member memberStub = new Member(new Name("Alex Yeoh"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("logistics"),
                getTagSet("head"));

        @Override
        public void logsInMember(String username, String password) {
            requireNonNull(username, password);
            addMember(member);
            Member checkMember = usernameMemberHashMap.get(username);
            if (checkMember != null && usernamePasswordHashMap.get(username).equals(password)) {
                currentlyLoggedIn = checkMember;
            }
        }

        @Override
        public void addMember(Member member) {
            requireNonNull(member);
            usernameMemberHashMap.put(member.getCredentials().getUsername().value, member);
            usernamePasswordHashMap.put(member.getCredentials().getUsername().value,
                    member.getCredentials().getUsername().value); //purposely to have a wrong password
        }

        //@@author th14thmusician
        @Override
        public ReadOnlyClubBook getClubBook() {
            ClubBook clubBook = new ClubBook();
            try {
                clubBook.addMember(memberStub);
            } catch (DuplicateMatricNumberException e) {
                e.printStackTrace();
            }
            return clubBook;
        }

        @Override
        public Member getLoggedInMember() {
            return null;
        }
        //@@author
    }
}

