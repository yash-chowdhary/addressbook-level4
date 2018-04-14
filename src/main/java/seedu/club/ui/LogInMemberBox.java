package seedu.club.ui;
//@@author th14thmusician
import static seedu.club.model.member.ProfilePhoto.DEFAULT_PHOTO_PATH;
import static seedu.club.model.member.ProfilePhoto.EMPTY_STRING;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.club.MainApp;
import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.events.ui.UpdateCurrentlyLogInMemberEvent;
import seedu.club.model.member.ProfilePhoto;

/**
 * The currently logged in panel of the application
 */
public class LogInMemberBox extends UiPart<Region> {

    private static final String FXML = "CurrentlyLogInMemberBox.fxml";

    private final Logger logger = LogsCenter.getLogger(LogInMemberBox.class);
    private final Integer photoWidth = 34;
    private final Integer photoHeight = 45;

    @FXML
    private Label currentlyloginMember;
    @FXML
    private ImageView profilePhoto;

    public LogInMemberBox () {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleUpdateCurrentlyLogInMemberEvent (UpdateCurrentlyLogInMemberEvent event) {
        if (event.getCurrentlyLogIn() == null) {
            currentlyloginMember.setText("Log in to use Club Connect");
            profilePhoto.setVisible(false);
        } else {
            profilePhoto.setVisible(true);
            currentlyloginMember.setText("Logged In: " + event.getCurrentlyLogIn().getName().toString());
            setProfilePhoto(event.getCurrentlyLogIn().getProfilePhoto());
        }
    }

    //@@author amrut-prabhu
    /**
     * Sets the profile photo of {@code member} to the displayed photo shape.
     */
    private void setProfilePhoto(ProfilePhoto currentPhoto) {
        Image photo;
        String photoPath = currentPhoto.getPhotoPath();
        if (photoPath.equals(EMPTY_STRING) || photoPath.equals(DEFAULT_PHOTO_PATH)) {
            photo = new Image(MainApp.class.getResourceAsStream(DEFAULT_PHOTO_PATH), photoWidth, photoHeight,
                    false, true);
        } else {
            photo = new Image("file:" + photoPath, photoWidth, photoHeight, false, true);
        }
        profilePhoto.setImage(photo);
    }
}
