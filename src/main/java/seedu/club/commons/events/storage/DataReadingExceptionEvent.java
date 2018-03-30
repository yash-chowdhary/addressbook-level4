//@@author amrut-prabhu
package seedu.club.commons.events.storage;

import seedu.club.commons.events.BaseEvent;

/**
 * Indicates an exception during a file reading.
 */
public class DataReadingExceptionEvent extends BaseEvent {

    public final Exception exception;

    public DataReadingExceptionEvent(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return exception.toString();
    }

}
