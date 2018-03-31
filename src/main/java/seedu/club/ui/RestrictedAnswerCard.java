package seedu.club.ui;

import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Poll;

/**
 * An UI component that displays information of a {@code answerValue} except the results.
 */
public class RestrictedAnswerCard extends AnswerCard {

    private static final String FXML = "RestrictedAnswerListCard.fxml";

    public RestrictedAnswerCard(Answer answer, int displayedIndex, Poll poll) {
        super(answer, displayedIndex, poll, FXML);
    }
}
