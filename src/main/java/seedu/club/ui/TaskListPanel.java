package seedu.club.ui;
//@@author yash-chowdhary
import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.club.model.task.Task;

/**
 * Panel containing tasks to be completed by the members.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private static final String DIRECTORY_PATH = "view/";
    private static final String TASK_YET_TO_BEGIN_CSS = DIRECTORY_PATH + "TaskYetToBegin.css";
    private static final String TASK_IN_PROGRESS_CSS = DIRECTORY_PATH + "TaskInProgress.css";
    private static final String TASK_COMPLETED_CSS = DIRECTORY_PATH + "TaskCompleted.css";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    @FXML
    private ListView<TaskCard> taskListView;

    public TaskListPanel(ObservableList<Task> taskList) {
        super(FXML);
        setConnections(taskList);
        registerAsAnEventHandler(this);
    }

    public void setConnections(ObservableList<Task> taskList) {
        setMemberListView(taskList);
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    public void setMemberListView(ObservableList<Task> taskList) {
        ObservableList<TaskCard> mappedList = EasyBind.map(
                taskList, (task) -> new TaskCard(task, taskList.indexOf(task) + 1));
        taskListView.setItems(mappedList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

    /**
     * Scrolls to the {@code TaskCard} at {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<TaskCard> {

        @Override
        protected void updateItem(TaskCard task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
                return;
            }

            this.getStylesheets().clear();
            logger.info("Status: " + task.task.getStatus().getStatus());
            if (task.isTaskYetToBegin()) {
                logger.info("In here");
                this.getStylesheets().add(TASK_YET_TO_BEGIN_CSS);
            } else if (task.isTaskInProgress()) {
                this.getStylesheets().add(TASK_IN_PROGRESS_CSS);
            } else if (task.isTaskCompleted()) {
                this.getStylesheets().add(TASK_COMPLETED_CSS);
            }
            setGraphic(task.getRoot());
        }
    }
}
