package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.function.Predicate;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.ClubBook;
import seedu.club.model.Model;
import seedu.club.model.ReadOnlyClubBook;
import seedu.club.model.group.Group;
import seedu.club.model.group.exceptions.GroupCannotBeRemovedException;
import seedu.club.model.group.exceptions.GroupNotFoundException;
import seedu.club.model.member.Member;
import seedu.club.model.member.exceptions.DuplicateMemberException;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;
import seedu.club.testutil.MemberBuilder;




public class LogInCommandTest {
    private Member member = new MemberBuilder().build();

    @Test
    public void execute_memberSuccessfullyLogIn() throws CommandException {
        ModelStubAcceptingMemberLoggingIn modelStubAcceptingMemberLoggingIn = new ModelStubAcceptingMemberLoggingIn();
        CommandResult commandResult = getLogInCommandForMember(member, modelStubAcceptingMemberLoggingIn).execute();
        assertEquals(LogInCommand.MESSAGE_SUCCESS + member.getName().toString(), commandResult.feedbackToUser);
    }

    @Test
    public void execute_memberUnsuccessfullyLogIn() throws CommandException {
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
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addMember(Member member) throws DuplicateMemberException {
            fail("This method should not be called.");
        }

        @Override
        public void removeGroup(Group toRemove) throws GroupNotFoundException, GroupCannotBeRemovedException {
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
        public ObservableList<Member> getFilteredMemberList() {
            fail("This method should not be called.");
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
    }
    /**
     * A Model stub that always accept the member being added.
     */
    private class ModelStubAcceptingMemberLoggingIn extends ModelStub {
        final HashMap<String, Member> usernameMemberHashMap = new HashMap<>();
        final HashMap<String, String> usernamePasswordHashMap = new HashMap<>();
        private Member currentlyLoggedIn = null;

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

        @Override
        public Member getLoggedInMember() {
            return currentlyLoggedIn;
        }

        @Override
        public ReadOnlyClubBook getClubBook() {
            return new ClubBook();
        }
    }

    /**
     * A Model stub that always rejects the member to log in.
     */
    private class ModelStubRejectingMemberLoggingIn extends ModelStub {
        final HashMap<String, Member> usernameMemberHashMap = new HashMap<>();
        final HashMap<String, String> usernamePasswordHashMap = new HashMap<>();
        private Member currentlyLoggedIn = null;

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

        @Override
        public Member getLoggedInMember() {
            return currentlyLoggedIn;
        }

        @Override
        public ReadOnlyClubBook getClubBook() {
            return new ClubBook();
        }
    }
}

