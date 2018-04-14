package seedu.club.ui;

import static seedu.club.model.member.ProfilePhoto.DEFAULT_PHOTO_PATH;
import static seedu.club.model.member.ProfilePhoto.EMPTY_STRING;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import seedu.club.MainApp;
import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.events.ui.ClearMemberSelectPanelEvent;
import seedu.club.commons.events.ui.MemberPanelSelectionChangedEvent;
import seedu.club.commons.events.ui.ModifiedTaskPanelSelecetionChangedEvent;
import seedu.club.commons.events.ui.SendEmailRequestEvent;
import seedu.club.commons.events.ui.UpdateSelectionPanelEvent;
import seedu.club.model.email.Client;
import seedu.club.model.member.Member;
import seedu.club.model.tag.Tag;
import seedu.club.model.task.Task;
import seedu.club.model.task.TaskIsRelatedToMemberPredicate;

/**
 * The Browser Panel of the App.
 */
public class MemberOverviewPanel extends UiPart<Region> {
    public static final String GMAIL_URL = "https://mail.google.com/mail/?view=cm&fs=1&to=%1$s"
            + "&su=%2$s&body=%3$s";
    public static final String OUTLOOK_URL = "https://outlook.office.com/?path=/mail/action/"
            + "compose&to=%1$s&subject=%2$s&body=%3$s";

    private static final String FXML = "MemberDetailsPanel.fxml";

    private static final Integer PHOTO_WIDTH = 130;
    private static final Integer PHOTO_HEIGHT = 152;
    private static final String DEFAULT_PHOTO = "/images/defaultProfilePhoto.png";
    private static final String PHONE_ICON = "/images/phone_icon.png";
    private static final String EMAIL_ICON = "/images/email_icon.png";
    private static final String[] TAG_COLORS = {"red", "yellow", "grey", "brown", "pink", "white",
                                                "orange", "blue", "violet"};

    private static final String DIRECTORY_PATH = "view/";
    private static final String TASK_YET_TO_BEGIN_CSS = DIRECTORY_PATH + "LightTaskYetToBegin.css";
    private static final String TASK_IN_PROGRESS_CSS = DIRECTORY_PATH + "LightTaskInProgress.css";
    private static final String TASK_COMPLETED_CSS = DIRECTORY_PATH + "LightTaskCompleted.css";

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private ObservableList<Task> taskList;
    private Member currentlySelectedMember;
    private Stack<Member> undoStack = new Stack<>();

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
    private ImageView phoneIcon;
    @FXML
    private ImageView emailIcon;
    @FXML
    private ListView<ModifiedTaskCard> modifiedTaskCardListView;
    @FXML
    private GridPane gridPane;


    public MemberOverviewPanel(ObservableList<Task> taskList) {
        super(FXML);
        this.taskList = taskList;
        loadDetails(false);
        registerAsAnEventHandler(this);
    }

    //@@author th14thmusician

