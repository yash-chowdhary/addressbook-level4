//@@author amrut-prabhu
package seedu.club.commons.events.model;

import java.io.File;

import seedu.club.commons.events.BaseEvent;

/**
 * Indicates that a new member to export is available.
 */
public class NewExportDataAvailableEvent extends BaseEvent {

    public final File exportFile;
    public final String data;

    public NewExportDataAvailableEvent(File exportFile, String memberData) {
        this.exportFile = exportFile;
        this.data = memberData;
    }

    public NewExportDataAvailableEvent(String memberData) {
        this.exportFile = null;
        this.data = memberData;
    }

    @Override
    public String toString() {
        return "add " + data + " to file";
    }
}
