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
import seedu.club.commons.events.ui.JumpToListRequestEvent;
import seedu.club.commons.events.ui.MemberPanelSelectionChangedEvent;
import seedu.club.model.member.Member;

/**
 * Panel containing the list of members.
 */
public class MemberListPanel extends UiPart<Region> {
    private static final String FXML = "MemberListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MemberListPanel.class);
    private boolean isDisplayingCompressedMembers;

    @FXML
    private ListView<MemberCard> memberListView;

    public MemberListPanel(ObservableList<Member> memberList) {
        super(FXML);
        isDisplayingCompressedMembers = true;
        setConnections(memberList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Member> memberList) {
        ObservableList<MemberCard> mappedList;
        if (isDisplayingCompressedMembers) {
            mappedList = EasyBind.map(
                    memberList, (member) -> new CompressedMemberCard(member, memberList.indexOf(member) + 1));
        } else {
            mappedList = EasyBind.map(
                    memberList, (member) -> new MemberCard(member, memberList.indexOf(member) + 1));
        }
        memberListView.setItems(mappedList);
        memberListView.setCellFactory(listView -> new MemberListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        memberListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in member list panel changed to : '" + newValue + "'");
                        raise(new MemberPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code MemberCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            memberListView.scrollTo(index);
            memberListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code MemberCard}.
     */
    class MemberListViewCell extends ListCell<MemberCard> {

        @Override
        protected void updateItem(MemberCard member, boolean empty) {
            super.updateItem(member, empty);

            if (empty || member == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(member.getRoot());
            }
        }
    }

}
