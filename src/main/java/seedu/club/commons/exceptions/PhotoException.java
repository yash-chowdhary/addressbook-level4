//@@author amrut-prabhu
package seedu.club.commons.exceptions;

import java.io.IOException;

/**
 * Represents an error during reading or writing of a photo file.
 */
public class PhotoException extends IOException {
    public PhotoException(String message) {
        super(message);
    }

}
