package seedu.club.model.task.exceptions;

import seedu.club.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation would result in duplicate task objects.
 */
public class DuplicateTaskException extends DuplicateDataException {
    public DuplicateTaskException() {
        super("Operation would result in duplicate tasks");
    }
}
