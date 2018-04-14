# th14thmusician
###### \java\seedu\club\commons\events\ui\ClearMemberSelectPanelEvent.java
``` java
import seedu.club.commons.events.BaseEvent;

/**
 * Requesting to clear or logout removing the details in the members panel
 */
public class ClearMemberSelectPanelEvent extends BaseEvent {
    private boolean toClear;

    public ClearMemberSelectPanelEvent (boolean toClear) {
        this.toClear = toClear;
    }

    public boolean isToClear() {
        return toClear;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\club\commons\events\ui\ModifiedTaskPanelSelecetionChangedEvent.java
``` java
import seedu.club.commons.events.BaseEvent;
import seedu.club.ui.ModifiedTaskCard;

/**
 * Represents a change in the Modified Task List Panel
 */
public class ModifiedTaskPanelSelecetionChangedEvent extends BaseEvent {
    private final ModifiedTaskCard newSelection;

    public ModifiedTaskPanelSelecetionChangedEvent(ModifiedTaskCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ModifiedTaskCard getNewSelection() {
        return newSelection;
    }
}
```
###### \java\seedu\club\commons\events\ui\UpdateCurrentlyLogInMemberEvent.java
``` java
import seedu.club.commons.events.BaseEvent;
import seedu.club.model.member.Member;

/**
 * Represent a change in log in member
 */
public class UpdateCurrentlyLogInMemberEvent extends BaseEvent {
    private Member currentlyLogIn;

    public UpdateCurrentlyLogInMemberEvent(Member member) {
        this.currentlyLogIn = member;
    }

    public Member getCurrentlyLogIn() {
        return currentlyLogIn;
    }

    @Override
    public String toString() {
        return null;
    }
}
```
###### \java\seedu\club\commons\events\ui\UpdateSelectionPanelEvent.java
``` java
import seedu.club.commons.events.BaseEvent;
import seedu.club.model.member.Member;
import seedu.club.model.tag.Tag;

/**
 *
 */
public class UpdateSelectionPanelEvent extends BaseEvent {
    private Member toEditMember;
    private Member editedMember;
    private boolean toDelete;
    private Tag tagToDelete;
    private boolean toUndo;

    public UpdateSelectionPanelEvent (Member toEditMember, Member editedMember, boolean toDelete,
                                      Tag removedTag, boolean toUndo) {
        this.toEditMember = toEditMember;
        this.editedMember = editedMember;
        this.toDelete = toDelete;
        this.tagToDelete = removedTag;
        this.toUndo = toUndo;
    }

    public Member getToEditMember() {
        return toEditMember;
    }

    public Member getEditedMember() {
        return editedMember;
    }

    public boolean isToUndo() {
        return toUndo;
    }

    public Tag getTagToDelete() {
        return tagToDelete;
    }

