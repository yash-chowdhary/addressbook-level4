//@@author amrut-prabhu
package seedu.club.commons.events.model;

import java.io.File;

import seedu.club.commons.events.BaseEvent;

/**
 * Indicates that a new member to export is available.
 */
public class NewExportMemberAvailableEvent extends BaseEvent {

    public final File exportFile;
    public final String memberData;

    public NewExportMemberAvailableEvent(File exportFile, String memberData) {
        this.exportFile = exportFile;
        this.memberData = memberData;
    }

    @Override
    public String toString() {
        return "add " + memberData + " to " + exportFile.toString();
    }
}
