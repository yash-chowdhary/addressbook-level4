package seedu.club.logic.commands;

import static seedu.club.model.Model.PREDICATE_SHOW_ALL_MEMBERS;

/**
 * Lists all members in the club book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all members";


    @Override
    public CommandResult execute() {
        model.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
