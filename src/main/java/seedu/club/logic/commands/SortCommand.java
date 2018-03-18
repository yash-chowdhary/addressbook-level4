package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

/**
 * Sorts the member list
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "List sorted in alphabetical order";

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        model.sort();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
