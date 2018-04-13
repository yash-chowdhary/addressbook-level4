package seedu.club.ui;
//@@author th14thmusician
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.club.model.task.Task;

/**
 * UI component that displays the summary of the task of a specific person
 */
public class ModifiedTaskCard extends UiPart<Region> {

    private static final String FXML = "ModifiedTaskListCard.fxml";

    public final Task task;

    @FXML
    private Label description;

    @FXML
    private Label id;
    @FXML
    private Label date;

    @FXML
    private Label assignor;

    public ModifiedTaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        description.setText(task.getDescription().getDescription());
        date.setText("Due Date: " + task.getDate().getDate());
        assignor.setText("Assigned by: " + task.getAssignor().getValue());
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
        ModifiedTaskCard card = (ModifiedTaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
//@@author
