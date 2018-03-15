package seedu.club.model.Member.exceptions;

import seedu.club.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Member objects.
 */
public class DuplicatePersonException extends DuplicateDataException {
    public DuplicatePersonException() {
        super("Operation would result in duplicate persons");
    }
}
