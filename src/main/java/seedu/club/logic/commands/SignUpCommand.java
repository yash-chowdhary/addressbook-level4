package seedu.club.logic.commands;

import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;
import seedu.club.model.member.Member;
import seedu.club.model.member.exceptions.MemberListNotEmptyException;

//@@author th14thmusician
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
//@@author