    public boolean isToDelete() {
        return toDelete;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\club\logic\commands\ChangePasswordCommand.java
``` java
/**
 * Changes the password of a member in the ClubBook
 */
public class ChangePasswordCommand extends Command {
    public static final String COMMAND_WORD = "changepass";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "changepw")
    );
    public static final String COMMAND_FORMAT = "changepass u/ pw/ npw/";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes your password.\n"
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "OLD_PASSWORD "
            + PREFIX_NEWPASSWORD + "NEW_PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "A0164589X "
            + PREFIX_PASSWORD + "password "
            + PREFIX_NEWPASSWORD + "iLovecats18";
    public static final String MESSAGE_SUCCESS = "Password changed successfully.";
    public static final String MESSAGE_PASSWORD_INCORRECT = "The old password entered is incorrect.";
    public static final String MESSAGE_AUTHENTICATION_FAILED = "You can only change your own password.";
    public static final String MESSAGE_USERNAME_NOTFOUND = "This username does not exist.";
    private Username username;
    private Password oldPassword;
    private Password newPassword;

    public ChangePasswordCommand (Username username, Password oldPassword, Password newPassword) {
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        try {
            model.changePassword(username.value, oldPassword.value, newPassword.value);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (PasswordIncorrectException e) {
            throw new CommandException(MESSAGE_PASSWORD_INCORRECT);
        } catch (DataToChangeIsNotCurrentlyLoggedInMemberException dataToChangeIsNotCurrentlyLoggedInMemberException) {
            throw new CommandException(MESSAGE_AUTHENTICATION_FAILED);
        } catch (MatricNumberNotFoundException e) {
            throw new CommandException(MESSAGE_USERNAME_NOTFOUND);
        }
    }

    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        super.setData(model, history, undoRedoStack);
    }
}
```
###### \java\seedu\club\logic\commands\ClearCommand.java
``` java
/**
 * Clears the club book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Club Connect has been cleared.";
    public static final String MESSAGE_FAILURE = "Action to clear Club Connect data has been cancelled.";
    public static final String MESSAGE_CONFRIMATION = "Are you sure that you want to clear all data in Club Connect?"
            + " Enter 'clear Y' to confirm and 'clear N' to cancel."
            + " \nWARNING: THIS CANNOT BE UNDONE!";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " Y/N";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "c", "erase")
    );
    private String args;

    public ClearCommand() {
    }

    public ClearCommand(String args) {
        this.args = args;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        if (!model.getClearConfirmation()) {
            model.setClearConfirmation(true);
            return new CommandResult(MESSAGE_CONFRIMATION);
        } else {
            if (args == null) {
                return new CommandResult(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, COMMAND_FORMAT));
            } else if (args.equals(" Y")) {
                model.resetData(new ClubBook());
                model.clearClubBook();
                EventsCenter.getInstance().post(new ClearMemberSelectPanelEvent(true));
                return new CommandResult(MESSAGE_SUCCESS);
            } else {
                model.setClearConfirmation(false);
                return new CommandResult(MESSAGE_FAILURE);
            }
        }
    }
}
```
###### \java\seedu\club\logic\commands\Command.java
``` java
    /**
     * Requires user to login before proceeding
     */
    protected void requireToLogIn () throws CommandException {
        if (model.getLoggedInMember() == null) {
            throw new CommandException(Messages.MESSAGE_REQUIRE_LOG_IN);
        }
    }

    /**
     * Requires user to Sign Up
     */
    protected void requireToSignUp () throws CommandException {
        if (model.getClubBook().getMemberList().isEmpty()) {
            throw new CommandException(Messages.MESSAGE_REQUIRE_SIGN_UP);
        }
    }

    /**
     * Requires exco access to use the command
     */
    protected void requireExcoLogIn () throws CommandException {
        boolean isExco = model.getLoggedInMember().getGroup().toString().equalsIgnoreCase(Group.GROUP_EXCO);
        if (!isExco) {
            throw new CommandException(Messages.MESSAGE_REQUIRE_EXCO_LOG_IN);
        }
    }

    /**
     * Requires user to logout
     */
    protected void requireToLogOut () throws CommandException {
        if (model.getLoggedInMember() != null) {
            throw new CommandException(Messages.MESSAGE_REQUIRE_LOG_OUT);
        }
    }
}
```
###### \java\seedu\club\logic\commands\LogInCommand.java
``` java
/**
 * Logs in a member to ClubConnect
 */
public class LogInCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "signin")
    );

    public static final String COMMAND_FORMAT = "login u/ pw/ ";

    public static final String MESSAGE_SUCCESS = "Hi %1$s. Welcome to Club Connect!";
    public static final String MESSAGE_FAILURE = "Login unsuccessful. Please try again.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows you to log in to Club Connect.\n"
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "A0167855F" + " "
            + PREFIX_PASSWORD + "password";
    private final Username username;
    private final Password password;

    public LogInCommand(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        requireToSignUp();
        requireToLogOut();
        model.logsInMember(username.value, password.value);
        if (model.getLoggedInMember() != null) {
            EventsCenter.getInstance().post(new UpdateCurrentlyLogInMemberEvent(model.getLoggedInMember()));
            return new CommandResult(String.format(MESSAGE_SUCCESS, model.getLoggedInMember().getName().toString()));
        }
        return new CommandResult(MESSAGE_FAILURE);
    }

    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        super.setData(model, history, undoRedoStack);
    }
}
```
###### \java\seedu\club\logic\commands\LogOutCommand.java
``` java
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.ClearMemberSelectPanelEvent;
import seedu.club.commons.events.ui.HideResultsRequestEvent;
import seedu.club.commons.events.ui.UpdateCurrentlyLogInMemberEvent;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;


/**
 * Logs a member out of the clubbook
 */
public class LogOutCommand extends Command {
    public static final String COMMAND_WORD = "logout";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "signout")
    );

    public static final String MESSAGE_SUCCESS = "Logout successful.";

    public LogOutCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        requireToLogIn();
        EventsCenter.getInstance().post(new HideResultsRequestEvent());
        model.logOutMember();
        EventsCenter.getInstance().post(new ClearMemberSelectPanelEvent(true));
        EventsCenter.getInstance().post(new UpdateCurrentlyLogInMemberEvent(null));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        super.setData(model, history, undoRedoStack);
    }
}
```
###### \java\seedu\club\logic\commands\SignUpCommand.java
``` java
/**
 * Signs up a member when the clubbook is empty
 */
public class SignUpCommand extends Command {
    public static final String COMMAND_WORD = "signup";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "register", "enroll")
    );

    public static final String COMMAND_FORMAT = COMMAND_WORD + " n/ p/ e/ m/ [t/]";

    public static final String MESSAGE_SUCCESS = "Sign up successful! Please log in with your matriculation number"
            + " and password to start using Club Connect.";
    public static final String MESSAGE_FAILURE = "Club Connect is already set up. Please log in to start.";
    public static final String MESSAGE_USAGE = "Lets you sign up for Club Connect.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_MATRIC_NUMBER + "MATRIC NUMBER "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_MATRIC_NUMBER + "A0123456H "
            + PREFIX_TAG + "president ";
    private final Member toSignUp;

    public SignUpCommand(Member member) {
        this.toSignUp = member;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.signUpMember(toSignUp);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (MemberListNotEmptyException e) {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        super.setData(model, history, undoRedoStack);
    }
}
```
###### \java\seedu\club\logic\parser\ChangePasswordCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ChangePasswordCommand object
 */
public class ChangePasswordCommandParser implements Parser<ChangePasswordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangePasswordCommand
     * and returns an ChangeCommandCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangePasswordCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD, PREFIX_NEWPASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD, PREFIX_NEWPASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangePasswordCommand.MESSAGE_USAGE));
        }

        try {
            Username username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME)).get();
            Password oldPassword = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD)).get();
            Password newPassword = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_NEWPASSWORD)).get();

            return new ChangePasswordCommand(username, oldPassword, newPassword);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\club\logic\parser\ClearCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser implements Parser<ClearCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * and returns a ClearCommand object for execution.
     */
    public ClearCommand parse(String args) throws ParseException {
        if (args.equals("")) {
            return  new ClearCommand();
        } else {
            return new ClearCommand(args);
        }
    }
}
```
###### \java\seedu\club\logic\parser\LoginCommandParser.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.club.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.member.Password;
import seedu.club.model.member.Username;

/**
 * Parses input arguments and creates a new LogInCommand object
 */
public class LoginCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the LogInCommand
     * and returns an LogInCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LogInCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogInCommand.MESSAGE_USAGE));
        }

        try {
            Username username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME)).get();
            Password password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD)).get();

            return new LogInCommand(username, password);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\club\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String username} into an {@code Username}.
     * Leading and trailing whitespaces will be trimmed.
     *
     */
    public static Username parseUsername(String username) {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        return new Username(trimmedUsername);
    }

    /**
     * Parses a {@code Optional<String> username} into an {@code Optional<Username>} if {@code username} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Username> parseUsername(Optional<String> username) throws IllegalValueException {
        requireNonNull(username);
        return  username.isPresent() ? Optional.of(parseUsername(username.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String password} into an {@code Password}.
     * Leading and trailing whitespaces will be trimmed.
     *
     */
    public static Password parsePassword(String password) {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        return new Password(trimmedPassword);
    }

    /**
     * Parses a {@code Optional<String> password} into an {@code Optional<Passowrd>} if {@code password} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Password> parsePassword(Optional<String> password) {
        return password.isPresent() ? Optional.of(parsePassword(password.get())) : Optional.empty();
    }

```
###### \java\seedu\club\logic\parser\SignUpCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SignUpCommand object
 */
