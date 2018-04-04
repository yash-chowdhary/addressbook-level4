package seedu.club.ui;

import seedu.club.model.poll.Answer;

/**
 * An UI component that displays information of a {@code answerValue} except the results.
 */
public class RestrictedAnswerCard extends AnswerCard {

    private static final String FXML = "RestrictedAnswerListCard.fxml";

    public RestrictedAnswerCard(Answer answer, int displayedIndex, int totalVoteCount) {
        super(answer, displayedIndex, totalVoteCount, FXML);
    }
}
