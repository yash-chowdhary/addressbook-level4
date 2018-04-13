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
    private boolean isDataExported;

    public NewExportDataAvailableEvent(File exportFile, String data) {
        this.exportFile = exportFile;
        this.data = data;
        this.isDataExported = true;
    }

    public boolean isDataExported() {
        return isDataExported;
    }

    public void setDataExported(boolean isDataExported) {
        this.isDataExported = isDataExported;
    }

    @Override
    public String toString() {
        return "add " + data + " to file " + exportFile.getAbsolutePath();
    }
}
