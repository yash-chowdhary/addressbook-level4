package seedu.club.model.member.exceptions;

import seedu.club.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate member objects.
 */
public class DuplicateMatricNumberException extends DuplicateDataException {
    public DuplicateMatricNumberException() {
        super("Operation would result in a member with duplicate matric number");
    }
}
