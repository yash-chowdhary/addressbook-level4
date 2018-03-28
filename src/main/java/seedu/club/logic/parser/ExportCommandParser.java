//@@author amrut-prabhu
package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.io.IOException;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.ExportCommand;
import seedu.club.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    public static final String MESSAGE_FILE_CREATION_ERROR = "Error while creating file %1$s";

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCommand parse(String args) throws ParseException {
        try {
            File exportFile = ParserUtil.parseExportPath(args);
            return new ExportCommand(exportFile);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        } catch (IOException ioe) {
            throw new ParseException(String.format(MESSAGE_FILE_CREATION_ERROR, args));
        }
    }

}
//@@author
