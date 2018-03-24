package seedu.club.model.poll.exceptions;

import seedu.club.commons.exceptions.DuplicateDataException;

/**
 * Signals that an operation would have violated the 'no duplicates' property of the list.
 */
public class DuplicatePollException extends DuplicateDataException {
    public DuplicatePollException() {
        super("Operation would result in duplicate polls");
    }
}
