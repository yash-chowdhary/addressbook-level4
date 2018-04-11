package seedu.club.ui;
//@@author MuhdNurKamal
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import seedu.club.model.poll.Answer;

/**
 * A UI component that displays information of an {@code answer}.
 */
public class AnswerCard extends UiPart<Region> {

    private static final String FXML = "AnswerListCard.fxml";
    private static final String DESCRIPTION_VOTE_COUNT = "Votes: ";
    private static final String PERCENTAGE_SYMBOL = "%";
    private final int totalVoteCount;

    @FXML
    private Label answerValue;

    @FXML
    private Label choice;

    @FXML
    private Label voteCount;

    @FXML
    private ProgressBar votePercentageBar;

    @FXML
    private Label votePercentage;

    /**
     * A constructor to initialize AnswerCard using {@value FXML} with results
     */
    public AnswerCard(Answer answer, int displayedIndex, int totalVoteCount) {
        super(FXML);
        this.totalVoteCount = totalVoteCount;
        choice.setText(displayedIndex + ". ");
        answerValue.setText(answer.getValue());
        voteCount.setText(DESCRIPTION_VOTE_COUNT + answer.getVoteCount());
        setVotePercentage(answer);
    }

    /**
     * A constructor to initialize AnswerCard using without results
     *
     * @param fxml file configure layout of this AnswerCard
     */
    public AnswerCard(Answer answer, int displayedIndex, int totalVoteCount, String fxml) {
        super(fxml);
        this.totalVoteCount = totalVoteCount;
        choice.setText(displayedIndex + ". ");
        answerValue.setText(answer.getValue());
    }

    private void setVotePercentage(Answer answer) {
        int voteCount = answer.getVoteCount();
        double voteFraction = totalVoteCount == 0
                ? 0 : ((double) voteCount) / totalVoteCount;
        votePercentageBar.setProgress(voteFraction);
        votePercentage.setText((Math.round(voteFraction * 1000)) / 10 + PERCENTAGE_SYMBOL);
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
