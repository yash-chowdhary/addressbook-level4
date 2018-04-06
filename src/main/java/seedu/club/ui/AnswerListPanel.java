package seedu.club.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.club.commons.core.LogsCenter;
import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Poll;

/**
 * Panel containing the list of answers.
 */
public class AnswerListPanel extends UiPart<Region> {
    private static final String FXML = "AnswerListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AnswerListPanel.class);
    private final Poll poll;
    private boolean isShowingResults;

    @FXML
    private VBox answersPlaceholder;

    public AnswerListPanel(ObservableList<Answer> answerList, Poll poll, boolean isShowingResults) {
        super(FXML);
        this.poll = poll;
        this.isShowingResults = isShowingResults;
        setAnswersPlaceholder(answerList);
    }

    private void setAnswersPlaceholder(ObservableList<Answer> answerList) {
        int totalVoteCount = poll.getTotalVoteCount();
        ObservableList<Node> children = answersPlaceholder.getChildren();
        if (isShowingResults) {
            for (int index = 0; index < answerList.size(); index++) {
                children.add(new AnswerCard(answerList.get(index), index + 1, totalVoteCount).getRoot());
            }
        } else {
            for (int index = 0; index < answerList.size(); index++) {
                children.add(new RestrictedAnswerCard(answerList.get(index), index + 1, totalVoteCount).getRoot());
            }
        }
    }
}