public class SignUpCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the SignUpCommand
     * and returns an SignUpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SignUpCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_MATRIC_NUMBER, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_MATRIC_NUMBER, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignUpCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            MatricNumber matricNumber = ParserUtil.parseMatricNumber(argMultimap.getValue(PREFIX_MATRIC_NUMBER)).get();
            Group group = new Group("exco");
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Member member = new Member(name, phone, email, matricNumber, group, tagList);


            return new SignUpCommand(member);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\club\logic\UndoRedoStack.java
``` java
    /**
     * Clear the stacks if it logouts
     */
    private void clear() {
        redoStack.clear();
        undoStack.clear();
    }
```
###### \java\seedu\club\model\ClubBook.java
``` java
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
```
###### \java\seedu\club\model\ClubBook.java
``` java
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
```
###### \java\seedu\club\model\member\Credentials.java
``` java
/**
 * Stores the username and password for a specific member
 */
public class Credentials {
    private Username username;
    private Password password;

    public Credentials(Username username) {
        this.username = username;
        this.password = new Password("password");
    }

    public Credentials(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    public boolean isValid(String username, String password) {
        return password.equals(this.password.value) && username.equals(this.username.value);
    }

    public Username getUsername() {
        return this.username;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }
}
```
###### \java\seedu\club\model\member\exceptions\DataToChangeIsNotCurrentlyLoggedInMemberException.java
``` java
/**
 * Signals that the operation to change a specific data require the user to be logged in as the data's credential
 */
public class DataToChangeIsNotCurrentlyLoggedInMemberException extends Exception {
}
```
###### \java\seedu\club\model\member\exceptions\MemberListNotEmptyException.java
``` java
/**
 * Signals that the operations could not be proceeded as there are members inside clubconnect
 */
public class MemberListNotEmptyException extends Exception {
}
```
###### \java\seedu\club\model\member\exceptions\PasswordIncorrectException.java
``` java
/**
 * Signals that the operations will result in an incorrect password entered
 */
public class PasswordIncorrectException extends Exception{
}
```
###### \java\seedu\club\model\member\Password.java
``` java
/**
 * Represents a member's password.
 */
public class Password {
    public final String value;

    public Password(String password) {
        this.value = password;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this    //short circuit if same object
                || (other instanceof Password    //handles nulls
                && this.value.equals(((Password) other).value));   //state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\club\model\member\UniqueMemberList.java
``` java
    /**
     * Logs in a member successfully and return a true value
     * @return
     */
    public void logsInMember(String username, String password) {
        Member checkMember = usernameCredentialsHashMap.get(username);
        if (checkMember != null && usernamePasswordHashMap.get(username).equals(password)) {
            currentlyLogInMember = checkMember;
        }
    }

    /**
     * Get the member who is logged in
     * @return
     */
    public Member getCurrentlyLogInMember() {
        return currentlyLogInMember;
    }

    /**
     * Fill the hashmap with username and member, and also username and password
     */
    public void fillHashMap() {
        for (Member anInternalList : internalList) {
            usernameCredentialsHashMap.put(anInternalList.getCredentials().getUsername().value, anInternalList);
            usernamePasswordHashMap.put(anInternalList.getCredentials().getUsername().value,
                    anInternalList.getCredentials().getPassword().value);
        }
    }

    /**
     Sort the list according to alphabetical order
     */
    public void sort() {
        internalList.sort(new Comparator<Member>() {
            @Override
            public int compare(Member otherMember1, Member otherMember2) {
                return otherMember1.getName().toString().compareTo(otherMember2.getName().toString());
            }
        });
    }

    /**
     * Logs out the user
     */
    public void logout() {
        setCurrentlyLogInMember(null);
    }

    public void setCurrentlyLogInMember(Member member) {
        currentlyLogInMember = member;
    }

    /**
     * Changes the password of a member
     */
    public void changePassword (String username, String oldPassword, String newPassword)
            throws PasswordIncorrectException, DataToChangeIsNotCurrentlyLoggedInMemberException,
            MatricNumberNotFoundException {
        Member checkMember = usernameCredentialsHashMap.get(username);
        if (!usernameCredentialsHashMap.containsKey(username)) {
            throw new MatricNumberNotFoundException();
        }
        if (!checkMember.equals(currentlyLogInMember)) {
            throw new DataToChangeIsNotCurrentlyLoggedInMemberException();
        }
        if (usernamePasswordHashMap.get(username).equals(oldPassword)) {
            internalList.get(internalList.indexOf(checkMember)).getCredentials().setPassword(new Password(newPassword));
            usernamePasswordHashMap.remove(username);
            usernamePasswordHashMap.put(username, newPassword);
        } else {
            throw new PasswordIncorrectException();
        }
    }
    /**
     * Sign up a user when the clubbook is empty
     */
    public void signup(Member member) throws MemberListNotEmptyException {
        if (!internalList.isEmpty()) {
            throw new MemberListNotEmptyException();
        }
        internalList.add(member);
        usernameCredentialsHashMap.put(member.getCredentials().getUsername().value, member);
        usernamePasswordHashMap.put(member.getCredentials().getUsername().value,
                member.getCredentials().getPassword().value);
    }

    /**
     * Clears the clubbook
     */
    public void clear() {
        internalList.clear();
        usernamePasswordHashMap.clear();
        usernameCredentialsHashMap.clear();
        setCurrentlyLogInMember(null);
        System.out.println(getCurrentlyLogInMember());
    }
