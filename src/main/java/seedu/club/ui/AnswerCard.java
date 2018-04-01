package seedu.club.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Region;
import seedu.club.model.poll.Answer;

/**
 * A UI component that displays information of an {@code answer}.
 */
public class AnswerCard extends UiPart<Region> {

    private static final String FXML = "AnswerListCard.fxml";
    private static final String DESCRIPTION_VOTE_COUNT = "Vote Count: ";
    private final int totalVoteCount;

    @FXML
    private Label answerValue;

    @FXML
    private Label choice;

    @FXML
    private Label voteCount;

    @FXML
    private ProgressIndicator voteCountIndicator;

    /**
     * A constructor to initialize AnswerCard using {@value FXML} with results
     */
    public AnswerCard(Answer answer, int displayedIndex, int totalVoteCount) {
        super(FXML);
        this.totalVoteCount = totalVoteCount;
        choice.setText(displayedIndex + ". ");
        answerValue.setText(answer.getValue());
        voteCount.setText(DESCRIPTION_VOTE_COUNT + answer.getVoteCount());
        setVoteCountIndicator(answer);
    }

    /**
     * A constructor to initialize AnswerCard using {@param fxml} without results
     */
    public AnswerCard(Answer answer, int displayedIndex, int totalVoteCount, String fxml) {
        super(fxml);
        this.totalVoteCount = totalVoteCount;
        choice.setText(displayedIndex + ". ");
        answerValue.setText(answer.getValue());
    }

    private void setVoteCountIndicator(Answer answer) {
        int voteCount = answer.getVoteCount();
        double progress = totalVoteCount == 0
                ? 0 : ((double) voteCount) / totalVoteCount;
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
                && answerValue.equals(card.answerValue);
    }
}
