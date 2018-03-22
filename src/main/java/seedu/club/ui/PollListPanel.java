package seedu.club.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.events.ui.CompressMembersRequestEvent;
import seedu.club.commons.events.ui.DecompressMembersRequestEvent;
import seedu.club.commons.events.ui.JumpToListRequestEvent;
import seedu.club.commons.events.ui.MemberPanelSelectionChangedEvent;
import seedu.club.commons.events.ui.PollPanelSelectionChangedEvent;
import seedu.club.model.member.Member;
import seedu.club.model.poll.Poll;

/**
 * Panel containing the list of polls.
 */
public class PollListPanel extends UiPart<Region> {
    private static final String FXML = "PollListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PollListPanel.class);

    @FXML
    private ListView<PollCard> pollListView;

    public PollListPanel(ObservableList<Poll> pollList) {
        super(FXML);
        setConnections(pollList);
        registerAsAnEventHandler(this);
    }


    private void setConnections(ObservableList<Poll> pollList) {
        setpollListView(pollList);
        setEventHandlerForSelectionChangeEvent();
    }

    private void setpollListView(ObservableList<Poll> pollList) {
        ObservableList<PollCard> mappedList = EasyBind.map(
                pollList, (poll) -> new PollCard(poll, pollList.indexOf(poll) + 1));
        pollListView.setItems(mappedList);
        pollListView.setCellFactory(listView -> new pollListViewCell());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        pollListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in poll list panel changed to : '" + newValue + "'");
                        raise(new PollPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PollCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            pollListView.scrollTo(index);
            pollListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PollCard}.
     */
    class pollListViewCell extends ListCell<PollCard> {

        @Override
        protected void updateItem(PollCard poll, boolean empty) {
            super.updateItem(poll, empty);

            if (empty || poll == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(poll.getRoot());
            }
        }
    }
}
