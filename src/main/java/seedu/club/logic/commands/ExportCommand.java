//@@author amrut-prabhu
package seedu.club.logic.commands;

import seedu.club.logic.commands.exceptions.CommandException;

/**
 * Exports Club Connect members' information to the file specified.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the members' information to the specified CSV file.\n"
            + "Parameters: FILE_PATH (must be an absolute path)\n"
            + "Example: " + COMMAND_WORD + " C:/Users/Jane Doe/Desktop/members.csv";

    public static final String MESSAGE_EXPORT_SUCCESS = "Members' details exported to %1$s";

    private final String exportFilePath;

    public ExportCommand(String exportFilePath) {
        this.exportFilePath = exportFilePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.exportClubConnect(exportFilePath);

        return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS, exportFilePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.exportFilePath.equals(((ExportCommand) other).exportFilePath)); // state check
    }
}
//@@author amrut-prabhu
