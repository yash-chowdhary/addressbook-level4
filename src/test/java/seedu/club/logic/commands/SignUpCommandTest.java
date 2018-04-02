package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.logic.commands.exceptions.IllegalExecutionException;
import seedu.club.model.ClubBook;
import seedu.club.model.Model;
import seedu.club.model.ReadOnlyClubBook;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.member.exceptions.DuplicateMemberException;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.poll.Poll;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskCannotBeDeletedException;
import seedu.club.model.task.exceptions.TaskNotFoundException;
import seedu.club.model.task.exceptions.TasksAlreadyListedException;
import seedu.club.model.task.exceptions.TasksCannotBeDisplayedException;
import seedu.club.testutil.MemberBuilder;



public class SignUpCommandTest {
    private Member member = new MemberBuilder().build();

    @Test
    public void executeMemberSuccessfullySigningUp() throws CommandException {
        ModelStubAcceptingMemberSignUp modelStubAcceptingMemberSignUp = new ModelStubAcceptingMemberSignUp();
        CommandResult commandResult = getSignUpCommandForMember(member, modelStubAcceptingMemberSignUp).execute();
        assertEquals(SignUpCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
    }

    /**
     * Generates a new LogInCommand with the details of the given member.
     */
    private SignUpCommand getSignUpCommandForMember(Member member, Model model) {
        SignUpCommand command = new SignUpCommand(member);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
        public void addMember(Member member) throws DuplicateMemberException {
            fail("This method should not be called.");
        }

        @Override
        public void viewMyTasks() throws TasksAlreadyListedException {
            fail("This method should not be called");
        }

        @Override
        public void removeGroup(Group toRemove) {
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
        public void assignTask(Task toAdd, Name name) throws MemberNotFoundException, DuplicateTaskException,
                IllegalExecutionException {
            fail("This method should not be called");
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
        public void deleteMember(Member target) throws MemberNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateMember(Member target, Member editedMember)
                throws DuplicateMemberException {
            fail("This method should not be called.");
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
        public void exportClubConnectMembers(File exportFile) throws IOException {
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
        public void signUpMember(Member member) {
            fail("This method should not be called");
            return;
        }

        @Override
        public void viewAllTasks() throws TasksCannotBeDisplayedException {
            fail("This method should not be called");
            return;
        }
    }
    /**
     * A Model stub that always accept the member being added.
     */
    private class ModelStubAcceptingMemberSignUp extends ModelStub {
        private ReadOnlyClubBook clubBook = new ClubBook();
        @Override
        public void signUpMember(Member member) {
            requireNonNull(member);
        }

        @Override
        public ReadOnlyClubBook getClubBook() {
            return clubBook;
        }
    }
}
