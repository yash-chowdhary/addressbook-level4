package seedu.club.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.club.model.poll.Poll;

/**
 * An UI component that displays information of a {@code poll}.
 */
public class PollCard extends UiPart<Region> {

    private static final String FXML = "PollListCard.fxml";
    private static final String DESCRIPTION_TOTAL_VOTE_COUNT = "Total Vote Count: ";

    public final Poll poll;

    private AnswerListPanel answerListPanel;

    @FXML
    private HBox cardPane;

    @FXML
    private Label id;

    @FXML
    private Label question;

    @FXML
    private StackPane answerListPanelPlaceholder;

    @FXML
    private Label totalVoteCount;

    /**
     * A constructor to initialize PollCard using {@value FXML} with results
     */
    public PollCard(Poll poll, int displayedIndex) {
        super(FXML);
        this.poll = poll;
        id.setText(displayedIndex + ". ");
        question.setText(poll.getQuestion().toString());

        answerListPanel = new AnswerListPanel(poll.getAnswers(), poll, true);
        answerListPanelPlaceholder.getChildren().add(answerListPanel.getRoot());

        totalVoteCount.setText(DESCRIPTION_TOTAL_VOTE_COUNT + poll.getTotalVoteCount());
    }

    /**
     * A constructor to initialize PollCard using {@param fxml} without results
     */
    public PollCard(Poll poll, int displayedIndex, String fxml) {
        super(fxml);
        this.poll = poll;
        id.setText(displayedIndex + ". ");
        question.setText(poll.getQuestion().toString());

        answerListPanel = new AnswerListPanel(poll.getAnswers(), poll, false);
        answerListPanelPlaceholder.getChildren().add(answerListPanel.getRoot());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PollCard)) {
            return false;
        }

        // state check
        PollCard card = (PollCard) other;
        return id.getText().equals(card.id.getText())
                && poll.equals(card.poll);
    }
}
