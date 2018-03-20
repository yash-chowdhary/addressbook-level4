//@@author amrut-prabhu
package seedu.club.commons.events.model;

import seedu.club.commons.events.BaseEvent;

/**
 * Indicates that the profile photo of a member has changed.
 */
public class ProfilePhotoChangedEvent extends BaseEvent {

    public final String originalPhotoPath;
    public final String newFileName;

    public ProfilePhotoChangedEvent(String originalPhotoPath, String newFileName) {
        this.originalPhotoPath = originalPhotoPath;
        this.newFileName = newFileName;
    }

    @Override
    public String toString() {
        return originalPhotoPath + " stored as " + newFileName;
    }

}
