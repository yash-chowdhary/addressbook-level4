//@@author amrut-prabhu
package seedu.club.commons.exceptions;

import java.io.IOException;

/**
 * Represents an error while reading a photo file.
 */
public class PhotoReadException extends IOException {

    public PhotoReadException() {}

    public PhotoReadException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return this.getMessage();
    }

}
