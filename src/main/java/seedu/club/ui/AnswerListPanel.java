package seedu.club.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.club.commons.core.LogsCenter;
import seedu.club.model.poll.Answer;

/**
 * Panel containing the list of answers.
 */
public class AnswerListPanel extends UiPart<Region> {
    private static final String FXML = "AnswerListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AnswerListPanel.class);
    private ObservableList<Answer> answerList;

    @FXML
    private ListView<AnswerCard> answerListView;

    public AnswerListPanel(ObservableList<Answer> answerList) {
        super(FXML);
        this.answerList = answerList;
        setConnections(answerList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Answer> answerList) {
        setAnswerListView(answerList);
    }

    private void setAnswerListView(ObservableList<Answer> answerList) {
        ObservableList<AnswerCard> mappedList = EasyBind.map(
                answerList, answer -> new AnswerCard(answer, answerList.indexOf(answer) + 1));
        answerListView.setItems(mappedList);
        answerListView.setCellFactory(listView -> new AnswerListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code AnswerCard}.
     */
    class AnswerListViewCell extends ListCell<AnswerCard> {

        @Override
        protected void updateItem(AnswerCard answer, boolean empty) {
            super.updateItem(answer, empty);

            if (empty || answer == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(answer.getRoot());
            }
        }
    }
}
