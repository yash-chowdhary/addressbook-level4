package seedu.club.model.task.exceptions;

/**
 * Signals that the task list is already listed.
 */
public class TasksAlreadyListedException extends Exception {
    public TasksAlreadyListedException(String message) {
        super(message);
    }
}
