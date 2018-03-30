//@@author amrut-prabhu
package seedu.club.commons.exceptions;

import java.io.IOException;

/**
 * Represents an error while writing a photo file.
 */
public class PhotoWriteException extends IOException {

    public PhotoWriteException() {}

    public PhotoWriteException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return this.getMessage();
    }

}
