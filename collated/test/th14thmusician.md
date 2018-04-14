# th14thmusician
###### \java\seedu\club\logic\commands\AddCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\AddCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\AddPollCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\AddPollCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\AddTaskCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\AddTaskCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\AssignTaskCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\AssignTaskCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\AssignTaskCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\ChangePasswordCommandTest.java
``` java

public class ChangePasswordCommandTest {
    private Model model;
    private Model expectedModel;
    private ObservableList<Member> observableList;
    private Password newPassword;
    private Member member;

    @Before
    public void setUp () throws CommandException {
        model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalClubBook(), new UserPrefs());
        observableList = model.getClubBook().getMemberList();
        member = observableList.get(0);
        LogInCommand command = new LogInCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
        command.setData(expectedModel, new CommandHistory(), new UndoRedoStack());
        command.execute();
        newPassword = new Password("test");
    }

    @Test
    public void excecute_changepassword_success ()
            throws PasswordIncorrectException, DataToChangeIsNotCurrentlyLoggedInMemberException,
            MatricNumberNotFoundException {
        Member memberToChangePasswordOf = new Member(member.getName(), member.getPhone(), member.getEmail(),
                member.getMatricNumber(), member.getGroup(), member.getTags());
        expectedModel.changePassword(this.member.getCredentials().getUsername().value,
                this.member.getCredentials().getPassword().value, newPassword.value);
        assertCommandSuccess(prepareCommand(memberToChangePasswordOf, model), model,
                ChangePasswordCommand.MESSAGE_SUCCESS, expectedModel);
        expectedModel.changePassword(member.getCredentials().getUsername().value,
                member.getCredentials().getPassword().value, "password");
    }

    @Test
    public void execute_changepassword_throwscommandexception () throws PasswordIncorrectException {
        assertCommandFailure(prepareCommandThatFails(member, model), model,
                ChangePasswordCommand.MESSAGE_PASSWORD_INCORRECT);
    }
    @Test
    public void execute_changepassword_throwsauthenicationerrorexception () {
        Member othermember = observableList.get(1);
        assertCommandFailure(prepareCommand(othermember, model), model,
                ChangePasswordCommand.MESSAGE_AUTHENTICATION_FAILED);
    }

    @Test
    public void execute_changepassword_throwsincorrectusernameexception () {
        assertCommandFailure(prepareCommandThatThrowsIncorrectUsername(member, model), model,
                ChangePasswordCommand.MESSAGE_USERNAME_NOTFOUND);
    }
    /**
     * Generates a ChangePasswordCommand upon execution
     * @param member
     * @param model
     * @return
     */
    private ChangePasswordCommand prepareCommand(Member member, Model model) {
        ChangePasswordCommand command = new ChangePasswordCommand(member.getCredentials().getUsername(),
                member.getCredentials().getPassword(), newPassword);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a ChangePasswordCommand upon exceution that throws incorrectPassword
     * @param member
     * @param model
     * @return
     */
    private ChangePasswordCommand prepareCommandThatFails(Member member, Model model) {
        ChangePasswordCommand command = new ChangePasswordCommand(member.getCredentials().getUsername(),
                new Password("fake"), newPassword);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a ChangePasswordCommand upon execution that throws IncorrectUsername
     */
    private ChangePasswordCommand prepareCommandThatThrowsIncorrectUsername(Member member, Model model) {
        ChangePasswordCommand command = new ChangePasswordCommand(new Username("A0000000Z"),
                member.getCredentials().getPassword(), newPassword);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\club\logic\commands\ChangeProfilePhotoCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\ChangeProfilePhotoCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\ExportCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\ExportCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\ImportCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\ImportCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\ImportCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\LogInCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\LogInCommandTest.java
``` java
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
```
###### \java\seedu\club\logic\commands\RemoveProfilePhotoCommandTest.java
``` java
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
```
