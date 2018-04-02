package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.club.logic.commands.AddCommand;
import seedu.club.logic.commands.AddPollCommand;
import seedu.club.logic.commands.AddTaskCommand;
import seedu.club.logic.commands.AssignTaskCommand;
import seedu.club.logic.commands.ChangeProfilePhotoCommand;
import seedu.club.logic.commands.ClearCommand;
import seedu.club.logic.commands.Command;
import seedu.club.logic.commands.CompressCommand;
import seedu.club.logic.commands.DecompressCommand;
import seedu.club.logic.commands.DeleteCommand;
import seedu.club.logic.commands.DeletePollCommand;
import seedu.club.logic.commands.DeleteTagCommand;
import seedu.club.logic.commands.DeleteTaskCommand;
import seedu.club.logic.commands.EditCommand;
import seedu.club.logic.commands.EmailCommand;
import seedu.club.logic.commands.ExitCommand;
import seedu.club.logic.commands.ExportCommand;
import seedu.club.logic.commands.FindCommand;
import seedu.club.logic.commands.HelpCommand;
import seedu.club.logic.commands.HideResultsCommand;
import seedu.club.logic.commands.HistoryCommand;
import seedu.club.logic.commands.ListCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.LogOutCommand;
import seedu.club.logic.commands.RedoCommand;
import seedu.club.logic.commands.RemoveGroupCommand;
import seedu.club.logic.commands.SelectCommand;
import seedu.club.logic.commands.ShowResultsCommand;
import seedu.club.logic.commands.SignUpCommand;
import seedu.club.logic.commands.UndoCommand;
import seedu.club.logic.commands.ViewAllTasksCommand;
import seedu.club.logic.commands.ViewMyTasksCommand;
import seedu.club.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ClubBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddPollCommand.COMMAND_WORD:
            return new AddPollCommandParser().parse(arguments);

        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);

        case AssignTaskCommand.COMMAND_WORD:
            return new AssignTaskCommandParser().parse(arguments);

        case ChangeProfilePhotoCommand.COMMAND_WORD:
            return new ChangeProfilePhotoCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case CompressCommand.COMMAND_WORD:
            return new CompressCommand();

        case DecompressCommand.COMMAND_WORD:
            return new DecompressCommand();

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeletePollCommand.COMMAND_WORD:
            return new DeletePollCommandParser().parse(arguments);

        case DeleteTagCommand.COMMAND_WORD:
            return new DeleteTagCommandParser().parse(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteTaskCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EmailCommand.COMMAND_WORD:
            return new EmailCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case LogInCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case RemoveGroupCommand.COMMAND_WORD:
            return new RemoveGroupCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case LogOutCommand.COMMAND_WORD:
            return new LogOutCommand();

        case ShowResultsCommand.COMMAND_WORD:
            return new ShowResultsCommand();

        case HideResultsCommand.COMMAND_WORD:
            return new HideResultsCommand();

        case SignUpCommand.COMMAND_WORD:
            return new SignUpCommandParser().parse(arguments);

        case ViewAllTasksCommand.COMMAND_WORD:
            return new ViewAllTasksCommand();

        case ViewMyTasksCommand.COMMAND_WORD:
            return new ViewMyTasksCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
