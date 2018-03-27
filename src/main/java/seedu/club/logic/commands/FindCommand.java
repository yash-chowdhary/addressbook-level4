package seedu.club.logic.commands;

import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.club.model.member.FieldContainsKeywordsPredicate;

/**
 * Finds and lists all members in club book whose field contains any of the argument keywords.
 * Partial match is acceptable.
 * Keyword matching is case insensitive.
 * Finds by all possible fields if prefix is not specified
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_FORMAT = "find [n/  ] || [p/  ] || [e/  ] || [m/  ]"
            + " || [g/  ] || [t/  ]";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all members whose field contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Insert field prefix after 'find' to search by field, otherwise all fields will be searched.\n"
            + "Parameters: "
            + "[ "
            + PREFIX_NAME + " | "
            + PREFIX_PHONE + " | "
            + PREFIX_EMAIL + " | "
            + PREFIX_MATRIC_NUMBER + " | "
            + PREFIX_GROUP + " | "
            + PREFIX_TAG
            + " ]"
            + " KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_GROUP + " logistics";

    private final FieldContainsKeywordsPredicate predicate;

    public FindCommand(FieldContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredMemberList(predicate);
        return new CommandResult(getMessageForMemberListShownSummary(model.getFilteredMemberList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
