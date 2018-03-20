package seedu.club.ui;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.club.model.member.Member;
import seedu.club.storage.ProfilePhotoStorage;

/**
 * An UI component that displays information of a {@code member}.
 */
public class MemberCard extends UiPart<Region> {

    private static final String FXML = "MemberListCard.fxml";
    private static final Integer PHOTO_WIDTH = 100;
    private static final Integer PHOTO_HEIGHT = 100;
    private static final String DEFAULT_PHOTO = "src/main/resources/images/defaultProfilePhoto.png";
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
    private Circle profilePhoto;

    public MemberCard(Member member, int displayedIndex) {
        super(FXML);
        this.member = member;
        id.setText(displayedIndex + ". ");
        name.setText(member.getName().fullName);
        phone.setText(member.getPhone().value);
        matricNumber.setText(member.getMatricNumber().value);
        group.setText(member.getGroup().groupName);
        email.setText(member.getEmail().value);
        //setProfilePhoto(member);
        member.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    public MemberCard(Member member, int displayedIndex, String fxml) {
        super(fxml);
        this.member = member;
        id.setText(displayedIndex + ". ");
        name.setText(member.getName().fullName);
        phone.setText(member.getPhone().value);
        group.setText(member.getGroup().groupName);
        email.setText(member.getEmail().value);
    }

    //@@author amrut-prabhu
    /**
     * Sets the profile photo to the displayed photo shape.
     */
    private void setProfilePhoto(Member member) {
        Image photo = null;
        String photoPath;

        if (!member.getProfilePhoto().getProfilePhotoPath().equals(EMPTY_STRING)) {
            photoPath = member.getProfilePhoto().getProfilePhotoPath();

            //Defensive programming
            File file = new File(member.getProfilePhoto().getProfilePhotoPath());
            if (!file.exists()) {
                photoPath = ProfilePhotoStorage.getCurrentDirectory() + DEFAULT_PHOTO;
            }
        } else {
            photoPath = ProfilePhotoStorage.getCurrentDirectory() + DEFAULT_PHOTO;
        }

        photo = new Image("file:" + photoPath, PHOTO_WIDTH, PHOTO_HEIGHT, false, false);

        profilePhoto.setFill(new ImagePattern(photo));
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
