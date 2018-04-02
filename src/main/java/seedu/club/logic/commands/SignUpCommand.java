package seedu.club.logic.commands;

import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.club.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;
import seedu.club.model.member.Member;
import seedu.club.model.member.exceptions.MemberListNotEmptyException;

//@@author Song Weiyang
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
