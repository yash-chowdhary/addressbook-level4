//@@author amrut-prabhu
package seedu.club.logic.commands;

import java.io.File;

import seedu.club.logic.commands.exceptions.CommandException;

/**
 * Exports Club Connect's members' information to the file specified.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_FORMAT = "export FILE_PATH";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the members' information to the specified CSV file. "
            + "Parameters: FILE_PATH (must be an absolute CSV file path)\n"
            + "Example: " + COMMAND_WORD + " C:/Users/Jane Doe/Desktop/members.csv";

    public static final String MESSAGE_EXPORT_SUCCESS = "Members' details exported to %1$s";
    public static final String MESSAGE_EXPORT_FAILURE = "Error occurred while exporting to the file: %1$s";

    private final File exportFile;

    /**
     * @param exportFile CSV file to be exported to.
     */
    public ExportCommand(File exportFile) {
        this.exportFile = exportFile;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (model.exportClubConnect(exportFile)) {
            return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS, exportFile));
        } else {
            return new CommandResult(String.format(MESSAGE_EXPORT_FAILURE, exportFile));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.exportFile.equals(((ExportCommand) other).exportFile)); // state check
    }
}
//@@author amrut-prabhu
