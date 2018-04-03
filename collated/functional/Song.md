# Song
###### \java\seedu\club\logic\commands\LogInCommand.java
``` java
/**
 * Logs in a member to ClubConnect
 */
public class LogInCommand extends Command {

    public static final String COMMAND_WORD = "login";

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

    public static final String COMMAND_FORMAT = "login n/ p/ e/ m/ [pic/ ] ";

    public static final String MESSAGE_SUCCESS = "Sign up successful! Please proceed to log in";
    public static final String MESSAGE_FAILURE =
            "Club Connect already has members of the club."
            + " Log in to start using Club Connect.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Sign up for Club Connect."
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
     * Signs up a member if the clubbook is empty
     * @param member
     */
    void signUpMember(Member member) throws MemberListNotEmptyException;

    void viewAllTasks() throws TasksCannotBeDisplayedException;

    void assignTask(Task toAdd, Name name) throws MemberNotFoundException, DuplicateTaskException,
            IllegalExecutionException;

    void viewMyTasks() throws TasksAlreadyListedException;
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

    @Override
    public void addTaskToTaskList(Task toAdd) throws DuplicateTaskException {
        try {
            Assignor assignor = new Assignor(clubBook.getLoggedInMember().getName().toString());
            Assignee assignee = new Assignee(clubBook.getLoggedInMember().getName().toString());
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
    public void assignTask(Task toAdd, Name name) throws MemberNotFoundException, DuplicateTaskException,
            IllegalExecutionException {
        if (!clubBook.getLoggedInMember().getGroup().toString().equalsIgnoreCase(Group.GROUP_EXCO)) {
            throw new IllegalExecutionException();
        }
        boolean found = false;
        for (Member member : clubBook.getMemberList()) {
            if (member.getName().equals(name)) {
                found = true;
            }
        }
        if (!found) {
            throw new MemberNotFoundException();
        }
        try {
            Assignor assignor = new Assignor(clubBook.getLoggedInMember().getName().toString());
            Assignee assignee = new Assignee(name.toString());
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
    public void deleteTask(Task targetTask) throws TaskNotFoundException, TaskCannotBeDeletedException {
        Assignor assignor = targetTask.getAssignor();
        Assignee assignee = targetTask.getAssignee();
        String currentMember = getLoggedInMember().getName().toString();
        if (!currentMember.equalsIgnoreCase(assignor.getAssignor())
                && !currentMember.equalsIgnoreCase(assignee.getAssignee())) {
            throw new TaskCannotBeDeletedException();
        }
        clubBook.deleteTask(targetTask);
        indicateClubBookChanged();
    }

    @Override
    public void viewAllTasks() throws TasksCannotBeDisplayedException {
        if (!getLoggedInMember().getGroup().toString().equalsIgnoreCase(Group.GROUP_EXCO)) {
            throw new TasksCannotBeDisplayedException();
        }
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateClubBookChanged();
    }

    @Override
    public void viewMyTasks() throws TasksAlreadyListedException {
        if (filteredTasks.getPredicate().equals(new TaskIsRelatedToMemberPredicate(getLoggedInMember()))) {
            throw new TasksAlreadyListedException(ViewMyTasksCommand.MESSAGE_ALREADY_LISTED);
        }
        updateFilteredTaskList(new TaskIsRelatedToMemberPredicate(getLoggedInMember()));
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



}
```
