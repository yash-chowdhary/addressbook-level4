package seedu.club.logic.commands;

import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.member.FieldContainsKeywordsPredicate;

/**
 * Finds and lists all members in club book whose field contains any of the argument keywords.
 * Partial match is acceptable.
 * Keyword matching is case insensitive.
 * Finds by all possible fields if prefix is not specified
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "f", "search")
    );
    public static final String COMMAND_FORMAT = "find [n/  ] || [p/  ] || [e/  ] || [m/  ]"
            + " || [g/  ] || [t/  ]";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all members whose field contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers. "
            + "Insert the field prefix after 'find' to search by field. Otherwise, all fields will be searched.\n"
            + "Parameters: "
            + "[ "
            + PREFIX_NAME + " or "
            + PREFIX_PHONE + " or "
            + PREFIX_EMAIL + " or "
            + PREFIX_MATRIC_NUMBER + " or "
            + PREFIX_GROUP + " or "
            + PREFIX_TAG
            + " ]"
            + " KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_GROUP + " Logistics";

    private final FieldContainsKeywordsPredicate predicate;

    public FindCommand(FieldContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireToSignUp();
        requireToLogIn();
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
