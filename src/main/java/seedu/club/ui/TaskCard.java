package seedu.club.ui;

//@@author yash-chowdhary

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.club.model.task.Task;

/**
 * A UI component that displays information of an {@code Order}.
 */
public class TaskCard extends UiPart<Region> {
    private static final String FXML = "TaskListCard.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;

    @FXML
    private Label description;

    @FXML
    private Label id;

    @FXML
    private Label time;

    @FXML
    private Label date;

    @FXML
    private Label assignor;

    @FXML
    private Label assignee;

    @FXML
    private Label status;

    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        description.setText(task.getDescription().getDescription());
        date.setText("Due Date: " + task.getDate().getDate());
        time.setText("Time: " + task.getTime().getTime());
        assignor.setText("Assigned by: " + task.getAssignor().getAssignor());
        assignee.setText("Assigned to: " + task.getAssignee().getAssignee());
        status.setText("Status: " + task.getStatus().getStatus());
    }

    public boolean isTaskYetToBegin() {
        return task.hasTaskNotBegun();
    }

    public boolean isTaskInProgress() {
        return task.isTaskInProgress();
    }

    public boolean isTaskCompleted() {
        return task.isTaskCompleted();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
