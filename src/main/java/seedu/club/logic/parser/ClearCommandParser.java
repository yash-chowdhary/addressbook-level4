package seedu.club.logic.parser;

import seedu.club.logic.commands.ClearCommand;
import seedu.club.logic.parser.exceptions.ParseException;

//@@author th14thmusician
/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser implements Parser<ClearCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * and returns a ClearCommand object for execution.
     */
    public ClearCommand parse(String args) throws ParseException {
        if (args.equals("")) {
            return  new ClearCommand();
        } else {
            return new ClearCommand(args);
        }
    }
}
//@@author
