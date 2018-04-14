package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String DATE_FIELD_ID = "#date";
    private static final String TIME_FIELD_ID = "#time";
    private static final String ASSIGNOR_FIELD_ID = "#assignor";
    private static final String ASSIGNEE_FIELD_ID = "#assignee";
    private static final String STATUS_FIELD_ID = "#status";

    private final Label idLabel;
    private final Label descriptionLabel;
    private final Label dateLabel;
    private final Label timeLabel;
    private final Label assignorLabel;
    private final Label assigneeLabel;
    private final Label statusLabel;

    public TaskCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        this.dateLabel = getChildNode(DATE_FIELD_ID);
        this.timeLabel = getChildNode(TIME_FIELD_ID);
        this.assignorLabel = getChildNode(ASSIGNOR_FIELD_ID);
        this.assigneeLabel = getChildNode(ASSIGNEE_FIELD_ID);
        this.statusLabel = getChildNode(STATUS_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getTime() {
        return timeLabel.getText();
    }

    public String getAssignor() {
        return assignorLabel.getText();
    }

    public String getAssignee() {
        return assigneeLabel.getText();
    }

    public String getStatus() {
        return statusLabel.getText();
    }

}
