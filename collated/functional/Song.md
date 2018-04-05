# Song
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
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the password of a member in the ClubBook"
            + "Parameters: "
            + PREFIX_USERNAME + "username "
            + PREFIX_PASSWORD + "oldpassword "
            + PREFIX_NEWPASSWORD + "newpassword";
    public static final String MESSAGE_SUCCESS = "Password changed successfully!";
    public static final String MESSAGE_PASSWORD_INCORRECT = "Password is incorrect";
    public static final String MESSAGE_AUTHENTICATION_FAILED =
            "You do not have the rights to change other member's password";
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
        try {
            model.changePassword(username.value, oldPassword.value, newPassword.value);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (PasswordIncorrectException e) {
            throw new CommandException(MESSAGE_PASSWORD_INCORRECT);
        } catch (DataToChangeIsNotCurrentlyLoggedInMemberException dataToChangeIsNotCurrentlyLoggedInMemberException) {
            throw new CommandException(MESSAGE_AUTHENTICATION_FAILED);
        }
    }

    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        super.setData(model, history, undoRedoStack);
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

    public static final String MESSAGE_SUCCESS = "login successful!";
    public static final String MESSAGE_FAILURE = "login unsuccessful!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs in a member to ClubConnect. "
            + "Parameters: "
            + PREFIX_USERNAME + "username "
            + PREFIX_PASSWORD + "password\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "JohnDoe" + " "
            + PREFIX_PASSWORD + "password";
    private final Username username;
    private final Password password;

    public LogInCommand(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        model.logsInMember(username.value, password.value);
        if (model.getLoggedInMember() != null) {
            return new CommandResult(MESSAGE_SUCCESS + model.getLoggedInMember().getName().toString());
        }
        return new CommandResult(MESSAGE_FAILURE);
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

    public static final String COMMAND_FORMAT = "login n/ p/ e/ m/ [pic/ ] ";

    public static final String MESSAGE_SUCCESS = "Sign up successful! Please proceed to log in";
    public static final String MESSAGE_FAILURE =
            "Club Connect already has members of the club."
            + " Log in to start using Club Connect.";
    public static final String MESSAGE_USAGE = "Sign up for Club Connect."
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_MATRIC_NUMBER + "MATRIC NUMBER "
            + "[" + PREFIX_GROUP + "GROUP] "
            + "[" + PREFIX_TAG + "TAG]... "
            + PREFIX_USERNAME + "username "
            + PREFIX_PASSWORD + "password\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_MATRIC_NUMBER + "A0123456H "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney ";
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
            throws PasswordIncorrectException, DataToChangeIsNotCurrentlyLoggedInMemberException {
        members.changePassword(username, oldpassword, newPassword);
    }
```
###### \java\seedu\club\model\member\UniqueMemberList.java
``` java
    /**
     * Changes the password of a member
     */
    public void changePassword (String username, String oldPassword, String newPassword)
            throws PasswordIncorrectException, DataToChangeIsNotCurrentlyLoggedInMemberException {
        Member checkMember = usernameCredentialsHashMap.get(username);
        if (!checkMember.equals(currentlyLogInMember)) {
            throw new DataToChangeIsNotCurrentlyLoggedInMemberException();
        }
        if (checkMember != null && usernamePasswordHashMap.get(username).equals(oldPassword)) {
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
}
```
###### \java\seedu\club\model\Model.java
``` java
    /**
     * Logs In a member in the club
     */
    void logsInMember(String username, String password);

```
###### \java\seedu\club\model\Model.java
``` java
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
            throws PasswordIncorrectException, DataToChangeIsNotCurrentlyLoggedInMemberException;

    /**
     * Signs up a member if the clubbook is empty
     * @param member
     */
    void signUpMember(Member member) throws MemberListNotEmptyException;

    void viewAllTasks() throws TasksCannotBeDisplayedException;

    void assignTask(Task toAdd, Name name) throws MemberNotFoundException, DuplicateTaskException,
            IllegalExecutionException;

    void viewMyTasks() throws TasksAlreadyListedException;

    void changeStatus(Task taskToEdit, Task editedTask) throws TaskNotFoundException, DuplicateTaskException,
            IllegalExecutionException;
}
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

```
###### \java\seedu\club\model\ModelManager.java
``` java
    @Override
    public Member getLoggedInMember() {
        return clubBook.getLoggedInMember();
    }

```
###### \java\seedu\club\model\ModelManager.java
``` java
    @Override
    public void logOutMember() {
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
            throws PasswordIncorrectException, DataToChangeIsNotCurrentlyLoggedInMemberException {
        clubBook.changePassword(username, oldPassword, newPassword);
        indicateClubBookChanged();
    }

```
###### \java\seedu\club\model\ModelManager.java
``` java
    @Override
    public void signUpMember(Member member) throws MemberListNotEmptyException {
        clubBook.signUpMember(member);
        indicateClubBookChanged();
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
```
