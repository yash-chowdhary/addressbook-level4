package seedu.club.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.club.model.poll.Answer;

/**
 * An UI component that displays information of a {@code answerValue}.
 */
public class AnswerCard extends UiPart<Region> {

    private static final String FXML = "AnswerListCard.fxml";
    private static final String EMPTY_STRING = "";
    public final Answer answer;

    @FXML
    private HBox cardPane;
    @FXML
    private Label answerValue;
    @FXML
    private Label choice;

    public AnswerCard(Answer answer, int displayedIndex) {
        super(FXML);
        this.answer = answer;
        choice.setText(displayedIndex + ". ");
        answerValue.setText(answer.getValue());

    }

    public AnswerCard(Answer answer, int displayedIndex, String fxml) {
        super(fxml);
        this.answer = answer;
        choice.setText(displayedIndex + ". ");
        answerValue.setText(answer.getValue());
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

    protected Label getChoice() {
        return choice;
    }
}
