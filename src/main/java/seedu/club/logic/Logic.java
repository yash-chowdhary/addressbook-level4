package seedu.club.logic;

import javafx.collections.ObservableList;
import seedu.club.logic.commands.CommandResult;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.member.Member;
import seedu.club.model.poll.Poll;
import seedu.club.model.task.Task;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /** Returns an unmodifiable view of the filtered list of members */
    ObservableList<Member> getFilteredMemberList();

    /** Returns an unmodifiable view of the filtered list of polls */
    ObservableList<Poll> getFilteredPollList();

    /** Returns an unmodifiable view of the filtered list of tasks */
    ObservableList<Task> getFilteredTaskList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
