package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.club.logic.commands.AddCommand;
import seedu.club.logic.commands.AddPollCommand;
import seedu.club.logic.commands.AddTaskCommand;
import seedu.club.logic.commands.AssignTaskCommand;
import seedu.club.logic.commands.ChangeAssigneeCommand;
import seedu.club.logic.commands.ChangePasswordCommand;
import seedu.club.logic.commands.ChangeProfilePhotoCommand;
import seedu.club.logic.commands.ChangeTaskStatusCommand;
import seedu.club.logic.commands.ClearCommand;
import seedu.club.logic.commands.Command;
import seedu.club.logic.commands.CompressCommand;
import seedu.club.logic.commands.DecompressCommand;
import seedu.club.logic.commands.DeleteCommand;
import seedu.club.logic.commands.DeleteGroupCommand;
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
import seedu.club.logic.commands.ImportCommand;
import seedu.club.logic.commands.ListCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.LogOutCommand;
import seedu.club.logic.commands.RedoCommand;
import seedu.club.logic.commands.RemoveProfilePhotoCommand;
import seedu.club.logic.commands.SelectCommand;
import seedu.club.logic.commands.SignUpCommand;
import seedu.club.logic.commands.UndoCommand;
import seedu.club.logic.commands.ViewAllTasksCommand;
import seedu.club.logic.commands.ViewMyTasksCommand;
import seedu.club.logic.commands.ViewResultsCommand;
import seedu.club.logic.commands.VoteCommand;
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
        //@@author yash-chowdhary
        if (isAddCommand(commandWord)) {
            return new AddCommandParser().parse(arguments);
        } else if (isAddPollCommand(commandWord)) {
            return new AddPollCommandParser().parse(arguments);
        } else if (isAddTaskCommand(commandWord)) {
            return new AddTaskCommandParser().parse(arguments);
        } else if (isAssignTaskCommand(commandWord)) {
            return new AssignTaskCommandParser().parse(arguments);
        } else if (isChangeAssigneeCommand(commandWord)) {
            return new ChangeAssigneeCommandParser().parse(arguments);
        } else if (isChangePasswordCommand(commandWord)) {
            return new ChangePasswordCommandParser().parse(arguments);
        } else if (isChangePicCommand(commandWord)) {
            return new ChangeProfilePhotoCommandParser().parse(arguments);
        } else if (isChangeTaskStatusCommand(commandWord)) {
            return new ChangeTaskStatusCommandParser().parse(arguments);
        } else if (isClearCommand(commandWord)) {
            return new ClearCommandParser().parse(arguments);
        } else if (isCompressCommand(commandWord)) {
            return new CompressCommand();
        } else if (isDecompressCommand(commandWord)) {
            return new DecompressCommand();
        } else if (isDeleteCommand(commandWord)) {
            return new DeleteCommandParser().parse(arguments);
        } else if (isDeletePollCommand(commandWord)) {
            return new DeletePollCommandParser().parse(arguments);
        } else if (isDeleteTagCommand(commandWord)) {
            return new DeleteTagCommandParser().parse(arguments);
        } else if (isDeleteTaskCommand(commandWord)) {
            return new DeleteTaskCommandParser().parse(arguments);
        } else if (isEditCommand(commandWord)) {
            return new EditCommandParser().parse(arguments);
        } else if (isEmailCommand(commandWord)) {
            return new EmailCommandParser().parse(arguments);
        } else if (isExitCommand(commandWord)) {
            return new ExitCommand();
        } else if (isExportCommand(commandWord)) {
            return new ExportCommandParser().parse(arguments);
        } else if (isFindCommand(commandWord)) {
            return new FindCommandParser().parse(arguments);
        } else if (isHelpCommand(commandWord)) {
            return new HelpCommand();
        } else if (isHideResults(commandWord)) {
            return new HideResultsCommand();
        } else if (isHistoryCommand(commandWord)) {
            return new HistoryCommand();
        } else if (isImportCommand(commandWord)) {
            return new ImportCommandParser().parse(arguments);
        } else if (isListCommand(commandWord)) {
            return new ListCommand();
        } else if (isLoginCommand(commandWord)) {
            return new LoginCommandParser().parse(arguments);
        } else if (isLogoutCommand(commandWord)) {
            return new LogOutCommand();
        } else if (isRedoCommand(commandWord)) {
            return new RedoCommand();
        } else if (isRemovePicCommand(commandWord)) {
            return new RemoveProfilePhotoCommand();
        } else if (isDeleteGroupCommand(commandWord)) {
            return new DeleteGroupCommandParser().parse(arguments);
        } else if (isSelectCommand(commandWord)) {
            return new SelectCommandParser().parse(arguments);
        } else if (isViewResultsCommand(commandWord)) {
            return new ViewResultsCommand();
        } else if (isSignUpCommand(commandWord)) {
            return new SignUpCommandParser().parse(arguments);
        } else if (isUndoCommand(commandWord)) {
            return new UndoCommand();
        } else if (isViewAllTasksCommand(commandWord)) {
            return new ViewAllTasksCommand();
        } else if (isViewMyTasksCommand(commandWord)) {
            return new ViewMyTasksCommand();
        } else if (isVoteCommand(commandWord)) {
            return new VoteCommandParser().parse(arguments);
        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Returns true if {@code commandWord} matches any of ChangeAssigneeCommand's aliases
     */
    private boolean isChangeAssigneeCommand(String commandWord) {
        for (String commandAlias : ChangeAssigneeCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ChangeTaskStatusCommand's aliases
     */
    private boolean isChangeTaskStatusCommand(String commandWord) {
        for (String commandAlias : ChangeTaskStatusCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of VoteCommand's aliases
     */
    private boolean isVoteCommand(String commandWord) {
        for (String commandAlias : VoteCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ChangePasswordCommand's aliases
     */
    private boolean isChangePasswordCommand(String commandWord) {
        for (String commandAlias : ChangePasswordCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ViewMyTasksCommand's aliases
     */
    private boolean isViewMyTasksCommand(String commandWord) {
        for (String commandAlias : ViewMyTasksCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ViewAllTasksCommand's aliases
     */
    private boolean isViewAllTasksCommand(String commandWord) {
        for (String commandAlias : ViewAllTasksCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of UndoCommand's aliases
     */
    private boolean isUndoCommand(String commandWord) {
        for (String commandAlias : UndoCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of SignUpCommand's aliases
     */
    private boolean isSignUpCommand(String commandWord) {
        for (String commandAlias : SignUpCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ViewResultsCommand's aliases
     */
    private boolean isViewResultsCommand(String commandWord) {
        for (String commandAlias : ViewResultsCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of SelectCommand's aliases
     */
    private boolean isSelectCommand(String commandWord) {
        for (String commandAlias : SelectCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of DeleteGroupCommand's aliases
     */
    private boolean isDeleteGroupCommand(String commandWord) {
        for (String commandAlias : DeleteGroupCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of RedoCommand's aliases
     */
    private boolean isRedoCommand(String commandWord) {
        for (String commandAlias : RedoCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of LogoutCommand's aliases
     */
    private boolean isLogoutCommand(String commandWord) {
        for (String commandAlias : LogOutCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of LoginCommand's aliases
     */
    private boolean isLoginCommand(String commandWord) {
        for (String commandAlias : LogInCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ListCommand's aliases
     */
    private boolean isListCommand(String commandWord) {
        for (String commandAlias : ListCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of HistoryCommand's aliases
     */
    private boolean isHistoryCommand(String commandWord) {
        for (String commandAlias : HistoryCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of HideResultsCommand's aliases
     */
    private boolean isHideResults(String commandWord) {
        for (String commandAlias : HideResultsCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of HelpCommand's aliases
     */
    private boolean isHelpCommand(String commandWord) {
        for (String commandAlias : HelpCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of FindCommand's aliases
     */
    private boolean isFindCommand(String commandWord) {
        for (String commandAlias : FindCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ImportCommand's aliases
     */
    private boolean isImportCommand(String commandWord) {
        for (String commandAlias : ImportCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ExportCommand's aliases
     */
    private boolean isExportCommand(String commandWord) {
        for (String commandAlias : ExportCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ExitCommand's aliases
     */
    private boolean isExitCommand(String commandWord) {
        for (String commandAlias : ExitCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of EmailCommand's aliases
     */
    private boolean isEmailCommand(String commandWord) {
        for (String commandAlias : EmailCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of EditCommand's aliases
     */
    private boolean isEditCommand(String commandWord) {
        for (String commandAlias : EditCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of DeleteTaskCommand's aliases
     */
    private boolean isDeleteTaskCommand(String commandWord) {
        for (String commandAlias : DeleteTaskCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of DeleteTagCommand's aliases
     */
    private boolean isDeleteTagCommand(String commandWord) {
        for (String commandAlias : DeleteTagCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of DeletePollCommand's aliases
     */
    private boolean isDeletePollCommand(String commandWord) {
        for (String commandAlias : DeletePollCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of DecompressCommand's aliases
     */
    private boolean isDeleteCommand(String commandWord) {
        for (String commandAlias : DeleteCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of DecompressCommand's aliases
     */
    private boolean isDecompressCommand(String commandWord) {
        for (String commandAlias : DecompressCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of CompressCommand's aliases
     */
    private boolean isCompressCommand(String commandWord) {
        for (String commandAlias : CompressCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ClearCommand's aliases
     */
    private boolean isClearCommand(String commandWord) {
        for (String commandAlias : ClearCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of ChangeProfilePhotoCommand's aliases
     */
    private boolean isChangePicCommand(String commandWord) {
        for (String commandAlias : ChangeProfilePhotoCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of AssignTaskCommand's aliases
     */
    private boolean isAssignTaskCommand(String commandWord) {
        for (String commandAlias : AssignTaskCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of AddPollCommand's aliases
     */
    private boolean isAddTaskCommand(String commandWord) {
        for (String commandAlias : AddTaskCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of AddPollCommand's aliases
     */
    private boolean isAddPollCommand(String commandWord) {
        for (String commandAlias : AddPollCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of AddCommand's aliases
     */
    private boolean isAddCommand(String commandWord) {
        for (String commandAlias : AddCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code commandWord} matches any of RemoveProfilePhotoCommand's aliases
     */
    private boolean isRemovePicCommand(String commandWord) {
        for (String commandAlias : RemoveProfilePhotoCommand.COMMAND_ALIASES) {
            if (commandWord.equals(commandAlias)) {
                return true;
            }
        }
        return false;
    }
    //@@author
}