    /**
     * Loads a blank details if no one is selected
     * @param show
     */
    public void loadDetails (Boolean show) {
        if (show) {
            gridPane.setStyle("-fx-background-color: #cccccc");
        } else {
            gridPane.setStyle("-fx-background-color: #d6d6d6");
        }
        int size = gridPane.getChildren().size();
        for (int i = 0; i < size; i++) {
            gridPane.getChildren().get(i).setVisible(show);
        }
    }
    public void setConnections(ObservableList<Task> taskList, Member member) {
        loadDetails(true);
        setMemberListView(taskList, member);
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        modifiedTaskCardListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new ModifiedTaskPanelSelecetionChangedEvent(newValue));
                    }
                });
    }

    public void setMemberListView(ObservableList<Task> taskList, Member member) {
        final ObservableList<Task> filteredTaskList = taskList.filtered(new TaskIsRelatedToMemberPredicate(member));
        ObservableList<ModifiedTaskCard> mappedList = EasyBind.map(
                filteredTaskList, (task) -> new ModifiedTaskCard(task, filteredTaskList.indexOf(task) + 1));
        modifiedTaskCardListView.setItems(mappedList);
        modifiedTaskCardListView.setCellFactory(listView -> new TaskListViewCell());
    }

    @Subscribe
    public void handleMemberPanelSelectionChangeEvent(MemberPanelSelectionChangedEvent event) {
        currentlySelectedMember = event.getNewSelection().member;
        loadMemberPage(event.getNewSelection().member);
        setConnections(taskList, event.getNewSelection().member);
    }

    @Subscribe
    private void handleClearMemberSelectPanelEvent (ClearMemberSelectPanelEvent event) {
        if (event.isToClear()) {
            loadDetails(false);
        } else {
            loadDetails(true);
        }
    }

    @Subscribe
    public void handleUpdateSelectionPanelEvent (UpdateSelectionPanelEvent event) {
        if (event.isToUndo()) {
            loadMemberPage(undoStack.pop());
        }
        if (event.getTagToDelete() != null) {
            if (currentlySelectedMember.hasTag(event.getTagToDelete())) {
                undoStack.push(currentlySelectedMember);
                Set<Tag> memberTags = new HashSet<>(currentlySelectedMember.getTags());
                memberTags.remove(event.getTagToDelete());
                currentlySelectedMember = new Member(currentlySelectedMember.getName(),
                        currentlySelectedMember.getPhone(),
                        currentlySelectedMember.getEmail(),
                        currentlySelectedMember.getMatricNumber(),
                        currentlySelectedMember.getGroup(),
                        memberTags,
                        currentlySelectedMember.getCredentials(),
                        currentlySelectedMember.getProfilePhoto());
                loadMemberPage(currentlySelectedMember);
            }
        } else if (event.isToDelete()) {
            if (currentlySelectedMember.equals(event.getToEditMember())) {
                undoStack.push(currentlySelectedMember);
                loadDetails(false);
            }
        } else if (currentlySelectedMember.equals(event.getToEditMember())) {
            undoStack.push(currentlySelectedMember);
            currentlySelectedMember = event.getEditedMember();
            loadMemberPage(event.getEditedMember());
        }
    }

    //@@author
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

    //@@author th14thmusician
    /**
     * Loads the details of member into a new panel with more details
     * @param member
     */
    private void loadMemberPage(Member member) {
        currentlySelectedMember = member;
        name.setText(member.getName().fullName);
        setProfilePhoto(member);
        phone.setText(member.getPhone().value);
        matricNumber.setText(member.getMatricNumber().value);
        email.setText(member.getEmail().value);
        group.setText(member.getGroup().groupName);
        group.setAlignment(Pos.CENTER);
        createTags(member);
        //setIcons();
    }

    /**
     * Set Icon pictures
     */
    /*private void setIcons() {
        Image phoneImg;
        Image emailImg;
        phoneImg = new Image(MainApp.class.getResourceAsStream(PHONE_ICON),
                21, 21, false, true);
        phoneIcon.setImage(phoneImg);
        emailImg = new Image(MainApp.class.getResourceAsStream(EMAIL_ICON),
                21, 21, false, true);
        emailIcon.setImage(emailImg);
    }*/
    //@@author

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
        String photoPath = member.getProfilePhoto().getPhotoPath();
        if (photoPath.equals(EMPTY_STRING) || photoPath.equals(DEFAULT_PHOTO_PATH)) {
            photo = new Image(MainApp.class.getResourceAsStream(DEFAULT_PHOTO), PHOTO_WIDTH, PHOTO_HEIGHT,
                    false, true);
        } else {
            photo = new Image("file:" + photoPath, PHOTO_WIDTH, PHOTO_HEIGHT, false, true);
        }
        profilePhoto.setImage(photo);
    }

    //@@author yash-chowdhary
    /**
     * Creates the labels for tags by randomly generating a color from `TAG_COLORS`
     */
    private void createTags(Member member) {
        tags.getChildren().clear();
        member.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(returnColor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
        tags.setAlignment(Pos.CENTER);
    }

    /**
     * Returns a color chosen uniformly at random from TAG_COLORS
     */
    private String returnColor(String tag) {
        return TAG_COLORS[Math.abs(tag.hashCode()) % TAG_COLORS.length];
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<ModifiedTaskCard> {

        @Override
        protected void updateItem(ModifiedTaskCard task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
                return;
            }

            this.getStylesheets().clear();
            logger.info("Status: " + task.task.getStatus().getStatus());
            if (task.isTaskYetToBegin()) {
                logger.info("In here");
                this.getStylesheets().add(TASK_YET_TO_BEGIN_CSS);
            } else if (task.isTaskInProgress()) {
                this.getStylesheets().add(TASK_IN_PROGRESS_CSS);
            } else if (task.isTaskCompleted()) {
                this.getStylesheets().add(TASK_COMPLETED_CSS);
            }
            setGraphic(task.getRoot());
        }
    }
    //@@author
}
