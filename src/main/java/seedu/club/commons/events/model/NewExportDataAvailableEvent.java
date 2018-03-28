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
    private boolean isFileChanged;

    public NewExportDataAvailableEvent(File exportFile) {
        this.exportFile = exportFile;
        this.data = null;
        this.isFileChanged = true;

    }

    public NewExportDataAvailableEvent(String data) {
        this.data = data;
        this.exportFile = null;
        this.isFileChanged = true;
    }

    public boolean isFileChanged() {
        return isFileChanged;
    }

    public void setFileChanged(boolean isFileChanged) {
        this.isFileChanged = isFileChanged;
    }

    @Override
    public String toString() {
        return "add " + data + " to file";
    }
}
