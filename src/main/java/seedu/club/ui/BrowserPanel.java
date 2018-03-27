package seedu.club.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
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

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";
    public static final String GMAIL_URL = "https://mail.google.com/mail/?view=cm&fs=1&to=%1$s"
            + "&su=%2$s&body=%3$s";
    public static final String OUTLOOK_URL = "https://outlook.office.com/?path=/mail/action/"
            + "compose&to=%1$s&subject=%2$s&body=%3$s";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
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

    private void loadMemberPage(Member member) {
        loadPage(SEARCH_PAGE_URL + member.getName().fullName);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

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
}
