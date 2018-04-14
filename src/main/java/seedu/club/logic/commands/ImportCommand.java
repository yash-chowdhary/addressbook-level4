//@@author amrut-prabhu
package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.commands.exceptions.CommandException;

/**
 * Exports members' information from the specified file into Club Connect.
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_FORMAT = "import FILE_PATH";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "imp")
    );

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports the members' information from the specified CSV file into Club Connect.\n"
            + "Parameters: FILE_PATH (must be an absolute path to a CSV file)\n"
            + "Example: " + COMMAND_WORD + " C:/Users/John Doe/Downloads/new_members.csv";

    public static final String MESSAGE_IMPORT_SUCCESS = "Successfully imported %1$d member(s) from %2$s";
    public static final String MESSAGE_IMPORT_FAILURE = "Error occurred while importing from %1$s";
    public static final String MESSAGE_MEMBERS_NOT_IMPORTED = "0 members imported from %1$s. This may be due to "
            + "incorrect format of the data or duplicate members in the file.";

    private final File importFile;

    /**
     * @param importFile CSV file to import members from.
     */
    public ImportCommand(File importFile) {
        requireNonNull(importFile);
        this.importFile = importFile;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        try {
            int numberImported = model.importMembers(importFile);
            if (numberImported == 0) {
                return new CommandResult(String.format(MESSAGE_MEMBERS_NOT_IMPORTED, importFile));
            }
            return new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS, numberImported, importFile));
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_IMPORT_FAILURE, importFile));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && this.importFile.equals(((ImportCommand) other).importFile)); // state check
    }
}
