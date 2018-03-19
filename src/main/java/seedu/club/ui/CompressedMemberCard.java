package seedu.club.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.club.model.member.Member;

/**
 * An UI component that displays compressed information of a {@code member}.
 */
public class CompressedMemberCard extends UiPart<Region> {

    private static final String FXML = "CompressedMemberListCard.fxml";

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
    private Label group;
    @FXML
    private Label email;

    public CompressedMemberCard(Member member, int displayedIndex) {
        super(FXML);
        this.member = member;
        id.setText(displayedIndex + ". ");
        name.setText(member.getName().fullName);
        phone.setText(member.getPhone().value);
        group.setText(member.getGroup().groupName);
        email.setText(member.getEmail().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompressedMemberCard)) {
            return false;
        }

        // state check
        CompressedMemberCard card = (CompressedMemberCard) other;
        return id.getText().equals(card.id.getText())
                && member.equals(card.member);
    }
}
