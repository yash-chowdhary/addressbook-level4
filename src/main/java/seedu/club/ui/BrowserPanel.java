package seedu.club.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.club.MainApp;
import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.events.ui.MemberPanelSelectionChangedEvent;
import seedu.club.commons.events.ui.SendEmailRequestEvent;
import seedu.club.model.email.Client;
import seedu.club.model.member.Member;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {
    public static final String GMAIL_URL = "https://mail.google.com/mail/?view=cm&fs=1&to=%1$s"
            + "&su=%2$s&body=%3$s";
    public static final String OUTLOOK_URL = "https://outlook.office.com/?path=/mail/action/"
            + "compose&to=%1$s&subject=%2$s&body=%3$s";

    private static final String FXML = "MemberDetailsPanel.fxml";

    private static final Integer PHOTO_WIDTH = 90;
    private static final Integer PHOTO_HEIGHT = 120;
    private static final String DEFAULT_PHOTO = "/images/defaultProfilePhoto.png";
    private static final String EMPTY_STRING = "";
    private static final String[] TAG_COLORS = {"red", "yellow", "grey", "brown", "pink", "white",
                                                "orange", "blue", "violet"};

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;
    @FXML
    private Label name;
    @FXML
    private ImageView profilePhoto;
    @FXML
    private Label phone;
    @FXML
    private Label matricNumber;
    @FXML
    private Label group;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label nameText;
    @FXML
    private Label phoneText;
    @FXML
    private Label matricNumberText;
    @FXML
    private Label emailText;
    @FXML
    private Label groupText;



    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        loadBlankMemberPage();
        registerAsAnEventHandler(this);
    }

    //@@author yash-chowdhary
    /**
     * Loads the client page based on {@code client}
     */
    private void callClient(String client, String recipients, String subject, String body) {
        if (client.equalsIgnoreCase(Client.VALID_CLIENT_GMAIL)) {
            String gMailUrl = String.format(GMAIL_URL, recipients, subject, body);
            loadGmailPage(gMailUrl);
        } else if (client.equalsIgnoreCase(Client.VALID_CLIENT_OUTLOOK)) {
            String outlookUrl = String.format(OUTLOOK_URL, recipients, subject, body);
            loadOutlookPage(outlookUrl);
        }
    }

    /**
     * loads the 'Compose Email' page based on the {@code outlookUrl} in Outlook
     * adapted from https://www.codeproject.com/Questions/398241/how-to-open-url-in-java
     */
    private void loadOutlookPage(String outlookUrl) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(URI.create(outlookUrl));
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * loads the 'Compose Email' page based on the {@code gMailUrl} in GMail
     * adapted from https://www.codeproject.com/Questions/398241/how-to-open-url-in-java
     */
    private void loadGmailPage(String gMailUrl) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(URI.create(gMailUrl));
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //@@author

    //@@author th14thmusician
    /**
     * Loads the details of member into a new panel with more details
     * @param member
     */
    private void loadMemberPage(Member member) {
        nameText.setText("Name");
        phoneText.setText("Mobile");
        matricNumberText.setText("Matric Number");
        groupText.setText("Group");
        emailText.setText("E-Mail");
        name.setText(member.getName().fullName);
        setProfilePhoto(member);
        phone.setText(member.getPhone().value);
        matricNumber.setText(member.getMatricNumber().value);
        email.setText(member.getEmail().value);
        group.setText(member.getGroup().groupName);
        createTags(member);
    }

    /**
     * Loads a blank member page if no one is selected
     */
    private void loadBlankMemberPage() {
        name.setText("");
        phone.setText("");
        matricNumber.setText("");
        email.setText("");
        group.setText("");
        nameText.setText("");
        phoneText.setText("");
        matricNumberText.setText("");
        groupText.setText("");
        emailText.setText("");
    }
    //@@author

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handleMemberPanelSelectionChangedEvent(MemberPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadMemberPage(event.getNewSelection().member);
    }

    //@@author yash-chowdhary
    @Subscribe
    private void handleSendingEmailEvent(SendEmailRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Sending email via "
                + event.getClient().toString()));
        callClient(event.getClient().toString(), event.getRecipients(), event.getSubject().toString(),
                event.getBody().toString());
    }

    //@@author amrut-prabhu
    /**
     * Sets the profile photo to the displayed photo shape.
     */
    private void setProfilePhoto(Member member) {
        Image photo;
        String photoPath = member.getProfilePhoto().getProfilePhotoPath();
        if (photoPath.equals(EMPTY_STRING)) {
            photo = new Image(MainApp.class.getResourceAsStream(DEFAULT_PHOTO),
                    PHOTO_WIDTH, PHOTO_HEIGHT, false, true);
        } else {
            try {
                InputStream photoStream = MainApp.class.getResourceAsStream(photoPath);
                photo = new Image("file:" + photoPath, PHOTO_WIDTH, PHOTO_HEIGHT, false, false);
            } catch (NullPointerException npe) {
                photo = new Image(MainApp.class.getResourceAsStream("/images/default.png"), //DEFAULT_PHOTO),
                        PHOTO_WIDTH, PHOTO_HEIGHT, false, true);
            }
        }
        profilePhoto.setImage(photo);
    }
    //@@author

    //@@author yash-chowdhary
    /**
     * Creates the labels for tags by randomly generating a color from `TAG_COLORS`
     */
    private void createTags(Member member) {
        member.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(returnColor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     * Returns a color chosen uniformly at random from TAG_COLORS
     */
    private String returnColor(String tag) {
        return TAG_COLORS[Math.abs(tag.hashCode()) % TAG_COLORS.length];
    }
    //@@author
}
