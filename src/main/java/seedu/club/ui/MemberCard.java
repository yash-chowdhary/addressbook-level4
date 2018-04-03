package seedu.club.ui;

import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.club.MainApp;
import seedu.club.model.member.Member;

/**
 * An UI component that displays information of a {@code member}.
 */
public class MemberCard extends UiPart<Region> {

    private static final String FXML = "MemberListCard.fxml";
    private static final Integer PHOTO_WIDTH = 90;
    private static final Integer PHOTO_HEIGHT = 120;
    private static final String[] TAG_COLORS = {"red", "yellow", "grey", "brown", "pink", "white",
        "orange", "blue", "violet"};
    private static final String DEFAULT_PHOTO = "/images/defaultProfilePhoto.png";
    private static final String EMPTY_STRING = "";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Member member;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
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
    private ImageView profilePhoto;

    public MemberCard(Member member, int displayedIndex) {
        super(FXML);
        this.member = member;
        id.setText(displayedIndex + ". ");
        name.setText(member.getName().fullName);
        phone.setText(member.getPhone().value);
        matricNumber.setText(member.getMatricNumber().value);
        group.setText(member.getGroup().groupName);
        email.setText(member.getEmail().value);
        setProfilePhoto(member);
        createTags(member);
    }

    public MemberCard(Member member, int displayedIndex, String fxml) {
        super(fxml);
        this.member = member;
        id.setText(displayedIndex + ". ");
        name.setText(member.getName().fullName);
        phone.setText(member.getPhone().value);
        group.setText(member.getGroup().groupName);
        email.setText(member.getEmail().value);
        setProfilePhoto(member);
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

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MemberCard)) {
            return false;
        }

        // state check
        MemberCard card = (MemberCard) other;
        return id.getText().equals(card.id.getText())
                && member.equals(card.member);
    }

    protected Label getId() {
        return id;
    }
}
