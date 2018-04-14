package seedu.club.ui;
//@@author th14thmusician
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.events.ui.UpdateCurrentlyLogInMemberEvent;

/**
 * The currently logged in panel of the application
 */
public class LogInMemberBox extends UiPart<Region> {

    private static final String FXML = "CurrentlyLogInMemberBox.fxml";

    private final Logger logger = LogsCenter.getLogger(LogInMemberBox.class);


    @FXML
    private Label currentlyloginMember;

    public LogInMemberBox () {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleUpdateCurrentlyLogInMemberEvent (UpdateCurrentlyLogInMemberEvent event) {
        if (event.getCurrentlyLogIn() == null) {
            currentlyloginMember.setText("No one is currently logged in.");
        } else {
            currentlyloginMember.setText("Currently Logged In: " + event.getCurrentlyLogIn().getName().toString());
        }
    }
}
//@@author
