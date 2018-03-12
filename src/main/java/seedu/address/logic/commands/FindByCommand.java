package seedu.address.logic.commands;

import seedu.address.model.person.FieldContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose field contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindByCommand<Field> extends Command {

    public static final String COMMAND_WORD = "findBy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose field contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final FieldContainsKeywordsPredicate predicate;

    public FindByCommand(FieldContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindByCommand // instanceof handles nulls
                && this.predicate.equals(((FindByCommand) other).predicate)); // state check
    }
}
