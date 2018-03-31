package seedu.club.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Poll;

/**
 * An UI component that displays information of a {@code answerValue}.
 */
public class AnswerCard extends UiPart<Region> {

    private static final String FXML = "AnswerListCard.fxml";
    private static final String DESCRIPTION_VOTE_COUNT = "Vote Count: ";
    private static final String EMPTY_STRING = "";
    private final Answer answer;
    private final Poll poll;

    @FXML
    private HBox cardPane;

    @FXML
    private TextArea answerValue;

    @FXML
    private Label choice;

    @FXML
    private Label voteCount;

    @FXML
    private ProgressIndicator voteCountIndicator;

    public AnswerCard(Answer answer, int displayedIndex, Poll poll) {
        super(FXML);
        this.answer = answer;
        this.poll = poll;
        choice.setText(displayedIndex + ". ");
        answerValue.setText(answer.getValue());
        voteCount.setText(DESCRIPTION_VOTE_COUNT + answer.getVoteCount());
        setVoteCountIndicator();
    }

    private void setVoteCountIndicator() {
        int totalVoteCount = poll.getTotalVoteCount();
        int voteCount = answer.getVoteCount();
        double progress = totalVoteCount == 0
                ? 0 : voteCount / totalVoteCount;
        voteCountIndicator.setProgress(progress);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AnswerCard)) {
            return false;
        }

        // state check
        AnswerCard card = (AnswerCard) other;
        return choice.getText().equals(card.choice.getText())
                && answer.equals(card.answer);
    }
}
