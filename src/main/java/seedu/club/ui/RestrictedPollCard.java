package seedu.club.ui;

import seedu.club.model.poll.Poll;

/**
 * An UI component that displays information of a {@code poll} excluding the results.
 */
public class RestrictedPollCard extends PollCard {

    private static final String FXML = "RestrictedPollListCard.fxml";

    public RestrictedPollCard(Poll poll, int displayedIndex) {
        super(poll, displayedIndex, FXML);
    }
}