```
###### \java\seedu\club\model\member\Username.java
``` java
/**
 * Represents a member's username.
 */
public class Username {
    public final String value;

    public Username(String name) {
        this.value = name;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this    //short circuit if same object
                || (other instanceof Username    //handles nulls
                && this.value.equals(((Username) other).value));   //state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\club\model\Model.java
``` java
    /**
     * Logs In a member in the club
     */
    void logsInMember(String username, String password);

    /**
     * Returns the member who is currently logged in to Club Connect.
     */
    Member getLoggedInMember();
```
###### \java\seedu\club\model\Model.java
``` java
    /**
     * Logs out a member from clubbook
     */
    void logOutMember();

    void addTaskToTaskList(Task toAdd) throws DuplicateTaskException;

    void deleteTask(Task taskToDelete) throws TaskNotFoundException, TaskCannotBeDeletedException;

    void updateFilteredTaskList(Predicate<Task> predicate);
```
###### \java\seedu\club\model\Model.java
``` java
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
```
###### \java\seedu\club\model\ModelManager.java
``` java
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

    @Override
    public Member getLoggedInMember() {
        return clubBook.getLoggedInMember();
    }

```
###### \java\seedu\club\model\ModelManager.java
``` java
    @Override
    public void logOutMember() {
        updateFilteredMemberList(Model.PREDICATE_NOT_SHOW_ALL_MEMBERS);
        updateFilteredTaskList(Model.PREDICATE_NOT_SHOW_ALL_TASKS);
        updateFilteredPollList(Model.PREDICATE_NOT_SHOW_ALL_POLLS);
        clubBook.logOutMember();
    }

```
###### \java\seedu\club\model\ModelManager.java
``` java
    /**
     * Changes the password of {@code member} in the clubBook
      * @param username
     * @param oldPassword
     * @param newPassword
     */
    public void changePassword (String username, String oldPassword, String newPassword)
            throws PasswordIncorrectException, DataToChangeIsNotCurrentlyLoggedInMemberException,
            MatricNumberNotFoundException {
        clubBook.changePassword(username, oldPassword, newPassword);
        indicateClubBookChanged();
    }
```
###### \java\seedu\club\model\ModelManager.java
``` java
    @Override
    public void signUpMember(Member member) throws MemberListNotEmptyException {
        clubBook.signUpMember(member);
        filteredMembers.setPredicate(PREDICATE_NOT_SHOW_ALL_MEMBERS);
        indicateClubBookChanged();
    }

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
```
###### \java\seedu\club\ui\LogInMemberBox.java
``` java
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.events.ui.UpdateCurrentlyLogInMemberEvent;

/**
 * The currently logged in panel of the application
 */
public class LogInMemberBox extends UiPart<Region> {

    private static final String FXML = "CurrentlyLogInMemberBox.fxml";

    private final Logger logger = LogsCenter.getLogger(LogInMemberBox.class);


    @FXML
    private Label currentlyloginMember;

    public LogInMemberBox () {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleUpdateCurrentlyLogInMemberEvent (UpdateCurrentlyLogInMemberEvent event) {
        if (event.getCurrentlyLogIn() == null) {
            currentlyloginMember.setText("No one is currently logged in.");
        } else {
            currentlyloginMember.setText("Currently Logged In: " + event.getCurrentlyLogIn().getName().toString());
        }
    }
}
```
###### \java\seedu\club\ui\MemberOverviewPanel.java
``` java

    /**
     * Loads a blank details if no one is selected
     * @param show
     */
    public void loadDetails (Boolean show) {
        gridPane.setStyle("-fx-background-color: #cccccc");
        int size = gridPane.getChildren().size();
        for (int i = 0; i < size; i++) {
            gridPane.getChildren().get(i).setVisible(show);
        }
    }
    public void setConnections(ObservableList<Task> taskList, Member member) {
        loadDetails(true);
        setMemberListView(taskList, member);
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        modifiedTaskCardListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new ModifiedTaskPanelSelecetionChangedEvent(newValue));
                    }
                });
    }

    public void setMemberListView(ObservableList<Task> taskList, Member member) {
        final ObservableList<Task> filteredTaskList = taskList.filtered(new TaskIsRelatedToMemberPredicate(member));
        ObservableList<ModifiedTaskCard> mappedList = EasyBind.map(
                filteredTaskList, (task) -> new ModifiedTaskCard(task, filteredTaskList.indexOf(task) + 1));
        modifiedTaskCardListView.setItems(mappedList);
        modifiedTaskCardListView.setCellFactory(listView -> new TaskListViewCell());
    }

    @Subscribe
    public void handleMemberPanelSelectionChangeEvent(MemberPanelSelectionChangedEvent event) {
        currentlySelectedMember = event.getNewSelection().member;
        loadMemberPage(event.getNewSelection().member);
        setConnections(taskList, event.getNewSelection().member);
    }

    @Subscribe
    private void handleClearMemberSelectPanelEvent (ClearMemberSelectPanelEvent event) {
        if (event.isToClear()) {
            loadDetails(false);
        } else {
            loadDetails(true);
        }
    }

    @Subscribe
    public void handleUpdateSelectionPanelEvent (UpdateSelectionPanelEvent event) {
        if (event.isToUndo()) {
            loadMemberPage(undoStack.pop());
        }
        if (event.getTagToDelete() != null) {
            if (currentlySelectedMember.hasTag(event.getTagToDelete())) {
                undoStack.push(currentlySelectedMember);
                Set<Tag> memberTags = new HashSet<>(currentlySelectedMember.getTags());
                memberTags.remove(event.getTagToDelete());
                currentlySelectedMember = new Member(currentlySelectedMember.getName(),
                        currentlySelectedMember.getPhone(),
                        currentlySelectedMember.getEmail(),
                        currentlySelectedMember.getMatricNumber(),
                        currentlySelectedMember.getGroup(),
                        memberTags,
                        currentlySelectedMember.getCredentials(),
                        currentlySelectedMember.getProfilePhoto());
                loadMemberPage(currentlySelectedMember);
            }
        } else if (event.isToDelete()) {
            if (currentlySelectedMember.equals(event.getToEditMember())) {
                undoStack.push(currentlySelectedMember);
                loadDetails(false);
            }
        } else if (currentlySelectedMember.equals(event.getToEditMember())) {
            undoStack.push(currentlySelectedMember);
            currentlySelectedMember = event.getEditedMember();
            loadMemberPage(event.getEditedMember());
        }
    }
```
###### \java\seedu\club\ui\MemberOverviewPanel.java
``` java
    /**
     * Loads the details of member into a new panel with more details
     * @param member
     */
    private void loadMemberPage(Member member) {
        currentlySelectedMember = member;
        name.setText(member.getName().fullName);
        setProfilePhoto(member);
        phone.setText(member.getPhone().value);
        matricNumber.setText(member.getMatricNumber().value);
        email.setText(member.getEmail().value);
        group.setText(member.getGroup().groupName);
        group.setAlignment(Pos.CENTER);
        createTags(member);
        //setIcons();
    }

    /**
     * Set Icon pictures
     */
    /*private void setIcons() {
        Image phoneImg;
        Image emailImg;
        phoneImg = new Image(MainApp.class.getResourceAsStream(PHONE_ICON),
                21, 21, false, true);
        phoneIcon.setImage(phoneImg);
        emailImg = new Image(MainApp.class.getResourceAsStream(EMAIL_ICON),
                21, 21, false, true);
        emailIcon.setImage(emailImg);
    }*/
```
###### \java\seedu\club\ui\ModifiedTaskCard.java
``` java
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.club.model.task.Task;

/**
 * UI component that displays the summary of the task of a specific person
 */
public class ModifiedTaskCard extends UiPart<Region> {

    private static final String FXML = "ModifiedTaskListCard.fxml";

    public final Task task;

    @FXML
    private Label description;

    @FXML
    private Label id;
    @FXML
    private Label date;

    @FXML
    private Label assignor;

    public ModifiedTaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        description.setText(task.getDescription().getDescription());
        date.setText("Due Date: " + task.getDate().getDate());
        assignor.setText("Assigned by: " + task.getAssignor().getValue());
    }
    public boolean isTaskYetToBegin() {
        return task.hasTaskNotBegun();
    }

    public boolean isTaskInProgress() {
        return task.isTaskInProgress();
    }

    public boolean isTaskCompleted() {
        return task.isTaskCompleted();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        ModifiedTaskCard card = (ModifiedTaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
```
###### \resources\view\CurrentlyLogInMemberBox.fxml
``` fxml

<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.StackPane?>

<StackPane styleClass="loginpane" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
    <Label fx:id="currentlyloginMember" maxHeight="25.0" text="No one is currently logged in.">
    </Label>
</StackPane>
```
###### \resources\view\MemberDetailsPanel.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>
<?import javafx.scene.text.Text?>
<?import javafx.scene.text.TextFlow?>

<VBox xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
    <TextFlow styleClass="text-panel-header" textAlignment="CENTER">
        <Text fill="darkblue" text="MEMBER PROFILE" VBox.vgrow="NEVER" />
    </TextFlow>
<GridPane fx:id="gridPane" prefHeight="543.0" prefWidth="244.0" translateY="4.0" HBox.hgrow="ALWAYS" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">

    <columnConstraints>
        <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
    </columnConstraints>
    <ImageView fx:id="profilePhoto" fitHeight="152.0" fitWidth="114.0" style="-fx-background-color:white" GridPane.halignment="CENTER">
        <HBox.margin>
            <Insets bottom="7.5" left="7.5" right="5.0" top="7.5" />
        </HBox.margin>
    </ImageView>
    <Label fx:id="name" styleClass="cell_medium_label" text="/$name" GridPane.halignment="CENTER" GridPane.rowIndex="2" />
    <ImageView fx:id="phoneIcon" fitHeight="21.0" fitWidth="21.0" translateX="10.0" GridPane.halignment="LEFT" GridPane.rowIndex="4">
        <HBox.margin>
            <Insets bottom="7.5" left="7.5" right="5.0" top="7.5" />
        </HBox.margin>
    </ImageView>
    <Label fx:id="group" contentDisplay="TOP" layoutX="10.0" layoutY="169.0" prefHeight="27.0" prefWidth="130.0" styleClass="label-group-panel" text="/$group" textAlignment="CENTER" GridPane.halignment="CENTER" GridPane.valignment="BOTTOM">
        <font>
            <Font size="18.0" />
        </font></Label>
    <Label fx:id="matricNumber" styleClass="cell_medium_label" text="/$matricnumber" GridPane.halignment="CENTER" GridPane.rowIndex="3" />
    <Label fx:id="phone" styleClass="cell_medium_label" text="/$mobile" GridPane.halignment="CENTER" GridPane.rowIndex="4" />
    <Label fx:id="email" prefHeight="28.0" prefWidth="243.0" styleClass="cell_medium_label" text="/$email" textAlignment="CENTER" GridPane.halignment="CENTER" GridPane.rowIndex="5" />
    <ImageView fx:id="emailIcon" fitHeight="21.0" fitWidth="21.0" layoutX="10.0" layoutY="227.0" translateX="10.0" GridPane.rowIndex="5" />
    <FlowPane fx:id="tags" GridPane.rowIndex="1">
        <padding>
            <Insets bottom="5" top="5" />
        </padding>
    </FlowPane>

    <Text styleClass="cell_medium_label" text="Related Tasks" GridPane.halignment="CENTER" GridPane.rowIndex="6" />
    <VBox GridPane.rowIndex="7">
        <ListView fx:id="modifiedTaskCardListView" prefHeight="155.0" prefWidth="244.0" GridPane.rowIndex="6" VBox.vgrow="ALWAYS">
        </ListView>
    </VBox>
    <rowConstraints>
        <RowConstraints />
        <RowConstraints minHeight="10.0" prefHeight="30.0" />
        <RowConstraints minHeight="10.0" prefHeight="30.0" />
        <RowConstraints minHeight="10.0" prefHeight="30.0" />
        <RowConstraints minHeight="10.0" prefHeight="30.0" />
        <RowConstraints minHeight="10.0" prefHeight="30.0" />
      <RowConstraints minHeight="10.0" prefHeight="30.0" />
        <RowConstraints minHeight="10.0" prefHeight="200.0" />
    </rowConstraints>
</GridPane>
</VBox>
```
###### \resources\view\ModifiedTaskListCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="cardPane" fx:id="cardPane" maxHeight="1.7976931348623157E308" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane maxHeight="1.7976931348623157E308" HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" maxHeight="1.7976931348623157E308" minHeight="63.0" prefHeight="63.0" prefWidth="150.0" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <HBox alignment="CENTER_LEFT" maxHeight="1.7976931348623157E308" spacing="5">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="description" maxHeight="1.7976931348623157E308" styleClass="cell_big_label" text="\$first" wrapText="true" />
            </HBox>
            <Label fx:id="date" styleClass="cell_small_label" text="\$date" />
            <Label fx:id="assignor" styleClass="cell_small_label" text="\$assignor" />
        </VBox>
        <rowConstraints>
            <RowConstraints />
        </rowConstraints>
    </GridPane>
</HBox>
```
