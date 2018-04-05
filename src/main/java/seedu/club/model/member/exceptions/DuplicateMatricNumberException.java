//@@author amrut-prabhu
package seedu.club.model.member.exceptions;

import seedu.club.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in a member object with a non-unique matric number.
 */
public class DuplicateMatricNumberException extends DuplicateDataException {
    public DuplicateMatricNumberException() {
        super("Operation would result in a member with duplicate matric number");
    }
}
