package seedu.club.ui;

import seedu.club.model.member.Member;

/**
 * An UI component that displays compressed information of a {@code member}.
 */
public class CompressedMemberCard extends MemberCard {

    private static final String FXML = "CompressedMemberListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public CompressedMemberCard(Member member, int displayedIndex) {
        super(member, displayedIndex, FXML);
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
        return getId().getText().equals(getId().getText())
                && member.equals(card.member);
    }
}
