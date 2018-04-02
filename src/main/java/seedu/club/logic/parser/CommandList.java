package seedu.club.logic.parser;
//@@author yash-chowdhary
import java.util.ArrayList;
import java.util.Collections;

import seedu.club.logic.commands.AddCommand;
import seedu.club.logic.commands.AddPollCommand;
import seedu.club.logic.commands.AddTaskCommand;
import seedu.club.logic.commands.AssignTaskCommand;
import seedu.club.logic.commands.ChangeProfilePhotoCommand;
import seedu.club.logic.commands.ClearCommand;
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
import seedu.club.logic.commands.ListCommand;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.LogOutCommand;
import seedu.club.logic.commands.RedoCommand;
import seedu.club.logic.commands.RemoveGroupCommand;
import seedu.club.logic.commands.SelectCommand;
import seedu.club.logic.commands.ShowResultsCommand;
import seedu.club.logic.commands.UndoCommand;
import seedu.club.logic.commands.ViewAllTasksCommand;
import seedu.club.logic.commands.ViewMyTasksCommand;

/**
 * Stores list of commands
 */
public class CommandList {

    private final ArrayList<String> commandList = new ArrayList<>();

    public ArrayList<String> getCommandList() {
        commandList.add(AddCommand.COMMAND_FORMAT);
        commandList.add(ChangeProfilePhotoCommand.COMMAND_FORMAT);
        commandList.add(ClearCommand.COMMAND_WORD);
        commandList.add(CompressCommand.COMMAND_WORD);
        commandList.add(DecompressCommand.COMMAND_WORD);
        commandList.add(DeleteCommand.COMMAND_FORMAT);
        commandList.add(DeleteTagCommand.COMMAND_FORMAT);
        commandList.add(EditCommand.COMMAND_FORMAT);
        commandList.add(EmailCommand.COMMAND_FORMAT);
        commandList.add(ExitCommand.COMMAND_WORD);
        commandList.add(FindCommand.COMMAND_FORMAT);
        commandList.add(HelpCommand.COMMAND_WORD);
        commandList.add(ListCommand.COMMAND_WORD);
        commandList.add(LogInCommand.COMMAND_FORMAT);
        commandList.add(RedoCommand.COMMAND_WORD);
        commandList.add(RemoveGroupCommand.COMMAND_FORMAT);
        commandList.add(SelectCommand.COMMAND_FORMAT);
        commandList.add(UndoCommand.COMMAND_WORD);
        commandList.add(AddTaskCommand.COMMAND_FORMAT);
        commandList.add(DeleteTaskCommand.COMMAND_FORMAT);
        commandList.add(ShowResultsCommand.COMMAND_WORD);
        commandList.add(HideResultsCommand.COMMAND_WORD);
        commandList.add(ViewAllTasksCommand.COMMAND_WORD);
        commandList.add(ViewMyTasksCommand.COMMAND_WORD);
        commandList.add(AssignTaskCommand.COMMAND_FORMAT);
        commandList.add(AddPollCommand.COMMAND_FORMAT);
        commandList.add(DeletePollCommand.COMMAND_FORMAT);
        commandList.add(ExportCommand.COMMAND_FORMAT);
        commandList.add(LogOutCommand.COMMAND_WORD);

        Collections.sort(commandList);
        return commandList;
    }
}
