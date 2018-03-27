package seedu.club.model.poll;

import java.util.List;
import java.util.function.Predicate;

import seedu.club.commons.util.StringUtil;

/**
 * Tests that a {@code poll}'s {@code question} matches any of the keywords given.
 */
public class PollContainsKeywordsPredicate implements Predicate<Poll> {
    private final List<String> keywords;

    public PollContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Poll poll) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(poll.getQuestion().getValue(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PollContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PollContainsKeywordsPredicate) other).keywords)); // state check
    }

}
