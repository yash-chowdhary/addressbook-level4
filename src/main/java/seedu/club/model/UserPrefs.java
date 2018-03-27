package seedu.club.model;

import java.util.Objects;

import seedu.club.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String clubBookFilePath = "data/clubbook.xml";
    private String clubBookName = "TypicalClubBookName";

    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0);
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public String getClubBookFilePath() {
        return clubBookFilePath;
    }

    public void setClubBookFilePath(String clubBookFilePath) {
        this.clubBookFilePath = clubBookFilePath;
    }

    public String getClubBookName() {
        return clubBookName;
    }

    public void setClubBookName(String clubBookName) {
        this.clubBookName = clubBookName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(clubBookFilePath, o.clubBookFilePath)
                && Objects.equals(clubBookName, o.clubBookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, clubBookFilePath, clubBookName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + clubBookFilePath);
        sb.append("\nClubBook name : " + clubBookName);
        return sb.toString();
    }

}
