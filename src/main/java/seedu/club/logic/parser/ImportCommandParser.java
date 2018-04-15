//@@author amrut-prabhu
package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.ImportCommand;
import seedu.club.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        try {
            File importFile = ParserUtil.parseImportPath(args);
            return new ImportCommand(importFile);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ParserUtil.MESSAGE_INVALID_CSV_PATH
                    + ImportCommand.MESSAGE_USAGE));
        }
    }

}
