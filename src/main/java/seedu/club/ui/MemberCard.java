package seedu.club.ui;

import static seedu.club.model.member.ProfilePhoto.DEFAULT_PHOTO_PATH;
import static seedu.club.model.member.ProfilePhoto.EMPTY_STRING;

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
    private static final String[] TAG_COLORS = {"red", "yellow", "grey", "brown", "pink", "white",
        "orange", "blue", "violet"};

    public final Member member;

    private final Integer photoWidth = 100;
    private final Integer photoHeight = 130;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

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

    //@@author MuhdNurKamal
    /**
     * A constructor to initialize MemberCard using without matricNumber
     *
     * @param fxml file configure layout of this MemberCard
     */
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
     * Sets the profile photo of {@code member} to the displayed photo shape.
     */
    private void setProfilePhoto(Member member) {
        Image photo;
        String photoPath = member.getProfilePhoto().getPhotoPath();
        if (photoPath.equals(EMPTY_STRING) || photoPath.equals(DEFAULT_PHOTO_PATH)) {
            photo = new Image(MainApp.class.getResourceAsStream(DEFAULT_PHOTO_PATH), photoWidth, photoHeight,
                    false, true);
        } else {
            photo = new Image("file:" + photoPath, photoWidth, photoHeight, false, true);
        }
        profilePhoto.setImage(photo);
    }

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
