package seedu.club.testutil;

//@@author yash-chowdhary

import static seedu.club.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.club.logic.commands.AddTaskCommand;
import seedu.club.model.task.Task;

/**
 * Utility class for Task
 */
public class TaskUtil {
    public static String getAddTaskCommand(Task task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    private static String getTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DESCRIPTION + task.getDescription().getDescription() + " ");
        sb.append(PREFIX_DATE + task.getDate().getDate() + " ");
        sb.append(PREFIX_TIME + task.getTime().getTime() + " ");
        return sb.toString();
    }
}
