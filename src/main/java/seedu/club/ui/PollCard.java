package seedu.club.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.club.model.poll.Poll;

/**
 * An UI component that displays information of a {@code poll}.
 */
public class PollCard extends UiPart<Region> {

    private static final String FXML = "PollListCard.fxml";

    public final Poll poll;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label question;
    @FXML
    private FlowPane answers;

    public PollCard(Poll poll, int displayedIndex) {
        super(FXML);
        this.poll = poll;
        id.setText(displayedIndex + ". ");
        question.setText(poll.getQuestion().toString());
        poll.getAnswers().forEach(answer -> answers.getChildren().add(new Label(answer.toString())));
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

    protected Label getId() {
        return id;
    }
}
